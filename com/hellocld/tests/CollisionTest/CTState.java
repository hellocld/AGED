/**
 * 
 */
package com.hellocld.tests.CollisionTest;

import com.hellocld.AGED.core.GameState;
import com.hellocld.AGED.basicComponents.*;
import com.hellocld.AGED.basicSystems.*;

/**
 * @author CLD
 *
 */
public class CTState extends GameState {
	public int entity1;
	public int entity2;
	
	public void create() {
		super.create();
		
		entity1 = em.createEntity();
		entity2 = em.createEntity();
		
		em.addComponent(entity1, new Position2D());
		em.addComponent(entity1, new Size2D());
		em.addComponent(entity1, new Velocity2D());
		em.addComponent(entity1, new Collision2D());
		em.addComponent(entity1, new SimpleMove());
		em.addComponent(entity1, new Render());
		em.addComponent(entity2, new Position2D());
		em.addComponent(entity2, new Size2D());
		em.addComponent(entity2, new Velocity2D());
		em.addComponent(entity2, new Collision2D());
		em.addComponent(entity2, new SimpleMove());
		em.addComponent(entity2, new Render());
		
		em.getComponent(entity1, Position2D.class).x = 0;
		em.getComponent(entity1, Position2D.class).y = 21;
		em.getComponent(entity1, Size2D.class).width = 20;
		em.getComponent(entity1, Size2D.class).height = 20;
		em.getComponent(entity1, Velocity2D.class).xVel = 5;
		em.getComponent(entity2, Position2D.class).x = 300;
		em.getComponent(entity2, Size2D.class).width = 20;
		em.getComponent(entity2, Size2D.class).height = 20;
		em.getComponent(entity2, Velocity2D.class).xVel = -5;
		
		systems.add(new Render2DSystem());
		systems.add(new SimpleMoveSystem());
		systems.add(new Collision2DSystem());
		
	}
	
	public void update() {
		super.update();
		
		if(em.getComponent(entity1, Position2D.class).x > 300) {
			em.getComponent(entity1, Position2D.class).x = 0;
		}
		
		if(em.getComponent(entity2, Position2D.class).x < 0) {
			em.getComponent(entity2, Position2D.class).x = 300;
		}
		
		
	}
}
