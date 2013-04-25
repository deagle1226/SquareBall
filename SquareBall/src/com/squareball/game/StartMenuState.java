package com.squareball.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.StateBasedGame;

import com.squareball.game.gui.ExitButton;
import com.squareball.game.gui.MenuButton;


public class StartMenuState extends MenuState {
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		super.init(gc, sbg);
		float height = GameWindow.WINDOW_HEIGHT/8;
		float width = GameWindow.WINDOW_WIDTH/4;
		buttons.add(new MenuButton(1, "Classic SquareBall", GameWindow.WINDOW_WIDTH/2-width/2, GameWindow.WINDOW_HEIGHT/2-100, width, height));
		buttons.add(new MenuButton(2, "Super SquareBall", GameWindow.WINDOW_WIDTH/2-width/2, GameWindow.WINDOW_HEIGHT/2+height+10-100, width, height));
		buttons.add(new MenuButton(3, "Epic SquareBall", GameWindow.WINDOW_WIDTH/2-width/2, GameWindow.WINDOW_HEIGHT/2+2*height+20-100, width, height));
		buttons.add(new ExitButton(GameWindow.WINDOW_WIDTH/2-width/2, GameWindow.WINDOW_HEIGHT/2+3*height+30-100, width, height));
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public String getTitle() {
		return "SquareBall";
	}


}
