package frontend;

import game.Game;
import game.Movement;
import ioGame.Options;
import ai.ABPMinimax;
import ai.LMinimax;
import ai.Minimax;

public class GameController{

	private Game game;
	private Window container;
	boolean humanTurn;
	boolean processingMove;
	private Options options;
	private Minimax ai;

	public GameController(Options options) {
		this.options = options;
		initialize();
		
		if (options.prune()){
			ai = new ABPMinimax(4, game.getStrategy(), game.getBoard(), game.getComputer(), game.getHuman());
		} else {
			ai = new LMinimax(4, game.getStrategy(), game.getBoard(), game.getComputer(), game.getHuman());
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
		if (humanTurn){
			humanTurn = !this.game.humanMove(move);
			if (this.game.hasWin(game.getHuman())){
				container.showWin();
			}
		}
		
		if (!humanTurn && !processingMove){
			processingMove = true;
			
			ai.setBoard(game.getBoard());
			this.game.move(ai.getBestMove());
			
			if (this.game.hasWin(game.getComputer())){
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

	private void startGame() {
		container.setGame(game.getBoardHeight(), game.getBoardWidth());
		container.setGameVisible();
		game.start(this);
	}
}
