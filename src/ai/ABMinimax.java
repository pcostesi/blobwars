package ai;

import game.Board;
import game.Computer;
import game.Human;
import game.Movement;

public class ABMinimax implements Minimax {

	private Board board;
	private Computer computer;
	private Human human;

	private class Node{
		public double score;
		public Movement move;
		public double alpha;
		public double beta;
		public Node(double score, Movement move){
			this.score = score;
			this.move = move;
		}
	}
	
	public ABMinimax(Board board, Human human, Computer computer){
		this.board = board;
		this.human = human;
		this.computer = computer;
	}
	
	
	private Node min(int level){
		return null;
	}
	
	private Node max(int level){
		return null;
	}
	
	@Override
	public Movement getBestMove() {
		// TODO Auto-generated method stub
		Node root = min(0);
		return null;
	}

}
