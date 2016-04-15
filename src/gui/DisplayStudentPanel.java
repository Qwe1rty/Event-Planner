package gui;

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
import javax.swing.table.DefaultTableModel;

import data.Parameter;
import data.Student;

public class DisplayStudentPanel extends JPanel {
	private final String BACK_BUTTON_TEXT = "Back";
	private final String ADD_BUTTON_TEXT = "Add";
	private final String REMOVE_BUTTON_TEXT = "Delete";
	private final String SEARCH_BUTTON_TEXT = "Search";

	private final int TEXT_AREA_ROWS = 17;
	private final int TEXT_AREA_COLS = 70;
	private final int TEXT_FIELD_COLS = 15;

	private final Font BUTTON_FONT = new Font("Tw Cen MT", Font.BOLD, 22);
	private final Font TEXT_FONT = new Font("Tw Cen MT", Font.BOLD, 26);
	private final Font FIELD_FONT = new Font("Tw Cen MT", Font.PLAIN, 22);
	private final Font SEARCH_FONT = new Font("Tw Cen MT", Font.PLAIN, 42);

	private final Dimension BUTTON_SIZE = new Dimension(108, 50);

	private final String STUDENT_NO_COLUMN_HEADER = "Student No.";
	private final String FIRST_NAME_COLUMN_HEADER = "First Name";
	private final String LAST_NAME_COLUMN_HEADER = "Last Name";
	private final String PAYMENT_COLUMN_HEADER = "Payment";
	private final String FOOD_CHOICE_COLUMN_HEADER = "Food Choice";
	private final String TABLE_NO_COLUMN_HEADER = "Table No.";

	private final String[] COLUMN_NAMES = { STUDENT_NO_COLUMN_HEADER,
			FIRST_NAME_COLUMN_HEADER, LAST_NAME_COLUMN_HEADER, PAYMENT_COLUMN_HEADER,
			FOOD_CHOICE_COLUMN_HEADER, TABLE_NO_COLUMN_HEADER };

	private JButton backButton;
	private JButton addButton;
	private JButton deleteButton;
	private JButton searchButton;

	private String searchItem;

	private JTextField searchBar;

	private JLabel studentNum;
	private JLabel firstName;
	private JLabel lastName;
	private JLabel payment;
	private JLabel foodChoice;
	private JLabel tableNum;

	private Image background;
	private JPanel nestedPanel;
	private JTable displayTable;
	private Object[][] placeholderData = { {} };
	private Vector<Vector<String>> rowData;
	private int selectedRow, selectedCol;

	private enum Header {
		ID, FIRST_NAME, LAST_NAME, PAID, FOOD_CHOICE, TABLE_NO
	};

	private Header selectedHeader;

	public DisplayStudentPanel() {
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

		// Search Button
		searchButton = new JButton(SEARCH_BUTTON_TEXT);
		searchButton.addActionListener(new SearchButtonActionListener());
		searchButton.setBackground(new Color(3, 159, 244));
		searchButton.setPreferredSize(BUTTON_SIZE);
		searchButton.setForeground(Color.WHITE);
		searchButton.setFont(BUTTON_FONT);

		// Position the search button next to the delete button
		c.gridx = 3;
		c.gridy = 0;
		nestedPanel.add(searchButton, c);

		// TODO: Change the data in the table to the loaded data
		StudentTableModel model = new StudentTableModel(placeholderData,
				COLUMN_NAMES);
		displayTable = new JTable(placeholderData, COLUMN_NAMES);

		displayTable.setModel(model);
		displayTable.addMouseListener(new TableMouseListener());
		displayTable
				.setPreferredScrollableViewportSize(new Dimension(1100, 500));
		displayTable.getTableHeader().addMouseListener(
				new TableColumnMouseListener());
		displayTable.setRowHeight(30);
		displayTable.setFont(FIELD_FONT);
		displayTable.getTableHeader().setFont(TEXT_FONT);
		displayTable.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollPane = new JScrollPane(displayTable);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 5;
		c.insets = new Insets(15, 0, 0, 0);
		nestedPanel.add(scrollPane, c);

		add(nestedPanel);

		// By default have the first row and column selected
		selectedRow = 0;
		selectedCol = 0;
		selectedHeader = null;
	}

	/**
	 * Call before changing this panel to the main frame. Refreshes the items in
	 * the food drop down box
	 */
	public void refresh() {
		// Remove all the items from the table
		DefaultTableModel model = (DefaultTableModel) displayTable.getModel();
		while (displayTable.getRowCount() > 0) {
			model.removeRow(0);
		}

		// Add the updated students to the table
		for (int i = 0; i < Student.listSize(); ++i) {
			Student student = Student.getStudent(i);
			String paid = student.isPaid() ? "Yes" : "No";
			int tableNum = student.getTableNum();
			String table = tableNum == 0 ? "Unassigned" : Integer
					.toString(tableNum);
			model.addRow(new Object[] { student.getID(),
					student.getFirstname(), student.getLastname(), paid,
					student.getFood().toString(), table });
		}
	}

