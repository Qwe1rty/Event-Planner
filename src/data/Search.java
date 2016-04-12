package data;

/**
 * Allows for parameterized search through a list of students 
 * 
 * @author Caleb Choi
 */
public class Search {
	
	public static LinkedList<Student> search(LinkedList<Student> ll, Parameter p, String s) {
		
		// New list of students with only matched items
		LinkedList<Student> lll = new LinkedList<Student>();
		
		// Through full list of students
		for (int i = 0; i < ll.size(); i++) {
			
			// Filters by parameter. If search string matches, it's added to new list
			if (p == Parameter.STUDENT_ID) {
				if (ll.get(i).getID().contains(s)) lll.append(ll.get(i));
			} else if (p == Parameter.FIRSTNAME) {
				if (ll.get(i).getFirstname().contains(s)) lll.append(ll.get(i));
			} else if (p == Parameter.LASTNAME) {
				if (ll.get(i).getLastname().contains(s)) lll.append(ll.get(i));
			} else if (p == Parameter.ALLERGIES) {
				if (ll.get(i).getAllergies().contains(s)) lll.append(ll.get(i));
			} else if (p == Parameter.FOODTYPE) {
				if (ll.get(i).getFood().toString().contains(s)) lll.append(ll.get(i));
			} else if (p == Parameter.TABLE_NUMBER) {
				if (String.valueOf(ll.get(i).getTableNum()).contains(s)) lll.append(ll.get(i));
			}
			
		}
		
		return ll;
	}
}
