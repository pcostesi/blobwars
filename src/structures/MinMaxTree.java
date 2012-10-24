package structures;


import game.Board;
import game.Movement;
import game.Player;
import game.Strategy;

import java.util.LinkedList;
import java.util.List;

public class MinMaxTree {
	private Node root;
	private int max_height;
	private Player[] players;
	private Strategy strategy;
	
	public MinMaxTree(Board board, int height, Player... players){
		this.root = new Node(board, null);
		max_height = height;
		this.players = players;
		this.strategy = board.getStrategy();
	}
	
	private static class Node{
		Board board;
		Movement bestMove;
		
		List<Node> children = new LinkedList<Node>();
		
		Node(Board board, Movement move){
			this.board = board;
			this.bestMove = move;
		}
	}
	
	private Player getPlayerForLevel(int level){
		return players[level % players.length];
	}
	
	private int minimax(Node node, int level){
		Node nodeChild;
		
		Player player = getPlayerForLevel(level);
		
		if (level == max_height){
			return strategy.evaluateScore(node.board, player);
		}
		int score = player.initialScore(); 
		
		int localScore;
		for (Pair<Board, Movement> pair : node.board.generateChildren(player)){
			nodeChild = new Node(pair.getFirst(), pair.getSecond());
			localScore = minimax(nodeChild, level + 1);
			node.children.add(nodeChild);
			if (player.betterScore(score, localScore)){
				score = localScore;
				node.bestMove = pair.getSecond();
			}
		}
		return score;
	}
	
	public Movement getBestMove(){
		minimax(root, 0);
		return root.bestMove;
	}
	
	
}
