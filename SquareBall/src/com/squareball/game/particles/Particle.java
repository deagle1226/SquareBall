package com.squareball.game.particles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Particle {
	
	private float life = 300f;
	
	private float size = 5;
	
	private float fade;
	private Vector2f pos;
	private float r,g,b,a;
	private Color color;
	
	public Particle(Vector2f pos, float size, float life, Color color, boolean rainbow){
		this.life = life;
		fade = life;
		this.pos = pos;
		double r = Math.random()*100;
		this.color = color;
		a = 1f;
		this.size = (float) (Math.random()*size + 3);	
		if (rainbow){
			if (r < 20){
				this.color = Color.red;
			} else if (r < 40){
				this.color = Color.blue;
			} else if (r < 60){
				this.color = Color.green;
			} else {
				this.color = Color.white;
			}
		}
	}
	
	public void update(ParticleManager manager, int delta){
		fade-=delta;
		a = (fade*fade)/(life*life);
		if (fade < 0){
			manager.remove(this);
		}
	}

	public void render(Graphics graphics) {
		graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), a/2));
		graphics.fill(new Rectangle(pos.x-size/2, pos.y-size/2, size, size));
		graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), a));
		graphics.draw(new Rectangle(pos.x-size/2, pos.y-size/2, size, size));
		graphics.setColor(Color.black);
	}



}
