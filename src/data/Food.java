package data;

/**
 * Stores and keeps track of food choices. Stores a global list of all available
 * food options. Individual food instances also represent a single food option
 * 
 * @author Caleb Choi
 */
public class Food {

	// *** FIELDS ***
	// Name of food
	private String food; 
	// Global list of foods
	private static LinkedList<Food> FOODLIST = new LinkedList<Food>();
	
	// *** CONSTRUCTOR ***
	/**
	 * Builds a food object, with the name of the food
	 * @param s
	 */
	public Food(String s) {food = s;}
	
	// *** STATIC FUNCTIONS ***
	// Add and remove from available foods
	/**
	 * Adds a food type to the global food list
	 * @param food Food type to be added
	 */
	public static void appendFood(Food food) {FOODLIST.append(food);}
	
	/**
	 * Removes a food from the global food list
	 * @param food A food type to be removed
	 * @return A boolean representing whether the operation was successful
	 */
	public static boolean removeFood(Food food) {
		for (int i = 0; i < FOODLIST.size(); i++) {
			if (FOODLIST.get(i).toString().equals(food.toString())) {
				FOODLIST.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes a food from the global food list
	 * @param name Name of the food type to be removed
	 * @return A boolean representing whether the operation was successful
	 */
	public static boolean removeFood(String name) {
		for (int i = 0; i < FOODLIST.size(); i++) {
			if (FOODLIST.get(i).toString().equals(name)) {
				FOODLIST.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the index of a type of food. -1 means it isn't found
	 * @param name Name of the food type to be indexed
	 * @return The index of the first instance of the food
	 */
	public static int indexOf(String name) {
		// This is going to have to be manually iterated
		for (int i = 0; i < FOODLIST.size(); i++) {
			if (FOODLIST.get(i).toString().equals(name)) return i;
		}
		return -1;
	}
	/**
	 * Gets the index of a type of food. -1 means it isn't found
	 * @param food The food type to be indexed
	 * @return The index of the first instance of the food
	 */
	public static int indexOf(Food food) {
		// This is going to have to be manually iterated
		for (int i = 0; i < FOODLIST.size(); i++) {
			if (FOODLIST.get(i).toString().equals(food.toString())) return i;
		}
		return -1;
	}
	
	/**
	 * Insert a food index by item. Indexes start at 0
	 * @param index Index of the food in the list
	 * @param food The food to be inserted
	 */
	public static void insert(int index, Food food) {FOODLIST.insert(index, food);}
	
	/**
	 * Get an item by index in the global food list
	 * @param index Index of the food in the list
	 * @return The selected food
	 */
	public static Food get(int index) {return FOODLIST.get(index);}
	
	/**
	 * Fully clear all foods from the food list
	 */
	public static void removeAll() {FOODLIST.clear();}

	/**
	 * Checks whether food exists in list
	 * @param food Food to be checked
	 * @return A boolean representing whether the food is a valid food option
	 */
	public static boolean isValidFood(Food food) {return FOODLIST.indexOf(food) == -1;}
	
	/**
	 * Returns the total size of the food list
	 * @return The total size of the food list
	 */
	public static int listSize() {return FOODLIST.size();}
	
	
	// *** INSTANCE FUNCTIONS ***
	/**
	 * Gets stringname of a food
	 * @return stringname of food 
	 */
	public String toString() {return food;}

	/**
	 * Converts the global food list into type String 
	 * @return a LinkedList<String> with foods converted to type String 
	 */
	public static LinkedList<String> getMealOptions() {
		LinkedList<String> foods = new LinkedList<String>();
		for (int i = 0; i < FOODLIST.size(); i++)
			try {foods.append(FOODLIST.get(i).toString());} catch (Exception e) {}
		return foods;
	}
	/**
	 * Clears all items in the global food list
	 */
	public static void clearList() {FOODLIST = new LinkedList<Food>();}
	
}
