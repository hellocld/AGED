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
	
	public String printPosition()
	{
		return "("+x+", "+y+")";
	}
}
