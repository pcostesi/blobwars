package frontend;

import game.Game;
import game.Movement;
import structures.Point;
import ai.LevelMinimax;
import ai.Minimax;

public class GameController{

	private Game game;
	private Window container;
	boolean humanTurn;

	public GameController() {
		initialize();
	}

	private void initialize() {
		container = new Window(this);
		game = new Game();
		humanTurn = true;
		startGame();
	}

	public void move(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		if (!humanTurn){
			return;
		}
		
		Point source = Point.getInstance(sourceColumn, sourceRow);
		Point target = Point.getInstance(targetColumn, targetRow);
		Movement move = new Movement(source, target);
		
		humanTurn = !this.game.humanMove(move);
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
		
		boolean win = false;
		Minimax ai; 
	
		while (!win){
			if (humanTurn) {
				
				try {
				    Thread.sleep(1000);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				
			} else {
				ai = new LevelMinimax(game.getStrategy(), game.getBoard(), 4, game.getHuman(), game.getComputer());
				this.game.move(ai.getBestMove());
				humanTurn = true;
			}
		}
	}

	public void onWin() {
		container.showWin();
	}

}
