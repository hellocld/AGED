/**
 * 
 */
package com.hellocld.tests.CollisionGroupTest;

import com.hellocld.AGED.core.Game;

/**
 * A test of the new EntityGroup and CollideSet components
 * @author CLD
 *
 */
public class CollisionGroupTest {
	public static Game cgt;
	
	public static void main(String[] args) {
		cgt = new Game();
		cgt.createGame(320, 240, 30, new State1());

	}
}
