package com.squareball.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.squareball.game.gui.MenuButton;


public class StartMenuState extends MenuState {

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		buttons.add(new MenuButton(1, "Classic", GameWindow.WINDOW_WIDTH/2, GameWindow.WINDOW_HEIGHT/2, 100,50));
		buttons.add(new MenuButton(1, "Custom", GameWindow.WINDOW_WIDTH/2, GameWindow.WINDOW_HEIGHT/2+100, 100,50));
	}

	@Override
	public int getID() {
		return 0;
	}


}
