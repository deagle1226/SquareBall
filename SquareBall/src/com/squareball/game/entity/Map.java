package com.squareball.game.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import com.squareball.game.GameWindow;

public class Map extends StaticEntity {
	
	
	public Map(){
		shape = new Rectangle(0,0,GameWindow.WINDOW_WIDTH, GameWindow.WINDOW_HEIGHT);
	}

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(Color.white);
		graphics.fill(shape);
		int padding = 10;
		int across = 16;
		int high = 9;
		for (int x = 0; x < across; x++){
			for (int y = 0; y < high; y++){
				graphics.setColor(new Color(0f,0f,0f,0.025f));
				Rectangle grid = new Rectangle(x*(GameWindow.WINDOW_WIDTH/across)+padding/2, y*(GameWindow.WINDOW_HEIGHT/high)+padding/2, GameWindow.WINDOW_WIDTH/across-padding, GameWindow.WINDOW_HEIGHT/high-padding);
				graphics.fill(grid);
			}
		}
	}

	@Override
	public void collide(GameContainer gc, EntityManager manager, Entity other) {
	}

}
