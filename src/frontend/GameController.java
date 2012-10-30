package frontend;

import game.Game;
import game.Movement;
import ai.LevelMinimax;
import ai.Minimax;

public class GameController{

	private Game game;
	private Window container;
	boolean humanTurn;
	boolean processingMove;

	public GameController() {
		initialize();
	}

	private void initialize() {
		container = new Window(this);
		game = new Game();
		humanTurn = true;
		processingMove = false;
		startGame();
	}

	public void play(Movement move) {
		if (humanTurn){
			humanTurn = !this.game.humanMove(move);
		}
		
		if (!humanTurn && !processingMove){
			processingMove = true;
			
			
			Minimax ai; 
			ai = new LevelMinimax(game.getStrategy(), game.getBoard(), 4, game.getHuman(), game.getComputer());
			this.game.move(ai.getBestMove());
			
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

	private void startGame() {
		container.setGame(game.getBoardHeight(), game.getBoardWidth());
		container.setGameVisible();
		game.start(this);
	}

	public void onWin() {
		container.showWin();
	}

}
