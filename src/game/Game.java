package game;

import frontend.GameController;
import structures.Point;

public class Game {
	private Board board;
	private transient GameController observer;
	private Strategy strategy;
	private Computer computer;
	private Human human;
	
	public Game(){
		this.computer = new Computer();
		this.human = new Human();	
		this.strategy = new BlobStrategy(human, computer);
		this.board = strategy.startingBoard();
	}
	
	public Board getBoard(){
		return board;
	}


	public int getBoardHeight() {
		return Board.SIZE;
	}

	public int getBoardWidth() {
		return Board.SIZE;
	}
	
	
	public void start(GameController observer) {
		this.observer = observer;
		this.strategy.setObserver(observer);
		printBoard();
	}
	
	public void move(Movement move){
		if (strategy.isValid(board, move)){
			this.board = strategy.move(board, board.getTileOwner(move.source), move);
		}
	}
	
	private void printBoard() {
		for (int i = 0; i < getBoardHeight(); i++){
			for (int j = 0; j < getBoardWidth(); j++){
				Point source = Point.getInstance(j, i);
				if (board.getTile(source) != ' '){
					observer.onTileUpdate(i, j, board.getTile(source));
				}
			}
		}
	}
	
	public Strategy getStrategy(){
		return strategy;
	}
	
	public Player[] getPlayers(){
		Player[] players = new Player[2];
		players[0] = computer;
		players[1] = human;
		return players;
	}
	
}
