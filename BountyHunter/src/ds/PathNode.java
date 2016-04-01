package ds;

public class PathNode {
	public Node node;
	public int etc;
	public int csf;
	public PathNode parent;
	
	public PathNode(Node node){
		this.node  = node;
		this.etc = Integer.MAX_VALUE;
		this.csf = Integer.MAX_VALUE;
		this.parent = null;
	}
}
