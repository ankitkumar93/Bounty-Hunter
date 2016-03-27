package shapes;

import processing.core.*;

public class ThiefShape {
	PShape thief, head, body;
	PApplet mainApplet;
	
	public ThiefShape(PApplet myApplet){
		//import applet
		mainApplet = myApplet;
		
		//thief
		thief = mainApplet.createShape(PConstants.GROUP);
								
		//body
		body = mainApplet.createShape(PConstants.ELLIPSE, 0, 0, 40, 40);
		body.setFill(mainApplet.color(0));
		body.setStroke(false);
							
		//head
		head = mainApplet.createShape(PConstants.TRIANGLE, 0, -20, 0, 20, 40, 0);
		head.setFill(mainApplet.color(0));
		head.setStroke(false);
								
		//add shape parts to player
		thief.addChild(body);
		thief.addChild(head);
	}
	
	public void drawShape(){
		mainApplet.shape(thief);
	}
}
