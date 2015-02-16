package org.jrune.core;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.jrune.entity.Entity;
import org.jrune.entity.EntityLoader;
import org.jrune.entity.InvalidEntityException;
import org.jrune.entity.UnknownEntityException;

/**
 * The engine is the master object. It manages all actions that are related
 * to the game state and is the central point of access for the whole game
 * engine.
 * 
 * @author lycis
 *
 */
public class Engine {
	
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
	
	private Map<String, Entity> _blueprintRegister = new HashMap<>();
	private Map<String, Entity> _activeEntities = new HashMap<>();
	
	
	public Engine() {
		this("."); // use workdir as base path
	}
	
	public Engine(String basePath) {
		this.basePath = basePath;
	}
	
	/**
	 * Starts the engine and loads all blueprints
	 */
	public void start() {
		Logger.getLogger(LOGGER_SUBSYSTEM).info("Enginge is starting (options = " + options.toString()+ ")");
		
		if(!isOptionEnabled(OPTION_LAZY_LOAD)) {
			Logger.getLogger(LOGGER_SUBSYSTEM).info("Lazy loading disabled. Loading ALL entities.");
			EntityLoader el = new EntityLoader(this);
			el.loadAll("");
			Logger.getLogger(LOGGER_SUBSYSTEM).info("Loaded blueprints ("+_blueprintRegister.size()+")");
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
	synchronized public void registerEntityBlueprint(Entity e) {
		if(e == null) {
			Logger.getLogger(LOGGER_SUBSYSTEM).severe("Invalid entity: null entity");
			throw new InvalidEntityException("null entity");
		}
		
		if(e.getProperty(Entity.PROP_ENTITY).isEmpty()) {
			Logger.getLogger(LOGGER_SUBSYSTEM).severe("Invalid entity: missing entity name");
			throw new InvalidEntityException("missing entity name");
		}
		
		Logger.getLogger(LOGGER_SUBSYSTEM).info("entity blueprint registered = " +
				e.getProperty(Entity.PROP_ENTITY));
		
		_blueprintRegister.put(e.getProperty(Entity.PROP_ENTITY), e);
	}
	
	/**
	 * Clone an entity from a given blueprint. If the blueprint is not
	 * known it will be loaded if lazy loading is enabled.
	 * 
	 * @param entityName
	 * @return cloned entity
	 */
	synchronized public Entity cloneEntity(String entityName) throws UnknownEntityException {
		Logger.getLogger(LOGGER_SUBSYSTEM).fine("Cloning entity = "+entityName);
		if(!_blueprintRegister.containsKey(entityName)) {
			if(options.get(OPTION_LAZY_LOAD)) {
				Logger.getLogger(LOGGER_SUBSYSTEM).fine("Lazy loading entity = "+entityName);
				// lazy load entity
				EntityLoader loader = new EntityLoader(this);
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
		
		Entity e = new Entity(_blueprintRegister.get(entityName));
		UUID entityId = UUID.randomUUID();
		while(_activeEntities.containsKey(entityId.toString())) {
			entityId = UUID.randomUUID();
		}
		e.setProperty(Entity.PROP_UID, entityId.toString());
		_activeEntities.put(e.getProperty(Entity.PROP_UID), e);
		return e;
	}
}
