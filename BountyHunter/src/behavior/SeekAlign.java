package behavior;

import ds.KinematicDS;
import processing.core.PVector;

public class SeekAlign {
	public static KinematicDS update(KinematicDS character, int[] target, int maxVelocity, float maxRotation){
		KinematicDS output = character;
		
		//Seek
		PVector velocity = new PVector(target[0] - character.position.x, target[1] - character.position.y);
		if(velocity.mag() > maxVelocity){
			velocity.normalize();
			velocity.mult(maxVelocity);
		}
		output.velocity.set(velocity);
		
		output.position.add(velocity);
		
		//Align
		float targetOrientation = (float)Math.atan2(target[1] - character.position.y, target[0] - character.position.x);
		float rotation = targetOrientation - character.orientation;
		if(rotation > maxRotation){
			rotation = maxRotation*(rotation/Math.abs(rotation));
		}
		output.rotation = rotation;
		
		output.orientation += output.rotation;
		
		
		return output;
	}
}
