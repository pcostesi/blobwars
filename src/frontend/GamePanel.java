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
	private Image humanBlob;
	private Image computerBlob;

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
		loadPlayerAssets();
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
	
	private void loadPlayerAssets(){
		try {
			this.humanBlob = ImageUtils.loadImage("resources/blob_human.png");
			this.computerBlob = ImageUtils.loadImage("resources/blob_computer.png");
		} catch (IOException e) {
		}
	}

	public void updateTile(int row, int column, char player) {
		boardPanel.clearImage(row, column);
		
		if (player == 'h'){ 
			boardPanel.appendImage(row, column, humanBlob);
		} else if (player == 'c'){
			boardPanel.appendImage(row, column, computerBlob);
		}
		
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
