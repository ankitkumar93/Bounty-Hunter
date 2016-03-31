package AI;

import java.util.ArrayList;
import java.util.List;

import ds.EnvironMap;
import ds.Node;

public class BountyPredictor {
	
	/* Private Variables */
	private EnvironMap envMap;
	private int timeInterval;
	
	/* Private Functions */
	private List<int[]> getPossiblePositions(int[] bountyPosition, int velocity){
		List<int[]> possiblePositions = new ArrayList<int[]>();
		
		int maxDistance = velocity*timeInterval;
		int leftNodes = bountyPosition[0] - maxDistance;
		int rightNodes = bountyPosition[0] + maxDistance;
		
		int limitLeft = Math.min(leftNodes, 0);
		int limitRight = Math.min(rightNodes, envMap.size - 1);
		
		for(int i = limitLeft; i <= limitRight; i++){
			
			//Calculate offset
			int offset = maxDistance - Math.abs(bountyPosition[0] - i);
			
			//Calculate index
			int topNodes = bountyPosition[1] - offset;
			int bottomNodes = bountyPosition[1] + offset;
			
			//Calculate limit
			int limitTop = Math.min(topNodes, 0);
			int limitBottom = Math.min(bottomNodes, envMap.size - 1);
			
			for(int j = limitTop; j <= limitBottom; j++){
				int[] position = {i,j};
				possiblePositions.add(position);
			}
			
		}
		
		return possiblePositions;
	}
	
	/* Public Functions */
	//Constructor
	public BountyPredictor(EnvironMap envMap, int timeInterval){
		
		//copy parameters
		this.envMap = envMap;
		this.timeInterval = timeInterval;
		
	}
	
	public List<Node> update(int[] bountyPosition, int[] thiefPosition, int velocity){
		List<Node> output = new ArrayList<Node>();
		
		//Get possible positions
		List<int[]> possiblePositions = getPossiblePositions(bountyPosition, velocity);
		
		//Compute nodes for all possible positions found
		for(int[] position: possiblePositions){
			int state = envMap.matrix[position[0]][position[1]];
			int[] relativePosition = {position[0] - thiefPosition[0], position[1] - thiefPosition[1]};
			
			Node node = new Node(state, relativePosition);
			output.add(node);
		}
		
		return output;
	}
}
