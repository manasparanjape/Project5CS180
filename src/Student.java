import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Student.java
 * <p>
 * Contains course selection level options for students
 *
 * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
 * @version 11/15/2021
 */

public class Student {
    private String username;
    private String firstName;
    private String lastName;
    private Course course;
    private String coursesListFileName = "CoursesList.txt";
    private static String courseSelectionPrompt = "Which course would you like to open?";
    private ArrayList<String> courseList = new ArrayList<>();

    public Student(String username, String firstName, String lastName, Course course, JFrame jframe) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.jframe = jframe;
    }

    //adds all the courses to an array list courseList
    public void readCourseListsFile() throws IOException {
        File f = new File(coursesListFileName);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();
        while (line != null) {
            courseList.add(line);
            line = bfr.readLine();
        }
    }

    //checks if user inputted course is present in the courseList array list
    public void openCourse() throws Exception {
        readCourseListsFile();
        Object[] options = new Object[courseList.size()];
        for (int i = 0; i < courseList.size(); i++) {
            options[i] = courseList.get(i);
        }
        Object selectedObject = JOptionPane.showInputDialog(null, courseSelectionPrompt, "Delete Forum", JOptionPane.PLAIN_MESSAGE, null, options, JOptionPane.CLOSED_OPTION);
        if (selectedObject != null) {
            String selectedCourse = selectedObject.toString();
            String discussionBoardsListFileName = selectedCourse + "-forumslist.txt";
            course = new Course(selectedCourse, username, firstName, lastName, null, discussionBoardsListFileName, jframe);
            course.readForumListFile();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        runMethodStudent();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    JFrame jframe;

    public void runMethodStudent() throws Exception {
        course.studentDiscussionForumOpened();
    }

    public void runnableMethod() throws Exception {
        Account account = new Account(username, firstName, lastName, false, jframe);
        account.accountMainMethod();
    }
}
