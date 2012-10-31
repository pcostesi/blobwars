package ai;

import game.Board;

public class MinimaxTimeoutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4985516221077210753L;

	public MinimaxTimeoutException(long millis, Board board){
		super("Failed to find a valid move for board :\n" + board + "\n in " + millis + "ms.");
	}
	
	public MinimaxTimeoutException(long millis, Board board, int levels){
		super("Failed to find a valid move for board :\n" + board + "\n in " + millis + "ms. after " + levels + " levels.");
	}
}
