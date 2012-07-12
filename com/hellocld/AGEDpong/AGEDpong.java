/**
 * 
 */
package com.hellocld.AGEDpong;

import com.hellocld.AGED.core.Game;

/**
 * AGEDpong: a simple example of what AGED can do
 * @author CLD
 *
 */
public class AGEDpong extends Game {
	public static Game agedpong;
	
	public static void main(String[] args) {
		agedpong = new Game();
		agedpong.createGame(320, 240, 60, new SinglePlayerPong());
	}
}
