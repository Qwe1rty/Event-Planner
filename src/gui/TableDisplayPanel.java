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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import data.LinkedList;
import data.Parameter;
import data.Settings;
import data.Student;
import data.Table;

/**
 * Displays the table planning panel
 *
 * @author Matthew Sun, Connor Murphy
 */
public class TableDisplayPanel extends JPanel {
    // Names for the columns of the table
    private final String STUDENT_NO_COLUMN_HEADER = "Student No.";
    private final String FIRST_NAME_COLUMN_HEADER = "First Name";
    private final String LAST_NAME_COLUMN_HEADER = "Last Name";
    private final String PAYMENT_COLUMN_HEADER = "Payment";
    private final String FOOD_CHOICE_COLUMN_HEADER = "Food Choice";
    private final String TABLE_NO_COLUMN_HEADER = "Table No.";

    // Button text
    private final String BACK_BUTTON_TEXT = "Back";
    private final String REMOVE_BUTTON_TEXT = "Delete";
    private final String ADD_BUTTON_TEXT = "Add";

    // Fonts
    private final Font BUTTON_FONT = new Font("Tw Cen MT", Font.BOLD, 22);
    private final Font TEXT_FONT = new Font("Tw Cen MT", Font.BOLD, 28);
    private final Font FIELD_FONT = new Font("Tw Cen MT", Font.PLAIN, 24);
    private final Font SEARCH_FONT = new Font("Tw Cen MT", Font.PLAIN, 30);

    // The array of names for the columns
    private final String[] COLUMN_NAMES = {STUDENT_NO_COLUMN_HEADER,
            FIRST_NAME_COLUMN_HEADER, LAST_NAME_COLUMN_HEADER,
            PAYMENT_COLUMN_HEADER,
            FOOD_CHOICE_COLUMN_HEADER, TABLE_NO_COLUMN_HEADER};

    // Dimensions and sizes
    private final Dimension BUTTON_SIZE = new Dimension(108, 50);
    private final int SEARCH_FIELD_ROWS = 20;

    private JButton deleteButton, addButton, backButton;

    private Image background;
    private JPanel nestedPanel;

    private Vector<Vector<String>> tables;
    private Vector<String> columnNames;
    private JTable availableTables, studentDisplay, tableDisplay;
    private JScrollPane availableTablesScrollPane, studentDisplayScrollPane,
            tableDisplayScrollPane;

    private Object[][] placeholderData = new Object[0][0];

    // Variables used to search for a student
    private final String ALL_SEARCH_OPTION = "All";
    private final String STUDENT_ID_SEARCH_OPTION = "Student ID";
    private final String FIRST_NAME_SEARCH_OPTION = "First Name";
    private final String LAST_NAME_SEARCH_OPTION = "Last Name";
    private final String FOOD_CHOICE_SEARCH_OPTION = "Food Choice";
    private final String TABLE_NO_SEARCH_OPTION = "Table No.";
    private final String PAID_SEARCH_OPTION = "Paid";
    private final String[] SEARCH_OPTIONS = {ALL_SEARCH_OPTION, STUDENT_ID_SEARCH_OPTION,
            FIRST_NAME_SEARCH_OPTION, LAST_NAME_SEARCH_OPTION, FOOD_CHOICE_SEARCH_OPTION, TABLE_NO_SEARCH_OPTION,
            PAID_SEARCH_OPTION};
    private final String PRE_SEARCH_TEXT = "Search...";
    private String searchItem;
    private JTextField searchBar;
    private JComboBox<String> searchOptions;

    // A list of the displayed students
    private LinkedList<Student> displayedStudents;

    private int selectedRowOnUnassigned, selectedRowOnAssigned;
    private int currentSelectedTable;

    /**
     * Used to represent the values in the column headers
     */
    private enum Header {
        ID, FIRST_NAME, LAST_NAME, PAID, FOOD_CHOICE, TABLE_NO
    }

    /**
     * Used to keep track of what was the last selected column header in order
     * to know whether to sort ascending or descending
     */
    private Header selectedHeader;

