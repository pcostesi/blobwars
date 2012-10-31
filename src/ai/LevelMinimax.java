package ai;


import game.Board;
import game.Movement;
import game.Player;
import game.Strategy;
import structures.Pair;

public class LevelMinimax implements Minimax{
	private Board root;
	private int max_height;
	private Player maximizer;
	private Player minimizer;
	private Strategy strategy;
	
	
	public LevelMinimax(Strategy strategy, Board board, int height, Player maximizer, Player minimizer){
		this.root = board;
		max_height = height;
		this.maximizer = maximizer;
		this.minimizer = minimizer;
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
		return level % 2 == 0 ? maximizer : minimizer;
	}
	
	private Node minimax(Board board, int level){
		Movement bestMove = null;
		boolean moved = false;
		
		Player player = getPlayerForLevel(level);
		
		if (level == max_height){
			double score = strategy.evaluateScore(board, player);
			return new Node(score, null);
		}
		
		double score = player.initialScore(); 
		
		Node localScore;
		for (Pair<Board, Movement> pair : board.generateChildren(player)){
			moved = true;
			Board localBoard = pair.getFirst();
			Movement localMove = pair.getSecond();
			localScore = minimax(localBoard, level + 1);
			if (player == maximizer){
				if (score < localScore.score){
					score = localScore.score;
					bestMove = localMove;
				}
			} else if (score > localScore.score){
				score = localScore.score;
				bestMove = localMove;
			}
		}
		if (!moved){
			score = strategy.evaluateScore(board, player);
		}
		
		return new Node(score, bestMove);
	}
	
	public Movement getBestMove(){
		Node bestMove = minimax(root, 0);
		System.out.println(bestMove.move);
		return bestMove.move;
	}

	@Override
	public void setBoard(Board board) {
		this.root = board;
		
	}
	
	
}
