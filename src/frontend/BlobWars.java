package frontend;

import ioGame.IOGameController;
import ioGame.Options;

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
		Options options = new Options(args);
		
		if (!options.valid()){
			System.out.println("Wrong arguments");
			System.exit(0);
		}
		
		if (options.visualMode()){
			new GameController(options);
		} else if (options.consoleMode()){
			new IOGameController(options.getFileName(), options.getPlayerNumber(), options);
		}
	}
}
