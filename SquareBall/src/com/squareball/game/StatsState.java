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

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		steals = new int[]{0,0,0,0};
		goal_time = new int[]{0,0,0,0};
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(Color.lightGray);
		g.fill(new Rectangle(0,0,GameWindow.WINDOW_WIDTH, GameWindow.WINDOW_HEIGHT));
		g.setColor(Color.black);
		if (ScoreState.score1 > ScoreState.score2){
			g.drawString("Blue Team Wins", 10, 50);
		} else {
			g.drawString("Red Team Wins", 10, 50);
		}
		g.drawString("Blue: " + ScoreState.score1 + "  Red: " + ScoreState.score2, 10, 70);
		
		g.drawString("p1 goal time: " + goal_time[0] + "  steals: " + steals[0], 10, 100);
		g.drawString("p2 goal time: " + goal_time[1] + "  steals: " + steals[1], 10, 120);
		g.drawString("p3 goal time: " + goal_time[2] + "  steals: " + steals[2], 10, 140);
		g.drawString("p4 goal time: " + goal_time[3] + "  steals: " + steals[3], 10, 160);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if (gc.getInput().isKeyPressed(Keyboard.KEY_RETURN)){
			sbg.enterState(0);
		}
//		for (int i = 0; i < 6; i++){
//			if (gc.getInput().isButtonPressed(0, i)){
//				sbg.enterState(0);
//			}
//		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
	}

}
