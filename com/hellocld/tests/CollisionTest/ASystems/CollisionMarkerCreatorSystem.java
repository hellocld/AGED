/**
 * 
 */
package com.hellocld.tests.CollisionTest.ASystems;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.hellocld.AGED.basicComponents.Collision2D;
import com.hellocld.AGED.basicComponents.Collision2D.CollideType;
import static com.hellocld.AGED.basicComponents.Collision2D.CollideType.*;
import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.Render;
import com.hellocld.AGED.basicComponents.Size2D;
import com.hellocld.AGED.core.ASystem;
import com.hellocld.AGED.core.EntityManager;
import com.hellocld.tests.CollisionTest.Components.CollisionMarker;

/**
 * @author CLD
 *
 */
public class CollisionMarkerCreatorSystem implements ASystem {
	
	Set<Integer> entities, collisions;
	Iterator<Integer> entityIter, collisionIter;
	int entity, collision, collisionMarker;
	HashMap<CollideType, Float> collisionData;
	
	/* (non-Javadoc)
	 * @see com.hellocld.AGED.core.ASystem#execute(com.hellocld.AGED.core.EntityManager)
	 */
	@Override
	public void execute(EntityManager em) {
		entities = em.getAllEntitiesPossessingComponent(Collision2D.class);
		
		for(entityIter = entities.iterator(); entityIter.hasNext();) {
			entity = entityIter.next();
			collisions = em.getComponent(entity, Collision2D.class).collidingEntities.keySet();
			for(collisionIter = collisions.iterator(); collisionIter.hasNext();) {
				collision = collisionIter.next();
				collisionMarker = em.createEntity();
				
				collisionData = em.getComponent(entity, Collision2D.class).collidingEntities.get(collision);
				em.addComponent(collisionMarker, new CollisionMarker());
				em.addComponent(collisionMarker, new Position2D());
				em.addComponent(collisionMarker, new Size2D());
				em.addComponent(collisionMarker, new Render());
				
				em.getComponent(collisionMarker, Position2D.class).x = collisionData.get(COLLISION_X);
				em.getComponent(collisionMarker, Position2D.class).y = collisionData.get(COLLISION_Y);
				em.getComponent(collisionMarker, Size2D.class).width = 1;
				em.getComponent(collisionMarker, Size2D.class).height = 1;
				em.getComponent(collisionMarker, Render.class).setColor(1, 0, 0, 1);
				
			}
			
		}

	}

}
