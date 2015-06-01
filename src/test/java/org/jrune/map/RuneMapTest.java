package org.jrune.map;

import static org.junit.Assert.*;

import org.junit.Test;

public class RuneMapTest {

    /**
     * Check basic measurements (width & heights)
     */
    @Test
    public void testWidthAndHeight() {
	RuneMap map = new RuneMap();
	map.setHeight(10);
	map.setWidth(20);
	assertEquals("map height is wrong", map.getHeight(), 10);
	assertEquals("map width is wrong", map.getWidth(), 20);
    }

}
