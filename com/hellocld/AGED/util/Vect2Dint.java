/**
 * 
 */
package com.hellocld.AGED.util;

/**
 * A basic 2D vector class used to pass x and y (or width and height) variables between components
 * This particular version is for Integer vectors only
 * @author CLD
 * 
 */
public class Vect2Dint {
	public int x;
	public int y;
	
	/**
	 * Creates a new Vect2Dint object
	 * @param x	the X value (position or width)
	 * @param y	the Y value (position or height)
	 */
	public Vect2Dint(int x, int y){
		this.x = x;
		this.y = y;
	}
}
