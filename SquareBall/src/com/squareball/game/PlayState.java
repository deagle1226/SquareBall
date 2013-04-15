package com.squareball.game;

import java.util.ArrayList;

import net.java.games.input.Controller;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
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

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		
		Map m = new Map();
		entities.add(m);
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
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		super.enter(gc, sbg);
		ScoreState.clear();
		sbg.getState(2).init(gc, sbg);
		entities.clear();
		removeList.clear();
		addList.clear();
		
		Map m = new Map();
		entities.add(m);
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
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		for (Entity e : entities){
			e.render(g);
		}
		//g.drawString("1: " + ScoreState.score1 + "  2: " + ScoreState.score2, GameWindow.WINDOW_WIDTH/2, 10);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if (ScoreState.score1 >= ScoreState.maxScore || ScoreState.score2 >= ScoreState.maxScore){
			sbg.enterState(2);
		}
		
		for (int i=0;i<entities.size();i++) {
			Entity entity = (Entity) entities.get(i);
			
			for (int j=i+1;j<entities.size();j++) {
				Entity other = (Entity) entities.get(j);
				
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

}
