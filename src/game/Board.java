package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import structures.Pair;
import structures.Point;
import util.Chain;

public class Board implements Cloneable {
	protected static final int SIZE = 8;
	private Strategy strategy; 
	
	Player[] owner;
	private Player player1;
	private Player player2;
	
	public Board(Strategy strategy, Player pl1, Player pl2){
		this.strategy = strategy;
		this.owner = new Player[SIZE * SIZE];
		player1 = pl1;
		player2 = pl2;
		
	}
	
	public int countTilesForPlayer(Player p){
		int base = 0;
		for (Player cur : owner){
			if (cur != null && cur.equals(p)){
				base++;
			}
		}
		return base;
	}
	
	public Board(Board other){
		//this.tiles = Arrays.copyOf(other.tiles, SIZE * SIZE);
		this.owner = Arrays.copyOf(other.owner, SIZE * SIZE);
		this.player1 = other.player1;
		this.player2 = other.player2;
		this.strategy = other.strategy;
		
	}
	
	public int getWidth(){
		return SIZE;
	}
	
	public int getHeight(){
		return SIZE;
	}
	
	//ATTENTION only method that modifies board
	public void setTile(Point target, Player p){
		owner[pointToIndex(target)] = p;
	}
	
	public Board putBlob(Player player, Point target){
		setTile(target, player);
		
		return this;
	}
	
	 public Board deleteBlob(Player player, Point target){
		//tiles[pointToIndex(target)] = ' ';
		owner[pointToIndex(target)] = null;
		return this;
	}
	 
	public char getTile(Point source){
		Player p = getTileOwner(source);
		if (p == null){
			return ' ';
		}
		return p.toTile();
	}
	
	public Player getTileOwner(Point source){
		return owner[pointToIndex(source)];
	}
	
	
	public Iterable<Pair<Board, Movement>> generateChildren(Player player){
		List<Iterable<Pair<Board, Movement>>> children = new ArrayList<Iterable<Pair<Board, Movement>>>(SIZE * SIZE);
		for (int i = 0; i < SIZE; i++){
			for (int j = 0; j < SIZE; j++){
				Point source = Point.getInstance(j, i);
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

	private int pointToIndex(Point p){
		return (p.getX() + p.getY() * SIZE);
	}
	
	public Object clone(){
		return new Board(this);
	}
	
}
