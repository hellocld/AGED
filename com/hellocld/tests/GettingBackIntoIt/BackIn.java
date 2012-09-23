/**
 * 
 */
package com.hellocld.tests.GettingBackIntoIt;

import com.hellocld.AGED.core.Game;

/**
 * @author cld
 *
 */
public class BackIn {

	public static Game game;
	
	public static void main(String[] args) {
		game = new Game();
		game.createGame(640, 480, 30, new StateOne());

	}

}
