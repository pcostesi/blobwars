package game;

import java.awt.Point;
import java.util.List;

import structures.Pair;

public abstract class Strategy {
	public abstract List<Pair<Board, Movement>> generateBoards(Board board, Point source); 
	public abstract boolean isValid(Board board, Movement move);
	public abstract int evaluateScore(Board board);

	public abstract Player getPlayer(int level);

}
