package game;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

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
	private ThiefVision thiefVision;
	
	//Shapes
	private ThiefShape thiefShape;
	private BountyShape bountyShape;
	
	//Kinematic DS
	private KinematicDS kinematicThief;
	private KinematicDS kinematicBounty;
	
	//Positional Data
	private int[] thiefPosition;
	private int[] bountyPosition;
	
	//Coin Data
	private int totalCoins;
	
	//Time Data
	private int totalTime;
	private Date beginDate;
	private Date endDate;
	
	//Traversal Data
	private int nodesTraversed;
	
	
	/* Processing Data */
	private final int WINDOW_HEIGHT = 500;
	private final int WINDOW_WIDTH = 1050;
	
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
		
		//Initialize Coins
		totalCoins = environment.getCoins();
		
		//Initialize Nodes Traversed
		nodesTraversed = 0;
		
		//Initialize Kinematic DS
		kinematicBounty = new KinematicDS(bountyPosition, Constants.BOUNTYHUNTERRORIENTATION);
		kinematicThief = new KinematicDS(thiefPosition, Constants.THIEFORIENTATION);
		
		//Initialize Character
		thief = new Thief(environment.getBountyRelativePosition(), environment);
		bountyHunter = new BountyHunter(environment);
		
		//Initialize Time and Date
		totalTime = 0;
		beginDate = new Date();
		endDate = null;
		
		//Initialize Thief Vision Map
		thiefVision = new ThiefVision(this, environment.getEnvMap(), thief);
	}
	
	//Draw
	public void draw(){
		background(Constants.BGCOLOR);
		
		//Updations
		thief.update();
		
		//Movement
		moveThief();
		moveBountyHunter();
		
		//Draw Methods
		environment.draw();
		thiefVision.draw();
		drawThief();
		drawBountyHunter();
		
		//Detect Game End
		if(detectGameEnd()){
			endDate = new Date();
			totalTime = (int)(endDate.getTime() - beginDate.getTime());
			System.out.println("Coins Collected: " + thief.getCoins());
			System.out.println("Time Passed: " + totalTime);
			System.out.println("Nodes Traversed: " + nodesTraversed);
			
			writeToFile(thief.getCoins(), totalTime, nodesTraversed);
			noLoop();
		}
	}
	
	public void keyPressed(){
		if(key == CODED){
			if(keyCode == LEFT){
				bountyHunter.setDirection(Constants.MOVELEFT);
			}else if(keyCode == RIGHT){
				bountyHunter.setDirection(Constants.MOVERIGHT);
			}else if(keyCode == UP){
				bountyHunter.setDirection(Constants.MOVEUP);
			}else if(keyCode == DOWN){
				bountyHunter.setDirection(Constants.MOVEDOWN);
			}
		}
	}
	
	/* Private Functions */
	
	//Move Thief
	private void moveThief(){
		int[] target = thief.getNextTarget();
		if(target == null)
			return;
		
		float targetOrientation = thief.getTargetOrientation(target);
		kinematicThief = SeekAlign.update(kinematicThief, target, targetOrientation, Constants.MVTHIEF, Constants.MRTHIEF);
		
		int newX = (int)kinematicThief.position.x;
		int newY = (int)kinematicThief.position.y;
		
		if(newX == target[0] && newY == target[1]){
			thief.move(target);
			thiefPosition = environment.getThiefPosition();
			nodesTraversed += 1;
		}
	}
	
	//Move Bounty Hunter
	private void moveBountyHunter(){
		int[] target = bountyHunter.getNextTarget();
		if(target == null)
			return;
		
		float targetOrientation = bountyHunter.getTargetOrientation(target);
		
		kinematicBounty = SeekAlign.update(kinematicBounty, target, targetOrientation, Constants.MVBOUNTYHUNTER, Constants.MRBOUNTYHUNTER);
		
		int newX = (int)kinematicBounty.position.x;
		int newY = (int)kinematicBounty.position.y;
		
		if(newX == target[0] && newY == target[1]){
			bountyHunter.move();
			bountyPosition = environment.getBountyHunterPosition();
		}
		
	}
	
	//Draw thief
	private void drawThief(){
		
		//Draw on Main Game
		pushMatrix();
		translate(kinematicThief.position.x, kinematicThief.position.y);
		rotate(kinematicThief.orientation);
		thiefShape.drawShape();
		popMatrix();
		
		//Draw on Thief Map
		pushMatrix();
		translate(kinematicThief.position.x + 550, kinematicThief.position.y);
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
	
	//Detect Game End
	private boolean detectGameEnd(){
		int remainingCoins = totalCoins - thief.getCoins();
		if((thiefPosition[0] == bountyPosition[0]) && (thiefPosition[1] == bountyPosition[1]))
			return true;
		else if(remainingCoins == 0)
			return true;
			
		return false;
	}
	
	//Write to File
	private void writeToFile(int coinsW, int timeW, int distW){
		FileWriter outFile;
		BufferedWriter outBuf;
		PrintWriter outPrint;
		
		try{
			outFile = new FileWriter(Constants.LOG_FILE, true);
			outBuf = new BufferedWriter(outFile);
			outPrint = new PrintWriter(outBuf);
			outPrint.println(Constants.ROVTHIEF + "," + Constants.ROFTHIEF + "," + Constants.PREDICTION_TIME + "," + coinsW + "," + timeW + "," + distW);
			outPrint.close();
			outBuf.close();
			outFile.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/* Main */
	public static void main(String args[]){
		PApplet.main(new String[]{"game.Game"});
	}
}
