package game;

import structures.Pair;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.management.RuntimeErrorException;

public class Board implements Cloneable {
	protected static final int SIZE = 8;
	private Strategy strategy; 
	
	char[] tiles;
	private Map<Player, List<Point>> blobs = new HashMap<Player, List<Point>>();

	public Board(Strategy strategy, Player... players){
		this.strategy = strategy;
		this.tiles = new char[SIZE * SIZE];
		fillTiles();
		for (Player p : players){
			blobs.put(p, new LinkedList<Point>());
		}
		
	}
	
	public int countTilesForPlayer(Player p){
		return blobs.containsKey(p) ? blobs.get(p).size() : 0;
	}
	
	public Board(Board other){
		this.tiles = Arrays.copyOf(other.tiles, SIZE * SIZE);
		this.blobs = new HashMap<Player, List<Point>>();
		if (other.blobs != null){
			for (Entry<Player, List<Point>> es : other.blobs.entrySet()){
				List<Point> points = new LinkedList<Point>(es.getValue());
				this.blobs.put(es.getKey(), points);
			}
		}
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
		tiles[pointToIndex(target)] = p.toTile();
	}
	
	public Board putBlob(Player player, Point target){
		setTile(target, player);
		blobs.get(player).add(target);
		return this;
	}
	
	 public Board deleteBlob(Player player, Point target){
		tiles[pointToIndex(target)] = ' ';
		blobs.get(player).remove(target);
		return this;
	}
	 
	public char getTile(Point source){
		return this.tiles[pointToIndex(source)];
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
		int i;
		for (i=0; i < SIZE * SIZE; i++){
				tiles[i] = ' ';
		}
	}
	
	public List<Pair<Board, Movement>> generateChildren(Player player){
		List<Pair<Board, Movement>> children = new LinkedList<Pair<Board, Movement>>();
		
		for(Point source : blobs.get(player)){
			children.addAll(strategy.generateBoards(this, source));
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
	
	private int pointToIndex(Point p){
		return (int) (p.getX() + p.getY() * SIZE);
	}
	
	public Object clone(){
		return new Board(this);
	}
	
}
