/**
 * 
 */
package com.hellocld.tests.GameTest.Components;

import com.hellocld.AGED.core.Component;

/**
 * Since input handling is going to vary from game to game, I'm keeping the input component and
 * system outside of AGED's basic component set. It's a little more work on the coder, but it also
 * means more freedom for the designer. Eventually there will be some standard input handlers 
 * in basicComponents and basicSystems though.
 * @author CLD
 *
 */
public class PlayerInput implements Component {
	//is the object currently accepting input?
	public boolean controllable = true;
}
