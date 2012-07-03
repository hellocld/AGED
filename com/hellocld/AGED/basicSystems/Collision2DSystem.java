/**
 * 
 */
package com.hellocld.AGED.basicSystems;

import java.util.Iterator;
import java.util.Set;

import com.hellocld.AGED.basicComponents.Collision2D;
import com.hellocld.AGED.core.ASystem;
import com.hellocld.AGED.core.EntityManager;

/**
 * Probably one of the most complicated ASystems in AGED, Collision2DSystem checks to see if two objects
 * collided, and then tells each component where they hit, which entity (or entities) they hit, which
 * sides were hit on each collision, etc. From there other ASystems can handle what to do with each
 * entity. 
 * @author CLD
 *
 */
public class Collision2DSystem implements ASystem {

	/* (non-Javadoc)
	 * @see com.hellocld.AGED.core.ASystem#execute(com.hellocld.AGED.core.EntityManager)
	 */
	@Override
	public void execute(EntityManager em) {
		//generate the set of all entities containing Collision2D components
		Set<Integer> collideSet = em.getAllEntitiesPossessingComponent(Collision2D.class);
		
		//big ol' loop through the collideSet
		for(Iterator<Integer> collideIter = collideSet.iterator(); collideIter.hasNext();) {
			//pick an entity (preferably the next one)
			int entity = collideIter.next();
			
			//next, we check to see if entity is in the collidingEntities HashMap of the entity we're checking;
			//if it is, that means we've already done the calculations once and don't need to do it again
		}

	}

}
