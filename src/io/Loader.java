package io;

import gui.EventPlanner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import data.Food;
import data.LinkedList;
import data.Parameter;
import data.Settings;
import data.Sort;
import data.Student;
import data.Student.InvalidFoodException;
import data.Student.InvalidStudentIDException;

/**
 * A class that loads the data from the .event files saved by the program
 * @author Caleb Choi
 * @author Connor Murphy
 * @version 1.0
 *
 */
public final class Loader {

	// Program file extension and filter
	private static final String FILE_EXTENSION = "event";
	private static final FileNameExtensionFilter FILE_FILTER = new FileNameExtensionFilter("Custom extension only", FILE_EXTENSION);

	/**
	 * Loads the students into a list of students from the given file. File chooser dialog box
	 * will appear and user can choose the file to load.
	 * 
	 * @return null If the load was successful, returns a linked list with students' data.
	 * 		Otherwise, null will be returned
	 * @throws FileNotFoundException 
	 * @author Caleb Choi
	 */
	public static void parseFile() throws FileNotFoundException {

		// Shows file selection dialog for user, and keeps selected file 
		JFileChooser fc = initializeFileChooser();
		int selection = fc.showOpenDialog(EventPlanner.FRAME);
		if (selection != JFileChooser.APPROVE_OPTION) return;

		// Creates input stream
		try (BufferedReader br = new BufferedReader(new FileReader(fc.getSelectedFile()))) {

			// Read the global data
			Settings.setLocation(br.readLine());
			Settings.setNumTables(Integer.parseInt(br.readLine()));
			Settings.setTableSize(Integer.parseInt(br.readLine()));
			Settings.setTicketCost(Double.parseDouble(br.readLine()));

			// Foods
			int numMealOptions = Integer.parseInt(br.readLine());
			for (int option = 0; option < numMealOptions; ++option)
				Food.appendFood(new Food(br.readLine()));

			Settings.setNumStudents(Integer.parseInt(br.readLine()));
			LinkedList<Student> students = new LinkedList<Student>();
			for(int i = 0; i < Settings.getNumStudents(); ++i) {

				StringTokenizer st = new StringTokenizer(br.readLine(), ",");
				Student s = new Student();

				// ID
				try {s.setStudentId(st.nextToken());
				} catch (InvalidStudentIDException e) {
					try {s.setStudentId("000000000");} catch (Exception e1) {}
				}
				// Firstname
				s.setFirstname(st.nextToken());
				// Lastname
				s.setLastname(st.nextToken());
				// Food
				try {s.setFood(st.nextToken());
				} catch (InvalidFoodException e) {
					try {s.setFood(null);} catch (Exception e1) {}
				}
				// Paid?
				boolean paid = st.nextToken().equals("true");
				s.setPaid(paid);
				// Paid by
				s.setPaidBy(st.nextToken());
				// Form submitted
				boolean form = st.nextToken().equals("true");
				s.setFormSubmitted(form);

				// Allergies and table number
				while (st.hasMoreTokens()) {
					String nextToken = st.nextToken();
					if (nextToken.indexOf("A") == 0) s.setAllergies(nextToken);
					else if (nextToken.indexOf("T") == 0) s.setTableNum(Integer.parseInt(nextToken));
				}

				// add student to list
				students.append(s);
			}

			// Returns list of students. Default sorting order is by Firstname
			Student.sort(Parameter.FIRSTNAME, true);
		}

		catch (FileNotFoundException e) {return;}
		catch (IOException e) {return;}
	}

	/**
	 * Will save the current project file to the user's selected directory
	 * 
	 * @param LinkedList<Student> a linked list full of students. Function will also read
	 * 		from Settings.java for global settings
	 * @author Caleb Choi
	 */
	public static void saveFile(LinkedList<Student> ll) {

		// Shows file selection dialog for user, and keeps selected file 
		JFileChooser fc = initializeFileChooser();
		int selection = fc.showOpenDialog(EventPlanner.FRAME);
		if (selection != JFileChooser.APPROVE_OPTION) return;

		try {

			// Gets the path of the chosen file. Checks if file extension needs to be added
			String fileName = fc.getSelectedFile().getAbsolutePath();
			if (fileName.indexOf(".") != -1) {
				fileName = fileName.substring(0, fileName.indexOf(".")) + FILE_EXTENSION;
			} else fileName += "." + FILE_EXTENSION;

			// Checks if file exists. If so, it is overriden
			File file = new File(fileName);
			if (file.exists()) {file.delete();}
			file.createNewFile();

			// Creates output stream
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));

			// Prints global data
			bw.write(Settings.getLocation()); bw.newLine();
			bw.write(Settings.getNumTables()); bw.newLine();
			bw.write(Settings.getTableSize()); bw.newLine();
			bw.write(String.valueOf(Settings.getTicketCost())); bw.newLine();

			// Prints all foods
			bw.write(Food.listSize()); bw.newLine();
			for (int i = 0; i < Food.listSize(); i++) {
				try {
					bw.write(Food.get(i).toString());
					bw.newLine();
				} catch (Exception e) {}
			}

			// Student stuff
			bw.write(Settings.getNumStudents()); bw.newLine();

			for (int i = 0; i < ll.size(); i++) {
				String student = "";

				student += ll.get(i).getID() + ",";
				student += ll.get(i).getFirstname() + ",";
				student += ll.get(i).getLastname() + ",";
				student += ll.get(i).getFood().toString() + ",";
				student += ll.get(i).getPaidBy() + ",";
				student += ll.get(i).isPaid() + ",";
				student += ll.get(i).getPaidBy();
				student += ll.get(i).isFormSubmitted();
				
				if (ll.get(i).getAllergies() != null) {
					student += ",A" + ll.get(i).getAllergies();
				}
				if (ll.get(i).getTableNum() != 0) {
					student += ",T" + ll.get(i).getTableNum();
				}
				
				bw.write(student);
				bw.newLine();

			}

			// Close stream
			bw.close();

		} catch (IOException ioe) {

		}

	}

	/**
	 * Creates a customized file chooser
	 * 
	 * @return a JFileChooser with filter and selection mode set up
	 * @author Caleb Choi
	 */
	private static JFileChooser initializeFileChooser() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(FILE_FILTER);
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		return fc;
	}

}
