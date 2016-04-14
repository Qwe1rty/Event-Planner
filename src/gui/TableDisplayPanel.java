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
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import data.Settings;
import data.Student;

/**
 * Displays the table planning panel
 * @author Matthew Sun
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
	
    private final String[] COLUMN_NAMES = {"Student No.", "First Name", "Last Name", "Payment", "Food Choice", "Table No."};

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
	
    private Object[][] placeholderData = {{"067848929", "Matthew", "Sun", "Yes", "Vegetarian", "1062"},
            {"1232132132", "Connor", "Murple", "No", "Maybe", "12"},
            {"022222229", "Hello", "Caleb", "Yes", "-", "23"},
            {"213232323", "dff", "df", "aa", "ss", "1"},
            {"232323434", "asdfd", "asd", "43", "44", "10652"}
    };
	private JTable studentDisplay;
	private JScrollPane studentDisplayScrollPane;
	
	

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
		c.insets = new Insets(10, 0, 0, 10);
		nestedPanel.add(availibleTablesScrollPane, c);

		
        //Create a new table that displays students
        StudentTableModel model = new StudentTableModel(placeholderData, COLUMN_NAMES);
        studentDisplay = new JTable(placeholderData, COLUMN_NAMES);

        studentDisplay.setModel(model);
        studentDisplay.setPreferredScrollableViewportSize(new Dimension(880, 495));
        studentDisplay.setRowHeight(30);
        studentDisplay.setFont(FIELD_FONT);
        studentDisplay.getTableHeader().setFont(TEXT_FONT);
        studentDisplayScrollPane = new JScrollPane(studentDisplay);
		
        // Position this table right of the table table
        c.gridx = 2;
        c.gridy = 1;
        nestedPanel.add(studentDisplayScrollPane, c);
        
		
		add(nestedPanel);
	}

	/**
	 * Draws the background image onto main panel
	 */
	public void paintComponent(Graphics g)
	{
		g.drawImage(background, 0, 0, null);
	}

	/**
	 * Call before changing this panel to the main frame. Refreshes the number
	 * of tables, and the current students in the system
	 */
	public void refresh ()
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
		for (int n = 1; n < Settings.getNumTables () + 1; n ++)
		{
			Vector<String> rowData = new Vector<>();
			rowData.addElement("Table " + n);
			System.out.println(rowData);
			tableTableModel.addRow(rowData);
		}
		
		DefaultTableModel studentTableModel = (DefaultTableModel) studentDisplay.getModel();
        while (studentDisplay.getRowCount() > 0) {
        	studentTableModel.removeRow(0);
        }

        //Add the updated students to the table
        for (int i = 0; i < Student.listSize(); ++i) {
            Student student = Student.getStudent(i);
            String paid = student.isPaid() ? "Yes" : "No";
            studentTableModel.addRow(new Object[]{student.getID(), student.getFirstname(), student.getLastname(), paid, student.getFood().toString(), student.getTableNum()});
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

	class BackButtonActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			EventPlanner.setPanel(EventPlanner.Panel.HOME);
		}
	}

	class AddButtonActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{

		}
	}

	class DeleteButtonActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{

		}
	}
}
