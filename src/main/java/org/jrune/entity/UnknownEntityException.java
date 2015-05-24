package org.jrune.entity;

import org.jrune.core.RuneException;

/**
 * Entity was not found for cloning
 * 
 * @author lycis
 *
 */
public class UnknownEntityException extends RuneException {
	public UnknownEntityException(String name) {
		super(name);
	}
	
	public UnknownEntityException(String name, Throwable cause) {
		super(name, cause);
	}
}
