package structures;
import java.lang.Integer;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import game.Board;
import game.Movement;
import game.Player;
import game.Strategy;

public class MinMaxTree {
	private Node root;
	private Strategy strategy;
	
	public MinMaxTree(Strategy strategy, Board board){
		this.root = new Node(board, null);
		this.strategy = strategy;
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
		if (level == 0){
			return strategy.evaluateScore(node.board);
		}
		
		Node nodeChild;
		
		Player player = strategy.getPlayer(level);
		
		int score = player.initialScore(); 
		
		int localScore;
		for (Pair<Board, Movement> pair : node.board.generateChildren(player)){
			nodeChild = new Node(pair.getFirst(), pair.getSecond());
			localScore = minimax(nodeChild, level - 1);
			node.children.add(nodeChild);
			
//			if ((level % 2 == 0 && localScore < score) || (level % 2 == 1 && localScore > score)){
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
