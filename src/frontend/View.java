package frontend;


/**
 * Interface that defines the actions that can be performed in a <tt>View</tt>.
 */
public interface View {

	/**
	 * Updates the cell located at the specified row and column with the drawing
	 * of the parameter.
	 * 
	 * @param row
	 * @param column
	 * @param cell
	 */
	public abstract void updateTile(int row, int column, char tile);

	/**
	 * Clears the cell located at the specified row and column.
	 * 
	 * @param row
	 * @param column
	 */
	public abstract void clearTile(int row, int column);

	public abstract void refresh();

}