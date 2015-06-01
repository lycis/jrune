package org.jrune.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

import org.jrune.core.RuneEngine;
import org.jrune.core.RuneException;
import org.jrune.entity.RuneEntity;
import org.jrune.script.RuneScriptException;
import org.junit.Before;
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
	entity.setProperty("int", 1);
	assertEquals("1", entity.getProperty("int"));
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
     * Check if simple inheritance (no-script) is working.
     * @throws RuneScriptException
     * @throws org.jrune.entity.UnknownEntityException
     */
    @Test
    public void testNoScriptInheritance() throws RuneScriptException, org.jrune.entity.UnknownEntityException {
	RuneEntity derived = engine.cloneEntity("npc.goblins.goblin");
	assertTrue("entity not based on parent", derived.isBasedOn("npc.monster"));
	assertEquals("inherited value was not correctly overwritten", "10", derived.getProperty("hp"));
	assertEquals("inherited value not set", "parent", derived.getProperty("inheritedValue"));
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
