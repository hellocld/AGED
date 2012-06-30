/**
 * 
 */
package com.hellocld.AGED.basicComponents;

import com.hellocld.AGED.core.Component;

/**
 * 2D dimensions of an object
 * @author CLD
 *
 */
public class Size2D implements Component {
	public float width, height;
	
	public String printSize()
	{
		return "("+width+", "+height+")";
	}
}
