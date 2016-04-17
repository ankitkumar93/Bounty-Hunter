/* Game - Bounty Hunter */
/* Graph Class for Thief */
/* Designed By Ankit Kumar */

package ds;

import java.util.HashMap;
import java.util.HashSet;
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
	
	//Hash Map of Nodes Explored
	private HashMap<CoordHolder, Node> nodeMap;
	
	public Graph(int state, int cellWidth, int cellHeight){
		int coord[] = {0, 0};
		position = new Node(state, coord);
		
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		
		nodeMap = new HashMap<CoordHolder, Node>();
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
		position.updateEdgeWeight(flag, Constants.NOCOINEDGE);
		position = position.getNeighbour(flag);
	}
	
	public Node getPosition(){
		return position;
	}
	
	public void populate(int[][] nodes, int size){
		
		int mid = (size - 1)/2;
		
		int[] nodePosition = position.getPosition();
		
		int[] offset = {mid - nodePosition[0], mid - nodePosition[1]};
		
		CoordHolder nodeCoord = new CoordHolder(nodePosition[0], nodePosition[1]);
		nodeMap.put(nodeCoord, position);
		
		Queue<CoordHolder> queue = new LinkedList<CoordHolder>();
		queue.add(nodeCoord);
		
		HashSet<CoordHolder> visited = new HashSet<CoordHolder>();
		
		while(!queue.isEmpty()){
			CoordHolder currentElement = queue.remove();
			
			Node currentNode = nodeMap.get(currentElement);
			
			int[] coord = currentElement.getCoord();
			int[] index = coordToIndex(coord, offset);
			
			int[] left = index.clone();
			left[1] -= 1;
			
			int[] right = index.clone();
			right[1] += 1;
			
			int[] top = index.clone();
			top[0] -= 1;
			
			int[] bottom = index.clone();
			bottom[0] += 1;
			
			//Left
			boolean reachable = ((left[1] >= 0) && (nodes[left[0]][left[1]] != Constants.OUTOFBOUNDS) && (nodes[left[0]][left[1]] != Constants.OBSTACLE));
			
			if(reachable){
				int state = nodes[left[0]][left[1]];
				int[] neighborCoord = indexToCoord(left, offset);
				CoordHolder neighborElement = new CoordHolder(neighborCoord[0], neighborCoord[1]);
				Node neighborNode = null;
				
				if(!nodeMap.containsKey(neighborElement)){
					neighborNode = new Node(state, neighborCoord);
					nodeMap.put(neighborElement, neighborNode);
				}else
					neighborNode = nodeMap.get(neighborElement);
				
				if(currentNode.getNeighbour(Constants.LEFT) == null){
					currentNode.setNeighbour(Constants.LEFT, neighborNode);
				}
				
				if(!visited.contains(neighborElement))
					queue.add(neighborElement);
			}
			
			//Right
			reachable = ((right[1] < size) && (nodes[right[0]][right[1]] != Constants.OUTOFBOUNDS) && (nodes[right[0]][right[1]] != Constants.OBSTACLE));
			
			if(reachable){
				int state = nodes[right[0]][right[1]];
				int[] neighborCoord = indexToCoord(right, offset);
				CoordHolder neighborElement = new CoordHolder(neighborCoord[0], neighborCoord[1]);
				Node neighborNode = null;
				
				if(!nodeMap.containsKey(neighborElement)){
					neighborNode = new Node(state, neighborCoord);
					nodeMap.put(neighborElement, neighborNode);
				}else
					neighborNode = nodeMap.get(neighborElement);
				
				if(currentNode.getNeighbour(Constants.RIGHT) == null){
					currentNode.setNeighbour(Constants.RIGHT, neighborNode);
				}	
				
				if(!visited.contains(neighborElement))
					queue.add(neighborElement);
			}
			
			//Top
			reachable = ((top[0] >= 0) && (nodes[top[0]][top[1]] != Constants.OUTOFBOUNDS) && (nodes[top[0]][top[1]] != Constants.OBSTACLE));
			
			if(reachable){
				int state = nodes[top[0]][top[1]];
				int[] neighborCoord = indexToCoord(top, offset);
				CoordHolder neighborElement = new CoordHolder(neighborCoord[0], neighborCoord[1]);
				Node neighborNode = null;
				
				if(!nodeMap.containsKey(neighborElement)){
					neighborNode = new Node(state, neighborCoord);
					nodeMap.put(neighborElement, neighborNode);
				}else
					neighborNode = nodeMap.get(neighborElement);
				
				if(currentNode.getNeighbour(Constants.TOP) == null){
					currentNode.setNeighbour(Constants.TOP, neighborNode);
				}
				
				if(!visited.contains(neighborElement))
					queue.add(neighborElement);
			}
			
			//Bottom
			reachable = ((bottom[0] < size) && (nodes[bottom[0]][bottom[1]] != Constants.OUTOFBOUNDS) && (nodes[bottom[0]][bottom[1]] != Constants.OBSTACLE));
			
			if(reachable){
				int state = nodes[bottom[0]][bottom[1]];
				int[] neighborCoord = indexToCoord(bottom, offset);
				CoordHolder neighborElement = new CoordHolder(neighborCoord[0], neighborCoord[1]);
				Node neighborNode = null;
				
				if(!nodeMap.containsKey(neighborElement)){
					neighborNode = new Node(state, neighborCoord);
					nodeMap.put(neighborElement, neighborNode);
				}else
					neighborNode = nodeMap.get(neighborElement);
				
				if(currentNode.getNeighbour(Constants.BOTTOM) == null){
					currentNode.setNeighbour(Constants.BOTTOM, neighborNode);
				}	
				
				if(!visited.contains(neighborElement))
					queue.add(neighborElement);
			}
			
			visited.add(currentElement);
		}
	}
	
	/* Map Check */
	public boolean isInMap(int x, int y){
		CoordHolder checkCoord = new CoordHolder(x, y);
		return nodeMap.containsKey(checkCoord);
	}
	
	/* Coordinate Index Conversions */
	private int[] coordToIndex(int[] coord, int[] offset){
		int output[] = {coord[1] + offset[1], coord[0] + offset[0]};
		return output;
	}
	
	private int[] indexToCoord(int[] index, int[] offset){
		int output[] = {index[1] - offset[0], index[0] - offset[1]};
		return output;
	}
}
