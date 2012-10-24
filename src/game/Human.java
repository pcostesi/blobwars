package game;

public class Human implements Player{

	@Override
	public boolean betterScore(int score, int localScore) {
		// TODO Auto-generated method stub
		return localScore < score;
	}

	@Override
	public int initialScore() {
		// TODO Auto-generated method stub
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
