package com.squareball.game;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameWindow extends StateBasedGame {
	
	public static final int WINDOW_WIDTH = 1920;
	public static final int WINDOW_HEIGHT = 1080;
	private static boolean fullScreen = false;

	public GameWindow() {
		super("SquareBall");
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		gc.setTargetFrameRate(60);
		gc.getInput().clearControlPressedRecord();
		gc.setMouseGrabbed(true);
		//gc.getGraphics().setFont(new TrueTypeFont(new Font("sans-serif", Font.PLAIN, 18), true));
		this.addState(new StartMenuState());
		this.addState(new PlayState());
		this.addState(new StatsState());
		enterState(0);
	}
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		if (gc.getInput().isKeyPressed(Keyboard.KEY_ESCAPE)){
			if (getCurrentStateID() == 0){
				System.exit(0);
			}
			enterState(0);
		}
		super.update(gc, delta);
	}

	public static void main(String[] args) throws SlickException{
		GameWindow window = new GameWindow();
		window.addState(new PlayState());
		
		AppGameContainer app = new AppGameContainer(new GameWindow());
		app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, fullScreen);
		app.start();
	}

}
