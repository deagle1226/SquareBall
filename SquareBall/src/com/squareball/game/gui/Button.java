package com.squareball.game.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.squareball.game.particles.ParticleManager;

public abstract class Button {
	
	private Shape shape;
	private String text;
	private boolean focus;
	private ParticleManager particles;
	
	public Button(String text, float x, float y, float dimx, float dimy){
		shape = new Rectangle(x, y, dimx, dimy);
		focus = false;
		this.text = text;
		particles = new ParticleManager(dimx/2, dimy/2, 200, 20, Color.black, true);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg){
		if (focus){
			if (gc.getInput().isButtonPressed(0, gc.getInput().ANY_CONTROLLER)){
				action(sbg);
			}
			particles.update(new Vector2f(shape.getCenterX(), shape.getCenterY()), 5);
		}
	}
	
	public void render(Graphics g, UnicodeFont font){
		
		if (focus) particles.render(g);
		g.setColor(new Color(1,1,1,.5f));
		//if (!focus) g.setColor(new Color(0,0,0,.2f));
		g.setColor(new Color(0,0,0,.3f));
		g.fill(shape);
		font.drawString(shape.getCenterX()-font.getWidth(text.toUpperCase())/2, shape.getCenterY()-font.getLineHeight()/2, text.toUpperCase(), Color.white);
		g.setColor(Color.lightGray);
		g.draw(shape);
	}
	
	public abstract void action(StateBasedGame sbg);
	
	public void setFocus(boolean focus){
		this.focus = focus;
	}

}
