package tests;

import static org.junit.Assert.*;

import game.BlobStrategy;
import game.Board;
import game.Computer;
import game.Human;
import game.Movement;
import game.Player;
import game.Strategy;
import game.Tile;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import structures.Pair;

public class TestGenerateBoards {
	private Strategy strategy;
	
	private Player computer = new Computer();
	private Player human = new Human();

	public void before(){
		this.strategy = new BlobStrategy();
	}
	
	@Test
	public void upperLeftCorner() {
		Board board = starterBoard(); 
		Point source = new Point(0, 0);
		
		List<Pair<Board, Movement>> expect = new LinkedList<Pair<Board, Movement>>();
		
		expect.add(mockAdyacentMove(human, board, source, new Point(1, 0)));
		expect.add(mockAdyacentMove(human, board, source, new Point(0, 1)));
		expect.add(mockAdyacentMove(human, board, source, new Point(1, 1)));
		
		assert(strategy.generateBoards(board, source).containsAll(expect));
	}
	
	
	@Test
	public void upperAdyacentEnemy() {
		Point source = new Point(0, 0);
		
		Board board = new Board(8, 8).
				putBlob(human, new Point(4, 4)).
				putBlob(computer, new Point(4, 5));
		
		Board expect = new Board(8, 8).
				putBlob(human, new Point(4, 4)).
				putBlob(human, new Point(4, 5));
		
		assert(strategy.generateBoards(board, source).contains(expect));
	}
	
	private Pair<Board, Movement> mockAdyacentMove(Player p, Board board, Point source, Point target){
		return 	new Pair<Board, Movement>(
				board.putBlob(p, target), new Movement(source, target));
	}

	private Board starterBoard(){
		Board baseBoard = new Board(8, 8);
		baseBoard = baseBoard.
				putBlob(human, new Point(0, 0)).
				putBlob(human, new Point(0, 7)).
				putBlob(computer, new Point(7, 0)).
				putBlob(computer, new Point(7, 7));
		
		return baseBoard;
	}
	
}
