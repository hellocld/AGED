/**
 * 
 */
package com.hellocld.AGED.basicSystems;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.hellocld.AGED.basicComponents.Collision2D;
import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.Size2D;
import com.hellocld.AGED.basicComponents.Velocity2D;
import com.hellocld.AGED.basicComponents.Collision2D.CollideType;
import static com.hellocld.AGED.basicComponents.Collision2D.CollideType.*;
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
		
		//first, update all the collision data in all the entities
		for(Iterator<Integer> collideIter = collideSet.iterator(); collideIter.hasNext();) {
			int entity = collideIter.next();
			HashMap<CollideType, Float> tempCollisionData = new HashMap<CollideType, Float>(em.getComponent(entity, Collision2D.class).collisionData);
			tempCollisionData.put(HALFWIDTH, (em.getComponent(entity, Size2D.class).width/2));
			tempCollisionData.put(HALFHEIGHT, (em.getComponent(entity, Size2D.class).height/2));
			tempCollisionData.put(CURRENT_X, (em.getComponent(entity, Position2D.class).x + tempCollisionData.get(HALFWIDTH)));
			tempCollisionData.put(CURRENT_Y, (em.getComponent(entity, Position2D.class).y + tempCollisionData.get(HALFHEIGHT)));
			tempCollisionData.put(NEXT_X, (em.getComponent(entity, Velocity2D.class).xVel + tempCollisionData.get(CURRENT_X)));
			tempCollisionData.put(NEXT_Y, (em.getComponent(entity, Velocity2D.class).yVel + tempCollisionData.get(CURRENT_Y)));
			em.getComponent(entity, Collision2D.class).collisionData = tempCollisionData;
		}
		
		//big ol' loop through the collideSet
		for(Iterator<Integer> collideIter = collideSet.iterator(); collideIter.hasNext();) {
			//pick an entity (preferably the next one)
			int entity = collideIter.next();
			
			//make a new list of all the other entities the one we're checking could possibly collide with
			Set<Integer> possibleSet = em.getAllEntitiesPossessingComponent(Collision2D.class);
			
			//and another for loop to go through all the possibles
			for(Iterator<Integer> possibleIter = possibleSet.iterator(); possibleIter.hasNext();) {
				
				int possibleEntity = possibleIter.next();
				//first, we check to see if entity and possible entity are one and the same
				//if they are, we just skip to the next item in possibleIter; an object can't collide with itself, can it?
				if(entity == possibleEntity) continue;
				
				//next, we check to see if we've already done a test between possibleEntity and entity
				//if we have, then we just copy the data from possibleEntity to entity and move on to the next possibleIter
				//this prevents duplicating efforts
				if(em.getComponent(possibleEntity, Collision2D.class).collidingEntities.containsKey(entity)) {
					em.getComponent(entity, Collision2D.class).collidingEntities.put(possibleEntity, em.getComponent(possibleEntity, Collision2D.class).collidingEntities.get(entity));
					continue;
				}
				
				//finally, if entity isn't checking against itself and no test has been made against possibleEntity already, we do the maths
				//for the collision test
				
				//first, let's collect all the collision data on both entities in temporary variables so we don't need to keep making calls
				//to the entity manager
				HashMap<CollideType, Float> eCollisionData = new HashMap<CollideType, Float>(em.getComponent(entity, Collision2D.class).collisionData);
				HashMap<CollideType, Float> pCollisionData = new HashMap<CollideType, Float>(em.getComponent(possibleEntity, Collision2D.class).collisionData);
				
				//make a "superobject" for each entity, the size of the entire space the object will take up from where it starts to where it moves to
				//we'll do the x axis first, so if there's no collision on that axis we can skip the y axis check
				float eXL = eCollisionData.get(CURRENT_X) - eCollisionData.get(HALFWIDTH);
				float eXR = eCollisionData.get(NEXT_X) + eCollisionData.get(HALFWIDTH);
				float pXL = pCollisionData.get(CURRENT_X) - pCollisionData.get(HALFWIDTH);
				float pXR = pCollisionData.get(NEXT_X) + pCollisionData.get(HALFWIDTH);
				
				//do the check
				// if eXL OR eXR is somewhere inside pX					  
				if((eXL > pXL && eXL < pXR) || (eXR > pXL && eXR < pXR)) {
					//if the x check returns true, then do the y check
					float eYT = eCollisionData.get(CURRENT_Y) - eCollisionData.get(HALFHEIGHT);
					float eYB = eCollisionData.get(NEXT_Y) + eCollisionData.get(HALFHEIGHT);
					float pYT = pCollisionData.get(CURRENT_Y) - pCollisionData.get(HALFHEIGHT);
					float pYB = pCollisionData.get(NEXT_Y) + pCollisionData.get(HALFHEIGHT);
					if((eYT > pYT && eYT < pYB) || (eYB > pYT && eYB < pYB)) {
						//if the y check returns true,then there's a collision!
						//debug log!
						System.out.println("Collision detected between entities "+entity+" and "+possibleEntity);
						//DO COLLISION DATA CREATION TESTING STUFF HERE
					}
				}
			}
			
		}

	}

}
