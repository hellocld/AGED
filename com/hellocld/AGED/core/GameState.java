/**
 * 
 */
package com.hellocld.AGED.core;

import java.util.List;
import java.util.LinkedList;

/**
 * A basic game state. It handles an EntityManager, the creation of objects, and a basic game loop
 * @author CLD
 *
 */
public class GameState {
	//the EntityManager that will handle all the Entities in the GameState
	public EntityManager em;
	
	//the list of Systems executed during the update loop
	public List<ASystem> systems;
	
	public GameState() {
		
	}
	
	public void create() {
		//make the EntityManager and List of systems for adding stuff
		em = new EntityManager();
		systems = new LinkedList<ASystem>();
	}
	
	public void update() {
		//execute the systems
		for (int i = 0; i<systems.size(); i++)
			systems.get(i).execute(em);
	}
}
