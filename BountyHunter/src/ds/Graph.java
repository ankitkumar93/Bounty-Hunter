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
	
	//Cell Dimensions
	private int cellWidth;
	private int cellHeight;
	
	public Graph(int state, int cellWidth, int cellHeight){
		int coord[] = {0, 0};
		position = new Node(state, coord);
		
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
	}
	
	//Localize
	public int[] localize(int[] graphPosition){
		int[] localPosition = new int[2];
		
		localPosition[0] = cellWidth*graphPosition[0] + cellWidth/2;
		localPosition[1] = cellHeight*graphPosition[1] + cellHeight/2;
		
		return localPosition;
	}
	
	//Quantize
	public int[] quantize(int[] localPosition){
		int[] graphPosition = new int[2];
		
		graphPosition[0] = localPosition[0]/cellWidth;
		graphPosition[1] = localPosition[1]/cellHeight;
		
		return graphPosition;
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
		int currentnode = (mid*size) + mid;
		
		int[] nodePosition = position.getPosition();
		
		int[] offset = {mid - nodePosition[1], mid - nodePosition[0]};
		
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
			
			boolean reachable = (x - 1 >= 0 && nodes[left[0]][left[1]] != Constants.OUTOFBOUNDS && nodes[left[0]][left[1]] != Constants.OBSTACLE);
			
			//Left Node
			if(reachable){
				int neighbor = left[1] + (left[0] *size);
				Node leftNeighbor = null;
				if(nodeMap.containsKey(neighbor)){
					leftNeighbor = nodeMap.get(neighbor);
					current.setNeighbour(Constants.LEFT, leftNeighbor);
				}else if(current.getNeighbour(Constants.LEFT) != null){
					nodeMap.put(neighbor, current.getNeighbour(Constants.LEFT));
				}else{
					int[] coord = {left[1] - offset[1], left[0] - offset[0]};
					leftNeighbor = new Node(nodes[left[0]][left[1]], coord);
					current.setNeighbour(Constants.LEFT, leftNeighbor);
					nodeQueue.add(leftNeighbor);
					indexQueue.add(neighbor);
					nodeMap.put(neighbor, leftNeighbor);
				}
			}
			
			reachable = (x + 1 < size && nodes[right[0]][right[1]] != Constants.OUTOFBOUNDS && nodes[right[0]][right[1]] != Constants.OBSTACLE);
			
			//Right Node
			if(reachable){
				int neighbor = right[1] + (right[0] *size);
				Node rightNeighbor = null;
				if(nodeMap.containsKey(neighbor)){
					rightNeighbor = nodeMap.get(neighbor);
					current.setNeighbour(Constants.RIGHT, rightNeighbor);
				}else if(current.getNeighbour(Constants.RIGHT) != null){
					nodeMap.put(neighbor, current.getNeighbour(Constants.RIGHT));
				}else{
					int[] coord = {right[1] - offset[1], right[0] - offset[0]};
					rightNeighbor = new Node(nodes[right[0]][right[1]], coord);
					current.setNeighbour(Constants.RIGHT, rightNeighbor);
					nodeQueue.add(rightNeighbor);
					indexQueue.add(neighbor);
					nodeMap.put(neighbor, rightNeighbor);
				}
			}
			
			reachable = (y - 1 >= 0 && nodes[top[0]][top[1]] != Constants.OUTOFBOUNDS && nodes[top[0]][top[1]] != Constants.OBSTACLE);
			
			//Top Node
			if(reachable){
				int neighbor = top[1] + (top[0] *size);
				Node topNeighbor = null;
				if(nodeMap.containsKey(neighbor)){
					topNeighbor = nodeMap.get(neighbor);
					current.setNeighbour(Constants.TOP, topNeighbor);
				}else if(current.getNeighbour(Constants.TOP) != null){
					nodeMap.put(neighbor, current.getNeighbour(Constants.TOP));
				}else{
					int[] coord = {top[1] - offset[1], top[0] - offset[0]};
					topNeighbor = new Node(nodes[top[0]][top[1]], coord);
					current.setNeighbour(Constants.TOP, topNeighbor);
					nodeQueue.add(topNeighbor);
					indexQueue.add(neighbor);
					nodeMap.put(neighbor, topNeighbor);
				}
			}
			
			reachable = (y + 1 < size && nodes[bottom[0]][bottom[1]] != Constants.OUTOFBOUNDS && nodes[bottom[0]][bottom[1]] != Constants.OBSTACLE);
			
			//Bottom Node
			if(reachable){
				int neighbor = bottom[1] + (bottom[0] *size);
				Node bottomNeighbor = null;
				if(nodeMap.containsKey(neighbor)){
					bottomNeighbor = nodeMap.get(neighbor);
					current.setNeighbour(Constants.BOTTOM, bottomNeighbor);
				}else if(current.getNeighbour(Constants.BOTTOM) != null){
					nodeMap.put(neighbor, current.getNeighbour(Constants.BOTTOM));
				}else{
					int[] coord = {bottom[1] - offset[1], bottom[0] - offset[0]};
					bottomNeighbor = new Node(nodes[bottom[0]][bottom[1]], coord);
					current.setNeighbour(Constants.BOTTOM, bottomNeighbor);
					nodeQueue.add(bottomNeighbor);
					indexQueue.add(neighbor);
					nodeMap.put(neighbor, bottomNeighbor);
				}
			}
			
		}
		
	}
}
