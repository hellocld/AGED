/**
 * 
 */
package com.hellocld.AGED.basicSystems;

import static com.hellocld.AGED.basicComponents.Collision2D.CollideType.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.hellocld.AGED.basicComponents.Collision2D;
import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.Size2D;
import com.hellocld.AGED.basicComponents.Velocity2D;
import com.hellocld.AGED.basicComponents.Collision2D.CollideType;
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
	//create all the variables so we're not making new ones on every call. That would be dumb.
	Set<Integer> collideSet, possibleSet;
	Iterator<Integer> collideIter, possibleIter;
	int entity, possibleEntity;
	HashMap<CollideType, Float> tempCollisionData, eCollisionData, pCollisionData, temp;
	float dX, dY, eMX, eMY, pMX, pMY, eXVel, eYVel, pXVel, pYVel, collideTimeX, collideTimeY, collideTime;
	
	/* (non-Javadoc)
	 * @see com.hellocld.AGED.core.ASystem#execute(com.hellocld.AGED.core.EntityManager)
	 */
	@Override
	public void execute(EntityManager em) {
		//generate the set of all entities containing Collision2D components
		collideSet = em.getAllEntitiesPossessingComponent(Collision2D.class);
		
		//first, update all the collision data in all the entities and clear the old data
		for(collideIter = collideSet.iterator(); collideIter.hasNext();) {
			entity = collideIter.next();
			tempCollisionData = em.getComponent(entity, Collision2D.class).collisionData;
			tempCollisionData.put(CURRENT_X, em.getComponent(entity, Position2D.class).x + tempCollisionData.get(HALFWIDTH) + tempCollisionData.get(OFFSET_X));
			tempCollisionData.put(CURRENT_Y, em.getComponent(entity, Position2D.class).y + tempCollisionData.get(HALFHEIGHT) + tempCollisionData.get(OFFSET_Y));
			tempCollisionData.put(NEXT_X, tempCollisionData.get(CURRENT_X)+em.getComponent(entity, Velocity2D.class).xVel);
			tempCollisionData.put(NEXT_Y, tempCollisionData.get(CURRENT_Y)+em.getComponent(entity, Velocity2D.class).yVel);
			
			em.getComponent(entity, Collision2D.class).collisionData = tempCollisionData;
			em.getComponent(entity, Collision2D.class).collidingEntities.clear();
			tempCollisionData.clear();
		}
		
		//big ol' loop through the collideSet
		for(collideIter = collideSet.iterator(); collideIter.hasNext();) {
			//pick an entity (preferably the next one)
			entity = collideIter.next();
			
			//make a new list of all the other entities the one we're checking could possibly collide with
			possibleSet = em.getAllEntitiesPossessingComponent(Collision2D.class);
			
			//and another for loop to go through all the possibles
			for(possibleIter = possibleSet.iterator(); possibleIter.hasNext();) {
				
				possibleEntity = possibleIter.next();
				//first, we check to see if entity and possible entity are one and the same
				//if they are, we just skip to the next item in possibleIter; an object can't collide with itself, can it?
				if(entity == possibleEntity) continue;
				
				//next, we check to see if we've already done a test between possibleEntity and entity (entity will already contain the data for the collision)
				//this prevents duplicating efforts
				if(em.getComponent(entity, Collision2D.class).collidingEntities.containsKey(possibleEntity)) {
					continue;
				}
				
				//finally, if entity isn't checking against itself and no test has been made against possibleEntity already, we do the maths
				//for the collision test
				
				//first, let's collect all the collision data on the possibleEntity
				eCollisionData = em.getComponent(entity, Collision2D.class).collisionData;
				pCollisionData = em.getComponent(possibleEntity, Collision2D.class).collisionData;
				
				//next, determine the distance between both entities
				dX = Math.abs(eCollisionData.get(CURRENT_X) - pCollisionData.get(CURRENT_X));
				dY = Math.abs(eCollisionData.get(CURRENT_Y) - pCollisionData.get(CURRENT_Y));
				
				//the movement vectors of entity and possibleEntity
				eMX = Math.abs(eCollisionData.get(NEXT_X) - eCollisionData.get(CURRENT_X)) + eCollisionData.get(HALFWIDTH);
				eMY = Math.abs(eCollisionData.get(NEXT_Y) - eCollisionData.get(CURRENT_Y)) + eCollisionData.get(HALFHEIGHT);
				pMX = Math.abs(pCollisionData.get(NEXT_X) - pCollisionData.get(CURRENT_X)) + pCollisionData.get(HALFWIDTH);
				pMY = Math.abs(pCollisionData.get(NEXT_Y) - pCollisionData.get(CURRENT_Y)) + pCollisionData.get(HALFHEIGHT);
				
				//debug
				//System.out.println("["+dX+"] "+eMX+" | "+pMX);
				//check for the collision
				if((dX <= eMX + pMX) && (dY <= eMY + pMY)) {
					//collision detected!
					System.out.println("Overlap detected between entities "+entity+" and "+possibleEntity);
					
					//so we need to figure out where and when the collision occurs
					
					//we're gonna need the velocity info on both objects
					eXVel = em.getComponent(entity, Velocity2D.class).xVel;
					eYVel = em.getComponent(entity, Velocity2D.class).yVel;
					pXVel = em.getComponent(possibleEntity, Velocity2D.class).xVel;
					pYVel = em.getComponent(possibleEntity, Velocity2D.class).yVel;
					
					//probably need to calculate the time first; we can derive where the collision occurs from that
					//make sure none of the velocities equal zero first
					if(eXVel == 0 || pXVel == 0) {
						collideTimeX = 1;
					} else {
						collideTimeX = (dX/Math.abs(eXVel)) + (dX/Math.abs(pXVel));
					}
					//repeat for y axis
					if(eYVel == 0 || pYVel == 0) {
						collideTimeY = 1;
					} else {
						collideTimeY = (dY/Math.abs(eYVel)) + (dY/Math.abs(eYVel));
					}
					
					//now check to see which time to use (if for some weird reason they turn out to be different) and add it to the collidingEntities 
					//data map for entity and possibleEntity
					temp = new HashMap<CollideType, Float>();
					if(collideTimeX != collideTimeY) {
						if(collideTimeX > collideTimeY) {
							collideTime = collideTimeY;
							temp.put(TIME, collideTime);
						} else {
							collideTime = collideTimeX;
							temp.put(TIME, collideTime);
						}
					} else {
						collideTime = collideTimeX;
						temp.put(TIME, collideTime);
					}
					
					//next, calculate the points of collision and add them to the collidingEntities HashMap
					temp.put(COLLISION_X, (collideTime * eXVel) + eCollisionData.get(CURRENT_X));
					temp.put(COLLISION_Y, (collideTime * eYVel) + eCollisionData.get(CURRENT_Y));
					System.out.println("Collision at ["+temp.get(COLLISION_X)+", "+temp.get(COLLISION_Y)+"]");
					//and finally, put all the collision data into the collidingEntities HashMaps
					em.getComponent(entity, Collision2D.class).collidingEntities.put(possibleEntity, temp);
					em.getComponent(possibleEntity, Collision2D.class).collidingEntities.put(entity, temp);
									
				} else {
					continue;
				}
			}
			
		}

	}

}
