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
	
	private final static int SIZE = 8;
	
	private static Point[] points = new Point[(SIZE + 1) * (SIZE + 1)];
	
	/**
	 * Instantiates a new point.
	 *
	 * @param x the x
	 * @param y the y
	 */
	private Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Gets the single instance of Point.
	 *
	 * @param x the x
	 * @param y the y
	 * @return single instance of Point
	 */
	public static Point getInstance(int x, int y){
		Point p = points[SIZE * y + x];
		if (p == null){
			p = new Point(x, y);
			points[SIZE * y + x] = p;
		}
		return p;
	}
	
	/**
	 * Gets the x value
	 *
	 * @return the x value
	 */
	public int getX(){
		return x;
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
		return "<" + x + ", " + y + ">";
	}
}
