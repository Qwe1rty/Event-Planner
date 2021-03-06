package gui;

import io.Loader;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import data.Food;
import data.LinkedList;
import data.Settings;
import data.Student;

/**
 * The settings panel is a panel in which to edit the global settings of the
 * event.
 *
 * @author Connor Murphy, Matthew Sun
 * @version 1.2
 */
public class SettingsPanel extends JPanel {
	//The text that appears buttons or as labels for text input areas
	private final String BACK_BUTTON_TEXT = "Back";
	private final String TITLE_TEXT = "                                       Current Food Items                                       ";
	private final String ADD_BUTTON_TEXT = "Add Food Item";
	private final String REMOVE_BUTTON_TEXT = "Remove Food Item";
	private final String NUM_TABLES_TEXT = "Number of Tables:";
	private final String PPL_PER_TABLE_TEXT = "People per Table:";
	private final String CLEAR_DATA_TEXT = "Clear Data";
	private final String SAVE_TEXT = "Save Data";
	private final String LOAD_TEXT = "Load File";

	//The size of the text field
	private final int TEXT_FIELD_COLS = 12;

	//Fonts used by the gui components
	private final Font BUTTON_FONT = new Font("Tw Cen MT", Font.BOLD, 22);
	private final Font SMALLER_BUTTON_FONT = new Font("Tw Cen MT",
			Font.BOLD, 22);
	private final Font TITLE_FONT = new Font("Tw Cen MT", Font.BOLD, 42);
	private final Font TEXT_FONT = new Font("Tw Cen MT", Font.BOLD, 26);
	private final Font FIELD_FONT = new Font("Tw Cen MT", Font.PLAIN, 22);

	//The size that buttons are
	private final Dimension BUTTON_SIZE = new Dimension(375, 60);

	//The title for the food options table
	private JLabel title;

	//The buttons that are in this screen
	private JButton backButton;
	private JButton addFoodItemButton;
	private JButton removeFoodItemButton;
	private JButton clearDataButton;
	private JButton saveButton;
	private JButton loadButton;

	//Labels for the table settings text fields
	private JLabel numTables;
	private JLabel pplPerTable;

	//Where user enters and edits table settings
	private JTextField numTablesField;
	private JTextField pplPerTableField;

	//Varaibles for the table
	private Vector<String> columnNames;
	private JTable foodOptions;
	private JScrollPane foodOptionsScrollPane;
	private int selectedRow;
	private Vector<Vector<String>> options;

	//Aesthetics
	private Image background;
	private JPanel nestedPanel;


