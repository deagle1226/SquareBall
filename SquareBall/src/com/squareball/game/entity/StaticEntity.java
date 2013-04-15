package com.squareball.game.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public abstract class StaticEntity implements Entity {
	
	protected Shape shape;
	
	@Override
	public void update(GameContainer gc, EntityManager manager, int delta) {
	}
	
	@Override
	public boolean intersects(Entity other) {
		return getShape().intersects(other.getShape());
	}
	
	@Override
	public Shape getShape(){
		return shape;
	}

}
