/**
 * 
 */
package com.hellocld.tests.GameTest;

import com.hellocld.AGED.core.GameState;
import com.hellocld.AGED.basicComponents.*;
import com.hellocld.AGED.basicSystems.*;

/**
 * A test GameState
 * @author CLD
 *
 */
public class TestGameState extends GameState {
	public void create() {
		super.create();
		
		//add a test entity with a bunch of components
		int testEntity = em.createEntity();
		em.addComponent(testEntity, new Position2D());
		em.addComponent(testEntity, new Size2D());
		em.addComponent(testEntity, new Render());
		em.getComponent(testEntity, Position2D.class).x = 10;
		em.getComponent(testEntity, Position2D.class).y = 10;
		em.getComponent(testEntity, Size2D.class).width = 2;
		em.getComponent(testEntity, Size2D.class).height = 2;
		
		//add any Systems to the systems list
		//Be sure to add them in the order you want them to execute
		systems.add(new Render2DSystem());
	}
	
	public void update() {
		super.update();
		
	}
	
}
