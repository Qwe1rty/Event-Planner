package gui;

import data.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Gives a in depth visual display of the student
 *
 * @author Connor Murphy
 */
public class StudentProfile extends JPanel {

    private final String BACK_BUTTON_TEXT = "Back";
    private final String ID_LABEL_TEXT = "Student ID:";
    private final String FIRST_NAME_LABEL_TEXT = "First Name:";
    private final String LAST_NAME_LABEL_TEXT = "Last Name:";
    private final String FOOD_LABEL_TEXT = "Food Choice:";
    private final String PAID_LABEL_TEXT = "Paid:";
    private final String PAID_BY_LABEL_TEXT = "Paid By:";
    private final String ALLERGIES_LABEL_TEXT = "Allergies:";
    private final String MORE_INFO_LABEL_TEXT = "Additional Information:";
    private final String TABLE_NUM_LABEL_TEXT = "Table Number:";


    private static final Font BUTTON_FONT = new Font("Tw Cen MT", Font.BOLD, 22);
    private static final Font TEXT_FONT = new Font("Tw Cen MT", Font.BOLD, 28);
    private static final Font FIELD_FONT = new Font("Tw Cen MT", Font.PLAIN, 24);

    private final Dimension BUTTON_SIZE = new Dimension(108, 50);

    private Student student;

    private GridBagConstraints c;

    private JButton backButton;
    private JButton finishButton;
    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField foodField;
    private JTextField paidField;
    private JTextField paidByField;
    private JTextArea allergiesArea;
    private JTextArea moreInfoArea;
    private JTextField tableNumField;

    private JLabel idLabel;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel foodLabel;
    private JLabel paidLabel;
    private JLabel paidByLabel;
    private JLabel allergiesLabel;
    private JLabel moreInfoLabel;
    private JLabel tableNumLabel;

    //TODO: add a way for the user to know if the student id and food entered is valid or not
    //TODO: Change the paid text field to a check mark
    //TODO: change food to combo box
    //Could make it so that the user can click on the student paid by and open up their data

    public StudentProfile(Student student) {
        this.student = student;

        setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        // Background color to a light grey with slightly raised borders
        setBackground(new Color(238, 238, 238));
        setBorder(BorderFactory.createRaisedBevelBorder());

        // Set size of nested to slightly smaller (static value)
        setPreferredSize(new Dimension(1206, 626));

        //Back button
        backButton = new JButton(BACK_BUTTON_TEXT);
        backButton.addActionListener(new BackButtonActionListener());
        backButton.setBackground(new Color(3, 159, 244));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(BUTTON_FONT);
        backButton.setPreferredSize(BUTTON_SIZE);

        // Position the back button west of screen
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 0, 0, 100);
        add(backButton, c);

        //Student name
        firstNameLabel = new JLabel(FIRST_NAME_LABEL_TEXT);
        c.gridx = 0;
        c.gridy = 1;
        add(firstNameLabel, c);

        firstNameField = new JTextField(student.getFirstname());
        c.gridx = 1;
        c.gridy = 1;
        add(firstNameField, c);

        lastNameLabel = new JLabel(LAST_NAME_LABEL_TEXT);
        c.gridx = 2;
        c.gridy = 1;
        add(lastNameLabel, c);

        lastNameField = new JTextField(student.getLastname());
        c.gridx = 3;
        c.gridy = 1;
        add(lastNameField, c);

        //Student id
        idLabel = new JLabel(ID_LABEL_TEXT);
        c.gridx = 0;
        c.gridy = 2;
        add(idLabel, c);

        idField = new JTextField(student.getID());
        c.gridx = 1;
        c.gridy = 2;
        add(idField, c);

        //Food
        foodLabel = new JLabel(FOOD_LABEL_TEXT);
        c.gridx = 2;
        c.gridy = 2;
        add(foodLabel, c);

        foodField = new JTextField(student.getFood().toString());
        c.gridx = 3;
        c.gridy = 2;
        add(foodField, c);

        //Payment information
        paidLabel = new JLabel(PAID_LABEL_TEXT);
        c.gridx = 0;
        c.gridy = 3;
        add(paidLabel, c);

        paidField = new JTextField(Boolean.toString(student.isPaid()));
        c.gridx = 1;
        c.gridy = 3;
        add(paidField, c);

        paidByLabel = new JLabel(PAID_BY_LABEL_TEXT);
        c.gridx = 2;
        c.gridy = 3;
        add(paidByLabel);

        paidByField = new JTextField(student.getPaidBy());
        c.gridx = 3;
        c.gridy = 3;
        add(paidField, c);

        //Allergies and more information labels each taking up two spaces
        allergiesLabel = new JLabel(ALLERGIES_LABEL_TEXT);
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 4;
        add(allergiesLabel, c);

        moreInfoLabel = new JLabel(MORE_INFO_LABEL_TEXT);
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 4;
        add(allergiesLabel, c);
    /*
        //The allergies and the additional information areas
        allergiesTextArea = new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLS);
        allergiesTextArea.setFont(FIELD_FONT);
        allergiesTextArea.setBorder(textFieldBorder);

        c.gridx = 0;
        c.gridy ++;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets (10, 0, 0, 15);
        fieldsPanel.add(allergiesTextArea, c);

        moreInfoTextArea = new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLS);
        moreInfoTextArea.setFont(FIELD_FONT);
        moreInfoTextArea.setBorder(textFieldBorder);

        c.gridx = 3;
        fieldsPanel.add(moreInfoTextArea, c);*/

        /*
	private String allergies;
	more info
	private int tableNum;*
         */

        //The Finish button
    }

    class BackButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            EventPlanner.setPanel(EventPlanner.Panel.DISPLAY_STUDENT);
        }
    }

    /**
     * When the user selects that they are finished viewing this students profile, this class updates the new data for the student
     */
    class FinishButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                student.setStudentId(idField.getText());
            } catch (Student.InvalidStudentIDException ex) {
                //If the id is invalid, do not update it
            }
            try {
                student.setFood(foodField.getText());
            } catch (Student.InvalidFoodException ex) {
                //If the food is invalid, do not update it
            }
            try {
                student.setTableNum(Integer.parseInt(tableNumField.getText()));
            } catch (NumberFormatException ex) {
                //If the table number is invalid, do not update it
            }

            student.setLastname(lastNameField.getText());
            student.setFirstname(firstNameField.getText());

            String paid = paidField.getText();
            if (paid.equalsIgnoreCase("true")) {
                student.setPaid(true);
            } else if (paid.equalsIgnoreCase("false")) {
                student.setPaid(false);
            }
            student.setPaidBy(paidByField.getText());
            student.setAllergies(allergiesArea.getText());

            EventPlanner.setPanel(EventPlanner.Panel.DISPLAY_STUDENT);
        }
    }
}
