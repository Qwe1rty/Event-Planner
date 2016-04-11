package gui;

import javax.swing.*;

public class EventPlanner {

	public enum Panel
	{
			HOME, SETTINGS, STUDENT
	};

	// Window width and height
	public static final int WINDOW_HEIGHT = 720;
	public static final int WINDOW_WIDTH = 1280;

	private static JPanel homePanel, settingsPanel, studentPanel;

	// Program frame
	public static final JFrame FRAME = new JFrame("RHHS Event Planner");

	public static void main(String[] args) throws Exception {

		// Creation of frame
		//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		//Create the panels
		homePanel = new HomePanel();
		settingsPanel = new SettingsPanel();
		studentPanel = new StudentPanel();

		// Adds home panel
		FRAME.getContentPane().add(homePanel);

		// Miscellaneous settings
		FRAME.setSize(WINDOW_WIDTH, WINDOW_HEIGHT + 27);
		FRAME.setLocationRelativeTo(null); //Centre the window
		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FRAME.setResizable(false);
		FRAME.setVisible(true);
	}

	/**
	 * Used to change the panel currently displayed to the user
	 * @param panel the type of panel to change to
     */
	public static void setPanel(Panel panel)
	{
		FRAME.getContentPane().removeAll();
		if(panel == Panel.HOME)
		{
			FRAME.add(homePanel);
		}
		else if(panel == Panel.SETTINGS)
		{
			FRAME.add(settingsPanel);
		}
		else if(panel == Panel.STUDENT)
		{
			FRAME.add(studentPanel);
		}
		FRAME.revalidate();
		FRAME.repaint();
	}

}
