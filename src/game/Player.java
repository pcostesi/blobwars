package game;

public abstract class Player {

	public abstract int initialScore();

	public abstract char toTile();

	public abstract boolean betterScore(double score, double localScore);
	
	public int hashCode(){
		return toString().hashCode();
	}

}
