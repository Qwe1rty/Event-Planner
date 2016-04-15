package gui;

import gui.DisplayStudentPanel.StudentTableModel;
import gui.DisplayStudentPanel.TableMouseListener;
import gui.SettingsPanel.EditMouseListener;

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
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import data.Food;
import data.Settings;
import data.Student;

/**
 * Displays the table planning panel
 * @author Matthew Sun, Connor Murphy
 *
 */
public class TableDisplayPanel extends JPanel
{
	private static final String BACK_BUTTON_TEXT = "Back";
	private static final String REMOVE_BUTTON_TEXT = "Delete";
	private static final String ADD_BUTTON_TEXT = "Add";

	private static final int TEXT_AREA_ROWS = 10;
	private static final int TEXT_AREA_COLS = 35;

	private static final int TEXT_FIELD_ROWS = 21;
	private static final Font BUTTON_FONT = new Font("Tw Cen MT", Font.BOLD, 22);
	private static final Font TEXT_FONT = new Font("Tw Cen MT", Font.BOLD, 28);
	private static final Font FIELD_FONT = new Font("Tw Cen MT", Font.PLAIN, 24);

	private final String[] COLUMN_NAMES = { "Student No.", "First Name",
			"Last Name", "Payment", "Food Choice", "Table No." };

	private final Dimension COMBO_SIZE = new Dimension(340, 30);
	private final Dimension BUTTON_SIZE = new Dimension(108, 50);

	private JButton deleteButton;
	private JButton addButton;
	private JButton backButton;

	private Image background;
	private JPanel nestedPanel;

	private Vector<Vector<String>> tables;
	private Vector<String> columnNames;
	private JTable availibleTables;
	private JScrollPane availibleTablesScrollPane;

	private Object[][] placeholderData = new Object[0][0];
	private JTable studentDisplay;
	private JScrollPane studentDisplayScrollPane;
	private JTable tableDisplay;
	private JScrollPane tableDisplayScrollPane;

	private int selectedRowOnUnassigned, selectedRowOnAssigned;
	private int currentSelectedTable;

