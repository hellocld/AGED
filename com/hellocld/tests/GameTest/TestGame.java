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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final Game testGame = new Game();
		
		testGame.createGame(320, 240, 60, new TestGameState());

	}

}
