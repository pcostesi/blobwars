package game;

import structures.Pair;

import java.awt.Point;
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
		this.blobs = new HashMap<Player, LinkedList<Point>>();
		this.height = height;
		this.width = width;
		fillTiles();
	}
	
	public Board(Tile[][] tiles, Map<Player, LinkedList<Point>> blobs){
		this.tiles = tiles;
		this.blobs = blobs;
		this.height = tiles.length;
		this.width = tiles[0].length;
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
}
