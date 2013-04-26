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
	Map m;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		font64 = new UnicodeFont("res/oswald.ttf", (int) (64*(GameWindow.WINDOW_HEIGHT/720f)) , false, false);
		font64.addAsciiGlyphs();
		font64.getEffects().add(new ColorEffect());
		font64.loadGlyphs();
		
		hiphop = new Music("res/music.ogg", false);
		hydrogen = new Music("res/Hydrogen.ogg", false);
		
		m = new Map();
		Goal g = new Goal(true);
		entities.add(g);
		g = new Goal(false);
		entities.add(g);
		
		int n = 0;
		for (int i = 0; i < gc.getInput().getControllerCount(); i++){
			if (gc.getInput().getAxisCount(i) == 5 && n < 4){
				Player player = new Player(i, n%2, n);
				entities.add(player);
				n++;
			}
		}
		
		Ball b = new Ball(true);
		entities.add(b);
		
		ballCatch = new Sound("res/catch.ogg");
		ballIntercept = new Sound("res/intercept.ogg");
		whoosh = new Sound("res/whoosh.ogg");
		point1 = new Sound("res/point1.ogg");
		point2 = new Sound("res/point2.ogg");
		container = gc;
		
		
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		StatsState.matchTime = 0;
		super.enter(gc, sbg);
		current = hydrogen;
		current.play();
		ScoreState.clear();
		sbg.getState(2).init(gc, sbg);
		entities.clear();
		removeList.clear();
		addList.clear();
		
		Goal g = new Goal(true);
		entities.add(g);
		g = new Goal(false);
		entities.add(g);
		
		int n = 0;
		for (int i = 0; i < gc.getInput().getControllerCount(); i++){
			if (gc.getInput().getAxisCount(i) == 5 && n < 4){
				Player player = new Player(i, n%2, n);
				entities.add(player);
				n++;
			}
		}
		
		Ball b = new Ball(true);
		entities.add(b);
		paused = false;
		pausedTime = 0;
		countDown = GameSettings.countDown;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		m.render(g);
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
				if (gc.getInput().isKeyPressed(Keyboard.KEY_SPACE) && pausedTime > 20){
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
				
				if (gc.getInput().isKeyPressed(Keyboard.KEY_SPACE) && pausedTime > 20){
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
