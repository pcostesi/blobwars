package structures;


import game.Board;
import game.Movement;
import game.Player;

import java.util.List;

public class MinMaxTree {
	private Node root;
	private int max_height;
	
	public MinMaxTree(Board board, int height){
		this.root = new Node(board, null);
		max_height = height;
	}
	
	private static class Node{
		Board board;
		Movement bestMove;
		
		List<Node> children;
		
		Node(Board board, Movement move){
			this.board = board;
			this.bestMove = move;
		}
	}
	
	private int minimax(Node node, int level){
		if (level == max_height){
			return node.board.getStrategy().evaluateScore(node.board);
		}
		
		Node nodeChild;
		
		
		int score = player.initialScore(); 
		
		int localScore;
		for (Pair<Board, Movement> pair : node.board.generateChildren(player)){
			nodeChild = new Node(pair.getFirst(), pair.getSecond());
			localScore = minimax(nodeChild, level - 1);
			node.children.add(nodeChild);
			
			if (player.betterScore(score, localScore)){
				score = localScore;
				node.bestMove = pair.getSecond();
			}
		}
		
		return score;
	}
	
	public Movement getBestMove(int levels){
		minimax(root, levels);
		return root.bestMove;
	}
	
	
}
