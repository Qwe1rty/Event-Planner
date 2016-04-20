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

/**
 * The student panel is a panel in which new students are added
 *
 * @author Matthew Sun, Connor Murphy
 * @version 1.3
 */
// TODO: change the class to be able to be used to add and edit students
public class StudentPanel extends JPanel {
	private final String BACK_BUTTON_TEXT = "Back";
	private final String GUEST_BUTTON_TEXT = "Show Guest";
	private final String FIRST_NAME_LABEL_TEXT = "First Name: ";
	private final String LAST_NAME_LABEL_TEXT = "Last Name: ";
	private final String STUDENT_ID_LABEL_TEXT = "Student Number: ";
	private final String FOOD_CHOICE_LABEL_TEXT = "    Food Choice: ";
	private final String PHONE_NUM_LABEL_TEXT = "Phone Number: ";
	private final String PAID_BY_LABEL_TEXT = "Paid By: ";
	private final String INITIALS_LABEL_TEXT = "Initials: ";
	private final String FORM_SUBMITTED_LABEL_TEXT = "Form Submitted: ";
	private final String TABLE_NUM_LABEL_TEXT = "Table Number: ";
	private final String GUEST_LABEL_TEXT = "Has Guest: ";
	private final String ALLERGIES_LABEL_TEXT = "Allergies";
	private final String MORE_INFO_LABEL_TEXT = "Additional Information";
	private final String CANCEL_BUTTON_TEXT = "Cancel";
	private final String CONFIRM_BUTTON_TEXT = "Confirm";

	private final int TEXT_AREA_ROWS = 8;
	private final int TEXT_AREA_COLS = 35;

	private final int TEXT_FIELD_ROWS = 21;
	private final Font BUTTON_FONT = new Font("Tw Cen MT", Font.BOLD, 22);
	private final Font TEXT_FONT = new Font("Tw Cen MT", Font.BOLD, 28);
	private final Font FIELD_FONT = new Font("Tw Cen MT", Font.PLAIN, 24);

	private final Dimension COMBO_SIZE = new Dimension(340, 30);
	private final Dimension BUTTON_SIZE = new Dimension(108, 50);

	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField studentIdTextField;
	private JComboBox<String> foodChoiceComboBox;
	private JTextField phoneNumTextField;
	private JTextField paidByTextField;
	private JComboBox<String> tableNumComboBox;
	private JTextField initialsTextField;
	private JCheckBox formSubmittedCheckBox;
	private JCheckBox hasGuestCheckBox;
	
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel studentIdLabel;
	private JLabel foodChoiceLabel;
	private JLabel phoneNumLabel;
	private JLabel paidByLabel;
	private JLabel tableNumLabel;
	private JLabel initialsLabel;
	private JLabel formSubmittedLabel;
	private JLabel hasGuestLabel;

	private JLabel allergiesLabel;
	private JLabel moreInfoLabel;

	private JTextArea allergiesTextArea;
	private JTextArea moreInfoTextArea;

	private JButton cancelButton;
	private JButton confirmButton;
	private JButton backButton;

	private Image background;
	private JPanel fieldsPanel;
	private Border textFieldBorder;

