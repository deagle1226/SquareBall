package com.squareball.game;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StatsState extends BasicGameState {
	
	public static int[] steals = {0,0,0,0};
	public static int[] goal_time = {0,0,0,0};
	public static int[] ball_time = {0,0,0,0};
	public static int[] tosses = {0,0,0,0};

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		steals = new int[]{0,0,0,0};
		goal_time = new int[]{0,0,0,0};
		ball_time = new int[]{0,0,0,0};
		tosses = new int[]{0,0,0,0};
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(Color.lightGray);
		g.fill(new Rectangle(0,0,GameWindow.WINDOW_WIDTH, GameWindow.WINDOW_HEIGHT));
		g.setColor(Color.black);
		if (ScoreState.score1 > ScoreState.score2){
			g.drawString("Blue Team Wins", 10, 50);
			g.drawString("Blue: Shit Tons" + "  Red: " + ScoreState.score2, 10, 70);
		} else {
			g.drawString("Red Team Wins", 10, 50);
			g.drawString("Blue: " + ScoreState.score1 + "  Red: Shit Tons", 10, 70);
		}
		
		for (int i = 0; i < 4; i++){
			g.drawString("p" + (i+1) + "  ball time: " + ball_time[i] +
					"  goal time: " + goal_time[i] + 
					"  steals: " + steals[i] +
					"  throws: " + tosses[i], 10, 100 + (20*i));
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if (gc.getInput().isKeyPressed(Keyboard.KEY_RETURN)){
			sbg.enterState(0);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
	}

}
