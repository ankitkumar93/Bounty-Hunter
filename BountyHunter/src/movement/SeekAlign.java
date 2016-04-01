package movement;

import ds.KinematicDS;
import processing.core.PVector;

public class SeekAlign {
	public KinematicDS update(KinematicDS character, int[] target){
		KinematicDS output = character;
		
		//Seek
		PVector direction = new PVector(character.position.x - target[0], character.position.y - target[1]);
		
		
		//Align
		
		
		return output;
	}
}
