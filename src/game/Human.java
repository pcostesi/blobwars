package game;

public class Human implements Player{

	@Override
	public boolean betterScore(int score, int localScore) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int initialScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public String toString(){
		return "O";
	}
}
