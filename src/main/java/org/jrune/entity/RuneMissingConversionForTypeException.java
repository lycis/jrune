package org.jrune.entity;

import org.jrune.core.RuneRuntimeException;

/**
 * This exception is thrown when you try to set a property from an object
 * that does not have a conversion algorithm registered for its type.
 * 
 * @author lycis
 *
 */
public class RuneMissingConversionForTypeException extends RuneRuntimeException {
    
    public RuneMissingConversionForTypeException(String type) {
	super(type);
    }
}
