package ds;

public class CoordHolder {
	private int x;
	private int y;
	
	public CoordHolder(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int[] getCoord(){
		int[] coord = {x, y};
		return coord;
	}
	
	@Override
	public boolean equals(Object obj) {
		CoordHolder obj2 = (CoordHolder)obj;
		if(x == obj2.x && y == obj2.y)
			return true;
		
		return false;
	}
	
	@Override
	public String toString() {
		return "X: " + x + "	Y: " + y;
	}
	
	@Override
	public int hashCode() {
		return x + y;
	}
}
