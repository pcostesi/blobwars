package game;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import structures.Pair;

public class BlobStrategy implements Strategy{

	@Override
	public List<Pair<Board, Movement>> generateBoards(Board board, Point source) {
		
		Player player = board.getTile(source).getPlayer();
		// devuelve board y destino, enlistados
		List<Pair<Board, Movement>> list = new LinkedList<Pair<Board, Movement>>();
		
		addAdyacentMoves(list, board, player, source);
		
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
				
				if (!board.getTile(target).isEmpty()){
					board.setTile(target, new Blob(player));
				}
			}
		}
	}
	
	private void addAdyacentMoves(List<Pair<Board, Movement>> list, Board board, Player player, Point source){
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
				
				if (board.getTile(target).isEmpty()){
					list.add(adyacentMove(board, player, source, target));
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
		return false;
	}

	@Override
	public int evaluateScore(Board board) {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public Player getPlayer(int level) {
		// TODO Auto-generated method stub
		return null;
	}

}
