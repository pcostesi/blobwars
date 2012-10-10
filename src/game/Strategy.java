package game;

import java.awt.Point;
import java.util.List;

public interface Strategy {
	public List<Board> generateBoards(Board board, Point source);
	public boolean isValid(Board board, Point source, Point target);
}
