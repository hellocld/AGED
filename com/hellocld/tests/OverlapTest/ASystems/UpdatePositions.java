/**
 * 
 */
package com.hellocld.tests.OverlapTest.ASystems;

import java.util.Iterator;
import java.util.Set;

import com.hellocld.AGED.basicComponents.PhysicsObject2D;
import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.Size2D;
import com.hellocld.AGED.core.ASystem;
import com.hellocld.AGED.core.EntityManager;
import com.hellocld.AGED.core.Game;

/**
 * @author chris
 *
 */
public class UpdatePositions implements ASystem {

	Set<Integer> updateSet;
	Iterator<Integer> updateIter;
	int entity;
	
	public UpdatePositions() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.hellocld.AGED.core.ASystem#execute(com.hellocld.AGED.core.Game, com.hellocld.AGED.core.EntityManager)
	 */
	@Override
	public void execute(Game game, EntityManager em) {
		updateSet = em.getAllEntitiesPossessingComponent(PhysicsObject2D.class);
		for(updateIter = updateSet.iterator(); updateIter.hasNext();) {
			entity = updateIter.next();
			if(em.hasComponent(entity, Position2D.class)) {
				em.getComponent(entity, Position2D.class).setPosition(em.getComponent(entity, PhysicsObject2D.class).getX(), em.getComponent(entity, PhysicsObject2D.class).getY());
			}
			if(em.hasComponent(entity, Size2D.class)) {
				em.getComponent(entity, Size2D.class).setSize(em.getComponent(entity, PhysicsObject2D.class).getWidth(), em.getComponent(entity, PhysicsObject2D.class).getHeight());
			}
		}

	}

}
