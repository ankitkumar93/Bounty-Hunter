/* Game - Bounty Hunter */
/* Thief's AI Class for Controlling Thief */
/* Designed By Ankit Kumar */

package characters;

import java.util.List;

import AI.GoalDecider;
import behavior.PathFinder;
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
	
	//Last Known Bounty Hunter's Position
	int[] bountyPosition;
	
	//Environment (Limit Access)
	private Environment environment;
	
	/* AI Modules */
	private GoalDecider goalDecider;
	private PathFinder pathFinder;
	
	private boolean goalFlag = true;
	
	//Constructor
	public Thief(int[] bountyPosition, Environment environment){
		//Initialize graph
		graph = new Graph(Constants.THIEF);
		
		//Copy environment
		this.environment = environment;
		
		//set tunable parameters
		radiusOfVision = Constants.ROVTHIEF;
		
		//Initialize parameters
		coins = 0;
		neighbourMatrixSize = radiusOfVision*2 + 1;
		this.bountyPosition = bountyPosition;
		
		
		//Initialize AI Modules
		goalDecider = new GoalDecider(graph);
		pathFinder = new PathFinder(graph);
		
		//Update the Graph Initially
		updateGraph();
	}
	
	//AI Updation
	public void update(){
		//AI Updation Method
		if(goalFlag){
		
			//Compute the Goal
			Node goal = goalDecider.update(bountyPosition);
		
			System.out.println("Goal Position is: "  + goal.getPosition()[0] + ":" + goal.getPosition()[1]);
			
			List<Node> path = pathFinder.search(goal);
			
			for(Node pathNode : path){
				System.out.println(pathNode.getPosition()[0] + ":" + pathNode.getPosition()[1]);
			}
			
			goalFlag = false;
		}
	}
	
	//Get Next Point
	public int[] getNextTarget(){
		int[] nextTarget = null;
		
		return nextTarget;
	}
	
	//Move to a Node
	public int move(int direction){
		//Handle Movement to a Node
		graph.move(direction);
		
		
		//Handle coins
		Node position = graph.getPosition();
		if(position.getState() == Constants.COIN){
			coins++;
			position.setState(Constants.EMPTY);
		}
		
		//Update the Graph
		updateGraph();
		
		return position.getState();
		
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
