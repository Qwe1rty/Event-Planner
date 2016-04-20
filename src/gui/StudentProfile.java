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
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import data.Food;
import data.LinkedList;
import data.Settings;
import data.Student;
import data.Table;
import data.Student.InvalidFoodException;
import data.Student.InvalidStudentIDException;

/**
 * Gives a in depth visual display of the student by listing its information in a similar way to the add student panel
 *
 * @author Connor Murphy, Matthew Sun
 */
public class StudentProfile extends JPanel {

    //Constant values for labels' and buttons' text
    private static final String BACK_BUTTON_TEXT = "Back";
	private final String GUEST_BUTTON_TEXT = "Show Guest";
    private static final String FIRST_NAME_LABEL_TEXT = "First Name: ";
    private static final String LAST_NAME_LABEL_TEXT = "Last Name: ";
    private static final String STUDENT_ID_LABEL_TEXT = "Student Number: ";
    private static final String FOOD_CHOICE_LABEL_TEXT = "    Food Choice: ";
    private static final String PHONE_NUM_LABEL_TEXT = "Phone Number: ";
    private static final String PAID_BY_LABEL_TEXT = "Paid By: ";
    private static final String TABLE_NUM_LABEL_TEXT = "Table Number: ";
	private final String GUEST_LABEL_TEXT = "Has Guest: ";
    private static final String ALLERGIES_LABEL_TEXT = "Allergies";
    private static final String MORE_INFO_LABEL_TEXT = "Additional Information";
    private static final String CANCEL_BUTTON_TEXT = "Cancel";
    private static final String CONFIRM_BUTTON_TEXT = "Confirm";
    private static final String INITIALS_LABEL_TEXT = "Initials: ";
    private static final String FORM_SUBMITTED_LABEL_TEXT = "Form Submitted: ";

    //Size of text areas
    private static final int TEXT_AREA_ROWS = 8;
    private static final int TEXT_AREA_COLS = 35;

    //Size of text fields
    private static final int TEXT_FIELD_ROWS = 21;

    //Fonts for all the gui elements shown
    private static final Font BUTTON_FONT = new Font("Tw Cen MT", Font.BOLD, 22);
    private static final Font TEXT_FONT = new Font("Tw Cen MT", Font.BOLD, 28);
    private static final Font FIELD_FONT = new Font("Tw Cen MT", Font.PLAIN, 24);

    //Sizes for combo boxes and buttons
    private final Dimension COMBO_SIZE = new Dimension(340, 30);
    private final Dimension BUTTON_SIZE = new Dimension(108, 50);

    //Gui elements that display and get information about the student to the user
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField studentIdTextField;
    private JComboBox<String> foodChoiceComboBox;
    private JTextField phoneNumTextField;
    private JTextField paidByTextField;
    private JComboBox<String> tableNumComboBox;
    private JTextField initialsTextField;
    private JCheckBox formSubmittedCheckBox;
    private JTextArea allergiesTextArea;
    private JTextArea moreInfoTextArea;
	private JCheckBox hasGuestCheckBox;

    //Labels to define what each gui element shows
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel studentIdLabel;
    private JLabel foodChoiceLabel;
    private JLabel phoneNumLabel;
    private JLabel paidByLabel;
    private JLabel tableNumLabel;
    private JLabel initialsLabel;
    private JLabel formSubmittedLabel;
    private JLabel allergiesLabel;
    private JLabel moreInfoLabel;
	private JLabel hasGuestLabel;

    //Buttons the user can press to navigate away from this screen.
    //Cancel and back buttons go to the previous screen without saving changes
    //The confirm button goes to the previous screen and saves changes
    private JButton cancelButton;
    private JButton confirmButton;
    private JButton backButton;
	private JButton showGuest;

    //Elements that make the screen more visually pleasing
    private Image background;
    private JPanel fieldsPanel;
    private Border textFieldBorder;

    //The studen whose information is shown
    private Student student;

