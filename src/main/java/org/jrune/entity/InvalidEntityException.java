package org.jrune.entity;

import org.jrune.core.RuneRuntimeException;

/**
 * Indicates that an entity does not meet specific criteria that are
 * required for an entity (example: wrong file format, missing system props)
 * 
 * @author lycis
 *
 */

public class InvalidEntityException extends RuneRuntimeException {
	
	public InvalidEntityException(String message) {
		super(message);
	}
	
	public InvalidEntityException(String message, Throwable t) {
		super(message, t);
	}
}
