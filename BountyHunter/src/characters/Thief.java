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
	
	//Constructor
	public Thief(){
		//Init graph
		graph = new Graph(Constants.THIEF);
		
		//Init coins
		coins = 0;
		
		//Init params
		neighbourMatrixSize = radiusOfVision*2 + 1;
	}
	
	//AI Updation
	public void update(){
		//AI Updation Method
		//Find Path
	}
	
	//Move to a Node
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
	
	//Get Coins Collected
	public int getCoins(){
		return coins;
	}
	
	//Private Functions
	private void updateGraph(){
		//Ask the Environment for Neighbor Matrix
		int neighbours[][] = null;
		
		//Supply the Matrix to the Graph
		graph.populate(neighbours, neighbourMatrixSize);
	}
}
