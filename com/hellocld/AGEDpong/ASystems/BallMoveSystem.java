/**
 * 
 */
package com.hellocld.AGEDpong.ASystems;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.hellocld.AGED.basicComponents.Collision2D;
import com.hellocld.AGED.basicComponents.Collision2D.CollideType;
import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.Size2D;
import com.hellocld.AGED.basicComponents.Velocity2D;
import com.hellocld.AGED.core.ASystem;
import com.hellocld.AGED.core.EntityManager;
import com.hellocld.AGED.core.Game;

/**
 * This handles movement of the ball, bouncing it off the paddle and walls and whatnot
 * @author CLD
 *
 */
public class BallMoveSystem implements ASystem {
	//a set for all the ball objects
	public Set<Integer> balls = new HashSet<Integer>();
	public Iterator<Integer> ballSet;
	public int ball;
	
	//a set for collidingEntities data to search
	public HashMap<Integer, HashMap<CollideType, Float>> collidingEntities = new HashMap<Integer, HashMap<CollideType, Float>>();
	public float time;
	public int collidingEntity;
	
	/* (non-Javadoc)
	 * @see com.hellocld.AGED.core.ASystem#execute(com.hellocld.AGED.core.Game, com.hellocld.AGED.core.EntityManager)
	 */
	@Override
	public void execute(Game game, EntityManager em) {
		for(ballSet = balls.iterator(); ballSet.hasNext();) {
			ball = ballSet.next();
			if(em.getComponent(ball, Collision2D.class).collidingEntities == null) {
				//if there's no collision, just move the ball normally
				em.getComponent(ball, Position2D.class).x += em.getComponent(ball, Velocity2D.class).xVel;
				em.getComponent(ball, Position2D.class).y += em.getComponent(ball, Velocity2D.class).yVel;
			} else {
				//reset the time to 1
				time = 1;
				collidingEntities = em.getComponent(ball, Collision2D.class).collidingEntities;
				
				//this loop goes through all the collision data and determines which collision occurs first, bouncing the ball off that collision point
				for(Iterator<Integer> entities = collidingEntities.keySet().iterator(); entities.hasNext();) {
					int entity = entities.next();
					if(time > collidingEntities.get(entity).get(CollideType.TIME)) {
						time = collidingEntities.get(entity).get(CollideType.TIME);
						collidingEntity = entity;
					}
				}
				
				//make both moves based on the collision data and the new updated velocities
				em.getComponent(ball, Position2D.class).x += em.getComponent(ball, Velocity2D.class).xVel * time;
				em.getComponent(ball, Position2D.class).y += em.getComponent(ball, Velocity2D.class).yVel * time;
				
				//determine where the collision occurred in relation to the ball's center, and adjust velocity appropriately
				if(collidingEntities.get(collidingEntity).get(CollideType.COLLISION_X) < em.getComponent(ball, Position2D.class).x ||  collidingEntities.get(collidingEntity).get(CollideType.COLLISION_X) > em.getComponent(ball, Position2D.class).x + em.getComponent(ball, Size2D.class).width) {
					em.getComponent(ball, Velocity2D.class).xVel *= -1;
				}
				
			}
		}

	}

}