	public TableDisplayPanel()
	{
		setLayout(new GridBagLayout());

		// Get the bg image
		try
		{
			background = ImageIO.read(getClass().getResource("/images/bg.png"));
		}
		catch (IOException ioe)
		{
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
		backButton.setPreferredSize(BUTTON_SIZE);
		backButton.setForeground(Color.WHITE);
		backButton.setFont(BUTTON_FONT);

		// Position the back button west of screen
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 20);
		nestedPanel.add(backButton, c);

		// Add Button
		addButton = new JButton(ADD_BUTTON_TEXT);
		addButton.addActionListener(new AddButtonActionListener());
		addButton.setBackground(new Color(56, 186, 125));
		addButton.setPreferredSize(BUTTON_SIZE);
		addButton.setForeground(Color.WHITE);
		addButton.setFont(BUTTON_FONT);

		// Position the add button right of the back button
		c.gridx = 1;
		c.gridy = 0;
		nestedPanel.add(addButton, c);

		// Delete Button
		deleteButton = new JButton(REMOVE_BUTTON_TEXT);
		deleteButton.addActionListener(new DeleteButtonActionListener());
		deleteButton.setBackground(new Color(243, 69, 65));
		deleteButton.setPreferredSize(BUTTON_SIZE);
		deleteButton.setForeground(Color.WHITE);
		deleteButton.setFont(BUTTON_FONT);

		// Position the add button right of the back button
		c.gridx = 2;
		c.gridy = 0;
		nestedPanel.add(deleteButton, c);

		// Create new table names
		tables = new Vector<>();
		columnNames = new Vector<>();
		columnNames.add("Total Tables");

		// Add the table names to the display table
		for (int n = 1; n < Settings.getNumTables() + 1; n++)
		{
			Vector<String> rowData = new Vector<>();
			rowData.addElement("Table " + n);
			System.out.println(rowData);
			tables.addElement(rowData);
		}

		// Create the new table that isn't able to be edited
		availibleTables = new JTable(tables, columnNames)
		{
			@Override
			public boolean isCellEditable(int row, int col)
			{
				return false;
			}
		};

		availibleTables.addMouseListener(new AvailibleTableMouseListener());
		// Set display parameters, font, no header
		availibleTables.setPreferredScrollableViewportSize(new Dimension(240,
				530));
		availibleTables.setRowHeight(30);
		availibleTables.setFont(FIELD_FONT);
		availibleTables.setTableHeader(null);
		availibleTablesScrollPane = new JScrollPane(availibleTables);

		// Position to the table below the buttons left of screen
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 2;
		c.insets = new Insets(10, 0, 0, 10);
		nestedPanel.add(availibleTablesScrollPane, c);

		// This object finds the row and column where the user has clicked
		MouseListener unassignedStudentMouseListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				selectedRowOnUnassigned = studentDisplay.rowAtPoint(e
						.getPoint());
			}
		};

		// Create a new table that only displays students that haven't been
		// assigned yet
		StudentTableModel unassigned = new StudentTableModel(placeholderData,
				COLUMN_NAMES);
		studentDisplay = new JTable(placeholderData, COLUMN_NAMES);

		studentDisplay.setModel(unassigned);
		studentDisplay.setPreferredScrollableViewportSize(new Dimension(880,
				250));
		studentDisplay.addMouseListener(unassignedStudentMouseListener);
		studentDisplay.getTableHeader().setReorderingAllowed(false);
		studentDisplay.setRowHeight(30);
		studentDisplay.setFont(FIELD_FONT);
		studentDisplay.getTableHeader().setFont(TEXT_FONT);
		studentDisplayScrollPane = new JScrollPane(studentDisplay);

		// Position this table right of the table table
		c.gridx = 2;
		c.gridy = 1;
		c.gridheight = 1;
		nestedPanel.add(studentDisplayScrollPane, c);

		// This object finds the row and column where the user has clicked
		MouseListener assignedStudentMouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				selectedRowOnAssigned = studentDisplay.rowAtPoint(e
						.getPoint());
			}
		};
		
		// Create a table that displays students that are at the current table
		// selected
		StudentTableModel atTable = new StudentTableModel(placeholderData,
				COLUMN_NAMES);
		tableDisplay = new JTable(placeholderData, COLUMN_NAMES);

		tableDisplay.setModel(atTable);
		tableDisplay
				.setPreferredScrollableViewportSize(new Dimension(880, 230));
		tableDisplay.setRowHeight(30);
		tableDisplay.setFont(FIELD_FONT);
		tableDisplay.setTableHeader(null);
		tableDisplayScrollPane = new JScrollPane(tableDisplay);

		// Position this table below the unassigned students
		c.gridx = 2;
		c.gridy = 2;
		nestedPanel.add(tableDisplayScrollPane, c);

		add(nestedPanel);
	}

	/**
	 * Draws the background image onto main panel
	 */
	public void paintComponent(Graphics g)
	{
		// TODO 4/15/16 change paint component to resize the BG depending on screen size
		g.drawImage(background, 0, 0, null);
	}

	/**
	 * Call before changing this panel to the main frame. Refreshes the number
	 * of tables, and the current students in the system
	 */
	public void refresh()
	{
		// Remove all the availible tables from the table
		DefaultTableModel tableTableModel = (DefaultTableModel) availibleTables
				.getModel();
		System.out.println(availibleTables.getRowCount() + "ROWS");
		while (availibleTables.getRowCount() > 0)
		{
			tableTableModel.removeRow(0);
			System.out.println("REMOVING");
		}
		// Add the updated number of tables to the table
		for (int n = 1; n < Settings.getNumTables() + 1; n++)
		{
			Vector<String> rowData = new Vector<>();
			rowData.addElement("Table " + n);
			System.out.println(rowData);
			tableTableModel.addRow(rowData);
		}

		// Remove all students from the unassigned students table
		DefaultTableModel studentTableModel = (DefaultTableModel) studentDisplay
				.getModel();
		while (studentDisplay.getRowCount() > 0)
		{
			studentTableModel.removeRow(0);
		}
		System.out.println("REFREH");
		// Add the updated students to the table (only unassigned students)
		for (int i = 0; i < Student.listSize(); ++i)
		{
			Student student = Student.getStudent(i);
			System.out.println(student.getTableNum());
			// Only add a student to the unassigned panel if they are unassigned
			if (student.getTableNum() == 0)
			{
				System.out.println("HELLO");
				String paid = student.isPaid() ? "Yes" : "No";
				int tableNumber = student.getTableNum();
				String table = tableNumber == 0 ? "Unassigned" : Integer
						.toString(tableNumber);
				studentTableModel.addRow(new Object[] { student.getID(),
						student.getFirstname(), student.getLastname(), paid,
						student.getFood().toString(), table });
			}
		}
		
		// Remove all students from the current table display
		DefaultTableModel currentStudentsTableModel = (DefaultTableModel) tableDisplay.getModel();
		while (tableDisplay.getRowCount() > 0)
		{
			currentStudentsTableModel.removeRow(0);
		}
		System.out.println("REFREH");
		// Add the updated students to the table (only unassigned students)
		for (int i = 0; i < Student.listSize(); ++i)
		{
			Student student = Student.getStudent(i);
			System.out.println(student.getTableNum());
			// Only add a student to the unassigned panel if they are unassigned
			if (student.getTableNum() == currentSelectedTable)
			{
				System.out.println("HELLO");
				String paid = student.isPaid() ? "Yes" : "No";
				int tableNumber = student.getTableNum();
				String table = tableNumber == 0 ? "Unassigned" : Integer
						.toString(tableNumber);
				currentStudentsTableModel.addRow(new Object[] { student.getID(),
						student.getFirstname(), student.getLastname(), paid,
						student.getFood().toString(), table });
			}
		}
	}
	
	/**
	 * Refreshes everything on the page except for the table display
	 */
	public void refreshEveryThingButTableDisplay ()
	{
		// Remove all students from the unassigned students table
		DefaultTableModel studentTableModel = (DefaultTableModel) studentDisplay
				.getModel();
		while (studentDisplay.getRowCount() > 0)
		{
			studentTableModel.removeRow(0);
		}
		System.out.println("REFREH");
		// Add the updated students to the table (only unassigned students)
		for (int i = 0; i < Student.listSize(); ++i)
		{
			Student student = Student.getStudent(i);
			System.out.println(student.getTableNum());
			// Only add a student to the unassigned panel if they are unassigned
			if (student.getTableNum() == 0)
			{
				System.out.println("HELLO");
				String paid = student.isPaid() ? "Yes" : "No";
				int tableNumber = student.getTableNum();
				String table = tableNumber == 0 ? "Unassigned" : Integer
						.toString(tableNumber);
				studentTableModel.addRow(new Object[] { student.getID(),
						student.getFirstname(), student.getLastname(), paid,
						student.getFood().toString(), table });
			}
		}
		
		// Remove all students from the current table display
		DefaultTableModel currentStudentsTableModel = (DefaultTableModel) tableDisplay.getModel();
		while (tableDisplay.getRowCount() > 0)
		{
			currentStudentsTableModel.removeRow(0);
		}
		System.out.println("REFREH");
		// Add the updated students to the table (only unassigned students)
		for (int i = 0; i < Student.listSize(); ++i)
		{
			Student student = Student.getStudent(i);
			System.out.println(student.getTableNum());
			// Only add a student to the unassigned panel if they are unassigned
			if (student.getTableNum() == currentSelectedTable)
			{
				System.out.println("HELLO");
				String paid = student.isPaid() ? "Yes" : "No";
				int tableNumber = student.getTableNum();
				String table = tableNumber == 0 ? "Unassigned" : Integer
						.toString(tableNumber);
				currentStudentsTableModel.addRow(new Object[] { student.getID(),
						student.getFirstname(), student.getLastname(), paid,
						student.getFood().toString(), table });
			}
		}
	}

	class StudentTableModel extends DefaultTableModel
	{
		public StudentTableModel(Vector rows, Vector columnNames)
		{
			super(rows, columnNames);
		}

		public StudentTableModel(Object[][] rows, Object[] columnNames)
		{
			super(rows, columnNames);
		}

		@Override
		public boolean isCellEditable(int row, int col)
		{
			return false;
		}
	}

	/**
	 * Handles events that happens when a table is clicked
	 * @author Matthew Sun
	 * @version 4/14/16
	 */
	class AvailibleTableMouseListener extends MouseAdapter
	{
		/**
		 * Actions to take when the mouse clicks on a table (left JTable)
		 */
		public void mousePressed(MouseEvent e)
		{
			// Remove all current elements in the current table display table
			DefaultTableModel currentMembers = (DefaultTableModel) tableDisplay
					.getModel();
			while (tableDisplay.getRowCount() > 0)
			{
				currentMembers.removeRow(0);
			}

			currentSelectedTable = availibleTables.rowAtPoint(e.getPoint()) + 1;
			// Add all table members at the current selected table
			for (int i = 0; i < Student.listSize(); ++i)
			{
				Student student = Student.getStudent(i);
				System.out.println(student.getTableNum());
				// Only add a student to the current seated if they are at the
				// selected table
				if (student.getTableNum() == currentSelectedTable)
				{
					System.out.println("HELLO");
					String paid = student.isPaid() ? "Yes" : "No";
					int tableNumber = student.getTableNum();
					String table = tableNumber == 0 ? "Unassigned" : Integer
							.toString(tableNumber);
					currentMembers.addRow(new Object[] { student.getID(),
							student.getFirstname(), student.getLastname(),
							paid, student.getFood().toString(), table });
				}
			}
		}
	}

	class BackButtonActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			EventPlanner.setPanel(EventPlanner.Panel.HOME);
		}
	}

	/**
	 * Handles events that happen when add is pressed
	 * @author Matthew Sun
	 * @version 4/14/16
	 */
	class AddButtonActionListener implements ActionListener
	{
		/**
		 * Actions to take when add is pressed
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			// When add is pressed, remove the student from the unassigned table
			// and add to the current selected table
			

			
			 String id = (String) studentDisplay.getValueAt(selectedRowOnUnassigned, 0);
             String firstName = (String) studentDisplay.getValueAt(selectedRowOnUnassigned, 1);
             String lastName = (String) studentDisplay.getValueAt(selectedRowOnUnassigned, 2);
             System.out.println("ID " + id + " firstname " + firstName);
             
             //Go through all the students and see if that one is the same as the one selected
             for (int i = 0; i < Student.listSize(); ++i) {
                 Student student = Student.getStudent(i);
                 if (student.getID().equalsIgnoreCase(id) &&
                         student.getFirstname().equalsIgnoreCase(firstName) &&
                         student.getLastname().equalsIgnoreCase(lastName)) {
                     //This is the student, change their table
         			student.setTableNum(currentSelectedTable);
                     break;
                 }
             }
			
			
			refreshEveryThingButTableDisplay();
		}
	}

	/**
	 * Handles events that happen when delete is pressed
	 * @author Matthew Sun
	 * @version 4/14/16
	 */
	class DeleteButtonActionListener implements ActionListener
	{
		/**
		 * Actions to take when delete is pressed
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			 String id = (String) tableDisplay.getValueAt(selectedRowOnAssigned, 0);
             String firstName = (String) tableDisplay.getValueAt(selectedRowOnAssigned, 1);
             String lastName = (String) tableDisplay.getValueAt(selectedRowOnAssigned, 2);
             System.out.println("ID " + id + " firstname " + firstName);
             
             //Go through all the students and see if that one is the same as the one selected
             for (int i = 0; i < Student.listSize(); ++i) {
                 Student student = Student.getStudent(i);
                 if (student.getID().equalsIgnoreCase(id) &&
                         student.getFirstname().equalsIgnoreCase(firstName) &&
                         student.getLastname().equalsIgnoreCase(lastName)) {
                     //This is the student, change their table
         			student.setTableNum(0);
                     break;
                 }
             }
             
         	refreshEveryThingButTableDisplay();
		}
	}
}
