/* Game - Bounty Hunter */
/* Thief AI Goal Decider Class for the Game */
/* Designed By Ankit Kumar */

package AI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import constants.Constants;
import ds.Graph;
import ds.Node;

public class GoalDecider {
	
	/* Private Variables */
	private Graph graph;
	
	/* Private Functions */
	//Compute Distance between two Nodes
	private int getDist(Node a, int[] b_coord){
		int dist = 0;
		
		int a_coord[] = a.getPosition();
		
		dist =  Math.abs(a_coord[0] - b_coord[0]) + Math.abs(a_coord[1] - b_coord[1]);
		
		return dist;
	}
	
	//Compute possible goal states
	private List<Node> getPossibleGoals(){
		List<Node> output = new ArrayList<Node>();
		
		//Do DFS
		Node source = graph.getPosition();
		Stack<Node> stack =  new Stack<Node>();
		stack.push(source);
		HashSet<Node> visited = new HashSet<Node>();
		
		while(!stack.isEmpty()){
			
			//Get Node and Neighbors
			Node current = stack.pop();
			
			Node top = current.getNeighbour(Constants.TOP);
			Node bottom = current.getNeighbour(Constants.BOTTOM);
			Node left = current.getNeighbour(Constants.LEFT);
			Node right = current.getNeighbour(Constants.RIGHT);
			
			//Push Neighbors
			if(top != null && !visited.contains(top))
				stack.push(top);
			if(bottom != null && !visited.contains(bottom))
				stack.push(bottom);
			if(left != null && !visited.contains(left))
				stack.push(left);
			if(right != null && !visited.contains(right))
				stack.push(right);
			
			//Check Node
			if(current.getState() == Constants.COIN)
				output.add(current);
			
			visited.add(current);
			
		}
		
		return output;
	}
	
	//Decide the Goal
	private Node decideGoal(List<Node> possibleGoal, int[] bountyPosition, int[] thiefPosition, int confidence){
		//System.out.println("Conf: " + confidence);
		Node output = null;
		int minDist = Integer.MAX_VALUE;
		int weightConst = Constants.COLLISION_CONTROL*confidence/100;
		for(Node target: possibleGoal){
			int tempDistBounty = getDist(target, bountyPosition);
			int tempDistThief = getDist(target, thiefPosition);
			int bountyFactor = 0;
			if(tempDistBounty == 0)
				bountyFactor = Constants.INFINITY;
			else
				bountyFactor = weightConst/tempDistBounty;
			int tempDist = tempDistThief + bountyFactor;
			if(tempDist < minDist){
				output = target;
				minDist = tempDist;
			}
		}
		
		return output;
	}
	
	/* Public Functions */
	//Constructor
	public GoalDecider(Graph graph){
		
		//copy parameters
		this.graph = graph;
	}
	
	public Node update(int[] bountyPosition, int[] thiefPosition, int confidence){
		Node goal = null;
		
		List<Node> possibleGoals = getPossibleGoals();
		goal = decideGoal(possibleGoals, bountyPosition, thiefPosition, confidence);
		
		return goal;
	}
}
