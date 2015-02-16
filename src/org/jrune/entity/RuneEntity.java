package org.jrune.entity;

import java.util.HashMap;
import java.util.Map;

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
	
	// internal variables
	private Map<String, String> properties;
	
	// constructors
	public RuneEntity() {
		properties = new HashMap<>();
	}
	
	/**
	 * Copies a property from another one that serves as
	 * blueprint. It performs a copy and circumvents any
	 * property hooks.
	 * @param blueprint
	 */
	public RuneEntity(RuneEntity blueprint) {
		this();
		for(String prop: blueprint.properties.keySet()) {
			properties.put(prop, blueprint.properties.get(prop));
		}
	}
	
	// methods
	/**
	 * Set or change the value of a property.
	 * @param prop
	 * @param value
	 */
	public void setProperty(String prop, String value) {
		properties.put(prop, value);
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
		
		// if this entity is not directly based on the requested one we do a recursive search
		// TODO once engine reference is available
		return false;
	}
}
