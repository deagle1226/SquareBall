package com.squareball.game.gui;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;

import com.squareball.game.GameSettings;

public class MenuButton extends Button {
	
	private int target;

	public MenuButton(int target, String text, float x, float y, float dimx, float dimy) {
		super(text, x, y, dimx, dimy);
		this.target = target;
	}

	@Override
	public void action(StateBasedGame sbg) {
		if (target == 2) {
			GameSettings.superMode();
			sbg.enterState(target-1);
		}
		else {
			if (target == 1) GameSettings.classicMode();
			sbg.enterState(target);
		}
	}

}
