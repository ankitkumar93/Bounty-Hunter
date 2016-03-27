package ds;

public class EnvironMap {
	
	//Public Data
	public int[][] matrix;
	public int size;
	
	//Private Data
	private int cellWidth;
	private int cellHeight;
	
	public EnvironMap(int arr[][], int size, int width, int height){
		this.size = size;
		this.cellWidth = width;
		this.cellHeight = height;

		this.matrix = new int[size][];
		
		//Copy Array to Matrix
		for(int i = 0; i < size; i++){
			this.matrix[i] = new int[size];
			for(int j = 0; j < size; j++){
				matrix[i][j] = arr[i][j];
			}
		}
	}
	
	public int[] localize(int x, int y){
		int[] position = new int[2];
		
		position[0] = cellWidth*x + cellWidth/2;
		position[1] = cellHeight*y + cellHeight/2;
		
		return position;
	}
	
	public int[] quantize(int x, int y){
		int[] position = new int[2];
		
		position[0] = x/cellWidth;
		position[1] = y/cellHeight;
		
		return position;
	}
}
