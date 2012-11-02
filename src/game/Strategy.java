package game;

import structures.Pair;
import structures.Point;
import frontend.GameController;

/**
 * The Interface Strategy. Every game that could be played with a minimax AI, should have its own strategy
 * These are the logic rules of the game.
 */
public interface Strategy {
	
	/**
	 * The possible movements and its resulting boards for a given board and a given Point
	 *
	 * @param board the original board
	 * @param source the position of the tile to move
	 * @return an iterable of a Pair with the new board and necessary movement to get from the original board to it.
	 */
	public abstract Iterable<Pair<Board, Movement>> boardsForMove(Board board, Point source); 
	
	/**
	 * Checks if a move inside the board is valid for these set of rules.
	 *
	 * @param board the board
	 * @param move the move
	 * @return true, if it is valid
	 */
	public abstract boolean isValid(Board board, Movement move);
	
	/**
	 * Evaluate score for a given board.
	 *
	 * @param board the board
	 * @param maximizer the maximizer
	 * @return the double
	 */
	public abstract double evaluateScore(Board board, Player maximizer);
	
	/**
	 * The movement of a tile and all its repercusions inside the board.
	 *
	 * @param board the board
	 * @param player the player
	 * @param move the move
	 * @return the board
	 */
	public abstract Board move(Board board, Player player, Movement move);
	
	/**
	 * The starting board for this game.
	 *
	 * @return the board
	 */
	public abstract Board startingBoard();
	
	/**
	 * Checks if it has an available move.
	 *
	 * @param board the board
	 * @param source the source
	 * @return true, if successful
	 */
	public abstract boolean hasAvailableMove(Board board, Point source);

}
