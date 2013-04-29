package com.squareball.game.entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import com.squareball.game.GameWindow;

public abstract class MobileEntity implements Entity {
	protected Vector2f vel;
	protected Shape shape;
	
	@Override
	public void update(GameContainer gc, EntityManager manager, int delta) {
		boundsCheck();
		Vector2f temp = new Vector2f(vel);
		shape.setLocation(shape.getX() + temp.x*delta, shape.getY() + temp.y*delta);
	}
	
	@Override
	public boolean intersects(Entity other) {
		return getShape().intersects(other.getShape());
	}
	
	@Override
	public Shape getShape(){
		return shape;
	}
	
	public void boundsCheck(){
		if (shape.getX() < 0){
			vel.x = -vel.x;
			shape.setX(5);
		} else if (shape.getX() > GameWindow.WINDOW_WIDTH-shape.getWidth()) {
			vel.x = -vel.x;
			shape.setX(GameWindow.WINDOW_WIDTH-shape.getWidth()-5);
		}
		if (shape.getY() < 0){
			vel.y = -vel.y;
			shape.setY(5);
		} else if (shape.getY() > GameWindow.WINDOW_HEIGHT-shape.getHeight()) {
			vel.y = -vel.y;
			shape.setY(GameWindow.WINDOW_HEIGHT-shape.getHeight()-5);
		}
	}
}
