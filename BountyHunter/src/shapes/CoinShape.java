package shapes;

import processing.core.*;

public class CoinShape {
	PShape coin;
	PApplet mainApplet;
	
	public CoinShape(PApplet myApplet){
		//import applet
		mainApplet = myApplet;
		
		//coin
		coin = mainApplet.createShape(PConstants.ELLIPSE, 0, 0, 20, 20);
		coin.setFill(mainApplet.color(255, 204, 0));
		coin.setStroke(false);
	}
	
	public void drawShape(){
		mainApplet.shape(coin);
	}
}
