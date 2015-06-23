package org.jrune.map;

/**
 * Thrown when a map can not be loaded from a given file.
 * @author lycis
 *
 */
public class MapLoadException extends RuntimeException {
    public MapLoadException(String name) {
	super(name);
    }
    
    public MapLoadException(String name, Throwable cause) {
	super(name);
    }
}
