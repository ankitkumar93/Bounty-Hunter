package behavior;

import java.util.ArrayList;
import java.util.List;

import constants.Constants;

public class Heuristic {
	public static int getHeuristic(int[] bountyPosition, int[] nodePosition, int bountyDirection, int confidence){
		int weightConst = Constants.COLLISION_CONTROL*confidence/100;
		int distance = getDist(nodePosition, bountyPosition, bountyDirection);
		if(distance != 0)
			return (weightConst/distance);
		else
			return Constants.INFINITY;
	}
	
	private static int getDist(int[] bountyPosition, int[] nodePosition, int bountyDirection){
		int distance = Integer.MAX_VALUE;
		List<int[]> nodeList = getNodes(bountyPosition, bountyDirection);
		for(int i = 0; i < nodeList.size(); i++){
			int[] tempPosition = nodeList.get(i);
			int tempDistance = Math.abs(tempPosition[0] - nodePosition[0]) + Math.abs(tempPosition[1] - nodePosition[1]);
			if(tempDistance < distance)
				distance = tempDistance;
		}
		
		return distance;
	}
	
	private static List<int[]> getNodes(int[] bountyPosition, int bountyDirection){
		List<int[]> nodeList = new ArrayList<int[]>();
		int[] position = bountyPosition.clone();
		nodeList.add(position);
		
		int xDiff = 0;
		int yDiff = 0;
		
		if(bountyDirection ==  Constants.MOVEUP)
			yDiff -= 1;
		else if(bountyDirection ==  Constants.MOVEDOWN)
			yDiff += 1;
		else if(bountyDirection ==  Constants.MOVELEFT)
			xDiff -= 1;
		else if(bountyDirection ==  Constants.MOVERIGHT)
			xDiff += 1;
		else
			return nodeList;
		
		for(int i = 0; i < Constants.PREDICTION_TIME; i++){
			position = bountyPosition.clone();
			position[0] += (xDiff * (i+1));
			position[1] += (yDiff * (i+1));
			nodeList.add(position);
		}
		
		return nodeList;
	}
}
