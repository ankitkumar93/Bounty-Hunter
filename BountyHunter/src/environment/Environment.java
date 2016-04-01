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
			{2,2,2,2,2,2,2,2,2,2},
			{2,1,1,1,1,1,1,2,1,2},
			{2,2,2,2,2,2,2,2,1,2},
			{2,1,2,1,1,2,1,2,1,2},
			{2,1,2,1,1,2,1,2,1,2},
			{2,1,2,2,2,2,1,2,1,2},
			{2,1,2,1,1,1,1,2,1,2},
			{2,1,2,2,2,2,2,2,2,2},
			{2,1,2,1,1,1,1,1,1,2},
			{2,2,2,2,2,2,2,2,2,2}			
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
	
	
	//Draw the Environment
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
	
	//Get Relative Position of the Bounty Hunter From the Thief
	public int[] getBountyRelativePosition(){
		int[] relativePosition = new int[2];
		
		relativePosition[0] = bountyPosition[0] - thiefPosition[0];
		relativePosition[1] = bountyPosition[1] - thiefPosition[1];
		
		return relativePosition;
	}
	
	//Get Environment Map
	public EnvironMap getEnvMap(){
		return this.graph;
	}
	
	//Get Neighbors for Thief
	public int[][] getNeighbours(int neighborRadius){
		int matrixSize = (neighborRadius*2) + 1;
		int[][] neighborMatrix = new int[matrixSize][];
		
		for(int i = 0; i < matrixSize; i++){
			
			neighborMatrix[i] = new int[matrixSize];
			
			int offsetX = thiefPosition[0] + (i - neighborRadius);
			
			for(int j = 0; j < matrixSize; j++){
				
				int offsetY = thiefPosition[1] + (j - neighborRadius);
				
				if(((offsetX >= 0) && (offsetX < graph.size)) && ((offsetY >= 0) && (offsetY < graph.size)))
					neighborMatrix[i][j] = graph.matrix[offsetX][offsetY];
				else
					neighborMatrix[i][j] = Constants.OUTOFBOUNDS;
				
			}
		}
		
		return neighborMatrix;
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
