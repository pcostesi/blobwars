package ai;

import game.Board;
import game.Computer;
import game.Human;
import game.Movement;
import game.Strategy;

public class TBIDABPMinimax implements Minimax {

	/*
	 * Time Bound, Iterative Deepening, Alpha-Beta Prunning MiniMax 
	 * We've created a monster.
	 * */
	
	private Movement solution;
	private Strategy strategy;
	private Board board;
	private Human human;
	private Computer computer;
	private boolean hasTime = true;
	private int millis;

	private Thread worker;
	private Thread clock;
	private ABPMMWorker task;
	
	public TBIDABPMinimax(int millis, Strategy strategy, Board board,
			Human human, Computer computer) {
		this.millis = millis;
		this.strategy = strategy;
		this.board = board;
		this.human = human;
		this.computer = computer;
	}

	private synchronized void postSolution(Movement move){
		this.solution = move;
		this.notify();
	}
	
	private synchronized void stopTime(){
		this.hasTime = false;
		task.poison();
		worker.interrupt();
		this.notify();
	}
	
	private class ABPMMWorker extends ABPMinimax implements Runnable{

		public ABPMMWorker(int levels, Strategy strategy, Board board,
				Human human, Computer computer) {
			super(levels, strategy, board, human, computer);
		}

		@Override
		public void run() {
			postSolution(getBestMove());			
		}
		
	}
	
	private class Clock extends Thread implements Runnable{

		private int millis;
		
		public Clock(int millis){
			this.millis = millis;
		}
		@Override
		public void run() {
			try {
				Clock.sleep(millis);
				stopTime();
			} catch (InterruptedException e) {
			}
		}
		
	}
	
	@Override
	public synchronized Movement getBestMove() {
		/*
		 * We spawn two threads:
		 * - Ye Olde ABPMinimax (with a custom height progression).
		 * - An alarm thread.
		 * 
		 * When the alarm thread rings it signals this thread, which promptly
		 * kills the ABPMinimax worker (which is a service thread, unjoined).
		 * 
		 * Every time the worker returns it sets a solution (Movement) in
		 * this thread. This happens inside a while(flag) loop that increments
		 * the height for each run.
		 * */
		int level = 1;
		
		clock = new Clock(millis);
		clock.start();

		try {
			while (hasTime){
				task = new ABPMMWorker(level, strategy, board, human, computer);
				worker = new Thread(task);
				worker.setDaemon(true);
				worker.start();
				this.wait();
				level++;
			}
			clock.join();
		} catch (InterruptedException e) {
		}
		return solution;
	}

}
