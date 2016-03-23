/* Game - Bounty Hunter */
/* Graph Class for Thief */
/* Designed By Ankit Kumar */

package ds;

import java.util.LinkedList;
import java.util.Queue;

import constants.Constants;

//Implement the Class
public class Graph {
	
	//Position in Graph
	private Node position;
	
	public Graph(int state){
		int coord[] = {0, 0};
		position = new Node(state, coord);
	}
	
	public void move(int flag){
		position = position.getNeighbour(flag);
	}
	
	public Node getPosition(){
		return position;
	}
	
	public void populate(int[][] nodes, int size){
		//init queues
		Queue<Node> nodeQueue = new LinkedList<Node>();
		Queue<Integer> xQueue = new LinkedList<Integer>();
		Queue<Integer> yQueue = new LinkedList<Integer>();
		
		//get current position
		int mid = (size - 1)/2;
		int[] pos = position.getPosition();
		int[] offset = {pos[0] - mid, pos[1] - mid};
		
		//add initial node and index
		nodeQueue.add(position);
		xQueue.add(mid);
		yQueue.add(mid);
		
		//evaluate nodes till queue not empty
		while(!nodeQueue.isEmpty()){
			Node current = nodeQueue.remove();
			int x = xQueue.remove();
			int y = yQueue.remove();
			
			//Left Neighbor
			if(x - 1 >= 0){
				
				//Init Neighbor
				int coord[] = {x-1 + offset[0], y + offset[1]};
				Node leftNeighbour = new Node(nodes[x-1][y], coord);
				
				//Add to Queue
				nodeQueue.add(leftNeighbour);
				xQueue.add(x-1);
				yQueue.add(y);
				
				//Assign Neighbor
				current.setNeighbour(Constants.LEFT, leftNeighbour);
			}
			
			//Right Neighbor
			if(x + 1 < size){
				
				//Init Neighbor
				int coord[] = {x+1 + offset[0], y + offset[1]};
				Node rightNeighbour = new Node(nodes[x+1][y], coord);
				
				//Add to Queue
				nodeQueue.add(rightNeighbour);
				xQueue.add(x+1);
				yQueue.add(y);
				
				//Assign Neighbor
				current.setNeighbour(Constants.RIGHT, rightNeighbour);
			}
			
			//Top Neighbor
			if(y - 1 <= 0){
				
				//Init Neighbor
				int coord[] = {x + offset[0], y-1 + offset[1]};
				Node topNeighbour = new Node(nodes[x][y-1], coord);
				
				//Add to Queue
				nodeQueue.add(topNeighbour);
				xQueue.add(x);
				yQueue.add(y-1);
				
				//Assign Neighbor
				current.setNeighbour(Constants.TOP, topNeighbour);
			}
			
			//Bottom Neighbor
			if(y + 1 < size){
				
				//Init Neighbor
				int coord[] = {x + offset[0], y+1 + offset[1]};
				Node bottomNeighbour = new Node(nodes[x][y+1], coord);
				
				//Add to Queue
				nodeQueue.add(bottomNeighbour);
				xQueue.add(x);
				yQueue.add(y+1);
				
				//Assign Neighbor
				current.setNeighbour(Constants.BOTTOM, bottomNeighbour);
			}
		}
	}
}
