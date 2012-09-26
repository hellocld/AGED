/**
 * 
 */
package com.hellocld.AGED.basicComponents;

import com.hellocld.AGED.core.Component;

/**
 * A basic physics component that can be used by any of the physics systems in AGED
 * @author CLD
 *
 */
public class PhysicsObject2D implements Component {
	//define the position of the object (top left)
	float x, y;
	//define the size of the object
	float width, height;
	//the offset of the physics object (useful for making objects smaller/larger than their sprites)
	float offsetX, offsetY;
	//the mass of the object
	float mass;
	//the velocity of the object
	float xVel, yVel;
	//an idenifier/label
	String label;
	
	/**
	 * Define the parameters for the Physics Object
	 * @param x			X position (top left)
	 * @param y			Y position (top left)
	 * @param width		Width
	 * @param height	Height
	 * @param offsetX	X offset of the object
	 * @param offsetY	Y offset of the object
	 * @param mass		Mass
	 * @param xVel		X velocity
	 * @param yVel		Y velocity
	 * @param label		A basic identifier for the object
	 */
	public PhysicsObject2D(float x, float y, float width, float height, float xVel, float yVel) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.offsetX = 0;
		this.offsetY = 0;
		this.mass = 0;
		this.xVel = xVel;
		this.yVel = yVel;
		this.label = "";
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getMass() {
		return mass;
	}
	
	public float getXvel() {
		return xVel;
	}
	
	public float getYvel() {
		return yVel;
	}
	
	public float getXoffset() {
		return offsetX;
	}
	
	public float getYoffset() {
		return offsetY;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public void setOffset(float x, float y) {
		this.offsetX = x;
		this.offsetY = y;
	}
	
	public void setVelocity(float xVel, float yVel) {
		this.xVel = xVel;
		this.yVel = yVel;
	}
	
	public void setMass(float mass) {
		this.mass = mass;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
}
