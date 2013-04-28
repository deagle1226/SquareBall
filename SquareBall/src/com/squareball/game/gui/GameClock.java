package com.squareball.game.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import com.squareball.game.GameWindow;
import com.squareball.game.StatsState;
import com.squareball.game.entity.Entity;
import com.squareball.game.entity.EntityManager;

public class GameClock implements Entity {
	
	private UnicodeFont font;
	private Shape shape;
	
	public GameClock(UnicodeFont font){
		this.font = font;
		shape = new Circle(0,0,1);
	}

	@Override
	public void update(GameContainer gc, EntityManager manager, int delta) {
	}

	@Override
	public void render(Graphics graphics) {
		font.drawString(GameWindow.WINDOW_WIDTH/2 - font.getWidth(timeFormat(0))/2,10, timeFormat(StatsState.matchTime), Color.lightGray);
	}

	@Override
	public Shape getShape() {
		return shape;
	}

	@Override
	public void collide(GameContainer gc, EntityManager manager, Entity other) {
	}

	@Override
	public boolean intersects(Entity other) {
		return false;
	}
	
	public String timeFormat(long millis){
		String str = "";
		long minutes = millis/1000/60;
		long seconds = millis/1000 - (minutes*60);
		str += minutes + ":";
		if (seconds < 10) str += "0";
		str += seconds;
		String tenths = (millis/100) + "";
		str += "." + tenths.substring(tenths.length()-1, tenths.length());
		return str;
	}

}
