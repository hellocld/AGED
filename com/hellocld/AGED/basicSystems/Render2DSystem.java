/**
 * 
 */
package com.hellocld.AGED.basicSystems;

import java.util.Iterator;
import java.util.Set;

import static org.lwjgl.opengl.GL11.*;

import com.hellocld.AGED.basicComponents.*;
import com.hellocld.AGED.basicComponents.Render;
import com.hellocld.AGED.core.EntityManager;
import com.hellocld.AGED.core.ASystem;


/**
 * A very basic 2D Rendering System
 * @author CLD
 *
 */
public class Render2DSystem implements ASystem {

	/* (non-Javadoc)
	 * @see com.hellocld.AGED.core.System#execute()
	 */
	@Override
	public void execute(EntityManager em) {
		//clear all buffers
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		//get all the renderable Entities
		Set<Integer> renderSet = em.getAllEntitiesPossessingComponent(Render.class);
		
		if(!renderSet.isEmpty()) {
			//loop through all the renderable entities
			for(Iterator<Integer> renderIter = renderSet.iterator(); renderIter.hasNext();) {
				//double check just to make sure rendering is turned on; if it isn't, the entity is skipped
				int entity = renderIter.next();
				if(em.getComponent(entity, Render.class).on) {
					//gather all the necessary info from the components for rendering
					float x = em.getComponent(entity, Position2D.class).x;
					float y = em.getComponent(entity, Position2D.class).y;
					float w = em.getComponent(entity, Size2D.class).width;
					float h = em.getComponent(entity, Size2D.class).height;
					
					//set the color of the quad
					glColor3f(1.0f, 1.0f, 1.0f);
					
					//draw the quad!
					glBegin(GL_QUADS);
						glVertex2f(x,y);
						glVertex2f(x+w, y);
						glVertex2f(x+w, y+h);
						glVertex2f(x, y+h);
					glEnd();
				}
			}
		}	
	}

}
