package com.squareball.game;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.squareball.game.entity.Map;
import com.squareball.game.gui.Button;
import com.squareball.game.gui.ExitButton;
import com.squareball.game.gui.MenuButton;

public abstract class MenuState extends BasicGameState {
	
	protected ArrayList<Button> buttons;
	private int focused;
	private int moveTime = 15;
	private int moveCur = 0;
	
	private UnicodeFont font32;
	private UnicodeFont font64;
	private Map map;

	public MenuState() {
		buttons = new ArrayList<Button>();
		focused = 0;
		map = new Map();
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		font32 = new UnicodeFont("res/oswald.ttf", (int) (32*(GameWindow.WINDOW_HEIGHT/720f)) , false, false);
		font32.addAsciiGlyphs();
		font32.getEffects().add(new ColorEffect());
		font32.loadGlyphs();
		font64 = new UnicodeFont("res/oswald.ttf", (int) (128*(GameWindow.WINDOW_HEIGHT/720f)) , false, false);
		font64.addAsciiGlyphs();
		font64.getEffects().add(new ColorEffect());
		font64.loadGlyphs();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		map.render(g);
		font64.drawString(GameWindow.WINDOW_WIDTH/2f - font64.getWidth(getTitle().toUpperCase())/2f, 0, getTitle().toUpperCase(), Color.lightGray);
		for (Button b : buttons){
			b.render(g, font32);
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
	
	public abstract String getTitle();
}
