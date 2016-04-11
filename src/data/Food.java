package data;

/**
 * Stores and keeps track of food choices. Stores a list of all available
 * food options and allows for modification of list
 * 
 * @author Caleb Choi
 */
public class Food {
	
	// Name of food
	private String food; 
	
	// Global list of foods
	public static LinkedList<Food> FOODLIST = new LinkedList<Food>();
	
	// Builds a food with name
	public Food(String s) {food = s;}
	
	// Add and remove from available foods
	public static void addFood(Food f) {FOODLIST.append(f);}
	public static void removeFood(Food f) {FOODLIST.remove(f);}

	//Remove all foods from the food list
	public static void removeAll()
	{
		int size = FOODLIST.size();
		for(int i = 0; i < size; ++i)
		{
			FOODLIST.remove(0);
		}
	}
	// Checks whether food exists in list
	public static boolean isValidFood(Food f) {return FOODLIST.indexOf(f) == -1;}
	
	// Gets stringname
	public String toString() {return food;}

	// Gets list of foods in string list form - used for GUI
	public static LinkedList<String> getMealOptions() {
		LinkedList<String> foods = new LinkedList<String>();
		for (int i = 0; i < FOODLIST.size(); i++) {
			try {foods.append(FOODLIST.get(i).toString());} catch (Exception e) {}
		}
		return foods;
	}

}
