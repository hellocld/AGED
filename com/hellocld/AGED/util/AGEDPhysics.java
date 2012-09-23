/**
 * 
 */
package com.hellocld.AGED.util;

import java.util.Arrays;

import com.hellocld.AGED.basicComponents.PhysicsObject2D;
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
	 * Calculates and returns the distance between the two furthest points of each object along one axis
	 * @param entity1	One of two entities
	 * @param entity2	One of two entities
	 * @param axis		The axis being checked
	 * @return
	 */
	public float calculateAxisArea (PhysicsObject2D entity1, PhysicsObject2D entity2, Axis axis) {
		float area = 0;
		if(axis == Axis.X) {
			float vals[] = {entity1.getX() + entity1.getWidth(), entity1.getX(), entity1.getXvel()+entity1.getX()+entity1.getWidth(), entity1.getXvel()+entity1.getX(), 
							entity2.getX() + entity2.getWidth(), entity2.getX(), entity2.getXvel()+entity2.getX()+entity2.getWidth(), entity2.getXvel()+entity2.getX()
							};
			Arrays.sort(vals);
			area = Math.abs(vals[7] - vals[0]);
		} else if(axis == Axis.Y) {
			float vals[] = {entity1.getY() + entity1.getWidth(), entity1.getY(), entity1.getYvel()+entity1.getY()+entity1.getWidth(), entity1.getYvel()+entity1.getY(), 
					entity2.getY() + entity2.getWidth(), entity2.getY(), entity2.getYvel()+entity2.getY()+entity2.getWidth(), entity2.getYvel()+entity2.getY()
					};
			Arrays.sort(vals);
			area = Math.abs(vals[7] - vals[0]);
		}
		return area;
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
	 * Calculates the sum of the area covered by each object along one axis
	 * @param entity1	One of two objects checked
	 * @param entity2	One of two objects checked
	 * @param axis		The axis ('x' or 'y') being checked
	 */
	public float calculatePostArea(PhysicsObject2D entity1, PhysicsObject2D entity2, Axis axis) {
		float area = 0;
		if(axis == Axis.X) {
			area = (Math.abs(entity1.getXvel()) + Math.abs(entity2.getXvel()) + entity1.getWidth() + entity2.getWidth());
		} else if(axis == Axis.Y) {
			area = (Math.abs(entity1.getYvel()) + Math.abs(entity2.getYvel()) + entity1.getHeight() + entity2.getHeight());
		}
		return area;
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
	
	/**
	 * Checks for an overlap between two Physics Objects	
	 * @param entity1	One of two PhysicsObjects
	 * @param entity2	One of two PhysicsObjects
	 * @return
	 */
	public boolean checkOverlap(PhysicsObject2D entity1, PhysicsObject2D entity2) {
		//make sure this isn't checking an object against itself
		
		//this horrifyingly long if statement is true only if there is an overlap between entity1 and entity2 across both axis
		if(calculateAxisArea(entity1, entity2, Axis.X) <= calculatePostArea(entity1, entity2, Axis.X) && calculateAxisArea(entity1, entity2, Axis.Y) <= calculatePostArea(entity1, entity2, Axis.Y)) {
			return true;
		} else {
			return false;
		}
	}
}

