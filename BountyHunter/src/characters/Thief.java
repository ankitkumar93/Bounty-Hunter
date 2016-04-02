/* Game - Bounty Hunter */
/* Thief's AI Class for Controlling Thief */
/* Designed By Ankit Kumar */

package characters;

import java.util.List;

import AI.GoalDecider;
import AI.PathFinder;
import AI.PathFollower;
import constants.Constants;
import ds.Graph;
import ds.Node;
import environment.Environment;

public class Thief {
	//Graph
	private Graph graph;
	
	//Parameters of Game
	private int coins;
	
	//Parameters of Control
	private int radiusOfVision;
	
	//Dependent parameters
	private int neighbourMatrixSize;
	
	//Cell Dimensions
	private int cellWidth;
	private int cellHeight;
	
	//Last Known Bounty Hunter's Position
	int[] bountyPosition;
	
	//Environment (Limit Access)
	private Environment environment;
	
	/* AI Modules */
	private GoalDecider goalDecider;
	private PathFinder pathFinder;
	private PathFollower pathFollower;
	
	private boolean goalFlag;
	private boolean decisionFlag;
	
	//Constructor
	public Thief(int[] bountyPosition, Environment environment){
		//Initialize Cell Dimensions
		cellWidth = Constants.CELLWIDTH;
		cellHeight = Constants.CELLHEIGHT;
		
		//Initialize graph
		graph = new Graph(Constants.THIEF, cellWidth, cellHeight);
		
		//Copy environment
		this.environment = environment;
		
		//set tunable parameters
		radiusOfVision = Constants.ROVTHIEF;
		
		//Initialize parameters
		coins = 0;
		neighbourMatrixSize = radiusOfVision*2 + 1;
		this.bountyPosition = bountyPosition;
		this.decisionFlag = true;
		this.goalFlag = false;
		
		//Initialize AI Modules
		goalDecider = new GoalDecider(graph);
		pathFinder = new PathFinder(graph);
		pathFollower = new PathFollower();
		
		//Update the Graph Initially
		updateGraph();
	}
	
	//AI Updation
	public void update(){
		//AI Updation Method
		if(decisionFlag){
		
			//Compute the Goal
			Node goal = goalDecider.update(bountyPosition);
			
			List<Node> path = pathFinder.search(goal);
			
			pathFollower.setPath(path);
			
			goalFlag = true;
			decisionFlag = false;
		}
	}
	
	//Get Next Point
	public int[] getNextTarget(){
		int[] nextTarget = null;
		
		Node target = pathFollower.getNextTarget(graph.getPosition());
		if(target != null){
			nextTarget = graph.localize(target.getPosition());
		}else
			goalFlag = false;
		
		if(nextTarget == null)
			goalFlag = false;
		
		return nextTarget;
	}
	
	//Move to a Node
	public void move(int[] target){
		int[] prevPosition = graph.getPosition().getPosition();
		int[] newPosition = graph.quantize(target);
		
		int[] directionVec = {newPosition[0] - prevPosition[0], newPosition[1] - prevPosition[1]};
		
		int direction = 0;
		
		//Calc Direction
		if(directionVec[0] == 1)
			direction = Constants.RIGHT;
		else if(directionVec[0] == -1)
			direction = Constants.LEFT;
		else if(directionVec[1] == 1)
			direction = Constants.BOTTOM;
		else if(directionVec[1] == -1)
			direction = Constants.TOP;
		
		//Set Current Position to Empty State
		Node position = graph.getPosition();
		position.setState(Constants.EMPTY);
		environment.updateThiefPosition(target, Constants.EMPTY);
		
		//Handle Movement to a Node
		graph.move(direction);
		
		//Handle New State
		Node position2 = graph.getPosition();
		if(position2.getState() == Constants.COIN){
			coins++;
		}
		position2.setState(Constants.THIEF);
		environment.updateState(target, Constants.THIEF);
		
		//Update the Graph
		updateGraph();
		
		//Enable Decision Making
		decisionFlag = true;
		
	}
	
	//Get Coins Collected
	public int getCoins(){
		return coins;
	}
	
	//Private Functions
	private void updateGraph(){
		//Ask the Environment for Neighbor Matrix
		int neighbours[][] = environment.getNeighbours(radiusOfVision);
		
		//Supply the Matrix to the Graph
		graph.populate(neighbours, neighbourMatrixSize);
	}
}
