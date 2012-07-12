/**
 * 
 */
package com.hellocld.tests.CollisionTest.ASystems;

import org.lwjgl.input.Keyboard;

import com.hellocld.AGED.basicComponents.Collision2D;
import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.Render;
import com.hellocld.AGED.basicComponents.SimpleMove;
import com.hellocld.AGED.basicComponents.Size2D;
import com.hellocld.AGED.basicComponents.Velocity2D;
import com.hellocld.AGED.core.ASystem;
import com.hellocld.AGED.core.EntityManager;

/**
 * @author chris
 *
 */
public class BouncingBoxGenerator implements ASystem {
	public boolean created = false;
	public int tempEntity;
	/* (non-Javadoc)
	 * @see com.hellocld.AGED.core.ASystem#execute(com.hellocld.AGED.core.EntityManager)
	 */
	@Override
	public void execute(EntityManager em) {
		
		while(Keyboard.next()) {
			if(Keyboard.getEventKeyState()) {
				if(Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
					if(!created) {
						tempEntity = em.createEntity();
						em.addComponent(tempEntity, new Position2D());
						em.addComponent(tempEntity, new Size2D());
						em.addComponent(tempEntity, new Velocity2D());
						em.addComponent(tempEntity, new Collision2D());
						em.addComponent(tempEntity, new SimpleMove());
						em.addComponent(tempEntity, new Render());
						em.getComponent(tempEntity, Position2D.class).setPosition(155, 115);
						em.getComponent(tempEntity, Size2D.class).setSize(10, 10);
						em.getComponent(tempEntity, Velocity2D.class).setVelocity((float)((Math.random()*10) - 5), (float)((Math.random()*10) - 5));
						created = true;
					}
				}
			} else {
				if(Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
					if(created) created = false;
				}
			}
		}
	}
}
