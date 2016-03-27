package shapes;

import processing.core.*;

public class ObstacleShape {
	PShape obstacle;
	PApplet mainApplet;
	
	public ObstacleShape(PApplet myApplet){
		//import applet
		mainApplet = myApplet;
		
		//coin
		obstacle = mainApplet.createShape(PConstants.RECT, -25, -25, 50, 50);
		obstacle.setFill(mainApplet.color(0));
		obstacle.setStroke(false);
	}
	
	public void drawShape(){
		mainApplet.shape(obstacle);
	}
}