    public TableDisplayPanel() {
        setLayout(new GridBagLayout());

        // Get the bg image
        try {
            background = ImageIO.read(getClass().getResource("/images/bg.png"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // Set the layout of the nested panel to follow grid bag layout
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
        c.gridx ++;
        nestedPanel.add(addButton, c);

        // Delete Button
        deleteButton = new JButton(REMOVE_BUTTON_TEXT);
        deleteButton.addActionListener(new DeleteButtonActionListener());
        deleteButton.setBackground(new Color(243, 69, 65));
        deleteButton.setPreferredSize(BUTTON_SIZE);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(BUTTON_FONT);

        // Position the delete button next to the add button
        c.gridx ++;
        nestedPanel.add(deleteButton, c);

        // Advanced search options
        searchOptions = new JComboBox<String>(SEARCH_OPTIONS);
        searchOptions.setFont(FIELD_FONT);

        // Position the search options next to the search bar
        c.gridx ++;
        c.insets = new Insets(0, 180, 0, 7);
        nestedPanel.add(searchOptions, c);

        // Search bar
        searchItem = "";
        searchBar = new JTextField(SEARCH_FIELD_ROWS);
        searchBar.addFocusListener(new SearchBarFocusListener());
        searchBar.addKeyListener(new SearchBarKeyListener());
        searchBar.setText(PRE_SEARCH_TEXT);
        searchBar.setForeground(Color.GRAY);
        searchBar.setFont(SEARCH_FONT);

        // Position the search bar next to the search options
        c.gridx ++;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(2, 0, 0, 0);
        nestedPanel.add(searchBar, c);

        // Create new table names
        tables = new Vector<>();
        columnNames = new Vector<>();
        columnNames.add("Total Tables");

        // Add the table names to the display table
        for (int n = 1; n < Settings.getNumTables() + 1; n++) {
            Vector<String> rowData = new Vector<>();
            rowData.addElement("Table " + n);

            tables.addElement(rowData);
        }

        // Create the new table that isn't able to be edited
        availableTables = new JTable(tables, columnNames) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        availableTables.addMouseListener(new AvailableTableMouseListener());
        // Set display parameters, font, no header
        availableTables.setPreferredScrollableViewportSize(new Dimension(240,
                530));
        availableTables.setRowHeight(30);
        availableTables.setFont(FIELD_FONT);
        availableTables.setTableHeader(null);
        availableTables.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        availableTablesScrollPane = new JScrollPane(availableTables);

        // Position to the table below the buttons left of screen
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 2;
        c.insets = new Insets(10, 0, 0, 10);
        nestedPanel.add(availableTablesScrollPane, c);

        // This object finds the row and column where the user has clicked
        MouseListener unassignedStudentMouseListener = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
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
        studentDisplay.getTableHeader().addMouseListener(
                new TableColumnMouseListener());
        studentDisplay.getTableHeader().setReorderingAllowed(false);
        studentDisplay.setRowHeight(30);
        studentDisplay.setFont(FIELD_FONT);
        studentDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentDisplay.getTableHeader().setFont(TEXT_FONT);
        studentDisplayScrollPane = new JScrollPane(studentDisplay);

        // Position this table right of the table table
        c.gridx = 2;
        c.gridwidth = 3;
        c.gridheight = 1;
        nestedPanel.add(studentDisplayScrollPane, c);

        // This object finds the row and column where the user has clicked
        MouseListener assignedStudentMouseListener = new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                selectedRowOnAssigned = tableDisplay.rowAtPoint(e
                        .getPoint());
            }
        };

        // Create a table that displays students that are at the current table
        // selected
        StudentTableModel atTable = new StudentTableModel(placeholderData,
                COLUMN_NAMES);
        tableDisplay = new JTable(placeholderData, COLUMN_NAMES);
        tableDisplay.addMouseListener(assignedStudentMouseListener);
        tableDisplay.setModel(atTable);
        tableDisplay
                .setPreferredScrollableViewportSize(new Dimension(880, 230));
        tableDisplay.setRowHeight(30);
        tableDisplay.setFont(FIELD_FONT);
        tableDisplay.setTableHeader(null);
        tableDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableDisplayScrollPane = new JScrollPane(tableDisplay);

        // Position this table below the unassigned students
        c.gridy ++;
        nestedPanel.add(tableDisplayScrollPane, c);

        add(nestedPanel);

        // By default have the first table
        currentSelectedTable = 1;

        // Create a list of students that are to be displayed
        displayedStudents = new LinkedList<>();
        for (int i = 0; i < Student.listSize(); ++i) {
            displayedStudents.append(Student.getStudent(i));
        }
    }

    public void refresh(boolean updateFromGlobalStudentList) {
        // Remove all the items from the table
        DefaultTableModel model = (DefaultTableModel) studentDisplay.getModel();
        while (studentDisplay.getRowCount() > 0) {
            model.removeRow(0);
        }
        if (updateFromGlobalStudentList) {
            // Remove students from displayed list
            displayedStudents.clear();

            // Add students to the list of displayed students
            for (int i = 0; i < Student.listSize(); ++i) {
                displayedStudents.append(Student.getStudent(i));
            }
        }
        // Else the display list is already updated and proceed normally

        // Add the updated students to the table
        for (int i = 0; i < displayedStudents.size(); ++i) {
            Student student = displayedStudents.get(i);
            int tableNum = student.getTableNum();
            // Only add unassigned students
            if (tableNum == 0) {
                String paid = student.isPaid() ? "Yes" : "No";
                String table = tableNum == 0 ? "Unassigned" : Integer
                        .toString(tableNum);
                model.addRow(new Object[]{student.getID(),
                        student.getFirstname(), student.getLastname(), paid,
                        student.getFood().toString(), table});
            }
        }
        
     // Remove all students from the current table display
        DefaultTableModel currentStudentsTableModel = (DefaultTableModel) tableDisplay
                .getModel();
        while (tableDisplay.getRowCount() > 0) {
            currentStudentsTableModel.removeRow(0);
        }

        // Add the updated students to the table (only unassigned students)
        for (int i = 0; i < Student.listSize(); ++i) {
            Student student = Student.getStudent(i);

            // Only add a student to the unassigned panel if they are unassigned
            if (student.getTableNum() == currentSelectedTable) {

                String paid = student.isPaid() ? "Yes" : "No";
                int tableNumber = student.getTableNum();
                String table = tableNumber == 0 ? "Unassigned" : Integer
                        .toString(tableNumber);
                currentStudentsTableModel.addRow(new Object[]{
                        student.getID(),
                        student.getFirstname(), student.getLastname(), paid,
                        student.getFood().toString(), table});
            }
        }

       indicateFullTables();
    }

    /**
     * Draws the background image onto main panel
     */
    public void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
    }

