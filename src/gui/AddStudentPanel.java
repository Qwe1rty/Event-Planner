package gui;


import data.LinkedList;
import data.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO: change the class to be able to be used to add and edit students
public class AddStudentPanel extends JPanel
{
    private static final String BACK_BUTTON_TEXT = "Back";
    private static final String FIRST_NAME_LABEL_TEXT = "First Name:";
    private static final String LAST_NAME_LABEL_TEXT = "Last Name:";
    private static final String STUDENT_ID_LABEL_TEXT = "Student Number:";
    private static final String FOOD_CHOICE_LABEL_TEXT = "Food Choice";
    private static final String PHONE_NUM_LABEL_TEXT = "Phone Number:";
    private static final String PAID_BY_LABEL_TEXT = "Paid By:";
    private static final String TABLE_NUM_LABEL_TEXT = "Table Number:";
    private static final String ALLERGIES_LABEL_TEXT = "Allergies";
    private static final String MORE_INFO_LABEL_TEXT = "Additional Information";
    private static final String CANCEL_BUTTON_TEXT = "Cancel";
    private static final String CONFIRM_BUTTON_TEXT = "Confirm";

    private static final int TEXT_AREA_ROWS = 20;
    private static final int TEXT_AREA_COLS = 30;

    private static final int TEXT_FIELD_ROWS = 20;


    private JPanel backButtonPanel;
    private JButton backButton;

    private JPanel fieldsPanel;
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

    private JPanel textAreaTitlesPanel;
    private JPanel allergiesLabelPanel;
    private JLabel allergiesLabel;
    private JPanel moreInfoLabelPanel;
    private JLabel moreInfoLabel;

    private JPanel textAreasPanel;
    private JPanel allergiesTextAreaPanel;
    private JTextArea allergiesTextArea;
    private JPanel moreInfoTextAreaPanel;
    private JTextArea moreInfoTextArea;

    private JPanel buttonsPanel;
    private JButton cancelButton;
    private JButton confirmButton;

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        AddStudentPanel panel = new AddStudentPanel();
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public AddStudentPanel()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Back button
        backButton = new JButton(BACK_BUTTON_TEXT);
        backButton.addActionListener(new BackButtonActionListener());
        backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        backButtonPanel.add(backButton);

        //Student information fields
        firstNameLabel = new JLabel(FIRST_NAME_LABEL_TEXT);
        firstNameTextField = new JTextField(TEXT_FIELD_ROWS);

        lastNameLabel = new JLabel(LAST_NAME_LABEL_TEXT);
        lastNameTextField = new JTextField(TEXT_FIELD_ROWS);

        studentIdLabel = new JLabel(STUDENT_ID_LABEL_TEXT);
        studentIdTextField = new JTextField(TEXT_FIELD_ROWS);

        LinkedList<String> foodChoices = Settings.getMealOptions();
        String[] choices;
        if(foodChoices != null) {
            choices = new String[foodChoices.size()];
            try {
                for (int i = 0; i < foodChoices.size(); ++i) {
                    choices[i] = foodChoices.get(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            foodChoiceLabel = new JLabel(FOOD_CHOICE_LABEL_TEXT);
            foodChoiceComboBox = new JComboBox<String>(choices);
        }
        else
        {
            choices = new String[0];
            foodChoiceLabel = new JLabel(FOOD_CHOICE_LABEL_TEXT);
            foodChoiceComboBox = new JComboBox<String>(choices);
        }


        phoneNumLabel = new JLabel(PHONE_NUM_LABEL_TEXT);
        phoneNumTextField = new JTextField(TEXT_FIELD_ROWS);

        paidByLabel = new JLabel(PAID_BY_LABEL_TEXT);
        paidByTextField = new JTextField(TEXT_FIELD_ROWS);

        int numTables = Settings.getNumTables();
        Integer[] tables = new Integer[numTables];
        try {
            for (int i = 0; i < numTables; ++i) {
                tables[i] = (i+1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        tableNumLabel = new JLabel(TABLE_NUM_LABEL_TEXT);
        tableNumComboBox = new JComboBox<Integer>(tables);

        fieldsPanel = new JPanel(new GridLayout(4,4));
        fieldsPanel.add(firstNameLabel);
        fieldsPanel.add(firstNameTextField);
        fieldsPanel.add(lastNameLabel);
        fieldsPanel.add(lastNameTextField);

        fieldsPanel.add(studentIdLabel);
        fieldsPanel.add(studentIdTextField);
        fieldsPanel.add(foodChoiceLabel);
        fieldsPanel.add(foodChoiceComboBox);

        fieldsPanel.add(phoneNumLabel);
        fieldsPanel.add(phoneNumTextField);
        fieldsPanel.add(paidByLabel);
        fieldsPanel.add(paidByTextField);

        fieldsPanel.add(tableNumLabel);
        fieldsPanel.add(tableNumComboBox);

        //Titles for the allergies and additional information text areas
        allergiesLabel = new JLabel(ALLERGIES_LABEL_TEXT);
        allergiesLabelPanel = new JPanel(new FlowLayout());
        allergiesLabelPanel.add(allergiesLabel);

        moreInfoLabel = new JLabel(MORE_INFO_LABEL_TEXT);
        moreInfoLabelPanel = new JPanel(new FlowLayout());
        moreInfoLabelPanel.add(moreInfoLabel);

        textAreaTitlesPanel = new JPanel(new GridLayout(1,1));
        textAreaTitlesPanel.add(allergiesLabelPanel);
        textAreaTitlesPanel.add(moreInfoLabelPanel);

        //The allergies and the additional information areas
        allergiesTextArea = new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLS);
        allergiesTextAreaPanel = new JPanel(new FlowLayout());
        allergiesTextAreaPanel.add(allergiesTextArea);

        moreInfoTextArea = new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLS);
        moreInfoTextAreaPanel = new JPanel(new FlowLayout());
        moreInfoTextAreaPanel.add(moreInfoTextArea);

        textAreasPanel = new JPanel(new GridLayout(1,1));
        textAreasPanel.add(allergiesTextAreaPanel);
        textAreasPanel.add(moreInfoTextAreaPanel);

        //The cancel and confirm buttons
        cancelButton = new JButton(CANCEL_BUTTON_TEXT);
        cancelButton.addActionListener(new CancelButtonActionListener());
        confirmButton = new JButton(CONFIRM_BUTTON_TEXT);
        confirmButton.addActionListener(new ConfirmButtonActionListener());

        buttonsPanel = new JPanel();
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(confirmButton);

        //Add all components to the panel
        add(backButtonPanel);
        add(fieldsPanel);
        add(textAreaTitlesPanel);
        add(textAreasPanel);
        add(buttonsPanel);
    }

    private class BackButtonActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

        }
    }

    private class CancelButtonActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

        }
    }

    private class ConfirmButtonActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

        }
    }
}