	/**
	 * Creates the panel and sets up all the gui components
	 */
	public SettingsPanel() {
		setLayout(new GridBagLayout());

		// Get the bg image
		try {
			background = ImageIO.read(getClass().getResource("/images/bg.png"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		// Set the layout of the nested panel to follow grid bag layotu
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		nestedPanel = new JPanel(layout);

		// Background color to a light grey with slightly raised borders
		nestedPanel.setBackground(new Color(238, 238, 238));
		nestedPanel.setBorder(BorderFactory.createRaisedBevelBorder());

		// Set size of nested to slightly smaller (static value)
		nestedPanel.setPreferredSize(new Dimension(1206, 626));

		// Back button
		backButton = new JButton(BACK_BUTTON_TEXT);
		backButton.addActionListener(new BackButtonActionListener());
		backButton.setBackground(new Color(3, 159, 244));
		backButton.setPreferredSize(new Dimension(108, 50));
		backButton.setForeground(Color.WHITE);
		backButton.setFont(BUTTON_FONT);

		// Position the back button west of screen
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 90);
		nestedPanel.add(backButton, c);

		//Load button
		loadButton = new JButton(LOAD_TEXT);
		loadButton.addActionListener(new LoadButtonListener());
		loadButton.setBackground(new Color(3, 159, 244));
		loadButton.setForeground(Color.WHITE);
		loadButton.setFont(SMALLER_BUTTON_FONT);
		loadButton.setPreferredSize(new Dimension(200, 50));

		c.gridx = 6;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		nestedPanel.add(loadButton, c);

		// Title
		c.insets = new Insets(0, 0, 0, 0);
		title = new JLabel(TITLE_TEXT);
		title.setFont(TITLE_FONT);
		c.gridx = 0;
		c.gridy ++;
		c.gridwidth = 7;
		c.anchor = GridBagConstraints.CENTER;
		nestedPanel.add(title, c);

		// Varaibles for the table
		selectedRow = 0;
		columnNames = new Vector<>();
		columnNames.add("Food");

		// /Find the already defined foods
		LinkedList<String> food = Food.getMealOptions();
		options = new Vector<>();

		if (food != null) {
			try {
				for (int i = 0; i < food.size(); ++i) {
					Vector<String> rowData = new Vector<>();
					rowData.addElement(food.get(i));
					options.addElement(rowData);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//Create the JTable
		foodOptions = new JTable(options, columnNames) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		//Set variables for the table
		foodOptions.setPreferredScrollableViewportSize(new Dimension(760, 300));
		foodOptions.addMouseListener(new TableMouseListener());
		foodOptions.setRowHeight(30);
		foodOptions.setFont(FIELD_FONT);
		foodOptions.setTableHeader(null);
		foodOptionsScrollPane = new JScrollPane(foodOptions);

		c.gridy ++;
		c.gridwidth = 7;
		c.insets = new Insets(15, 10, 0, 10);
		c.anchor = GridBagConstraints.CENTER;
		nestedPanel.add(foodOptionsScrollPane, c);

		c.gridwidth = 3;
		c.insets = new Insets(10, 10, 10, 10);
		// Add edit and remove buttons
		addFoodItemButton = new JButton(ADD_BUTTON_TEXT);
		addFoodItemButton.addActionListener(new AddFoodButtonActionListener());
		addFoodItemButton.setFont(SMALLER_BUTTON_FONT);
		addFoodItemButton.setBackground(new Color(3, 159, 244));
		addFoodItemButton.setForeground(Color.WHITE);
		addFoodItemButton.setPreferredSize(BUTTON_SIZE);
		c.gridx = 1;
		c.gridy = 4;
		c.anchor = GridBagConstraints.WEST;
		nestedPanel.add(addFoodItemButton, c);

		removeFoodItemButton = new JButton(REMOVE_BUTTON_TEXT);
		removeFoodItemButton
		.addActionListener(new RemoveFoodButtonActionListener());
		removeFoodItemButton.setBackground(new Color(3, 159, 244));
		removeFoodItemButton.setForeground(Color.WHITE);
		removeFoodItemButton.setFont(SMALLER_BUTTON_FONT);
		removeFoodItemButton.setPreferredSize(BUTTON_SIZE);
		c.gridx = 4;
		c.anchor = GridBagConstraints.WEST;
		nestedPanel.add(removeFoodItemButton, c);

		// Table settings
		numTables = new JLabel(NUM_TABLES_TEXT);
		numTables.setFont(TEXT_FONT);

		pplPerTable = new JLabel(PPL_PER_TABLE_TEXT);

		pplPerTable.setFont(TEXT_FONT);

		numTablesField = new JTextField(TEXT_FIELD_COLS);
		numTablesField.setText(Integer.toString(Settings.getNumTables()));
		numTablesField.setFont(FIELD_FONT);

		pplPerTableField = new JTextField(TEXT_FIELD_COLS);
		pplPerTableField.setText(Integer.toString(Settings.getTableSize()));
		pplPerTableField.setFont(FIELD_FONT);

		c.insets = new Insets(5, 0, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy ++;
		nestedPanel.add(numTables, c);

		c.anchor = GridBagConstraints.WEST;
		c.gridwidth = 1;
		c.gridx = 3;
		c.gridy = 5;
		nestedPanel.add(numTablesField, c);

		c.anchor = GridBagConstraints.EAST;
		c.gridwidth = 2;
		c.gridx = 4;
		c.gridy = 5;
		nestedPanel.add(pplPerTable, c);

		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 6;
		nestedPanel.add(pplPerTableField, c);

		// Save button
		saveButton = new JButton(SAVE_TEXT);
		saveButton.addActionListener(new SaveButtonListener());
		saveButton.setBackground(new Color(56, 186, 125));
		saveButton.setForeground(Color.WHITE);
		saveButton.setFont(SMALLER_BUTTON_FONT);
		saveButton.setPreferredSize(new Dimension(200, 50));

		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 4;
		c.insets = new Insets(10, 10, 10, 10);
		c.anchor = GridBagConstraints.EAST;
		nestedPanel.add(saveButton, c);

		// Clear Data
		clearDataButton = new JButton(CLEAR_DATA_TEXT);
		clearDataButton.addActionListener(new ClearDataButtonActionListener());
		clearDataButton.setBackground(new Color(243, 69, 65));
		clearDataButton.setForeground(Color.WHITE);
		clearDataButton.setFont(SMALLER_BUTTON_FONT);
		clearDataButton.setPreferredSize(new Dimension(200, 50));

		c.gridx = 4;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.WEST;
		nestedPanel.add(clearDataButton, c);


		// Add all panels to the main panel
		add(nestedPanel);

		setVisible(true);
	}

	/**
	 * Draws the background image onto main panel
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
	}
	
	/**
	 * Refreshes all food options, table details
	 * @author Matthew Sun
	 */
	private void refresh ()
	{
		// Set field values
		numTablesField.setText(Integer.toString(Settings.getNumTables()));
		pplPerTableField.setText(Integer.toString(Settings.getTableSize()));
		
//		// Remove all food options
		DefaultTableModel food = (DefaultTableModel) foodOptions.getModel();
        while (foodOptions.getRowCount() > 0) {
        	food.removeRow(0);
        }
        
        for (int n = 0 ; n < Food.listSize() ; n ++)
        {
        	food.addRow(new Object[] {Food.get(n)});
        }
	}

	/**
	 * Listens for clicks on the food table and records which row is selected.  If the user clicks twice,
	 * an edit dialog appears allowing the user to edit the selected food option
	 */
	private class TableMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			//Store the mouse selected row
			selectedRow = foodOptions.rowAtPoint(e.getPoint());

			//Get the mouse selected column
			int col = foodOptions.columnAtPoint(e.getPoint());

			//Edit the food item on a double click
			if (e.getClickCount() == 2) {
				String value = (String) foodOptions.getValueAt(selectedRow, col);

				try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception ex) {}
				String result = JOptionPane.showInputDialog(EventPlanner.FRAME, "Edit Food:", "Edit", JOptionPane.PLAIN_MESSAGE);
				try {UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());} catch (Exception ex) {}

				// Edit item if OK/Enter if there is actually an edit
				if (result != null) {
					foodOptions.setValueAt(result, selectedRow, col);
					//Remove the old item and add the new item
					int index = Food.indexOf(new Food(value));
					Food.removeFood(value);
					Food.insert(index, new Food(result));
				}
			}
		}
	}

	/**
	 * Allows the user to go back to the home screen. It saves changes the user made to the global settings
	 */
	private class BackButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Get any integer values entered into the text fields
			String numTables = numTablesField.getText();
			String perTable = pplPerTableField.getText();

			// Only if integers are in the fields, change the current global values
			if (isInteger(numTables) && (Integer.parseInt(numTables) >= 0))
				Settings.setNumTables(Integer.parseInt(numTables));
			if (isInteger(perTable) && (Integer.parseInt(perTable) >= 0))
				Settings.setTableSize(Integer.parseInt(perTable));
			// Switch to the home panel
			EventPlanner.setPanel(EventPlanner.Panel.HOME);
		}

		/**
		 * Checks if a given String is an integer or not
		 *
		 * @param s the given String
		 * @return whether or not the given String is an integer or not
		 */
		private boolean isInteger(String s) {
			// Empty strings are not integers
			if (s.equals(""))
				return false;

			// Checks each letter, if not digit return false
			for (int n = 0; n < s.length(); n++) {
				if (!Character.isDigit(s.charAt(n)))
					return false;
			}
			return true;
		}
	}

