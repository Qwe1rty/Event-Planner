package data;

/**
 * Stores all necessary data for a student
 *
 * @param ID        Stores the YRDSB student ID
 * @param lastname  Stores lastname
 * @param firstname Stores firstname
 * @param food      Stores person's food option
 * @param paid      Stores whether person actually paid yet
 * @param paidBy    Stores who paid ticket
 * @param allergies Stores the person's allergies
 * @param tableNum  Stores the person's selected table
 * @param phoneNum  Stores the person's phone number
 * @param info      Stores extra information for a person
 * @author Caleb Choi
 */
public class Student {

	// *** FIELDS ***
	// Global list of students
	private static LinkedList<Student> STUDENT_LIST = new LinkedList<Student>();

	// Instance fields. -o indicates that it is an optional field
	private String ID;
	private String lastname;
	private String firstname;
	private String initials;
	private Food food;
	private boolean paid;
	private String paidBy;
	private boolean formSubmitted;
	private boolean hasGuest;
	private String allergies; // -o
	private int tableNum; // -o; 0 means unassigned
	private String phoneNum; // -o
	private String info; // -o

	// *** CONSTRUCTORS ***
	/**
	 * A constructor for fully declared students. For optional values, if you want to make them
	 * invalid/empty, set them as null (or 0 for table #)
	 *
	 * @param ID            Student's ID
	 * @param firstname     Student's first name
	 * @param lastname      Student's last name
	 * @param initials      Student's initials
	 * @param food          Student's food choice
	 * @param paid          Student's paid status
	 * @param paidBy        Person who paid student's ticket
	 * @param formSubmitted Student's form submission status
	 * @param allergies     Student's allergies
	 * @param tableNum      Student's table number
	 * @param phoneNum      Student's phone number
	 * @param info          Student's additional information
	 * @param hasGuest 		Student's having guest status
	 * @throws InvalidStudentIDException Thrown when an invalid student ID is detected
	 * @throws InvalidFoodException      Thrown when an invalid food choice is detected
	 */
	public Student(String ID, String firstname, String lastname, String initials,
			Food food, boolean paid, String paidBy, boolean formSubmitted, String allergies,
			int tableNum, String phoneNum, String info, boolean hasGuest) throws InvalidStudentIDException, InvalidFoodException {
		this(ID, firstname, lastname, initials, food, paid, paidBy, formSubmitted, hasGuest);
		if (allergies != null) setAllergies(allergies);
		setTableNum(tableNum);
		if (phoneNum != null) setPhoneNum(phoneNum);
		if (info != null) setInfo(info);
	}

	/**
	 * A constructor for a student with only mandatory fields filled out
	 *
	 * @param ID            Student's ID
	 * @param firstname     Student's first name
	 * @param lastname      Student's last name
	 * @param initials      Student's initials
	 * @param food          Student's food choice
	 * @param paid          Student's paid status
	 * @param paidBy        Person who paid student's ticket
	 * @param formSubmitted Student's form submission status
	 * @param hasGuest 		Student's having guest status
	 * @throws InvalidStudentIDException Thrown when an invalid student ID is detected
	 * @throws InvalidFoodException      Thrown when an invalid food choice is detected
	 */
	public Student(String studentId, String firstname, String lastname, String initials,
			Food food, boolean paid, String paidBy, boolean formSubmitted, boolean hasGuest) throws InvalidStudentIDException, InvalidFoodException {
		this(firstname, lastname, initials, food, paid, paidBy, formSubmitted, hasGuest);
		if (studentId == null) studentId = "000000000";
		else setStudentId(studentId);
	}

	/**
	 * A constructor for prom guests who are not RHHS students. In this constructor, there
	 * lacks a student ID field and does not throw InvalidStudentIDException
	 *
	 * @param firstname     Student's first name
	 * @param lastname      Student's last name
	 * @param initials      Student's initials
	 * @param food          Student's food choice
	 * @param paid          Student's paid status
	 * @param paidBy        Person who paid student's ticket
	 * @param formSubmitted Student's form submission status
	 * @param hasGuest 		Student's having guest status
	 * @throws InvalidFoodException Thrown when an invalid food choice is detected
	 */
	public Student(String firstname, String lastname, String initials, Food food,
			boolean paid, String paidBy, boolean formSubmitted, boolean hasGuest) throws InvalidFoodException {
		setFirstname(firstname);
		setLastname(lastname);
		setInitials(initials);
		setFood(food);
		setPaid(paid);
		setPaidBy(paidBy);
		setFormSubmitted(formSubmitted);
		setGuest(hasGuest);
	}

