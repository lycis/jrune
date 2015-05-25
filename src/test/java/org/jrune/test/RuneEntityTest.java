package org.jrune.test;

import static org.junit.Assert.*;

import org.jrune.core.RuneEngine;
import org.jrune.entity.RuneEntity;
import org.junit.Before;
import org.junit.Test;

public class RuneEntityTest {
    private RuneEngine engine = null;
    
    @Before
    public void before() {
	engine = new RuneEngine();
    }

    @Test
    public void testSetPropertyString() {
	RuneEntity entity = new RuneEntity(engine);
	entity.setProperty("string", "test-value");
	assertEquals("test-value", entity.getProperty("string"));
    }

}
