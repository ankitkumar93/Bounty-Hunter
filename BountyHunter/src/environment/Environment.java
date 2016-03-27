/* Game - Bounty Hunter */
/* Enviroment Class for the Game */
/* Designed By Wanqiu Li */

package environment;

import constants.Constants;
import ds.EnvironMap;
import processing.core.*;
import shapes.*;

public class Environment{
	
	/* Processing Variables */
	private PApplet mainApplet;
	
	/* Hard coded Graph */
	private final int[][] HARDCODED_GRAPH = {
			{0,0,2,2,2,2,2,0,0,0},
			{0,1,1,1,1,1,1,0,1,0},
			{0,0,0,0,0,0,0,2,1,0},
			{0,1,0,1,1,0,1,2,1,2},
			{0,1,0,1,1,0,1,2,1,2},
			{2,1,0,0,0,0,1,0,1,2},
			{2,1,0,1,1,1,1,0,1,0},
			{2,1,0,0,0,0,0,0,0,0},
			{2,1,0,1,1,1,1,1,1,0},
			{2,0,0,0,0,2,2,0,0,0}			
	};
	private final int HARDCODED_GRAPH_SIZE = 10;
	private final int cellWidth = 50;
	private final int cellHeight = 50;
	
	/* Class Data */
	
	//Position Holders
	private int[] thiefPosition;
	private int[] bountyPosition;
	
	//Data Structure
	private EnvironMap graph;
	
	//Shape Holders
	private ThiefShape thiefShape;
	private BountyShape bountyShape;
	private CoinShape coinShape;
	private ObstacleShape obstacleShape;
	
	/* Class Functions */
	
	/* Public API Functions */
	
	//Constructor
	public Environment(PApplet myApplet){
		//Copy parameters
		mainApplet = myApplet;
		
		//Initialize Position
		initializePosition();
		
		//Initialize Graph
		initializeGraph();
		
		//Initialize Shapes
		initializeShapes();
	}
	
	public void draw(){
		for(int i = 0; i < graph.size; i++){
			for(int j = 0; j < graph.size; j++){
				
				//Get Position on Screen
				int position[] = graph.localize(i, j);
				
				//Translate
				mainApplet.pushMatrix();
				mainApplet.translate(position[0], position[1]);
				
				//Draw according to State
				if(graph.matrix[i][j] == Constants.COIN)
					coinShape.drawShape();
				else if(graph.matrix[i][j] == Constants.THIEF)
					thiefShape.drawShape();
				else if(graph.matrix[i][j] == Constants.OBSTACLE)
					obstacleShape.drawShape();
				else if(graph.matrix[i][j] == Constants.BOUNTYHUNTER)
					bountyShape.drawShape();
				
				//Remove Translate
				mainApplet.popMatrix();
			}
		}
	}
	
	
	/* Private Helper Functions */
	
	/*Initialization Functions */
	//Initialize Position of Thief and Bounty Hunter
	private void initializePosition(){
		thiefPosition = new int[2];
		bountyPosition = new int[2];
		
		//Thief at the First Node
		thiefPosition[0] = 0;
		thiefPosition[1] = 0;
		
		//Bounty Hunter at the Last Node
		bountyPosition[0] = 9;
		bountyPosition[1] = 9;
	}
	
	
	//Initialize Graph
	private void initializeGraph(){
		graph = new EnvironMap(HARDCODED_GRAPH, HARDCODED_GRAPH_SIZE, cellWidth, cellHeight);
		
		//Put Thief and Bounty Hunter on the Graph
		graph.matrix[thiefPosition[0]][thiefPosition[1]] = Constants.THIEF;
		graph.matrix[bountyPosition[0]][bountyPosition[1]] = Constants.BOUNTYHUNTER;
	}
	
	//Initialize Shapes for the Objects in the Environment
	private void initializeShapes(){
		thiefShape = new ThiefShape(mainApplet);
		bountyShape = new BountyShape(mainApplet);
		coinShape = new CoinShape(mainApplet);
		obstacleShape = new ObstacleShape(mainApplet);
	}
}
