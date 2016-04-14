package gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

/**
 * Header class for the program.
 * 
 * @author Caleb Choi, Connur Murphy, Matthew Sun
 */
public class EventPlanner {

	public enum Panel {
		HOME, SETTINGS, STUDENT, DISPLAY_STUDENT, TABLE_DISPLAY
	};

	// Window width and height
	public static final int WINDOW_HEIGHT = 720;
	public static final int WINDOW_WIDTH = 1280;

	private static JPanel homePanel, settingsPanel, studentPanel, studentDisplay, tableDisplay;

	// Program frame
	public static final JFrame FRAME = new JFrame("RHHS Event Planner");

	public static void main(String[] args) throws Exception {

		// System ook and feel
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(EventPlanner.class.getResource("/font/font.ttf").toURI())));
		} catch (IOException|FontFormatException e) {}

		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(EventPlanner.class.getResource("/font/fontbold.ttf").toURI())));
		} catch (IOException|FontFormatException e) {}

		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

		//Create the panels
		homePanel = new HomePanel();
		settingsPanel = new SettingsPanel();
		studentPanel = new StudentPanel();
		studentDisplay = new DisplayStudentPanel();
		tableDisplay  = new TableDisplayPanel();

		// Adds home panel
		FRAME.getContentPane().add(homePanel);

		// Miscellaneous settings
		FRAME.setLocation(50, 0);
		FRAME.setSize(WINDOW_WIDTH, WINDOW_HEIGHT + 27);
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
			((StudentPanel) studentPanel).refresh();
			FRAME.add(studentPanel);
		}
		else if (panel == Panel.DISPLAY_STUDENT)
		{
			((DisplayStudentPanel)studentDisplay).refresh();
			FRAME.add(studentDisplay);
		}
		else if (panel == Panel.TABLE_DISPLAY)
		{
			FRAME.add(tableDisplay);
		}
		FRAME.revalidate();
		FRAME.repaint();
	}

	/**
	 * Used to change the currently selected panel to the student profile palen
	 * @param panel the panel of the profile to show
     */
	public static void showStudentProfile(StudentProfile panel)
	{
		FRAME.getContentPane().removeAll();
		FRAME.add(panel);
		FRAME.revalidate();
		FRAME.repaint();
	}

}
