package org.jrune.script;

import org.jrune.entity.RuneEntity;
import org.luaj.vm2.LuaDouble;
import org.luaj.vm2.LuaValue;

/**
 * This class wraps any entities when they are referenced from within a script. It is one of the
 * basic classes that make up the scripting API.
 * 
 * @author lycis
 *
 */
public class RuneEntityScriptWrapper {
    private RuneEntity entity = null;
    
    /**
     * Create a new wrapper for the given entity.
     * @param entity
     */
    public RuneEntityScriptWrapper(RuneEntity entity) {
	this.entity = entity;
    }
    
    /**
     * set a property from lua
     * @param name name of the property
     * @param value
     */
    public void setProperty(String name, Object value) {	
	// todo table & function
	entity.setProperty(name, value);
    }
}
