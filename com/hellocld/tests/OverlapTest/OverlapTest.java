/**
 * 
 */
package com.hellocld.tests.OverlapTest;

import com.hellocld.AGED.core.Game;

/**
 * Time to start testing out that Physics stuff
 * @author CLD
 *
 */
public class OverlapTest {
	public static Game overlapTest = new Game();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		overlapTest.createGame(640, 480, 60, new OverlapState());
	}

}
