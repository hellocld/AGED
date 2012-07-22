/**
 * 
 */
package com.hellocld.AGED.util;

/**
 * A simple Pair type for storing two values that are arbitrarily related
 * @author CLD
 *
 */
public class Pair<L,R> {
	public final L left;
	public final R right;
	
	public Pair(L left, R right) {
		this.left = left;
		this.right = right;
	}
	
	public L getLeft() {
		return left;
	}
	
	public R getRight() {
		return right;
	}
	
}
