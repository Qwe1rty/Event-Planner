package gui;

import io.Loader;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.URISyntaxException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.Food;
import data.Settings;
import data.Student;
import data.Student.InvalidFoodException;
import data.Student.InvalidStudentIDException;
import data.Table;

/**
 * Header class for the program.
 *
 * @author Caleb Choi, Connur Murphy, Matthew Sun
 */
public class EventPlanner implements WindowListener {

    public enum Panel {
        HOME, SETTINGS, STUDENT, DISPLAY_STUDENT, TABLE_DISPLAY
    }

    // Window width and height
    public static final int WINDOW_HEIGHT = 720;
    public static final int WINDOW_WIDTH = 1280;

    private static JPanel homePanel, settingsPanel, studentPanel,
            studentDisplay, tableDisplay;

    // Program frame
    public static final JFrame FRAME = new JFrame("RHHS Event Planner");

    public static void main(String args[]) {
        new EventPlanner().run();
    }

    /**
     * Sets up the main window for the program
     */
    public void run() {

        //Load settings before frame appears
        // TODO: use actual loader methods
/*
		  try { Loader.parseFile(); } catch (FileNotFoundException fnf) {
		  JOptionPane.showMessageDialog(FRAME, "The file was not found",
		  "File Not Found", JOptionPane.ERROR_MESSAGE); }
*/

        // Table
        Settings.setNumTables(10);
        Settings.setTableSize(5);

        // System look and feel
		/*
		 * try {
		 * UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		 * } catch (ClassNotFoundException | InstantiationException |
		 * IllegalAccessException | UnsupportedLookAndFeelException e1) { //
		 * TODO Auto-generated catch block e1.printStackTrace(); }
		 */

        try {
            GraphicsEnvironment ge = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,
                    EventPlanner.class.getResourceAsStream("/font/font.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,
                    EventPlanner.class.getResourceAsStream("/font/fontbold.ttf")));
        } catch (IOException | FontFormatException e) {
        }

        String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();

        // Create the panels
        homePanel = new HomePanel();

        studentPanel = new StudentPanel();
        studentDisplay = new DisplayStudentPanel();
        tableDisplay = new TableDisplayPanel();

        // Adds home panel
        FRAME.getContentPane().add(homePanel);

        // Miscellaneous settings
        FRAME.addWindowListener(this);
        FRAME.setLocation(50, 0);
        FRAME.setSize(WINDOW_WIDTH, WINDOW_HEIGHT + 27);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.setMinimumSize(new Dimension(1240, 680));
        FRAME.setVisible(true);




        // Add food
        for (int i = 0; i < 10; ++i) {
            Food.appendFood(new Food(generateRandName(8)));
        }
        
        settingsPanel = new SettingsPanel();

        // Add test students
        for (int i = 0; i < 10; ++i) {
            Random rand = new Random();

            boolean paid = rand.nextBoolean();
            boolean formSubmitted = rand.nextBoolean();
            boolean guest = rand.nextBoolean();
//            int tableNum = rand.nextInt(Settings.getNumTables() + 1);

            Student student = null;

            if (guest) {
                try {
                    student = new Student(generateRandNumberString(9),
                            generateRandName(rand.nextInt(8)),
                            generateRandName(rand.nextInt(8)),
                            generateRandName(2),Food.get(
                            rand.nextInt(Food.listSize())),
                            paid, generateRandName(rand.nextInt(8)), formSubmitted,
                            generateRandName(rand.nextInt(100)),
                            0,
                            generateRandNumberString(10),
                            generateRandName(rand.nextInt(100)), false);
                } catch (InvalidStudentIDException | InvalidFoodException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    student = new Student(generateRandNumberString(9),
                            generateRandName(rand.nextInt(8)),
                            generateRandName(rand.nextInt(8)),
                            generateRandName(2),Food.get(
                            rand.nextInt(Food.listSize())),
                            paid, "", formSubmitted,
                            generateRandName(rand.nextInt(100)),
                            0,
                            generateRandNumberString(10),
                            generateRandName(rand.nextInt(100)), true);
                } catch (InvalidStudentIDException | InvalidFoodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
           // Table.addStudent(tableNum, student);
            Student.addStudent(student);
        }
    }

    private String generateRandName(int size) {
        Random rand = new Random();
        String name = "";
        for (int i = 0; i < size + 1; ++i) {
            name += (char) ('a' + rand.nextInt(26));
        }
        return name;
    }

    private String generateRandNumberString(int size) {
        Random rand = new Random();
        String id = "";
        int[] nums = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        for (int i = 0; i < size; ++i) {
            id += nums[rand.nextInt(nums.length)];
        }
        return id;
    }
    

    /**
     * Used to change the panel currently displayed to the user.
     *
     * @param panel
     *            the type of panel to change to
     */
    public static void setPanel(Panel panel) {
        FRAME.getContentPane().removeAll();
        if (panel == Panel.HOME) {
            FRAME.add(homePanel);
        } else if (panel == Panel.SETTINGS) {
            FRAME.add(settingsPanel);
        } else if (panel == Panel.STUDENT) {
            ((StudentPanel) studentPanel).refresh();
            FRAME.add(studentPanel);
        } else if (panel == Panel.DISPLAY_STUDENT) {
            ((DisplayStudentPanel) studentDisplay).refresh(true);
            FRAME.add(studentDisplay);
        } else if (panel == Panel.TABLE_DISPLAY) {
            ((TableDisplayPanel) tableDisplay).refresh(true);
            FRAME.add(tableDisplay);
        }
        FRAME.revalidate();
        FRAME.repaint();
    }

    /**
     * Only used for the display student panel to ensure searched results are
     * not refreshed when returning to the student display
     *
     * @param panel
     *            the panel to change to (should be a DisplayStudentPanel)
     * @param refreshWithMasterStudentList
     *            if the displated students should be refreshed from the master
     *            student list
     */
    public static void setPanel(Panel panel,
                                boolean refreshWithMasterStudentList) {
        FRAME.getContentPane().removeAll();
        if (panel == Panel.DISPLAY_STUDENT) {
            ((DisplayStudentPanel) studentDisplay)
                    .refresh(refreshWithMasterStudentList);
            FRAME.add(studentDisplay);
        }
        FRAME.revalidate();
        FRAME.repaint();
    }

    /**
     * Used to change the currently selected panel to the student profile palen
     *
     * @param panel
     *            the panel of the profile to show
     */
    public static void showStudentProfile(StudentProfile panel) {
        FRAME.getContentPane().removeAll();
        FRAME.add(panel);
        FRAME.revalidate();
        FRAME.repaint();
    }

    /**
     * Gives the user a chance to save before the program shuts down
     */
    public void windowClosing(WindowEvent e) {
        int result = JOptionPane.showConfirmDialog(FRAME,
                "Do you want to save changes before exiting?", "Save",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            Loader.saveFile();
        }
    }

    @Override
    public void windowActivated(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowClosed(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowDeactivated(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowDeiconified(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowIconified(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowOpened(WindowEvent arg0) {
        // TODO Auto-generated method stub

    }
}
