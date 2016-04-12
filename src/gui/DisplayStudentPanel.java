package gui;

import gui.SettingsPanel.BackButtonActionListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.*;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class DisplayStudentPanel extends JPanel {
    private final String BACK_BUTTON_TEXT = "Back";
    private final String ADD_BUTTON_TEXT = "Add";
    private final String REMOVE_BUTTON_TEXT = "Delete";

    private final int TEXT_AREA_ROWS = 17;
    private final int TEXT_AREA_COLS = 70;
    private final int TEXT_FIELD_COLS = 3;

    private static final Font BUTTON_FONT = new Font("Tw Cen MT", Font.BOLD, 22);
    private static final Font TEXT_FONT = new Font("Tw Cen MT", Font.BOLD, 26);
    private static final Font FIELD_FONT = new Font("Tw Cen MT", Font.PLAIN, 22);

    private final Dimension BUTTON_SIZE = new Dimension(108, 50);

    private final String[] COLUMN_NAMES = {"Student No.", "First Name", "Last Name", "Payment", "Food Choice", "Table No."};

    private JButton backButton;
    private JButton addButton;
    private JButton deleteButton;

    private JLabel studentNum;
    private JLabel firstName;
    private JLabel lastName;
    private JLabel payment;
    private JLabel foodChoice;
    private JLabel tableNum;

    private Image background;
    private JPanel nestedPanel;
    private JTable displayTable;
    private Object[][] placeholderData = {{"067848929", "Matthew", "Sun", "Yes", "Vegetarian", "1062"},
            {"1232132132", "Connor", "Murple", "No", "Maybe", "12"},
            {"022222229", "Hello", "Caleb", "Yes", "-", "23"},
            {"213232323", "dff", "df", "aa", "ss", "1"},
            {"232323434", "asdfd", "asd", "43", "44", "10652"}
    };
    private Vector<Vector<String>> rowData;

    private int selectedRow, selectedCol;

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

        //Back button
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

        // Add Button
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

        //TODO: Change the data in the table to the loaded data
        displayTable = new JTable(placeholderData, COLUMN_NAMES) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        displayTable.setPreferredScrollableViewportSize(new Dimension(1100, 500));
        JScrollPane scrollPane = new JScrollPane(displayTable);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 5;
        c.insets = new Insets(20, 0, 0, 0);
        nestedPanel.add(scrollPane, c);

        add(nestedPanel);

        //By default have the first row selected
        selectedRow = 0;
        selectedCol = 0;
    }

    /**
     * Draws the background image onto main panel
     */
    public void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, null);
    }

    class BackButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 6; col++) {
                    System.out.println(placeholderData[row][col] + " ");
                }
                System.out.println(" ");
            }
            EventPlanner.setPanel(EventPlanner.Panel.HOME);
        }
    }

    //This object finds the row and column where the user has clicked
    class TableMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            selectedRow = displayTable.rowAtPoint(e.getPoint());//get mouse-selected row
            selectedCol = displayTable.columnAtPoint(e.getPoint());//get mouse-selected col

            //If there is a double click open the student profile;
            if(e.getClickCount() == 2)
            {
              //  EventPlanner.showStudentProfile(new StudentProfile());
            }

        }
    };

    class AddButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            EventPlanner.setPanel(EventPlanner.Panel.STUDENT);
        }
    }

    class DeleteButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {

        }
    }
}
