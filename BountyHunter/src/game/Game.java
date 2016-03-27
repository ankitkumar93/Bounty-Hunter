package game;

import environment.Environment;
import processing.core.*;

public class Game extends PApplet{
	
	/* Class Data */
	private Environment environment;
	
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
	}
	
	//Draw
	public void draw(){
		background(255);
		environment.draw();
	}
	
	/* Main */
	public static void main(String args[]){
		PApplet.main(new String[]{"game.Game"});
	}
}
