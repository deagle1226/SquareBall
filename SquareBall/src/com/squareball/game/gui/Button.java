package com.squareball.game.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Button {
	
	private Shape shape;
	private String text;
	private boolean focus;
	
	public Button(String text, float x, float y, float dimx, float dimy){
		shape = new Rectangle(x, y, dimx, dimy);
		focus = false;
		this.text = text;
	}
	
	public void update(GameContainer gc, StateBasedGame sbg){
		if (focus){
			if (gc.getInput().isButtonPressed(0, gc.getInput().ANY_CONTROLLER)){
				action(sbg);
			}
			
		}
	}
	
	public void render(Graphics g){
		g.setColor(Color.white);
		if (focus) g.setColor(Color.lightGray);
		g.fill(shape);
		g.setColor(Color.black);
		g.drawString(text, shape.getX(), shape.getY());
	}
	
	public abstract void action(StateBasedGame sbg);
	
	public void setFocus(boolean focus){
		this.focus = focus;
	}

}
