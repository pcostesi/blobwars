package game;

import java.util.List;

import structures.Pair;
import structures.Point;

public interface Strategy {
	public abstract List<Pair<Board, Movement>> generateBoards(Board board, Point source); 
	public abstract void injectBoards(Board board, Point source, List<Pair<Board, Movement>> l); 
	public abstract boolean isValid(Board board, Movement move);
	public abstract int evaluateScore(Board board, Player p);
	public abstract Board move(Board board, Player player, Movement move);
	public abstract Board startingBoard();


}