	/**
	 * Allows the user to add a food to the global list of foods
	 */
	private class AddFoodButtonActionListener implements ActionListener {
		/**
		 * Called when the user clicks the button
		 *
		 * @param e the action
		 */
		public void actionPerformed(ActionEvent e) {

			// Store the user's edited value
			try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception ex) {}
			String newFoodItem = JOptionPane.showInputDialog(EventPlanner.FRAME,
					"Enter new item: ", "", JOptionPane.PLAIN_MESSAGE);
			try {UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());} catch (Exception ex) {}


			// Only add the item if the String entered is valid
			if (newFoodItem != null && newFoodItem.length() > 0) {
				Food.appendFood(new Food(newFoodItem));

				// Add the food item
				Vector<String> foodItem = new Vector<>();
				foodItem.add(newFoodItem);

				//Update the new food in the table
				DefaultTableModel model = (DefaultTableModel) foodOptions
						.getModel();
				model.addRow(foodItem);
				foodOptions.revalidate();
				foodOptions.repaint();
			}


		}
	}

	/**
	 * Allos the user to remove a food from the global list of foods
	 */
	private class RemoveFoodButtonActionListener implements ActionListener {
		/**
		 * Removes the food at the selected row from the table and from the global
		 * food list when this button is clicked
		 *
		 * @param e the event
		 */
		public void actionPerformed(ActionEvent e) {
			if (selectedRow >= 0 && selectedRow < foodOptions.getRowCount()) {
				System.out.println(foodOptions.getRowCount());
				String value = (String) foodOptions.getValueAt(selectedRow, 0);
				((DefaultTableModel) foodOptions.getModel())
				.removeRow(selectedRow);
				Food.removeFood(value);
				selectedRow = -1;
			}
		}
	}

	/**
	 * Clears all the food options from the program
	 *
	 * @author Connor Murphy
	 */
	private class ClearDataButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			DefaultTableModel model = (DefaultTableModel) foodOptions.getModel();

			//Remove all foods from the master list and the table
			Food.removeAll();
			while (model.getRowCount() > 0) {
				model.removeRow(0);
			}

			Student.removeAll();

			//Reset number of tables and table size
			Settings.setNumTables(0);
			Settings.setTableSize(0);

			//Clear the text from the text fields
			numTablesField.setText("");
			pplPerTableField.setText("");

		}
	}

	/**
	 * Saves the file when the user clicks the save button
	 */
	private class SaveButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			// Saves file
			Loader.saveAsFile();
		}
	}

	/**
	 * Loads when the user clicks the load button
	 */
	private class LoadButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//Clear the data
			clearDataButton.doClick();

			try {
				boolean success;
				success = Loader.parseFile();
				
				// Give a notification that the file was loaded if successful
				// If success is false, it means the user just clicked the X or canceled
				if(success) {
					try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception ex) {}
					JOptionPane.showMessageDialog(EventPlanner.FRAME, "File has been successfully loaded", "File loaded", JOptionPane.INFORMATION_MESSAGE);
					try {UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());} catch (Exception ex) {}
					refresh();
				}
			} catch (FileNotFoundException fnf) { // If file was not found
				try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception ex) {}
		          JOptionPane.showMessageDialog(EventPlanner.FRAME, "The file was not found\nPlease try again",
		                    "File Not Found", JOptionPane.ERROR_MESSAGE);
		          try {UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());} catch (Exception ex) {}
			} catch (Exception exc) { // If there was a parsing error
				try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception ex) {}
				JOptionPane.showMessageDialog(EventPlanner.FRAME, "File is corrupted - file could not be loaded", "Corrupted file", JOptionPane.ERROR_MESSAGE);
				try {UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());} catch (Exception ex) {}
			}
			
		}
	}
}