	private void indicateFullTables ()
	{
		// Remove all the availible tables from the table
		DefaultTableModel tableTableModel = (DefaultTableModel) availableTables
				.getModel();

		while (availableTables.getRowCount() > 0)
		{
			tableTableModel.removeRow(0);

		}
		// Add the updated number of tables to the table
		for (int n = 0; n < Table.listSize(); n++)
		{
			Vector<String> rowData = new Vector<>();
			if (Table.getTable(n).isFull())
				rowData.addElement("Table " + (n + 1) + " (FULL)");
			else
				rowData.addElement("Table " + (n + 1));

			tableTableModel.addRow(rowData);
		}
	}

    private class StudentTableModel extends DefaultTableModel {
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

     /**
     * Handles events that happens when a table is clicked
     *
     * @author Matthew Sun
     * @version 4/14/16
     */
    private class AvailableTableMouseListener extends MouseAdapter {
        /**
         * Actions to take when the mouse clicks on a table (left JTable)
         */
        public void mouseReleased(MouseEvent e) {
            // Remove all current elements in the current table display table
            DefaultTableModel currentMembers = (DefaultTableModel) tableDisplay
                    .getModel();
            while (tableDisplay.getRowCount() > 0) {
                currentMembers.removeRow(0);
            }

            currentSelectedTable = availableTables.rowAtPoint(e.getPoint()) + 1;

            // Add all table members at the current selected table
            for (int i = 0; i < Student.listSize(); ++i) {
                Student student = Student.getStudent(i);
               // System.out.println(student.getTableNum());
                // Only add a student to the current seated if they are at the
                // selected table
                if (student.getTableNum() == currentSelectedTable) {
                    //System.out.println("HELLO");
                    String paid = student.isPaid() ? "Yes" : "No";
                    int tableNumber = student.getTableNum();
                    String table = tableNumber == 0 ? "Unassigned" : Integer
                            .toString(tableNumber);
                    currentMembers.addRow(new Object[]{student.getID(),
                            student.getFirstname(), student.getLastname(),
                            paid, student.getFood().toString(), table});
                }
            }
        }
    }

