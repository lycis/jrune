package org.jrune.entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.jrune.core.Engine;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

/**
 * This class serves as loader (factory) for entities. It reads the entity
 * file and creates an entity based on it.
 * 
 * @author lycis
 *
 */
public class EntityLoader {
	
	private Engine engine = null;
	
	public EntityLoader(Engine e) {
		this.engine = e;
	}
	
	/**
	 * Loads an entity into the blueprint register of the engine.
	 */
	public Entity load(String entityName) {
		Logger.getLogger(Engine.LOGGER_SUBSYSTEM).fine("Loading entity = "+entityName);
		File entityFile = new File(engine.basePath() + File.separator + "entity" 
	                               + File.separator + entityName.replace('.', File.separatorChar) + ".yml");
		
		Logger.getLogger(Engine.LOGGER_SUBSYSTEM).fine("file = "+entityFile.getAbsolutePath());
		
		if(!entityFile.exists()) {
			Logger.getLogger(Engine.LOGGER_SUBSYSTEM).severe("EntityDefinitionNotFound: "+entityName);
			throw new EntityDefinitionNotFoundException(entityName);
		}
		
		YamlReader reader = null;
		try{
			reader = new YamlReader(new FileReader(entityFile));
		} catch(FileNotFoundException e) {
			Logger.getLogger(Engine.LOGGER_SUBSYSTEM).severe("EntityDefinitionNotFound: "+entityName);
			throw new EntityDefinitionNotFoundException(entityName);
		}
		
		Entity e = null;
		try {
			Map<String, String> properties = (Map<String, String>) reader.read();
			
			// inerhit from base entity if given
			if(properties.containsKey(Entity.PROP_BASE)) {
				Logger.getLogger(Engine.LOGGER_SUBSYSTEM).finer("loading base entity = "+properties.get(Entity.PROP_BASE));
				e = new Entity(load(properties.get(Entity.PROP_BASE)));
			} else {
				Logger.getLogger(Engine.LOGGER_SUBSYSTEM).finer("no base entity");
				e = new Entity();
			}
			
			// set all properties and possible overwrite base entity
			for(String prop: properties.keySet()) {
				Logger.getLogger(Engine.LOGGER_SUBSYSTEM).finest("loaded property: "+prop+" = "+properties.get(prop));
				e.setProperty(prop, properties.get(prop));
			}
			
			// set relevant system properties
			e.setProperty(Entity.PROP_ENTITY, entityName);
			
		} catch (ClassCastException ex) {
			Logger.getLogger(Engine.LOGGER_SUBSYSTEM).severe("InvalidEntity: invalid file format (cause: "+ex.getMessage()+")");
			throw new InvalidEntityException("invalid file format", ex);
		} catch (YamlException ex) {
			Logger.getLogger(Engine.LOGGER_SUBSYSTEM).severe("InvalidEntity: error in entity description (cause: "+ex.getMessage()+")");
			throw new InvalidEntityException("error in entity description", ex);
		}
		
		engine.registerEntityBlueprint(e);
		return e;
	}
	
	
	/**
	 * Loads all entities that begin with the given string from their descriptions.
	 * @param beginsWith
	 * @return
	 */
	public void loadAll(String beginsWith) {
		String rootFileName = beginsWith.replace('.', File.separatorChar);
		File rootFolder = new File(engine.basePath() + File.separator + "entity" + File.separator + rootFileName);
		
		if(!rootFolder.exists()) {
			throw new EntityDefinitionNotFoundException(beginsWith);
		}
		
		// find all entities
		List<String> entitiesToLoad = searchForLoadableEntities(rootFolder, 0);
		
		// load them
		for(String entityName: entitiesToLoad) {
			load(entityName);
		}
	}

	private List<String> searchForLoadableEntities(File rootFolder, int level) {
		File[] content = rootFolder.listFiles();
		List<String> entities = new ArrayList<>();
		
		for(File f: content) {
			if(f.isDirectory() && ("..".equals(f.getName()) || ".".equals(f.getName()))) {
				continue; // this and parent folder
			}
			
			if(f.isDirectory()) {
				for(String s: searchForLoadableEntities(f, ++level)) {
					entities.add((level != 0?f.getName()+"."+s:s));
				}
			} else {
				if(!f.getName().endsWith(".yml")) {
					continue; // we only care about yml files
				}
				
				String entityName = f.getName().substring(0, f.getName().length()-4).
						replace(File.separatorChar, '.');
				entities.add(entityName);
			}
		}
		
		return entities;
	}
}
