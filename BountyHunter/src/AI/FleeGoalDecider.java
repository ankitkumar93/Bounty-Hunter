package AI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import constants.Constants;
import ds.Graph;
import ds.Node;

public class FleeGoalDecider {
	/* Private Variables */
	private Graph graph;
	private int radiusOfFlee;
	
	/* Private Functions */
	//Compute Distance between two Nodes
	private int getDist(Node a, int[] b_coord){
		int dist = 0;
		
		int a_coord[] = a.getPosition();
		
		dist =  Math.abs(a_coord[0] - b_coord[0]) + Math.abs(a_coord[1] - b_coord[1]);
		
		return dist;
	}
	
	//Check if Inrange
	public boolean inrange(int[] thiefPosition, Node node){
		int[] position = node.getPosition();
		int xDiff = Math.abs(thiefPosition[0] - position[0]);
		int yDiff = Math.abs(thiefPosition[1] - position[1]);
		
		if(xDiff < this.radiusOfFlee && yDiff < this.radiusOfFlee)
			return true;
		
		return false;
	}
	
	//Compute possible goal states
	private List<Node> getPossibleGoals(int[] thiefPosition){
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
			if(top != null && !visited.contains(top) && inrange(thiefPosition, top))
				stack.push(top);
			if(bottom != null && !visited.contains(bottom) && inrange(thiefPosition, bottom))
				stack.push(bottom);
			if(left != null && !visited.contains(left) && inrange(thiefPosition, left))
				stack.push(left);
			if(right != null && !visited.contains(right) && inrange(thiefPosition, right))
				stack.push(right);
			
			output.add(current);
			
			visited.add(current);
		
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
	//Constructor
	public FleeGoalDecider(Graph graph){
		this.graph = graph;
		this.radiusOfFlee = Constants.ROFTHIEF;
	}
	
	public Node update(int[] bountyPosition, int[] thiefPosition){
		Node goal = null;
		
		List<Node> possibleGoals = getPossibleGoals(thiefPosition);
		goal = decideGoal(possibleGoals, bountyPosition);
		
		return goal;
	}
	
}
