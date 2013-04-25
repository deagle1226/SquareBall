package com.squareball.game;

public class GameSettings {
	
	public static int maxScore = 100;
	
	public static boolean goal_passive = false;
	
	public static int countDown = 210;
	
	public static float playerSize = (GameWindow.WINDOW_HEIGHT/100)*5.5f;
	public static float playerSpeed = 0.01f * ((float)GameWindow.WINDOW_WIDTH/1280f);
	
	public static int hustleLength = 200;
	public static int hustleCooldown = 205;
	public static float hustleSpeed = 3f; // multiplier of playerSpeed
	
	public static int catchCooldown = 200;
	
	public static float ballSize = (GameWindow.WINDOW_HEIGHT/100)*1.5f;
	public static float maxBallVel = 4f; // multiplier of playerSpeed
	public static float buildUpSpeed = 0.1f;
	public static float jumpBallSpeed = 2f;
	public static float ballRotationRadius = GameWindow.WINDOW_WIDTH/30f;
	
	public static float goalHeight = GameWindow.WINDOW_HEIGHT/3f;
	public static float goalWidth = GameWindow.WINDOW_WIDTH/8f;
	
	public static float friction_map = 0.99f;
	public static float friction_goal = 0.93f;
	
	
	public static void superMode(){
		goal_passive = true;
		playerSize = (GameWindow.WINDOW_HEIGHT/100)*7f;
		playerSpeed = 0.03f * ((float)GameWindow.WINDOW_WIDTH/1280f);
		
		hustleLength = 100;
		hustleCooldown = 105;
		hustleSpeed = 3f; // multiplier of playerSpeed
		
		ballSize = (GameWindow.WINDOW_HEIGHT/100)*3f;
		maxBallVel = 5f; // multiplier of playerSpeed
		buildUpSpeed = 0.2f;
		jumpBallSpeed = 1.5f;
		ballRotationRadius = GameWindow.WINDOW_WIDTH/20f;
		
		goalHeight = GameWindow.WINDOW_HEIGHT/2f;
		goalWidth = GameWindow.WINDOW_WIDTH/6f;
		
		friction_map = 1f; // NO FRICTION!
		friction_goal = 0.8f;
	}
	
	public static void classicMode() {
		goal_passive = false;
		playerSize = (GameWindow.WINDOW_HEIGHT/100)*5.5f;
		playerSpeed = 0.01f * ((float)GameWindow.WINDOW_WIDTH/1280f);
		
		hustleLength = 200;
		hustleCooldown = 225;
		hustleSpeed = 3f; // multiplier of playerSpeed
		
		ballSize = (GameWindow.WINDOW_HEIGHT/100)*1.5f;
		maxBallVel = 4f; // multiplier of playerSpeed
		buildUpSpeed = 0.1f;
		jumpBallSpeed = 2f;
		ballRotationRadius = GameWindow.WINDOW_WIDTH/30f;
		
		goalHeight = GameWindow.WINDOW_HEIGHT/3f;
		goalWidth = GameWindow.WINDOW_WIDTH/8f;
		
		friction_map = 0.99f;
		friction_goal = 0.93f;
	}
	
	public static void bigFieldMode(){
		goal_passive = false;
		playerSize = (GameWindow.WINDOW_HEIGHT/100)*2.8f;
		playerSpeed = 0.005f * ((float)GameWindow.WINDOW_WIDTH/1280f);
		
		hustleLength = 200;
		hustleCooldown = 225;
		hustleSpeed = 2f; // multiplier of playerSpeed
		
		ballSize = (GameWindow.WINDOW_HEIGHT/100)*.75f;
		maxBallVel = 2f; // multiplier of playerSpeed
		buildUpSpeed = 0.1f;
		jumpBallSpeed = 1f;
		ballRotationRadius = GameWindow.WINDOW_WIDTH/45f;
		
		goalHeight = GameWindow.WINDOW_HEIGHT/6f;
		goalWidth = GameWindow.WINDOW_WIDTH/16f;
		
		friction_map = 0.98f;
		friction_goal = 0.92f;
	}

}