	/**
	 * Blank student, useful for IO. Should not be used anywhere else
	 */
	public Student() {
	}

	// *** STATIC FUNCTIONS ***
	// Wrapper functions for the global LinkedList

	/**
	 * Appends a student to the global student list
	 *
	 * @param student Student to be added
	 */
	public static void addStudent(Student student) {
		STUDENT_LIST.append(student);
	}

	/**
	 * Removes a student from the global student list, given an exemplar student
	 *
	 * @param student Student to be removed
	 */
	public static void removeStudent(Student student) {
		STUDENT_LIST.remove(student);
	}

	/**
	 * Gets a student from the global student list, given an index in the list
	 *
	 * @param index An integer that represents the position of the desired student in the list
	 * @return The selected student
	 */
	public static Student getStudent(int index) {
		return STUDENT_LIST.get(index);
	}

	/**
	 * Returns the size of the global student list
	 *
	 * @return An integer, representing the size of the global student list
	 */
	public static int listSize() {
		return STUDENT_LIST.size();
	}

	/**
	 * Clears the entire global student list
	 */
	public static void removeAll() {
		STUDENT_LIST.clear();
	}

	/**
	 * Sorts the global list of students by a given parameter. Ordering can be
	 * selected. Sorting method is shellsort, with a gap of n/2^k where k is the number
	 * of iterations through the list. It is worth noting that not all parameters
	 * are searchable.
	 *
	 * @param param     Parameter to sort the students by
	 * @param ascending Determines sorting order. If true, order is ascending
	 * @author Caleb Choi
	 */
	public static void sort(Parameter param, boolean ascending) {

		// Gets the length of the list for use later, as .size() is expensive
		int length = STUDENT_LIST.size();

		// Shellsort algorithm
		for (int gap = length / 2; gap > 0; gap /= 2) {
			for (int wall = gap; wall < length; wall++) {
				try {

					// Feast your eyes upon the most disgusting code ever written by mankind
					if (param == Parameter.STUDENT_ID) { // Sort by student ID
						if (ascending)
							for (int index = wall; index >= gap && ((STUDENT_LIST.get(index)).getID().compareTo((STUDENT_LIST.get(index - gap)).getID()) < 0); index -= gap)
								STUDENT_LIST.swap(index - gap, index);
						else
							for (int index = wall; index >= gap && ((STUDENT_LIST.get(index)).getID().compareTo((STUDENT_LIST.get(index - gap)).getID()) > 0); index -= gap)
								STUDENT_LIST.swap(index - gap, index);

					} else if (param == Parameter.FIRSTNAME) { // Sort by first names
						if (ascending)
							for (int index = wall; index >= gap && ((STUDENT_LIST.get(index)).getFirstname().compareTo((STUDENT_LIST.get(index - gap)).getFirstname()) < 0); index -= gap)
								STUDENT_LIST.swap(index - gap, index);
						else
							for (int index = wall; index >= gap && ((STUDENT_LIST.get(index)).getFirstname().compareTo((STUDENT_LIST.get(index - gap)).getFirstname()) > 0); index -= gap)
								STUDENT_LIST.swap(index - gap, index);

					} else if (param == Parameter.LASTNAME) { // Sort by last names
						if (ascending)
							for (int index = wall; index >= gap && ((STUDENT_LIST.get(index)).getLastname().compareTo((STUDENT_LIST.get(index - gap)).getLastname()) < 0); index -= gap)
								STUDENT_LIST.swap(index - gap, index);
						else
							for (int index = wall; index >= gap && ((STUDENT_LIST.get(index)).getLastname().compareTo((STUDENT_LIST.get(index - gap)).getLastname()) > 0); index -= gap)
								STUDENT_LIST.swap(index - gap, index);

					} else if (param == Parameter.PAID) { // Sort by whether they paid or not
						if (ascending)
							for (int index = wall; index >= gap && (((Boolean) (STUDENT_LIST.get(index)).isPaid()).compareTo((STUDENT_LIST.get(index - gap)).isPaid()) < 0); index -= gap)
								STUDENT_LIST.swap(index - gap, index);
						else
							for (int index = wall; index >= gap && (((Boolean) (STUDENT_LIST.get(index)).isPaid()).compareTo((STUDENT_LIST.get(index - gap)).isPaid()) > 0); index -= gap)
								STUDENT_LIST.swap(index - gap, index);

					} else if (param == Parameter.FOODTYPE) { // Sort by food type
						if (ascending)
							for (int index = wall; index >= gap && ((STUDENT_LIST.get(index)).getFood().toString().compareTo((STUDENT_LIST.get(index - gap)).getFood().toString()) < 0); index -= gap)
								STUDENT_LIST.swap(index - gap, index);
						else
							for (int index = wall; index >= gap && ((STUDENT_LIST.get(index)).getFood().toString().compareTo((STUDENT_LIST.get(index - gap)).getFood().toString()) > 0); index -= gap)
								STUDENT_LIST.swap(index - gap, index);

					} else if (param == Parameter.TABLE_NUMBER) { // Sort by table number
						if (ascending)
							for (int index = wall; index >= gap && ((STUDENT_LIST.get(index)).getTableNum() - ((STUDENT_LIST.get(index - gap)).getTableNum()) > 0); index -= gap)
								STUDENT_LIST.swap(index - gap, index);
						else
							for (int index = wall; index >= gap && ((STUDENT_LIST.get(index)).getTableNum() - ((STUDENT_LIST.get(index - gap)).getTableNum()) < 0); index -= gap)
								STUDENT_LIST.swap(index - gap, index);

					} else return; // Otherwise don't do any sorting and exit method

				} catch (Exception e) {
					System.out.println("nope");
				} // Should never be needed
			}
		}
	}

