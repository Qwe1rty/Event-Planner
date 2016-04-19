package data;

import java.util.Set;

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
    private Table() {
        this.students = new LinkedList<Student>();
    }

    // ** Static methods ***
    // LinkedList wrapper functions for global table list
    public static void addTable(Table table) {
        TABLE_LIST.append(table);
    }

    public static void removeTable(int index) {
        TABLE_LIST.remove(index);
    }

    public static Table getTable(int index) {
        return TABLE_LIST.get(index);
    }

    public static int listSize() {
        return TABLE_LIST.size();
    }

    // LinkedList wrapper functions for students within tables in global table list
    // There isn't really a way to modify tables without doing this, which is why this is disgusting
    public static void addStudent(int index, Student student) {
        Table table = TABLE_LIST.get(index);
        table.appendStudent(student);
        TABLE_LIST.remove(index);
        TABLE_LIST.insert(index, table);
    }

    public static void removeStudent(int tableIndex, int studentIndex) {
        Table table = TABLE_LIST.get(tableIndex);
        table.removeStudent(studentIndex);
        TABLE_LIST.remove(tableIndex);
        TABLE_LIST.insert(tableIndex, table);
    }

    public static void insertStudent(int tableIndex, int studentIndex, Student student) {
        Table table = TABLE_LIST.get(tableIndex);
        table.insertStudent(studentIndex, student);
        TABLE_LIST.remove(tableIndex);
        TABLE_LIST.insert(tableIndex, table);
    }

    public static int tableSize(int index) {
        return TABLE_LIST.get(index).tableSize();
    }

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
                TABLE_LIST.remove(i);
        
    }

    // *** Instance methods ***
    // LinkedList wrapped functions for individual tables. These are private because
    // you should only be modifying individual tables through the static method above.
    // (There were some complications, in case you were wondering why I did that)
    private boolean appendStudent(Student student) {
        if (students.size() < Settings.getTableSize()) {
            students.append(student);
            return true;
        }
        return false;
    }

    private boolean removeStudent(int index) {
        return students.remove(index);
    }

    private boolean insertStudent(int index, Student s) {
        return students.insert(index, s);
    }

    private int tableSize() {
        return students.size();
    }

    public Student getStudent(int index) {
        return students.get(index);
    }

    public boolean isFull() {
        return students.size() >= Settings.getTableSize();
    }
//	private boolean swapStudent(int indexa, int indexb) {
//		try {return students.swap(indexa, indexb);} catch (Exception e) {return false;}
//	}

}
