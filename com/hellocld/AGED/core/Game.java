/**
 * 
 */
package com.hellocld.AGED.core;

import com.hellocld.AGED.util.*;

/**
 * A basic set of Game initiation functions. This is the starting point of every game using AGED
 * 
 * @author CLD
 *
 */
public class Game {
	
	//the currently running GameState
	public GameState gs;
	
	/**
	 * This will create the game and start the game loop. The game will run until
	 * the "Close Window" button is selected
	 * @param width			The width of the window
	 * @param height		The height of the window
	 * @param fps			The maximum framerate of the window
	 * @param firstState	The initial game state
	 */
	public void createGame(int width, int height, int fps, GameState firstState) {
		Window window = new Window(width, height, fps);
		window.create();
		this.gs = firstState;
		gs.create();
		
		while(!window.isCloseRequested()){
			window.update();
			gs.update();
		}
		
		window.destroy();
	}
	
	/**
	 * Change to a new game state
	 * @param gs	the new GameState class
	 */
	public void changeState(GameState gs) {
		this.gs = gs;
		gs.create();
	}

}
