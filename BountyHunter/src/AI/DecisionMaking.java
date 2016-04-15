package AI;

import constants.Constants;

public class DecisionMaking {
	
	//Radius of Vision
	private int radiusOfVision;
	
	public DecisionMaking(){
		
		//Initialize Parameters
		this.radiusOfVision = Constants.ROVTHIEF;
	}
	
	public int update(int[] bountyPosition, int[] thiefPosition, boolean goalFlag){
		int diffX = bountyPosition[0] - thiefPosition[0];
		int diffY = bountyPosition[1] - thiefPosition[1];
		
		int relDistanceX = Math.abs(diffX);
		int relDistanceY = Math.abs(diffY);	
		
		if(relDistanceX <= radiusOfVision && relDistanceY <= radiusOfVision){
				return Constants.FLEEALERT;
		}
		
		if(goalFlag)
			return Constants.CONTINUE;
		else
			return Constants.NEWGOAL;
	}
}
