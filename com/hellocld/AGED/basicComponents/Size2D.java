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
	
	/**
	 * Easy way to configure the width and height settings of the component
	 * @param width		Width
	 * @param height	Height
	 */
	public void setSize(float width, float height)
	{
		this.width = width;
		this.height = height;
	}
}
