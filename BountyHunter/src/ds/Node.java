/* Game - Bounty Hunter */
/* Node Class for Thief's Graph */
/* Designed By Ankit Kumar */

package ds;

//Import Constants
import constants.Constants;

//Implement the Class
public class Node {
	
	//Neighbour References
	private Node left;
	private Node right;
	private Node top;
	private Node bottom;
	
	//State
	private int state;
	
	//Constructor
	public Node(int state){
		this.state = state;
	}
	
	//Get Neighbours
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
		}
		
		return null;
	}
	
	//Set Neighbour
	public void setNeighbour(int flag, Node node){
		switch(flag){
			case Constants.LEFT:
				left = node;
				break;
			case Constants.RIGHT:
				right = node;
				break;
			case Constants.TOP:
				top = node;
				break;
			case Constants.BOTTOM:
				bottom = node;
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
}
