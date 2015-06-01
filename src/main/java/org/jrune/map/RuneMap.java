package org.jrune.map;

/**
 * Represents the current status of a map. It handles everything related
 * to this map like positioning entities and triggering actions.
 * 
 * A map is based on a YAML file that describes it.
 * 
 * @author lycis
 *
 */
public class RuneMap {
    private long width  = 0;
    private long height = 0;
    
    /**
     * Initialises an empty map.
     */
    public RuneMap() {
	width = 0;
	height = 0;
    }
    
    /**
     * Initialises an empty map with the given measurements.
     * @param width
     * @param height
     */
    public RuneMap(long width, long height) {
	super();
	this.width = 0;
	this.height = 0;
    }
    
    /**
     * 
     * @return total width of the map
     */
    public long getWidth() {
	return width;
    }
    
    /**
     * Change the width of the map to a new value.
     * @param width new width
     */
    public void setWidth(long width) {
	this.width = width;
    }
    
    /**
     * Provides the total height of a map.
     * @return height of a map
     */
    public long getHeight() {
	return height;
    }
    
    /**
     * Change the height of the map to a new value.
     * @param height new height
     */
    public void setHeight(long height) {
	this.height = height;
    }
}
