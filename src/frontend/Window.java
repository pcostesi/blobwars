package frontend;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Window extends JFrame {

	private GameController controller;
	private JPanel contentPane;
	private GamePanel gamePanel;

	public Window(GameController controller) {
		this.controller = controller;
		initialize();
	}

	/**
	 * Initializes the frame.
	 */
	
	public void initialize() {
		setTitle("Blob Wars");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		initializeContentPane();

		// TODO: fix magic numbers
		setGame(8, 8);
		setGameVisible();
		setVisible(true);
	}
	
	private void initializeContentPane() {
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	public void setGame(int boardHeight, int boardWidth) {
		if (gamePanel != null) {
			getContentPane().remove(gamePanel);
		}
		gamePanel = new GamePanel(controller, boardHeight, boardWidth);
	}

	public void setGameVisible() {
			getContentPane().add(gamePanel);
			setSize(gamePanel.getWidth(), gamePanel.getHeight());

	}

	public View getView() {
		return gamePanel;
	}

	public void showWin() {
		JOptionPane.showMessageDialog(this, "You have won!", "Win",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void showLose() {
		JOptionPane.showMessageDialog(this, "You have lost!", "Lose",
				JOptionPane.INFORMATION_MESSAGE);
	}

}
