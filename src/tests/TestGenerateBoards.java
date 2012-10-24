package tests;

import game.BlobStrategy;
import game.Board;
import game.Computer;
import game.Human;
import game.Movement;
import game.Player;
import game.Strategy;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import structures.Pair;
import structures.Point;

public class TestGenerateBoards {
	private Strategy strategy;
	
	private Computer computer = new Computer();
	private Human human = new Human();

	public void before(){
		this.strategy = new BlobStrategy(human, computer);
	}
	
	@Test
	public void upperLeftCorner() {
		Board board = starterBoard(); 
		Point source = Point.getInstance(0, 0);
		
		List<Pair<Board, Movement>> expect = new LinkedList<Pair<Board, Movement>>();
		
		expect.add(mockAdyacentMove(human, board, source, Point.getInstance(1, 0)));
		expect.add(mockAdyacentMove(human, board, source, Point.getInstance(0, 1)));
		expect.add(mockAdyacentMove(human, board, source, Point.getInstance(1, 1)));
		
		assert(strategy.generateBoards(board, source).containsAll(expect));
	}
	
	@Test
	public void twoSteps() {
		Board board = starterBoard().deleteBlob(human, Point.getInstance(0, 0)); 
		Point source = Point.getInstance(0, 0);
		
		List<Pair<Board, Movement>> expect = new LinkedList<Pair<Board, Movement>>();
		
		expect.add(mockAdyacentMove(human, board, source, Point.getInstance(2, 0)));
		expect.add(mockAdyacentMove(human, board, source, Point.getInstance(0, 2)));
		expect.add(mockAdyacentMove(human, board, source, Point.getInstance(1, 2)));
		expect.add(mockAdyacentMove(human, board, source, Point.getInstance(2, 1)));
		expect.add(mockAdyacentMove(human, board, source, Point.getInstance(2, 2)));
		
		assert(strategy.generateBoards(board, source).containsAll(expect));
	}
	
	
	@Test
	public void upperAdyacentEnemy() {
		Point source = Point.getInstance(0, 0);
		
		Board board = new Board(strategy, human, computer).
				putBlob(human, Point.getInstance(4, 4)).
				putBlob(computer, Point.getInstance(4, 5));
		
		Board expect = new Board(strategy, human, computer).
				putBlob(human, Point.getInstance(4, 4)).
				putBlob(human, Point.getInstance(4, 5));
		assert(strategy.generateBoards(board, source).contains(expect));
	}
	
	private Pair<Board, Movement> mockAdyacentMove(Player p, Board board, Point source, Point target){
		return 	new Pair<Board, Movement>(
				board.putBlob(p, target), new Movement(source, target));
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
