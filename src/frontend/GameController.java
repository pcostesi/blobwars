package frontend;

import game.Game;
import game.Movement;
import optparse.Options;
import structures.Point;
import ai.ABPMinimax;
import ai.LMinimax;
import ai.Minimax;
import ai.TBIDABPMinimax;
import ai.TBIDLMinimax;

public class GameController {

	private Game game;
	private Window container;
	boolean humanTurn;
	boolean processingMove;

	private Options options;
	private Minimax ai;

	public GameController(Options options) {
		this.options = options;
		initialize();

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
	}

	private void initialize() {
		container = new Window(this);
		game = new Game();
		humanTurn = true;
		processingMove = false;
		startGame();
	}

	public void play(Movement move) {
		if (humanTurn) {
			humanTurn = !this.game.humanMove(move);
			printBoard();
			if (this.game.hasWin(game.getHuman())) {
				container.showWin();
			}
		}

		if (!humanTurn && !processingMove) {
			processingMove = true;

			ai.setBoard(game.getBoard());
			this.game.move(ai.getBestMove());

			printBoard();
			if (this.game.hasWin(game.getComputer())) {
				container.showLose();
			}

			processingMove = false;
			humanTurn = true;
		}
	}

	public void onTileUpdate(int row, int column, char player) {
		container.getView().updateTile(row, column, player);
	}

	public void quit() {
		System.exit(0);
	}

	private void printBoard() {
		for (int i = 0; i < Game.getBoardHeight(); i++) {
			for (int j = 0; j < Game.getBoardWidth(); j++) {
				char tile = game.getBoard().getTile(new Point(j, i));
				if (tile != ' ') {
					container.getView().updateTile(i, j, tile);
				}
			}
		}
		container.getView().refresh();
	}

	private void startGame() {
		container.setGame(game.getBoardHeight(), game.getBoardWidth());
		container.setGameVisible();
		game.start(this);
		printBoard();
	}
}
