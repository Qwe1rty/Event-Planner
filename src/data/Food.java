package data;

/**
 * Stores and keeps track of food choices. Stores a list of all available
 * food options and allows for modification of list
 * 
 * @author Caleb Choi
 */
public class Food {

	// *** Fields ***
	// Name of food
	private String food; 
	// Global list of foods
	private static LinkedList<Food> FOODLIST = new LinkedList<Food>();
	
	// *** Constructor ***
	// Builds a food with name
	public Food(String s) {food = s;}
	
	// *** Static functions ***
	// Add and remove from available foods
	public static void appendFood(Food f) {FOODLIST.append(f);}
	public static boolean removeFood(Food f) {
		for (int i = 0; i < FOODLIST.size(); i++) {
			if (FOODLIST.get(i).toString().equals(f.toString())) {
				FOODLIST.remove(i);
				return true;
			}
		}
		return false;
	}
	public static boolean removeFood(String s) {
		for (int i = 0; i < FOODLIST.size(); i++) {
			if (FOODLIST.get(i).toString().equals(s)) {
				FOODLIST.remove(i);
				return true;
			}
		}
		return false;
	}
	// Gets the index of a type of food. -1 means it isn't found
	public static int indexOf(String s) {
		// This is going to have to be manually iterated
		for (int i = 0; i < FOODLIST.size(); i++) {
			if (FOODLIST.get(i).toString().equals(s)) return i;
		}
		return -1;
	}
	public static int indexOf(Food f) {
		// This is going to have to be manually iterated
		for (int i = 0; i < FOODLIST.size(); i++) {
			if (FOODLIST.get(i).toString().equals(f.toString())) return i;
		}
		return -1;
	}
	// Insert a food index by item. Indexes start at 0
	public static void insert(int index, Food f) {FOODLIST.insert(index, f);}
	// Get an item by index
	public static Food get(int index) {return FOODLIST.get(index);}
	// Remove all foods from the food list
	public static void removeAll() {FOODLIST.clear();}
	// Checks whether food exists in list
	public static boolean isValidFood(Food f) {return FOODLIST.indexOf(f) == -1;}
	// Return size of foodlist
	public static int listSize() {return FOODLIST.size();}
	
	
	// *** Instance functions ***
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
		for (int i = 0; i < FOODLIST.size(); i++)
			try {foods.append(FOODLIST.get(i).toString());} catch (Exception e) {}
		return foods;
	}
	/**
	 * Clears all items in the global food list
	 * @author Caleb Choi
	 */
	public static void clearList() {FOODLIST = new LinkedList<Food>();}

}
