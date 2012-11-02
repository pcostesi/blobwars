package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import structures.Pair;
import structures.Point;
import util.Chain;

/**
 * The Board represents a squared game board. In reality, it is implemented by a large array of references to each Player.
 */
public class Board implements Cloneable {
	
	protected static final int SIZE = 8;
	
	private Strategy strategy;
	
	Player[] owner;
	
	private Player player1;
	private Player player2;
	
	/**
	 * Instantiates a new board.
	 *
	 * @param strategy the strategy
	 * @param pl1 the pl1
	 * @param pl2 the pl2
	 */
	public Board(Strategy strategy, Player pl1, Player pl2){
		this.strategy = strategy;
		this.owner = new Player[SIZE * SIZE];
		player1 = pl1;
		player2 = pl2;
	}
	
	/**
	 * Instantiates a new board with a given array of chars 1 for player 1, 2 for player 2
	 *
	 * @param strategy the strategy
	 * @param pl1 the pl1
	 * @param pl2 the pl2
	 * @param charBoard the char board
	 */
	public Board(Strategy strategy, Player pl1, Player pl2, char[] charBoard){
		this(strategy, pl1, pl2);
		convertCharsToPlayers(charBoard);
	}
	
	/**
	 * Convert an array of chars to the board players.
	 *
	 * @param charBoard the char board
	 */
	private void convertCharsToPlayers(char[] charBoard){
		for (int i = 0; i < SIZE * SIZE; i++){
			if (charBoard[i] == '1'){
				this.owner[i] = player1;
			} else if (charBoard[i] == '2') {
				this.owner[i] = player2;
			}
		}
		
	}
	
	/**
	 * Count tiles for a given player.
	 *
	 * @param p the player
	 * @return the number of tiles
	 */
	public int countTilesForPlayer(Player p){
		int base = 0;
		for (Player cur : owner){
			if (cur != null && cur.equals(p)){
				base++;
			}
		}
		return base;
	}
	
	/**
	 * Gradient for player.
	 *
	 * @param p the p
	 * @return the int
	 */
	public int gradientForPlayer(Player p){
		int base = 0;
		for (Player cur : owner){
			if (cur == null) continue;
			if (cur.equals(p)){
				base++;
			} else {
				base--;
			}
		}
		return base;
	}
	
	/**
	 * Count all the used, not empty tiles.
	 *
	 * @return the count
	 */
	public int countTiles(){
		int tiles = 0;
		for (int i = 0; i < SIZE * SIZE; i++){
			if (owner[i] != null){
				tiles++;
			}
		}
		return tiles;
	}
	
	/**
	 * Instantiates a new board from an other board. Note that it only creates a copy of the tiles.
	 *
	 * @param other the other
	 */
	public Board(Board other){
		this.owner = Arrays.copyOf(other.owner, SIZE * SIZE);
		this.player1 = other.player1;
		this.player2 = other.player2;
		this.strategy = other.strategy;
	}
	
	
	/**
	 * Gets the board width.
	 *
	 * @return the width
	 */
	public int getWidth(){
		return SIZE;
	}
	
	/**
	 * Gets the board height.
	 *
	 * @return the height
	 */
	public int getHeight(){
		return SIZE;
	}
	
	/**
	 * Sets a tile.
	 *
	 * @param target the location
	 * @param p the player
	 */
	public void setTile(Point target, Player p){
		owner[pointToIndex(target)] = p;
	}
	
	/**
	 * Deletes a tile.
	 *
	 * @param target the location
	 */
	public void deleteTile(Point target){
		owner[pointToIndex(target)] = null;
	}
	
	/**
	 * Puts blob. Similar to setTile, but returns the board.
	 *
	 * @param player the player
	 * @param target the target
	 * @return the board
	 */
	public Board putBlob(Player player, Point target){
		setTile(target, player);
		return this;
	}
	
	 /**
 	 * Delete blob. Similar to deleteTile, but requires a Player
 	 *
 	 * @param player the player
 	 * @param target the target
 	 * @return the board
 	 */
 	public Board deleteBlob(Player player, Point target){
		deleteTile(target);
		return this;
	}
	 
	/**
	 * Gets the tile (char) in a given location.
	 *
	 * @param source the location
	 * @return the tile, c for computer, h for human
	 */
	public char getTile(Point source){
		Player p = getTileOwner(source);
		if (p == null){
			return ' ';
		}
		return p.toTile();
	}
	
	/**
	 * Gets the tile owner.
	 *
	 * @param source the source
	 * @return the tile owner
	 */
	public Player getTileOwner(Point source){
		return owner[pointToIndex(source)];
	}
	
	
	/**
	 * Generate children.
	 *
	 * @param player the player
	 * @return the iterable
	 */
	public Iterable<Pair<Board, Movement>> generateChildren(Player player){
		List<Iterable<Pair<Board, Movement>>> children = new ArrayList<Iterable<Pair<Board, Movement>>>(SIZE * SIZE);
		for (int i = 0; i < SIZE; i++){
			for (int j = 0; j < SIZE; j++){
				Point source = new Point(j, i);
				if (owner[pointToIndex(source)] == player){
					children.add(strategy.boardsForMove(this, source));
				}
			}
		}
		
		Chain<Pair<Board, Movement>> chain = new Chain<Pair<Board, Movement>>(children);
		return chain;
	}
	
	

	public String toString(){
		StringBuilder result = new StringBuilder();

		for (int z = 0; z < SIZE; z++){
			result.append("+---");
		}
		result.append("+").append("\n");
		for (int i = 0; i < SIZE; i++){
			for (int j = 0; j < SIZE; j++){
				char tile = ' ';
				if (owner[i * SIZE + j] != null){
					tile = owner[i * SIZE + j].toTile();
				}
				result.append("| ").append(tile).append(" ");
			}
			result.append("|\n");
			for (int z = 0; z < SIZE; z++){
				result.append("+---");
			}
			result.append("+").append("\n");
		}
		return result.toString();
	}

	/**
	 * Point to index. Used to convert a Point into a number to retrieve a char for the owner array.
	 *
	 * @param p the point
	 * @return the int
	 */
	private int pointToIndex(Point p){
		return (p.getX() + p.getY() * SIZE);
	}
	

	public Object clone(){
		return new Board(this);
	}
	
	/**
	 * Checks for available move for a certain Point
	 *
	 * @param source the source
	 * @return true, if successful
	 */
	public boolean hasAvailableMove(Point source){
		return strategy.hasAvailableMove(this, source);
	}

	/**
	 * Checks for available moves for a player, looks for every tile it has.
	 *
	 * @param player the player
	 * @return true, if successful
	 */
	public boolean hasAvailableMoves(Player player){
		for (int i = 0; i < SIZE; i++){
			for (int j = 0; j < SIZE; j++){
				Point source = new Point(j, i);
				if (owner[pointToIndex(source)] == player){
					if (hasAvailableMove(source)){
						return true;
					}
				}
			}
		}	
		return false;
	}
	
	/**
	 * Count available moves for a given player.
	 *
	 * @param player the player
	 * @return the int
	 */
	public int countAvailableMoves(Player player){
		int itr = 0;
		for (int i = 0; i < SIZE; i++){
			for (int j = 0; j < SIZE; j++){
				Point source = new Point(j, i);
				if (owner[pointToIndex(source)] == player){
					if (hasAvailableMove(source)){
						itr++;
					}
				}
			}
		}	
		return itr;
	}
	
}
