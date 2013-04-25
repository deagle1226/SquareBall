package com.squareball.game.entity;

public interface EntityManager {

	public void removeEntity(Entity entity);
	
	public void addEntity(Entity entity);
	
	public void ballCatch(float volume);
	
	public void ballInterception(float volume);
	
	public void hustle();
	
	public void point(boolean endgame);
}
