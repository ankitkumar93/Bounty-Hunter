package game;

import characters.Thief;
import constants.Constants;
import ds.KinematicDS;
import environment.Environment;
import processing.core.*;
import shapes.BountyShape;
import shapes.ThiefShape;

public class Game extends PApplet{
	
	/* Class Data */
	private Environment environment;
	private Thief thief;
	
	//Shapes
	private ThiefShape thiefShape;
	private BountyShape bountyShape;
	
	//Kinematic DS
	private KinematicDS kinematicThief;
	private KinematicDS kinematicBounty;
	
	//Positional Data
	private int[] thiefPosition;
	private int[] bountyPosition;
	
	
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
		
		//Initialize Position
		thiefPosition = environment.getThiefPosition();
		bountyPosition = environment.getBountyHunterPosition();
		
		//Initialize Kinematic DS
		kinematicBounty = new KinematicDS(thiefPosition, Constants.BOUNTYHUNTERRORIENTATION);
		kinematicThief = new KinematicDS(bountyPosition, Constants.THIEFORIENTATION);
		
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
		translate(kinematicThief.position.x, kinematicThief.position.y);
		rotate(kinematicThief.orientation);
		thiefShape.drawShape();
		popMatrix();
	}
		
	//Draw bounty
	public void drawBountyHunter(){
		
		pushMatrix();
		translate(kinematicBounty.position.x, kinematicBounty.position.y);
		rotate(kinematicBounty.orientation);
		bountyShape.drawShape();
		popMatrix();
	}
	
	/* Main */
	public static void main(String args[]){
		PApplet.main(new String[]{"game.Game"});
	}
}
