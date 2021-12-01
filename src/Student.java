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
    private Scanner scan;

    private static String courseSelectionPrompt = "Which course would you like to open?";
    private static String courseEnteredPrompt = "Please enter the option number of what you want to do.\n1)"
            + " Open discussion forum\n2) View points\n3) Exit course";
    private ArrayList<String> courseList = new ArrayList<>();

    public Student(String username, String firstName, String lastName, Course course, Scanner scan, JFrame jframe) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.scan = scan;
        this.jframe = jframe;
    }

    //uses a for loop to iterate through the courseList array and appends every course to a StringBuilder object
    //prints out course list
    public void printCourseList() {
        StringBuilder output = new StringBuilder();
        for (String s : courseList) {
            output.append(s).append("\n");
        }
        output = new StringBuilder(output.substring(0, output.length() - 1));
        System.out.println(output);
        if (output.toString().isEmpty()) {
            System.out.println("No courses created yet.");
        }
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

    //checks if the course exists
    public boolean courseExists(String courseName) {
        if (courseList.isEmpty()) {
            return false;
        } else {
            return courseList.contains(courseName);
        }
    }

    //checks if user inputted course is present in the courseList array list
    public void openCourse() throws Exception {
        readCourseListsFile();
        printCourseList();
        System.out.println(courseSelectionPrompt);
        String selectedCourse = scan.nextLine();
        if (selectedCourse == null || !courseExists(selectedCourse)) {
            System.out.println("The course you entered does not exist!");
        } else {
            String discussionBoardsListFileName = selectedCourse + "-forumslist.txt";
            course = new Course(selectedCourse, username, firstName, lastName, null, discussionBoardsListFileName, scan, null);
            course.readForumListFile();
            openCourseMainMethod(scan);
        }
    }

    //gives the user 2 options: open discussion forum and view points
    public void openCourseMainMethod(Scanner scan) throws Exception {
        int option = 0;
        while (option != 3) {
            try {
                System.out.println(courseEnteredPrompt);
                option = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("You did not input an integer. Please input an integer between 1 and 3.");
                option = 0;
                scan.nextLine();
                continue;
            }
            if (option < 1 || option > 3) {
                System.out.println("You entered an invalid number. Please enter a number between 1 and 3.");
            } else {
                switch (option) {
                    case 1 -> course.studentDiscussionForumOpened();
                    case 2 -> course.viewPoints();
                }
            }
        }
    }

    JFrame jframe;

    public void runnableMethod() {

    }
}
