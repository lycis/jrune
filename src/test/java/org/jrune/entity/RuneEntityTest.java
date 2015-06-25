package org.jrune.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

import org.jrune.core.RuneEngine;
import org.jrune.core.RuneException;
import org.jrune.script.RuneScriptException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class RuneEntityTest {
    private RuneEngine engine = null;
    
    /**
     * Set up valid engine to work with during testing
     * @throws URISyntaxException
     * @throws RuneException
     */
    @Before
    public void before() throws URISyntaxException, RuneException {
	URL url = this.getClass().getResource("/engineData");
	File baseDir = new File(url.toURI());
	engine = new RuneEngine(baseDir.getAbsolutePath());
	engine.start();
    }

    /**
     * Setting string properties.
     */
    @Test
    public void testSetPropertyString() {
	RuneEntity entity = new RuneEntity(engine);
	entity.setProperty("string", "test-value");
	assertEquals("test-value", entity.getProperty("string"));
    }
    
    /**
     * Setting integer properties.
     */
    @Test
    public void testSetPropertyInteger() {
	RuneEntity entity = new RuneEntity(engine);
	entity.setProperty("int", (int) 1);
	assertEquals(new Integer(1), entity.getProperty(Integer.class, "int"));
    }
    
    /**
     * Setting short properties.
     */
    @Test
    public void testSetPropertyShort() {
	RuneEntity entity = new RuneEntity(engine);
	entity.setProperty("short", (short) 9);
	assertEquals(new Short((short) 9), entity.getProperty(Short.class, "short"));
    }
    
    /**
     * Setting long properties.
     */
    @Test
    public void testSetPropertyLong() {
	RuneEntity entity = new RuneEntity(engine);
	entity.setProperty("long", 9L);
	assertEquals(new Long(9L), entity.getProperty(Long.class, "long"));
    }
    
    /**
     * Setting float properties.
     */
    @Test
    public void testSetPropertyFloat() {
	RuneEntity entity = new RuneEntity(engine);
	entity.setProperty("float", 9.77f);
	assertEquals(new Float(9.77f), entity.getProperty(Float.class, "float"));
    }
    
    /**
     * Setting double properties.
     */
    @Test
    public void testSetPropertyDouble() {
	RuneEntity entity = new RuneEntity(engine);
	entity.setProperty("double", 9.77);
	assertEquals(new Double(9.77), entity.getProperty(Double.class, "double"));
    }
    
    /**
     * Setting boolean properties.
     */
    @Test
    public void testSetPropertyBooleanTrue() {
	RuneEntity entity = new RuneEntity(engine);
	entity.setProperty("bool", true);
	assertTrue(entity.getProperty(Boolean.class, "bool"));
    }
    
    /**
     * Setting boolean properties.
     */
    @Test
    public void testSetPropertyBooleanFalse() {
	RuneEntity entity = new RuneEntity(engine);
	entity.setProperty("bool", false);
	assertFalse(entity.getProperty(Boolean.class, "bool"));
    }
    
    /**
     * hasProperty(...)
     */
    @Test
    public void testHasProperty() {
	RuneEntity entity = new RuneEntity(engine);
	entity.setProperty("foo", "1");
	
	assertTrue("property foo not registered although set", entity.hasProperty("foo"));
	assertFalse("non-set property identifies as registered", entity.hasProperty("not-set"));
    }
    
    /**
     * Check if simple inheritance is working.
     * @throws RuneScriptException
     * @throws org.jrune.entity.UnknownEntityException
     */
    @Test @Ignore
    public void testInheritance() throws RuneScriptException, org.jrune.entity.UnknownEntityException {
	// TODO implement inheritance
	RuneEntity derived = engine.cloneEntity("npc.goblins.goblin");
	assertTrue("entity not based on parent", derived.isBasedOn("npc.monster"));
	assertEquals("inherited value was not correctly overwritten", "10", derived.getProperty("hp"));
	assertEquals("inherited value not set", "parent", derived.getProperty("inheritedValue"));
	assertEquals("property of init not set", 1, derived.getProperty("test"));
    }
    
    @Test
    public void testSimpleEntityClone() throws UnknownEntityException, RuneScriptException {
	RuneEntity e = engine.cloneEntity("npc.monster");
	assertTrue("entity not loaded", e != null);
	
	// check properties
	assertEquals("property hp not set correctly", new Integer(100), e.getProperty(Integer.class, "hp"));
	assertFalse("property $passable not set correctly", e.getProperty(Boolean.class, RuneEntity.PROP_PASSABLE));
    }
    
    /**
     * getProperties() -> list all props
     */
    @Test
    public void testGetProperties() {
	RuneEntity entity = new RuneEntity(engine);
	entity.setProperty("a", "1");
	entity.setProperty("b", "2");
	
	Set<String> props = entity.getProperties();
	assertEquals("not corrent number of properties", 2, props.size());
	assertTrue("property 'a' not listed", props.contains("a"));
	assertTrue("property 'b' not listed", props.contains("b"));
    }

}
