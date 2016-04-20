package data;

/**
 * This class holds all the global settings throughout the program
 * 
 * @author Connor Murphy
 */
public final class Settings
{
	private static String location;
	private static int numTables;
	private static int tableSize;
	private static double ticketCost;
	private static int numStudents;
	
	private Settings(){}
	
	
	public static String getLocation()
	{
		return location;
	}


	public static void setLocation(String location)
	{
		Settings.location = location;
	}


	public static int getNumTables()
	{
		return numTables;
	}

	public static void setNumTables(int numTables)
	{
		Settings.numTables = numTables;
		Table.setLimit(numTables);
	}

	public static int getTableSize()
	{
		return tableSize;
	}

	public static void setTableSize(int tableSize)
	{
		Settings.tableSize = tableSize;
	}

	public static double getTicketCost()
	{
		return ticketCost;
	}

	public static void setTicketCost(double ticketCost)
	{
		Settings.ticketCost = ticketCost;
	}

	public static int getNumStudents()
	{
		return numStudents;
	}

	public static void setNumStudents(int numStudents)
	{
		Settings.numStudents = numStudents;
	}
	
	
}
