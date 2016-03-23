package characters;


import constants.Constants;
import ds.Graph;
import ds.Node;

public class Thief {
	//Graph
	private Graph graph;
	
	//Parameters of Game
	private int coins;
	
	//Parameters of Control
	private int radiusOfVision = 5;
	
	//Dependent parameters
	private int neighbourMatrixSize;
	
	public Thief(){
		//Init graph
		graph = new Graph(Constants.THIEF);
		
		//init coins
		coins = 0;
		
		//init params
		neighbourMatrixSize = radiusOfVision*2 + 1;
	}
	
	public void update(){
		//AI Updation Method
		//Find Path
	}
	
	public void move(int direction){
		//Handle Movement to a Node
		graph.move(direction);
		
		//Handle coins
		Node position = graph.getPosition();
		if(position.getState() == Constants.COIN){
			coins++;
			position.setState(Constants.EMPTY);
		}
		
		//Update the Graph
		updateGraph();
		
	}
	
	//Private Functions
	private void updateGraph(){
		//Ask the Environment for Neighbour Matrix
		int neighbours[][] = null;
		
		//Supply the Matrix to the Graph
		graph.populate(neighbours, neighbourMatrixSize);
	}
}
