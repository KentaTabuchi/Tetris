package tetris.main;
import javax.swing.JFrame;

/**
 * 
 */

/**
 * @author misskabu
 *
 */
@SuppressWarnings("serial")
public class GameWindow extends JFrame {

	/**
	 * @throws HeadlessException
	 */
	public GameWindow()  {
		GamePanel panel = new GamePanel();
		this.getContentPane().add(panel);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}



}
