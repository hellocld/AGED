/**
 * 
 */
package com.hellocld.tests.GettingBackIntoIt;

import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.Render;
import com.hellocld.AGED.basicComponents.Size2D;
import com.hellocld.AGED.basicSystems.Render2DSystem;
import com.hellocld.AGED.core.GameState;

/**
 * @author chris
 *
 */
public class StateOne extends GameState {
	public int thing1, thing2;
	
	/**
	 * 
	 */
	public void create() {
		super.create();
		
		thing1 = em.createEntity();
		thing2 = em.createEntity();
		
		em.addComponent(thing1, new Size2D(16, 16));
		em.addComponent(thing1, new Position2D(32, 32));
		em.addComponent(thing1, new Render());
		
		em.addComponent(thing2, new Size2D(32, 32));
		em.addComponent(thing2, new Position2D(128, 128));
		em.addComponent(thing2, new Render());
		
		systems.add(new Render2DSystem());
	}
	
	public void update() {
		super.update();
		
	}

}
