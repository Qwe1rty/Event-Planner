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
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import data.LinkedList;
import data.Parameter;
import data.Student;

/**
 * This is the main screen the user sees. It displays the main information about
 * every student in the program in a table. The user can sort the information by
 * clicking the column headers at any time
 *
 * @author Connor Murphy, Matthew Sun
 */
public class DisplayStudentPanel extends JPanel {
    // Names for the columns of the table
    private final String STUDENT_NO_COLUMN_HEADER = "Student No.";
    private final String FIRST_NAME_COLUMN_HEADER = "First Name";
    private final String LAST_NAME_COLUMN_HEADER = "Last Name";
    private final String PAYMENT_COLUMN_HEADER = "Payment";
    private final String FOOD_CHOICE_COLUMN_HEADER = "Food Choice";
    private final String TABLE_NO_COLUMN_HEADER = "Table No.";

    // Text for all the buttons
    private final String BACK_BUTTON_TEXT = "Back";
    private final String ADD_BUTTON_TEXT = "Add";
    private final String REMOVE_BUTTON_TEXT = "Delete";

    // Font for everythring that is shown in this screen
    private final Font BUTTON_FONT = new Font("Tw Cen MT", Font.BOLD, 22);
    private final Font TEXT_FONT = new Font("Tw Cen MT", Font.BOLD, 26);
    private final Font FIELD_FONT = new Font("Tw Cen MT", Font.PLAIN, 22);
    private final Font SEARCH_FONT = new Font("Tw Cen MT", Font.PLAIN, 27);

    // The size for each button
    private final Dimension BUTTON_SIZE = new Dimension(108, 50);

    // The size of a text field
    private final int SEARCH_FIELD_ROWS = 20;

    // The array of names for the columns
    private final String[] COLUMN_NAMES = {STUDENT_NO_COLUMN_HEADER,
            FIRST_NAME_COLUMN_HEADER, LAST_NAME_COLUMN_HEADER,
            PAYMENT_COLUMN_HEADER,
            FOOD_CHOICE_COLUMN_HEADER, TABLE_NO_COLUMN_HEADER};

    // The buttons
    private JButton backButton;
    private JButton addButton;
    private JButton deleteButton;

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

    // The background for this screen
    private Image background;

    // The panel used to display everything
    private JPanel nestedPanel;

    // The table that shows all the student data
    private JTable displayTable;

    // Used to find where the user has clicked
    private int selectedRow;

    // A list of the displayed students
    private LinkedList<Student> displayedStudents;

    /**
     * Used to represent the values in the column headers
     */
    private enum Header {
        ID, FIRST_NAME, LAST_NAME, PAID, FOOD_CHOICE, TABLE_NO
    }

    ;

    /**
     * Used to keep track of what was the last selected column header in order
     * to know whether to sort ascending or descending
     */
    private Header selectedHeader;

    /**
     * Creates a panel that shows information about students
     */
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
        c.gridx ++;
        nestedPanel.add(addButton, c);

        // Delete Button
        deleteButton = new JButton(REMOVE_BUTTON_TEXT);
        deleteButton.addActionListener(new DeleteButtonActionListener());
        deleteButton.setBackground(new Color(243, 69, 65));
        deleteButton.setPreferredSize(BUTTON_SIZE);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(BUTTON_FONT);

        // Position the add button right of the back button
        c.gridx ++;
        nestedPanel.add(deleteButton, c);

        // Advanced search options
        searchOptions = new JComboBox<String>(SEARCH_OPTIONS);
        searchOptions.setFont(FIELD_FONT);

        // Position the search options next to the search bar
        c.gridx ++;
        c.insets = new Insets(0, 200, 0, 1);
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
        c.insets = new Insets(2, 0, 0, 0);
        nestedPanel.add(searchBar, c);

