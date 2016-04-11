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
	// Checks whether food exists in list
	public static boolean isValidFood(Food f) {return FOODLIST.indexOf(f) == -1;}
	
	/**
	 * Gets stringname of a food
	 * @return stringname of food 
	 */
	public String toString() {return food;}

	/**
	 * Converts the global food list into type String 
	 * @return a LinkedList<String> with foods converted to type String 
	 */
	// Gets list of foods in string list form - used for GUI
	public static LinkedList<String> getMealOptions() {
		LinkedList<String> foods = new LinkedList<String>();
		for (int i = 0; i < FOODLIST.size(); i++) {
			try {foods.append(FOODLIST.get(i).toString());} catch (Exception e) {}
		}
		return foods;
	}
	
	/**
	 * Clears all items in the global food list
	 * @author Caleb Choi
	 */
	public static void clearList() {FOODLIST = new LinkedList<Food>();}

}
