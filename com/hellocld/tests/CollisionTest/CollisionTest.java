/**
 * 
 */
package com.hellocld.tests.CollisionTest;

import com.hellocld.AGED.core.Game;

/**
 * @author CLD
 *
 */
public class CollisionTest {
	public static Game collisionTest;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		collisionTest = new Game();
		collisionTest.createGame(320, 240, 30, new CTState());
	}

}
