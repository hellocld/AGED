/**
 * 
 */
package com.hellocld.tests.CollisionGroupTest;

import com.hellocld.AGED.basicComponents.CollideSet;
import com.hellocld.AGED.basicComponents.Collision2D;
import com.hellocld.AGED.basicComponents.EntityGroup;
import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.Render;
import com.hellocld.AGED.basicComponents.SimpleMove;
import com.hellocld.AGED.basicComponents.Size2D;
import com.hellocld.AGED.basicComponents.Velocity2D;
import com.hellocld.AGED.basicSystems.Collision2DSystem;
import com.hellocld.AGED.basicSystems.Render2DSystem;
import com.hellocld.AGED.basicSystems.SimpleMoveSystem;
import com.hellocld.AGED.core.GameState;

/**
 * The state used in CollisionGroupTest
 * @author CLD
 *
 */
public class State1 extends GameState {
	//the entities
	int utility;
	int[] entity = new int[4];
	
	public void create() {
		super.create();
		//create the utility entity
		utility = em.createEntity();
		//add the EntityGroup and CollideSet components to it
		em.addComponent(utility, new EntityGroup());
		em.addComponent(utility, new CollideSet());
		
		//create all the entities in the test
		for(int i = 0; i < 4; i++) {
			entity[i] = em.createEntity();
			em.addComponent(entity[i], new Position2D());
			em.addComponent(entity[i], new Size2D());
			em.addComponent(entity[i], new Collision2D());
			em.addComponent(entity[i], new Velocity2D());
			em.addComponent(entity[i], new SimpleMove());
			em.addComponent(entity[i], new Render());
		}
		//set values for the entities
		em.getComponent(entity[0], Position2D.class).setPosition(0, 10);
		em.getComponent(entity[0], Velocity2D.class).xVel = 5;
		em.getComponent(entity[0], Size2D.class).setSize(10, 10);
		em.getComponent(entity[0], Render.class).setColor(1, 0, 0, 1);
		
		em.getComponent(entity[1], Position2D.class).setPosition(310, 10);
		em.getComponent(entity[1], Velocity2D.class).xVel = -5;
		em.getComponent(entity[1], Size2D.class).setSize(10, 10);
		em.getComponent(entity[1], Render.class).setColor(0, 0, 1, 1);
		
		em.getComponent(entity[2], Position2D.class).setPosition(0, 100);
		em.getComponent(entity[2], Velocity2D.class).xVel = 5;
		em.getComponent(entity[2], Size2D.class).setSize(10, 10);
		em.getComponent(entity[2], Render.class).setColor(1, 1, 0, 1);
		
		em.getComponent(entity[3], Position2D.class).setPosition(310, 100);
		em.getComponent(entity[3], Velocity2D.class).xVel = -5;
		em.getComponent(entity[3], Size2D.class).setSize(10, 10);
		em.getComponent(entity[3], Render.class).setColor(0, 1, 1, 1);
		
		//create three groups
		em.getComponent(utility, EntityGroup.class).createGroup("collideA");
		em.getComponent(utility, EntityGroup.class).createGroup("collideB");
		em.getComponent(utility, EntityGroup.class).createGroup("noCollide");
		
		//add the entities to the groups
		em.getComponent(utility, EntityGroup.class).addToGroup("collideA", entity[0]);
		em.getComponent(utility, EntityGroup.class).addToGroup("collideA", entity[2]);
		em.getComponent(utility, EntityGroup.class).addToGroup("collideB", entity[1]);
		em.getComponent(utility, EntityGroup.class).addToGroup("noCollide", entity[3]);
		
		//add the groups to the CollideSet
		//group "noCollide" isn't added, so any entity in "noCollide" will not be checked for a collision
		em.getComponent(utility, CollideSet.class).addCheck("collideA", "collideB");
		
		//add the systems
		systems.add(new Collision2DSystem(utility));
		systems.add(new SimpleMoveSystem());
		systems.add(new Render2DSystem());
	}
}
