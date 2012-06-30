/**
 * 
 */
package com.hellocld.AGED.core;

/**
 * All systems MUST use this interface
 * @author CLD
 *
 */
public interface ASystem {
	//This function is the reason each System must use this interface
	//Since each System needs to do something, it should have a standard function name for
	//running and doing stuff.
	//Also, each system is going to need to work with the EntityManager of it's GameState, so we
	//pass that to it during the execute function
	public void execute(EntityManager em);
}
