package data;

/**
 * Allows for parameterized search through a list of students 
 * 
 * @author Caleb Choi
 */
public class Search {
	
	/**
	 * A search function that will return matched students in a new list,
	 * given a full list of students. Ordering of the new list is the same
	 * as the original.
	 * 
	 * @param origList Full list of students
	 * @param param Parameter to search students by
	 * @param search The user's search term to match by
	 * @return newList New matched list of students
	 */
	public static LinkedList<Student> search(LinkedList<Student> origList, Parameter param, String search) {
		
		// New list of students with only matched items
		LinkedList<Student> newList = new LinkedList<Student>();
		
		// Through full list of students
		for (int i = 0; i < origList.size(); i++) {
			
			// Filters by parameter. If search string matches, it's added to new list
			if (param == Parameter.STUDENT_ID) {
				if (origList.get(i).getID().contains(search)) newList.append(origList.get(i));
			} else if (param == Parameter.FIRSTNAME) {
				if (origList.get(i).getFirstname().contains(search)) newList.append(origList.get(i));
			} else if (param == Parameter.LASTNAME) {
				if (origList.get(i).getLastname().contains(search)) newList.append(origList.get(i));
			} else if (param == Parameter.ALLERGIES) {
				if (origList.get(i).getAllergies().contains(search)) newList.append(origList.get(i));
			} else if (param == Parameter.FOODTYPE) {
				if (origList.get(i).getFood().toString().contains(search)) newList.append(origList.get(i));
			} else if (param == Parameter.TABLE_NUMBER) {
				if (String.valueOf(origList.get(i).getTableNum()).contains(search)) newList.append(origList.get(i));
			}
			
		}
		
		// Returns new list
		return newList;
	}
}
