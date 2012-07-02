/**
 * 
 */
package com.hellocld.tests.GameTest;

import com.hellocld.AGED.core.GameState;
import com.hellocld.AGED.basicSystems.*;

/**
 * @author atto
 *
 */
public class TestGameState2 extends GameState {
	
	public void create() {
		super.create();
		System.out.println("State Changed!");
		systems.add(new Render2DSystem());
	}
	
	public void update() {
		super.update();
		
	}
}
