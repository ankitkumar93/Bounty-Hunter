package game;

/* Imports */
import characters.Thief;
import constants.Constants;
import ds.EnvironMap;
import ds.Graph;
import processing.core.PApplet;
import shapes.BountyShape;
import shapes.CoinShape;
import shapes.ObstacleShape;

public class ThiefVision {
	/* Processing Variables */
	private PApplet mainApplet;
	
	// Data Structure
	private EnvironMap envMap;
	private Graph nodeGraph;

	// Shape Holders
	private CoinShape coinShape;
	private ObstacleShape obstacleShape;
	private BountyShape bountyShape;
	
	//Thief Object
	private Thief thief;
	
	//Bounty Data
	private int[] bountyPosition;
	private int bountyDirection;
	private float bountyOrientation;
	
	public ThiefVision(PApplet myApplet,EnvironMap env, Thief thief) {
		// TODO Auto-generated constructor stub
		this.thief = thief;
		mainApplet = myApplet;
		envMap = env;
		nodeGraph = thief.graph;
		bountyPosition = thief.bountyPosition;
		bountyOrientation = Constants.BOUNTYHUNTERRORIENTATION;
		
		initializeShapes();
	}

	// Initialize Shapes for the Objects in the Environment
	private void initializeShapes() {
		coinShape = new CoinShape(mainApplet);
		obstacleShape = new ObstacleShape(mainApplet);
		bountyShape = new BountyShape(mainApplet);
	}

	// Draw the Environment
	public void draw() {
		drawSeparatingLine();
		drawNodeMap();
		drawBountyHunter();

	}

	private void drawNodeMap() {
		// TODO Auto-generated method stub
		for (int i = 0; i < envMap.size; i++) {
			for (int j = 0; j < envMap.size; j++) {

				// Get Position on Screen
				int position[] = envMap.localize(i, j);

				// Translate
				mainApplet.pushMatrix();
				mainApplet.translate(Constants.MEMORYMAP_X_TRANS + position[0], position[1]);

				if(nodeGraph.isInMap(i, j))
				{
					// Draw according to State
					if (envMap.matrix[i][j] == Constants.COIN)
						coinShape.drawShape();
					else if (envMap.matrix[i][j] == Constants.OBSTACLE)
						obstacleShape.drawShape();
				}
				else
				{
					obstacleShape.drawShape();
				}
				// Remove Translate
				mainApplet.popMatrix();
			}
		}
	}

	private void drawBountyHunter() {
		int[] position = nodeGraph.localize(bountyPosition);
		updateBountyOrientation();
		mainApplet.pushMatrix();
		mainApplet.translate(Constants.MEMORYMAP_X_TRANS+position[0], position[1]);
		mainApplet.rotate(bountyOrientation);
		bountyShape.drawShape();
		mainApplet.popMatrix();
	}

	private void drawSeparatingLine() {
		int graphWidth = Constants.HARDCODED_GRAPH_SIZE * Constants.CELLWIDTH;
		int xTranslation;
		int yTranslation;
		for (int i = 0; i < Constants.HARDCODED_GRAPH_SIZE; i++) {
			xTranslation = graphWidth + Constants.CELLWIDTH / 2;
			yTranslation = i * Constants.CELLHEIGHT + Constants.CELLHEIGHT / 2;
			// Translate
			mainApplet.pushMatrix();
			mainApplet.translate(xTranslation, yTranslation);
			mainApplet.fill(mainApplet.color(255, 0, 0));
			obstacleShape.drawShape();
			mainApplet.popMatrix();
		}

	}
	
	private void updateBountyOrientation(){
		bountyDirection = thief.bountyDirection;
		switch(bountyDirection){
				case Constants.DONTMOVE:
					break;
				case Constants.MOVEUP:
					bountyOrientation = -(float)Math.PI/2;
					break;
				case Constants.MOVEDOWN:
					bountyOrientation = (float)Math.PI/2;
					break;
				case Constants.MOVELEFT:
					bountyOrientation = -(float)Math.PI;
					break;
				case Constants.MOVERIGHT:
					bountyOrientation = 0.f;
					break;
		}
	}
}
