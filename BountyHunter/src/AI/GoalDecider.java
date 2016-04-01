/* Game - Bounty Hunter */
/* Thief AI Goal Decider Class for the Game */
/* Designed By Ankit Kumar */

package AI;

import java.util.ArrayList;
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
		
		while(!stack.isEmpty()){
			
			//Get Node and Neighbors
			Node current = stack.pop();
			Node top = current.getNeighbour(Constants.TOP);
			Node bottom = current.getNeighbour(Constants.BOTTOM);
			Node left = current.getNeighbour(Constants.LEFT);
			Node right = current.getNeighbour(Constants.RIGHT);
			
			//Push Neighbors
			if(top != null)
				stack.push(top);
			if(bottom != null)
				stack.push(bottom);
			if(left != null)
				stack.push(left);
			if(right != null)
				stack.push(right);
			
			//Check Node
			if(current.getState() == Constants.COIN)
				output.add(current);
			
		}
		
		return output;
	}
	
	//Decide the Goal
	private Node decideGoal(List<Node> possibleGoal, int[] bountyPosition){
		Node output = null;
		int maxDist = Integer.MIN_VALUE;
		for(Node target: possibleGoal){
			int tempDist = getDist(target, bountyPosition);
			if(tempDist > maxDist){
				output = target;
				maxDist = tempDist;
			}
		}
		
		return output;
	}
	
	/* Public Functions */
	//Copnstructor
	public GoalDecider(Graph graph){
		
		//copy parameters
		this.graph = graph;
	}
	
	public Node update(int[] bountyPosition){
		Node goal = null;
		
		List<Node> possibleGoals = getPossibleGoals();
		goal = decideGoal(possibleGoals, bountyPosition);
		
		return goal;
	}
}
