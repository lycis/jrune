package org.jrune.map;

public class MapNotFoundException extends RuntimeException {
    public MapNotFoundException(String name) {
	super(name);
    }
}
