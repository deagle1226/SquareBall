package com.squareball.game.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.squareball.game.GameWindow;
import com.squareball.game.StatsState;
import com.squareball.game.particles.ParticleManager;

public class Ball extends MobileEntity {
	
	public float friction = 0.99f;
	private ParticleManager particles;
	private float size = (GameWindow.WINDOW_HEIGHT/100)*1.5f;
	private Color color;
	public boolean caught = false;
	public Player player;
	private float rotation;
	private Player lastPossession;
	
	public Ball(boolean up) {
		shape = new Rectangle(GameWindow.WINDOW_WIDTH/2, GameWindow.WINDOW_HEIGHT/2, size, size);
		vel = new Vector2f(0,1);
		if (up) vel = new Vector2f(0,-1);
		particles = new ParticleManager(size/4, 150f, 12, Color.black, false);
		color = Color.black;
		rotation = 0;
	}
	
	@Override
	public void update(GameContainer gc, EntityManager manager, int delta) {
		vel = vel.scale(friction);
		super.update(gc, manager, delta);
		
		if (caught){
			rotation = (rotation + delta/2)%360;
			Vector2f rot = new Vector2f(rotation).scale(GameWindow.WINDOW_WIDTH/30);
			shape.setLocation(shape.getX()+rot.x, shape.getY()+rot.y);
			particles.update(new Vector2f(shape.getX(), shape.getY()), delta);
			
		} else {
			particles.update(new Vector2f(shape.getX()+size/2, shape.getY()+size/2), delta);
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
			friction = 0.93f;
		} else {
			friction = 0.99f;
		}
	}
	
	public void grab(Player p){
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
	
	public void toss(Vector2f vel) {
		this.vel = new Vector2f(vel);
		caught = false;
		StatsState.tosses[player.playerNumber]++;
		lastPossession = player;
		player = null;
	}
	
	public void inGoal(){
		
	}

}
