package org.jrune.script;

import org.jrune.core.RuneException;

/**
 * An exception of this kind will be thrown when something with the scripting interface went wrong.
 * This may happen for example when the compilation of a script during the loading of an entity blueprint
 * fails for some reason.
 * 
 * @author lycis
 *
 */
public class RuneScriptException extends RuneException {
    public RuneScriptException(String message) {
	super(message);
    }

    public RuneScriptException(String message, Throwable t) {
	super(message, t);
    }
}
