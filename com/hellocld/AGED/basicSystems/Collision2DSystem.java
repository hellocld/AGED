/**
 * 
 */
package com.hellocld.AGED.basicSystems;

import static com.hellocld.AGED.basicComponents.Collision2D.CollideType.COLLISION_X;
import static com.hellocld.AGED.basicComponents.Collision2D.CollideType.COLLISION_Y;
import static com.hellocld.AGED.basicComponents.Collision2D.CollideType.TIME;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.hellocld.AGED.basicComponents.CollideSet;
import com.hellocld.AGED.basicComponents.Collision2D;
import com.hellocld.AGED.basicComponents.EntityGroup;
import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.Size2D;
import com.hellocld.AGED.basicComponents.Velocity2D;
import com.hellocld.AGED.basicComponents.Collision2D.CollideType;
import com.hellocld.AGED.core.ASystem;
import com.hellocld.AGED.core.EntityManager;
import com.hellocld.AGED.util.Pair;

/**
 * An ASystem for checking collisions between groups of entities.
 * @author CLD
 *
 */
public class Collision2DSystem implements ASystem {
	//create all the variables so we're not making new ones on every call. That would be dumb.
	Set<Pair<String, String>> collidePairs;
	Pair<String, String> checkPair;
	Iterator<Pair<String, String>> collidePairsIter;
	String groupA, groupB;
	Set<Integer> groupASet, groupBSet;
	Iterator<Integer> groupAIter, groupBIter;
	int entityA, entityB, utilityEntity;
		
	float aX, aY, aHW, aHH, aXVel, aYVel, bX, bY, bHW, bHH, bXVel, bYVel, collideX, collideY, collideXtime, collideYtime, collideTime;
	
	//this system requires access to the entity containing EntityGroup and groupASet components
	public Collision2DSystem(int utilityEntity) {
		this.utilityEntity = utilityEntity;
	}
	
	/* (non-Javadoc)
	 * @see com.hellocld.AGED.core.ASystem#execute(com.hellocld.AGED.core.EntityManager)
	 */
	@Override
	public void execute(EntityManager em) {
		//clear the collidePairs set
		if(collidePairs == null) collidePairs = new HashSet<Pair<String, String>>();
		collidePairs.clear();
		//collect all the collision pairs
		collidePairs.addAll(em.getComponent(utilityEntity, CollideSet.class).pairs);
		//start an iterator for the pairs
		for(collidePairsIter = collidePairs.iterator(); collidePairsIter.hasNext();) {
			//get the next collision pair/groups to check
			checkPair = collidePairsIter.next();
			groupA = checkPair.getLeft();
			groupB = checkPair.getRight();
			//create the sets for groupA and groupB
			if(groupASet == null) groupASet = new HashSet<Integer>();
			groupASet.clear();
			groupASet.addAll(em.getComponent(utilityEntity, EntityGroup.class).getGroup(groupA));
			
			if(groupBSet == null) groupBSet = new HashSet<Integer>();
			groupBSet.clear();
			groupBSet.addAll(em.getComponent(utilityEntity, EntityGroup.class).getGroup(groupB));
			
			//iterate through each entity in groupASet
			for(groupAIter = groupASet.iterator(); groupAIter.hasNext();) {
				//get the first entity in collideIter
				entityA = groupAIter.next();
				//iterate through each entity in groupBSet
				for(groupBIter = groupBSet.iterator(); groupBIter.hasNext();) {
					//get the first entity in groupBIter
					entityB = groupBIter.next();
					
					//if entityA and entityB are the same, we skip the test and move to the next entityB
					if(entityA == entityB) continue;
					
					//if we've already checked between entity and entityB, we skip the test and move to the next entityB
					if(em.getComponent(entityA, Collision2D.class).collidingEntities.containsKey(entityB)) continue;
					
					//finally we check for an overlap
					
					if(checkOverlap(em, entityA, entityB)) {
						//calculate the collision point and record it!
						recordCollision(em, entityA, entityB);
						//debug
						System.out.println("COLLISION between entity "+entityA+" and "+entityB);
					} else {
						//no collision
						continue;
					}
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
	
	//a big scary method that checks for an overlap between two entities
	public boolean checkOverlap(EntityManager em, int entity1, int entity2) {
		//we collect lots of information about entity1 and entity2 and store it all in these temporary values
		aX = em.getComponent(entity1, Position2D.class).x;		//x position
		aY = em.getComponent(entity1, Position2D.class).y;		//y position
		aHW = (em.getComponent(entity1, Size2D.class).width)*0.5f;	//halfwidth
		aHH = (em.getComponent(entity1, Size2D.class).height)*0.5f;	//halfheight
		aXVel = em.getComponent(entity1, Velocity2D.class).xVel;	//x velocity
		aYVel = em.getComponent(entity1, Velocity2D.class).yVel;	//y velocity
		
		bX = em.getComponent(entity2, Position2D.class).x;
		bY = em.getComponent(entity2, Position2D.class).y;
		bHW = (em.getComponent(entity2, Size2D.class).width)*0.5f;
		bHH = (em.getComponent(entity2, Size2D.class).height)*0.5f;
		bXVel = em.getComponent(entity2, Velocity2D.class).xVel;
		bYVel = em.getComponent(entity2, Velocity2D.class).yVel;
		
		//this horrifyingly long if statement is true only if there is an overlap between entity1 and entity2 across both axis
		if((calculateD(aX, aXVel, aHW, bX, bXVel, bHW) <= (Math.abs(aXVel+aHW*2) + Math.abs(bXVel+bHW*2))) && (calculateD(aY, aYVel, aHH, bY, bYVel, bHH) <= (Math.abs(aYVel+aHH*2) + Math.abs(bYVel+bHH*2)))) {
			return true;
		} else {
			return false;
		}
	}
	
	public void recordCollision (EntityManager em, int entity1, int entity2) {
		//calculate the collision time on both axis
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
		collideX = collideTime * ((aXVel+bXVel)/2) + ((aX+bX)/2) + ((aHW + bHW)/2);
		collideY = collideTime * ((aYVel+bYVel)/2) + ((aY+bY)/2) + ((aHH + bHH)/2);
		
		//finally, let's add the data to entity and entityB
		em.getComponent(entity1, Collision2D.class).collidingEntities.put(entity2, getCollisionData(collideX, collideY, collideTime));
		em.getComponent(entity2, Collision2D.class).collidingEntities.put(entity1, getCollisionData(collideX, collideY, collideTime));
	}
}
