package game;

import structures.Pair;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Board {
	protected static int width ;
	protected static int height;
	protected static Strategy strategy; 
	
	Tile[][] tiles;
	private Map<Player, LinkedList<Point>> blobs;

	public Board(int width, int height){
		this.tiles = new Tile[height][width];
//		this.blobs = new HashMap<Player, LinkedList<Point>>();
		this.height = height;
		this.width = width;
		fillTiles();
		
	}
	
//	public void addPlayer(Player player){
//		this.blobs.put(player, new LinkedList<Point>);
//	}
	
	public Board(Tile[][] tiles, Map<Player, LinkedList<Point>> blobs){
		this.tiles = Arrays.copyOf(tiles, tiles.length);
		for (int i = 0; i < Board.height; i++){
			this.tiles[i] = Arrays.copyOf(tiles[i], tiles[i].length); //tiles[i].clone();	
		}
//		this.blobs = new HashMap<Player, LinkedList<Point>>(blobs);
		this.height = tiles.length;
		this.width = tiles[0].length;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	//ATENCION only method that modifies board
	public void setTile(Point target, Tile tile){
		tiles[(int) target.getY()][(int) target.getX()] = tile;
	}
	
	public Board putBlob(Player player, Point target){
		Board board = new Board(this.tiles, this.blobs);
		
		Blob blob = new Blob(player);
		board.tiles[(int) target.getY()][(int) target.getX()] = blob;
//		board.blobs.get(player).add(target);
		return board;
	}
	
	 public Board deleteBlob(Player player, Point target){
		Board board = new Board(this.tiles, this.blobs);
		
		board.tiles[(int) target.getY()][(int) target.getX()] = new EmptyTile();
		
//		board.blobs.get(player).remove(target);
		return board;
	}
	 
	public Tile getTile(Point source){
		return this.tiles[(int) source.getY()][(int) source.getX()];
	}
	
	private void fillTiles(){
		int i, j;
		for (i=0; i < height; i++){
			for (j=0; j < width; j++){
				tiles[i][j] = new EmptyTile();
			}
			
		}
	}
	
	public List<Pair<Board, Movement>> generateChildren(Player player){
		List<Pair<Board, Movement>> children = new LinkedList<Pair<Board, Movement>>();
		
		for(Point source : blobs.get(player)){
			children.addAll(this.strategy.generateBoards(this, source));
		}
				
		return children;
	}
	
	public String toString(){
		StringBuilder result = new StringBuilder();
		for (Tile[] row : tiles){
			for (Tile tile : row){
				result.append("| " + tile.toString() + " ");
			}
			result.append("|\n");
			for (int i = 0; i < row.length; i++){
				result.append("+---");
			}
			result.append("+");
			result.append("\n");
		}
		return result.toString();
	}
}
