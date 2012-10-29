package game;

import structures.Pair;
import structures.Point;
import frontend.GameController;

public interface Strategy {
	public abstract Iterable<Pair<Board, Movement>> boardsForMove(Board board, Point source); 
	public abstract boolean isValid(Board board, Movement move);
	public abstract int evaluateScore(Board board, Player p);
	public abstract Board move(Board board, Player player, Movement move);
	public abstract Board startingBoard();
	public abstract void setObserver(GameController observer);


}
