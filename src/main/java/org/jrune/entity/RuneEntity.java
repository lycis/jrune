package org.jrune.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jrune.core.RuneEngine;
import org.jrune.core.RuneRuntimeException;
import org.jrune.script.RuneNoScriptException;
import org.jrune.script.RuneScriptContext;
import org.jrune.script.RuneScriptException;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;

/**
 * This represents any entity within the game with.
 * 
 * It manages all properties of an entity as well as its scripted
 * behaviour. This class may represent a cloned entity as well as a 
 * blueprint for an entity.
 * 
 * @author lycis
 *
 */
public class RuneEntity {
	
	// property constants
	/** system property for the uid of the entity **/
	public static String PROP_UID      = "$uid";
	
	/** system property for the base entity in case of inheritance **/
	public static String PROP_BASE     = "$base";
	
	/** system property to identify the entity type **/
	public static String PROP_ENTITY   = "$entity";
	
	/** system property that stores the map and coordinates of the entity **/
	public static String PROP_LOCATION = "$location";
	
	/** system property that tells if the object occupies space **/
	public static String PROP_PASSABLE = "$passable";
	
	/** system property that specifies actions of the entity **/
	public static String PROP_ACTIONS  = "$actions";
	
	/** system property that identifies the associated script file **/
	public static String PROP_SCRIPT   = "$script";
	
	// mapping of class type to conversion algorithm
	private static Map<Class<?>, IRunePropertyValueConversion<?> > valueConversionMap = new HashMap<>();
	
	// internal variables
	private Map<String, String> properties; // properties of entity
	private RuneScriptContext scriptContext;
	private RuneEngine engine;
	
	// static initialisation block
	static {
	    registerPropertyValueConversion(String.class, new StringConversion());
	}
	
	// constructors
	public RuneEntity(RuneEngine engine) {
		properties = new HashMap<>();
		this.engine = engine;
	}
	
	/**
	 * Copies a property from another one that serves as
	 * blueprint. It performs a copy and circumvents any
	 * property hooks.
	 * @param blueprint
	 */
	public RuneEntity(RuneEntity blueprint, RuneEngine engine) {
		this(engine);
		for(String prop: blueprint.properties.keySet()) {
			properties.put(prop, blueprint.properties.get(prop));
		}
		setScriptContext(blueprint.getScriptContext());
	}
	
	// methods
	/**
	 * Set or change the value of a property.
	 * @param prop
	 * @param value
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setProperty(String prop, Object value) {
	    Class<?> valueClass = value.getClass();
	    
	    // check if conversion for that type is registered
	    if(!valueConversionMap.containsKey(value.getClass())) {
		// throw error if not
		throw new RuneMissingConversionForTypeException(value.getClass().toString());
	    }
	    
	    // convert value
	    IRunePropertyValueConversion conversion = valueConversionMap.get(valueClass);
	    
	    properties.put(prop, conversion.toPropertyValue(value));
	}
	
	/**
	 * Provides the value of a property. In case this property
	 * does not exist it will return an empty string.
	 * @param prop
	 * @return
	 */
	public String getProperty(String prop) {
		if(!hasProperty(prop)) {
			return "";
		}
		
		return properties.get(prop);
	}
	
	/**
	 * Indicates whether the entity has a property of that name.
	 * @param prop
	 * @return
	 */
	public boolean hasProperty(String prop) {
		return properties.containsKey(prop);
	}
	
	/**
	 * Checks if an entity is based on another.
	 * @param entityName
	 * @return
	 */
	public boolean isBasedOn(String entityName) {
		if(!hasProperty(PROP_BASE)) {
			return false; // no base entity
		}
		
		if(getProperty(PROP_ENTITY).equals(entityName)) {
			return true; // the same entity
		}
		
		if(getProperty(PROP_BASE).equals(entityName)) {
			return true; // directly based on entity
		}
		
		// if this entity is not directly based on the requested one, do a recursive search		
		return engine.getBlueprint(getProperty(PROP_BASE)).isBasedOn(entityName);
	}
	
	/**
	 * Return all available properties.
	 * @return
	 */
	public Set<String> getProperties() {
		return properties.keySet();
	}
	
	/**
	 * Provides the caller with the compiled script of the entity.
	 * @return <code>null</code> if no script is set
	 */
	public RuneScriptContext getScriptContext() {
	    return scriptContext;
	}
	
	/**
	 * Sets the script that controls this entity. Compilation of the script has to be
	 * done beforehand.
	 * @param script
	 */
	public void setScriptContext(RuneScriptContext context) {
	    scriptContext = context;
	}
	
	@Override
	public String toString() {
	    return getProperty(PROP_ENTITY)+"["+(hasProperty(PROP_UID)?getProperty(PROP_UID):"!")+"]";
	}
	
	/**
	 * Directly call a script action of the entity. When no script is loaded or the action
	 * is not defined the call will silently be ignored.
	 * @param action
	 */
	public void call(String action) throws RuneScriptException {
	    if(scriptContext == null) {
		if(engine.isOptionEnabled(RuneEngine.OPTION_STRICT_FUNCALL)) {
		    // throw exception when strict function calls are enabled
		    throw new RuneNoScriptException();
		}
		return;
	    }
	    
	    scriptContext.executeFunction(action);
	}
	
	/**
	 * Provides a link to the rune engine that this entity is registered to.
	 * @return
	 */
	public RuneEngine getEngine() {
	    return engine;
	}
	
	/**
	 * Reassigns this entity to the given engine.
	 * @param engine
	 */
	public void setEngine(RuneEngine engine) {
	    this.engine = engine;
	}
	
	// TODO actions will be registered on the entity in YML and map to functions
	
	/**
	 * Registers a new conversion algorithm for a type of object (= class). When
	 * setting a property to an object it will be converted using this algorithm.
	 * 
	 * @param type class that will be converted
	 * @param conversion algorithm for conversion
	 */
	public static <T> void registerPropertyValueConversion(Class<T> type, IRunePropertyValueConversion<T> conversion) {
	    valueConversionMap.put(type, conversion);
	}
}
