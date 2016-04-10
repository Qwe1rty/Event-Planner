package gui;

import data.Food;
import data.LinkedList;
import data.Settings;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AbstractDocument;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The settings panel is a panel in which to edit the global settings of the event.
 * @author Connor Murphy, Matthew Sun
 * @version 1.2
 */
public class SettingsPanel extends JPanel {

    private static final String BACK_BUTTON_TEXT = "Back";
    private static final String TITLE_TEXT = "                                       Current Food Items                                       ";
    private static final String ADD_BUTTON_TEXT = "Add Food Item";
    private static final String EDIT_BUTTON_TEXT = "Edit Food Item";
    private static final String REMOVE_BUTTON_TEXT = "Remove Food Item";
    private static final String NUM_TABLES_TEXT = "Number of Tables:";
    private static final String PPL_PER_TABLE_TEXT = "People per Table:";
    private static final String CLEAR_DATA_TEXT = "Clear Data";
    private static final String FINISH_BUTTON_TEXT = "Finish";

    private static final int TEXT_AREA_ROWS = 17;
    private static final int TEXT_AREA_COLS = 70;
    private static final int TEXT_FIELD_COLS = 3;
    
    private static final Font BUTTON_FONT = new Font ("Tw Cen MT", Font.BOLD, 22);
    private static final Font SMALLER_BUTTON_FONT = new Font ("Tw Cen MT", Font.BOLD, 22);
    private static final Font TITLE_FONT = new Font ("Tw Cen MT", Font.BOLD, 42);
    private static final Font TEXT_FONT = new Font ("Tw Cen MT", Font.BOLD, 26);
    private static final Font FIELD_FONT = new Font ("Tw Cen MT", Font.PLAIN, 22);
    
    private final Dimension BUTTON_SIZE = new Dimension (244, 60);

    private JButton backButton;

    private JLabel title;
    
    private JTextArea foodOptions;
    private JScrollPane foodOptionsScrollPane;

    private JButton addFoodItemButton;
    private JButton editFoodItemButton;
    private JButton removeFoodItemButton;

    private JLabel numTables;
    private JLabel pplPerTable;
    private JTextField numTablesField;
    private JTextField pplPerTableField;

    private JButton clearDataButton;
    private JButton finishButton;
    
    private Image background;
    private JPanel nestedPanel;

    /**
     * Creates the panel and sets up all the gui components
     */
    public SettingsPanel()
    {
    	setLayout(new GridBagLayout());
    	
    	// Get the bg image
		try {
			background = ImageIO.read(getClass().getResource("/images/bg.png"));
		}
		catch(IOException ioe)
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
		nestedPanel.setPreferredSize(new Dimension (1206, 626));
		
		
        //Back button
        backButton = new JButton(BACK_BUTTON_TEXT);
        backButton.addActionListener(new BackButtonActionListener());
        backButton.setBackground(new Color (3, 159, 244));
        backButton.setPreferredSize(new Dimension(108, 50));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(BUTTON_FONT);

        // Position the back button west of screen
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets (0, 0, 0, 90);
        nestedPanel.add(backButton, c);

        //Title
        c.insets = new Insets (0,0,0,0);
        title = new JLabel(TITLE_TEXT);
        title.setFont(TITLE_FONT);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 7;
        c.anchor = GridBagConstraints.CENTER;
        nestedPanel.add(title, c);

        //Food options
        foodOptions = new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLS);

