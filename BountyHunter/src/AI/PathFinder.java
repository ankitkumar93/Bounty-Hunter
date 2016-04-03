package AI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import behavior.Heuristic;
import constants.Constants;
import ds.Graph;
import ds.Node;
import ds.PathNode;

public class PathFinder {
	
	/* Private Data */
	private Graph graph;
	
	/* Public Functions */
	public PathFinder(Graph graph){
		this.graph = graph;
	}
	
	public List<Node> search(Node target, int[] bountyPosition){
		
		Node source = graph.getPosition();
		
		//Source Node
		PathNode sourceNode = new PathNode(source);
		sourceNode.csf = 0;
		sourceNode.etc = 0;
		
		//PathNode Map
		HashMap<Node, PathNode> pathMap = new HashMap<Node, PathNode>();
		pathMap.put(sourceNode.node, sourceNode);
		
		//Open List
		PriorityQueue<Node> ol = new PriorityQueue<Node>(new Comparator<Node>() {

			@Override
			public int compare(Node n1, Node n2) {
				
				PathNode o1 = pathMap.get(n1);
				PathNode o2 = pathMap.get(n2);
				
				if(o1.etc < o2.etc)
					return -1;
				else
					return 1;
			}
		});
		ol.add(source);
		
		//Closed List
		HashSet<Node> cl = new HashSet<Node>();
		
		while(!ol.isEmpty()){
			Node currentNode = ol.remove();
			
			if(currentNode == target)
				break;
			
			List<Node> neighbors = getNeighbors(currentNode);
			
			for(Node neighbor : neighbors){
				if(!cl.contains(neighbor)){
					if(!ol.contains(neighbor)){
						PathNode newNode = new PathNode(neighbor);
						updateETC(newNode, pathMap.get(currentNode), bountyPosition);
						pathMap.put(neighbor, newNode);
						ol.add(neighbor);
					}else{
						updateETC(pathMap.get(neighbor), pathMap.get(currentNode), bountyPosition);
					}
				}
			}
			
			cl.add(currentNode);
		}
		
		List<Node> nodeList = getPath(cl, pathMap.get(target));
		
		return nodeList;
	}
	
	/* Private Functions */
	
	//Get Neighbors of a Node
	private List<Node> getNeighbors(Node node){
		List<Node> neighbors = new ArrayList<Node>();
		
		//Top
		Node topNeighbor = node.getNeighbour(Constants.TOP);
		if(topNeighbor != null)
			neighbors.add(topNeighbor);
		
		//Bottom
		Node bottomNeighbor = node.getNeighbour(Constants.BOTTOM);
		if(bottomNeighbor != null)
			neighbors.add(bottomNeighbor);
		
		//Left
		Node leftNeighbor = node.getNeighbour(Constants.LEFT);
		if(leftNeighbor != null)
			neighbors.add(leftNeighbor);
		
		//Right
		Node rightNeighbor = node.getNeighbour(Constants.RIGHT);
		if(rightNeighbor != null)
			neighbors.add(rightNeighbor);
		
		return neighbors;
	}
	
	//Update the ETC of a PathNode
	private void updateETC(PathNode dest, PathNode source, int[] bountyPosition){
		
		int weight = source.node.getEdgeWeight(dest.node);
		int heuristic = Heuristic.getHeuristic(bountyPosition, dest.node.getPosition());
		
		int csf = (weight + source.csf);
		int etc = csf + heuristic;
		
		if(etc < dest.etc){
			dest.etc = etc;
			dest.csf = csf;
			dest.parent = source;
		}
	}
	
	//Return the Desired Path
	private List<Node> getPath(HashSet<Node> cl, PathNode target){
		
		List<Node> pathList = new ArrayList<Node>();
		pathList.add(0, target.node);
		
		PathNode temp = target.parent;
		
		while(temp != null){
			pathList.add(0, temp.node);
			temp = temp.parent;
		}
		
		return pathList;
	}
}
