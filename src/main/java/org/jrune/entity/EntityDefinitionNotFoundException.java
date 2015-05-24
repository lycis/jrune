package org.jrune.entity;

import org.jrune.core.RuneRuntimeException;

/**
 * This exception indicates that the given entity description file could
 * not be found.
 * 
 * @author lycis
 *
 */
public class EntityDefinitionNotFoundException extends RuneRuntimeException {
	
	public EntityDefinitionNotFoundException(String entityName) {
		super(entityName);
	}
	
}
