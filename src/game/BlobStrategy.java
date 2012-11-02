package game;

import java.util.Iterator;
import java.util.NoSuchElementException;

import structures.Pair;
import structures.Point;

/**
 * An implementation of Strategy for BlobWars.
 */
public class BlobStrategy implements Strategy {

	Human human;
	Computer computer;

	/**
	 * Instantiates a new blob strategy.
	 *
	 * @param h the h
	 * @param c the c
	 */
	public BlobStrategy(Human h, Computer c) {
		human = h;
		computer = c;
	}

	/**
	 * BlobStrategyIterable.
	 */
	private class BlobStrategyIterable implements
			Iterable<Pair<Board, Movement>> {

		private Board base;
		private Point source;
		private Player player;

		/**
		 * Instantiates a new blob strategy iterable.
		 *
		 * @param board the board
		 * @param source the source
		 */
		public BlobStrategyIterable(Board board, Point source) {
			Player player = board.getTileOwner(source);

			if (player == null) {
				throw new RuntimeException("Player is null");
			}

			this.base = board;
			this.player = player;
			this.source = source;
		}

		public Iterator<Pair<Board, Movement>> iterator() {
			return new BlobStrategyIterator(base, source, player);
		}

	}

	/**
	 * The BlobStrategyIterator.
	 */
	private class BlobStrategyIterator implements
			Iterator<Pair<Board, Movement>> {

		private final Board base;
		private Point source;
		private Player player;
		private Movement moves[] = new Movement[5 * 5 - 1];
		private int moveIdx = 0;
		private int top = 0;

		/**
		 * Instantiates a new blob strategy iterator.
		 *
		 * @param base the base
		 * @param source the source
		 * @param player the player
		 */
		public BlobStrategyIterator(Board base, Point source, Player player) {

			this.base = base;
			this.source = source;
			this.player = player;
			generateMoves();
		}

		/**
		 * Generate all the possible moves.
		 */
		private void generateMoves() {
			int idx = 0;
			for (int dx = -2; dx < 3; dx++) {
				for (int dy = -2; dy < 3; dy++) {
					if (dx == 0 && dy == 0) {

						continue;
					}
					int x = (int) (source.getX() + dx);
					int y = (int) (source.getY() + dy);

					if (x < 0 || y < 0 || x >= base.getWidth()
							|| y >= base.getHeight()) {
						continue;
					}
					Point target = new Point(x, y);
					if (base.getTileOwner(target) != null) {

						continue;
					}
					this.moves[idx++] = new Movement(source, target);
				}
			}
			top = idx;

		}

		public boolean hasNext() {
			return moveIdx < top;
		}

		public Pair<Board, Movement> next() {
			if (!hasNext()) {

				throw new NoSuchElementException();
			}
			Movement move = this.moves[moveIdx++];
			Board board = (Board) base.clone();

			board = board.putBlob(player, move.target);
			if (move.distance() == 2) {

				board = board.deleteBlob(player, source);
			}
			attack(player, board, move.target);
			return new Pair<Board, Movement>(board, move);
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	/**
	 * Tells if the given move is valid inside the given board 
	 *
	 * @param board the board 
	 * @param move the intended move 
	 */
	public boolean isValid(Board board, Movement move) {
		if (move == null || board == null) {

			return false;
		}
		Point source = move.source;
		Point target = move.target;

		if (source.equals(target) || move.distance() > 2) {
			return false;
		}
		if (board.getTileOwner(source) == null
				|| board.getTileOwner(target) != null) {

			return false;
		}
		return true;
	}

	/**
	 * Evaluate the score of a board 
	 *
	 * @param board the board 
	 * @param maximzer the player 
	 * @return the score 
	 * 
	 */

	public double evaluateScore(Board board, Player maximizer) {
		return board.gradientForPlayer(maximizer);
	}

	/**
	 * Makes a move inside the board.
	 * Depending on the movement it causes an attack, a duplication of blob or both. 
	 *
	 * @param board the board 
	 * @param player the player 
	 * @param move the movement 
	 * @return the same board
	 */
	
	public Board move(Board board, Player player, Movement move) {
		board.putBlob(player, move.target);

		if (move.distance() == 2) {
			board.deleteBlob(player, move.source);
		}

		attack(player, board, move.target);

		return board;
	}

	/**
	 * The starting board for the blobwars game 
	 *
	 * @param board the board 
	 * @return a new board
	 */
	public Board startingBoard() {

		Board board = new Board(this, human, computer);
		board.putBlob(human, new Point(0, 7));
		board.putBlob(human, new Point(0, 0));
		board.putBlob(computer, new Point(7, 0));
		board.putBlob(computer, new Point(7, 7));
		return board;
	}

	/**
	 * Attack, and all its consequences on the board
	 *
	 * @param player the player
	 * @param board the board
	 * @param source the source
	 */
	private void attack(Player player, Board board, Point source) {
		for (int dx = -1; dx < 2; dx++) {
			for (int dy = -1; dy < 2; dy++) {
				if (dx == 0 && dy == 0) {
					continue;
				}

				int x = (int) (source.getX() + dx);
				int y = (int) (source.getY() + dy);

				if (x < 0 || y < 0 || x >= board.getWidth()
						|| y >= board.getHeight()) {
					continue;
				}

				Point target = new Point(x, y);

				if (board.getTileOwner(target) != null) {
					board.setTile(target, player);
				}
			}
		}
	}

	public Iterable<Pair<Board, Movement>> boardsForMove(Board board,
			Point source) {
		return new BlobStrategyIterable(board, source);
	}
	
	/** Returns true if the given Point has avaiable moves inside the board
	 *
	 * @param player the player
	 * @param board the board
	 * @returns boolean 
	 */
	public boolean hasAvailableMove(Board board, Point source) {
		for (int dx = -2; dx < 3; dx++) {
			for (int dy = -2; dy < 3; dy++) {
				if (dx == 0 && dy == 0) {

					continue;
				}
				int x = (int) (source.getX() + dx);
				int y = (int) (source.getY() + dy);

				if (x < 0 || y < 0 || x >= board.getWidth()
						|| y >= board.getHeight()) {
					continue;
				}
				Point target = new Point(x, y);
				if (board.getTileOwner(target) == null) {

					return true;
				}
			}
		}
		return false;
	}
}
