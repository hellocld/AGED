/**
 * 
 */
package com.hellocld.tests.GameTest;

import com.hellocld.AGED.core.*;

/**
 * Testing the Game and GameState classes
 * @author CLD
 *
 */
public class TestGame {
	public static Game testGame;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testGame = new Game();
		
		testGame.createGame(320, 240, 60, new TestGameState());

	}

}
