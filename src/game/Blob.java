package game;

public class Blob implements Tile {
	private Player player;
	
	public Blob(Player player){
		this.player = player;
	}

	@Override
	public Player getPlayer() {
		return player;
	}
	
	public boolean isEmpty(){
		return false;
	}
}
