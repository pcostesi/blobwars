package game;

/**
 * The Computer player.
 */
public class Computer extends Player {

	@Override
	public boolean betterScore(double score, double localScore) {
		return localScore > score;
	}

	@Override
	public int initialScore() {
		return Integer.MIN_VALUE;
	}
	
	public char toTile(){
		return 'c';
	}
	
	public String toString(){
		return "c";
	}

}
