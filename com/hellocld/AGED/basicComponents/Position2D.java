/**
 * 
 */
package com.hellocld.AGED.basicComponents;

import com.hellocld.AGED.core.Component;

/**
 * A basic 2D position component
 * @author CLD
 *
 */
public class Position2D implements Component {
	public float x, y;
	
	/**
	 * A simple way to configure the x and y positions of the component
	 * @param x	Position on the x axis
	 * @param y	Position on the y axis
	 */
	
	
	public Position2D(){}
	
	/**
	 * Declare the position of the component
	 * @param x	X position
	 * @param y	Y position
	 */
	public Position2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
}