        //Add the already defined foods
        String options = "";
        LinkedList<String> food = Food.getMealOptions();
        if(food != null)
        {
            try {
                for (int i = 0; i < food.size(); ++i) {
                    options += food.get(i) + "\n";
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        foodOptions.setText(options);
        foodOptions.setEditable(false);

        foodOptionsScrollPane = new JScrollPane(foodOptions);
        foodOptionsScrollPane.setFont(TEXT_FONT);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 7;
        c.insets = new Insets(15, 10, 0, 10);
        c.anchor = GridBagConstraints.CENTER;
        nestedPanel.add(foodOptionsScrollPane, c);

        c.gridwidth = 2;
        c.insets = new Insets (10,10,10,10);
        //Add edit and remove buttons
        addFoodItemButton = new JButton(ADD_BUTTON_TEXT);
        addFoodItemButton.addActionListener(new AddFoodButtonActionListener());
        addFoodItemButton.setFont(SMALLER_BUTTON_FONT);
        addFoodItemButton.setBackground(new Color (3, 159, 244));
        addFoodItemButton.setForeground(Color.WHITE);
        addFoodItemButton.setPreferredSize(BUTTON_SIZE);
        c.gridx = 1;
        c.gridy = 4;
        c.anchor = GridBagConstraints.WEST;
        nestedPanel.add(addFoodItemButton, c);

        editFoodItemButton = new JButton(EDIT_BUTTON_TEXT);
        editFoodItemButton.addActionListener(new EditFoodButtonActionListener());
        editFoodItemButton.setBackground(new Color (3, 159, 244));
        editFoodItemButton.setForeground(Color.WHITE);
        editFoodItemButton.setFont(SMALLER_BUTTON_FONT);
        editFoodItemButton.setPreferredSize(BUTTON_SIZE);
        c.gridx = 3;
        c.gridy = 4;
        nestedPanel.add(editFoodItemButton, c);

        removeFoodItemButton = new JButton(REMOVE_BUTTON_TEXT);
        removeFoodItemButton.addActionListener(new RemoveFoodButtonActionListener());
        removeFoodItemButton.setBackground(new Color (3, 159, 244));
        removeFoodItemButton.setForeground(Color.WHITE);
        removeFoodItemButton.setFont(SMALLER_BUTTON_FONT);
        removeFoodItemButton.setPreferredSize(BUTTON_SIZE);
        c.gridx = 5;
        c.gridy = 4;
        c.anchor = GridBagConstraints.WEST;
        nestedPanel.add(removeFoodItemButton, c);

        //Table settings
        numTables = new JLabel(NUM_TABLES_TEXT);
        numTables.setFont(TEXT_FONT);
        
        pplPerTable = new JLabel(PPL_PER_TABLE_TEXT);
        pplPerTable.setFont(TEXT_FONT);
        
        numTablesField = new JTextField(TEXT_FIELD_COLS);
        numTablesField.setFont(FIELD_FONT);
        
        pplPerTableField = new JTextField(TEXT_FIELD_COLS);
        pplPerTableField.setFont(FIELD_FONT);
        
        c.insets = new Insets (5, 0, 5, 0);
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 1;
        c.gridx = 3;
        c.gridy = 5;
        nestedPanel.add(numTables, c);
        
        c.gridx = 4;
        c.gridy = 5;
        nestedPanel.add(numTablesField, c);
        
        c.gridx = 3;
        c.gridy = 6;
        nestedPanel.add(pplPerTable, c);
        
        c.gridx = 4;
        c.gridy = 6;
        nestedPanel.add(pplPerTableField, c);


        //Final buttons: clear data and finish
        clearDataButton = new JButton(CLEAR_DATA_TEXT);
        clearDataButton.addActionListener(new ClearDataButtonActionListener());
        clearDataButton.setBackground(new Color(243, 69, 65));
        clearDataButton.setForeground(Color.WHITE);
        clearDataButton.setFont(SMALLER_BUTTON_FONT);
        clearDataButton.setPreferredSize(new Dimension(200, 50));
        
        finishButton = new JButton(FINISH_BUTTON_TEXT);
        finishButton.addActionListener(new FinishButtonActionListener());

        
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 7;
        c.anchor = GridBagConstraints.CENTER;
        nestedPanel.add(clearDataButton, c);

        //Add all panels to the main panel
        add(nestedPanel);

        setVisible(true);
    }

    /**
     * Draws the background image onto main panel
     */
    public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, null);
	}
    
    class BackButtonActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            EventPlanner.setPanel(EventPlanner.Panel.HOME);
        }
    }

    class AddFoodButtonActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

        }
    }

    class EditFoodButtonActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

        }
    }

    class RemoveFoodButtonActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

        }
    }

    class ClearDataButtonActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

        }
    }

    class FinishButtonActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

        }
    }
}
