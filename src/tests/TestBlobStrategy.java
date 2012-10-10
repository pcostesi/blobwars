package tests;

import static org.junit.Assert.*;

import game.BlobStrategy;
import game.Board;
import game.Strategy;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class TestBlobStrategy {
	private Strategy strategy;

	public void before(){
		this.strategy = new BlobStrategy();
	}
	
	@Test
	public void testGenerateBoardsCorner() {
		Board board = new Board(10,10);
		
		List<Board> expect = new LinkedList<Board>();
		expect.add
		assert(strategy.generateBoards(board, new Point(9,9)).containsAll(expect));
	}

}
