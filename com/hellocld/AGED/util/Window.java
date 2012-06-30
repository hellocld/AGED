/**
 * 
 */
package com.hellocld.AGED.util;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.LWJGLException;

/**
 * A basic Window for showing stuff. Similar to LWJGL's Display class, but a little nicer to use
 * @author CLD
 * 
 */
public class Window {
	int width;
	int height;
	int fps;
	
	/**
	 * Sets up the Window.
	 * 
	 * @param width		The width of the window in pixels
	 * @param height	The height of the window in pixels
	 * @param fps		The sync rate in frames per second
	 */
	public Window(int width, int height, int fps){
		this.width = width;
		this.height = height;
		this.fps = fps;
	}
	
	/**
	 * Creates the Window
	 */
	public void create(){
		try{
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//also set up the camera, because hey, what the hell
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}
	
	/**
	 * Returns 'true' if 'Close Window' button has been clicked
	 * @return	
	 */
	public boolean isCloseRequested(){
		return Display.isCloseRequested();
	}
	
	/**
	 * Updates and syncs the Window
	 */
	public void update(){
		Display.sync(fps);
		Display.update();
	}
	
	/**
	 * Destroys the Window
	 */
	public void destroy(){
		Display.destroy();
	}
	
	/**
	 * Changes the max frames per second
	 * @param sync
	 */
	public void setSync(int sync){
		fps = sync;
	}
	
	/**
	 * Clears the Window (color and depth)
	 */
	public void clear(){
		glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
	}
}
