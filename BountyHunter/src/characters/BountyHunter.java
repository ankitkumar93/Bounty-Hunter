package characters;

import constants.Constants;
import ds.EnvironMap;

public class BountyHunter {
	// Graph
	private EnvironMap gameMap;
	private int x;
	private int y;
	private int direction;

	public int getDirection() {
		return direction;
	}

	private int cellSize;
	private int[] gameMapDimensions;

	public BountyHunter() {
		// TODO Auto-generated constructor stub
		direction = -1;
	}

	public void initializeGameSructure(int size, int[] dim, EnvironMap graph) {
		cellSize = size;
		gameMapDimensions = dim;
		gameMap = graph;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void update() {

		if (((x - cellSize / 2) % cellSize == 0) && ((y - cellSize / 2) % cellSize == 0))
		{
			if(checkForObstacle(direction))
			{
				direction = -1;
				
			}
				
		}
			switch (direction) {
			case Constants.BOTTOM:
				if (y < gameMapDimensions[1] - cellSize / 2)
					y += cellSize / 10;
				break;
			case Constants.LEFT:
				if (x > cellSize / 2)
					x -= cellSize / 10;
				;
				break;
			case Constants.TOP:
				if (y > cellSize / 2)
					y -= cellSize / 10;
				break;
			case Constants.RIGHT:
				if (x < gameMapDimensions[0] - cellSize / 2)
					x += cellSize / 10;
				break;

			default:
				break;
			}

	}

	public boolean checkForObstacle(int dir) {

		int[] hunterLoc = gameMap.quantize(getX(), getY());
		switch (dir) {
		case Constants.BOTTOM:
			if (hunterLoc[1] == (gameMapDimensions[1] / cellSize) - 1
					|| gameMap.matrix[hunterLoc[0]][hunterLoc[1] + 1] != Constants.OBSTACLE)
				return false;
			break;
		case Constants.LEFT:
			if (hunterLoc[0] == 0 || gameMap.matrix[hunterLoc[0] - 1][hunterLoc[1]] != Constants.OBSTACLE)
				return false;
			break;
		case Constants.TOP:
			if (hunterLoc[1] == 0 || gameMap.matrix[hunterLoc[0]][hunterLoc[1] - 1] != Constants.OBSTACLE)
				return false;
			break;
		case Constants.RIGHT:
			if (hunterLoc[0] == (gameMapDimensions[0] / cellSize) - 1
					|| gameMap.matrix[hunterLoc[0] + 1][hunterLoc[1]] != Constants.OBSTACLE)
				return false;
			break;
		default:
			return true;
		}
		return true;

	}

	public void setDirection(int direction) {

		this.direction = direction;
	}
}
