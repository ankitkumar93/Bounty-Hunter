/* Game - Bounty Hunter */
/* Constants Class for the Game */
/* Designed By Ankit Kumar */

package constants;

public class Constants {
	
	/* Constants */
	
	//Neighbor Constants
	public final static int LEFT = 0;
	public final static int RIGHT = 1;
	public final static int TOP = 2;
	public final static int BOTTOM = 3;
	
	//State Constants
	public final static int EMPTY = 0;
	public final static int OBSTACLE = 1;
	public final static int COIN = 2;
	public final static int THIEF = 3;
	public final static int BOUNTYHUNTER = 4;
	public final static int BHCOIN = 5;
	
	
	/* Tunable Parameters */
	
	//Max Velocities
	public final static int maxVelocityThief = 5;
	public final static int maxVelocityBountyHunter = 5;
	
	//Time Intervals
	public final static int predictionTimeInterval = 2;
}
