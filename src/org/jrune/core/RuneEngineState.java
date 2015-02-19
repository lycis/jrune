package org.jrune.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.jrune.entity.RuneEntity;
import org.json.JSONObject;

/**
 * <p>Represents the current state of the engine. It can be used to save and restore the state
 * for loading and saving game states. An implementation of this class manages all currently
 * active data (e.g. active entities and maps) and provides a reference to it.</p>
 * 
 * <p>It does not hold the global blueprint register or handles loading of entities and maps
 * in any way.</p>
 * 
 * <p>For implementation follow these guidelines:</p>
 * 
 * <p>The handling of entities is based on identifying them by a unique ID. This ID will be
 * used to access any stored entities. It has to be unique even when storing and restoring
 * the game state (e.g. loading and saving). Any specific details are up to you.</p>
 * 
 * <p>Loading and saving should not be handled by specific implementations but by the
 * abstract <code>RuneEngingeState</code> and methods like <code>toJSON()</code> and
 * <code>fromJSON(String json)</code> or equivalent YML based functions.</p>
 * 
 * <p>See <code>DefaultEngingeState</code> for a very simple implementation.</p>
 * 
 * @author lycis
 *
 */
public abstract class RuneEngineState {
	
	// constants
	private static final String PERSIST_KEY_ENTITIES = "entities";
	
	/**
	 * Provides all currently active clones of entity blueprints aka
	 * all active entity clones.
	 * @return
	 */
	public abstract Collection<String> getActiveEntityIds();
	
	/**
	 * Returns the active entity clone with the given ID or <code>null</code>
	 * if the id is unknown.
	 */
	public abstract RuneEntity getActiveEntity(String id);
	
	/**
	 * Adds the given entity to the managed active entity clones. It has to return
	 * the ID that this entity can be accessed with. An implementation of this
	 * method also has to set the ID of the entity to property <code>RuneEntity.PROP_UID</code>.
	 * 
	 * @param entity
	 * @return id of the entity
	 */
	public abstract String addActiveEntity(RuneEntity entity);
	
	/**
	 * Translates the current state into a JSON object. Implementations should not
	 * overwrite this.
	 * @return
	 */
	public String toJSON() {
		JSONObject json = new JSONObject();
		
		// persist active entities
		List<JSONObject> entities = new ArrayList<>();
		for(String id: getActiveEntityIds()) {
			RuneEntity e = getActiveEntity(id);
			if(e == null) {
				Logger.getLogger(RuneEngine.LOGGER_SUBSYSTEM).warning("Entity '"+id+"' is listed as active but <null>");
				continue; // safety net
			}
			
			JSONObject entityJSON = new JSONObject();
			e.getProperties().stream().forEach(prop -> entityJSON.put(prop, e.getProperty(prop)));
			entities.add(entityJSON);
		}
		json.put(PERSIST_KEY_ENTITIES, entities);
		
		return json.toString();
	}
}
