package org.jrune.core;

import java.util.Map;

import org.jrune.entity.RuneEntity;

/**
 * Default implementation for IRuneEngineState that is normally used without any
 * specific engine flags or options.
 * 
 * @author lycis
 *
 */
class DefaultEngineState implements IRuneEngineState{

	@Override
	public Map<String, RuneEntity> getActiveEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RuneEntity getActiveEntity(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
