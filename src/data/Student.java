package data;

/**
 * Stores all necessary data for a student
 *
 * @param ID Stores the YRDSB student ID
 * @param lastname Stores lastname
 * @param firstname Stores firstname
 * @param food Stores person's food option
 * @param paid Stores whether person actually paid yet
 * @param paidBy Stores who paid ticket
 * @param allergies Stores the person's allergies
 * @param tableNum Stores the person's selected table
 * @param phoneNum Stores the person's phone number
 * @param info Stores extra information for a person
 * @throws InvalidStudentIDException Will be thrown if student ID contains letters or is not 9 characters long
 * 
 * @author Caleb Choi
 */
public class Student {

	// *** Fields ***
	// Global list of students
	private static LinkedList<Student> STUDENT_LIST = new LinkedList<Student>();
	// Instance fields
	private String ID;
	private String lastname;
	private String firstname;
	private Food food;
	private boolean paid;
	private String paidBy;
	private boolean formSubmitted;
	private String allergies; // -o
	private int tableNum; // -o; 0 means unassigned
	private String phoneNum; // -o
	private String info; // -o

	// *** Constructors ***
	// Fully declared student
	// For optional values, if you want to make them invalid/empty, make then null (or 0 for table #)
	public Student(String ID, String firstname, String lastname,
			String food, boolean paid, String paidBy, boolean formSubmitted, String allergies,
			int tableNum, String phoneNum, String info) throws InvalidStudentIDException, InvalidFoodException {
		this(ID, firstname, lastname, food, paid, paidBy, formSubmitted);
		if (allergies != null) setAllergies(allergies);
		setTableNum(tableNum);
		if (phoneNum != null) setPhoneNum(phoneNum);
		if (info != null) setInfo(info);

		// Automatically assigns new student to table if valid
		if (tableNum != 0) Table.addStudent(tableNum, this);
	}
	// Student with only mandatory fields
	public Student(String studentId, String firstname, String lastname,
			String food, boolean paid, String paidBy, boolean formSubmitted) throws InvalidStudentIDException, InvalidFoodException {
		if (studentId == null) studentId = "000000000";
		else setStudentId(studentId);
		setFirstname(firstname);
		setLastname(lastname);
		setFood(food);
		setPaid(paid);
		setPaidBy(paidBy);
		setFormSubmitted(formSubmitted);
	}
	// Constructor for guests
	public Student(String firstname, String lastname, String food, 
			boolean paid, String paidBy, boolean formSubmitted) throws InvalidFoodException {
		setFirstname(firstname);
		setLastname(lastname);
		setFood(food);
		setPaid(paid);
		setPaidBy(paidBy);
		setFormSubmitted(formSubmitted);
	}
	// Blank student, useful for IO
	public Student() {}

