package data;

/**
 * Tables allow for the management of student seats. Table sizes and 
 * quantity are contained in Settings.java
 * 
 * @author Caleb Choi
 */
public class Table {
	
	// *** Fields ***
	// Global table list
	private static LinkedList<Table> TABLE_LIST = new LinkedList<Table>();
	// Each table holds a list of students, but size is limited by Settings
	private LinkedList<Student> students;
	
	// *** Constructor ***
	public Table() {this.students = new LinkedList<Student>();}
	
	// ** Static methods ***
	// LinkedList wrapper functions for global table listgit
	public static void addTable(Table table) {TABLE_LIST.append(table);}
	public static void removeTable(int index) {TABLE_LIST.remove(index);}
	public static Table getTable(int index) {return TABLE_LIST.get(index);}
	public static int listSize() {return TABLE_LIST.size();}
	/**
	 *  Sets new limit in the table list. If new limit is less than current list,
	 *  tables at bottom of list are truncated. If new limit is more than current
	 *  list, new tables are generated to compensate
	 *  
	 *  @param limit New table list size limit
	 */
	public static void setLimit(int limit) {
		int size = listSize();
		if (limit < 0) return;
		else if (limit - size <= 0)
			for (int i = 0; i < size - limit; i++) addTable(new Table());
		else for (int i = listSize() - 1; i > (size - (size - limit)) - 1; i--) TABLE_LIST.remove(i); 
	}
	
	// *** Instance methods ***
	// LinkedList wrapped functions for individual tables
	public boolean appendStudent(Student s) {
		if (students.size() < Settings.getTableSize()) {
			students.append(s);
			return true;}
		return false;
	}
	public boolean removeStudent(int index) {return students.remove(index);}
	public boolean insertStudent(int index, Student s) {return students.insert(index, s);}
	public boolean swapStudent(int indexa, int indexb) {
		try {return students.swap(indexa, indexb);} catch (Exception e) {return false;}
	}
	
	
}
