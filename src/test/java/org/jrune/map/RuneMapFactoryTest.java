package org.jrune.map;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.jrune.core.RuneEngine;
import org.jrune.core.RuneException;
import org.junit.Before;
import org.junit.Test;

public class RuneMapFactoryTest {
    
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
     * Loading a very primitive map.
     */
    @Test
    public void test() {
	RuneMapFactory factory = new RuneMapFactory(engine);
	RuneMap map = factory.load("maps.simple_map");
	assertTrue("map does not have right width", map.getWidth() == 50);
	assertTrue("map does not have right height", map.getHeight() == 20);
	assertTrue("wrong name", "simple map".equals(map.getName()));
    }

}
