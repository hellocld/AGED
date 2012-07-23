/**
 * 
 */
package com.hellocld.tests.MouseTest;

import com.hellocld.AGED.core.Game;

/**
 * Quick test for the Mouse system and entity, to go in to the Common Entity Toolkit
 * @author CLD
 *
 */
public class MouseTest {
	public static Game mt;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		mt = new Game();
		mt.createGame(640, 480, 30, new mtState());

	}

}
