package com.squareball.game;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameWindow extends StateBasedGame {
	
	public static int WINDOW_WIDTH = 1280;
	public static int WINDOW_HEIGHT = 720;
	private static boolean fullScreen = false;

	public GameWindow() {
		super("SquareBall");
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		gc.setMusicVolume(0.35f);
		gc.setTargetFrameRate(60);
		gc.setVSync(true);
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
		
		if (gc.getInput().isKeyPressed(Keyboard.KEY_UP)){
			gc.setMusicVolume(gc.getMusicVolume()+(0.05f));
			if (gc.getMusicVolume() > 1) gc.setMusicVolume(1f);
			
		} else if (gc.getInput().isKeyPressed(Keyboard.KEY_DOWN)){
			gc.setMusicVolume(gc.getMusicVolume()-(0.05f));
			if (gc.getMusicVolume() < 0) gc.setMusicVolume(0f);
		}
		super.update(gc, delta);
	}

	public static void main(String[] args) throws SlickException{
		GameWindow window = new GameWindow();
		
		AppGameContainer app = new AppGameContainer(new GameWindow());
		WINDOW_WIDTH = app.getScreenWidth();
		WINDOW_HEIGHT = app.getScreenHeight();
		app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), true);
		app.start();
	}

}
