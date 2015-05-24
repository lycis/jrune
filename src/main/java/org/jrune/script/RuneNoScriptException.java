package org.jrune.script;

import org.jrune.core.RuneRuntimeException;

/**
 * This exception indicates that an action that requires a script can not be executed
 * because no script is available.
 * 
 * I.e. this may happen because OPTION_STRICT_FUNCALL was enabled and an action of a
 * non-scripted entity was triggered.
 * 
 * @author lycis
 *
 */
public class RuneNoScriptException extends RuneRuntimeException {
    public RuneNoScriptException() {
	super("no script available");
    }
}
