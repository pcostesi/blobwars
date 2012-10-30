package game;

public interface Player {

	abstract int initialScore();


	abstract char toTile();


	abstract boolean betterScore(double score, double localScore);

}
//int score = (level % 2 == 0) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
