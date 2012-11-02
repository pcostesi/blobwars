package ioGame;

/**
 * Options.
 * An abstraction of the command line arguments.
 */
public class Options {
	
	private String[] args;
	
	private String mode;
	
	private String fileName;
	
	private String player;
	
	/**
	 * Instantiates a new options.
	 *
	 * @param args an array of arguments
	 */
	public Options(String[] args){
		if (args.length > 0 ){
			this.mode = args[0].substring(1);
		}
		this.args = args;
	}
	
	
	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName(){
		return fileName;
	}
	
	/**
	 * Gets the player number.
	 *
	 * @return the player number
	 */
	public String getPlayerNumber(){
		return player;
	}
	
	
	/**
	 * Validates if the given arguments are ok
	 *
	 * @return true, if successful
	 */
	public boolean valid(){
		if (this.args.length < 3){
			return false;
		}
		
		if (!consoleMode() && !visualMode()){
			return false;
		}
		
		if (!byTime() && !byDepth()){
			return false;
		}
		
		if (consoleMode()){
			if (this.args.length < 6){
				return false;
			}
			
			this.fileName = args[1];
			if (args[3].equals("1")) {
				this.player = "1";
			} else if (args[3].equals("2")){
				this.player = "2";
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Minimax with Prune option
	 *
	 * @return true, if successful
	 */
	public boolean prune(){
		return args[args.length - 1].equals("-prune");
	}
	
	/**
	 * Minimax by time option
	 *
	 * @return true, if successful
	 */
	public boolean byTime(){
		if (prune()){
			return args[args.length - 3].equals("-maxTime");
		}else{
			return args[args.length - 2].equals("-maxTime");
		}
	}
	
	/**
	 * Minimax by depth option.
	 *
	 * @return true, if successful
	 */
	public boolean byDepth(){
		if (prune()){
			return args[args.length - 3].equals("-depth");
		}else{
			return args[args.length - 2].equals("-depth");
		}	
	}
	
	/**
	 * Gets the value, be it the time in milliseconds or the depth level
	 *
	 * @return the value
	 */
	public int getValue(){
		if (prune()){
			return Integer.valueOf(args[args.length - 2]);
		}else{
			return Integer.valueOf(args[args.length - 1]);
		}	
	}
	
	/**
	 * Visual mode.
	 *
	 * @return true, if successful
	 */
	public boolean visualMode(){
		return mode != null && mode.equals("visual");
	}
	
	/**
	 * Console mode.
	 *
	 * @return true, if successful
	 */
	public boolean consoleMode(){
		return mode != null && mode.equals("file");
	}

}