	/**
	 * A search function that will return matched students in a new list,
	 * given a full list of students. Ordering of the new list is the same
	 * as the original.
	 *
	 * @param param   Parameter to search students by. To search all categories, put null
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
		for (int i = 0; i < STUDENT_LIST.size(); i++) {

			// Stores student
			Student student = STUDENT_LIST.get(i);

			// Filters by parameter. If search string matches, it's added to new list
			if (param == null) { // Search through all parameters
				
				// If it matches ANY of the parameters...
				if ((student.getID() != null && student.getID().toLowerCase().contains(search)) ||
						(student.getFirstname().toLowerCase().contains(search)) ||
						(student.getLastname().toLowerCase().contains(search)) ||
						(student.getAllergies() != null && student.getAllergies().toLowerCase().contains(search)) ||
						(student.getFood().toString().toLowerCase().contains(search)) ||
						(student.getTableNum() != 0 && String.valueOf(student.getTableNum()).contains(search)) ||
						(Boolean.toString(student.isPaid()).contains(search)))
					newList.append(student);

			} else if (param == Parameter.STUDENT_ID) { // Search by student ID
				if (student.getID() != null && student.getID().toLowerCase().contains(search)) {
					newList.append(student);
				}
			} else if (param == Parameter.FIRSTNAME) { // Search by student firstnames
				if (student.getFirstname().toLowerCase().contains(search)) {
					newList.append(student);
				}
			} else if (param == Parameter.LASTNAME) { // Search by student lastnames
				if (student.getLastname().toLowerCase().contains(search)) {
					newList.append(student);
				}
			} else if (param == Parameter.ALLERGIES) { // Search by student allergies
				if (student.getAllergies() != null && student.getAllergies().toLowerCase().contains(search)) {
					newList.append(student);
				}
			} else if (param == Parameter.FOODTYPE) { // Search by student foodtypes
				if (student.getFood().toString().toLowerCase().contains(search)) {
					newList.append(student);
				}
			} else if (param == Parameter.TABLE_NUMBER) { // Search by student table numbers
				if (student.getTableNum() != 0 && String.valueOf(student.getTableNum()).contains(search)) {
					newList.append(student);
				}
			}
			else if (param == Parameter.PAID) { // Search by whether they've paid
				if (Boolean.toString(student.isPaid()).contains(search)) {
					newList.append(student);
				}
			}

		}
		// Returns new list
		return newList;
	}

	// *** GETTERS ***
	/**
	 * Returns the student's ID
	 *
	 * @return Student's ID
	 */
	public String getID() {
		return ID;
	}

	/**
	 * Returns the student's last name
	 *
	 * @return Student's lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Returns the student's first name
	 *
	 * @return Student's firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Returns the student's initials
	 *
	 * @return Student's initials
	 */
	public String getInitials() {
		return initials;
	}

	/**
	 * Returns the student's food choice
	 *
	 * @return Student's food
	 */
	public Food getFood() {
		return food;
	}

	/**
	 * Returns the student's paid status
	 *
	 * @return Whether the student has paid
	 */
	public boolean isPaid() {
		return paid;
	}

