package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import data.Food;
import data.LinkedList;
import data.Settings;
import data.Student;
import data.Student.InvalidFoodException;
import data.Student.InvalidStudentIDException;

/**
 * Gives a in depth visual display of the student
 *
 * @author Connor Murphy
 */
public class StudentProfile extends JPanel {

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
	private static final String CANCEL_BUTTON_TEXT = "Cancel";
	private static final String CONFIRM_BUTTON_TEXT = "Confirm";

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

	private JButton cancelButton;
	private JButton confirmButton;
	private JButton backButton;

	private Image background;
	private JPanel fieldsPanel;
	private Border textFieldBorder;

	private Student student;

	public StudentProfile(Student student) {
		this.student = student;

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

		int numTables = Settings.getNumTables();
		Integer[] tables = new Integer[numTables];
		try {
			for (int i = 0; i < numTables; ++i) {
				tables[i] = (i + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		tableNumLabel = new JLabel(TABLE_NUM_LABEL_TEXT);
		tableNumLabel.setFont(TEXT_FONT);

		tableNumComboBox = new JComboBox<Integer>(tables);
		tableNumComboBox.setPreferredSize(COMBO_SIZE);
		tableNumComboBox.setFont(FIELD_FONT);
		String tableNum = Integer.toString(student.getTableNum());
		if (tableNum != null) {
			tableNumComboBox.setSelectedItem(tableNum);
		}

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

		c.gridx = 2;
		c.gridy = 4;
		c.gridwidth = 1;
		fieldsPanel.add(tableNumComboBox, c);

		// Titles for the allergies and additional information text areas
		allergiesLabel = new JLabel(ALLERGIES_LABEL_TEXT);
		allergiesLabel.setFont(TEXT_FONT);

		// Position allergy and extra-info components centered, each taking up 2
		// grid spaces
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.CENTER;

		c.gridx = 0;
		c.gridy = 5;
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
		String allergies = student.getAllergies();
		if (allergies != null) {
			allergiesTextArea.setText(allergies);
		}

		c.gridx = 0;
		c.gridy++;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(10, 0, 0, 15);
		fieldsPanel.add(allergiesTextArea, c);

		moreInfoTextArea = new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLS);
		moreInfoTextArea.setFont(FIELD_FONT);
		moreInfoTextArea.setBorder(textFieldBorder);
		String moreInfo = student.getMoreInfo();
		if (moreInfo != null) {
			moreInfoTextArea.setText(moreInfo);
		}

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

	class BackButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Custom button text
			Object[] options = { "Yes", "No" };
			int result = JOptionPane.showOptionDialog(null,
					"Are you sure you want to leave without saving?", "Back",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[1]);
			if (result == JOptionPane.YES_OPTION)
				EventPlanner.setPanel(EventPlanner.Panel.DISPLAY_STUDENT);
		}
	}

	/**
	 * When the user selects that they are finished viewing this students
	 * profile, this class updates the new data for the student
	 */
	class FinishButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				student.setStudentId(studentIdTextField.getText());
			} catch (Student.InvalidStudentIDException ex) {
				// If the id is invalid, do not update it
			}
			try {
				student.setFood((String) foodChoiceComboBox.getSelectedItem());
			} catch (Student.InvalidFoodException ex) {
				// If the food is invalid, do not update it
			}
			try {
				student.setTableNum(Integer.parseInt((String) tableNumComboBox
						.getSelectedItem()));
			} catch (NumberFormatException ex) {
				// If the table number is invalid, do not update it
			}

			student.setLastname(lastNameTextField.getText());
			student.setFirstname(firstNameTextField.getText());

			student.setPaidBy(paidByTextField.getText());
			student.setAllergies(allergiesTextArea.getText());

			EventPlanner.setPanel(EventPlanner.Panel.DISPLAY_STUDENT);
		}
	}
}
