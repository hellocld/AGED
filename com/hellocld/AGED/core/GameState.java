/**
 * 
 */
package com.hellocld.AGED.core;

import java.util.LinkedList;
import java.util.List;

/**
 * A basic game state. It handles an EntityManager, the creation of objects, and a basic game loop
 * 
 * @author CLD
 *
 */
public class GameState {
	//the EntityManager that will handle all the Entities in the GameState
	public EntityManager em;
	
	//the list of Systems executed during the update loop
	public List<ASystem> systems;
	
	//the game; used to switch states
	public Game game;
	
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
