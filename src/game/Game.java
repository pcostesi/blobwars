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
		printBoard();
	}
	
	public void move(Movement move){
		System.out.println(move);
		if (strategy.isValid(board, move)){
			this.board = strategy.move(board, human, move);
			// We could print all the board, or the strategy could know which ones to print
			
			printBoard();
		}
		
	}

	private void printBoard() {
		for (int i = 0; i < getBoardHeight(); i++){
			for (int j = 0; j < getBoardWidth(); j++){
				Point source = Point.getInstance(j, i);
				if (board.getTile(source) != ' '){
					System.out.println(source);
					observer.onTileUpdate(i, j, board.getTile(source));
				}
			}
		}
	}
}
