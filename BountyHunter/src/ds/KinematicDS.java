package ds;

import processing.core.PVector;

public class KinematicDS {
	public PVector position;
	public PVector velocity;
	
	public float orientation;
	public float rotation;
	
	public KinematicDS(int[] pos, float orientation){
		this.position = new PVector(pos[0], pos[1]);
		this.orientation = orientation;
		
		this.velocity = new PVector(0, 0);
		this.rotation = 0; 
	}
}
