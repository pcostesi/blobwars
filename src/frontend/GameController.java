package frontend;

import game.Game;
import game.Movement;
import structures.Point;

public class GameController{

	private Game game;
	private Window container;

	public GameController() {
		initialize();
	}

	private void initialize() {
		container = new Window(this);
		game = new Game();
		startGame();
	}

	public void move(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		Point source = Point.getInstance(sourceColumn, sourceRow);
		Point target = Point.getInstance(targetColumn, targetRow);
		Movement move = new Movement(source, target);
		this.game.move(move);
	}

	public void onTileUpdate(int row, int column, char player) {
		container.getView().updateTile(row, column, player);
	}

	public void quit() {
		System.exit(0);
	}

	private void startGame() {
		container.setGame(game.getBoardHeight(), game.getBoardWidth());
		container.setGameVisible();
		game.start(this);
	}

	public void onWin() {
		container.showWin();
	}

}
