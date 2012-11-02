package frontend;

import ioGame.IOGameController;
import optparse.FlagOption;
import optparse.OptionParser;
import optparse.Options;
import optparse.ValuedOption;

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

		OptionParser optparse = new OptionParser();
		optparse
			.addOption(new FlagOption("prune"))
			.addOption(new FlagOption("visual"))
			.addOption(new ValuedOption("player"))
			.addOption(new ValuedOption("file"))
			.addOption(new ValuedOption("maxtime"))
			.addOption(new ValuedOption("depth"));
		
		Options opts = optparse.parse(args);
		

		if (opts.hasFlag("visual")) {

			new GameController(opts);
		} else if (opts.hasFlag("file")) {
			new IOGameController(opts.getValue("file"),
					opts.getValue("player"), opts);
		}
	}
}
