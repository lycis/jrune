package org.jrune.map;

import java.util.ArrayList;
import java.util.List;

import org.jrune.core.RuneEngine;
import org.jrune.entity.RuneEntity;

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
    private int width  = 0;
    private int height = 0;
    private List<List<List<RuneEntity> > > placedEntities = null;
    private RuneEngine engine = null;
    private String name = "";
    
    /**
     * Initialises an empty map.
     */
    public RuneMap(RuneEngine engine) {
	width = 0;
	height = 0;
	this.engine = engine;
    }
    
    /**
     * Initialises an empty map with the given measurements.
     * @param width
     * @param height
     */
    public RuneMap(RuneEngine engine, int width, int height) {
	this(engine);
	this.width = width;
	this.height = height;
	resizePlacedEntities();
    }
    
    /**
     * 
     * @return total width of the map
     */
    public int getWidth() {
	return width;
    }
    
    /**
     * Change the width of the map to a new value.
     * @param width new width
     */
    public void setWidth(int width) {
	this.width = width;
	resizePlacedEntities();
    }
    
    /**
     * Returns the associated engine.
     * @return
     */
    public RuneEngine gtEngine() {
	return engine;
    }
    
    /**
     * Associates an engine with this map.
     * @param engine
     */
    public void setEngine(RuneEngine engine) {
	this.engine = engine;
    }
    
    /**
     * Provides the total height of a map.
     * @return height of a map
     */
    public int getHeight() {
	return height;
    }
    
    /**
     * Change the height of the map to a new value.
     * @param height new height
     */
    public void setHeight(int height) {
	this.height = height;
	resizePlacedEntities();
    }
    
    /**
     * Places an entity on the given position.
     * @param uid id of the entity
     * @param x x-ordinate
     * @param y y-ordinate
     * @return <code>true</code> if the entity was placed
     */
    public boolean setEntityPosition(String uid, int x, int y) {
	RuneEntity e = engine.getState().getActiveEntity(uid);
	if(e == null) {
	    return false;
	}
	
	return setEntityPosition(e, x, y);
    }
    
    /**
     * Places an entity on the given position.
     * @param e entity
     * @param x x-ordinate
     * @param y y-ordinate
     * @return <code>true</code> if the entity was placed
     */

    public boolean setEntityPosition(RuneEntity e, int x, int y) {
	if(e == null) {
	    return false;
	}
	
	if(!canMoveTo(e, x, y)) {
	    return false;
	}
	
	List<RuneEntity> entitiesAtLocation = getEntitiesAt(x, y);
	entitiesAtLocation.add(e);
	e.setProperty(RuneEntity.PROP_LOCATION, getName()+":"+x+":"+y);
	return true;
    }
    
    /**
     * Check if the given coordinate is on the map.
     * @param x
     * @param y
     * @return <code>true</code> if the coordinate is on the map
     */
    public boolean isCoordinateOnMap(int x, int y) {
	if(x > width || x < 0) {
	    return false;
	}
	
	if(y > height || y < 0) {
	    return false;
	}
	
	return true;
    }

    /**
     * Check if the given entity can be moved to the given location.
     * This method does not indicate why it is not possible to move there.
     * 
     * @param e entity
     * @param x coordinate
     * @param y coordinate
     * @return <code>true</code> if the entity can be moved to this square
     */
    public boolean canMoveTo(RuneEntity e, int x, int y) {
	if(!isCoordinateOnMap(x, y)) {
	    return false;
	}
	
	// entity can not share space with other non-passable entities
	if(!e.getProperty(Boolean.class, RuneEntity.PROP_PASSABLE) && isOccupied(x, y)) {
	   return false;
	}
	
	// TODO further checks
	return true;
    }

    /**
     * Tells if the given coordinate is already occupied by something else.
     * This method does not distinguish on different circumstances that occupy
     * this square.
     * 
     * @param x coordinate
     * @param y coordinate
     * @return <code>true</code> if the square is occupied by something
     */
    public boolean isOccupied(int x, int y) {
	// check if this square is blocked by a non-passable entity
	List<RuneEntity> entities = getEntitiesAt(x, y);
	for(RuneEntity e: entities) {
	    if(!e.getProperty(Boolean.class, RuneEntity.PROP_PASSABLE)) {
		return true;
	    }
	}
	return false;
    }
    
    List<RuneEntity> getEntitiesAt(int x, int y) {
	return placedEntities.get(x).get(y);
    }

    /**
     * Resize the list of placed entities by copying it to a larger/shorter list.
     */
    private void resizePlacedEntities() {
	if(placedEntities == null) {
	    placedEntities = createEmptyCoordinateMap();
	    return;
	}
	
	// create a new entity list
	List<List<List <RuneEntity> > > oldMap = placedEntities;
	placedEntities = createEmptyCoordinateMap();
	
	// copy from old to new
	for(int x=0; (x<oldMap.size() && x<width); ++x) {
	    for(int y=0; (y<oldMap.get(x).size() && y<height); ++y) {
		placedEntities.get(x).set(y, oldMap.get(x).get(y));
	    }
	}
	
	// TODO some entities are now lost
    }
    
    private List<List<List<RuneEntity> > > createEmptyCoordinateMap() {
	ArrayList<List<List<RuneEntity> > > coordinateMap = new ArrayList<>(width);
	for(int i=0; i<width; ++i) {
	    List<List<RuneEntity > > l = new ArrayList<List<RuneEntity> >(height);
	    coordinateMap.add(l);
	    for(int j=0; j<height; ++j) {
		l.add(new ArrayList<RuneEntity>());
	    }
	}
	return coordinateMap;
    }

    /**
     * Provides the name of the map. It is used for identification of the map
     * during runtime.
     * @return
     */
    public String getName() {
	return name;
    }

    /**
     * change the name of the map
     * @param name
     */
    void setName(String name) {
	this.name = name;
    }
}
