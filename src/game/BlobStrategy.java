package game;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import structures.Pair;

public class BlobStrategy implements Strategy{

	@Override
	public List<Pair<Board, Movement>> generateBoards(Board board, Point source) {
		
		Player player = null;
		// devuelve board y destino, enlistados
		List<Pair<Board, Movement>> list = new LinkedList<Pair<Board, Movement>>();
		
		addMoves(list, board, player, source);
		
		return list;
	}
	
	private Pair<Board, Movement> adyacentMove(Board board, Player player, Point source, Point target){
		Movement move = new Movement(source, target);
		
		board.putBlob(player, target);
		
		return new Pair<Board, Movement>(board, move);
	}
	
	// Only method that modifies the board
	private void attack(Player player, Board board, Point source){
		for (int dx = -1; dx < 2; dx++){
			for (int dy = -1; dy < 2; dy++){
				if (dx == 0 && dy == 0){
					continue;
				}
				
				int x = (int) (source.getX() + dx);
				int y = (int) (source.getY() + dy);
				
				if (x < 0 || y < 0 || x > board.getWidth() || y > board.getHeight()){
					continue;
				}
				
				Point target = new Point(x, y);
				
				if (board.getTileOwner(target) == null){
					board.setTile(target, player);
				}
			}
		}
	}
	
	private int distance(Point source, Point target){
		return (int) Math.min(Math.abs(source.getX() - target.getX()), Math.abs(source.getY() - target.getY()));
	}
	
	private void addMoves(List<Pair<Board, Movement>> list, Board board, Player player, Point source){
		for (int dx = -2; dx < 3; dx++){
			for (int dy = -2; dy < 3; dy++){
				if (dx == 0 && dy == 0){
					continue;
				}
				
				int x = (int) (source.getX() + dx);
				int y = (int) (source.getY() + dy);
				
				if (x < 0 || y < 0 || x > board.getWidth() || y > board.getHeight()){
					continue;
				}
				
				Point target = new Point(x, y);
				
				if (board.getTileOwner(target) == null){
					if (distance(target, source) <= 1){
						list.add(adyacentMove(board, player, source, target));
					} else {
						list.add(twoCellsMove(board, player, source, target));
					}
					attack(player, board, target);
				}
			}
		}
	}
	
	
	private Pair<Board, Movement> twoCellsMove(Board board, Player p, Point source, Point target){
		Movement move = new Movement(source, target);
		board.putBlob(p, target);
		board.deleteBlob(p, source);
		
		return new Pair<Board, Movement>(board, move);
	}
	
	
	@Override
	public boolean isValid(Board board, Movement move) {
		// TODO Auto-generated method stub
		Point source = move.source;
		Point target = move.target;
		if (source == target || distance(source, target) > 2){
			return false;
		}
		if (board.getTileOwner(source) == null || 
				board.getTileOwner(target) != null){
			return false;
		}
		return true;
	}

	@Override
	public int evaluateScore(Board board) {
		// TODO Auto-generated method stub
		return 5;
	}


}
