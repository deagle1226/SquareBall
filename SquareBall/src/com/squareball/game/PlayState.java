package com.squareball.game;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.squareball.game.entity.Ball;
import com.squareball.game.entity.Entity;
import com.squareball.game.entity.EntityManager;
import com.squareball.game.entity.Goal;
import com.squareball.game.entity.Map;
import com.squareball.game.entity.Player;
import com.squareball.game.gui.GameClock;

public class PlayState extends BasicGameState implements EntityManager {
	
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	/** The list of entities to be added at the next opportunity */
	private ArrayList<Entity> addList = new ArrayList<Entity>();
	/** The list of entities to be removed at the next opportunity */
	private ArrayList<Entity> removeList = new ArrayList<Entity>();
	
	private boolean paused = false;
	private int pausedTime = 0;
	private int countDown = GameSettings.countDown;
	private UnicodeFont font64;
	
	private Sound ballCatch;
	private Sound ballIntercept;
	private Sound whoosh;
	private Sound point1;
	private Sound point2;
	
	private Music hydrogen;
	private Music hiphop;
	private Music current;
	
	GameContainer container;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		StatsState.matchTime = 0;
		
		font64 = LoadingGameState.font.get(LoadingGameState.font64);
		hiphop = LoadingGameState.music.get(0);
		hydrogen = LoadingGameState.music.get(1);
		ballCatch = LoadingGameState.sound.get(0);
		ballIntercept = LoadingGameState.sound.get(1);
		whoosh = LoadingGameState.sound.get(2);
		point1 = LoadingGameState.sound.get(3);
		point2 = LoadingGameState.sound.get(4);
		container = gc;
		
		super.enter(gc, sbg);
		
		current = hydrogen;
		current.play();
		
		ScoreState.clear();
		sbg.getState(2).init(gc, sbg);
		
		entities.clear();
		removeList.clear();
		addList.clear();
		
		entities.add(new Map());
		entities.add(new GameClock(font64));
		entities.add(new Goal(true));
		entities.add(new Goal(false));
		
		int n = 0;
		for (int i = 0; i < gc.getInput().getControllerCount(); i++){
			if (gc.getInput().getAxisCount(i) == 5 && n < 4){
				Player player = new Player(i, n%2, n);
				entities.add(player);
				n++;
			}
		}
		entities.add(new Ball(true));
		
		paused = false;
		pausedTime = 0;
		countDown = GameSettings.countDown;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		//m.render(g);
		font64.drawString(GameWindow.WINDOW_WIDTH/2 - font64.getWidth(timeFormat(0))/2,10, timeFormat(StatsState.matchTime), Color.lightGray);
		for (Entity e : entities){
			e.render(g);
		}
		if (countDown > 0){
			String count = "3";
			if ((countDown+1-30)%60==0) {
				if (countDown == 29){
					ballCatch(1f);
				} else {
					
					ballInterception(1f);
				}
			}
			if (countDown < 150) count = "2";
			if (countDown < 90) count = "1";
			if (countDown < 30) count = "GO!";
			g.setColor(new Color(0,0,0,0.3f));
			g.fill(new Rectangle(0,0,GameWindow.WINDOW_WIDTH, GameWindow.WINDOW_HEIGHT));
			font64.drawString(GameWindow.WINDOW_WIDTH/2 - font64.getWidth(count)/2, GameWindow.WINDOW_HEIGHT/2 - font64.getLineHeight()/2, count, Color.white);
		}
		if (paused){
			g.setColor(new Color(0,0,0,0.3f));
			g.fill(new Rectangle(0,0,GameWindow.WINDOW_WIDTH, GameWindow.WINDOW_HEIGHT));
			font64.drawString(GameWindow.WINDOW_WIDTH/2 - font64.getWidth("PAUSED")/2, GameWindow.WINDOW_HEIGHT/2 - font64.getLineHeight()/2, "PAUSED", Color.white);
		}
		
	}
	
	public String timeFormat(long millis){
		String str = "";
		long minutes = millis/1000/60;
		long seconds = millis/1000 - (minutes*60);
		str += minutes + ":";
		if (seconds < 10) str += "0";
		str += seconds;
		str += "." + ((millis/100) - (seconds*10));
		return str;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		countDown--;
		if (countDown < 0){
			pausedTime++;
			if (!paused){
				StatsState.matchTime+=delta;
				if ((gc.getInput().isKeyPressed(Keyboard.KEY_SPACE) || gc.getInput().isButtonPressed(7, gc.getInput().ANY_CONTROLLER)) && pausedTime > 20){
					paused = true;
					pausedTime = 0;
				}
				if (ScoreState.score1 >= ScoreState.maxScore || ScoreState.score2 >= ScoreState.maxScore){
					sbg.enterState(2);
				}
				
				for (int i=0;i<entities.size();i++) {
					Entity entity = entities.get(i);
					
					for (int j=i+1;j<entities.size();j++) {
						Entity other = entities.get(j);
						if (entity.intersects(other)) {
							entity.collide(gc, this, other);
							other.collide(gc, this, entity);
						}
					}
				}
				entities.removeAll(removeList);
				entities.addAll(addList);
				
				removeList.clear();
				addList.clear();
				
				for (Entity e : entities) {
					e.update(gc, this, delta);
				}
			} else {
				if ((gc.getInput().isKeyPressed(Keyboard.KEY_SPACE) || gc.getInput().isButtonPressed(7, gc.getInput().ANY_CONTROLLER)) && pausedTime > 20){
					paused = false;
					pausedTime = 0;
				} else if (gc.getInput().isKeyPressed(Keyboard.KEY_ESCAPE)){
					sbg.enterState(0);
				}
			}
		}
		if (!current.playing() || gc.getInput().isKeyPressed(Keyboard.KEY_RIGHT)){
			if (current.equals(hiphop)) current = hydrogen;
			else if (current.equals(hydrogen)) current = hiphop;
			current.play();
		}
		if (gc.getInput().isKeyPressed(Keyboard.KEY_M)){
			if (current.playing()) current.pause();
			else current.resume();
		}
	}

	@Override
	public int getID() {
		return 1;
	}

	@Override
	public void removeEntity(Entity entity) {
		removeList.add(entity);
	}

	@Override
	public void addEntity(Entity entity) {
		addList.add(entity);
	}

	@Override
	public void ballCatch(float volume) {
		container.setSoundVolume(volume);
		ballCatch.play();
	}

	@Override
	public void ballInterception(float volume) {
		container.setSoundVolume(volume);
		ballIntercept.play();
	}
	
	@Override
	public void hustle() {
		container.setSoundVolume(0.3f);
		whoosh.play();
	}

	@Override
	public void point(boolean endgame) {
		if (endgame){
			container.setSoundVolume(0.6f);
			point2.play();
		} else {
			container.setSoundVolume(0.3f);
			point1.play();
		}
	}

}
