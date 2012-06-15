/**
 * 
 */
package com.hellocld.AGED.component;

import com.hellocld.AGED.util.Vect2Dint;
/**
 * A position component. Keeps track of where something is in X, Y space. Integer resolution (good for pixel specific values)
 * @author CLD
 *
 */
public class ComPosition2Dint {
	Vect2Dint position;
	
	public ComPosition2Dint(int x, int y){
		position.x = x;
		position.y = y;
	}
	
	public Vect2Dint get(){
		return position;
	}
	public void set(int x, int y){
		position.x = x;
		position.y = y;
	}
}
