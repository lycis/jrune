package org.jrune.core;

/**
 * Base class for any runtime exception that might occur.
 * 
 * @author lycis
 *
 */
public class RuneRuntimeException extends RuntimeException {
	public RuneRuntimeException() {
		super();
	}
	
	public RuneRuntimeException(String message) {
		super(message);
	}
	
	public RuneRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RuneRuntimeException(Throwable cause) {
		super(cause);
	}
}
