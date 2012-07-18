package com.hellocld.tests.CollisionTest.ASystems;

import static com.hellocld.AGED.basicComponents.Collision2D.CollideType.TIME;

import java.util.Iterator;
import java.util.Set;

import com.hellocld.AGED.basicComponents.Collision2D;
import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.SimpleMove;
import com.hellocld.AGED.basicComponents.Velocity2D;
import com.hellocld.AGED.core.ASystem;
import com.hellocld.AGED.core.EntityManager;
import com.hellocld.tests.CollisionTest.Components.BounceSurface;

public class BounceSurfaceHandler implements ASystem {
	
	public Set<Integer> bounceSurfaces, bouncingEntities;
	public Iterator<Integer> bounceIter, bounceEntitiesIter;
	public int bounceSurface, bounceEntity;
	public float time;
	
	@Override
	public void execute(EntityManager em) {
		bounceSurfaces = em.getAllEntitiesPossessingComponent(BounceSurface.class);
		for(bounceIter = bounceSurfaces.iterator(); bounceIter.hasNext();) {
			bounceSurface = bounceIter.next();
			bouncingEntities = em.getComponent(bounceSurface, Collision2D.class).collidingEntities.keySet();
			for(bounceEntitiesIter = bouncingEntities.iterator(); bounceEntitiesIter.hasNext();) {
				bounceEntity = bounceEntitiesIter.next();
				
				//get the time of the collision and move the object to the location of said collision
				time = (em.getComponent(bounceSurface, Collision2D.class).collidingEntities.get(bounceEntity).get(TIME));
				em.getComponent(bounceEntity, Position2D.class).x += em.getComponent(bounceEntity, Velocity2D.class).xVel * time;
				em.getComponent(bounceEntity, Position2D.class).y += em.getComponent(bounceEntity, Velocity2D.class).yVel * time;
				
				
				if(em.getComponent(bounceSurface, BounceSurface.class).xAxis) {
					em.getComponent(bounceEntity, Velocity2D.class).xVel *= -1;
				}
				if(em.getComponent(bounceSurface, BounceSurface.class).yAxis){
					em.getComponent(bounceEntity, Velocity2D.class).xVel *= -1;
				}
				
				//move the entity to the location post collision
				em.getComponent(bounceEntity, Position2D.class).x += em.getComponent(bounceEntity, Velocity2D.class).xVel * (1-time);
				em.getComponent(bounceEntity, Position2D.class).y += em.getComponent(bounceEntity, Velocity2D.class).yVel * (1-time);
				
				//finally, set moved to true so SimpleMoveSystem doesn't try moving it again
				em.getComponent(bounceEntity, SimpleMove.class).moved = true;
			}
			
		}
		
	}

}