	/**
	 * Draws the background image onto main panel
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, null);
	}

	class StudentTableModel extends DefaultTableModel {
		public StudentTableModel(Vector rows, Vector columnNames) {
			super(rows, columnNames);
		}

		public StudentTableModel(Object[][] rows, Object[] columnNames) {
			super(rows, columnNames);
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			return false;
		}
	}

	class BackButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			EventPlanner.setPanel(EventPlanner.Panel.HOME);
		}
	}

	// This object finds the row and column where the user has clicked
	class TableMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			selectedRow = displayTable.rowAtPoint(e.getPoint());// get
																// mouse-selected
																// row
			selectedCol = displayTable.columnAtPoint(e.getPoint());// get
																	// mouse-selected
																	// col

			// If there is a double click open the student profile;
			if (e.getClickCount() == 2) {
				String id = (String) displayTable.getValueAt(selectedRow, 0);
				String firstName = (String) displayTable.getValueAt(
						selectedRow, 1);
				String lastName = (String) displayTable.getValueAt(selectedRow,
						2);
				String paidValue = (String) displayTable.getValueAt(
						selectedRow, 3);
				boolean paid = false;
				if (paidValue.equalsIgnoreCase("Yes")) {
					paid = true;
				}
				String chosenFood = (String) displayTable.getValueAt(
						selectedRow, 4);

				int tableNum;
				try {
					tableNum = Integer.parseInt((String) displayTable
							.getValueAt(selectedRow, 5));
				} catch (NumberFormatException ex) {
					// table number is unassigned (0)
					tableNum = 0;
				}

				// Go through all the students and see if that one is the same
				// as the one selected
				for (int i = 0; i < Student.listSize(); ++i) {
					Student student = Student.getStudent(i);
					if (student.getID().equalsIgnoreCase(id)
							&& student.getFirstname().equalsIgnoreCase(
									firstName)
							&& student.getLastname().equalsIgnoreCase(lastName)
							&& student.isPaid() == paid
							&& student.getFood().toString()
									.equalsIgnoreCase(chosenFood)
							&& student.getTableNum() == tableNum) {
						// This is the student, show its information panel
						EventPlanner.showStudentProfile(new StudentProfile(
								student));
						break;
					}
				}

			}

		}
	}

	/**
	 * Listens for mouse presses on the header of the displayed table. Which
	 * ever column is presses is how to table is sorted
	 * 
	 * @author Connor Murphy
	 *
	 */
	class TableColumnMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			int headerIndex = displayTable.getTableHeader().columnAtPoint(
					e.getPoint());
			String value = (String) displayTable.getColumnModel()
					.getColumn(headerIndex).getHeaderValue();

			// Find which column is selected. If the column was already
			// selected, sort by descending and sort by ascending if not. To make
			// things easier, if a column is sorted as descending it is not
			// recorded as selected to be sorted by ascending later
			if (value.equalsIgnoreCase(STUDENT_NO_COLUMN_HEADER)) {
				if (selectedHeader == Header.ID) {
					selectedHeader = null;
					Student.sort(Parameter.STUDENT_ID, false);
				} else {
					selectedHeader = Header.ID;
					Student.sort(Parameter.STUDENT_ID, true);
				}
			} else if (value.equalsIgnoreCase(FIRST_NAME_COLUMN_HEADER)) {
				if (selectedHeader == Header.FIRST_NAME) {
					selectedHeader = null;
					Student.sort(Parameter.FIRSTNAME, false);
				} else {
					selectedHeader = Header.FIRST_NAME;
					Student.sort(Parameter.FIRSTNAME, true);
				}
			} else if (value.equalsIgnoreCase(LAST_NAME_COLUMN_HEADER)) {
				if (selectedHeader == Header.LAST_NAME) {
					selectedHeader = null;
					Student.sort(Parameter.LASTNAME, false);
				} else {
					selectedHeader = Header.LAST_NAME;
					Student.sort(Parameter.LASTNAME, true);
				}
			}else if (value.equalsIgnoreCase(PAYMENT_COLUMN_HEADER)) {
				if (selectedHeader == Header.PAID) {
					//TODO: be able to sort by paid
					selectedHeader = null;
					//Student.sort(Parameter.PAID, false);
				} else {
					selectedHeader = Header.PAID;
					//Student.sort(Parameter.PAID, true);
				}
			}
			else if (value.equalsIgnoreCase(FOOD_CHOICE_COLUMN_HEADER)) {
				if (selectedHeader == Header.FOOD_CHOICE) {
					selectedHeader = null;
					Student.sort(Parameter.FOODTYPE, false);
				} else {
					selectedHeader = Header.FOOD_CHOICE;
					Student.sort(Parameter.FOODTYPE, true);
				}
			}
			else if (value.equalsIgnoreCase(TABLE_NO_COLUMN_HEADER)) {
				if (selectedHeader == Header.TABLE_NO) {
					selectedHeader = null;
					Student.sort(Parameter.TABLE_NUMBER, false);
				} else {
					selectedHeader = Header.TABLE_NO;
					Student.sort(Parameter.TABLE_NUMBER, true);
				}
			}
			
			refresh();
		}
	}

	class AddButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			EventPlanner.setPanel(EventPlanner.Panel.STUDENT);
		}
	}

	class DeleteButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

		}
	}

	// TODO: 2016-04-12 Implement actual search functions
	class SearchButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			// A dialog box appears that prompts user input
			searchItem = JOptionPane.showInputDialog(EventPlanner.FRAME, "",
					"Search", JOptionPane.PLAIN_MESSAGE);

			if (searchItem != null && searchItem.length() > 0) {
				System.out.println("SEARCH FOR " + searchItem);
			}

		}
	}
}
