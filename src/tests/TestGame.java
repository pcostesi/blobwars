package tests;

import static org.junit.Assert.*;
import game.Board;
import game.Game;
import game.Movement;

import org.junit.Test;

import structures.Point;

public class TestGame {
	
	@Test 
	public void testWin(){
		
		Game startingGame = new Game();
		assertFalse(startingGame.hasWin(startingGame.getComputer()));
		assertFalse(startingGame.hasWin(startingGame.getHuman()));
		
		Game winGame = surroundedBoardGame();
		assertFalse(winGame.hasWin(winGame.getComputer()));
		assertTrue(winGame.hasWin(winGame.getHuman()));
		
		Game almostFinishedGame = almostFinishedGame(); 
		assertFalse(almostFinishedGame.hasWin(almostFinishedGame.getComputer()));
		
		// Move a computer blob near a human blob
		almostFinishedGame.move(new Movement(new Point(2, 5), new Point(2, 4)));
		
		assertFalse(almostFinishedGame.hasWin(almostFinishedGame.getHuman()));
		assertTrue(almostFinishedGame.hasWin(almostFinishedGame.getComputer()));
	}
	
	// computer sorrounded by player
	private Game surroundedBoardGame(){
		char[] board = new char[8 * 8];
		for (int i = 0; i < 8 * 8; i++){
			board[i] = '1';
		}
		board[0] = '2';
		return new Game(board);
	}
	
	private void emptyBoard(Board board){
		// Remove starting tiles
		board.deleteTile(new Point(0, 0));
		board.deleteTile(new Point(7, 0));
		board.deleteTile(new Point(7, 7));
		board.deleteTile(new Point(0, 7));
	}
	
	private Game almostFinishedGame(){
		Game game = new Game();
		emptyBoard(game.getBoard());
		
		game.getBoard().setTile(new Point(2, 3), game.getHuman());
		game.getBoard().setTile(new Point(2, 5), game.getComputer());
		return game;
	}
	

}
