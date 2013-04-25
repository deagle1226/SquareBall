package com.squareball.game.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.squareball.game.GameSettings;
import com.squareball.game.GameWindow;
import com.squareball.game.StatsState;
import com.squareball.game.particles.ParticleManager;

public class Ball extends MobileEntity {
	
	public float friction = GameSettings.friction_map;
	private ParticleManager particles;
	private float size = GameSettings.ballSize;
	private Color color;
	public boolean caught = false;
	public Player player;
	private float rotation;
	private Player lastPossession;
	
	private int bounces = 0;
	
	public Ball(boolean up) {
		shape = new Rectangle(GameWindow.WINDOW_WIDTH/2-size/2, GameWindow.WINDOW_HEIGHT/2, size, size);
		vel = new Vector2f(0,GameSettings.jumpBallSpeed);
		if (up) vel = new Vector2f(0,-GameSettings.jumpBallSpeed);
		particles = new ParticleManager(size/4, 150f, 12, Color.black, false);
		color = Color.black;
		rotation = 0;
	}
	
	@Override
	public void update(GameContainer gc, EntityManager manager, int delta) {
		float prevX = vel.x;
		float prevY = vel.y;
		vel = vel.scale(friction);
		super.update(gc, manager, delta);
		
		if (caught){
			vel = new Vector2f(0,0);
			rotation = (rotation + delta/2)%360;
			Vector2f rot = new Vector2f(rotation).scale(GameWindow.WINDOW_WIDTH/30);
			shape.setLocation(shape.getX()+rot.x, shape.getY()+rot.y);
			particles.update(new Vector2f(shape.getX(), shape.getY()), delta);
			
		} else {
			particles.update(new Vector2f(shape.getCenterX(), shape.getCenterY()), delta);
		}
		if ((vel.x == -prevX || vel.x == -prevX*friction) && vel.x != 0){
			bounces++;
			manager.ballCatch(vel.length()/2f);
		}
		if ((vel.y == -prevY || vel.y == -prevY*friction) && vel.y != 0){
			bounces++;
			manager.ballCatch(vel.length()/5f + 0.5f);
		}
	}

	@Override
	public void render(Graphics graphics) {
		particles.render(graphics);
		
//		graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()/3));
//		graphics.fill(shape);
//		graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()/2));
//		graphics.draw(shape);
	}

	@Override
	public void collide(GameContainer gc, EntityManager manager, Entity other) {
		if (other instanceof Goal){
			friction = GameSettings.friction_goal;
		} else {
			friction = GameSettings.friction_map;
		}
	}
	
	public void grab(Player p, EntityManager manager){
		boolean interception = false;
		if (lastPossession != null){
			if (bounces < 3 && vel.length() > 1f){
				if (lastPossession.team != p.team){
					StatsState.interceptions[p.playerNumber]++;
					interception = true;
				} else {
					StatsState.catches[p.playerNumber]++;
				}
			}
			
		}
		
		if (vel.length() > 1f) manager.ballInterception(1f);
		else manager.ballCatch(1f);
		
		if (player==null){
			player = p;
			p.caught = true;
		} else {
			player.caught = false;
			player = p;
			player.caught = true;
			
		}
		caught = true;
	}
	
	public void toss(Vector2f vel, EntityManager manager) {
		this.vel = new Vector2f(vel);
		caught = false;
		StatsState.tosses[player.playerNumber]++;
		lastPossession = player;
		player = null;
		bounces = 0;
		manager.ballInterception(vel.length()/8f + .4f);
	}
	
	public void inGoal(){
		
	}

}
