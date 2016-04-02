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
	private int cellWidth;
	private int cellHeight;
	
	/* Class Data */
	
	//Position Holders
	private int[] thiefPosition;
	private int[] bountyPosition;
	
	//Data Structure
	private EnvironMap graph;
	
	//Shape Holders
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
				else if(graph.matrix[i][j] == Constants.OBSTACLE)
					obstacleShape.drawShape();
				
				//Remove Translate
				mainApplet.popMatrix();
			}
		}
	}
	
	//Get Total Coins in the Environment
	public int getCoins(){
		int coins = 0;
		for(int i = 0; i < graph.size; i++){
			for(int j = 0; j < graph.size; j++){
				if(graph.matrix[i][j] == Constants.COIN)
					coins++;
			}
		}
		
		return coins;
	}
	
	//Get Relative Position of the Bounty Hunter From the Thief
	public int[] getBountyRelativePosition(){
		int[] relativePosition = new int[2];
		
		relativePosition[0] = bountyPosition[0] - thiefPosition[0];
		relativePosition[1] = bountyPosition[1] - thiefPosition[1];
		
		return relativePosition;
	}
	
	public int[] getThiefPosition(){
		return graph.localize(thiefPosition[0], thiefPosition[1]);
	}
	
	public int[] getBountyHunterPosition(){
		return graph.localize(bountyPosition[0], bountyPosition[1]);
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
			
			int offsetY = thiefPosition[1] + (i - neighborRadius);
			
			for(int j = 0; j < matrixSize; j++){
				
				int offsetX = thiefPosition[0] + (j - neighborRadius);
				
				if(((offsetX >= 0) && (offsetX < graph.size)) && ((offsetY >= 0) && (offsetY < graph.size)))
					neighborMatrix[i][j] = graph.matrix[offsetX][offsetY];
				else
					neighborMatrix[i][j] = Constants.OUTOFBOUNDS;
			}
		}
		
		return neighborMatrix;
	}
	
	//Update State of the Desired Node
	public void updateState(int[] target, int state){
		int[] targetNode = graph.quantize(target[0], target[1]);
		graph.setState(targetNode[0], targetNode[1], state);
	}
	
	//Update Position of Thief
	public void updateThiefPosition(int[] target, int state){
		graph.setState(thiefPosition[0], thiefPosition[1], state);
		thiefPosition = graph.quantize(target[0], target[1]);
	}
	
	//Update Position of Bounty Hunter
	public void updateBountyHunterPosition(int[] target){
		bountyPosition = target.clone();
	}
	
	
	/* Private Helper Functions */
	
	/*Initialization Functions */
	//Initialize Position of Thief and Bounty Hunter
	private void initializePosition(){
		thiefPosition = new int[2];
		bountyPosition = new int[2];
		
		//Thief at the First Node
		thiefPosition = Constants.THIEFPOS;
		
		//Bounty Hunter at the Last Node
		bountyPosition = Constants.BOUNTYHUNTERPOS;
	}
	
	
	//Initialize Graph
	private void initializeGraph(){
		cellWidth = Constants.CELLWIDTH;
		cellHeight = Constants.CELLHEIGHT;
		graph = new EnvironMap(HARDCODED_GRAPH, HARDCODED_GRAPH_SIZE, cellWidth, cellHeight);
		
		//Put Thief and Bounty Hunter on the Graph
		graph.matrix[thiefPosition[0]][thiefPosition[1]] = Constants.THIEF;
		graph.matrix[bountyPosition[0]][bountyPosition[1]] = Constants.BOUNTYHUNTER;
	}
	
	//Initialize Shapes for the Objects in the Environment
	private void initializeShapes(){
		coinShape = new CoinShape(mainApplet);
		obstacleShape = new ObstacleShape(mainApplet);
	}
}
