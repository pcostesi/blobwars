package game;

public class EmptyTile implements Tile {

	@Override
	public Player getPlayer() {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}
}
