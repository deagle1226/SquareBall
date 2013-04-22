package com.squareball.game;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.squareball.game.gui.Button;

public abstract class MenuState extends BasicGameState {
	
	protected ArrayList<Button> buttons;
	private int focused;
	private int moveTime = 10;
	private int moveCur = 0;

	public MenuState() {
		buttons = new ArrayList<Button>();
		focused = 0;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		for (Button b : buttons){
			b.render(g);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		handleInput(gc);
		int i = 0;
		for (Button b : buttons){
			if (i==focused) b.setFocus(true);
			else b.setFocus(false);
			b.update(gc, sbg);
			i++;
		}
	}
	
	public void handleInput(GameContainer gc){
		moveCur--;
		if (moveCur < 0){
			if (gc.getInput().isControllerDown(gc.getInput().ANY_CONTROLLER)){
				next();
				moveCur = moveTime;
			} else if (gc.getInput().isControllerUp(gc.getInput().ANY_CONTROLLER)) {
				prev();
				moveCur = moveTime;
			}
		}
		
	}
	
	public void next() {
		focused++;
		if (focused > buttons.size()-1){
			focused = 0;
		}
	}

	public void prev(){
		focused--;
		if (focused < 0){
			focused = buttons.size()-1;
		}
	}
}
