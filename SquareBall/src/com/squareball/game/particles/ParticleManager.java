package com.squareball.game.particles;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class ParticleManager {
	
	private ArrayList<Particle> particles;
	private ArrayList<Particle> add;
	private ArrayList<Particle> remove;
	
	private float radius;
	private float life;
	
	private int freq = 2;
	private int tick;
	private int density = 4;
	private float colorFreq;
	private Color color;
	private boolean rainbow;
	
	private float x, y;
	
	public ParticleManager(float r, float life, int density, Color color, boolean rainbow){
		particles = new ArrayList<Particle>();
		add = new ArrayList<Particle>();
		remove = new ArrayList<Particle>();
		tick = 1;
		x = r;
		y = r;
		radius = r;
		this.life = life;
		this.density = density;
		this.colorFreq = freq;
		this.color =color;
		this.rainbow = rainbow;
	}
	
	public ParticleManager(float x, float y, float life, int density, Color color, boolean rainbow){
		particles = new ArrayList<Particle>();
		add = new ArrayList<Particle>();
		remove = new ArrayList<Particle>();
		tick = 1;
		this.x = x/2;
		this.y = y/2;
		radius = 10;
		this.life = life;
		this.density = density;
		this.colorFreq = freq;
		this.color =color;
		this.rainbow = rainbow;
	}
	
	public void update(Vector2f pos, int delta){
		tick -= delta;
		if (tick < 0){
			tick = freq;
			for (int i = 0; i < density; i++){
				add(pos, radius);
			}
		}
		particles.removeAll(remove);
		particles.addAll(add);
		
		remove.clear();
		add.clear();
		
		for (Particle p : particles){
			p.update(this, delta);
		}
	}

	public void render(Graphics graphics) {
		for (Particle p : particles){
			p.render(graphics);
		}
	}
	
	public void remove(Particle p){
		remove.add(p);
	}
	
	public void add(Vector2f pos, float r){
		float dx = (float) (Math.sin(Math.random()*100f)*(2*x));
		float dy = (float) (Math.sin(Math.random()*100f)*(2*y));
		add.add(new Particle(new Vector2f(pos.x+dx, pos.y+dy), radius, life, color, rainbow));
	}
	
	

}
