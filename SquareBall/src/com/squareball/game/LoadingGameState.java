package com.squareball.game;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LoadingGameState extends BasicGameState {
	
	public static final int OSWALD = 0;
	public static final int font64 = 0;
	public static final int font32 = 1;
	public static final int font16 = 2;
	public static final int font128 = 3;
	
	private ArrayList<String> musicPath;
	private ArrayList<String> soundPath;
	private ArrayList<String> fontPath;
	
	public static ArrayList<Music> music;
	public static ArrayList<Sound> sound;
	public static ArrayList<UnicodeFont> font;
	
	private String text;
	private int step = 0;
	private float part = 0;
	private float whole;
	

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
		System.out.println(musicPath.get(music.size()) + music.size());
		music.add(new Music(musicPath.get(music.size()), true));
	}
	
	public void loadSound() throws SlickException{
		sound.add(new Sound(soundPath.get(sound.size())));
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
		g.setColor(Color.lightGray);
		g.fill(new Rectangle(100,200,200,20));
		g.setColor(Color.white);
		g.fill(new Rectangle(100,200,200*(part/whole),20));
		g.setColor(Color.lightGray);
		g.fill(new Rectangle(100,300,200,20));
		g.setColor(Color.white);
		g.fill(new Rectangle(100,300,200*((step-1)/3f),20));
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		switch (step){
		case 0:
			text = "Loading Music...0";
			whole = musicPath.size();
			step++;
			break;
		case 1:
			if (part < musicPath.size()){
				loadMusic();
				if (part == musicPath.size()-1) {
					part = 0;
					step++;
					text = "Loading Sounds...0";
					whole = soundPath.size();
				} else {
					part++;
					text = "Loading Music..." + part;
				}
			}
			break;
		case 2:
			if (part <= soundPath.size()){
				loadSound();
				if (part == soundPath.size()-1) {
					part = 0;
					step++;
					text = "Loading Fonts...";
					whole = 4;
				} else {
					part++;
					text = "Loading Sounds..." + part;
				}
			}
			break;
		case 3:
			loadFont();
			step++;
			break;
		case 4:
			sbg.addState(new StartMenuState());
			sbg.addState(new PlayState());
			sbg.addState(new StatsState());
			sbg.enterState(0);
			break;
		}
	}

	@Override
	public int getID() {
		return 999;
	}

}
