package game;

import structures.Pair;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Board {
	protected static final int SIZE = 8;
	private Strategy strategy; 
	
	char[] tiles;
	private Map<Player, List<Point>> blobs;

	public Board(){
		this.tiles = new char[SIZE * SIZE];
		fillTiles();
		
	}
	
//	public void addPlayer(Player player){
//		this.blobs.put(player, new LinkedList<Point>);
//	}
	
	public Board(Board other){
		this.tiles = Arrays.copyOf(other.tiles, SIZE * SIZE);
		this.blobs = new HashMap<Player, List<Point>>();
		if (other.blobs != null){
			for (Entry<Player, List<Point>> es : other.blobs.entrySet()){
				List<Point> points = new LinkedList<Point>(es.getValue());
				this.blobs.put(es.getKey(), points);
			}
		}
		
	}
	
	public int getWidth(){
		return SIZE;
	}
	
	public int getHeight(){
		return SIZE;
	}
	
	//ATENCION only method that modifies board
	public void setTile(Point target, Player p){
		tiles[(int) target.getY() * SIZE + (int) target.getX()] = p.toTile();
	}
	
	public Board putBlob(Player player, Point target){
		Board board = new Board(this);
		
		board.setTile(target, player);
//		board.blobs.get(player).add(target);
		return board;
	}
	
	 public Board deleteBlob(Player player, Point target){
		Board board = new Board(this);
		
		board.tiles[(int) target.getY() * SIZE + (int) target.getX()] = ' ';
		
//		board.blobs.get(player).remove(target);
		return board;
	}
	 
	public char getTile(Point source){
		return this.tiles[(int) source.getY() * SIZE + (int) source.getX()];
	}
	
	public Player getTileOwner(Point source){
		char t = getTile(source);
		for (Player player : blobs.keySet()){
			if (t == player.toTile()){
				return player;
			}
		}
		return null;
	}
	
	private void fillTiles(){
		int i, j;
		for (i=0; i < SIZE * SIZE; i++){
				tiles[i] = ' ';
		}
	}
	
	public List<Pair<Board, Movement>> generateChildren(Player player){
		List<Pair<Board, Movement>> children = new LinkedList<Pair<Board, Movement>>();
		
		for(Point source : blobs.get(player)){
			children.addAll(this.getStrategy().generateBoards(this, source));
		}
				
		return children;
	}
	
	public String toString(){
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < SIZE; i++){
			for (int j = 0; j < SIZE; j++){
				char tile = this.tiles[i * SIZE + j];
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

	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
}
