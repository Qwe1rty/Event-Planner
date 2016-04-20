package data;

/**
 * Tables allow for the management of student seats. It contains a global table
 * list that will store all tables and students within throughout the program. 
 * Table sizes and quantity are not managed here, but contained in Settings.java
 *
 * @author Caleb Choi
 */
public class Table {

    // *** FIELDS ***
    // Global table list
    private static LinkedList<Table> TABLE_LIST = new LinkedList<Table>();
    
    // Each table holds a list of students, but size is limited by Settings
    private LinkedList<Student> students;

    // *** CONSTRUCTOR ***
    /**
     * Builds an empty table. It is private as tables should only be
     * created and removed via the setLimit method
     */
    private Table() {
        this.students = new LinkedList<Student>();
    }

    // ** STATIC METHODS ***
    // LinkedList wrapper functions for global table list
    /**
     * Appends a table to the global table list. Should never be used outside
     * of this class, as adding and subtracting tables should only be done
     * via the setLimit method
     * @param table Table to be added to the global table list
     */
    private static void addTable(Table table) {
        TABLE_LIST.append(table);
    }
    
    /**
     * Deletes a table from the global table list. Should never be used outside
     * of this class, as adding and subtracting tables should only be done
     * via the setLimit method 
     * @param index Index of the table in the list to be removed
     */
    private static void removeTable(int index) {
        TABLE_LIST.remove(index);
    }
    
    /**
     * Gets a table from the global table list
     * @param index Index of the table in the list
     * @return The selected table
     */
    public static Table getTable(int index) {
        return TABLE_LIST.get(index);
    }
    
    /**
     * Returns the total size of the global table list
     * @return Integer representing the size of the list
     */
    public static int listSize() {
        return TABLE_LIST.size();
    }

    // LinkedList wrapper functions for students within tables in global table list
    // There isn't really a way to modify values within a linked list without doing this, 
    // which is why this is so disgusting
    /**
     * Adds a student to a specified table
     * @param index Index of the table within the global table list
     * @param student Student to be added to selected table
     */
    public static void addStudent(int index, Student student) {
        Table table = TABLE_LIST.get(index);
        table.appendStudent(student);
        TABLE_LIST.remove(index);
        TABLE_LIST.insert(index, table);
    }
    
    /**
     * Removes a student from a specified table
     * @param index Index of the table within the global table list
     * @param student Student to be removed from selected table
     */
    public static void removeStudent(int tableIndex, int studentIndex) {
        Table table = TABLE_LIST.get(tableIndex);
        table.removeStudent(studentIndex);
        TABLE_LIST.remove(tableIndex);
        TABLE_LIST.insert(tableIndex, table);
    }

    /**
     * Inserts a student into a specified table
     * @param tableIndex Index of the table within the global table list
     * @param studentIndex Index within the table where the student will be inserted
     * @param student Student to be added to the selected table
     */
    public static void insertStudent(int tableIndex, int studentIndex, Student student) {
        Table table = TABLE_LIST.get(tableIndex);
        table.insertStudent(studentIndex, student);
        TABLE_LIST.remove(tableIndex);
        TABLE_LIST.insert(tableIndex, table);
    }

    /**
     * Returns a student within a selected table
     * @param tableIndex Index of the table within the global table list
     * @param studentIndex Index of the student within the selected table
     * @return The selected student
     */
    public static Student getStudent(int tableIndex, int studentIndex) {
        return TABLE_LIST.get(tableIndex).getStudent(studentIndex);
    }

    /**
     * Sets new limit in the table list. If new limit is less than current list,
     * tables at bottom of list are truncated. If new limit is more than current
     * list, new tables are generated to compensate
     *
     * @param limit New table list size limit
     */
    public static void setLimit(int limit) {
	   // Stored in a varible as listSize() can be expensive
        int size = listSize();

        // Discards invalid input
        if (limit < 0) return;
        
        // If limit is above current size, tables are added to match the new limit
        else if (limit > size)
            for (int i = 0; i < limit - size; i++) 
            	addTable(new Table());
        
        // If limit is less than current size, tables are removed to match the new limit.
        // The tables near the end will be removed first
        else
            for (int i = size - 1; i >= limit; i--)
          	 removeTable(i);
    }

    // *** INSTANCE METHODS ***
    // LinkedList wrapped functions for individual tables. These are private because
    // you should only be modifying individual tables through the static method above.
    // (There were some complications, in case you were wondering why I did that)
    /**
     * Appends a student to the table
     * @param student Student to be added
     * @return A boolean on whether the operation was successful.
     */
    private boolean appendStudent(Student student) {
        if (students.size() < Settings.getTableSize()) {
            students.append(student);
            return true;
        }
        return false;
    }

    /**
     * Removes a student from the table
     * @param index Index of the student to be removed
     * @return A boolean on whether the operation was successful
     */
    private boolean removeStudent(int index) {
        return students.remove(index);
    }

    /**
     * Inserts a student into the table
     * @param index Index in the table for the student to be inserted to
     * @param student Student to be inserted
     * @return A boolean on whether the operation was successful
     */
    private boolean insertStudent(int index, Student student) {
        return students.insert(index, student);
    }

    /**
     * Returns the size of the table
     * @return Table size
     */
    private int tableSize() {
        return students.size();
    }

    /**
     * Gets a student from the table
     * @param index Index of the student in the table
     * @return Returns the selected student
     */
    public Student getStudent(int index) {
        return students.get(index);
    }

    /**
     * Returns a boolean on whether the table is full, as determined in Settings.java
     * @return A boolean on whether the table is full
     */
    public boolean isFull() {
        return students.size() >= Settings.getTableSize();
    }
//	private boolean swapStudent(int indexa, int indexb) {
//		try {return students.swap(indexa, indexb);} catch (Exception e) {return false;}
//	}

}
