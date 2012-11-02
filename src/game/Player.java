package game;

// TODO: Auto-generated Javadoc
/**
 * The abstract class Player, from which Human and Computer inherit.
 * This class is needed for the minimax algorithm.
 */
public abstract class Player {

	
	/**
	 * The char representation of the player in the board.
	 *
	 * @return the char
	 */
	public abstract char toTile();

	public int hashCode(){
		return toString().hashCode();
	}

}
