package game;

public class Computer implements Player {

	@Override
	public boolean betterScore(int score, int localScore) {
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
