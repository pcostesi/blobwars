package game;

import structures.Point;


/**
 * This class represents a movement from Point source to Point target
 */
public class Movement {
		
		public final Point source;
		public final Point target;
		
		/**
		 * Instantiates a new movement.
		 *
		 * @param source the source
		 * @param target the target
		 */
		public Movement(Point source, Point target){
			this.source = source;
			this.target = target;
		}
		
		public String toString(){
			return source + " -> " + target;
		}
		
		/**
		 * The number of tiles from source to target
		 *
		 * @return the distance
		 */
		public int distance(){
			return (int) Math.max(Math.abs(source.getX() - target.getX()), Math.abs(source.getY() - target.getY()));
		}
}
