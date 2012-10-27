package frontend;

import gui.BoardPanel;
import gui.BoardPanelListener;
import gui.ImageUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements View {

	private static final int CELL_SIZE = 30;

	private GameController controller;
	private final int boardWidth;
	private final int boardHeight;
	private BoardPanel boardPanel;
	private JPanel statusPanel;
	private Image background;

	public GamePanel(GameController controller, int boardHeight, int boardWidth) {
		this.controller = controller;
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		initialize();
	}

	/**
	 * Initializes the panel components
	 */
	public void initialize() {
		setLayout(new BorderLayout());

		// Load background image
		try {
			background = ImageUtils.loadImage("resources/background.png");
		} catch (IOException e) {
			JOptionPane
					.showMessageDialog(
							this,
							"Unable to load all resources. You may continue to play the game, but some images may not show.",
							"Resource error", JOptionPane.WARNING_MESSAGE);
		}

		initializeBoard();
		initializeStatusPanel();
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setSize(boardPanel.getWidth() + 20, boardPanel.getHeight() + 68);
	}

	/**
	 * Initializes the panel board
	 */
	private void initializeBoard() {
		boardPanel = new BoardPanel(boardHeight, boardWidth, CELL_SIZE);
		boardPanel.setBackground(Color.WHITE);
		boardPanel.setGridColor(Color.GRAY);
		boardPanel.setListener(new BoardPanelListener() {

			public void cellDragged(int sourceRow, int sourceColumn,
					int targetRow, int targetColumn) {
				
				System.out.println("drag");
				controller.move(sourceRow, sourceColumn, targetRow,
						targetColumn);
			}

			@Override
			public void cellClicked(int row, int column) {
				System.out.println("click");
			}

		});
		add(boardPanel);
	}

	private void initializeStatusPanel() {
		statusPanel = new JPanel();
		statusPanel.setLayout(new BorderLayout());
		statusPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		statusPanel.setOpaque(false);
		add(statusPanel, BorderLayout.SOUTH);
	}

	public void updateTile(int row, int column, char player) {
		Image img = null;
		try {
			img = ImageUtils.loadImage("resources/blob.png");
		} catch (IOException e) {
		}
		System.out.println("imprimir " + row + ", "+ column);
		
		boardPanel.clearImage(row, column);
			boardPanel.appendImage(row, column, img);
		boardPanel.repaint();
	}
	
public void clearTile(int row, int column) {
		boardPanel.clearImage(row, column);
		boardPanel.repaint();
}


	@Override
	public void paintComponent(Graphics g) {
		if (background != null) {			
			g.drawImage(background, 0, 0, null);
		}
		paintComponents(g);
	}


}
