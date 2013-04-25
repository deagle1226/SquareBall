package com.squareball.game.gui;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;

public class ExitButton extends MenuButton {

	public ExitButton(float x, float y, float dimx, float dimy) {
		super(0, "Exit Game", x, y, dimx, dimy);
	}

	@Override
	public void action(StateBasedGame sbg) {
		System.exit(0);
	}
	
	

}
