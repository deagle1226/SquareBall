package com.squareball.game;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.squareball.game.entity.Map;
import com.squareball.game.gui.GameClock;

public class StatsState extends BasicGameState {
	
	public static int[] steals = {0,0,0,0};
	public static int[] goal_time = {0,0,0,0};
	public static int[] ball_time = {0,0,0,0};
	public static int[] tosses = {0,0,0,0};
	public static int[] hustles = {0,0,0,0};
	public static int[] interceptions = {0,0,0,0};
	public static int[] catches = {0,0,0,0};
	public static int[] points = {0,0,0,0};
	public static long matchTime = 0;
	
	private UnicodeFont font18;
	private UnicodeFont font32;
	private Map map;
	
	private Music daisuke;
	private GameClock clock;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		daisuke = LoadingGameState.music.get(3);
		steals = new int[]{0,0,0,0};
		goal_time = new int[]{0,0,0,0};
		ball_time = new int[]{0,0,0,0};
		tosses = new int[]{0,0,0,0};
		hustles = new int[]{0,0,0,0};
		interceptions = new int[]{0,0,0,0};
		catches = new int[]{0,0,0,0};
		points = new int[]{0,0,0,0};
		matchTime = 0;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		font18 = LoadingGameState.font.get(LoadingGameState.font16);
		font32 = LoadingGameState.font.get(LoadingGameState.font32);
		
		map = new Map();
		super.enter(container, game);
		daisuke.loop();
		clock = new GameClock(font32);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		map.render(g);
		drawBoxes(g);
		drawStats(g);
		drawScore(g);
		clock.render(g);
	}
	
	public String min_sec(int time){
		String str = "";
		int remainder = time;
		int mins = remainder / (60*60);
		remainder = remainder - mins * 60;
		int secs = remainder;
		secs = time/60;
		mins = secs/60;
		secs = secs - mins*60;
		str += mins + ":";
		if (secs < 10) str += "0";
		str += secs;
		float millis = time - ((secs*60 + mins*60*60))/60f;
		str += "." + millis;
		return str.substring(0, str.length()-2);
	}
	
	public void drawStats(Graphics g){
		float padding = 10;
		float width = GameWindow.WINDOW_WIDTH/6-padding;
		float offset = GameWindow.WINDOW_WIDTH/6;
		float lineHeight = font32.getLineHeight();
		g.setColor(Color.white);
		for (int i = 0; i < 4; i++) {
			float x = offset + (i*(width+padding))+padding;
			float y = GameWindow.WINDOW_HEIGHT/4 + padding;
			font32.drawString(x, y, "Player " + (i+1), Color.white);
			font18.drawString(x, y+lineHeight, "Steals: " + steals[i], Color.white);
			font18.drawString(x, y+lineHeight+font18.getLineHeight(), "Throws: " + tosses[i], Color.white);
			font18.drawString(x, y+lineHeight+font18.getLineHeight()*2, "Possession: " + min_sec(ball_time[i]), Color.white);
			font18.drawString(x, y+lineHeight+font18.getLineHeight()*3, "Hustlin' " + hustles[i] + " times a game", Color.white);
			font18.drawString(x, y+lineHeight+font18.getLineHeight()*4, "Interceptions: " + interceptions[i], Color.white);
			font18.drawString(x, y+lineHeight+font18.getLineHeight()*5, "Completed Passes: " + catches[i], Color.white);
			//font18.drawString(x, y+lineHeight+font18.getLineHeight()*6, "Points: " + points[i], Color.white);
		}
	}
	
	public void drawScore(Graphics g){
		Color color = Color.blue;
		float padding  = 10;
		Rectangle shape = new Rectangle(GameWindow.WINDOW_WIDTH/4, GameWindow.WINDOW_HEIGHT/12, GameWindow.WINDOW_WIDTH/5, GameWindow.WINDOW_HEIGHT/8);
		g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()/2));
		g.fill(shape);
		g.setColor(color);
		g.fill(new Rectangle(shape.getX() + padding, shape.getY()+ padding, (shape.getWidth()-(2*padding)) * ((float)ScoreState.score1/(float)ScoreState.maxScore), shape.getHeight()-2*padding));
		g.draw(shape);
		font32.drawString(shape.getCenterX()-font32.getWidth(ScoreState.score1+"")/2, shape.getCenterY()-font32.getLineHeight()/2, ScoreState.score1+"", Color.white);
		color = Color.red;
		shape = new Rectangle(GameWindow.WINDOW_WIDTH-(GameWindow.WINDOW_WIDTH/4 + GameWindow.WINDOW_WIDTH/5), GameWindow.WINDOW_HEIGHT/12, GameWindow.WINDOW_WIDTH/5, GameWindow.WINDOW_HEIGHT/8);
		g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()/2));
		g.fill(shape);
		g.setColor(color);
		g.fill(new Rectangle(shape.getX() + padding, shape.getY()+ padding, (shape.getWidth()-(2*padding)) * ((float)ScoreState.score2/(float)ScoreState.maxScore), shape.getHeight()-2*padding));
		g.draw(shape);
		font32.drawString(shape.getCenterX()-font32.getWidth(ScoreState.score2+"")/2, shape.getCenterY()-font32.getLineHeight()/2, ScoreState.score2+"", Color.white);
	}
	
	public void drawBoxes(Graphics g){
		float padding = 10;
		float width = GameWindow.WINDOW_WIDTH/6-padding;
		float offset = GameWindow.WINDOW_WIDTH/6;
		for (int i = 0; i < 4; i++) {
			float x = offset + (i*(width+padding));
			float y = GameWindow.WINDOW_HEIGHT/4;
			Rectangle shape = new Rectangle(x, y, width, GameWindow.WINDOW_HEIGHT/2);
			Color color = Color.red;
			if (i % 2 == 0) color = Color.blue;
			g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()/2));
			g.fill(shape);
			g.setColor(color);
			g.draw(shape);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if (gc.getInput().isKeyPressed(Keyboard.KEY_RETURN) || gc.getInput().isKeyPressed(Keyboard.KEY_ESCAPE)){
			sbg.enterState(0);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
	}

}