	public StudentPanel() {
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

		// Set all field labels and fonts
		firstNameLabel = new JLabel(FIRST_NAME_LABEL_TEXT);
		firstNameLabel.setFont(TEXT_FONT);

		firstNameTextField = new JTextField(TEXT_FIELD_ROWS);
		firstNameTextField.setFont(FIELD_FONT);

		lastNameLabel = new JLabel(LAST_NAME_LABEL_TEXT);
		lastNameLabel.setFont(TEXT_FONT);

		lastNameTextField = new JTextField(TEXT_FIELD_ROWS);
		lastNameTextField.setFont(FIELD_FONT);

		studentIdLabel = new JLabel(STUDENT_ID_LABEL_TEXT);
		studentIdLabel.setFont(TEXT_FONT);

		studentIdTextField = new JTextField(TEXT_FIELD_ROWS);
		studentIdTextField.setFont(FIELD_FONT);

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

		phoneNumLabel = new JLabel(PHONE_NUM_LABEL_TEXT);
		phoneNumLabel.setFont(TEXT_FONT);

		phoneNumTextField = new JTextField(TEXT_FIELD_ROWS);
		phoneNumTextField.setFont(FIELD_FONT);

		paidByLabel = new JLabel(PAID_BY_LABEL_TEXT);
		paidByLabel.setFont(TEXT_FONT);

		paidByTextField = new JTextField(TEXT_FIELD_ROWS);
		paidByTextField.setFont(FIELD_FONT);

		int numTables = Settings.getNumTables();
		String[] tables = new String[numTables + 1];
		try {
			// The unassigned table
			tables[0] = "Unassigned";
			for (int i = 1; i < numTables + 1; ++i) {
				if (!Table.getTable( i - 1).isFull())
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
		
		initialsLabel = new JLabel(INITIALS_LABEL_TEXT);
		initialsLabel.setFont(TEXT_FONT);
		
		initialsTextField = new JTextField(TEXT_FIELD_ROWS);
		initialsTextField.setFont(FIELD_FONT);
		
		formSubmittedLabel = new JLabel(FORM_SUBMITTED_LABEL_TEXT);
		formSubmittedLabel.setFont(TEXT_FONT);
		
		formSubmittedCheckBox = new JCheckBox();
		
		hasGuestLabel = new JLabel (GUEST_LABEL_TEXT);
		hasGuestLabel.setFont(TEXT_FONT);
		
		hasGuestCheckBox = new JCheckBox();
		

		// All label components are right aligned with some vertical spacing
		// between them
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(10, 0, 0, 0);

		// Add all wider components first
		c.gridy = 1;
		fieldsPanel.add(firstNameLabel, c);

		c.gridy ++;
		fieldsPanel.add(studentIdLabel, c);

		c.gridy ++;
		fieldsPanel.add(phoneNumLabel, c);

		c.gridy ++;
		fieldsPanel.add(tableNumLabel, c);
		
		c.gridy ++;
		fieldsPanel.add(formSubmittedLabel, c);

		// Add all smaller components
		// All field components are left aligned
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		fieldsPanel.add(firstNameTextField, c);

		c.anchor = GridBagConstraints.EAST;
		c.gridx ++;
		fieldsPanel.add(lastNameLabel, c);

		c.anchor = GridBagConstraints.WEST;
		c.gridx ++;
		fieldsPanel.add(lastNameTextField, c);

		c.gridx = 2;
		c.gridy ++;
		fieldsPanel.add(studentIdTextField, c);

		c.anchor = GridBagConstraints.EAST;
		c.gridx ++;
		fieldsPanel.add(foodChoiceLabel, c);

		c.anchor = GridBagConstraints.WEST;
		c.gridx ++;
		fieldsPanel.add(foodChoiceComboBox, c);

		c.gridx = 2;
		c.gridy = 3;
		fieldsPanel.add(phoneNumTextField, c);

		c.anchor = GridBagConstraints.EAST;
		c.gridx ++;
		fieldsPanel.add(paidByLabel, c);

		c.anchor = GridBagConstraints.WEST;
		c.gridx ++;
		fieldsPanel.add(paidByTextField, c);
		
		c.gridx = 2;
		c.gridy = 4;
		fieldsPanel.add(tableNumComboBox, c);
		
		c.anchor = GridBagConstraints.EAST;
		c.gridx ++;
		fieldsPanel.add(initialsLabel, c);
		
		c.anchor = GridBagConstraints.WEST;
		c.gridx ++;
		fieldsPanel.add(initialsTextField, c);

		c.gridx = 2;
		c.gridy ++;
		c.insets = new Insets(10, 0, 0, 0);
		fieldsPanel.add(formSubmittedCheckBox, c);
		
		c.anchor = GridBagConstraints.EAST;
		c.gridx ++;
		fieldsPanel.add(hasGuestLabel, c);
		
		c.anchor = GridBagConstraints.WEST;
		c.gridx ++;
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
		confirmButton.addActionListener(new ConfirmButtonActionListener());
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
	 * Call before changing this panel to the main frame. Refreshes the items in
	 * the food drop down box
	 */
	public void refresh() {
		// Remove all the items from the combo box
		while (foodChoiceComboBox.getItemCount() > 0) {
			foodChoiceComboBox.removeItemAt(0);
		}

		LinkedList<String> foodChoices = Food.getMealOptions();
		if (foodChoices != null) {
			// Add all the items the combo box
			for (int i = 0; i < foodChoices.size(); ++i) {
				foodChoiceComboBox.addItem(foodChoices.get(i));
			}
		}
		
		//Remove old data from the table combo box
		while(tableNumComboBox.getItemCount() > 0)
		{
			System.out.println("count " + tableNumComboBox.getItemCount());
			tableNumComboBox.removeItemAt(0);
		}
		//Update the table numbers
		int numTables = Settings.getNumTables();
		String[] tables = new String[numTables + 1];
		try {
			// The unassigned table
			tableNumComboBox.addItem("Unassigned");
			for (int i = 1; i < numTables + 1; ++i) {
				if (!Table.getTable(i - 1).isFull())
					tableNumComboBox.addItem(Integer.toString(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (int n = 0 ; n < Table.listSize() ; n ++)
		{
			if (!Table.getTable(n).isFull())
				System.out.println("TABLE " + (n + 1) + " NOT FULL");
		}
	}

	/**
	 * Clears all the entered data from the last use of the panel
	 */
	private void clearText() {
		firstNameTextField.setText("");
		lastNameTextField.setText("");
		studentIdTextField.setText("");
		phoneNumTextField.setText("");
		paidByTextField.setText("");
		allergiesTextArea.setText("");
		moreInfoTextArea.setText("");
		initialsTextField.setText("");
		formSubmittedCheckBox.setSelected(false);
		hasGuestCheckBox.setSelected(false);
	}

	/**
	 * Goes back to the main screen that displays students when clicked
	 */
	private class BackButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			clearText();
			EventPlanner.setPanel(EventPlanner.Panel.DISPLAY_STUDENT);
		}
	}

	/**
	 * Saves all the entered data into a new Student. Checks to ensure all the
	 * entered data is valid and the minimum information for a student is
	 * provided.
	 */
	private class ConfirmButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Student student = new Student();
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
				try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception ex) {}
				JOptionPane.showMessageDialog(EventPlanner.FRAME, "Missing Entries: \n"
						+ missingComponents + "Please go back and correct.",
						"Missing Entries", JOptionPane.ERROR_MESSAGE);
				try {UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());} catch (Exception ex) {}
			}
			if (invalidComponents.length() > 0) {
				valid = false;
				try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception ex) {}
				JOptionPane.showMessageDialog(EventPlanner.FRAME, "Invalid Entries: \n"
						+ invalidComponents + "Please go back and correct.",
						"Invalid Entries", JOptionPane.ERROR_MESSAGE);
				try {UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());} catch (Exception ex) {}
			}

			// Let the user confirm only if the info entered is valid
			if (valid) {
				Student.addStudent(student);
				clearText();
				EventPlanner.setPanel(EventPlanner.Panel.DISPLAY_STUDENT);
			}
		}
	}

}