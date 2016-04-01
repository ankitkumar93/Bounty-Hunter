/* Game - Bounty Hunter */
/* Graph Class for Thief */
/* Designed By Ankit Kumar */

package ds;

import java.util.HashMap;
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
		
		//Construct Hash-Map of Nodes
		HashMap<Integer, Node> nodeMap = new HashMap<Integer, Node>();
		
		int mid = (size - 1)/2;
		int currentnode = (mid)*size + mid;
		
		nodeMap.put(currentnode, position);
		
		Queue<Node> nodeQueue = new LinkedList<Node>();
		nodeQueue.add(position);
		
		Queue<Integer> indexQueue = new LinkedList<Integer>();
		indexQueue.add(currentnode);
		
		while(!nodeQueue.isEmpty()){
			int index = indexQueue.remove();
			Node current = nodeQueue.remove();
			
			int x = index%size;
			int y = index/size;
			
			//Neighbor Indexes
			int[] left = {y, x - 1};
			int[] right = {y, x + 1};
			int[] top = {y-1, x};
			int[] bottom = {y+1, x};
			
			//Left Node
			if(x - 1 >= 0 && nodes[left[0]][left[1]] != -1){
				int neighbor = left[0] + (left[1] *size);
				Node leftNeighbor = null;
				if(nodeMap.containsKey(neighbor)){
					leftNeighbor = nodeMap.get(neighbor);
					current.setNeighbour(Constants.LEFT, leftNeighbor);
				}
				else{
					leftNeighbor = new Node(nodes[left[0]][left[1]], left);
					current.setNeighbour(Constants.LEFT, leftNeighbor);
					nodeQueue.add(leftNeighbor);
					indexQueue.add(neighbor);
					nodeMap.put(neighbor, leftNeighbor);
				}
			}
			
			//Right Node
			if(x + 1 < size && nodes[right[0]][right[1]] != -1){
				int neighbor = right[0] + (right[1] *size);
				Node rightNeighbor = null;
				if(nodeMap.containsKey(neighbor)){
					rightNeighbor = nodeMap.get(neighbor);
					current.setNeighbour(Constants.RIGHT, rightNeighbor);
				}
				else{
					rightNeighbor = new Node(nodes[right[0]][right[1]], right);
					current.setNeighbour(Constants.RIGHT, rightNeighbor);
					nodeQueue.add(rightNeighbor);
					indexQueue.add(neighbor);
					nodeMap.put(neighbor, rightNeighbor);
				}
			}
			
			//Top Node
			if(y - 1 >= 0 && nodes[top[0]][top[1]] != -1){
				int neighbor = top[0] + (top[1] *size);
				Node topNeighbor = null;
				if(nodeMap.containsKey(neighbor)){
					topNeighbor = nodeMap.get(neighbor);
					current.setNeighbour(Constants.TOP, topNeighbor);
				}
				else{
					topNeighbor = new Node(nodes[top[0]][top[1]], top);
					current.setNeighbour(Constants.TOP, topNeighbor);
					nodeQueue.add(topNeighbor);
					indexQueue.add(neighbor);
					nodeMap.put(neighbor, topNeighbor);
				}
			}
			
			//Bottom Node
			if(y + 1 < size && nodes[bottom[0]][bottom[1]] != -1){
				int neighbor = bottom[0] + (bottom[1] *size);
				Node bottomNeighbor = null;
				if(nodeMap.containsKey(neighbor)){
					bottomNeighbor = nodeMap.get(neighbor);
					current.setNeighbour(Constants.BOTTOM, bottomNeighbor);
				}
				else{
					bottomNeighbor = new Node(nodes[bottom[0]][bottom[1]], bottom);
					current.setNeighbour(Constants.BOTTOM, bottomNeighbor);
					nodeQueue.add(bottomNeighbor);
					indexQueue.add(neighbor);
					nodeMap.put(neighbor, bottomNeighbor);
				}
			}
			
		}
		
	}
}
