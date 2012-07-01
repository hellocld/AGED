/**
 * 
 */
package com.hellocld.tests.GameTest.ASystems;

import java.util.Iterator;
import java.util.Set;

import org.lwjgl.input.*;

import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.Velocity2D;
import com.hellocld.AGED.core.ASystem;
import com.hellocld.AGED.core.EntityManager;
import com.hellocld.tests.GameTest.Components.PlayerInput;

/**
 * Again, keeping this outside AGED's basicSystems package. See the PlayerInput component for an
 * explanation.
 * 
 * @author CLD
 *
 */
public class PlayerInputSystem implements ASystem {
	//a couple integers that'll serve as a sort of virtual joystick
	int x = 0;
	int y = 0;
	
	/* (non-Javadoc)
	 * @see com.hellocld.AGED.core.ASystem#execute(com.hellocld.AGED.core.EntityManager)
	 */
	@Override
	public void execute(EntityManager em) {
		
		Set<Integer> inputSet = em.getAllEntitiesPossessingComponent(PlayerInput.class);
		for(Iterator<Integer> inputIter = inputSet.iterator(); inputIter.hasNext();) {
			int entity = inputIter.next();
			
			//read the keyboard buffer
			while(Keyboard.next()) {
				if(Keyboard.getEventKeyState()) {
					if(Keyboard.getEventKey() == Keyboard.KEY_UP) {
						y = -1;
					}
					if(Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
						y = 1;
					}
					if(Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
						x = -1;
					}
					if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
						x = 1;
					}
				} else {
					if(Keyboard.getEventKey() == Keyboard.KEY_UP) {
						y = 0;
					}
					if(Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
						y = 0;
					}
					if(Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
						x = 0;
					}
					if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
						x = 0;
					}
				}
			}

			em.getComponent(entity, Position2D.class).x += (em.getComponent(entity, Velocity2D.class).xVel)*x;
			em.getComponent(entity, Position2D.class).y += (em.getComponent(entity, Velocity2D.class).yVel)*y;
			
		}

	}

}
