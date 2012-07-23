/**
 * 
 */
package com.hellocld.AGED.Toolkit;

import com.hellocld.AGED.basicComponents.Collision2D;
import com.hellocld.AGED.basicComponents.MouseCursor;
import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.Render;
import com.hellocld.AGED.basicComponents.Size2D;
import com.hellocld.AGED.core.GameState;

/**
 * A basic mouse cursor entity
 * 
 * @author CLD
 *
 */
public class AGEDmouse {
	public int id;
	
	/**
	 * Creates a basic mouse cursor entity
	 * @param gs		The game state the cursor is being made in (usually 'this')
	 * @param width		The width of the cursor
	 * @param height	The height of the cursor
	 * @return			The cursor's EntityManager ID
	 */
	public int create(GameState gs, float width, float height) {
		id = gs.em.createEntity();
		gs.em.addComponent(id, new Position2D());
		gs.em.addComponent(id, new Size2D());
		gs.em.addComponent(id, new MouseCursor());
		gs.em.addComponent(id, new Collision2D());
		gs.em.addComponent(id, new Render());
		gs.em.getComponent(id, Position2D.class).setPosition(0, 0);
		gs.em.getComponent(id, Size2D.class).setSize(width, height);
		return id;
	}
}
