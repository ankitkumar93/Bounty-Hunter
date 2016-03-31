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
	private int getDist(Node a, Node b){
		int dist = 0;
		
		int a_coord[] = a.getPosition();
		int b_coord[] = b.getPosition();
		
		dist =  Math.abs(a_coord[0] - b_coord[0]) + Math.abs(a_coord[1] - b_coord[1]);
		
		return dist;
	}
	
	//Compute Minimum Distance from all Predictions
	private int getMinDist(Node target, List<Node> predictedLocations){
		int minDist = Integer.MAX_VALUE;
		
		for(Node location: predictedLocations){
			int tempDist = getDist(target, location);
			if(tempDist > minDist)
				minDist = tempDist;
		}
		
		return minDist;
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
	private Node decideGoal(List<Node> possibleGoal, List<Node> predictedLocations){
		Node output = null;
		int minDist = Integer.MAX_VALUE;
		for(Node target: possibleGoal){
			int tempDist = getMinDist(target, predictedLocations);
			if(tempDist < minDist){
				output = target;
				minDist = tempDist;
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
	
	public Node update(List<Node> predictedLocations){
		Node goal = null;
		
		List<Node> possibleGoals = getPossibleGoals();
		goal = decideGoal(possibleGoals, predictedLocations);
		
		return goal;
	}
}
