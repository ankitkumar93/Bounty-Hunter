/* Game - Bounty Hunter */
/* Thief's AI Class for Controlling Thief */
/* Designed By Ankit Kumar */

package characters;

import java.util.Arrays;
import java.util.List;

import AI.DecisionMaking;
import AI.FleeGoalDecider;
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
	
	//Last Known Bounty Hunter's Direction
	int bountyDirection;
	
	//Confidence
	private int confidence;
	
	//Probability of Flee
	private float probabilityFlee;
	
	//Frame Data
	private int frameID;
	
	//Environment (Limit Access)
	private Environment environment;
	
	/* AI Modules */
	private GoalDecider goalDecider;
	private FleeGoalDecider fleeGoalDecider;
	private PathFinder pathFinder;
	private PathFollower pathFollower;
	private DecisionMaking decisionMaking;
	
	private boolean goalFlag;
	private boolean decisionFlag;
	private boolean fleeGoalFlag;
	
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
		this.radiusOfVision = Constants.ROVTHIEF;
		
		//Initialize parameters
		this.coins = 0;
		this.neighbourMatrixSize = radiusOfVision*2 + 1;
		this.bountyPosition = bountyPosition;
		this.decisionFlag = true;
		this.goalFlag = false;
		this.fleeGoalFlag = false;
		this.frameID = 0;
		this.confidence = Constants.CONFIDENCE_MAX;
		this.bountyDirection = Constants.DONTMOVE;
		this.probabilityFlee = Constants.FLEE_PROBABILITY_MAX;
		
		//Initialize AI Modules
		goalDecider = new GoalDecider(graph);
		fleeGoalDecider = new FleeGoalDecider(graph);
		pathFinder = new PathFinder(graph);
		pathFollower = new PathFollower();
		decisionMaking = new DecisionMaking();
		
		//Update the Graph Initially
		updateGraph();
	}
	
	//AI Updation
	public void update(){
		frameID++;
		//AI Updation Method
		if(decisionFlag){
			decisionFlag = false;
			
			Node goal = null;
			
			int decision = decisionMaking.update(bountyPosition, graph.getPosition().getPosition(), goalFlag, fleeGoalFlag, probabilityFlee);
			if(decision == Constants.CONTINUE)
				return;
			
			goal = getNewGoal(decision);
			goalFlag = true;
			
			if(goal == null){
				return;
			}
			
			
			System.out.println("Frame: " + frameID + " Goal :" + goal.toString());
			
			//Path Finding
			List<Node> path = pathFinder.search(goal, bountyPosition, this.bountyDirection, this.confidence);
			
			//Setup Path Follower
			pathFollower.setPath(path);
		}
		
		//Check for Bounty Seen
		if(environment.checkBountyPositionChanged()){
			updateBountyPosition(environment.getBountyRelativePosition(), environment.getBountyDirection());
		}
				
	}
	
	//Get Next Point
	public int[] getNextTarget(){
		int[] nextTarget = null;
		
		Node target = pathFollower.getNextTarget(graph.getPosition());
		if(target != null){
			nextTarget = graph.localize(target.getPosition());
		}else{
			goalFlag = false;
			decisionFlag = true;
		}
		
		return nextTarget;
	}
	
	//Get Target Orientation
	public float getTargetOrientation(int[] target){
		int[] currentPosition = graph.localize(graph.getPosition().getPosition());
		float targetOrientation =  (float)Math.atan2(target[1] - currentPosition[1], target[0] - currentPosition[0]);
		
		return targetOrientation;
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
		
		//Update Confidence
		this.confidence = Math.max(this.confidence - Constants.CONFIDENCE_DECAY_RATE , 0);
		
		//Enable Decision Making
		decisionFlag = true;
	}
	
	//Get Coins Collected
	public int getCoins(){
		return coins;
	}
	
	/* Private Functions */
	
	//Update Graph
	private void updateGraph(){
		//Ask the Environment for Neighbor Matrix
		int neighbours[][] = environment.getNeighbours(radiusOfVision);
		
		//Supply the Matrix to the Graph
		graph.populate(neighbours, neighbourMatrixSize);
	}
	
	//Change Bounty's Local Position
	private void updateBountyPosition(int[] relativePosition, int bountyDirection){
		int[] thiefPosition = graph.getPosition().getPosition();
		int[] bountyPositionOld = bountyPosition.clone();
		bountyPosition[0] = thiefPosition[0] + relativePosition[0];
		bountyPosition[1] = thiefPosition[1] + relativePosition[1];
		this.confidence = Constants.CONFIDENCE_MAX;
		this.bountyDirection = bountyDirection;
		if(!Arrays.equals(bountyPositionOld, bountyPosition)){
			this.probabilityFlee = Constants.FLEE_PROBABILITY_MAX;
		}else{
			this.probabilityFlee -= Constants.FLEE_PROBABILITY_DECAY_RATE;
		}
	}
	
	/* Decision Functions */
	
	//Compute a New Goal
	public Node getNewGoal(int decision){
		Node goal = null;
		if(decision ==  Constants.NEWGOAL){
			goal = goalDecider.update(bountyPosition, graph.getPosition().getPosition(), this.confidence);
			this.fleeGoalFlag = false;
		}
		else if(decision == Constants.FLEEALERT){
			goal = fleeGoalDecider.update(bountyPosition, graph.getPosition().getPosition());
			this.fleeGoalFlag = true;
		}
			
		return goal;
	}
}