        // Placeholder data TODO: Change the data in the table to the loaded
        // data
        Object[][] placeholderData = {{}};
        StudentTableModel model = new StudentTableModel(placeholderData,
                COLUMN_NAMES);
        displayTable = new JTable(placeholderData, COLUMN_NAMES);

        // Set values for the table
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
        displayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(displayTable);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 5;
        c.insets = new Insets(15, 0, 0, 0);
        nestedPanel.add(scrollPane, c);

        add(nestedPanel);

        // By default have the first row and column selected
        selectedRow = -1;
        selectedHeader = null;

        // Create a list of students that are to be displayed
        displayedStudents = new LinkedList<>();
        for (int i = 0; i < Student.listSize(); ++i) {
            displayedStudents.append(Student.getStudent(i));
        }
    }

    /**
     * Call before changing this panel to the main frame. Refreshes the items in
     * the food drop down box
     */
    public void refresh(boolean updateFromGlobalStudentList) {
        // Remove all the items from the table
        DefaultTableModel model = (DefaultTableModel) displayTable.getModel();
        while (displayTable.getRowCount() > 0) {
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
            String paid = student.isPaid() ? "Yes" : "No";
            int tableNum = student.getTableNum();
            String table = tableNum == 0 ? "Unassigned" : Integer
                    .toString(tableNum);
            model.addRow(new Object[]{student.getID(),
                    student.getFirstname(), student.getLastname(), paid,
                    student.getFood().toString(), table});
        }
    }

    /**
     * Draws the background image onto main panel
     */
    public void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
    }

    /**
     * The model used by the table. Its only purpose is to make the cells
     * uneditable in the table
     *
     * @author Connor Murphy
     */
    private class StudentTableModel extends DefaultTableModel {
        /**
         * Creates a new table model
         *
         * @param rows        the initail data that goes in the rows of the table
         * @param columnNames the name of each column
         */
        public StudentTableModel(Object[][] rows, Object[] columnNames) {
            super(rows, columnNames);
        }

        /**
         * Ensures that every cell in the table is uneditable
         *
         * @param row the row of the cell
         * @param col the column of the cell
         * @return if the column is editable (its not)
         */
        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }

    /**
     * Used to go to the previous screen which is the home screen
     *
     * @author Connor Murphy
     */
    private class BackButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            EventPlanner.setPanel(EventPlanner.Panel.HOME);
        }
    }

    /**
     * Finds the row and column where the user has clicked
     *
     * @author Matthew Sun
     */
    private class TableMouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            // Store the selected row and column that was selected
            selectedRow = displayTable.rowAtPoint(e.getPoint());

            // If there is a double click open the student profile;
            if (e.getClickCount() == 2) {
                Student student = displayedStudents.get(selectedRow);
                EventPlanner.showStudentProfile(new StudentProfile(
                        student));
            }

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
        public void mouseReleased(MouseEvent e) {
            // Find the String representation of which column header was clicked
            int headerIndex = displayTable.getTableHeader().columnAtPoint(
                    e.getPoint());
            String value = (String) displayTable.getColumnModel()
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
            displayedStudents.clear();

            for (int i = 0; i < Student.listSize(); ++i) {
                displayedStudents.append(Student.getStudent(i));
            }

            // Update the table to show the sorted results
            refresh(true);
        }
    }

    /**
     * Brings the user to the add student screen when they click the add button
     *
     * @author Matthew Sun
     */
    private class AddButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            EventPlanner.setPanel(EventPlanner.Panel.STUDENT);
        }
    }

    /**
     * Deletes the selected student from the list of students and removes it
     * from the table
     *
     * @author Matthew Sun
     */
    private class DeleteButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (selectedRow >= 0 && selectedRow < displayTable.getRowCount()) {
                Student student = displayedStudents.get(selectedRow);
                Student.removeStudent(student);
                displayedStudents.remove(selectedRow);
                refresh(true);
            }
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
        public void keyTyped(KeyEvent arg0) {

        }

    }
}