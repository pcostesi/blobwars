package game;

/**
 * The Human player
 */
public class Human extends Player{

	@Override
	public boolean betterScore(double score, double localScore) {
		return localScore < score;
	}

	@Override
	public int initialScore() {
		return Integer.MAX_VALUE;
	}

	
	public String toString(){
		return "h";
	}

	@Override
	public char toTile() {
		// TODO Auto-generated method stub
		return 'h';
	}
}
