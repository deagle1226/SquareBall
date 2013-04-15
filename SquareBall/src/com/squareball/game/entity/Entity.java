package com.squareball.game.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public interface Entity {
	
	public void update(GameContainer gc, EntityManager manager, int delta);
	
	public void render(Graphics graphics);
	
	public Shape getShape();
	
	public void collide(GameContainer gc, EntityManager manager, Entity other);
	
	public boolean intersects(Entity other);

}
