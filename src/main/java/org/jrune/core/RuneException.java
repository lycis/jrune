package org.jrune.core;

/**
 * Base class for any exception that can be thrown by the game engine.
 * @author lycis
 *
 */
public class RuneException extends Exception {
	
	public RuneException() {
		super();
	}

	public RuneException(String message) {
		super(message);
	}
	
	public RuneException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RuneException(Throwable cause) {
		super(cause);
	}
	
}
