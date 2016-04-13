package gui;

import gui.DisplayStudentPanel.AddButtonActionListener;
import gui.DisplayStudentPanel.BackButtonActionListener;
import gui.DisplayStudentPanel.DeleteButtonActionListener;

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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class TableDisplayPanel extends JPanel
{
    private static final String BACK_BUTTON_TEXT = "Back";
    private static final String FIRST_NAME_LABEL_TEXT = "First Name: ";
    private static final String LAST_NAME_LABEL_TEXT = "Last Name: ";
    private static final String STUDENT_ID_LABEL_TEXT = "Student Number: ";
    private static final String FOOD_CHOICE_LABEL_TEXT = "    Food Choice: ";
    private static final String PHONE_NUM_LABEL_TEXT = "Phone Number: ";
    private static final String PAID_BY_LABEL_TEXT = "Paid By: ";
    private static final String TABLE_NUM_LABEL_TEXT = "Table Number: ";
    private static final String ALLERGIES_LABEL_TEXT = "Allergies";
    private static final String MORE_INFO_LABEL_TEXT = "Additional Information";
    private static final String REMOVE_BUTTON_TEXT = "Delete";
    private static final String ADD_BUTTON_TEXT = "Add";

    private static final int TEXT_AREA_ROWS = 10;
    private static final int TEXT_AREA_COLS = 35;

    private static final int TEXT_FIELD_ROWS = 21;
    private static final Font BUTTON_FONT = new Font("Tw Cen MT", Font.BOLD, 22);
    private static final Font TEXT_FONT = new Font("Tw Cen MT", Font.BOLD, 28);
    private static final Font FIELD_FONT = new Font("Tw Cen MT", Font.PLAIN, 24);

    private final Dimension COMBO_SIZE = new Dimension(340, 30);
    private final Dimension BUTTON_SIZE = new Dimension(108, 50);

    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField studentIdTextField;
    private JComboBox<String> foodChoiceComboBox;
    private JTextField phoneNumTextField;
    private JTextField paidByTextField;
    private JComboBox<Integer> tableNumComboBox;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel studentIdLabel;
    private JLabel foodChoiceLabel;
    private JLabel phoneNumLabel;
    private JLabel paidByLabel;
    private JLabel tableNumLabel;

    private JLabel allergiesLabel;
    private JLabel moreInfoLabel;

    private JTextArea allergiesTextArea;
    private JTextArea moreInfoTextArea;

    private JButton deleteButton;
    private JButton addButton;
    private JButton backButton;

    private Image background;
    private JPanel nestedPanel;
    private Border textFieldBorder;
    
    public TableDisplayPanel ()
    {
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
        
        
        
    	add(nestedPanel);
    }
    
    /**
     * Draws the background image onto main panel
     */
    public void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, null);
    }
    
    class BackButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            EventPlanner.setPanel(EventPlanner.Panel.HOME);
        }
    }
    
    class AddButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {

        }
    }
    
    class DeleteButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
        	
        }
    }
}
