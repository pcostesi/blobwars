package frontend;

import ioGame.IOGameController;

/**
 * Blob Wars application entry point.
 */
public class BlobWars {

	/**
	 * Loads the controller that will handle the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String mode = args[0].substring(1);
		
		if (mode.equals("visual")){
			new GameController();
		} else if (mode.equals("file")){
			new IOGameController(args[1], args[3]);
		}
	}
}
