package data;

public class Search {
	
	public static LinkedList<Student> search(LinkedList<Student> ll, Parameter p, String s) {
		
		LinkedList<Student> lll = new LinkedList<Student>();
		
		for (int i = 0; i < ll.size(); i++) {
			if (p == Parameter.STUDENT_ID) {
				if (ll.get(i).getID().contains(s)) lll.append(ll.get(i));
			} else if (p == Parameter.FIRSTNAME) {
				if (ll.get(i).getFirstname().contains(s)) lll.append(ll.get(i));
			} else if (p == Parameter.LASTNAME) {
				
			} else if (p == Parameter.ALLERGIES) {
				
//			} else if (p == Parameter.)
		}
		return ll;
		
	}

}
