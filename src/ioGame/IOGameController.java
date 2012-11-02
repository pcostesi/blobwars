package ioGame;

import game.Board;
import game.Game;
import game.Movement;
import game.Player;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import optparse.Options;
import ai.ABPMinimax;
import ai.LMinimax;
import ai.Minimax;
import ai.TBIDABPMinimax;
import ai.TBIDLMinimax;

public class IOGameController {
	Game game;
	Minimax ai;

	public IOGameController(String fileName, String playerNumber,
			Options options) {

		char[] charBoard = openCharBoard(fileName);
		this.game = new Game(charBoard);
		
		Player minimizer = game.getHuman();
		Player maximizer = game.getComputer();

		
		if (playerNumber.equals("1")) {
			maximizer = game.getHuman();
			minimizer = game.getComputer();
		}
		
		int level = 0;
		int time = 0;
		if (options.hasFlag("prune")) {
			if (options.hasFlag("depth")) {
				level = options.getIntValue("depth");
				ai = new ABPMinimax(level, game.getStrategy(), game.getBoard(),
						maximizer, minimizer);
			} else if (options.hasFlag("maxtime")) {
				time = options.getIntValue("maxtime");
				ai = new TBIDABPMinimax(time * 1000, game.getStrategy(),
						game.getBoard(), maximizer, minimizer);
			}

		} else {
			if (options.hasFlag("depth")) {
				level = options.getIntValue("depth");
				ai = new LMinimax(level, game.getStrategy(), game.getBoard(),
						maximizer, minimizer);
			} else if (options.hasFlag("maxtime")) {
				time = options.getIntValue("maxtime");
				ai = new TBIDLMinimax(time * 1000, game.getStrategy(),
						game.getBoard(), maximizer, minimizer);
			}
		}

		start();
	}

	private void start() {
		Board before = (Board) game.getBoard().clone();
		Movement move = ai.getBestMove();
		game.move(move); // borrar
		Board after = (Board) game.getBoard().clone();

		if (move == null) {
			System.out.println("PASS");
		} else {
			System.out.println("Play: " + move);
		}
		System.out.println();
		System.out.println();
		System.out.println("Metadata about solver");
		System.out.println();
		System.out.println("Using solver: " + ai.getName());
		System.out.println("Max height reached during search: " + ai.getHeight());
		System.out.println("Number of states explored: " + ai.exploredStates());
		System.out.println("Time searching for a solution: " + ai.runTime() + "ms");
		System.out.println();
		System.out.println();
		System.out.println("Ply:");
		System.out.println("Board before playing:");
		System.out.println(before);
		System.out.println();
		System.out.println("Board after playing:");
		System.out.println(after);
	}

	private char[] openCharBoard(String path) {
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(path));

			char[] charBoard = new char[Game.getBoardHeight()
					* Game.getBoardWidth()];
			// buffer.read(charBoard);
			for (int i = 0; i < Game.getBoardHeight(); i++) {
				int j;
				for (j = 0; j < Game.getBoardWidth(); j++) {
					charBoard[i * Game.getBoardHeight() + j] = (char) buffer
							.read();
				}
				if (i * j < Game.getBoardHeight() * Game.getBoardWidth()) {
					buffer.read();
				}
			}
			buffer.close();
			return charBoard;

		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}

	}
}