	/**
	 * Returns the student's payer
	 *
	 * @return Who paid the ticket
	 */
	public String getPaidBy() {
		return paidBy;
	}

	/**
	 * Returns the student's allergies
	 *
	 * @return Student's allergies
	 */
	public String getAllergies() {
		return allergies;
	}

	/**
	 * Returns the student's table number
	 *
	 * @return Student's table number
	 */
	public int getTableNum() {
		return tableNum;
	}

	/**
	 * Returns the student's phone number
	 *
	 * @return Student's phone number
	 */
	public String getPhoneNum() {
		return phoneNum;
	}

	/**
	 * Returns the student's extra information
	 *
	 * @return Student's extra information
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * Returns the student's form submission status
	 *
	 * @return Whether the student has submitted their forms
	 */
	public boolean isFormSubmitted() {
		return formSubmitted;
	}

	/**
	 * Returns if student has a guest
	 * @return whether a student has a guest
	 */
	public boolean hasGuest () 
	{
		return hasGuest;
	}

	// *** SETTERS ***
	/**
	 * Sets the student's ID
	 *
	 * @param studentId A valid student ID to be set
	 * @throws InvalidStudentIDException Thrown when student's ID is not 9 digits
	 */
	public void setStudentId(String studentId) throws InvalidStudentIDException {
		if (studentId.length() != 9) throw new InvalidStudentIDException("Length of ID is not equal to 9");
		else if (studentId.matches("\\D")) throw new InvalidStudentIDException("ID cannot contain characters");
		else this.ID = studentId;
	}

	/**
	 * Sets the student's last name
	 *
	 * @param lastname The student's last name to be set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname.trim();
	}

	/**
	 * Sets the student's first name
	 *
	 * @param firstname The student's first name to be set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname.trim();
	}

	/**
	 * Sets the student's initials
	 *
	 * @param firstname The student's initials to be set
	 */
	public void setInitials(String initials) {
		this.initials = initials.trim();
	}

	/**
	 * Sets the student's food choice
	 *
	 * @param food The student's food choice to be set, as a Food object
	 * @throws InvalidFoodException Thrown if the food is not a valid choice in the food list
	 */
	public void setFood(Food food) throws InvalidFoodException {
		if (Food.isValidFood(food)) this.food = food;
		else throw new InvalidFoodException("That type of food does not exist");
	}

	/**
	 * Sets the student's food choice
	 *
	 * @param food The student's food choice to be set, as a String
	 * @throws InvalidFoodException Thrown if the food is not a valid choice in the food list
	 */
	public void setFood(String food) throws InvalidFoodException {
		if (Food.isValidFood(new Food(food))) this.food = new Food(food.trim());
		else throw new InvalidFoodException("That type of food does not exist");
	}

	/**
	 * Sets the status of whether the student has paid
	 *
	 * @param paid Boolean representing whether the student has paid
	 */
	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	/**
	 * Sets the person who paid the student's ticket
	 *
	 * @param paidBy
	 */
	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy.trim();
	}


	/**
	 * Sets the person's allergies
	 *
	 * @param allergies The person's allergies to be set
	 */
	public void setAllergies(String allergies) {
		this.allergies = allergies.trim();
	}

	/**
	 * Sets the person's table number
	 *
	 * @param tableNum The person's table number to be set
	 */
	public void setTableNum(int tableNum) {
		this.tableNum = tableNum;
	}

	/**
	 * Sets the person's phone number
	 *
	 * @param phoneNum The person's phone number to be set
	 */
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	/**
	 * Sets the person's additional info
	 *
	 * @param info The person's additional info to be set
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * Sets the status of whether the person has submitted their forms
	 *
	 * @param formSubmitted Boolean representing the person's form submission status
	 */
	public void setFormSubmitted(boolean formSubmitted) {
		this.formSubmitted = formSubmitted;
	}

	/**
	 * Sets whether or not the student has a guest
	 * @param hasGuest a boolean representing whether there's a guest
	 */
	public void setGuest (boolean hasGuest)
	{
		this.hasGuest = hasGuest;
	}

	// *** EXCEPTIONS ***
	/**
	 * An exception that is thrown when an invalid student ID is detected
	 */
	public class InvalidStudentIDException extends Exception {
		public InvalidStudentIDException(String message) {
			super(message);
		}
	}

	/**
	 * An exception that is thrown when an invalid student food type is detected
	 */
	public class InvalidFoodException extends Exception {
		public InvalidFoodException(String message) {
			super(message);
		}
	}

}