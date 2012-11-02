package game;

// TODO: Auto-generated Javadoc
/**
 * The abstract class Player, from which Human and Computer inherit.
 * This class is needed for the minimax algorithm.
 */
public abstract class Player {

	/**
	 * Initial score for the minimax algorithm.
	 *
	 * @return the int
	 */
	public abstract int initialScore();

	/**
	 * The char representation of the player in the board.
	 *
	 * @return the char
	 */
	public abstract char toTile();

	/**
	 * Decides if the localScore is better than the previous score.
	 *
	 * @param score the score
	 * @param localScore the local score
	 * @return true, if successful
	 */
	public abstract boolean betterScore(double score, double localScore);
	

	public int hashCode(){
		return toString().hashCode();
	}

}
