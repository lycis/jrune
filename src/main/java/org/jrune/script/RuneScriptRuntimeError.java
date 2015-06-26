package org.jrune.script;

import org.jrune.core.RuneRuntimeException;

public class RuneScriptRuntimeError extends RuneRuntimeException {
    public RuneScriptRuntimeError(String message) {
	super(message);
    }
}
