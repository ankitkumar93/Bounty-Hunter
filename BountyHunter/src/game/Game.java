package game;

import characters.Thief;
import environment.Environment;
import processing.core.*;

public class Game extends PApplet{
	
	/* Class Data */
	private Environment environment;
	private Thief thief;
	
	
	/* Processing Data */
	private final int WINDOW_HEIGHT = 500;
	private final int WINDOW_WIDTH = 500;
	
	/* Processing Functions */
	//Settings
	public void settings(){
		size(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	//Setup
	public void setup(){
		//Initialize Environment
		environment = new Environment(this);
		
		//Initialize Thief
		thief = new Thief(environment.getBountyRelativePosition(), environment);
	}
	
	//Draw
	public void draw(){
		background(255);
		environment.draw();
		thief.update();
	}
	
	/* Main */
	public static void main(String args[]){
		PApplet.main(new String[]{"game.Game"});
	}
}
