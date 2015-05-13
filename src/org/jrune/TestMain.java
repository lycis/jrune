package org.jrune;

import java.io.File;

import org.jrune.core.RuneEngine;
import org.jrune.core.RuneException;
import org.jrune.entity.RuneEntity;
import org.jrune.entity.UnknownEntityException;
import org.jrune.script.RuneScriptException;

public class TestMain {
	
	public static void main(String... args) {	
		RuneEngine engine = new RuneEngine("."+File.separator+"test");
		try {
		    engine.start();
		} catch (RuneException e1) {
		    
		    e1.printStackTrace();
		}
		try {
			RuneEntity goblin = engine.cloneEntity("npc.goblins.goblin");
			System.out.println("entity "+goblin.getProperty(RuneEntity.PROP_ENTITY)+" loaded.");
			goblin.call("foo");
		} catch (UnknownEntityException | RuneScriptException e) {
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
