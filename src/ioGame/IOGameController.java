package ioGame;


import game.BlobStrategy;
import game.Computer;
import game.Game;
import game.Human;
import game.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import structures.MinMaxTree;


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
		MinMaxTree ai = new MinMaxTree(game.getStrategy(), game.getBoard(), 4, game.getPlayers());
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
