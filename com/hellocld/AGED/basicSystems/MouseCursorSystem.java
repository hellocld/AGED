/**
 * 
 */
package com.hellocld.AGED.basicSystems;

import static com.hellocld.AGED.util.AGEDmethods.remap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.lwjgl.input.Mouse;

import com.hellocld.AGED.basicComponents.MouseCursor;
import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.core.ASystem;
import com.hellocld.AGED.core.EntityManager;
import com.hellocld.AGED.core.Game;

/**
 * Handles the mouse input from lwjgl and any entity with the MouseCursor component
 * @author CLD
 *
 */
public class MouseCursorSystem implements ASystem {
	public Set<Integer> cursors = new HashSet<Integer>();
	public Iterator<Integer> cursorsIter;
	public int cursor;
	/* (non-Javadoc)
	 * @see com.hellocld.AGED.core.ASystem#execute(com.hellocld.AGED.core.EntityManager)
	 */
	@Override
	public void execute(Game game, EntityManager em) {
		
		
		cursors = em.getAllEntitiesPossessingComponent(MouseCursor.class);
		for(cursorsIter = cursors.iterator(); cursorsIter.hasNext();) {
			cursor = cursorsIter.next();
			em.getComponent(cursor, Position2D.class).setPosition(Mouse.getX(), remap(Mouse.getY(), 0, game.height-1, game.height-1, 0));
			em.getComponent(cursor, MouseCursor.class).leftButton = Mouse.isButtonDown(0);
			em.getComponent(cursor, MouseCursor.class).rightButton = Mouse.isButtonDown(1);
		}

	}

}
