package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The home panel is the first screen the user sees.  It allows the user to access each part of the program or exit if they so choose
 * @author Connor Murphy, Matthew Sun, Caleb Choi
 * @version 1.2
 */
public class HomePanel extends JPanel {

	private Image background;
	private Image students, tables, settings, exit;

	/**
	 * Starts the frame, panel, and buttons
	 */
	public HomePanel()
	{

		// Create the BG panel following a GridBagLayout
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		// Put some space between the buttons
		c.insets = new Insets(10, 10, 10, 10);

		// Get the images
		try {
			students = ImageIO.read(getClass().getResource("/images/students.png"));
			tables =  ImageIO.read(getClass().getResource("/images/tables.png"));
			settings =  ImageIO.read(getClass().getResource("/images/settings.png"));
			exit =  ImageIO.read(getClass().getResource("/images/exit.png"));

			background = ImageIO.read(getClass().getResource("/images/bg.png"));
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}

		// Add the images to their buttons
		JButton studentButton = new JButton (new ImageIcon(students));
		JButton tableButton = new JButton (new ImageIcon(tables));
		JButton settingsButton = new JButton (new ImageIcon(settings));
		JButton exitButton = new JButton (new ImageIcon(exit));

		// Set the size of button to match the image size
		studentButton.setPreferredSize(new Dimension (295, 273));
		tableButton.setPreferredSize(new Dimension (295, 273));
		settingsButton.setPreferredSize(new Dimension (294, 278));
		exitButton.setPreferredSize(new Dimension (291, 275));

		// Add ActionListeners
		studentButton.addActionListener(new StudentButtonListener());
		tableButton.addActionListener(new TableButtonListener());
		settingsButton.addActionListener(new SettingsButtonListener());
		exitButton.addActionListener(new ExitButtonListener());

		// Add the buttons to the panel, each at their own location
		c.gridx = 0;
		c.gridy = 0;
		add(studentButton, c);

		c.gridx ++;
		add(tableButton, c);

		c.gridx = 0;
		c.gridy = 1;
		add(settingsButton, c);

		c.gridx ++;
		c.gridy = 1;
		add(exitButton, c);
	}	

	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
	}

	private class StudentButtonListener implements ActionListener
	{
		/**
		 * When student button is pressed
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			EventPlanner.setPanel(EventPlanner.Panel.DISPLAY_STUDENT);
		}
	}

	private class TableButtonListener implements ActionListener
	{
		/**
		 * When table button is pressed
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			EventPlanner.setPanel(EventPlanner.Panel.TABLE_DISPLAY);
		}
	}
	private class SettingsButtonListener implements ActionListener
	{
		/**
		 * When settings button is pressed
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			EventPlanner.setPanel(EventPlanner.Panel.SETTINGS);
		}
	}
	private class ExitButtonListener implements ActionListener
	{
		/**
		 * When exit button is pressed, exit application
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			EventPlanner.FRAME.dispatchEvent(new WindowEvent(EventPlanner.FRAME, WindowEvent.WINDOW_CLOSING));
		}
	}

}
