package org.jrune.script;

import java.io.File;

import org.jrune.core.RuneEngine;
import org.jrune.entity.RuneEntity;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * This is the context for a script. It provides the interface between the
 * engine and the script as well as any API functionality for the included
 * script.
 * 
 * @author lycis
 *
 */
public class RuneScriptContext {

    // private LuaValue script = null;
    private RuneEntity entity = null;
    private Globals luaGlobals = null;

    /**
     * Provides a script context for the given entity. This does <i>not</i>
     * assign the script context as primary script context for this entity!
     * 
     * @param entity
     * @throws RuneScriptException
     */
    public RuneScriptContext(RuneEntity entity) throws RuneScriptException {
	this.entity = entity;

	// load and compile script
	try {
	    String scriptPath = entity.getEngine().basePath() + File.separator + "script" + File.separator
		+ entity.getProperty(RuneEntity.PROP_SCRIPT);
	    luaGlobals = JsePlatform.standardGlobals();
	    LuaValue script = luaGlobals.loadfile(scriptPath).call();
	    System.out.println(script.tojstring());
	} catch (LuaError e1) {
	    throw new RuneScriptException("compiling script of entity " + entity + " failed", e1);
	}

	// set API references
	luaGlobals.STDOUT = System.out; // TODO change to logger
	// TODO do not pass entity directly but wrapped
	luaGlobals.set("self", CoerceJavaToLua.coerce(entity)); 
    }

    /**
     * Calls a lua function as action 
     * 
     * @param action
     * @throws RuneScriptException
     */
    public void call(String action) throws RuneScriptException {
	// TODO actions will be registered on the entity in YML and map to functions
	LuaValue func = luaGlobals.get(action);
	if (func == null || func == LuaValue.NIL) {
	    // function not found
	    if (entity.getEngine().isOptionEnabled(RuneEngine.OPTION_STRICT_FUNCALL)) {
		// throw exception when strict function calls are enabled
		throw new RuneInvalidFunctionException(action);
	    }
	    return;
	}

	if (!(func instanceof LuaFunction)) {
	    throw new RuneScriptException(action + " is not a function");
	}

	func.call();
    }
}
