package ai;

import game.Board;
import game.Movement;
import game.Player;
import game.Strategy;
import structures.Pair;

public class LMinimax implements Minimax {

	private Board board;
	private Player maximizer;
	private Player minimizer;
	private Strategy strategy;
	private int levels = 1;
	private boolean poison = false;

	private class Node{
		public double score;
		public Movement move;
		public Node(double score, Movement move){
			this.score = score;
			this.move = move;
		}
	}
	
	public LMinimax(int levels, Strategy strategy, Board board, Player maximizer, Player minimizer){
		this.board = board;
		this.maximizer = maximizer;
		this.minimizer = minimizer;
		this.levels = levels;
		this.strategy = strategy;
	}
	
	private Player cycle(Player p){
		if (p == maximizer)
			return minimizer;
		return maximizer;
	}
	
	private Node min(int level, Board board, Player player) throws InterruptedException{
		if (poison){
			throw new InterruptedException();
		}
		Movement bestMove = null;
		boolean moved = false;
		double score = player == maximizer? Integer.MIN_VALUE : Integer.MAX_VALUE;
		if (level == 0){
			return new Node(strategy.evaluateScore(board, maximizer), null);
		}
		
		Node localScore;
		for (Pair<Board, Movement> pair : board.generateChildren(player)){
			moved = true;
			Board localBoard = pair.getFirst();
			Movement localMove = pair.getSecond();
			localScore = max(level - 1, localBoard, cycle(player));
			if (score > localScore.score){
				score = localScore.score;
				bestMove = localMove;
			}
		}
		if (!moved){
			score = strategy.evaluateScore(board, maximizer);
		}
		
		return new Node(score , bestMove);
	}
	
	
	
	
	private Node max(int level, Board board, Player player) throws InterruptedException{
		if (poison){
			throw new InterruptedException();
		}
		Movement bestMove = null;
		boolean moved = false;

		double score = player == maximizer? Integer.MIN_VALUE : Integer.MAX_VALUE;
		
		if (level == 0){
			return new Node(strategy.evaluateScore(board, maximizer), null);
		}
		
		Node localScore;
		for (Pair<Board, Movement> pair : board.generateChildren(player)){
			moved = true;
			Board localBoard = pair.getFirst();
			Movement localMove = pair.getSecond();
			localScore = min(level - 1, localBoard, cycle(player));
			
			if (score < localScore.score){
				score = localScore.score;
				bestMove = localMove;
			}
			
			
		}
		if (!moved){
			score = strategy.evaluateScore(board, maximizer);
		}
		
		return new Node(score , bestMove);
	}
	
	public synchronized void poison(){
		this.poison = true;
	}
	
	@Override
	public Movement getBestMove() {
		Node root;
		poison = false;
		try {
			root = max(levels, board, maximizer);
			return root.move;
		} catch (InterruptedException e) {
		}
		return null;
	}



	@Override
	public void setBoard(Board board) {
		this.board = board;
	}

}
