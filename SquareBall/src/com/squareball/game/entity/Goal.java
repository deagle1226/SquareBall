package com.squareball.game.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import com.squareball.game.GameWindow;
import com.squareball.game.ScoreState;

public class Goal extends StaticEntity {
	
	private Color color;
	private boolean left;
	private int scoreSize = GameWindow.WINDOW_WIDTH/128;
	private int scorePause = 25;
	private int scoreTime = 0;
	public int team;
	
	public Goal(boolean left){
		this.left = left;
		if (left){
			shape = new Rectangle(0, GameWindow.WINDOW_HEIGHT/3, GameWindow.WINDOW_WIDTH/8, GameWindow.WINDOW_HEIGHT/3);
			color = Color.blue;
			team = 0;
		} else {
			shape = new Rectangle(GameWindow.WINDOW_WIDTH-GameWindow.WINDOW_WIDTH/8, GameWindow.WINDOW_HEIGHT/3, GameWindow.WINDOW_WIDTH/8, GameWindow.WINDOW_HEIGHT/3);
			color = Color.red;
			team = 1;
		}
	}
	

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()/2));
		graphics.fill(shape);
		
		if (left){
			for (int i = 0; i < ScoreState.score1; i++){
				float r1 = (float) (Math.random()*3);
				float r2 = (float) (Math.random()*3);
				Shape point = new Rectangle((2*scoreSize)*(i%8)+scoreSize/2+r1-1, GameWindow.WINDOW_HEIGHT/3 +(2*scoreSize *(i/8))+scoreSize/2+r2-1, scoreSize, scoreSize);
				graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()/4));
				graphics.fill(point);
				graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()/2));
				graphics.draw(point);
			}
		} else {
			for (int i = 0; i < ScoreState.score2; i++){
				float r1 = (float) (Math.random()*3);
				float r2 = (float) (Math.random()*3);
				Shape point = new Rectangle((GameWindow.WINDOW_WIDTH-shape.getWidth())+(2*scoreSize)*(i%8)+scoreSize/2+r1, GameWindow.WINDOW_HEIGHT/3 +(2*scoreSize *(i/8))+scoreSize/2+r2, scoreSize, scoreSize);
				graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()/4));
				graphics.fill(point);
				graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()/2));
				graphics.draw(point);
			}
		}
		graphics.setColor(color);
		graphics.draw(shape);
	}
		

	@Override
	public void collide(GameContainer gc, EntityManager manager, Entity other) {
		if (other instanceof Ball){
			scoreTime--;
			if (scoreTime < 0){
				if (left) ScoreState.score1++;
				else ScoreState.score2++;
				scoreTime = scorePause;
			}
			
		}
	}

}
