/**
 * 
 */
package com.hellocld.tests.CollisionTest.ASystems;

import java.util.Iterator;
import java.util.Set;

import com.hellocld.AGED.basicComponents.Render;
import com.hellocld.AGED.core.ASystem;
import com.hellocld.AGED.core.EntityManager;
import com.hellocld.tests.CollisionTest.Components.CollisionMarker;

/**
 * @author CLD
 *
 */
public class CollisionMarkerHandlerSystem implements ASystem {
	Set<Integer> entities;
	Iterator<Integer> entityIter;
	int entity;
	
	/* (non-Javadoc)
	 * @see com.hellocld.AGED.core.ASystem#execute(com.hellocld.AGED.core.EntityManager)
	 */
	@Override
	public void execute(EntityManager em) {
		entities = em.getAllEntitiesPossessingComponent(CollisionMarker.class);
		for(entityIter = entities.iterator(); entityIter.hasNext();) {
			entity = entityIter.next();
			if(em.getComponent(entity, Render.class).color[0] > 0) {
				em.getComponent(entity, Render.class).color[0] -= 0.01;
			} 
		}
		
		/*
		for(entityIter = entities.iterator(); entityIter.hasNext();) {
			if(em.getComponent(entity, Render.class).color[0] <= 0) {
				em.killEntity(entity);
			}
		}
		*/

	}

}
