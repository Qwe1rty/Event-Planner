package gui;

import io.Loader;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * Header class for the program.
 *
 * @author Caleb Choi, Connur Murphy, Matthew Sun
 */
public class EventPlanner implements WindowListener {

	public enum Panel {
		HOME, SETTINGS, STUDENT, DISPLAY_STUDENT, TABLE_DISPLAY
	}

	// Window width and height
	public static final int WINDOW_HEIGHT = 720;
	public static final int WINDOW_WIDTH = 1280;

	private static JPanel homePanel, settingsPanel, studentPanel,
	studentDisplay, tableDisplay;

	// Program frame
	public static final JFrame FRAME = new JFrame("RHHS Event Planner");

	public static void main(String args[]) {
		new EventPlanner().run();
	}

	/**
	 * Sets up the main window for the program
	 */
	public void run() {


		/*       // Table
        Settings.setNumTables(10);
        Settings.setTableSize(5);
		 */
		// System look and feel
		/*
		 * try {
		 * UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		 * } catch (ClassNotFoundException | InstantiationException |
		 * IllegalAccessException | UnsupportedLookAndFeelException e1) { //
		 * catch block e1.printStackTrace(); }
		 */

		try {
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,
					EventPlanner.class.getResourceAsStream("/font/font.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,
					EventPlanner.class.getResourceAsStream("/font/fontbold.ttf")));
		} catch (IOException | FontFormatException e) {
		}

		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();

		// Create the panels
		homePanel = new HomePanel();

		// Adds home panel
		FRAME.getContentPane().add(homePanel);

		// Miscellaneous settings
		FRAME.addWindowListener(this);
		FRAME.setLocation(50, 0);
		FRAME.setSize(WINDOW_WIDTH, WINDOW_HEIGHT + 27);
		FRAME.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		FRAME.setMinimumSize(new Dimension(1240, 680));
		FRAME.setVisible(true);

//		try {
//			boolean success;
//			success = Loader.parseFile();
//
//			// Give a notification that the file was loaded if successful
//			// If success is false, it means the user just clicked the X or canceled
//			if(success) {
//				try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception ex) {}
//				JOptionPane.showMessageDialog(EventPlanner.FRAME, "File has been successfully loaded", "File loaded", JOptionPane.INFORMATION_MESSAGE);
//				try {UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());} catch (Exception ex) {}
//			}
//		} catch (FileNotFoundException fnf) { // If file was not found
//			try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception ex) {}
//			JOptionPane.showMessageDialog(EventPlanner.FRAME, "The file was not found\nPlease try again",
//					"File Not Found", JOptionPane.ERROR_MESSAGE);
//			try {UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());} catch (Exception ex) {}
//			
//		} catch (Exception exc) { // If there was a parsing error
//			try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception ex) {}
//			JOptionPane.showMessageDialog(EventPlanner.FRAME, "File is corrupted - file could not be loaded", "Corrupted file", JOptionPane.ERROR_MESSAGE);
//			try {UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());} catch (Exception ex) {}
//		}

		studentPanel = new StudentPanel();
		studentDisplay = new DisplayStudentPanel();
		tableDisplay = new TableDisplayPanel();
		settingsPanel = new SettingsPanel();
	}

	/**
	 * Used to change the panel currently displayed to the user.
	 *
	 * @param panel the type of panel to change to
	 */
	public static void setPanel(Panel panel) {
		FRAME.getContentPane().removeAll();
		if (panel == Panel.HOME) {
			FRAME.add(homePanel);
		} else if (panel == Panel.SETTINGS) {
			FRAME.add(settingsPanel);
		} else if (panel == Panel.STUDENT) {
			((StudentPanel) studentPanel).refresh();
			FRAME.add(studentPanel);
		} else if (panel == Panel.DISPLAY_STUDENT) {
			((DisplayStudentPanel) studentDisplay).refresh(true);
			FRAME.add(studentDisplay);
		} else if (panel == Panel.TABLE_DISPLAY) {
			((TableDisplayPanel) tableDisplay).refresh(true);
			FRAME.add(tableDisplay);
		}
		FRAME.revalidate();
		FRAME.repaint();
	}

	/**
	 * Only used for the display student panel to ensure searched results are
	 * not refreshed when returning to the student display
	 *
	 * @param panel                        the panel to change to (should be a DisplayStudentPanel)
	 * @param refreshWithMasterStudentList if the displated students should be refreshed from the master
	 *                                     student list
	 */
	public static void setPanel(Panel panel,
			boolean refreshWithMasterStudentList) {
		FRAME.getContentPane().removeAll();
		if (panel == Panel.DISPLAY_STUDENT) {
			((DisplayStudentPanel) studentDisplay)
			.refresh(refreshWithMasterStudentList);
			FRAME.add(studentDisplay);
		}
		FRAME.revalidate();
		FRAME.repaint();
	}

	/**
	 * Used to change the currently selected panel to the student profile palen
	 *
	 * @param panel the panel of the profile to show
	 */
	public static void showStudentProfile(StudentProfile panel) {
		FRAME.getContentPane().removeAll();
		FRAME.add(panel);
		FRAME.revalidate();
		FRAME.repaint();
	}

	/**
	 * Gives the user a chance to save before the program shuts down
	 */
	public void windowClosing(WindowEvent e) {
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception ex) {}
		int result = JOptionPane.showConfirmDialog(FRAME,
				"Are you sure you want to exit?", "Exit",
				JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			FRAME.dispose();
			System.exit(0);
		}
		try {UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());} catch (Exception ex) {}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}
}
