package org.jrune.core;

import java.util.Map;

import org.jrune.entity.RuneEntity;

/**
 * Represents the current state of the engine. It can be used to save and restore the state
 * for loading and saving game states. An implementation of this class manages all currently
 * active data (e.g. active entities and maps) and provides a reference to it.
 * 
 * It does not hold the global blueprint register or handles loading of entities and maps
 * in any way.
 * 
 * @author lycis
 *
 */
public interface IRuneEngineState {
	
	/**
	 * Provides all currently active clones of entity blueprints aka
	 * all active entity clones.
	 * @return
	 */
	public Map<String, RuneEntity> getActiveEntities();
	
	/**
	 * Returns the active entity clone with the given ID or <code>null</code>
	 * if the id is unknown.
	 */
	public RuneEntity getActiveEntity(String id);
	
}
