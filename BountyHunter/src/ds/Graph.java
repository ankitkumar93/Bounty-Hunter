/* Game - Bounty Hunter */
/* Graph Class for Thief */
/* Designed By Ankit Kumar */

package ds;

//Implement the Class
public class Graph {
	
	//Position in Graph
	private Node position;
	
	public Graph(int state){
		position = new Node(state);
	}
	
	public void move(int flag){
		position = position.getNeighbour(flag);
	}
	
	public Node getPosition(){
		return position;
	}
	
	public void populate(int[][] nodes, int size){
		for(int i = 0; i < size; i++){
		}
	}
}
