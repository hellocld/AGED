/**
 * 
 */
package com.hellocld.AGED.util;

import java.util.Arrays;

import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.Size2D;
import com.hellocld.AGED.basicComponents.Velocity2D;
import com.hellocld.AGED.core.EntityManager;

/**
 * @author chris
 *
 */
public class AGEDPhysics {
	/**
	 * Calculates and returns the distance between the two furthest points of each object along one axis
	 * @param a		Starting point of the first object
	 * @param aVel	Velocity of the first object
	 * @param b		Starting point of the second object
	 * @param bVel	Velocity of the second object
	 * @return		Distance between two furthest points covered by both objects along one axis
	 */
	public float calculateAxisArea (float a, float aVel, float aH, float b, float bVel, float bH) {
		float vals[] = {a + aH*2, a, aVel+a+aH*2, aVel+a, b+bH*2, b, bVel+b+bH*2, bVel+b};
		Arrays.sort(vals);
		return  Math.abs(vals[7] - vals[0]);
	}
	
	/**
	 * Calculates the sum of the area covered by each object along one axis
	 * @param aVel	Velocity of the first object
	 * @param aH	Halfwidth/height of the first object
	 * @param bVel	Velocity of the second object
	 * @param bH	Halfwidth/height of the second object
	 * @return		Total distance travelled by both objects
	 */
	public float calculatePostArea(float aVel, float aH, float bVel, float bH) {
		return (Math.abs(aVel) + Math.abs(bVel) + aH*2 + bH*2);
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
	
	/**
	 * Checks for an overlap between two entities
	 * @param em		
	 * @param entity1
	 * @param entity2
	 * @return
	 */
	public boolean checkOverlap(EntityManager em, int entity1, int entity2) {
		//we collect lots of information about entity1 and entity2 and store it all in these temporary values
		float aX = em.getComponent(entity1, Position2D.class).x;		//x position
		float aY = em.getComponent(entity1, Position2D.class).y;		//y position
		float aHW = (em.getComponent(entity1, Size2D.class).width)*0.5f;	//halfwidth
		float aHH = (em.getComponent(entity1, Size2D.class).height)*0.5f;	//halfheight
		float aXVel, aYVel;
		if(!em.hasComponent(entity1, Velocity2D.class)) {
			aXVel = 0;
			aYVel = 0;
		} else {
			aXVel = em.getComponent(entity1, Velocity2D.class).xVel;	//x velocity
			aYVel = em.getComponent(entity1, Velocity2D.class).yVel;	//y velocity
		}
		
		
		float bX = em.getComponent(entity2, Position2D.class).x;
		float bY = em.getComponent(entity2, Position2D.class).y;
		float bHW = (em.getComponent(entity2, Size2D.class).width)*0.5f;
		float bHH = (em.getComponent(entity2, Size2D.class).height)*0.5f;
		float bXVel, bYVel;
		if(!em.hasComponent(entity2, Velocity2D.class)) {
			bXVel = 0;
			bYVel = 0;
		} else {
			bXVel = em.getComponent(entity2, Velocity2D.class).xVel;	//x velocity
			bYVel = em.getComponent(entity2, Velocity2D.class).yVel;	//y velocity
		}
		
		//this horrifyingly long if statement is true only if there is an overlap between entity1 and entity2 across both axis
		if(calculateAxisArea(aX, aXVel, aHW, bX, bXVel, bHW) <= calculatePostArea(aXVel, aHW, bXVel, bHW) && calculateAxisArea(aY, aYVel, aHH, bY, bYVel, bHH) <= calculatePostArea(aYVel, aHH, bYVel, bHH)) {
			return true;
		} else {
			return false;
		}
	}
}

