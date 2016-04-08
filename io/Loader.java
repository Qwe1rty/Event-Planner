package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import data.DoublyLinkedList;
import data.Settings;

/**
 * A class that loads the data from the .event files saved by the program
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
	 * @param path the path to load the file from
	 * @return null if an error occurred while loading. If the load was
	 *         successful, returns a linked list with the student data in it.
	 */
	public DoublyLinkedList<Student> load(String path)
	{
		try (BufferedReader reader = new BufferedReader(new FileReader(path)))
		{
			// Read the global data
			Settings.setLocation(reader.readLine());
			Settings.setNumTables(Integer.parseInt(reader.readLine()));
			Settings.setTableSize(Integer.parseInt(reader.readLine()));
			Settings.setTicketCost(Double.parseDouble(reader.readLine()));
			
			int numMealOptions = Integer.parseInt(reader.readLine());
			for (int option = 0; option < numMealOptions; ++option)
			{
				Settings.addMealOption(reader.readLine());
			}
			
			int numStudents = Integer.parseInt(reader.readLine());
			Settings.setNumStudents(numStudents);
			for(int student = 0; student < numStudents; ++student)
			{
			}
			
		}
		catch (FileNotFoundException e)
		{
			return null;
		}
		catch (IOException e)
		{
			return null;
		}
	}
}
