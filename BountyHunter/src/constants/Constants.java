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
	
	//Cell Dimensions
	public final static int CELLWIDTH = 50;
	public final static int CELLHEIGHT = 50;
	
	//Edge Weights
	public final static int COINEDGE = 1;
	public final static int NOCOINEDGE = 2;
	
	//Movement Directions for Bounty Hunter
	public final static int DONTMOVE = -1;
	public final static int MOVELEFT = 0;
	public final static int MOVERIGHT = 1;
	public final static int MOVEUP = 2;
	public final static int MOVEDOWN = 3;
	
	
	/* Character Parameters */
	public static final int[] THIEFPOS = {0,0};
	public static final int[] BOUNTYHUNTERPOS = {9,9};
	public static final float THIEFORIENTATION = 0.f;
	public static final float BOUNTYHUNTERRORIENTATION = -(float)(Math.PI);
	
	/* Decision Tree Return Values */
	public static final int FLEEALERT = 0;
	public static final int CONTINUE = 1;
	public static final int NEWGOAL = 2;
	
	/* Graph Parameters */
	public static final int MEMORYMAP_X_TRANS = 550;
	public static final int HARDCODED_GRAPH_SIZE = 10;
	
	/* Tunable Parameters */
	
	//Max Velocities
	public final static int MVTHIEF = 2;
	public final static int MVBOUNTYHUNTER = 2;
	
	//Max Rotation
	public final static float MRTHIEF = (float)(Math.PI/20);
	public final static float MRBOUNTYHUNTER = (float)(Math.PI/20);
	
	//Time Intervals
	public final static int PREDICTIONTI = 1;
	
	//Radiuses
	public final static int ROVTHIEF = 4;
	public final static int ROFTHIEF = 2;
	
	//Heuristic INFINITY
	public final static int INFINITY = 1000;
	
	//Confidence
	public final static int CONFIDENCE_MAX = 100;
	public final static int CONFIDENCE_DECAY_RATE = 10;
	
	//Constant of Collision Control
	public final static int COLLISION_CONTROL = 50;
	
	//Prediction Time
	public final static int PREDICTION_TIME = 3;
	
	//Flee Parameters
	public final static int FLEE_THRESHOLD = 3;
	public final static float FLEE_PROBABILITY_MAX = 1.f;
	public final static float FLEE_PROBABILITY_DECAY_RATE = 0.2f;
}
