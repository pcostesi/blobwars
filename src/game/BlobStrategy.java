package game;

import java.util.LinkedList;
import java.util.List;

import structures.Pair;
import structures.Point;

public class BlobStrategy implements Strategy{

	Human human;
	Computer computer;
	
	public BlobStrategy(Human h, Computer c){
		human = h;
		computer = c;
	}
	
	@Override
	public List<Pair<Board, Movement>> generateBoards(Board board, Point source) {
		
		Player player = board.getTileOwner(source);
		
		if (player == null){
			throw new RuntimeException("Player is null");
		}
		// devuelve board y destino, enlistados
		List<Pair<Board, Movement>> list = new LinkedList<Pair<Board, Movement>>();
		
		addMoves(list, board, player, source);
		
		return list;
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
				
				if (x < 0 || y < 0 || x >= board.getWidth() || y >= board.getHeight()){
					continue;
				}
				
				Point target = Point.getInstance(x, y);
				
				if (board.getTileOwner(target) != null){
					board.setTile(target, player);
				}
			}
		}
	}
	
	private int distance(Point source, Point target){
		return (int) Math.max(Math.abs(source.getX() - target.getX()), Math.abs(source.getY() - target.getY()));
	}
	
	private void addMoves(List<Pair<Board, Movement>> list, Board base, Player player, Point source){
		Board board;
		for (int dx = -2; dx < 3; dx++){
			for (int dy = -2; dy < 3; dy++){
				if (dx == 0 && dy == 0){
					continue;
				}
				
				int x = (int) (source.getX() + dx);
				int y = (int) (source.getY() + dy);
				
				if (x < 0 || y < 0 || x >= base.getWidth() || y >= base.getHeight()){
					continue;
				}
				
				Point target = Point.getInstance(x, y);
				board = (Board) base.clone();
				if (board.getTileOwner(target) != null){
					continue;
				}
				Movement move = new Movement(source, target);
				board = board.putBlob(player, target);
				if (distance(target, source) == 2){
					board = board.deleteBlob(player, source);
				}
				attack(player, board, target);
				list.add(new Pair<Board, Movement>(board, move));
			}
		}
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
	public int evaluateScore(Board board, Player p) {
		int tiles = board.countTilesForPlayer(computer) - board.countTilesForPlayer(human);
		return p == computer ? tiles : -tiles;
	}

	@Override
	public void injectBoards(Board board, Point source,
			List<Pair<Board, Movement>> list) {
		Player player = board.getTileOwner(source);
		
		if (player == null){
			throw new RuntimeException("Player is null");
		}
		addMoves(list, board, player, source);
	}

	@Override
	public Board move(Board board, Player player, Movement move) {
		System.out.println("entra al metodo");
			if (distance(move.source, move.target) > 2){
				return board;
			}
			//evaluar que pasa con cada movimiento
			board.putBlob(player, move.target);
			board.deleteBlob(player, move.source);
			return board;
	}
	
	public Board startingBoard(){
		Board board = new Board(this, human, computer);
		board.putBlob(human, new Point(0, 7));
		board.putBlob(human, new Point(0, 0));
		board.putBlob(computer, new Point(0, 7));
		board.putBlob(computer, new Point(7, 7));
		return board;
	}
}
