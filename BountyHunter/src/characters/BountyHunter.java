package characters;

import constants.Constants;
import ds.EnvironMap;
import environment.Environment;

public class BountyHunter {
	//Graph
	private EnvironMap gameMap;
	
	//Environment
	private Environment environment;	
	
	//Parameters
	private int[] position;
	private int direction;
	
	private int newDirection;

	public int getDirection() {
		return direction;
	}

	public BountyHunter(Environment environment) {
		//Assign Parameters
		this.direction = Constants.DONTMOVE;
		this.newDirection = Constants.DONTMOVE;
		
		this.environment = environment;
		this.gameMap = environment.getEnvMap();
		this.position = Constants.BOUNTYHUNTERPOS;
	}
	
	public int[] getNextTarget(){
		if(direction == -1)
			return null;
		
		int[] target = moveDirection(direction);
		
		if(target == null)
			return null;
		
		return gameMap.localize(target[0], target[1]);
	}
	
	//Get Target Orientation
	public float getTargetOrientation(int[] target){
		int[] currentPosition = gameMap.localize(position[0], position[1]);
		float targetOrientation =  (float)Math.atan2(target[1] - currentPosition[1], target[0] - currentPosition[0]);
		
		return targetOrientation;
	}
	
	public void move(){
		position = moveDirection(direction);
		environment.updateBountyHunterPosition(position, direction);
		updateDirection();
	}

	public void setDirection(int direction) {
		this.newDirection = direction;
		updateDirection();
	}
	
	/* Private Functions */
	private int[] moveDirection(int moveDirection){
		int[] newPosition = position.clone();
		
		switch(moveDirection){
			case Constants.MOVEUP:
				newPosition[1] -= 1;
				break;
			case Constants.MOVEDOWN:
				newPosition[1] += 1;
				break;
			case Constants.MOVELEFT:
				newPosition[0] -= 1;
				break;
			case Constants.MOVERIGHT:
				newPosition[0] += 1;
				break;
		}
		
		boolean outOfBounds = (newPosition[0] < 0 || newPosition[0] >= gameMap.size) || (newPosition[1] < 0 || newPosition[1] >= gameMap.size);
		if(outOfBounds)
			return null;
		
		int newState = gameMap.matrix[newPosition[0]][newPosition[1]];
		if(newState == Constants.OBSTACLE)
			return null;
		
		return newPosition;
	}
	
	//Update Direction to New Direction
	private void updateDirection(){
		int[] newPosition = moveDirection(newDirection);
		if(newPosition != null)
			this.direction = newDirection;
	}
}
