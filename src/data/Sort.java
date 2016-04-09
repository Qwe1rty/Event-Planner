package data;

public class Sort {
	
	public static void sort(LinkedList ll, Parameter p, boolean ascending) {
		int length = ll.size();
		for (int gap = length / 2; gap > 0; gap /= 2) {
			for (int wall = gap; wall < length; wall++) {
				try {
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
	}

}
