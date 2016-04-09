package data;

public class Food {
	
	private String food; 
	
	// Global list of foods
	public static LinkedList<Food> FOODLIST = new LinkedList<Food>();
	
	public Food(String s) {food = s;}
	
	// Add and remove
	public static void addFood(Food f) {FOODLIST.append(f);}
	public static void removeFood(Food f) {FOODLIST.remove(f);}
	
	// Checks whether food exists in list
	public static boolean isValidFood(Food f) {return FOODLIST.indexOf(f) == -1;}

}
