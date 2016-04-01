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
	public final static int OUTOFBOUNDS = -1;
	
	/* Character Parameters */
	public static final int[] THIEFPOS = {0,0};
	public static final int[] BOUNTYHUNTERPOS = {9,9};
	public static final float THIEFORIENTATION = 0.f;
	public static final float BOUNTYHUNTERRORIENTATION = -(float)(Math.PI);
	
	
	/* Tunable Parameters */
	
	//Max Velocities
	public final static int MVTHIEF = 5;
	public final static int MVBOUNTYHUNTER = 5;
	
	//Time Intervals
	public final static int PREDICTIONTI = 1;
	
	//Radiuses
	public final static int ROVTHIEF = 2;
}
