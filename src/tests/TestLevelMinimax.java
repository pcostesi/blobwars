package tests;

import game.BlobStrategy;
import game.Board;
import game.Computer;
import game.Human;
import game.Strategy;

import org.junit.Test;

import ai.LevelMinimax;

import structures.Point;

public class TestLevelMinimax {
	
	private Computer computer = new Computer();
	private Human human = new Human();
	private Strategy strategy = new BlobStrategy(human, computer);

	@Test
	public void chooseAMove() {
		Board board = starterBoard();
		LevelMinimax minimax = new LevelMinimax(strategy, board, 6, human, computer);
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
