package AI;

import java.util.List;

import ds.Node;

public class PathFollower {
	
	private List<Node> path;
	
	public void setPath(List<Node> path){
		this.path = path;
	}
	
	public Node getNextTarget(Node currentNode){
		int currentIndex = path.indexOf(currentNode);
		if(currentIndex == -1)
			return null;
		
		int nextIndex = currentIndex + 1;
		
		Node target = null;
		if(currentIndex + 1 < path.size())
			target = path.get(nextIndex);
		
		return target;
	}
}
