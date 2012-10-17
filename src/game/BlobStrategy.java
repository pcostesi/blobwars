package game;

import java.awt.Point;
import java.util.List;

import structures.Pair;

public class BlobStrategy implements Strategy{

	@Override
	public List<Pair<Board, Movement>> generateBoards(Board board, Point source) {
		// devuelve board y destino, enlistados
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid(Board board, Movement move) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int evaluateScore(Board board) {
		// TODO Auto-generated method stub
		return 5;
	}

}
