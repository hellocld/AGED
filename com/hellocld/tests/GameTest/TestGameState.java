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
		
		//add a couple test entities with a bunch of components
		int testEntity1 = em.createEntity();
		em.addComponent(testEntity1, new Position2D());
		em.addComponent(testEntity1, new Size2D());
		em.addComponent(testEntity1, new Render());
		em.getComponent(testEntity1, Position2D.class).x = 10;
		em.getComponent(testEntity1, Position2D.class).y = 10;
		em.getComponent(testEntity1, Size2D.class).width = 20;
		em.getComponent(testEntity1, Size2D.class).height = 20;
		
		int testEntity2 = em.createEntity();
		em.addComponent(testEntity2, new Position2D());
		em.addComponent(testEntity2, new Size2D());
		em.addComponent(testEntity2, new Render());
		em.addComponent(testEntity2, new SimpleMove());
		em.addComponent(testEntity2, new Velocity2D());
		em.getComponent(testEntity2, Position2D.class).x = 60;
		em.getComponent(testEntity2, Position2D.class).y = 83;
		em.getComponent(testEntity2, Size2D.class).width = 90;
		em.getComponent(testEntity2, Size2D.class).height = 17;
		em.getComponent(testEntity2, Velocity2D.class).xVel = 1;
		em.getComponent(testEntity2, Velocity2D.class).yVel = -1;
		
		//add any Systems to the systems list
		//Be sure to add them in the order you want them to execute
		systems.add(new Render2DSystem());
		systems.add(new SimpleMoveSystem());
	}
	
	public void update() {
		super.update();
		
	}
	
}