	// *** Static functions ***
	// Wrapper functions for LinkedList
	public static void addStudent(Student s) {STUDENT_LIST.append(s);}
	public static void removeStudent(Student s) {STUDENT_LIST.remove(s);}
	public static Student getStudent(int index) {return STUDENT_LIST.get(index);}
	public static int listSize() {return STUDENT_LIST.size();}
	/**
	 * Sorts the global list of students by a given parameter. Ordering can be
	 * selected. Sorting method is shellsort, with a gap of 1/2^k where k is the number
	 * of iterations through the list. It is worth noting that not all parameters
	 * are searchable.
	 * 
	 * @param param Parameter to sort the students by
	 * @param ascending Determines sorting order. If true, order is ascending
	 * @author Caleb Choi
	 */
	public static void sort(Parameter param, boolean ascending) {
		int length = STUDENT_LIST.size();
		for (int gap = length / 2; gap > 0; gap /= 2) {
			for (int wall = gap; wall < length; wall++) {
				try {
					
					// Feast your eyes upon the most disgusting code ever written by mankind
					if (param == Parameter.STUDENT_ID) { // Search by student ID
						if (ascending) for (int index = wall; index >= gap && ((STUDENT_LIST.get(index)).getID().compareTo((STUDENT_LIST.get(index - gap)).getID()) < 0); index -= gap)
							STUDENT_LIST.swap(index - gap, index);
						else for (int index = wall; index >= gap && ((STUDENT_LIST.get(index)).getID().compareTo((STUDENT_LIST.get(index - gap)).getID()) > 0); index -= gap)
							STUDENT_LIST.swap(index - gap, index);
						
					} else if (param == Parameter.FIRSTNAME) { // Search by first names
						if (ascending) for (int index = wall; index >= gap && ((STUDENT_LIST.get(index)).getFirstname().compareTo((STUDENT_LIST.get(index - gap)).getFirstname()) < 0); index -= gap)
							STUDENT_LIST.swap(index - gap, index);
						else for (int index = wall; index >= gap && ((STUDENT_LIST.get(index)).getFirstname().compareTo((STUDENT_LIST.get(index - gap)).getFirstname()) > 0); index -= gap)
							STUDENT_LIST.swap(index - gap, index);
						
					} else if (param == Parameter.LASTNAME) { // Search by last names
						if (ascending) for (int index = wall; index >= gap && ((STUDENT_LIST.get(index)).getLastname().compareTo((STUDENT_LIST.get(index - gap)).getLastname()) < 0); index -= gap)
							STUDENT_LIST.swap(index - gap, index);
						else for (int index = wall; index >= gap && ((STUDENT_LIST.get(index)).getLastname().compareTo((STUDENT_LIST.get(index - gap)).getLastname()) > 0); index -= gap)
							STUDENT_LIST.swap(index - gap, index);
						
					} else if (param == Parameter.PAID) { // Search by whether they paid or not
						if (ascending) for (int index = wall; index >= gap && (((Boolean) (STUDENT_LIST.get(index)).isPaid()).compareTo((STUDENT_LIST.get(index - gap)).isPaid()) < 0); index -= gap)
							STUDENT_LIST.swap(index - gap, index);
						else for (int index = wall; index >= gap && (((Boolean) (STUDENT_LIST.get(index)).isPaid()).compareTo((STUDENT_LIST.get(index - gap)).isPaid()) > 0); index -= gap)
							STUDENT_LIST.swap(index - gap, index);
						
					} else return; // Otherwise don't do any sorting

				} catch (Exception e) {System.out.println("nope");}
			}
		}
	}
	/**
	 * A search function that will return matched students in a new list,
	 * given a full list of students. Ordering of the new list is the same
	 * as the original.
	 * 
	 * @param param Parameter to search students by. To search all categories, put null
	 * @param keyword The user's search term to match by
	 * @return newList New matched list of students
	 * @author Caleb Choi
	 */
	public static LinkedList<Student> search(Parameter param, String keyword) {

		// New list of students with only matched items
		LinkedList<Student> newList = new LinkedList<Student>();

		// Makes the searchkey all lowercase
		String search = keyword.toLowerCase();

		// Through full list of students
		for (int i = 0; i < STUDENT_LIST.size(); i++)

			// Filters by parameter. If search string matches, it's added to new list
			if (param == Parameter.STUDENT_ID || param == null) // Search by student ID 
				if (STUDENT_LIST.get(i).getID() != null && STUDENT_LIST.get(i).getID().toLowerCase().contains(search)) 
					newList.append(STUDENT_LIST.get(i));
		
			else if (param == Parameter.FIRSTNAME || param == null) // Search by student firstnames
				if (STUDENT_LIST.get(i).getFirstname().toLowerCase().contains(search)) 
					newList.append(STUDENT_LIST.get(i));
		
			else if (param == Parameter.LASTNAME || param == null) // Search by student lastnames
				if (STUDENT_LIST.get(i).getLastname().toLowerCase().contains(search)) 
					newList.append(STUDENT_LIST.get(i));
		
			else if (param == Parameter.ALLERGIES || param == null) // Search by student allergies
				if (STUDENT_LIST.get(i).getAllergies() != null && STUDENT_LIST.get(i).getAllergies().toLowerCase().contains(search)) 
					newList.append(STUDENT_LIST.get(i));
		
			else if (param == Parameter.FOODTYPE || param == null) // Search by student foodtypes
				if (STUDENT_LIST.get(i).getFood().toString().toLowerCase().contains(search)) 
					newList.append(STUDENT_LIST.get(i));
		
			else if (param == Parameter.TABLE_NUMBER || param == null) // Search by student table numbers
				if (STUDENT_LIST.get(i).getTableNum() != 0 && String.valueOf(STUDENT_LIST.get(i).getTableNum()).toLowerCase().contains(search)) 
					newList.append(STUDENT_LIST.get(i));

		// Returns new list
		return newList;
	}

	// *** Getters ***
	public String getID() {return ID;}
	public String getLastname() {return lastname;}
	public String getFirstname() {return firstname;}
	public Food getFood() {return food;}
	public boolean isPaid() {return paid;}
	public String getPaidBy() {return paidBy;}
	public String getAllergies() {return allergies;}
	public int getTableNum() {return tableNum;}
	public String getPhoneNum() {return phoneNum;}
	public String getInfo() {return info;}
	public boolean isFormSubmitted() {return formSubmitted;}

	// *** Setters ***
	public void setStudentId(String studentId) throws InvalidStudentIDException {
		if (studentId.length() != 9) throw new InvalidStudentIDException("Length of ID is not equal to 9");
		else if (studentId.matches("\\D")) throw new InvalidStudentIDException("ID cannot contain characters");
		else this.ID = studentId;
	}
	public void setLastname(String lastname) {this.lastname = lastname.trim();}
	public void setFirstname(String firstname) {this.firstname = firstname.trim();}
	public void setFood(String food) throws InvalidFoodException {
		if (Food.isValidFood(new Food(food))) this.food = new Food(food.trim());
		else throw new InvalidFoodException("That type of food does not exist");
	}
	public void setPaid(boolean paid) {this.paid = paid;}
	public void setPaidBy(String paidBy) {this.paidBy = paidBy.trim();}
	public void setAllergies(String allergies) {this.allergies = allergies.trim();}
	public void setTableNum(int tableNum) {this.tableNum = tableNum;}
	public void setPhoneNum(String phoneNum) {this.phoneNum = phoneNum;}
	public void setInfo(String info) {this.info = info;}
	public void setFormSubmitted(boolean formSubmitted) {this.formSubmitted = formSubmitted;}

	// *** Exceptions ***
	public class InvalidStudentIDException extends Exception {
		public InvalidStudentIDException(String message) {super(message);}
	}
	public class InvalidFoodException extends Exception {
		public InvalidFoodException(String message) {super(message);}
	}

}
