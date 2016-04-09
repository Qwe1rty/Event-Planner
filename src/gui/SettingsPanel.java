package gui;

import data.Food;
import data.LinkedList;
import data.Settings;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SettingsPanel extends JPanel {

    private static final String BACK_BUTTON_TEXT = "Back";
    private static final String TITLE_TEXT = "Current Food Items";
    private static final String ADD_BUTTON_TEXT = "Add Food Item";
    private static final String EDIT_BUTTON_TEXT = "Edit Food Item";
    private static final String REMOVE_BUTTON_TEXT = "Remove Food Item";
    private static final String NUM_TABLES_TEXT = "Number of Tables";
    private static final String PPL_PER_TABLE_TEXT = "People per Table";
    private static final String CLEAR_DATA_TEXT = "Clear Data";
    private static final String FINISH_BUTTON_TEXT = "Finish";

    private static final int TEXT_AREA_ROWS = 20;
    private static final int TEXT_AREA_COLS = 60;
    private static final int TEXT_FIELD_COLS = 10;


    private JPanel backButtonPanel;
    private JButton backButton;

    private JPanel titlePanel;
    private JLabel title;

    private JPanel foodOptionsPanel;
    private JTextArea foodOptions;
    private JScrollPane foodOptionsScrollPane;

    private JPanel buttonsPanel;
    private JButton addFoodItemButton;
    private JButton editFoodItemButton;
    private JButton removeFoodItemButton;

    private JPanel tableInfoPanel;
    private JPanel rightInfoPanel;
    private JPanel leftInfoPanel;
    private JLabel numTables;
    private JLabel pplPerTable;
    private JTextField numTablesField;
    private JTextField pplPerTableField;

    private JPanel clearAndFinishPanel;
    private JButton clearDataButton;
    private JButton finishButton;


    public SettingsPanel()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Back button
        backButton = new JButton(BACK_BUTTON_TEXT);
        backButton.addActionListener(new BackButtonActionListener());
        backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

        //Add some spacing between the button and the sides of the panel
        backButtonPanel.add(backButton);

        //Title
        title = new JLabel(TITLE_TEXT);
        title.setFont(new Font("sans-serif", Font.BOLD, 20));
        titlePanel = new JPanel();
        titlePanel.add(title);

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

        foodOptionsScrollPane = new JScrollPane(foodOptions);
        foodOptionsPanel = new JPanel();

        //Add some spacing between the text area and the sides of the panel
        foodOptionsPanel.add(Box.createHorizontalStrut(10));
        foodOptionsPanel.add(foodOptionsScrollPane);
        foodOptionsPanel.add(Box.createHorizontalStrut(10));

        //Add edit and remove buttons
        addFoodItemButton = new JButton(ADD_BUTTON_TEXT);
        addFoodItemButton.addActionListener(new AddFoodButtonActionListener());

        editFoodItemButton = new JButton(EDIT_BUTTON_TEXT);
        editFoodItemButton.addActionListener(new EditFoodButtonActionListener());

        removeFoodItemButton = new JButton(REMOVE_BUTTON_TEXT);
        removeFoodItemButton.addActionListener(new RemoveFoodButtonActionListener());

        buttonsPanel = new JPanel();
        buttonsPanel.add(addFoodItemButton);
        buttonsPanel.add(editFoodItemButton);
        buttonsPanel.add(removeFoodItemButton);

        //Table settings
        numTables = new JLabel(NUM_TABLES_TEXT);
        pplPerTable = new JLabel(PPL_PER_TABLE_TEXT);
        numTablesField = new JTextField(TEXT_FIELD_COLS);
        pplPerTableField = new JTextField(TEXT_FIELD_COLS);

        leftInfoPanel = new JPanel();
        rightInfoPanel = new JPanel();
        leftInfoPanel.add(numTables);
        leftInfoPanel.add(numTablesField);
        rightInfoPanel.add(pplPerTable);
        rightInfoPanel.add(pplPerTableField);

        tableInfoPanel = new JPanel(new GridLayout(1,1));
        tableInfoPanel.add(leftInfoPanel);
        tableInfoPanel.add(rightInfoPanel);

        //Final buttons: clear data and finish
        clearDataButton = new JButton(CLEAR_DATA_TEXT);
        clearDataButton.addActionListener(new ClearDataButtonActionListener());
        finishButton = new JButton(FINISH_BUTTON_TEXT);
        finishButton.addActionListener(new FinishButtonActionListener());

        clearAndFinishPanel = new JPanel();
        clearAndFinishPanel.add(clearDataButton);
        clearAndFinishPanel.add(finishButton);

        //Add all panels to the main panel
        add(backButtonPanel);
        add(titlePanel);
        add(foodOptionsPanel);
        add(buttonsPanel);
        add(tableInfoPanel);
        add(clearAndFinishPanel);

        setVisible(true);
    }

    class BackButtonActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

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
