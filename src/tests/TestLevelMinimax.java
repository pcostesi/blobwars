package tests;

import game.BlobStrategy;
import game.Board;
import game.Computer;
import game.Human;
import game.Strategy;

import org.junit.Test;

import structures.MinMaxTree;
import structures.Point;

public class TestLevelMinimax {
	
	private Computer computer = new Computer();
	private Human human = new Human();
	private Strategy strategy = new BlobStrategy(human, computer);

	@Test
	public void chooseAMove() {
		Board board = starterBoard();
		MinMaxTree minimax = new MinMaxTree(board, 5, computer, human);
		System.out.println("Best so far: " + minimax.getBestMove());
	}
	

	private Board starterBoard(){
		Board baseBoard = new Board(strategy, human, computer);
		baseBoard = baseBoard.
				putBlob(human, Point.getInstance(0, 0)).
				putBlob(human, Point.getInstance(0, 7)).
				putBlob(computer, Point.getInstance(7, 0)).
				putBlob(computer, Point.getInstance(7, 7));
		
		return baseBoard;
	}
	
}
