/**
 * 
 */
package com.hellocld.AGED.basicComponents;

import com.hellocld.AGED.core.Component;

/**
 * A two-dimensional velocity component
 * @author CLD
 *
 */
public class Velocity2D implements Component {
	public float xVel = 0;
	public float yVel = 0;
	
	
	/**
	 * An easy way to set the x and y velocity values
	 * @param xVel	The x axis velocity
	 * @param yVel	The y axis velocity
	 */
	public void setVelocity(float xVel, float yVel) {
		this.xVel = xVel;
		this.yVel = yVel;
	}
}
