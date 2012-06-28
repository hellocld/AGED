/**
 * 
 */
package com.hellocld.tests;

//import the fun stuff!
import com.hellocld.AGED.core.*;
import com.hellocld.AGED.basicComponents.*;
/**
 * A little testing bed for AGED, make sure everything kinda sorta works
 * @author CLD
 *
 */
public class AGEDtest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManager em = new EntityManager();
		
		int testEntity = em.createEntity();
		em.addComponent(testEntity, new Position2D());
		em.getComponent(testEntity, Position2D.class).x = 10;
		em.getComponent(testEntity, Position2D.class).y = 20;
		
		String tempString = em.getComponent(testEntity, Position2D.class).printPosition();
		
		System.out.println("Your entity is at: "+ tempString);
		
	}

}
