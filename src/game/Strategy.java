package game;

import structures.Pair;
import structures.Point;
import frontend.GameController;

public interface Strategy {
	public abstract Iterable<Pair<Board, Movement>> boardsForMove(Board board, Point source); 
	public abstract boolean isValid(Board board, Movement move);
	public abstract double evaluateScore(Board board, Player maximizer);
	public abstract Board move(Board board, Player player, Movement move);
	public abstract Board startingBoard();
	public abstract boolean hasAvailableMove(Board board, Point source);


}
