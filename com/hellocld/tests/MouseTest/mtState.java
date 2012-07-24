/**
 * 
 */
package com.hellocld.tests.MouseTest;

import org.lwjgl.input.Mouse;

import com.hellocld.AGED.Toolkit.AGEDmouse;
import com.hellocld.AGED.basicComponents.CollideSet;
import com.hellocld.AGED.basicComponents.Collision2D;
import com.hellocld.AGED.basicComponents.EntityGroup;
import com.hellocld.AGED.basicComponents.MouseCursor;
import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.Render;
import com.hellocld.AGED.basicComponents.Size2D;
import com.hellocld.AGED.basicSystems.Collision2DSystem;
import com.hellocld.AGED.basicSystems.MouseCursorSystem;
import com.hellocld.AGED.basicSystems.Render2DSystem;
import com.hellocld.AGED.core.GameState;

/**
 * @author CLD
 *
 */
public class mtState extends GameState {
	public int mouse, entity;
	
	public void create() {
		super.create();
		//make sure the utilityEntity is ready to handle collisionSets and EntityGroups
		em.addComponent(utilityEntity, new EntityGroup());
		em.addComponent(utilityEntity, new CollideSet());
		
		//make an entity for the cursor to collide with
		entity = em.createEntity();
		
		em.addComponent(entity, new Position2D());
		em.addComponent(entity, new Size2D());
		em.addComponent(entity, new Collision2D());
		em.addComponent(entity, new Render());
		em.getComponent(entity, Position2D.class).setPosition(200,100);
		em.getComponent(entity, Size2D.class).setSize(64,32);
		
		//make our mouse cursor entity
		mouse = new AGEDmouse().create(this, 16, 16);
		
		//create the groups and collide sets
		em.getComponent(utilityEntity, EntityGroup.class).createGroup("mouse");
		em.getComponent(utilityEntity, EntityGroup.class).createGroup("box");
		em.getComponent(utilityEntity, EntityGroup.class).addToGroup("mouse", mouse);
		em.getComponent(utilityEntity, EntityGroup.class).addToGroup("box", entity);
		
		em.getComponent(utilityEntity, CollideSet.class).addCheck("mouse", "box");
		
		//add the systems
		systems.add(new Collision2DSystem(utilityEntity));
		systems.add(new MouseCursorSystem());
		systems.add(new Render2DSystem());
		
		//grab and hide the system mouse cursor
		//Mouse.setGrabbed(true);
	}
	
	public void update() {
		super.update();
		
		if(em.getComponent(mouse, MouseCursor.class).leftButton) {
			em.getComponent(mouse, Render.class).setColor(1, 0, 0, 1);
		} else if (em.getComponent(mouse, MouseCursor.class).rightButton) {
			em.getComponent(mouse, Render.class).setColor(0, 1, 0, 1);
		} else {
			em.getComponent(mouse, Render.class).setColor(1, 1, 1, 1);
		}
		
		if(em.getComponent(entity, Collision2D.class).collidingEntities.containsKey(mouse)) {
			em.getComponent(entity, Render.class).setColor(1, 1, 1, 1);
		} else {
			em.getComponent(entity, Render.class).setColor(0.5f, 0.5f, 0.5f, 1);
		}
	}
}
