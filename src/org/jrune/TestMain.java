package org.jrune;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jrune.core.RuneEngine;
import org.jrune.entity.RuneEntity;
import org.jrune.entity.UnknownEntityException;

public class TestMain {
	
	public static void main(String... args) {	
		RuneEngine engine = new RuneEngine("."+File.separator+"test");
		engine.start();
		try {
			RuneEntity goblin = engine.cloneEntity("npc.goblins.goblin");
			System.out.println("entity "+goblin.getProperty(RuneEntity.PROP_ENTITY)+" loaded.");
		} catch (UnknownEntityException e) {
			System.out.println("Entity not cloned: ");
			e.printStackTrace();
		}
		
		// jsonify
		String json = engine.getState().toJSON();
		
		// load new game state from json
		engine.getState().fromJSON(json);
		
		// compare
		if(json.equals(engine.getState().toJSON())) {
		    System.out.println("json state matches");
		} else {
		    System.out.println("json state does not match");
		    System.out.println("orig: "+json);
		    System.out.println("now: "+engine.getState().toJSON());
		}
	}
}
