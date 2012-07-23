/**
 * 
 */
package com.hellocld.AGED.basicSystems;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.Iterator;
import java.util.Set;

import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.Render;
import com.hellocld.AGED.basicComponents.Size2D;
import com.hellocld.AGED.core.ASystem;
import com.hellocld.AGED.core.EntityManager;
import com.hellocld.AGED.core.Game;


/**
 * A very basic 2D Rendering System
 * @author CLD
 *
 */
public class Render2DSystem implements ASystem {
	
	//all the temporary variables used in each cycle
	Set<Integer> renderSet;
	Iterator<Integer> renderIter;
	float x, y, w, h;
	float[] tempColor = new float[4];
	int entity;
	
	/* (non-Javadoc)
	 * @see com.hellocld.AGED.core.System#execute()
	 */
	@Override
	public void execute(Game game, EntityManager em) {
		//clear all buffers
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		//get all the renderable Entities
		renderSet = em.getAllEntitiesPossessingComponent(Render.class);
		
		//loop through all the renderable entities
		for(renderIter = renderSet.iterator(); renderIter.hasNext();) {
			//double check just to make sure rendering is turned on; if it isn't, the entity is skipped
			entity = renderIter.next();
			if(em.getComponent(entity, Render.class).on) {
				//gather all the necessary info from the components for rendering
				x = em.getComponent(entity, Position2D.class).x;
				y = em.getComponent(entity, Position2D.class).y;
				w = em.getComponent(entity, Size2D.class).width;
				h = em.getComponent(entity, Size2D.class).height;
				
				//set the color of the quad
				tempColor = em.getComponent(entity, Render.class).color;
				glColor4f(tempColor[0], tempColor[1], tempColor[2], tempColor[3]);
				
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
