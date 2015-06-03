package org.jrune.map;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.jrune.core.RuneEngine;
import org.jrune.core.RuneException;
import org.jrune.entity.RuneEntity;
import org.jrune.entity.UnknownEntityException;
import org.jrune.script.RuneScriptException;
import org.junit.Before;
import org.junit.Test;

public class RuneMapTest {
    private RuneEngine engine = null;
    
    @Before
    public void before() throws URISyntaxException, RuneException {
	URL url = this.getClass().getResource("/engineData");
	File baseDir = new File(url.toURI());
	engine = new RuneEngine(baseDir.getAbsolutePath());
	engine.start();
    }

    /**
     * Check basic measurements (width & heights)
     */
    @Test
    public void testWidthAndHeight() {
	RuneMap map = new RuneMap(engine);
	map.setHeight(10);
	map.setWidth(20);
	assertEquals("map height is wrong", map.getHeight(), 10);
	assertEquals("map width is wrong", map.getWidth(), 20);
    }

    @Test
    public void testPlaceEntity() throws UnknownEntityException, RuneScriptException {
	RuneEntity e = engine.cloneEntity("npc.goblins.goblin");
	RuneMap map = new RuneMap(engine, 20, 20);
	map.setName("testmap");
	assertTrue("could not place entity", 
	           map.setEntityPosition(e.getProperty(RuneEntity.PROP_UID), 10, 10));
	assertTrue("entity not on position", map.getEntitiesAt(10, 10).contains(e));
	assertEquals("location property not set", "testmap:10:10", e.getProperty(RuneEntity.PROP_LOCATION));
    }
    
    @Test
    public void testPlaceEntityOnOccupiedSquare() throws UnknownEntityException, RuneScriptException {
	RuneEntity e = engine.cloneEntity("npc.goblins.goblin");
	RuneEntity e2 = engine.cloneEntity("npc.goblins.goblin");
	RuneMap map = new RuneMap(engine, 20, 20);
	map.setName("testmap");
	assertTrue("could not place entity", 
	           map.setEntityPosition(e.getProperty(RuneEntity.PROP_UID), 10, 10));
	assertFalse("entity could be placed on occupied square",
	            map.setEntityPosition(e2.getProperty(RuneEntity.PROP_UID), 10, 10));
    }
    
    @Test
    public void placeSameEntityTwiceOnSameSquare() throws UnknownEntityException, RuneScriptException {
	RuneEntity e = engine.cloneEntity("items.arrow");
	RuneMap map = new RuneMap(engine, 20, 20);
	map.setName("testmap");
	
	assertTrue("could not place entity the first time", 
	           map.setEntityPosition(e.getProperty(RuneEntity.PROP_UID), 10, 10));
	assertTrue("entity not on target position", map.getEntitiesAt(10, 10).contains(e));
	assertTrue("could not place entity the second time", 
	           map.setEntityPosition(e.getProperty(RuneEntity.PROP_UID), 10, 10));
	int countEntityOccurance = 0;
	for(RuneEntity x: map.getEntitiesAt(10, 10)) {
	    if(e.equals(x)) {
		countEntityOccurance++;
	    }
	}
	
	assertEquals("entity listed multiple times at the same position", 1, countEntityOccurance);
    }
    
    @Test
    public void moveEntityToAnotherSquare() throws UnknownEntityException, RuneScriptException {
	RuneEntity e = engine.cloneEntity("npc.goblins.goblin");
	RuneMap map = new RuneMap(engine, 20, 20);
	map.setName("testmap");
	
	assertTrue("could not place entity the first time", 
	           map.setEntityPosition(e.getProperty(RuneEntity.PROP_UID), 10, 10));
	assertTrue("could not move entity to another square",
	    	   map.setEntityPosition(e.getProperty(RuneEntity.PROP_UID), 11, 11));
	
	assertFalse("entity was not removed from original position", map.getEntitiesAt(10, 10).contains(e));
	assertTrue("entity not on target position", map.getEntitiesAt(11, 11).contains(e));
    }
    
    // TODO test for moving entity between different maps
}
