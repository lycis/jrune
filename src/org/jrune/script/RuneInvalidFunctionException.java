package org.jrune.script;

import org.jrune.core.RuneRuntimeException;

/**
 * An occurrence of this Exception indicates that a script function was called that does not
 * exist or can not be accessed.
 * 
 * @author edd
 *
 */
public class RuneInvalidFunctionException extends RuneRuntimeException {
    public RuneInvalidFunctionException(String function) {
	super("no such function: "+function);
    }
}
