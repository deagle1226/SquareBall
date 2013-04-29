package com.squareball.game.entity;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.squareball.game.GameSettings;
import com.squareball.game.GameWindow;
import com.squareball.game.StatsState;
import com.squareball.game.particles.ParticleManager;

public class Player extends MobileEntity {
	
	private float speed = GameSettings.playerSpeed;
	public int sprintTime = 0;
	private float sprintSpeed = GameSettings.hustleSpeed;
	private int sprintCooldown = GameSettings.hustleCooldown;
	private boolean sprinted = false;
	private int catchTime = 0;
	private Ball ball;
	private float buildUp = 0;
	private float maxThrowSpeed = GameSettings.maxBallVel;
	public boolean caught = false;
	
	private int controllerNumber;
	private Color color;
	private float size = GameSettings.playerSize;
	
	private ParticleManager particles;
	public int playerNumber;
	public int team;
	private boolean triggered = false;
	
	private UnicodeFont font;
	
	public Player(int n, int team, int pn){
		if (team == 0){
			shape = new Rectangle(GameWindow.WINDOW_WIDTH/5, GameWindow.WINDOW_HEIGHT/2, size, size);
		} else {
			shape = new Rectangle(GameWindow.WINDOW_WIDTH-GameWindow.WINDOW_WIDTH/5-size, GameWindow.WINDOW_HEIGHT/2, size, size);
		}
		this.team = team;
		playerNumber = pn;
		vel = new Vector2f(0,0);
		controllerNumber = n;
		color = Color.red;
		if (team == 0) color = Color.blue;
		particles = new ParticleManager(size/4, 500f, 2, color, false);
		try {
			font = new UnicodeFont("res/oswald.ttf", (int) (size*.75), false, false);
			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect());
			font.loadGlyphs();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(GameContainer gc, EntityManager manager, int delta){
		particles.update(new Vector2f(shape.getX()+size/2, shape.getY()+size/2), delta);
		catchTime-=delta;
		controllerUpdate(gc, manager, delta);
		super.update(gc, manager, delta);
	}
	
	public void controllerUpdate(GameContainer gc, EntityManager manager, int delta){
		vel.x = 0;
		vel.y = 0;
		vel.x = gc.getInput().getAxisValue(controllerNumber, 1) * (speed);
		vel.y = gc.getInput().getAxisValue(controllerNumber, 0) * (speed);
		sprintCooldown -= delta;
		sprintTime -= delta;
		if (sprintCooldown < 0 && !caught){
			float axis = gc.getInput().getAxisValue(controllerNumber, 4);
			if (axis == -1f) axis = 0;
			if ((axis < -0.5) && !sprinted && !triggered){
				sprintTime = GameSettings.hustleLength;
				sprintCooldown = GameSettings.hustleCooldown;
				sprinted = true;
				StatsState.hustles[playerNumber]++;
				manager.hustle();
				triggered = true;
			} else {
				sprinted = false;
			}
		}
		if (gc.getInput().getAxisValue(controllerNumber, 4) == 0){
			triggered = false;
		}
		if (sprintTime > 0){
			vel.x = gc.getInput().getAxisValue(controllerNumber, 1) * (speed*sprintSpeed);
			vel.y = gc.getInput().getAxisValue(controllerNumber, 0) * (speed*sprintSpeed);
		}
		if (caught){
			StatsState.ball_time[playerNumber]++;
			ball.getShape().setLocation(shape.getCenterX(), shape.getCenterY());
			if (buildUp > maxThrowSpeed){
				Vector2f temp = new Vector2f(vel);
				temp = temp.normalise();
				ball.toss(temp.scale(buildUp), manager);
				caught = false;
				buildUp = 0;
			} else if (gc.getInput().isButtonPressed(2, controllerNumber) && catchTime < 0){
				buildUp += GameSettings.buildUpSpeed;
				
			} else if (buildUp > 0){
				Vector2f temp = new Vector2f(vel);
				temp = temp.normalise();
				ball.toss(temp.scale(buildUp), manager);
				caught = false;
				buildUp = 0;
			}
		} else if (gc.getInput().isButtonPressed(0, controllerNumber)){
			if (catchTime < 0) catchTime = GameSettings.catchCooldown;
		}
		//System.out.println(catchTime);
	}
	

	@Override
	public void render(Graphics graphics) {
		particles.render(graphics);
		graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()/2));
		graphics.fill(shape);
		graphics.setColor(color);
		graphics.draw(shape);
		graphics.setColor(Color.white);
		font.drawString(shape.getCenterX()-font.getWidth((playerNumber+1)+"")/2, shape.getCenterY()-font.getLineHeight()/2, (playerNumber+1)+"", Color.white);
	}

	@Override
	public void collide(GameContainer gc, EntityManager manager, Entity other) {
		if (other instanceof Goal){
			if (((Goal) other).team == team){
				if (!GameSettings.goal_passive)
				goalCollide((Goal) other);
			} else {
				StatsState.goal_time[playerNumber]++;
			}
			
		}

		if (other instanceof Ball){
			if (caught){
				
			} else if (((Ball) other).caught && ((Ball) other).player.team != team){
				if (gc.getInput().isButtonPressed(0, controllerNumber)){	
					if (catchTime < 0) {
						ball = (Ball) other;
						caught = true;
						ball.grab(this, manager);
						catchTime = GameSettings.catchCooldown;
						StatsState.steals[playerNumber]++;
					}
				}
			} else if (gc.getInput().isButtonPressed(0, controllerNumber)){
				if (catchTime < 0) {
					ball = (Ball) other;
					caught = true;
					ball.grab(this, manager);
					catchTime = GameSettings.catchCooldown;
				}
			}
			
		}
		
	}
	
	public void goalCollide(Goal g) {
//		if (team == 0 && shape.getX() < g.getShape().getX() + g.getShape().getWidth()){
//			if (shape.getY() + shape.getHeight() > g.getShape().getY() &&
//				shape.getY() < g.getShape().getY()){
//				shape.setY(g.getShape().getY()-shape.getHeight());
//			} else if (shape.getY() < g.getShape().getY() + g.getShape().getHeight() &&
//				shape.getY() + shape.getHeight() > g.getShape().getY() + g.getShape().getHeight()){
//				shape.setY(g.getShape().getY() + g.getShape().getHeight());
//			} else {
//				shape.setX(g.getShape().getX() + g.getShape().getWidth());
//			}
//		} else if (team != 0 && shape.getX() + shape.getWidth() > g.getShape().getX()){
//			if (shape.getY() + shape.getHeight() > g.getShape().getY() &&
//				shape.getY() < g.getShape().getY()){
//				shape.setY(g.getShape().getY()-shape.getHeight());
//			} else if (shape.getY() < g.getShape().getY() + g.getShape().getHeight() &&
//				shape.getY() + shape.getHeight() > g.getShape().getY() + g.getShape().getHeight()){
//				shape.setY(g.getShape().getY() + g.getShape().getHeight());
//			} else {
//				shape.setX(g.getShape().getX() - shape.getWidth());
//			}
//		}
		if (shape.getX() < g.getShape().getX() && shape.getX() + shape.getWidth() > g.getShape().getX()){
			shape.setX(g.getShape().getX() - shape.getWidth());
		} else if (shape.getX() < g.getShape().getX() + g.getShape().getWidth() && shape.getX() + shape.getWidth() > g.getShape().getX() + g.getShape().getWidth()){
			shape.setX(g.getShape().getX() + g.getShape().getWidth());
		} else if (shape.getY() < g.getShape().getY() && shape.getY() + shape.getHeight() > g.getShape().getY()){
			shape.setY(g.getShape().getY() - shape.getHeight());
		} else if (shape.getY() < g.getShape().getY() + g.getShape().getHeight() && shape.getY() + shape.getHeight() > g.getShape().getY() + g.getShape().getHeight()){
			shape.setY(g.getShape().getY() + g.getShape().getHeight());
		}
	}
	
	public void setVel(float x, float y){
		vel = new Vector2f(x, y);
	}
	
	public Ball getBall(){
		return ball;
	}

}
