package behavior;

import ds.KinematicDS;
import processing.core.PVector;

public class SeekAlign {
	public static KinematicDS update(KinematicDS character, int[] target, float targetOrientation, int maxVelocity, float maxRotation){
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
		float rotation = mapToRange(targetOrientation - character.orientation);
		if(rotation > maxRotation){
			rotation = maxRotation*(rotation/Math.abs(rotation));
		}
		output.rotation = rotation;
		
		output.orientation += output.rotation;
		
		
		return output;
	}
	
	/* Private Functions */
	private static float mapToRange(float rotation){
		float pi = (float)Math.PI;
		float newRotation = rotation % (2*pi);
		
		if(Math.abs(newRotation) <= pi)
			return newRotation;
		else if(newRotation > pi)
			return (newRotation - (2*pi));
		else
			return (newRotation + (2*pi));
	}
}
