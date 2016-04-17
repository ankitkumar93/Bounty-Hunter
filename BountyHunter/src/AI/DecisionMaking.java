package AI;

import java.util.Random;

import constants.Constants;

public class DecisionMaking {
	
	//Radius of Vision
	private int fleeThreshhold;
	
	public DecisionMaking(){
		
		//Initialize Parameters
		this.fleeThreshhold = Constants.FLEE_THRESHOLD;
	}
	
	public int update(int[] bountyPosition, int[] thiefPosition, boolean goalFlag, boolean fleeGoalFlag, float probabilityFlee){
		int diffX = bountyPosition[0] - thiefPosition[0];
		int diffY = bountyPosition[1] - thiefPosition[1];
		
		int relDistanceX = Math.abs(diffX);
		int relDistanceY = Math.abs(diffY);	
		
		if(relDistanceX <= fleeThreshhold && relDistanceY <= fleeThreshhold){
			Random random = new Random();
			float fleeChance = random.nextFloat();
			if(fleeChance < probabilityFlee)
				return Constants.FLEEALERT;
			else
				return Constants.NEWGOAL;
		}
		
		if(goalFlag)
			return Constants.CONTINUE;
		else
			return Constants.NEWGOAL;
	}
}
