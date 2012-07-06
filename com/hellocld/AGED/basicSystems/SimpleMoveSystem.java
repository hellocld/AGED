/**
 * 
 */
package com.hellocld.AGED.basicSystems;

import java.util.Iterator;
import java.util.Set;

import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.SimpleMove;
import com.hellocld.AGED.basicComponents.Velocity2D;
import com.hellocld.AGED.core.ASystem;
import com.hellocld.AGED.core.EntityManager;

/**
 * @author chris
 *
 */
public class SimpleMoveSystem implements ASystem {
	Set<Integer> moveSet;
	Iterator<Integer> moveIter;
	int entity;
	/* (non-Javadoc)
	 * @see com.hellocld.AGED.core.ASystem#execute(com.hellocld.AGED.core.EntityManager)
	 */
	@Override
	public void execute(EntityManager em) {
		moveSet = em.getAllEntitiesPossessingComponent(SimpleMove.class);
		
		for(moveIter = moveSet.iterator(); moveIter.hasNext();) {
			entity = moveIter.next();
			if(em.getComponent(entity, SimpleMove.class).active){
				em.getComponent(entity, Position2D.class).x += em.getComponent(entity, Velocity2D.class).xVel;
				em.getComponent(entity, Position2D.class).y += em.getComponent(entity, Velocity2D.class).yVel;
			}
		}
	}

}
