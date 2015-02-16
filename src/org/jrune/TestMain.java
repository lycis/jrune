package org.jrune;

import java.io.File;

import org.jrune.core.RuneEngine;
import org.jrune.entity.RuneEntity;
import org.jrune.entity.UnknownEntityException;

public class TestMain {
	
	public static void main(String... args) {
	/*	Logger log = Logger.getLogger(Engine.LOGGER_SUBSYSTEM);
		
		if(log.getHandlers().length > 0) {
			for(Handler h: log.getHandlers()) {
				h.setLevel(Level.FINEST);
			}
		} else {
			ConsoleHandler handler = new ConsoleHandler();
			handler.setLevel(Level.FINEST);
			log.addHandler(handler);
		}
		*/
		
		RuneEngine engine = new RuneEngine("."+File.separator+"test");
		engine.start();
		try {
			RuneEntity goblin = engine.cloneEntity("npc.goblins.goblin");
		} catch (UnknownEntityException e) {
			System.out.println("Entity not cloned: ");
			e.printStackTrace();
		}
	}
}
