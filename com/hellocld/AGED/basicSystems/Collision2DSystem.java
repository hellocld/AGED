/**
 * 
 */
package com.hellocld.AGED.basicSystems;

import static com.hellocld.AGED.basicComponents.Collision2D.CollideType.COLLISION_X;
import static com.hellocld.AGED.basicComponents.Collision2D.CollideType.COLLISION_Y;
import static com.hellocld.AGED.basicComponents.Collision2D.CollideType.TIME;

import java.util.Arrays;
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
	/* OLD VARIABLES
	HashMap<CollideType, Float> tempCollisionData, eCollisionData, pCollisionData, temp;
	float dX, dY, eMX, eMY, pMX, pMY, eXVel, eYVel, pXVel, pYVel, collideTimeX, collideTimeY, collideTime;
	*/
	
	float aX, aY, aHW, aHH, aXVel, aYVel, bX, bY, bHW, bHH, bXVel, bYVel, collideX, collideY, collideXtime, collideYtime, collideTime;
	
	/* (non-Javadoc)
	 * @see com.hellocld.AGED.core.ASystem#execute(com.hellocld.AGED.core.EntityManager)
	 */
	@Override
	public void execute(EntityManager em) {
		//generate the set of all entities containing Collision2D components
		collideSet = em.getAllEntitiesPossessingComponent(Collision2D.class);
		
		//first clear the old data
		for(collideIter = collideSet.iterator(); collideIter.hasNext();) {
			entity = collideIter.next();
			em.getComponent(entity, Collision2D.class).collidingEntities.clear();
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
				
				//collect some data
				aX = em.getComponent(entity, Position2D.class).x;
				aY = em.getComponent(entity, Position2D.class).y;
				aHW = (em.getComponent(entity, Size2D.class).width)/2;
				aHH = (em.getComponent(entity, Size2D.class).height)/2;
				aXVel = em.getComponent(entity, Velocity2D.class).xVel;
				aYVel = em.getComponent(entity, Velocity2D.class).yVel;
				
				bX = em.getComponent(possibleEntity, Position2D.class).x;
				bY = em.getComponent(possibleEntity, Position2D.class).y;
				bHW = (em.getComponent(possibleEntity, Size2D.class).width)/2;
				bHH = (em.getComponent(possibleEntity, Size2D.class).height)/2;
				bXVel = em.getComponent(possibleEntity, Velocity2D.class).xVel;
				bYVel = em.getComponent(possibleEntity, Velocity2D.class).yVel;
				
				
				//start with a check of the x axis
				//System.out.println(calculateD(aX, aXVel, aHW, bX, bXVel, bHW)+" :: "+(Math.abs(aXVel+aHW*2) + Math.abs(bXVel+bHW*2)));
				
				if(calculateD(aX, aXVel, aHW, bX, bXVel, bHW) < Math.abs(aXVel+aHW*2) + Math.abs(bXVel+bHW*2)) {
					//if x axis tests true, check the y axis
					System.out.println("X axis check TRUE");
					if(calculateD(aY, aYVel, aHH, bY, bYVel, bHH) < Math.abs(aYVel+aHH*2) + Math.abs(bYVel+bHH*2)) {
						System.out.println("X axis check TRUE");
						//oh hey, a collision! Now let's get the time of the collision
						collideXtime = calculateTime(aX, aXVel, aHW, bX, bXVel, bHW);
						collideYtime = calculateTime(aY, aYVel, aHW, bY, bYVel, bHW);
						//if one of the times is less than the other, that's the axis that hit first; we'll use that as our collision time
						if(collideXtime < collideYtime) {
							collideTime = collideXtime;
						} else {
							collideTime = collideYtime;
						}
						
						//now let's use collideTime to figure out where the point of collision is
						//there should be only one point of collision between two objects, so using a or b as the reference shouldn't matter
						collideX = collideTime * aXVel + aX;
						collideY = collideTime * aYVel + aY;
						
						//finally, let's add the data to entity and possibleEntity
						em.getComponent(entity, Collision2D.class).collidingEntities.put(possibleEntity, getCollisionData(collideX, collideY, collideTime));
						em.getComponent(possibleEntity, Collision2D.class).collidingEntities.put(entity, getCollisionData(collideX, collideY, collideTime));
						
						//a little debug printing to show if the collision detection works
						System.out.println("Collision between entities "+entity+" and "+possibleEntity+" at ["+collideX+", "+collideY+"], time "+collideTime);
					}
				} else {
					//no collision
					continue;
				}
			}
			
		}

	}
	
	/**
	 * Calculates and returns the total distance covered by two objects along one axis
	 * @param a	Starting point of the first object
	 * @param aVel	Velocity of the first object
	 * @param b	Starting point of the second object
	 * @param bVel	Velocity of the second object
	 * @return		Total area covered by both objects
	 */
	public float calculateD (float a, float aVel, float aH, float b, float bVel, float bH) {
		float vals[] = {a, aVel+a, b, bVel+b};
		Arrays.sort(vals);
		return  Math.abs(vals[3] - vals[0] + aH + bH);
	}
	
	
	//this might not be right
	/**
	 * Calculates the time of the collision
	 * @param a		Starting point of the first object
	 * @param aVel	Velocity of the first object
	 * @param b		Starting point of the second object
	 * @param bVel	Velocity of the second object
	 * @return		The time of the collision (fraction of one frame cycle)
	 */
	public float calculateTime(float a, float aVel, float aH, float b, float bVel, float bH) {
		float d = Math.abs(a-b);
		if(d == 0) {
			float time = 0;
			return time;
		} else {
			float time = d/Math.abs(aVel) + Math.abs(bVel);
			return time;
		}
	}
	
	public HashMap<CollideType, Float> getCollisionData(float X, float Y, float time) {
		HashMap<CollideType, Float> tempData = new HashMap<CollideType, Float>();
		tempData.put(COLLISION_X, X);
		tempData.put(COLLISION_Y, Y);
		tempData.put(TIME, time);
		return tempData;
	}

}