    /**
     * Creates a new screen that shows detailed information about the given student. Users can view and edit
     * information about the student.
     *
     * @param student the student to display and edit
     */
    public StudentProfile(Student student) {
        this.student = student;

        setLayout(new GridBagLayout());

        // Get the background image
        try {
            background = ImageIO.read(getClass().getResource("/images/bg.png"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // Set the layout of the nested panel to follow grid bag layotu
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        fieldsPanel = new JPanel(layout);

        // Background color to a light grey with slightly raised borders
        fieldsPanel.setBackground(new Color(238, 238, 238));
        fieldsPanel.setBorder(BorderFactory.createRaisedBevelBorder());

        // Set size of nested to slightly smaller (static value)
        fieldsPanel.setPreferredSize(new Dimension(1206, 626));

        // Back button
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
        fieldsPanel.add(backButton, c);
        
    	// Show guest button
		showGuest = new JButton(GUEST_BUTTON_TEXT);
		showGuest.addActionListener(new ShowGuestButtonActionListener());
		showGuest.setBackground(new Color(3, 159, 244));
		showGuest.setForeground(Color.WHITE);
		showGuest.setFont(BUTTON_FONT);
		showGuest.setPreferredSize(new Dimension (150, 50));
		
		// Position the show guest button, east on screen
		c.gridx = 4;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets (0, 0,0,0);
		c.anchor = GridBagConstraints.EAST;
		fieldsPanel.add(showGuest, c);
		c.gridwidth = 1;

        // Set all field labels and fonts
        firstNameLabel = new JLabel(FIRST_NAME_LABEL_TEXT);
        firstNameLabel.setFont(TEXT_FONT);

        firstNameTextField = new JTextField(TEXT_FIELD_ROWS);
        firstNameTextField.setFont(FIELD_FONT);
        String firstName = student.getFirstname();
        if (firstName != null) {
            firstNameTextField.setText(firstName);
        }

        lastNameLabel = new JLabel(LAST_NAME_LABEL_TEXT);
        lastNameLabel.setFont(TEXT_FONT);

        lastNameTextField = new JTextField(TEXT_FIELD_ROWS);
        lastNameTextField.setFont(FIELD_FONT);
        String lastName = student.getLastname();
        if (lastName != null) {
            lastNameTextField.setText(lastName);
        }

        studentIdLabel = new JLabel(STUDENT_ID_LABEL_TEXT);
        studentIdLabel.setFont(TEXT_FONT);

        studentIdTextField = new JTextField(TEXT_FIELD_ROWS);
        studentIdTextField.setFont(FIELD_FONT);
        String id = student.getID();
        if (id != null) {
            studentIdTextField.setText(id);
        }

        //Read in all the food choices and add them to the combo box for food choices
        //If there are no food choices, add an empty set of options to the combo box
        LinkedList<String> foodChoices = Food.getMealOptions();
        String[] choices;
        if (foodChoices != null) {
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
        } else {
            choices = new String[0];
            foodChoiceLabel = new JLabel(FOOD_CHOICE_LABEL_TEXT);
            foodChoiceComboBox = new JComboBox<String>(choices);
        }

        foodChoiceLabel.setFont(TEXT_FONT);
        foodChoiceComboBox.setPreferredSize(COMBO_SIZE);
        foodChoiceComboBox.setFont(FIELD_FONT);
        String foodChoice = student.getFood().toString();
        if (foodChoice != null) {
            foodChoiceComboBox.setSelectedItem(foodChoice);
        }

        phoneNumLabel = new JLabel(PHONE_NUM_LABEL_TEXT);
        phoneNumLabel.setFont(TEXT_FONT);

        phoneNumTextField = new JTextField(TEXT_FIELD_ROWS);
        phoneNumTextField.setFont(FIELD_FONT);
        String phoneNum = student.getPhoneNum();
        if (phoneNum != null) {
            phoneNumTextField.setText(phoneNum);
        }

        paidByLabel = new JLabel(PAID_BY_LABEL_TEXT);
        paidByLabel.setFont(TEXT_FONT);

        paidByTextField = new JTextField(TEXT_FIELD_ROWS);
        paidByTextField.setFont(FIELD_FONT);
        String paidBy = student.getPaidBy();
        if (paidBy != null) {
            paidByTextField.setText(paidBy);
        }

        //Add each table to a combo box of tables
        int numTables = Settings.getNumTables();
        String[] tables = new String[numTables + 1];
        try {
            //0 stands for an unassigned table and that is what the user should see
            tables[0] = "Unassigned";
            for (int i = 1; i < numTables + 1; ++i) {
                tables[i] = Integer.toString(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tableNumLabel = new JLabel(TABLE_NUM_LABEL_TEXT);
        tableNumLabel.setFont(TEXT_FONT);

        tableNumComboBox = new JComboBox<String>(tables);
        tableNumComboBox.setPreferredSize(COMBO_SIZE);
        tableNumComboBox.setFont(FIELD_FONT);
        int tableNum = student.getTableNum();
        //Since tables start at 0, tables can be selected like an index in an array
        tableNumComboBox.setSelectedIndex(tableNum);

        initialsLabel = new JLabel(INITIALS_LABEL_TEXT);
        initialsLabel.setFont(TEXT_FONT);

        initialsTextField = new JTextField(TEXT_FIELD_ROWS);
        initialsTextField.setFont(FIELD_FONT);
        //TODO: add student initals
        initialsTextField.setText(student.getInitials());

        formSubmittedLabel = new JLabel(FORM_SUBMITTED_LABEL_TEXT);
        formSubmittedLabel.setFont(TEXT_FONT);

        formSubmittedCheckBox = new JCheckBox();
        formSubmittedCheckBox.setSelected(student.isFormSubmitted());
        
		hasGuestLabel = new JLabel (GUEST_LABEL_TEXT);
		hasGuestLabel.setFont(TEXT_FONT);
		
		hasGuestCheckBox = new JCheckBox();
		hasGuestCheckBox.setSelected(student.hasGuest());

        // All label components are right aligned with some vertical spacing
        // between them
        c.anchor = GridBagConstraints.EAST;
        c.insets = new Insets(10, 0, 0, 0);

        // Add all wider components first
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        fieldsPanel.add(firstNameLabel, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        fieldsPanel.add(studentIdLabel, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        fieldsPanel.add(phoneNumLabel, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        fieldsPanel.add(tableNumLabel, c);
        
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        fieldsPanel.add(formSubmittedLabel, c);

        // Add all smaller components
        // All field components are left aligned
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        fieldsPanel.add(firstNameTextField, c);

        c.anchor = GridBagConstraints.EAST;
        c.gridx = 3;
        c.gridy = 1;
        fieldsPanel.add(lastNameLabel, c);

        c.anchor = GridBagConstraints.WEST;
        c.gridx = 4;
        c.gridy = 1;
        fieldsPanel.add(lastNameTextField, c);

        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        fieldsPanel.add(studentIdTextField, c);

        c.anchor = GridBagConstraints.EAST;
        c.gridx = 3;
        c.gridy = 2;
        fieldsPanel.add(foodChoiceLabel, c);

        c.anchor = GridBagConstraints.WEST;
        c.gridx = 4;
        c.gridy = 2;
        fieldsPanel.add(foodChoiceComboBox, c);

        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 1;
        fieldsPanel.add(phoneNumTextField, c);

        c.anchor = GridBagConstraints.EAST;
        c.gridx = 3;
        c.gridy = 3;
        fieldsPanel.add(paidByLabel, c);

        c.anchor = GridBagConstraints.WEST;
        c.gridx = 4;
        c.gridy = 3;
        fieldsPanel.add(paidByTextField, c);

        c.anchor = GridBagConstraints.EAST;
        c.gridx = 3;
        c.gridy = 4;
        fieldsPanel.add(initialsLabel, c);

        c.anchor = GridBagConstraints.WEST;
        c.gridx = 4;
        c.gridy = 4;
        fieldsPanel.add(initialsTextField, c);


        c.anchor = GridBagConstraints.WEST;
        c.gridx = 2;
        c.gridy = 4;
        c.gridwidth = 1;
        fieldsPanel.add(tableNumComboBox, c);
        
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 2;
		c.gridy = 5;
		c.insets = new Insets(10, 0, 0, 0);
		fieldsPanel.add(formSubmittedCheckBox, c);
		
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 3;
		c.gridy = 5;
		fieldsPanel.add(hasGuestLabel, c);
		
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 4;
		c.gridy = 5;
		fieldsPanel.add(hasGuestCheckBox, c);

        // Titles for the allergies and additional information text areas
        allergiesLabel = new JLabel(ALLERGIES_LABEL_TEXT);
        allergiesLabel.setFont(TEXT_FONT);

        // Position allergy and extra-info components centered, each taking up 2
        // grid spaces
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;

        c.gridx = 0;
        c.gridy = 6;
        fieldsPanel.add(allergiesLabel, c);

        moreInfoLabel = new JLabel(MORE_INFO_LABEL_TEXT);
        moreInfoLabel.setFont(TEXT_FONT);
        c.gridx = 3;
        fieldsPanel.add(moreInfoLabel, c);

        textFieldBorder = BorderFactory.createLineBorder(Color.GRAY, 1, true);
        // The allergies and the additional information areas
        allergiesTextArea = new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLS);
        allergiesTextArea.setFont(FIELD_FONT);
        allergiesTextArea.setBorder(textFieldBorder);

        c.gridx = 0;
        c.gridy++;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(10, 0, 0, 15);
        fieldsPanel.add(allergiesTextArea, c);

        moreInfoTextArea = new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLS);
        moreInfoTextArea.setFont(FIELD_FONT);
        moreInfoTextArea.setBorder(textFieldBorder);

        c.gridx = 3;
        fieldsPanel.add(moreInfoTextArea, c);

        // The cancel and confirm buttons
        cancelButton = new JButton(CANCEL_BUTTON_TEXT);
        cancelButton.addActionListener(new BackButtonActionListener());
        cancelButton.setBackground(new Color(243, 69, 65));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(BUTTON_FONT);
        cancelButton.setPreferredSize(BUTTON_SIZE);

        confirmButton = new JButton(CONFIRM_BUTTON_TEXT);
        confirmButton.addActionListener(new FinishButtonActionListener());
        confirmButton.setBackground(new Color(56, 186, 125));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(BUTTON_FONT);
        confirmButton.setPreferredSize(BUTTON_SIZE);

        // Position the cancel and confirm buttons with some spacing
        c.insets = new Insets(12, 0, 0, 10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;
        c.gridy++;
        fieldsPanel.add(cancelButton, c);

        c.gridx = 3;
        c.anchor = GridBagConstraints.WEST;
        fieldsPanel.add(confirmButton, c);

        // Add all components to the panel
        add(fieldsPanel);
    }

    /**
     * Draws the background image onto main panel
     */
    public void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
    }

    /**
     * When the user clicks the back or cancel button this class is called.  It checks if any
     * of the student's information changed and if it did warns the user that they have unsaved
     * changes made. If the user wants to leave, the screen is set to the display student panel
     *
     * @author Connor Murphy
     */
    class BackButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Get what is in the text fields
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String id = studentIdTextField.getText();
            String foodChoice = (String) foodChoiceComboBox.getSelectedItem();
            String phoneNumber = phoneNumTextField.getText();
            String paidBy = paidByTextField.getText();
            int tableNum;
            try {
                tableNum = Integer.parseInt((String) tableNumComboBox.getSelectedItem());
            } catch (NumberFormatException nfe) {
                //Tried to parse the string "Unassigned" which is stored as table number 0
                tableNum = 0;
            }
            String allergies = allergiesTextArea.getText();
            String moreInfo = moreInfoTextArea.getText();
            String initials = initialsTextField.getText();
            boolean formSubmitted = formSubmittedCheckBox.isSelected();
            boolean hasGuest = hasGuestCheckBox.isSelected();

            //Compare what is in with what the student has to see if something has changed
            boolean changed = false;
            if (!firstName.equals(student.getFirstname())) {
                changed = true;
            } else if (!lastName.equals(student.getLastname())) {
                changed = true;
            } else if (!id.equals(student.getID())) {
                changed = true;
            } else if (!foodChoice.equals(student.getFood().toString())) {
                changed = true;
            } else if (!phoneNumber.equals(student.getPhoneNum())) {
                changed = true;
            } else if (!paidBy.equals(student.getPaidBy())) {
                changed = true;
            } else if (tableNum != student.getTableNum()) {
                changed = true;
            } else if (!allergies.equals(student.getAllergies())) {
                changed = true;
            } else if (!moreInfo.equals(student.getInfo())) {
                changed = true;
            } else if (formSubmitted != student.isFormSubmitted()) {
                changed = true;
            } else if (hasGuest != student.hasGuest()){
            	changed = true;
            } else if (!initials.equals(student.getInitials())) {
            	changed = true;
            }
            
            //TODO: add initials to the list of checks

            // Show a confirm exit dialog if something has changed
            if (changed) {
                Object[] options = {"Yes", "No"};
                int result = JOptionPane.showOptionDialog(null,
                        "Are you sure you want to leave without saving?", "Back",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[1]);
                if (result == JOptionPane.YES_OPTION)
                    EventPlanner.setPanel(EventPlanner.Panel.DISPLAY_STUDENT, false);
            } else {
                //Nothing changed so the user can go back without consequences
                EventPlanner.setPanel(EventPlanner.Panel.DISPLAY_STUDENT, false);
            }
        }
    }
    
	/**
	 * Shows a guest of the current student
	 * @author Matthew Sun
	 */
	private class ShowGuestButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (!student.hasGuest())
				JOptionPane.showMessageDialog(EventPlanner.FRAME, "This student doesn't have a guest.",
						"No Guest", JOptionPane.ERROR_MESSAGE);
			else
			{
				boolean guestFound = false;
				// Look for the guest (same student number)
				for (int n = 0 ; n < Student.listSize() ; n ++)
				{
					Student guest = Student.getStudent(n);
					// Guest is found
					if (guest.getID().equals(studentIdTextField.getText()) && !guest.hasGuest())
					{
						System.out.println("GUEST FOUND");
						guestFound = true;
						EventPlanner.showStudentProfile(new StudentProfile(guest));
					}
				}
				// No guest found
				if (!guestFound)
					JOptionPane.showMessageDialog(EventPlanner.FRAME, "No guest found.",
						"No Guest", JOptionPane.ERROR_MESSAGE);
			}
				
		}
		
	}

    /**
     * When the user selects that they are finished viewing this students
     * profile, this class updates the new data for the student
     *
     * @author Connor Murphy, Matthew Sun
     */
    class FinishButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String missingComponents = "";
            String invalidComponents = "";

            String firstName = firstNameTextField.getText();
            if (firstName.length() > 0) {
                student.setFirstname(firstName);
            } else {
                missingComponents += "First Name\n";
            }

            String lastName = lastNameTextField.getText();
            if (lastName.length() > 0) {
                student.setLastname(lastName);
            } else {
                missingComponents += "Last Name\n";
            }

            try {
                String id = studentIdTextField.getText();
                if (id.length() > 0) {
                    student.setStudentId(id);
                } else {
                    missingComponents += "Student Number\n";
                }
            } catch (Student.InvalidStudentIDException ex) {
                invalidComponents += "Student Number\n";
            }

            try {
                String food = (String) foodChoiceComboBox.getSelectedItem();
                if (food != null && food.length() > 0) {
                    student.setFood(food);
                } else {
                    missingComponents += "FoodChoice\n";
                }
            } catch (Student.InvalidFoodException ex) {
                invalidComponents += "Food Choice\n";
            }

            if (formSubmittedCheckBox.isSelected())
                student.setFormSubmitted(true);
            else
                student.setFormSubmitted(false);

            if (hasGuestCheckBox.isSelected())
                student.setGuest(true);
            else
                student.setGuest(false);

            String initials = initialsTextField.getText();
            if (initials.length() > 0)
                student.setInitials(initials);
            else
                missingComponents += "Your Initials\n";

            String phoneNum = phoneNumTextField.getText();
            //Remove any spacing, brackets or dashes from the phone number entered
            phoneNum = phoneNum.replace("(", "").replace(")", "").replace("-", "").replace(" ", "").trim();
            //Validate the phone number
            if(phoneNum.length() == 0)
            {
                missingComponents += "Phone Number\n";
            }
            else if(phoneNum.length() != 10) {
                invalidComponents += "Phone Number\n";
            }
            else {
                student.setPhoneNum(phoneNum);
            }

            // These are not necessary so there is no need to check for validity
            student.setPaidBy(paidByTextField.getText());
            if (student.getPaidBy().equals(""))
                student.setPaid(false);
            else
                student.setPaid(true);

            try
            {
                int tableNum = Integer.parseInt((String) tableNumComboBox.getSelectedItem());
                // Table isn't full, add
                if (!Table.getTable(tableNum - 1).isFull())
                    student.setTableNum(tableNum);
                    // Full table
                else
                {
                    invalidComponents += "Table is Full\n";
                }
            }
            catch(NumberFormatException ex)
            {
                //Unassigned table
                student.setTableNum(0);
            }


            // These are not necessary so there is no need to check for validity
            student.setAllergies(allergiesTextArea.getText());
            student.setInfo(moreInfoTextArea.getText());

            boolean valid = true;
            // Ensure the student has at least the minimal information and
            // display a warning if they are errors
            if (missingComponents.length() > 0) {
                valid = false;
                JOptionPane.showMessageDialog(EventPlanner.FRAME, "Missing Entries: \n"
                                + missingComponents + "Please go back and correct.",
                        "Missing Entries", JOptionPane.ERROR_MESSAGE);
            }
            if (invalidComponents.length() > 0) {
                valid = false;
                JOptionPane.showMessageDialog(EventPlanner.FRAME, "Invalid Entries: \n"
                                + invalidComponents + "Please go back and correct.",
                        "Invalid Entries", JOptionPane.ERROR_MESSAGE);
            }

           	// Let the user confirm only if the info entered is valid
			if (valid) {
				EventPlanner.setPanel(EventPlanner.Panel.DISPLAY_STUDENT);
			}
        }
    }
}
