package com.squareball.game.entity;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.squareball.game.GameWindow;
import com.squareball.game.StatsState;
import com.squareball.game.particles.ParticleManager;

public class Player extends MobileEntity {
	
	private float speed = 0.01f * (GameWindow.WINDOW_WIDTH/1280f);
	public int sprintTime = 0;
	private float sprintSpeed = 3f;
	private int sprintCooldown = 300;
	private boolean sprinted = false;
	private int catchTime = 0;
	private Ball ball;
	private float buildUp = 0;
	public boolean caught = false;
	
	private int controllerNumber;
	private Color color;
	private float size = (GameWindow.WINDOW_HEIGHT/100)*5.5f;
	private int countDown = 1000;
	
	private ParticleManager particles;
	public int playerNumber;
	public int team;
	
	private TrueTypeFont font;
	
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
		font = new TrueTypeFont(new Font("sans-serif", 30, 30), true);
	}
	
	@Override
	public void update(GameContainer gc, EntityManager manager, int delta){
		particles.update(new Vector2f(shape.getX()+size/2, shape.getY()+size/2), delta);
		catchTime-=delta;
		controllerUpdate(gc, delta);
		super.update(gc, manager, delta);
	}
	
	public void controllerUpdate(GameContainer gc, int delta){
		countDown -= delta;
		vel.x = 0;
		vel.y = 0;
		if (countDown < 0){
			countDown = 0;
			vel.x = gc.getInput().getAxisValue(controllerNumber, 1) * (speed*delta);
			vel.y = gc.getInput().getAxisValue(controllerNumber, 0) * (speed*delta);
			sprintCooldown -= delta;
			sprintTime -= delta;
			if (sprintCooldown < 0){
				if (gc.getInput().isButtonPressed(0, controllerNumber) && !sprinted){
					sprintTime = 200;
					sprintCooldown = 205;
					sprinted = true;
				} else if (!gc.getInput().isButtonPressed(0, controllerNumber)){
					sprinted = false;
				}
			}
			
			if (sprintTime > 0 && !caught){
				vel.x = gc.getInput().getAxisValue(controllerNumber, 1) * (speed*delta*sprintSpeed);
				vel.y = gc.getInput().getAxisValue(controllerNumber, 0) * (speed*delta*sprintSpeed);
			}
			if (caught){
				StatsState.ball_time[playerNumber]++;
				ball.getShape().setLocation(shape.getX()+shape.getWidth()/2, shape.getY()+shape.getWidth()/2);
				if (buildUp > 3f){
					Vector2f temp = new Vector2f(vel);
					temp = temp.normalise();
					ball.toss(temp.scale(buildUp));
					caught = false;
					buildUp = 0;
				} else if (gc.getInput().isButtonPressed(1, controllerNumber)){
					buildUp += 0.1f;
				} else if (buildUp > 0){
					Vector2f temp = new Vector2f(vel);
					temp = temp.normalise();
					ball.toss(temp.scale(buildUp));
					caught = false;
					buildUp = 0;
				}
			}
		}
	}
	

	@Override
	public void render(Graphics graphics) {
		particles.render(graphics);
		graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()/2));
		graphics.fill(shape);
		graphics.setColor(color);
		graphics.draw(shape);
		graphics.setColor(Color.white);
		graphics.setFont(font);
		graphics.drawString(" " + (playerNumber+1), shape.getX(), shape.getY());
	}

	@Override
	public void collide(GameContainer gc, EntityManager manager, Entity other) {
		if (other instanceof Goal){
			if (((Goal) other).team == team){
				goalCollide((Goal) other);
			} else {
				StatsState.goal_time[playerNumber]++;
			}
			
		}

		if (other instanceof Ball){
			if (((Ball) other).caught && ((Ball) other).player.team != team){
				if (gc.getInput().isButtonPressed(2, controllerNumber)){	
					if (catchTime < 0) {
						ball = (Ball) other;
						caught = true;
						ball.grab(this);
						catchTime = 50;
						StatsState.steals[playerNumber]++;
					}
				}
			} else if (gc.getInput().isButtonPressed(2, controllerNumber)){
				if (catchTime < 0) {
					ball = (Ball) other;
					caught = true;
					ball.grab(this);
					catchTime = 50;
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
