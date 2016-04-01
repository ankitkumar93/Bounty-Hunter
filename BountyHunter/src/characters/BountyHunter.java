package characters;

import constants.Constants;
import ds.EnvironMap;

public class BountyHunter {
	// Graph
	private EnvironMap gameMap;
	private int[] position;
	private int direction;

	public int getDirection() {
		return direction;
	}

	public BountyHunter() {
		direction = -1;
	}

	public void initializeGameSructure(EnvironMap gameMap, int[] position) {
		this.gameMap = gameMap;
		this.position = position;
	}
	
	public int[] getNextTarget(){
		if(direction == -1)
			return null;
		
		int[] target = position;
		
		switch(direction){
			case Constants.TOP:
				target[1] -= 1;
				break;
			case Constants.BOTTOM:
				target[1] += 1;
				break;
			case Constants.LEFT:
				target[0] -= 1;
				break;
			case Constants.RIGHT:
				target[0] += 1;
				break;
		}
		
		if(gameMap.matrix[target[0]][target[1]] == Constants.OBSTACLE){
			direction = -1;
			return null;
		}
		
		return gameMap.quantize(target[0], target[1]);
	}
	
	public int move(int direction){
				
		switch(direction){
			case Constants.TOP:
				position[1] -= 1;
				break;
			case Constants.BOTTOM:
				position[1] += 1;
				break;
			case Constants.LEFT:
				position[0] -= 1;
				break;
			case Constants.RIGHT:
				position[0] += 1;
				break;
		}
		
		if(gameMap.matrix[position[0]][position[1]] == Constants.COIN)
			return Constants.BHCOIN;
		else
			return Constants.BOUNTYHUNTER;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
}
