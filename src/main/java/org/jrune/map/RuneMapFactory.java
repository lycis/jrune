package org.jrune.map;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Logger;

import org.jrune.core.RuneEngine;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

/**
 * This factory is used to load maps from their definition files. Basically
 * it reads the map definition and creates a map from it.
 * 
 * @author lycis
 *
 */
public class RuneMapFactory {
    private RuneEngine engine = null;
    
    public RuneMapFactory(RuneEngine engine) {
	this.engine = engine;
    }
    
    /**
     * Loads a map from a file.
     * 
     * @param name
     * @return
     */
    public RuneMap load(String name) {
	Logger.getLogger(RuneEngine.LOGGER_SUBSYSTEM).fine("Loading map = " + name);
	File defFile = engine.getGameFile(name, "yml");
	if(defFile == null) {
	    Logger.getLogger(RuneEngine.LOGGER_SUBSYSTEM).severe("MapNotFoundException: " + name);
	    throw new MapNotFoundException(name);
	}
	
	YamlReader reader = null;
	try {
	    reader = new YamlReader(new FileReader(defFile));
	} catch (FileNotFoundException e) {
	    Logger.getLogger(RuneEngine.LOGGER_SUBSYSTEM).severe("MapNotFoundException: " + name);
	    throw new MapNotFoundException(name);
	}
	
	RuneMap map = null;
	try {
	    map = reader.read(RuneMap.class);
	} catch (YamlException e) {
	    Logger.getLogger(RuneEngine.LOGGER_SUBSYSTEM).severe("loading map "+name+" failed: " + e.getMessage());
	    e.printStackTrace();
	   throw new MapLoadException(name, e);
	}

	return map;
    }
}
