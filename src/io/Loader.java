package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

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

	// Program file extension
	private static final String FILE_EXTENSION = "event";
	
	/**
	 * Loads the students into a list of students from the given file
	 * 
	 * @param path File path
	 * @return null If the load was successful, returns a linked list with students' data.
	 * 		Otherwise, null will be returned
	 */
	public static LinkedList<Student> parseFile(String path) {
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			// Read the global data
			Settings.setLocation(br.readLine());
			Settings.setNumTables(Integer.parseInt(br.readLine()));
			Settings.setTableSize(Integer.parseInt(br.readLine()));
			Settings.setTicketCost(Double.parseDouble(br.readLine()));

			int numMealOptions = Integer.parseInt(br.readLine());
			for (int option = 0; option < numMealOptions; ++option)
				Food.addFood(new Food(br.readLine()));

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
			return Sort.sort(students, Parameter.FIRSTNAME, true);

		}
		catch (FileNotFoundException e) {
			return null;
		}
		catch (IOException e) {
			return null;
		}
	}
}
