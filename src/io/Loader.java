package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import data.Food;
import data.LinkedList;
import data.Settings;
import data.Student;

/**
 * A class that loads the data from the .event files saved by the program
 * @author Caleb Choi
 * @author Connor Murphy
 * @version 1.0
 *
 */
public final class Loader
{
	private Loader()
	{
	}

	/**
	 * Loads the students into a list of students from the given file
	 * 
	 * @param path The path to load the file from
	 * @return null If the load was successful, returns a linked list with the student data in it.
	 * 		Otherwise, null will be returned
	 */
	public LinkedList<Student> load(String path) {
		
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
			
			for(int student = 0; student < Settings.getNumStudents(); ++student) {
				
			}

		}
		catch (FileNotFoundException e) {
			return null;
		}
		catch (IOException e) {
			return null;
		}
		return null;
	}
}
