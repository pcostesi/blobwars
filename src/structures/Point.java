package structures;


public class Point {
	private final int x;
	private final int y;
	private final static int SIZE = 8;
	
	private static Point[] points = new Point[(SIZE + 1) * (SIZE + 1)];
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public static Point getInstance(int x, int y){
		Point p = points[SIZE * y + x];
		if (p == null){
			p = new Point(x, y);
			points[SIZE * y + x] = p;
		}
		return p;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public String toString(){
		return "<" + x + ", " + y + ">";
	}
}
