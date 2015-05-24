package org.jrune.entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.script.ScriptException;

import org.jrune.core.RuneEngine;
import org.jrune.core.RuneException;
import org.jrune.script.RuneScriptContext;
import org.jrune.script.RuneScriptException;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

/**
 * This class serves as loader (factory) for entities. It reads the entity file
 * and creates an entity based on it.
 * 
 * @author lycis
 *
 */
public class RuneEntityLoader {

    private RuneEngine engine = null;

    public RuneEntityLoader(RuneEngine e) {
	this.engine = e;
    }

    /**
     * Loads an entity into the blueprint register of the engine.
     * 
     * @throws ScriptException
     *             thrown when an error occurred during loading of the entity
     *             script
     */
    public RuneEntity load(String entityName) throws RuneScriptException {
	Logger.getLogger(RuneEngine.LOGGER_SUBSYSTEM).fine("Loading entity = " + entityName);
	File entityFile = new File(engine.basePath() + File.separator + "entity"
	    + File.separator + entityName.replace('.', File.separatorChar) + ".yml");

	Logger.getLogger(RuneEngine.LOGGER_SUBSYSTEM).fine("file = " + entityFile.getAbsolutePath());

	if (!entityFile.exists()) {
	    Logger.getLogger(RuneEngine.LOGGER_SUBSYSTEM).severe("EntityDefinitionNotFound: " + entityName);
	    throw new EntityDefinitionNotFoundException(entityName);
	}

	YamlReader reader = null;
	try {
	    reader = new YamlReader(new FileReader(entityFile));
	} catch (FileNotFoundException e) {
	    Logger.getLogger(RuneEngine.LOGGER_SUBSYSTEM).severe("EntityDefinitionNotFound: " + entityName);
	    throw new EntityDefinitionNotFoundException(entityName);
	}

	RuneEntity e = null;
	try {
	    @SuppressWarnings("unchecked")
	    Map<String, String> properties = (Map<String, String>) reader.read();

	    // inerhit from base entity if given
	    if (properties.containsKey(RuneEntity.PROP_BASE)) {
		Logger.getLogger(RuneEngine.LOGGER_SUBSYSTEM).finer(
		    "loading base entity = " + properties.get(RuneEntity.PROP_BASE));
		e = new RuneEntity(load(properties.get(RuneEntity.PROP_BASE)), engine);
	    } else {
		Logger.getLogger(RuneEngine.LOGGER_SUBSYSTEM).finer("no base entity");
		e = new RuneEntity(engine);
	    }

	    // set all properties and possibly overwrite base entity
	    for (String prop : properties.keySet()) {
		Logger.getLogger(RuneEngine.LOGGER_SUBSYSTEM).finest("loaded property: " + prop + " = " + properties.get(prop));
		e.setProperty(prop, properties.get(prop));
	    }

	    // set relevant system properties
	    e.setEngine(engine);
	    e.setProperty(RuneEntity.PROP_ENTITY, entityName);

	    // check if default script exists
	    if(!e.hasProperty(RuneEntity.PROP_SCRIPT)) {
		String scriptPath = getScriptForEntity(e);
		File f = new File(engine.basePath() + File.separator + "script" + File.separator + scriptPath);
		if(f.exists()) {
		    e.setProperty(RuneEntity.PROP_SCRIPT, scriptPath);
		}
	    }
	    
	    // load entity script
	    // TODO outsource to script compiler class and do here only if precompiled scripts option is enabled
	    if (e.hasProperty(RuneEntity.PROP_SCRIPT) ) {
		RuneScriptContext scontext = new RuneScriptContext(e);
		e.setScriptContext(scontext);
		// invoke blueprint init function
		e.call("_bp_init");
	    }

	} catch (ClassCastException ex) {
	    Logger.getLogger(RuneEngine.LOGGER_SUBSYSTEM).severe(
		"InvalidEntity: invalid file format (cause: " + ex.getMessage() + ")");
	    throw new InvalidEntityException("invalid file format", ex);
	} catch (YamlException ex) {
	    Logger.getLogger(RuneEngine.LOGGER_SUBSYSTEM).severe(
		"InvalidEntity: error in entity description (cause: " + ex.getMessage() + ")");
	    throw new InvalidEntityException("error in entity description", ex);
	}

	engine.registerEntityBlueprint(e);
	return e;
    }

    /**
     * Check if a script exists for the given entity based on its name.
     * 
     * @param e
     * @return
     */
    private String getScriptForEntity(RuneEntity e) {
	String entityPath = e.getProperty(RuneEntity.PROP_ENTITY).replace('.', File.separatorChar);
	return File.separator + "entity" + File.separator + entityPath + ".lua";
    }

    /**
     * Loads all entities that begin with the given string from their
     * descriptions.
     * 
     * @param beginsWith
     * @return
     */
    public void loadAll(String beginsWith) throws RuneException {
	String rootFileName = beginsWith.replace('.', File.separatorChar);
	File rootFolder = new File(engine.basePath() + File.separator + "entity" + File.separator + rootFileName);

	if (!rootFolder.exists()) {
	    throw new EntityDefinitionNotFoundException(beginsWith);
	}

	// find all entities
	List<String> entitiesToLoad = searchForLoadableEntities(rootFolder, 0);

	// load them
	for (String entityName : entitiesToLoad) {
	    load(entityName);
	}
    }

    private List<String> searchForLoadableEntities(File rootFolder, int level) {
	File[] content = rootFolder.listFiles();
	List<String> entities = new ArrayList<>();

	for (File f : content) {
	    if (f.isDirectory() && ("..".equals(f.getName()) || ".".equals(f.getName()))) {
		continue; // this and parent folder
	    }

	    if (f.isDirectory()) {
		for (String s : searchForLoadableEntities(f, ++level)) {
		    entities.add((level != 0 ? f.getName() + "." + s : s));
		}
	    } else {
		if (!f.getName().endsWith(".yml")) {
		    continue; // we only care about yml files
		}

		String entityName = f.getName().substring(0, f.getName().length() - 4).
		    replace(File.separatorChar, '.');
		entities.add(entityName);
	    }
	}

	return entities;
    }
}
