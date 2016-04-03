package behavior;

import constants.Constants;

public class Heuristic {
	public static int getHeuristic(int[] bountyPosition, int[] nodePosition){
		int distance = Math.abs(nodePosition[0] - bountyPosition[0]) + Math.abs(nodePosition[1] - bountyPosition[1]);
		if(distance != 0)
			return (1/distance);
		else
			return Constants.INFINITY;
	}
}
