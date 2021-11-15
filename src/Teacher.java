import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Teacher.java
 *
 * Contains course selection level options for teachers
 *
 * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
 *
 * @version 11/15/2021
 *
 */

public class Teacher {
    String username;
    String firstName;
    String lastName;
    private Course course;
    private Scanner scan;
    private final String coursesListFileName = "CoursesList.txt";

    private final static String newCourseNamePrompt = "What would you like to name the new course?";
    private final static String newCourseCreated = "New course has been created!";
    private final static String courseSelectionPrompt = "Which course would you like to open?";
    private final static String courseEnteredPrompt = "Please enter the option number of what you want to do.\n1)" 
         + " Open discussion forum\n2) Create discussion forum\n3) Delete discussion forum\n4) Exit course";
    private ArrayList<String> courseList = new ArrayList<>();

    public Teacher (String username, String firstName, String lastName, Course course, Scanner scan) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.scan = scan;
    }

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

    public void readCourseListsFile() throws IOException {
        File f = new File(coursesListFileName);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<String> output = new ArrayList<>();
        String line = bfr.readLine();
        while (line != null) {
            output.add(line);
            line = bfr.readLine();
        }
        courseList = output;
    }

    public boolean courseExists(String courseName) throws IOException {
        readCourseListsFile();
        if (courseList == null) {
            return false;
        } else {
            return courseList.contains(courseName);
        }
    }

    public void createCourse() throws IOException {
        System.out.println(newCourseNamePrompt);
        String newCourseName = scan.nextLine();
        if (newCourseName == null || newCourseName.isBlank()) {
            System.out.println("Please enter a valid course name(ie. Not all spaces or blank).");
        } else if (courseExists(newCourseName)) {
            System.out.println("A course with that name already exists.");
        } else {
            courseList.add(newCourseName);
            FileOutputStream fos = new FileOutputStream(newCourseName + "-forumslist.txt" , false);
            PrintWriter pw = new PrintWriter(fos);
            pw.println();
            pw.close();
            fos = new FileOutputStream(coursesListFileName, true);
            pw = new PrintWriter(fos);
            pw.println(newCourseName);
            System.out.println(newCourseCreated);
            pw.close();
        }
    }

    public void openCourse() throws Exception {
        readCourseListsFile();
        printCourseList();
        System.out.println(courseSelectionPrompt);
        String selectedCourse = scan.nextLine();
        if (selectedCourse == null || !courseExists(selectedCourse)) {
            System.out.println("The course you entered does not exist!");
        } else {
            String discussionBoardsListFileName = selectedCourse + "-forumslist.txt";
            course = new Course(selectedCourse, username, firstName, lastName, null, discussionBoardsListFileName, scan);
            course.readForumListFile();
            openCourseMainMethod();
        }
    }

    public void openCourseMainMethod() throws Exception {
        int option = 0;
        while (option != 4) {
            try {
        		System.out.println(courseEnteredPrompt);
        		option = scan.nextInt();
                scan.nextLine();
        	} catch (Exception e) {
        		System.out.println("You did not input an integer. Please input an integer between 1 and 5.");
        		option = 0;
        		scan.nextLine();
        		continue;
        	}
            if (option < 1 || option > 4) {
                System.out.println("You entered an invalid number. Please enter a number between 1 and 5.");
            } else {
                switch (option) {
                    case 1 -> course.teacherDiscussionForumOpened();
                    case 2 -> course.createForum();
                    case 3 -> course.deleteForum();
                }
            }
        }
    }
}
