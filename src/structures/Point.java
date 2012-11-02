package structures;


/**
 * A more efficient implementation of the class Point for this game particular purpose.
 * Given that a board consists of a finite number of tiles, and that there could be
 * many Points referencing to the same tile, this implementation only stores one of each.
 * 
 * Also this implementation uses int values instead of doubles.
 */

public class Point {
	
	private final int x;
	private final int y;
	
	
	/**
	 * Instantiates a new point.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * Gets the x value
	 *
	 * @return the x value
	 */
	public int getX(){
		return x;
	}
	
	public boolean equals(Object obj){
		if (!(obj instanceof Point)){
			return false;
		}
		Point p = (Point) obj;
		return p.getX() == this.getX() && p.getY() == this.getY();
		
	}
	
	/**
	 * Gets the y value
	 *
	 * @return the y value
	 */
	public int getY(){
		return y;
	}
	
	public String toString(){
		return "<x: " + x + ", y: " + y + ">";
	}
}
