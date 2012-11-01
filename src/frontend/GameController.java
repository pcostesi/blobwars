package frontend;

import structures.Point;
import game.Game;
import game.Movement;
import ioGame.Options;
import ai.ABPMinimax;
import ai.LMinimax;
import ai.Minimax;

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

		if (options.prune()) {
			ai = new ABPMinimax(options.getValue(), game.getStrategy(), game.getBoard(),
					game.getComputer(), game.getHuman());
		} else {
			ai = new LMinimax(options.getValue(), game.getStrategy(), game.getBoard(),
					game.getComputer(), game.getHuman());
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
		for (int i = 0; i < Game.getBoardHeight(); i++){
			for (int j = 0; j < Game.getBoardWidth(); j++){
				char tile = game.getBoard().getTile(Point.getInstance(j, i));
				if (tile != ' '){
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
