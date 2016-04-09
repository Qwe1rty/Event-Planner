package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class HomePanel {
	
	private Dimension size;
	private JFrame frame;
	private BackgroundPanel main;
	private Image background;
	private ImageIcon students, tables, settings, exit;
	
	/**
	 * Starts the frame, panel, and buttons
	 */
	public void start ()
	{
		// Make frame
		size = new Dimension (1280, 720);
		frame = new JFrame("Event Planner");
		frame.setSize(size);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Get the images
		students = new ImageIcon ("Images/students.png");
		tables = new ImageIcon("Images/tables.png");
		settings = new ImageIcon("Images/settings.png");
		exit = new ImageIcon("Images/exit.png");
		background = new ImageIcon("Images/bg.png").getImage();
		
		// Add the images to their buttons
		JButton studentButton = new JButton (students);
		JButton tableButton = new JButton (tables);
		JButton settingsButton = new JButton (settings);
		JButton exitButton = new JButton (exit);
		
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
		
		// Remove the button borders
//		studentButton.setBorderPainted(false);
//		tableButton.setBorderPainted(false);
//		settingsButton.setBorderPainted(false);
//		exitButton.setBorderPainted(false);
		
		// Create the BG panel following a GridBagLayout
		main = new BackgroundPanel(background);
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		// Put some space between the buttons
		c.insets = new Insets(10, 10, 10, 10);
		
		// Add the buttons to the panel, each at their own location
		c.gridx = 0;
		c.gridy = 0;
		main.add(studentButton, c);
		
		c.gridx = 1;
		c.gridy = 0;
		main.add(tableButton, c);
		
		c.gridx = 0;
		c.gridy = 1;
		main.add(settingsButton, c);
		
		c.gridx = 1;
		c.gridy = 1;
		main.add(exitButton, c);
		
		// Add panel to frame and set a max and min resizeable size
		frame.getContentPane().add(main);

		frame.setMaximumSize(size);
		frame.setMinimumSize(new Dimension(700,700));
		frame.setVisible(true);
	}	

	class BackgroundPanel extends JPanel {
		  private Image bg;
		  
		  public BackgroundPanel(Image bg) {
		    this.bg = bg;
		  }

		  public void paintComponent(Graphics g) {
		    g.drawImage(bg, 0, 0, null);
		  }
	}
	
	class StudentButtonListener implements ActionListener
	{
		/**
		 * When student button is pressed
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			main.removeAll();
			main.repaint();	
		}
	}
	
	class TableButtonListener implements ActionListener
	{
		/**
		 * When table button is pressed
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			main.removeAll();
			main.repaint();	
		}
	}
	class SettingsButtonListener implements ActionListener
	{
		/**
		 * When settings button is pressed
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			main.removeAll();
			main.repaint();	
		}
	}
	class ExitButtonListener implements ActionListener
	{
		/**
		 * When exit button is pressed, exit application
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			frame.dispose();		
		}
	}
	
	public static void main(String[] args) {
		HomePanel test = new HomePanel ();
		test.start();

	}

}
