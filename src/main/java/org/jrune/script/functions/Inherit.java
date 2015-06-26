package org.jrune.script.functions;

import java.util.Arrays;
import java.util.List;

import org.jrune.entity.EntityDefinitionNotFoundException;
import org.jrune.entity.RuneEntity;
import org.jrune.entity.RuneEntityLoader;
import org.jrune.script.RuneScriptContext;
import org.jrune.script.RuneScriptException;
import org.jrune.script.RuneScriptRuntimeError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

/**
 * Provides the function <i>inherit</i> for the scripting API. calling it causes
 * an entity to inherit all properties and script functions from the given
 * parent.
 * 
 * To explicitly access functions of the parent (e.g. when overwriting them) use
 * the object called <code>parent</code>
 * 
 * <i>Example:</i> <code>
 * inherit('parent.entity')
 * parent:someInheritedFunction()
 * </code>
 * 
 * @author edd
 *
 */
public class Inherit extends OneArgFunction {
    private static String[] notInheritedProps = {
	    RuneEntity.PROP_ENTITY,
	    RuneEntity.PROP_BASE,
	    RuneEntity.PROP_UID,
	    RuneEntity.PROP_LOCATION,
	    RuneEntity.PROP_SCRIPT
    };

    private RuneScriptContext context = null;

    public Inherit(RuneScriptContext context) {
	this.context = context;
    }

    @Override
    public LuaValue call(LuaValue arg) {
	System.out.println("inheriting: "+arg.tostring());
	if (!arg.isstring()) {
	    throw new RuneScriptRuntimeError("invalid or missing argument");
	}

	RuneEntity self = context.getEntity();
	if (self == null) {
	    throw new RuneScriptRuntimeError("inherit is only allowed within entity context");
	}

	RuneEntity parent = self.getEngine().getBlueprint(arg.toString());
	if (parent == null) {
	    // entity not yet loaded -> try loading and fail if it does not
	    // exist
	    RuneEntityLoader loader = new RuneEntityLoader(self.getEngine());
	    try {
		loader.load(arg.toString());
	    } catch (RuneScriptException e) {
		throw new RuneScriptRuntimeError("error loading parent entity: " + e.getMessage());
	    } catch (EntityDefinitionNotFoundException e) {
		throw new RuneScriptRuntimeError("parent entity for inheritance does not exist");
	    }

	    parent = self.getEngine().getBlueprint(arg.toString());
	}

	// copy properties from parent
	List<String> excludedProps = Arrays.asList(notInheritedProps);
	for (String prop : parent.getProperties()) {
	    if (!excludedProps.contains(prop)) {
		self.setProperty(prop, parent.getProperty(prop));
	    }
	}
	
	self.setProperty(RuneEntity.PROP_BASE, parent.getProperty(RuneEntity.PROP_ENTITY));

	return LuaValue.valueOf(true);
    }

}
