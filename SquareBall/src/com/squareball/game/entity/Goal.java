package com.squareball.game.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import com.squareball.game.GameSettings;
import com.squareball.game.GameWindow;
import com.squareball.game.ScoreState;

public class Goal extends StaticEntity {
	
	private Color color;
	private boolean left;
	private int scoreSize = (int) (GameSettings.goalWidth/20);
	private int scorePause = 51;
	private int scoreTime = 0;
	public int team;
	
	public Goal(boolean left){
		this.left = left;
		if (left){
			shape = new Rectangle(0, GameWindow.WINDOW_HEIGHT/2 - GameSettings.goalHeight/2, GameSettings.goalWidth, GameSettings.goalHeight);
			color = Color.blue;
			team = 0;
		} else {
			shape = new Rectangle(GameWindow.WINDOW_WIDTH-GameSettings.goalWidth, GameWindow.WINDOW_HEIGHT/2 - GameSettings.goalHeight/2, GameSettings.goalWidth, GameSettings.goalHeight);
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
				Rectangle point = new Rectangle(0,0,GameSettings.goalWidth/20,GameSettings.goalHeight/20);
				point.setLocation((2*point.getWidth())*(i%10)+point.getWidth()/2+r1-1, shape.getY() +(2*point.getHeight() *(i/10))+point.getHeight()+r2-1-point.getHeight()/2);
				graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()/4));
				graphics.fill(point);
				graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()/2));
				graphics.draw(point);
			}
		} else {
			for (int i = 0; i < ScoreState.score2; i++){
				float r1 = (float) (Math.random()*3);
				float r2 = (float) (Math.random()*3);
				//Shape point = new Rectangle(shape.getX()+(2*scoreSize)*(i%10)+scoreSize/2+r1, shape.getY() +(3*scoreSize *(i/10))+scoreSize+r2, scoreSize, scoreSize*2);
				Rectangle point = new Rectangle(0,0,GameSettings.goalWidth/20,GameSettings.goalHeight/20);
				point.setLocation((2*point.getWidth())*(i%10)+point.getWidth()/2+r1-1 + shape.getMinX(), shape.getY() +(2*point.getHeight() *(i/10))+point.getHeight()+r2-1-point.getHeight()/2);
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
				if (ScoreState.score1 > GameSettings.maxScore-5 || ScoreState.score1 > GameSettings.maxScore-5){
					manager.point(true);
				} else {
					manager.point(false);
				}
			}
			
		}
	}

}
