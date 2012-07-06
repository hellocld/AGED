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

	/* (non-Javadoc)
	 * @see com.hellocld.AGED.core.ASystem#execute(com.hellocld.AGED.core.EntityManager)
	 */
	@Override
	public void execute(EntityManager em) {
		//generate the set of all entities containing Collision2D components
		Set<Integer> collideSet = em.getAllEntitiesPossessingComponent(Collision2D.class);
		
		//first, update all the collision data in all the entities and clear the old data
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
			em.getComponent(entity, Collision2D.class).collidingEntities.clear();
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
				
				//next, we check to see if we've already done a test between possibleEntity and entity (entity will already contain the data for the collision)
				//this prevents duplicating efforts
				if(em.getComponent(entity, Collision2D.class).collidingEntities.containsKey(possibleEntity)) {
					continue;
				}
				
				//finally, if entity isn't checking against itself and no test has been made against possibleEntity already, we do the maths
				//for the collision test
				
				//first, let's collect all the collision data on both entities in temporary variables so we don't need to keep making calls
				//to the entity manager
				HashMap<CollideType, Float> eCollisionData = new HashMap<CollideType, Float>(em.getComponent(entity, Collision2D.class).collisionData);
				HashMap<CollideType, Float> pCollisionData = new HashMap<CollideType, Float>(em.getComponent(possibleEntity, Collision2D.class).collisionData);
				
				//next, determine the distance between both entities
				float dX = Math.abs(eCollisionData.get(CURRENT_X) - pCollisionData.get(CURRENT_X));
				float dY = Math.abs(eCollisionData.get(CURRENT_Y) - pCollisionData.get(CURRENT_Y));
				
				//the movement vectors of entity and possibleEntity
				float eMX = Math.abs(eCollisionData.get(NEXT_X) - eCollisionData.get(CURRENT_X)) + eCollisionData.get(HALFWIDTH);
				float eMY = Math.abs(eCollisionData.get(NEXT_Y) - eCollisionData.get(CURRENT_Y)) + eCollisionData.get(HALFHEIGHT);
				float pMX = Math.abs(pCollisionData.get(NEXT_X) - pCollisionData.get(CURRENT_X)) + pCollisionData.get(HALFWIDTH);
				float pMY = Math.abs(pCollisionData.get(NEXT_Y) - pCollisionData.get(CURRENT_Y)) + pCollisionData.get(HALFHEIGHT);
				
				//debug
				//System.out.println("["+dX+"] "+eMX+" | "+pMX);
				//check for the collision
				if((dX <= eMX + pMX) && (dY <= eMY + pMY)) {
					//collision detected!
					System.out.println("Overlap detected between entities "+entity+" and "+possibleEntity);
					
					//so we need to figure out where and when the collision occurs
					
					//we're gonna need the velocity info on both objects
					float eXVel = em.getComponent(entity, Velocity2D.class).xVel;
					float eYVel = em.getComponent(entity, Velocity2D.class).yVel;
					float pXVel = em.getComponent(possibleEntity, Velocity2D.class).xVel;
					float pYVel = em.getComponent(possibleEntity, Velocity2D.class).yVel;
					
					//make the collideTime variables
					float collideTimeX;
					float collideTimeY;
					
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
					HashMap<CollideType, Float> temp = new HashMap<CollideType, Float>();
					float collideTime;
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
