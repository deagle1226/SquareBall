package com.squareball.game.gui;

import org.newdawn.slick.state.StateBasedGame;

public class MenuButton extends Button {
	
	private int target;

	public MenuButton(int target, String text, float x, float y, float dimx, float dimy) {
		super(text, x, y, dimx, dimy);
		this.target = target;
	}

	@Override
	public void action(StateBasedGame sbg) {
		sbg.enterState(target);
	}

}
