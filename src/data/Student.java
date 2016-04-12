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
 * @throws InvalidStudentIDException Will be thrown if student ID contains letters or is not 9 characters long
 * 
 * @author Caleb Choi
 */
public class Student {

	// Fields
	private String ID;
	private String lastname;
	private String firstname;
	private Food food;
	private boolean paid;
	private String paidBy;
	private String allergies;
	private int tableNum; // 0 means unassigned
	private String phoneNum;
	private String info;
	
	// Constructors
	// Fully declared student
	// For optional values, if you want to make them invalid/empty, make then null (or 0 for table #)
	public Student(String ID, String lastname, String firstname,
			String food, boolean paid, String paidBy, String allergies,
			int tableNum, String phoneNum, String info) throws InvalidStudentIDException, InvalidFoodException {
		this(ID, lastname, firstname, food, paid, paidBy);
		if (allergies != null) setAllergies(allergies);
		if (tableNum != 0) setTableNum(tableNum);
		if (phoneNum != null) setPhoneNum(phoneNum);
		if (info != null) setInfo(info);
	}
	// Student with only mandatory fields
	public Student(String studentId, String lastname, String firstname,
			String food, boolean paid, String paidBy) throws InvalidStudentIDException, InvalidFoodException {
		setStudentId(studentId);
		setLastname(lastname);
		setFirstname(firstname);
		setFood(food);
		setPaid(paid);
		setPaidBy(paidBy);
	}
	// Blank student, useful for IO
	public Student() {}

	// Getters
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
	
	// Setters
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

	public class InvalidStudentIDException extends Exception {
		public InvalidStudentIDException(String message) {super(message);}
	}
	
	public class InvalidFoodException extends Exception {
		public InvalidFoodException(String message) {super(message);}
	}
	
}
