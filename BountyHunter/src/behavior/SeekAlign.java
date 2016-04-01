package behavior;

import ds.KinematicDS;
import processing.core.PVector;

public class SeekAlign {
	public KinematicDS update(KinematicDS character, int[] target, int maxVelocity, float maxRotation){
		KinematicDS output = character;
		
		//Seek
		PVector velocity = new PVector(character.position.x - target[0], character.position.y - target[1]);
		if(velocity.mag() > maxVelocity){
			velocity.normalize();
			velocity.mult(maxVelocity);
		}
		output.velocity = velocity;
		
		//Align
		float targetOrientation = (float)Math.atan2(character.position.y - target[1], character.position.x - target[0]);
		float rotation = targetOrientation - character.orientation;
		if(rotation > maxRotation){
			rotation = maxRotation*(rotation/Math.abs(rotation));
		}
		output.rotation = rotation;
		
		
		return output;
	}
}
