package game;

public abstract class Player {

	abstract boolean betterScore(int score, int localScore);
//		(level % 2 == 0 && localScore < score) || (level % 2 == 1 && localScore > score)


	abstract int initialScore();

}
//int score = (level % 2 == 0) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
