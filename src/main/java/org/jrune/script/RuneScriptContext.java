package org.jrune.script;

import java.io.File;

import org.jrune.core.RuneEngine;
import org.jrune.entity.RuneEntity;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaDouble;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
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

	// set API references
	luaGlobals = JsePlatform.standardGlobals();
	luaGlobals.STDOUT = System.out; // TODO change to logger
	luaGlobals.set("self", CoerceJavaToLua.coerce(entity));
	luaGlobals.set("inherit", new org.jrune.script.functions.Inherit(this));

	// load and compile script
	try {
	    String scriptPath = entity.getProperty(RuneEntity.PROP_SCRIPT);
	    LuaValue script = luaGlobals.loadfile(scriptPath).call();
	} catch (LuaError e1) {
	    throw new RuneScriptException("compiling script of entity " + entity + " failed", e1);
	}

    }

    /**
     * Invokes a Lua function that is defined in the assigned entity.
     * 
     * @param function
     *            name of the function
     * @return a LuaValue representing the result of the call or
     *         <code>null</code> if the function does not exist
     * @throws RuneScriptException
     */
    public LuaValue executeFunction(String function, Object... args) throws RuneScriptException {
	LuaValue func = luaGlobals.get(function);
	if (func == null || func == LuaValue.NIL) {
	    // function not found
	    if (entity.getEngine().isOptionEnabled(RuneEngine.OPTION_STRICT_FUNCALL)) {
		// throw exception when strict function calls are enabled
		throw new RuneInvalidFunctionException(function);
	    }
	    return null;
	}

	if (!(func instanceof LuaFunction)) {
	    throw new RuneScriptException(function + " is not a function");
	}

	if (args.length < 1) {
	    return func.call();
	} else {

	    // translate arguments into function parameters
	    LuaValue[] luaArgs = new LuaValue[args.length];
	    for (int i = 0; i < args.length; ++i) {
		luaArgs[i] = CoerceJavaToLua.coerce(args[i]);
	    }

	    // execute with parameters
	    Varargs v = func.invoke(LuaValue.varargsOf(luaArgs));

	    // return according value
	    if (v.narg() <= 0) {
		return LuaValue.NIL;
	    } else if (v.narg() == 1) {
		return v.arg1();
	    } else {
		return new LuaTable(v);
	    }
	}
    }

    /**
     * Provides the entity bound to this context.
     * 
     * @return entity that is managed by this script context
     */
    public RuneEntity getEntity() {
	return entity;
    }
}
