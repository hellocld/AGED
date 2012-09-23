package com.hellocld.AGED.basicComponents;

import com.hellocld.AGED.core.Component;

/**
 * Tells the Render System to draw this entity
 * You can also turn it on or off via the "on" boolean, adjust color of the entity
 * @author CLD
 *
 */
public class Render implements Component {
	public boolean on = true;
	public float[] color = {1, 1, 1, 1};
	
	/**
	 * Set the color of the sprite rect (RGBA)
	 * @param r	Red
	 * @param g	Green
	 * @param b	Blue
	 * @param a	Alpha
	 */
	public void setColor(float r, float g, float b, float a) {
		color[0] = r;
		color[1] = g;
		color[2] = b;
		color[3] = a;
		
	}
	
	/**
	 * Enable rendering of the sprite
	 */
	public void turnOn() {
		on = true;
	}
	
	/**
	 * Disable rendering of the sprite
	 */
	public void turnOff() {
		on = false;
	}
}
