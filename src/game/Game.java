package game;

import frontend.GameController;
import structures.Point;

/**
 * The actual game
 */
public class Game {
	
	private Board board;
	private transient GameController observer;
	private Strategy strategy;
	private Computer computer;
	private Human human;
	
	/**
	 * Instantiates a new game.
	 */
	public Game(){
		this.computer = new Computer();
		this.human = new Human();	
		this.strategy = new BlobStrategy(human, computer);
		this.board = strategy.startingBoard();
	}
	
	/**
	 * Instantiates a new game with a given board
	 *
	 * @param charBoard an array of chars with 1 or 2 depending on the player
	 */
	public Game(char[] charBoard){
		this();
		this.board = new Board(strategy, human, computer, charBoard);
	}
	
	/**
	 * Gets the board.
	 *
	 * @return the board
	 */
	public Board getBoard(){
		return board;
	}


	/**
	 * Gets the board height.
	 *
	 * @return the board height
	 */
	public static int getBoardHeight() {
		return Board.SIZE;
	}

	/**
	 * Gets the board width.
	 *
	 * @return the board width
	 */
	public static int getBoardWidth() {
		return Board.SIZE;
	}
	
	
	/**
	 * Starts a game with a given GameController for visual mode.
	 *
	 * @param observer the observer
	 */
	public void start(GameController observer) {
		this.observer = observer;
	}
	
	/**
	 * Validates and then moves a human tile if it is their own.
	 *
	 * @param move the move
	 * @return true, if it is a valid move for a human tile.
	 */
	public boolean humanMove(Movement move){
		if (board.getTileOwner(move.source) == human){
			return move(move);
		}
		return false;
	}
	
	/**
	 * Validates with the game strategy if it is a valid move, then moves a tile.
	 *
	 * @param move the move
	 * @return true, if successful
	 */
	public boolean move(Movement move){
		if (strategy.isValid(board, move)){
			this.board = strategy.move(board, board.getTileOwner(move.source), move);
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the strategy.
	 *
	 * @return the strategy
	 */
	public Strategy getStrategy(){
		return strategy;
	}
	
	/**
	 * Gets the computer player.
	 *
	 * @return the computer
	 */
	public Computer getComputer(){
		return computer;
	}
	
	/**
	 * Gets the human player.
	 *
	 * @return the human
	 */
	public Human getHuman(){
		return human;
	}
	
	/**
	 * Gets the opponent player for a certain player in a given game.
	 *
	 * @param player the player
	 * @return the opponent
	 */
	public Player getOpponent(Player player){
		return player.equals(human) ? computer : human;
	}
	
	/**
	 * Checks for win.
	 *
	 * @param player the player
	 * @return true, if successful
	 */
	public boolean hasWin(Player player){
		int opponentTiles = this.board.countTilesForPlayer(getOpponent(player));
		
		if (opponentTiles == 0){
			return true;
		}
		
		if (!this.board.hasAvailableMoves(player)){
			return this.board.countTilesForPlayer(player) > opponentTiles;
		}
		
		return false;
	}
	
}
