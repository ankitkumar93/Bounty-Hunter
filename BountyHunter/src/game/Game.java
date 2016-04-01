package game;

import characters.Thief;
import environment.Environment;
import processing.core.*;
import shapes.BountyShape;
import shapes.ThiefShape;

public class Game extends PApplet{
	
	/* Class Data */
	private Environment environment;
	private Thief thief;
	
	//Add bounty and thief shape
	private ThiefShape thiefShape;
	private BountyShape bountyShape;
		
	//Add current bounty and thief position
	private int[] currentThief;
	private int[] currentBounty;
	
	
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
		
		//Initialize shape
		thiefShape = new ThiefShape(this);
		bountyShape = new BountyShape(this);
				
		//Initialize position
		currentBounty = environment.getBountyHunterPosition();
		currentThief = environment.getThiefPosition();
		
		//Initialize Thief
		thief = new Thief(environment.getBountyRelativePosition(), environment);
	}
	
	//Draw
	public void draw(){
		background(255);
		
		//Updations
		thief.update();
		
		//Draw Methods
		environment.draw();
		drawThief();
		drawBountyHunter();
	}
	
	//Draw thief
	public void drawThief(){
		pushMatrix();
		translate(currentThief[0], currentThief[1]);
		thiefShape.drawShape();
		popMatrix();
	}
		
	//Draw bounty
	public void drawBountyHunter(){
		pushMatrix();
		translate(currentBounty[0], currentBounty[1]);
		bountyShape.drawShape();
		popMatrix();
	}
	
	/* Main */
	public static void main(String args[]){
		PApplet.main(new String[]{"game.Game"});
	}
}
