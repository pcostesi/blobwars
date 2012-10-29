package game;

import structures.Point;


public class Movement {
		public final Point source;
		public final Point target;
		
		public Movement(Point source, Point target){
			this.source = source;
			this.target = target;
		}
		
		public String toString(){
			return source + " -> " + target;
		}
		
		public int distance(){
			return (int) Math.max(Math.abs(source.getX() - target.getX()), Math.abs(source.getY() - target.getY()));
		}
}
