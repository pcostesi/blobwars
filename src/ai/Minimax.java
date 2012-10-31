package ai;

import game.Board;
import game.Movement;

public interface Minimax {
	public Movement getBestMove();
	public void setBoard(Board board);
}
