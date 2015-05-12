package org.jrune.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.jrune.entity.RuneEntity;

/**
 * Default implementation for IRuneEngineState that is normally used without any
 * specific engine flags or options.
 * 
 * @author lycis
 *
 */
class DefaultEngineState extends RuneEngineState {
    private Map<String, RuneEntity> _activeEntities = new HashMap<>();

    @Override
    public RuneEntity getActiveEntity(String id) {
	if (!_activeEntities.containsKey(id)) {
	    return null;
	}

	return _activeEntities.get(id);
    }

    @Override
    public Collection<String> getActiveEntityIds() {
	return _activeEntities.keySet();
    }

    @Override
    public String addActiveEntity(RuneEntity entity) {
	// generate UID if entity does not already have one
	if (!entity.hasProperty(RuneEntity.PROP_UID)) {
	    UUID entityId = UUID.randomUUID();
	    while (_activeEntities.containsKey(entityId.toString())) {
		entityId = UUID.randomUUID();
	    }

	    // set id for entity
	    entity.setProperty(RuneEntity.PROP_UID, entityId.toString());
	}

	// add to list of clones
	_activeEntities.put(entity.getProperty(RuneEntity.PROP_UID), entity);

	return entity.getProperty(RuneEntity.PROP_UID);
    }

    @Override
    public void removeActiveEntity(RuneEntity entity) {
	if (!_activeEntities.containsKey(entity.getProperty(RuneEntity.PROP_UID))) {
	    return; // entity is not managed by this state
	}

	_activeEntities.remove(entity.getProperty(RuneEntity.PROP_UID));
    }

    @Override
    public void discard() {
	// remove all entities from this state
	_activeEntities.clear();

    }

}
