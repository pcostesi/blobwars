package ai;


import structures.Pair;
import game.Board;
import game.Computer;
import game.Human;
import game.Movement;
import game.Player;
import game.Strategy;

public class LevelMinimax implements Minimax{
	private Board root;
	private int max_height;
	private Human human;
	private Computer computer;
	private Strategy strategy;
	
	
	public LevelMinimax(Strategy strategy, Board board, int height, Human h, Computer c){
		this.root = board;
		max_height = height;
		this.computer = c;
		this.human = h;
		this.strategy = strategy; 
	}
	
	private class Node{
		public double score;
		public Movement move;
		public Node(double score, Movement move){
			this.score = score;
			this.move = move;
		}
	}
	
	private Player getPlayerForLevel(int level){
		if (level % 2 == 0){
			return computer;
		}
		return human;
	}
	
	private Node minimax(Board board, int level){
		Movement bestMove = null;
		
		Player player = getPlayerForLevel(level);
		
		if (level == max_height){
			double score = strategy.evaluateScore(board, player);
			return new Node(score, null);
		}
		double score = player.initialScore(); 
		
		Node localScore;
		for (Pair<Board, Movement> pair : board.generateChildren(player)){
			Board localBoard = pair.getFirst();
			Movement localMove = pair.getSecond();
			localScore = minimax(localBoard, level + 1);
			if (player.betterScore(score, localScore.score)){
				score = localScore.score;
				bestMove = localMove;
			}
		}
		
		return new Node(score, bestMove);
	}
	
	public Movement getBestMove(){
		Node bestMove = minimax(root, 0);
		return bestMove.move;
	}
	
	
}
