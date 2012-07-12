/**
 * 
 */
package com.hellocld.AGEDpong;

import com.hellocld.AGED.basicComponents.Collision2D;
import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.Render;
import com.hellocld.AGED.basicComponents.Size2D;
import com.hellocld.AGED.basicComponents.Velocity2D;
import com.hellocld.AGED.core.GameState;

/**
 * SinglePlayerPong GameState (or, 1 Player Pong)
 * 
 * @author CLD
 *
 */
public class SinglePlayerPong extends GameState {
	public void create() {
		super.create();
		
		//let's make some entities!
		int playerPaddle = em.createEntity();
		int aiPaddle = em.createEntity();
		int ball = em.createEntity();
		int topWall = em.createEntity();
		int bottomWall = em.createEntity();
		int playerScoreZone = em.createEntity();
		int aiScoreZone = em.createEntity();
		
		//we'll set up the playerPaddle first
		em.addComponent(playerPaddle, new Position2D());
		em.addComponent(playerPaddle, new Size2D());
		em.addComponent(playerPaddle, new PlayerControl());		//need to create this component!
		em.addComponent(playerPaddle, new Velocity2D());
		em.addComponent(playerPaddle, new Collision2D());
		em.addComponent(playerPaddle, new Render());
		em.addComponent(playerPaddle, new Pause());				//need to create this component!
		em.getComponent(playerPaddle, Position2D.class).setPosition(5, 105);
		em.getComponent(playerPaddle, Size2D.class).setSize(10, 30);
		
		//next we'll build the aiPaddle
		em.addComponent(aiPaddle, new Position2D());
		em.addComponent(aiPaddle, new Size2D());
		em.addComponent(aiPaddle, new AIControl());			//need to create this component!
		em.addComponent(aiPaddle, new Velocity2D());
		em.addComponent(aiPaddle, new Collision2D());
		em.addComponent(aiPaddle, new Render());
		em.addComponent(aiPaddle, new Pause());				//need to create this component!
		em.getComponent(aiPaddle, Position2D.class).setPosition(5, 105);
		em.getComponent(aiPaddle, Size2D.class).setSize(10, 30);
		
	}
	
	public void update() {
		super.update();
	}
}