/**
 * 
 */
package com.hellocld.AGED.core;

import com.hellocld.AGED.util.*;

/**
 * A basic set of Game initiation functions. This is the starting point of every game using AGED
 * @author CLD
 *
 */
public class Game {
	
	//the currently running GameState
	public GameState gs;
	
	
	public void createGame(int width, int height, int fps, GameState firstState) {
		Window window = new Window(width, height, fps);
		window.create();
		this.gs = firstState;
		gs.create();
	}
	
	public void update() {
		
	}
	
	public void quit() {
		
	}
	
	public void changeState(GameState gs) {
		this.gs = gs;
		gs.create();
	}

}
