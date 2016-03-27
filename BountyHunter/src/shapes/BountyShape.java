package shapes;

import processing.core.*;

public class BountyShape {
	PShape bounty, head, body;
	PApplet mainApplet;
	
	public BountyShape(PApplet myApplet){
		//import applet
		mainApplet = myApplet;
		
		//bounty
		bounty = mainApplet.createShape(PConstants.GROUP);
								
		//body
		body = mainApplet.createShape(PConstants.ELLIPSE, 0, 0, 40, 40);
		body.setFill(mainApplet.color(0, 0, 255));
		body.setStroke(false);
							
		//head
		head = mainApplet.createShape(PConstants.TRIANGLE, 0, -20, 0, 20, 40, 0);
		head.setFill(mainApplet.color(0, 0, 255));
		head.setStroke(false);
								
		//add shape parts to player
		bounty.addChild(body);
		bounty.addChild(head);
	}
	
	public void drawShape(){
		mainApplet.shape(bounty);
	}
}
