package ioGame;

import game.Game;
import game.Movement;
import game.Player;

import java.io.BufferedReader;
import java.io.File;
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
	File file;
	Game game;
	Minimax ai;

	public IOGameController(String fileName, String playerNumber,
			Options options) {

		char[] charBoard = openCharBoard(fileName);

		Player player;
		this.game = new Game(charBoard);

		if (playerNumber.equals("1")) {
			player = game.getHuman();
		} else {
			player = game.getComputer();
		}		start(player);

		int level = 0;
		int time = 0;
		if (options.hasFlag("prune")) {
			if (options.hasFlag("depth")) {
				level = options.getIntValue("depth");
				ai = new ABPMinimax(level, game.getStrategy(), game.getBoard(),
						game.getComputer(), game.getHuman());
			} else if (options.hasFlag("maxtime")) {
				time = options.getIntValue("maxtime");
				ai = new TBIDABPMinimax(time * 1000, game.getStrategy(),
						game.getBoard(), game.getComputer(), game.getHuman());
			}

		} else {
			if (options.hasFlag("depth")) {
				level = options.getIntValue("depth");
				ai = new LMinimax(level, game.getStrategy(), game.getBoard(),
						game.getComputer(), game.getHuman());
			} else if (options.hasFlag("maxtime")) {
				time = options.getIntValue("maxtime");
				ai = new TBIDLMinimax(time * 1000, game.getStrategy(),
						game.getBoard(), game.getComputer(), game.getHuman());
			}
		}
		System.out.println(this.game.getBoard());

	}

	private void start(Player player) {
		System.out.println(this.game.getBoard());

		Movement move = ai.getBestMove();

		game.move(move); // borrar

		if (move == null) {
			System.out.println("PASS");
		} else {
			System.out.println("Play:" + move);
		}
	}

	private char[] openCharBoard(String path) {
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(path));

			char[] charBoard = new char[Game.getBoardHeight()
					* Game.getBoardWidth()];

			buffer.read(charBoard);
			buffer.close();
			System.out.println(charBoard);
			return charBoard;

		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}

	}

}
