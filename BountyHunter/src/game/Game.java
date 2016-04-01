package game;

import behavior.SeekAlign;
import characters.BountyHunter;
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
	private BountyHunter bountyHunter;
	
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
		kinematicBounty = new KinematicDS(bountyPosition, Constants.BOUNTYHUNTERRORIENTATION);
		kinematicThief = new KinematicDS(thiefPosition, Constants.THIEFORIENTATION);
		
		//Initialize Character
		thief = new Thief(environment.getBountyRelativePosition(), environment);
		bountyHunter = new BountyHunter();
	}
	
	//Draw
	public void draw(){
		background(255);
		
		//Updations
		thief.update();
		
		//Movement
		moveThief();
		moveBountyHunter();
		
		//Draw Methods
		environment.draw();
		drawThief();
		drawBountyHunter();
	}
	
	/* Private Functions */
	
	//Move Thief
	private void moveThief(){
		int[] target = thief.getNextTarget();
		if(target == null)
			return;

		kinematicThief = SeekAlign.update(kinematicThief, target, Constants.MVTHIEF, Constants.MRTHIEF);
		
		int newX = (int)kinematicThief.position.x;
		int newY = (int)kinematicThief.position.y;
		
		if(newX == target[0] && newY == target[1]){
			thief.move(target);
		}
	}
	
	//Move Bounty Hunter
	private void moveBountyHunter(){
		int[] target = bountyHunter.getNextTarget();
		if(target == null)
			return;
		
		kinematicBounty = SeekAlign.update(kinematicBounty, target, Constants.MVBOUNTYHUNTER, Constants.MRBOUNTYHUNTER);
	}
	
	//Draw thief
	private void drawThief(){
		pushMatrix();
		translate(kinematicThief.position.x, kinematicThief.position.y);
		rotate(kinematicThief.orientation);
		thiefShape.drawShape();
		popMatrix();
	}
		
	//Draw bounty
	private void drawBountyHunter(){
		
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
