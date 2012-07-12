/**
 * 
 */
package com.hellocld.tests.CollisionTest;

import com.hellocld.AGED.basicComponents.Collision2D;
import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.Render;
import com.hellocld.AGED.basicComponents.Size2D;
import com.hellocld.AGED.basicComponents.Velocity2D;
import com.hellocld.AGED.basicSystems.Collision2DSystem;
import com.hellocld.AGED.basicSystems.Render2DSystem;
import com.hellocld.AGED.basicSystems.SimpleMoveSystem;
import com.hellocld.AGED.core.GameState;
import com.hellocld.tests.CollisionTest.ASystems.BounceSurfaceHandler;
import com.hellocld.tests.CollisionTest.ASystems.BouncingBoxGenerator;
import com.hellocld.tests.CollisionTest.Components.BounceSurface;

/**
 * @author CLD
 *
 */
public class CTState extends GameState {
	public int entity1;
	public int entity2;
	
	public void create() {
		super.create();
		
		//let's make some walls
		int[] walls = new int[4];
		
		for(int i = 0; i<4; i++) {
			walls[i] = em.createEntity();
			em.addComponent(walls[i], new Position2D());
			em.addComponent(walls[i], new Size2D());
			em.addComponent(walls[i], new Collision2D());
			em.addComponent(walls[i], new Velocity2D());
			em.addComponent(walls[i], new BounceSurface());
			em.addComponent(walls[i], new Render());
		}
		
		em.getComponent(walls[0], Position2D.class).setPosition(0, 0);
		em.getComponent(walls[0], Size2D.class).setSize(319,1);
		em.getComponent(walls[0], BounceSurface.class).yAxis = true;
		em.getComponent(walls[1], Position2D.class).setPosition(319, 0);
		em.getComponent(walls[1], Size2D.class).setSize(1,239);
		em.getComponent(walls[1], BounceSurface.class).xAxis = true;
		em.getComponent(walls[2], Position2D.class).setPosition(1, 239);
		em.getComponent(walls[2], Size2D.class).setSize(319,1);
		em.getComponent(walls[2], BounceSurface.class).yAxis = true;
		em.getComponent(walls[3], Position2D.class).setPosition(0, 1);
		em.getComponent(walls[3], Size2D.class).setSize(1,239);
		em.getComponent(walls[3], BounceSurface.class).xAxis = true;
		
		systems.add(new Render2DSystem());
		systems.add(new BouncingBoxGenerator());
		systems.add(new Collision2DSystem());
		systems.add(new BounceSurfaceHandler());
		systems.add(new SimpleMoveSystem());
		
	}
	
	public void update() {
		super.update();
		
	}
}