    private class BackButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            EventPlanner.setPanel(EventPlanner.Panel.HOME);
        }
    }

    /**
     * Handles events that happen when add is pressed
     *
     * @author Matthew Sun
     * @version 4/14/16
     */
    private class AddButtonActionListener implements ActionListener {
        /**
         * Adds the selected student to the table if the table is not full
         */
        public void actionPerformed(ActionEvent arg0) {
            Table table = Table.getTable(currentSelectedTable - 1);
            if(table == null)
            {
            	//There have been no created tables so do nothing
            	return;
            }
            if (!table.isFull()) {
                // Selected student data
                String id = (String) studentDisplay.getValueAt(
                        selectedRowOnUnassigned, 0);
                String firstName = (String) studentDisplay.getValueAt(
                        selectedRowOnUnassigned, 1);
                String lastName = (String) studentDisplay.getValueAt(
                        selectedRowOnUnassigned, 2);
                System.out.println("ID " + id + " firstname " + firstName);

                // Go through all the students and see if that one is the same as
                // the one selected
                for (int i = 0; i < Student.listSize(); ++i) {
                    Student student = Student.getStudent(i);
                    if (student.getID().equalsIgnoreCase(id) &&
                            student.getFirstname().equalsIgnoreCase(firstName) &&
                            student.getLastname().equalsIgnoreCase(lastName)) {

                        // This is the student, change their table and update their table in the global list
                        student.setTableNum(currentSelectedTable);
                        Table.addStudent(currentSelectedTable - 1, student);
                        break;
                    }
                }
                refresh(false);

            } else {
                //Show a warning message
          	 try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception ex) {}
                JOptionPane.showMessageDialog(EventPlanner.FRAME, "This table is full. Student cannot be added", "Full table", JOptionPane.WARNING_MESSAGE);
                try {UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());} catch (Exception ex) {}
            }
        }
    }

    /**
     * Handles events that happen when delete is pressed
     *
     * @author Matthew Sun, Connor Murphy
     */
    private class DeleteButtonActionListener implements ActionListener {
        /**
         * Actions to take when delete is pressed
         */
        public void actionPerformed(ActionEvent e) {
            String id = "";
            String firstName = "";
            String lastName = "";

            try {
                id = (String) tableDisplay.getValueAt(selectedRowOnAssigned,
                        0);
                firstName = (String) tableDisplay.getValueAt(
                        selectedRowOnAssigned, 1);
                lastName = (String) tableDisplay.getValueAt(
                        selectedRowOnAssigned, 2);
            } catch (Exception ex) {
                //Ignore any miss-clicks
            }

            System.out.println("ID " + id + " firstname " + firstName);

            // Go through all the students and see if that one is the same as
            // the one selected
            for (int i = 0; i < Student.listSize(); ++i) {
                Student student = Student.getStudent(i);
                if (student.getID().equalsIgnoreCase(id) &&
                        student.getFirstname().equalsIgnoreCase(firstName) &&
                        student.getLastname().equalsIgnoreCase(lastName)) {
                    // This is the student, change their table and remove them from their table on the global table list
                    student.setTableNum(0);
                    Table.removeStudent(currentSelectedTable - 1, selectedRowOnAssigned);
                    break;
                }
            }

            refresh(false);
        }
    }

    /**
     * Handles graphical changes when the search bar loses or gains focus
     *
     * @author Matthew Sun
     * @version 4/17/16
     */
    private class SearchBarFocusListener implements FocusListener {
        /**
         * User selects the search bar, clear the search bar and change color
         */
        public void focusGained(FocusEvent arg0) {
            searchBar.setText("");
            searchBar.setForeground(Color.BLACK);
        }

        /**
         * User deselects the search bar, reupdate the search bar text + color
         */
        public void focusLost(FocusEvent e) {
            searchBar.setText(PRE_SEARCH_TEXT);
            searchBar.setForeground(Color.GRAY);
        }
    }

    /**
     * Does a live instant search while user types search keywords
     *
     * @author Matthew Sun
     * @version 4/17/16
     */
    private class SearchBarKeyListener implements KeyListener {

        public void keyPressed(KeyEvent arg0) {

        }

        /**
         * Performs a live instant search that updates the display table
         */
        public void keyReleased(KeyEvent e) {
            // Get a live update on what is being searched (every time a key is
            // released)
            searchItem = searchBar.getText();

            // There is a search
            if (searchItem.length() > 0) {
                LinkedList<Student> searchResults = new LinkedList<>();
                // Find which parameter is selected and search by it
                if (searchOptions.getSelectedItem().equals(ALL_SEARCH_OPTION)) {
                    searchResults = Student.search(null,
                            searchItem);
                } else if (searchOptions.getSelectedItem().equals(
                        STUDENT_ID_SEARCH_OPTION)) {
                    searchResults = Student.search(Parameter.STUDENT_ID,
                            searchItem);
                } else if (searchOptions.getSelectedItem().equals(
                        FIRST_NAME_SEARCH_OPTION)) {
                    searchResults = Student.search(Parameter.FIRSTNAME,
                            searchItem);
                } else if (searchOptions.getSelectedItem().equals(
                        LAST_NAME_SEARCH_OPTION)) {
                    searchResults = Student.search(Parameter.LASTNAME,
                            searchItem);
                } else if (searchOptions.getSelectedItem().equals(
                        FOOD_CHOICE_SEARCH_OPTION)) {
                    searchResults = Student.search(Parameter.FOODTYPE,
                            searchItem);
                } else if (searchOptions.getSelectedItem().equals(
                        TABLE_NO_SEARCH_OPTION)) {
                    searchResults = Student.search(Parameter.TABLE_NUMBER,
                            searchItem);
                } else if (searchOptions.getSelectedItem()
                        .equals(PAID_SEARCH_OPTION)) {
                    searchResults = Student.search(Parameter.PAID,
                            searchItem);
                }

                // Update the displayed list based on search results
                displayedStudents = searchResults;
                // Update the table
                refresh(false);
            }
            // Search terms are cleared, reset the display table
            else if (searchItem.equals(""))
                refresh(true);
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    }

    /**
     * Listens for mouse presses on the header of the displayed table. Which
     * ever column is presses is how to table is sorted
     *
     * @author Connor Murphy
     */
    private class TableColumnMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Find the String representation of wich column header was clicked
            int headerIndex = studentDisplay.getTableHeader().columnAtPoint(
                    e.getPoint());
            String value = (String) studentDisplay.getColumnModel()
                    .getColumn(headerIndex).getHeaderValue();

            // Find which column is selected. If the column was already
            // selected, sort by descending and sort by ascending if not. To
            // make
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
            } else if (value.equalsIgnoreCase(PAYMENT_COLUMN_HEADER)) {
                if (selectedHeader == Header.PAID) {
                    selectedHeader = null;
                    Student.sort(Parameter.PAID, false);
                } else {
                    selectedHeader = Header.PAID;
                    Student.sort(Parameter.PAID, true);
                }
            } else if (value.equalsIgnoreCase(FOOD_CHOICE_COLUMN_HEADER)) {
                if (selectedHeader == Header.FOOD_CHOICE) {
                    selectedHeader = null;
                    Student.sort(Parameter.FOODTYPE, false);
                } else {
                    selectedHeader = Header.FOOD_CHOICE;
                    Student.sort(Parameter.FOODTYPE, true);
                }
            } else if (value.equalsIgnoreCase(TABLE_NO_COLUMN_HEADER)) {
                if (selectedHeader == Header.TABLE_NO) {
                    selectedHeader = null;
                    Student.sort(Parameter.TABLE_NUMBER, false);
                } else {
                    selectedHeader = Header.TABLE_NO;
                    Student.sort(Parameter.TABLE_NUMBER, true);
                }
            }
            // Update the displayed list
            while (displayedStudents.size() > 0)
                displayedStudents.remove(0);

            for (int i = 0; i < Student.listSize(); ++i) {
                displayedStudents.append(Student.getStudent(i));
            }

            // Update the table to show the sorted results
            refresh(true);
        }
    }
}
