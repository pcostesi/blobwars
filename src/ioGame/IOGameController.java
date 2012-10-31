package ioGame;

import game.Computer;
import game.Game;
import game.Human;
import game.Movement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ai.LMinimax;
import ai.Minimax;

public class IOGameController {
	File file;
	Game game;
	
	public IOGameController(String fileName, String playerNumber){
		char[] charBoard = openCharBoard(fileName);
		
		if (playerNumber.equals("2")){
			invertPlayers(charBoard);
		}
		
		this.game = new Game(charBoard);
		
		System.out.println(this.game.getBoard());
		//TODO: delete magic number
		Minimax ai = new LMinimax(4, game.getStrategy(), game.getBoard(), game.getHuman(), game.getComputer());
		System.out.println(ai.getBestMove());
		
		
		Movement move = ai.getBestMove();
		
		if (move == null){
			System.out.println("PASS");
		} else {
			System.out.println(ai.getBestMove());
		}
			
	}

	
	private void invertPlayers(char[] charBoard){
		for (int i = 0; i < charBoard.length; i++){
			if (charBoard[i] == '1'){
				charBoard[i] = '2';
			} else if (charBoard[i] == '2') {
				charBoard[i] = '1';
			}
		}
	}
	
	private char[] openCharBoard(String path){
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(path));
			
			char[] file = new char[game.getBoardHeight() * game.getBoardWidth()];
			
			buffer.read(file);
			buffer.close();
			
			return file;
			
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		
	}

}
