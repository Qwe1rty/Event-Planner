package data;

public class Sort {
	
	public static void sort(LinkedList ll, Parameter p, boolean ascending) {
		int length = ll.size();
		for (int gap = length / 2; gap > 0; gap /= 2) {
			for (int wall = gap; wall < length; wall++) {
				try {
					if (p == Parameter.STUDENT_ID) {
						for (int index = wall; index >= gap && ll.get(index).compareTo(ll.get(index - gap)) < 0; index -= gap) {
							
						}
					}
					
				} catch (Exception e) {System.out.println("nope");}
			}
		}
	}

}
