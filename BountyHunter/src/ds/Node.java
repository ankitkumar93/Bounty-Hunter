/* Game - Bounty Hunter */
/* Node Class for Thief's Graph */
/* Designed By Ankit Kumar */

package ds;

//Import Constants
import constants.Constants;

//Implement the Class
public class Node {
	
	//Neighbor References
	private Node left;
	private Node right;
	private Node top;
	private Node bottom;
	
	//State
	private int state;
	
	//Coordinate
	private int x;
	private int y;
	
	//Edges
	private int wl;
	private int wr;
	private int wt;
	private int wb;
	
	//Constructor
	public Node(int state, int coord[]){
		
		//Init State
		this.state = state;
		
		//Init coordinates
		x = coord[0];
		y = coord[1];
		
		//Init Weights
		wl = 0; wr = 0; wt = 0; wb = 0;
		
	}
	
	//Get Neighbors
	public Node getNeighbour(int flag){
		switch(flag){
			case Constants.LEFT: 
				return left;
			case Constants.RIGHT:
				return right;
			case Constants.TOP:
				return top;
			case Constants.BOTTOM:
				return bottom;
			default:
				return null;
		}
	}
	
	//Set Neighbor
	public void setNeighbour(int flag, Node node){
		switch(flag){
			case Constants.LEFT:
				left = node;
				wl = getWeight(node);
				break;
			case Constants.RIGHT:
				right = node;
				wr = getWeight(node);
				break;
			case Constants.TOP:
				top = node;
				wt = getWeight(node);
				break;
			case Constants.BOTTOM:
				bottom = node;
				wb = getWeight(node);
				break;
		}
	}
	
	//Get Node State
	public int getState(){
		return state;
	}
	
	//Set Node State
	public void setState(int state){
		this.state = state;
	}
	
	//Get Weight of Neighbor Edge
	public int getEdgeWeight(Node neighbor){
		if(neighbor == null)
			return 0;
		else if(neighbor == left)
			return wl;
		else if(neighbor == right)
			return wr;
		else if(neighbor == top)
			return wt;
		else
			return wb;
	}
	
	//Update the Weight of Neighbor Edge
	public void updateEdgeWeight(int direction, int edgeWeight){
		switch(direction){
			case Constants.LEFT:
				wl = edgeWeight;
				break;
			case Constants.RIGHT:
				wr = edgeWeight;
				break;
			case Constants.TOP:
				wt = edgeWeight;
				break;
			case Constants.BOTTOM:
				wb = edgeWeight;
				break;
		}
	}
	
	//Get Position of the Node
	public int[] getPosition(){
		int coord[] = {x, y};
		return coord;
	}
	
	/*Private Helper Functions*/
	private int getWeight(Node node){
		if(node.state == Constants.COIN)
			return 1;
		else
			return 2;
	}
	
	/* Overrides */
	@Override
	public String toString() {
		String str = "X:" + x + " Y:"  +y;
		return str;
	}
	
}
