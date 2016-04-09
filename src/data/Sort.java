package data;

/**
 * Allows for sorting of a list of students via a given parameter.
 * Will not work for any other type of object, so it would be wise
 * to avoid doing so.
 * 
 * @author Caleb Choi
 */
public class Sort {
	
	public static LinkedList<Student> sort(LinkedList ll, Parameter p, boolean ascending) {
		int length = ll.size();
		for (int gap = length / 2; gap > 0; gap /= 2) {
			for (int wall = gap; wall < length; wall++) {
				try {
					// Feast your eyes upon the most disgusting code ever written by humankind
					if (p == Parameter.STUDENT_ID) {
						if (ascending) {
							for (int index = wall; index >= gap && (((Student) ll.get(index)).getID().compareTo(((Student) ll.get(index - gap)).getID()) < 0); index -= gap) {
								ll.swap(index - gap, index);
							}
						} else {
							for (int index = wall; index >= gap && (((Student) ll.get(index)).getID().compareTo(((Student) ll.get(index - gap)).getID()) > 0); index -= gap) {
								ll.swap(index - gap, index);
							}
						}
					} else if (p == Parameter.FIRSTNAME) {
						if (ascending) {
							for (int index = wall; index >= gap && (((Student) ll.get(index)).getFirstname().compareTo(((Student) ll.get(index - gap)).getFirstname()) < 0); index -= gap) {
								ll.swap(index - gap, index);
							}
						} else {
							for (int index = wall; index >= gap && (((Student) ll.get(index)).getFirstname().compareTo(((Student) ll.get(index - gap)).getFirstname()) > 0); index -= gap) {
								ll.swap(index - gap, index);
							}
						}
					} else if (p == Parameter.LASTNAME) {
						if (ascending) {
							for (int index = wall; index >= gap && (((Student) ll.get(index)).getLastname().compareTo(((Student) ll.get(index - gap)).getLastname()) < 0); index -= gap) {
								ll.swap(index - gap, index);
							}
						} else {
							for (int index = wall; index >= gap && (((Student) ll.get(index)).getLastname().compareTo(((Student) ll.get(index - gap)).getLastname()) > 0); index -= gap) {
								ll.swap(index - gap, index);
							}
						}
//					} else if (p == Parameter.ALLERGIES) {
//						if (ascending) {
//							for (int index = wall; index >= gap && (((Student) ll.get(index)).getLastname().compareTo(((Student) ll.get(index - gap)).getLastname()) < 0); index -= gap) {
//								ll.swap(index - gap, index);
//							}
//						} else {
//							for (int index = wall; index >= gap && (((Student) ll.get(index)).getLastname().compareTo(((Student) ll.get(index - gap)).getLastname()) > 0); index -= gap) {
//								ll.swap(index - gap, index);
//							}
//						}
//					} else if (p == Parameter.TABLE_NUMBER) {
//						if (ascending) {
//							for (int index = wall; index >= gap && (((Student) ll.get(index)).getTableNum() < ((Student) ll.get(index - gap)).getTableNum()); index -= gap) {
//								ll.swap(index - gap, index);
//							}
//						} else {
//							for (int index = wall; index >= gap && (((Student) ll.get(index)).getTableNum() > ((Student) ll.get(index - gap)).getTableNum()); index -= gap) {
//								ll.swap(index - gap, index);
//							}
//						}
					} else break;
					
				} catch (Exception e) {System.out.println("nope");}
			}
		}
		
		// Returns sorted list
		return ll;
	}

}
