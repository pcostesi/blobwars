package ioGame;


import game.Computer;
import game.Game;
import game.Human;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ai.LevelMinimax;
import ai.Minimax;


public class IOGameController {
	File file;
	Game game;
	
	public IOGameController(String fileName, String player){
		char[] charBoard = openCharBoard(fileName);
		Human human = new Human();
		Computer computer = new Computer();
		
		this.game = new Game(charBoard);
		System.out.println(this.game.getBoard());
		//TODO: delete magic number
		Minimax ai = new LevelMinimax(game.getStrategy(), game.getBoard(), 4, game.getHuman(), game.getComputer());
		System.out.println(ai.getBestMove());
			
	}
	
	private char[] openCharBoard(String path){
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(path));
			
			char[] file = new char[64];
			
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
