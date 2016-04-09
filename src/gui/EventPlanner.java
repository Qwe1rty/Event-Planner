package gui;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class EventPlanner {

	// Window width and height
	public static final int WINDOW_HEIGHT = 720;
	public static final int WINDOW_WIDTH = 1280;

	// Program frame
	public static final JFrame FRAME = new JFrame("RHHS Event Planner");

	public static void main(String[] args) throws Exception {

		// Creation of frame
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		// Adds home panel
		FRAME.getContentPane().add(new HomePanel());

		// Miscellaneous settings
		FRAME.setLocation(50, 0);
		FRAME.setSize(WINDOW_WIDTH, WINDOW_HEIGHT + 27);
		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FRAME.setResizable(false);
		FRAME.setVisible(true);
	}

}
