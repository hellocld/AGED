/**
 * 
 */
package com.hellocld.AGEDpong;

import com.hellocld.AGED.basicComponents.CollideSet;
import com.hellocld.AGED.basicComponents.Collision2D;
import com.hellocld.AGED.basicComponents.EntityGroup;
import com.hellocld.AGED.basicComponents.Position2D;
import com.hellocld.AGED.basicComponents.Render;
import com.hellocld.AGED.basicComponents.Size2D;
import com.hellocld.AGED.basicComponents.Velocity2D;
import com.hellocld.AGED.basicSystems.Collision2DSystem;
import com.hellocld.AGED.basicSystems.Render2DSystem;
import com.hellocld.AGED.core.GameState;
import com.hellocld.AGEDpong.ASystems.BallMoveSystem;
import com.hellocld.AGEDpong.ASystems.CollisionMoveSystem;
import com.hellocld.AGEDpong.ASystems.PlayerPaddleSystem;
import com.hellocld.AGEDpong.Components.BallMove;
import com.hellocld.AGEDpong.Components.PlayerMove;

/**
 * SinglePlayerPong GameState (or, 1 Player Pong)
 * 
 * @author CLD
 *
 */
public class SinglePlayerPong extends GameState {
	//the entities
	public int playerPaddle, ball, failZone;
	public int[] walls = new int[3];
	
	//and a counter for the score
	public int score;
	
	public void create() {
		super.create();
		
		//let's make some entities!
		playerPaddle = em.createEntity();
		ball = em.createEntity();
		
		
		//we'll set up the playerPaddle first
		em.addComponent(playerPaddle, new Position2D());
		em.addComponent(playerPaddle, new Size2D());
		em.addComponent(playerPaddle, new PlayerMove());		//need to create this component!
		em.addComponent(playerPaddle, new Velocity2D());
		em.addComponent(playerPaddle, new Collision2D());
		em.addComponent(playerPaddle, new Render());
		em.getComponent(playerPaddle, Position2D.class).setPosition(5, 105);
		em.getComponent(playerPaddle, Size2D.class).setSize(10, 30);
		
		
		//next we'll make the ball
		em.addComponent(ball, new Position2D());
		em.addComponent(ball, new Size2D());
		em.addComponent(ball, new Velocity2D());
		em.addComponent(ball, new Collision2D());
		em.addComponent(ball, new BallMove());					//need to create this component!
		em.addComponent(ball, new Render());
		em.getComponent(ball, Position2D.class).setPosition(155, 115);
		em.getComponent(ball, Size2D.class).setSize(10, 10);
		em.getComponent(ball, Velocity2D.class).setVelocity(3, 3);
		
		//create the fail zone
		failZone = em.createEntity();
		em.addComponent(failZone, new Position2D());
		em.addComponent(failZone, new Size2D());
		em.addComponent(failZone, new Collision2D());
		em.getComponent(failZone, Position2D.class).setPosition(-8, 0);
		em.getComponent(failZone, Size2D.class).setSize(8, game.height);
		
		//finally let's create the walls
		for(int i = 0; i<3; i++) {
			walls[i] = em.createEntity();
			em.addComponent(walls[i], new Position2D());
			em.addComponent(walls[i], new Size2D());
			em.addComponent(walls[i], new Collision2D());
			em.addComponent(walls[i], new Render());
		}
		em.getComponent(walls[0], Position2D.class).setPosition(0, 0);
		em.getComponent(walls[0], Size2D.class).setSize(game.width, 8);
		em.getComponent(walls[2], Position2D.class).setPosition(game.height - 8, 0);
		em.getComponent(walls[2], Size2D.class).setSize(game.width, 8);
		em.getComponent(walls[1], Position2D.class).setPosition(game.width - 8, 8);
		em.getComponent(walls[1], Size2D.class).setSize(8, game.height - 16);
		
		//build our groups and collision sets
		em.addComponent(utilityEntity, new EntityGroup());
		em.addComponent(utilityEntity, new CollideSet());
		
		em.getComponent(utilityEntity, EntityGroup.class).createGroup("paddle");
		em.getComponent(utilityEntity, EntityGroup.class).addToGroup("paddle", playerPaddle);
		
		em.getComponent(utilityEntity, EntityGroup.class).createGroup("ball");
		em.getComponent(utilityEntity, EntityGroup.class).addToGroup("ball", ball);
		
		em.getComponent(utilityEntity, EntityGroup.class).createGroup("failZone");
		em.getComponent(utilityEntity, EntityGroup.class).addToGroup("failZone", failZone);
		
		em.getComponent(utilityEntity, EntityGroup.class).createGroup("walls");
		for(int i = 0; i < 3; i++) {
			em.getComponent(utilityEntity, EntityGroup.class).addToGroup("walls", walls[i]);
		}
		
		em.getComponent(utilityEntity, CollideSet.class).addCheck("paddle", "ball");
		em.getComponent(utilityEntity, CollideSet.class).addCheck("paddle", "walls");
		em.getComponent(utilityEntity, CollideSet.class).addCheck("ball", "walls");
		em.getComponent(utilityEntity, CollideSet.class).addCheck("ball", "failZone");
		
		//add our systems
		systems.add(new Collision2DSystem(utilityEntity));
		systems.add(new PlayerPaddleSystem());
		systems.add(new CollisionMoveSystem());
		systems.add(new BallMoveSystem());
		systems.add(new Render2DSystem());
		
		//reset the ball and score to initialize the game
		gameReset();
		
	}
	
	public void update() {
		super.update();
	}
	
	public void gameReset() {
		em.getComponent(ball, Position2D.class).setPosition(155, 115);
		em.getComponent(ball, Size2D.class).setSize(10, 10);
		em.getComponent(ball, Velocity2D.class).setVelocity(3, 3);
		
		score = 0;
	}
}