package game;

public class Computer extends Player {

	@Override
	public boolean betterScore(double score, double localScore) {
		// TODO Auto-generated method stub
		return localScore > score;
	}

	@Override
	public int initialScore() {
		// TODO Auto-generated method stub
		return Integer.MIN_VALUE;
	}
	
	public char toTile(){
		return 'c';
	}
	
	public String toString(){
		return "c";
	}

}
