package org.jrune.map;

import org.jrune.core.RuneException;

/**
 * When this Exception is thrown the user attempted to load a map although it is already loaded
 * @author edd
 *
 */
public class DuplicateMapLoadException extends RuneException {
    public DuplicateMapLoadException(String name) {
	super(name);
    }
}
