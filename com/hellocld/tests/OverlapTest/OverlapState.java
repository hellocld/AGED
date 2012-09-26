/**
 * 
 */
package com.hellocld.tests.OverlapTest;

import com.hellocld.AGED.basicComponents.PhysicsObject2D;
import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.Render;
import com.hellocld.AGED.basicComponents.Size2D;
import com.hellocld.AGED.core.GameState;

/**
 * @author CLD
 *
 */
public class OverlapState extends GameState {
	//a couple of entities;
	int player;
	int obstacle;
	
	public OverlapState() {
		// TODO Auto-generated constructor stub
	}
	
	public void create() {
		super.create();
		player = em.createEntity();
		obstacle = em.createEntity();
		
		em.addComponent(player, new PhysicsObject2D(0, 0, 16, 16, 0, 0));
		em.addComponent(player, new Position2D(0, 0));
		em.addComponent(player, new Size2D(16, 16));
		em.addComponent(player, new Render());
		
		em.addComponent(obstacle, new PhysicsObject2D(128, 128, 64, 64, 0, 0));
		em.addComponent(obstacle, new Position2D(128, 128));
		em.addComponent(obstacle, new Size2D(64, 64));
		em.addComponent(obstacle, new Render());
		
		
	}
	
	public void update() {
		super.update();
	}

}
