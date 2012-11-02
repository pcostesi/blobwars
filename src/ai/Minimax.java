package ai;

import game.Board;
import game.Movement;

public interface Minimax {
	public Movement getBestMove();
	public void setBoard(Board board);
	public int getHeight();
	public int exploredStates();
	public long runTime();
}
