/**
 * 
 */
package com.hellocld.AGED.util;

/**
 * A class full of various useful methods. Import statically for easiest use.
 * @author CLD
 *
 */
public class AGEDmethods {
	
	/**
	 * Remaps a value from one range to another.
	 * @param value	The value to remap
	 * @param min1	The current minimum value
	 * @param max1	The current maximum value
	 * @param min2	The proposed minimum value
	 * @param max2	The proposed maximum value
	 * @return		The remapped value
	 */
	public static float remap(float value, float min1, float max1, float min2, float max2) {
		return (min2 + (value - min1) * (max2 - min2)/(max1 - min1));
	}
}
