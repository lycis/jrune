package org.jrune.core;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.jrune.entity.InvalidEntityException;
import org.jrune.entity.RuneEntity;
import org.jrune.entity.RuneEntityLoader;
import org.jrune.entity.UnknownEntityException;

/**
 * The engine is the master object. It manages all actions that are related
 * to the game state and is the central point of access for the whole game
 * engine.
 * 
 * @author lycis
 *
 */
public class RuneEngine {
	
	// constants
	
	/** Option for lazy loading. When enabled the engine will load entities,
	 *  maps, scripts and other objects only when needed. If disabled everything
	 *  will be loaded at startup. 
	 */
	public static int OPTION_LAZY_LOAD = 1;
	
	public static String LOGGER_SUBSYSTEM = "JRune";
	
	// private members
	private String basePath = "";
	private BitSet options = new BitSet(32);
	
	private Map<String, RuneEntity> _blueprintRegister = new HashMap<>();
	private RuneEngineState _gameState = null;
	
	
	public RuneEngine() {
		this("."); // use workdir as base path
	}
	
	public RuneEngine(String basePath) {
		this.basePath = basePath;
	}
	
	/**
	 * Starts the engine and loads all blueprints
	 */
	public void start() {
		Logger.getLogger(LOGGER_SUBSYSTEM).info("Enginge is starting (options = " + options.toString()+ ")");
		
		// load entities (without OPTION_LAZY_LOAD
		if(!isOptionEnabled(OPTION_LAZY_LOAD)) {
			Logger.getLogger(LOGGER_SUBSYSTEM).info("Lazy loading disabled. Loading ALL entities.");
			RuneEntityLoader el = new RuneEntityLoader(this);
			el.loadAll("");
			Logger.getLogger(LOGGER_SUBSYSTEM).info("Loaded blueprints ("+_blueprintRegister.size()+")");
		}
		
		// instantiate game state if not already loaded
		if(_gameState == null) {
			Logger.getLogger(LOGGER_SUBSYSTEM).info("Instantiating new game state");
			_gameState = new DefaultEngineState();
		} else {
			Logger.getLogger(LOGGER_SUBSYSTEM).info("Reusing previously loaded game state.");
		}
		
		Logger.getLogger(LOGGER_SUBSYSTEM).info("ready for use");
	}
	
	/**
	 * Enable an engine option.
	 * @param option
	 */
	public void enableOption(int option) {
		options.set(option);
	}
	
	/**
	 * Disable an engine option.
	 * @param option
	 */
	public void disableOption(int option) {
		options.clear(option);
	}
	
	/**
	 * Check if the given option is enabled.
	 * @param option
	 * @return
	 */
	public boolean isOptionEnabled(int option) {
		return options.get(option);
	}
	
	/**
	 * Provides the base path of the engine. This is the folder where
	 * everything is stored.
	 * @return
	 */
	public String basePath() {
		return basePath;
	}
	
	/**
	 * Registers a new entity blueprint with the engine. It can be
	 * cloned and used afterwards. If a blueprint with the given name
	 * already exists it will be overwritten.
	 * @param e
	 */
	synchronized public void registerEntityBlueprint(RuneEntity e) {
		if(e == null) {
			Logger.getLogger(LOGGER_SUBSYSTEM).severe("Invalid entity: null entity");
			throw new InvalidEntityException("null entity");
		}
		
		if(e.getProperty(RuneEntity.PROP_ENTITY).isEmpty()) {
			Logger.getLogger(LOGGER_SUBSYSTEM).severe("Invalid entity: missing entity name");
			throw new InvalidEntityException("missing entity name");
		}
		
		Logger.getLogger(LOGGER_SUBSYSTEM).info("entity blueprint registered = " +
				e.getProperty(RuneEntity.PROP_ENTITY));
		
		_blueprintRegister.put(e.getProperty(RuneEntity.PROP_ENTITY), e);
	}
	
	/**
	 * Clone an entity from a given blueprint. If the blueprint is not
	 * known it will be loaded if lazy loading is enabled.
	 * 
	 * @param entityName
	 * @return cloned entity
	 */
	synchronized public RuneEntity cloneEntity(String entityName) throws UnknownEntityException {
		Logger.getLogger(LOGGER_SUBSYSTEM).fine("Cloning entity = "+entityName);
		if(!_blueprintRegister.containsKey(entityName)) {
			if(options.get(OPTION_LAZY_LOAD)) {
				Logger.getLogger(LOGGER_SUBSYSTEM).fine("Lazy loading entity = "+entityName);
				// lazy load entity
				RuneEntityLoader loader = new RuneEntityLoader(this);
				try{
					loader.load(entityName);
				} catch(RuneRuntimeException ex) {
					Logger.getLogger(LOGGER_SUBSYSTEM).severe("Lazy loading failed: "+ex.getMessage());
					throw new UnknownEntityException(entityName, ex);
				}
			} else {
				Logger.getLogger(LOGGER_SUBSYSTEM).severe("UnknownEntity: "+entityName);
				throw new UnknownEntityException(entityName);
			}
		}
		
		RuneEntity e = new RuneEntity(_blueprintRegister.get(entityName));
		_gameState.addActiveEntity(e);
		return e;
	}
}
