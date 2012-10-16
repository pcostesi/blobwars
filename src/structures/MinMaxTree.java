package structures;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import game.Board;

public class MinMaxTree {
	private Node root;
	
	public MinMaxTree(Board board){
		this.root = new Node(board, null);
	}
	
	private static class Node{
		int value;
		Board board;
		Pair<Point, Point> move;
		
		// TODO: Implement it with an ordered structure
		List<Node> children;
		
		Node(Board board, Pair<Point, Point> move){
			this.board = board;
			this.move = move;
		}
	}
	
	public void populate(){
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);
		
		Boolean max = true;
		int score;
		
		while (!condiciondecorte){
			Node node = queue.poll();
			
			for (Pair<Board, Pair<Point, Point>> pair: node.board.generateChildren()){
				
				score = System.getScore(pair.getFirst());
				if (score....){
					node.children.add(new Node(pair.getFirst(), pair.getSecond()));
					queue.add(node);
				}
			}
			
			max = !max;
		}
		
	}
	
	
}
