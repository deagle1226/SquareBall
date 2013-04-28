package com.squareball.game;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LoadingGameState extends BasicGameState {
	
	public static final int OSWALD = 0;
	public static final int font64 = 0;
	public static final int font32 = 1;
	public static final int font16 = 2;
	
	private ArrayList<String> musicPath;
	private ArrayList<String> soundPath;
	private ArrayList<String> fontPath;
	
	public static ArrayList<Music> music;
	public static ArrayList<Sound> sound;
	public static ArrayList<UnicodeFont> font;
	
	private String text;
	private int step = 0;
	

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		musicPath = new ArrayList<String>();
		soundPath = new ArrayList<String>();
		fontPath = new ArrayList<String>();
		
		music = new ArrayList<Music>();
		sound = new ArrayList<Sound>();
		font = new ArrayList<UnicodeFont>();
		
		musicPath.add("res/music.ogg");
		musicPath.add("res/Hydrogen.ogg");
		musicPath.add("res/DeepCover.ogg");
		musicPath.add("res/Daisuke.ogg");
		
		soundPath.add("res/catch.ogg");
		soundPath.add("res/intercept.ogg");
		soundPath.add("res/whoosh.ogg");
		soundPath.add("res/point1.ogg");
		soundPath.add("res/point2.ogg");
		
		fontPath.add("res/oswald.ttf");
		
		text = "Loading...";
	}
	
	public void loadMusic() throws SlickException{
		for (String path : musicPath){
			music.add(new Music(path, false));
		}
	}
	
	public void loadSound() throws SlickException{
		for (String path : soundPath){
			sound.add(new Sound(path));
		}
	}
	
	public void loadFont() throws SlickException{
		font.add(new UnicodeFont(fontPath.get(OSWALD), (int) (64*(GameWindow.WINDOW_HEIGHT/720f)) , false, false));
		font.add(new UnicodeFont(fontPath.get(OSWALD), (int) (32*(GameWindow.WINDOW_HEIGHT/720f)) , false, false));
		font.add(new UnicodeFont(fontPath.get(OSWALD), (int) (16*(GameWindow.WINDOW_HEIGHT/720f)) , false, false));
		font.add(new UnicodeFont(fontPath.get(OSWALD), (int) (128*(GameWindow.WINDOW_HEIGHT/720f)) , false, false));
		
		for (UnicodeFont f : font){
			f.addAsciiGlyphs();
			f.getEffects().add(new ColorEffect());
			f.loadGlyphs();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.drawString(text, 10, 100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		switch (step){
		case 0: 
			text = "Loading Music...";
			break;
		case 1:
			loadMusic();
			text = "Loading Sounds...";
			break;
		case 2:
			loadSound();
			text = "Loading Fonts...";
			break;
		case 3:
			loadFont();
			break;
		case 4:
			sbg.addState(new StartMenuState());
			sbg.addState(new PlayState());
			sbg.addState(new StatsState());
			sbg.enterState(0);
		}
		step++;
	}

	@Override
	public int getID() {
		return 999;
	}

}
