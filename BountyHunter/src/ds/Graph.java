/* Game - Bounty Hunter */
/* Graph Class for Thief */
/* Designed By Ankit Kumar */

package ds;

import java.util.LinkedList;
import java.util.Queue;

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
		
		//add initial node and index
		nodeQueue.add(position);
		xQueue.add((size - 1)/2);
		yQueue.add((size - 1)/2);
		
		//evaluate nodes till queue not empty
		while(!nodeQueue.isEmpty()){
			Node current = nodeQueue.remove();
			int x = xQueue.remove();
			int y = yQueue.remove();
			
			//Left Neighbor
			if(x - 1 >= 0){
				int coord[] = {x-1, y};
				Node leftNeighbour = new Node(nodes[x-1][y], coord);
				nodeQueue.add(leftNeighbour);
			}
			
			//Right Neighbor
			if(x + 1 < size){
				int coord[] = {x-1, y};
				Node rightNeighbour = new Node(nodes[x+1][y], coord);
				nodeQueue.add(rightNeighbour);
			}
			
			//Top Neighbor
			if(y - 1 <= 0){
				int coord[] = {x, y-1};
				Node topNeighbour = new Node(nodes[x][y-1], coord);
				nodeQueue.add(topNeighbour);
			}
			
			//Bottom Neighbor
			if(y + 1 < size){
				int coord[] = {x, y+1};
				Node bottomNeighbour = new Node(nodes[x][y+1], coord);
				nodeQueue.add(bottomNeighbour);
			}
		}
	}
}
