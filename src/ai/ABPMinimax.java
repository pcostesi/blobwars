package ai;

import game.Board;
import game.Movement;
import game.Player;
import game.Strategy;
import structures.Pair;

public class ABPMinimax implements Minimax {

	private Board board;
	private Player maximizer;
	private Player minimizer;
	private Strategy strategy;
	private int levels = 1;
	private boolean poison = false;

	private class Node {
		public double score;
		public Movement move;

		public Node(double score, Movement move) {

			this.score = score;
			this.move = move;
		}
	}

	public ABPMinimax(int levels, Strategy strategy, Board board,
			Player maximizer, Player minimizer) {
		this.board = board;
		this.maximizer = maximizer;
		this.minimizer = minimizer;
		this.levels = levels;
		this.strategy = strategy;
	}

	private Player cycle(Player p) {
		if (p == maximizer)
			return minimizer;
		return maximizer;
	}

	private Node min(int level, Board board, double alpha, double beta,
			Player player) throws InterruptedException {
		if (poison) {
			throw new InterruptedException();
		}
		Movement bestMove = null;
		boolean moved = false;

		if (level == 0) {
			return new Node(strategy.evaluateScore(board, maximizer), null);
		}

		Node localScore;
		for (Pair<Board, Movement> pair : board.generateChildren(player)) {
			moved = true;
			Board localBoard = pair.getFirst();
			Movement localMove = pair.getSecond();
			localScore = max(level - 1, localBoard, alpha, beta, cycle(player));
			if (beta > localScore.score) {
				beta = localScore.score;
				bestMove = localMove;
			}
			if (localScore.score <= alpha) {
				break;
			}

		}
		if (!moved) {
			beta = strategy.evaluateScore(board, maximizer);
		}

		return new Node(beta, bestMove);
	}

	private Node max(int level, Board board, double alpha, double beta,
			Player player) throws InterruptedException {
		if (poison) {
			throw new InterruptedException();
		}
		Movement bestMove = null;
		boolean moved = false;

		if (level == 0) {
			return new Node(strategy.evaluateScore(board, maximizer), null);
		}

		Node localScore;
		for (Pair<Board, Movement> pair : board.generateChildren(player)) {
			moved = true;
			Board localBoard = pair.getFirst();
			Movement localMove = pair.getSecond();
			localScore = min(level - 1, localBoard, alpha, beta, cycle(player));

			if (alpha < localScore.score) {
				alpha = localScore.score;
				bestMove = localMove;
			}

			if (localScore.score >= beta) {
				break;
			}

		}
		if (!moved) {
			alpha = strategy.evaluateScore(board, maximizer);
		}

		return new Node(alpha, bestMove);
	}

	public synchronized void poison() {
		this.poison = true;
	}

	public Movement getBestMove() {
		Node root;
		poison = false;
		try {
			root = max(levels, board, Integer.MIN_VALUE, Integer.MAX_VALUE,
					maximizer);

			return root.move;
		} catch (InterruptedException e) {
		}
		return null;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

}
