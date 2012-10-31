package ai;

import game.Board;
import game.Computer;
import game.Human;
import game.Movement;
import game.Player;
import game.Strategy;
import structures.Pair;

public class ABPMinimax implements Minimax {

	private Board board;
	private Computer computer;
	private Human human;
	private Strategy strategy;
	private int levels = 1;

	private class Node{
		public double score;
		public Movement move;
		public Node(double score, Movement move){
			this.score = score;
			this.move = move;
		}
	}
	
	public ABPMinimax(int levels, Strategy strategy, Board board, Human human, Computer computer){
		this.board = board;
		this.human = human;
		this.computer = computer;
		this.levels = levels;
		this.strategy = strategy;
	}
	
	
	
	private Node min(int level, Board board, double alpha, double beta){
		Movement bestMove = null;
		boolean moved = false;
		
		if (level == 0){
			return new Node(strategy.evaluateScore(board, human), null);
		}
		
		Node localScore;
		for (Pair<Board, Movement> pair : board.generateChildren(human)){
			moved = true;
			Board localBoard = pair.getFirst();
			Movement localMove = pair.getSecond();
			localScore = max(level - 1, localBoard, alpha, beta);
			
			if (human.betterScore(beta, localScore.score)){
				beta = localScore.score;
				bestMove = localMove;
			}
			if (localScore.score <= alpha){
				break;
			}
			
		}
		if (!moved){
			beta = strategy.evaluateScore(board, human);
		}
		
		return new Node(beta , bestMove);
	}
	
	
	
	
	private Node max(int level, Board board, double alpha, double beta){
		Movement bestMove = null;
		boolean moved = false;
		
		if (level == 0){
			return new Node(strategy.evaluateScore(board, computer), null);
		}
		
		Node localScore;
		for (Pair<Board, Movement> pair : board.generateChildren(computer)){
			moved = true;
			Board localBoard = pair.getFirst();
			Movement localMove = pair.getSecond();
			localScore = min(level - 1, localBoard, alpha, beta);
			
			if (computer.betterScore(alpha, localScore.score)){
				alpha = localScore.score;
				bestMove = localMove;
			}
			
			if (localScore.score >= beta){
				break;
			}
			
		}
		if (!moved){
			alpha = strategy.evaluateScore(board, computer);
		}
		
		return new Node(alpha , bestMove);
	}
	
	
	@Override
	public Movement getBestMove() {
		Node root = max(levels, board, computer.initialScore(), human.initialScore());
		return root.move;
	}

}
