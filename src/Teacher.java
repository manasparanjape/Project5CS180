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
    private final String coursesListFileName = "CoursesList.txt";

    private final static String newCourseNamePrompt = "What would you like to name the new course?";
    private final static String newCourseCreated = "New course has been created!";
    private final static String courseSelectionPrompt = "Which course would you like to open?";
    private final static String courseEnteredPrompt = "Please enter the option number of what you want to do.\n1)" 
         + " Open discussion forum\n2) Create discussion forum\n3) Delete discussion forum\n4) Exit course";
    private ArrayList<String> courseList = new ArrayList<>();

    public Teacher (String username, String firstName, String lastName, Course course) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
    }
    //uses a for loop to iterate through the courseList array and appends 
    //every course to a StringBuilder object
    //prints out course list as string
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
    //reads courses the “CoursesList.txt” file and stores in arraylist
    //stores all the course names in the courseList array list
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
    //checks if the given course exists
    public boolean courseExists(String courseName) throws IOException {
        readCourseListsFile();
        if (courseList == null) {
            return false;
        } else {
            return courseList.contains(courseName);
        }
    }
    //creates a course
    //adds course name to courseList array list
    //writes course name to text file using PrintWriter 
    public void createCourse(Scanner scan) throws IOException {
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
    //checks if user inputted course is present in the courseList array list
    //reads all the discussion forum title names for that specific course
    public void openCourse(Scanner scan) throws Exception {
        readCourseListsFile();
        printCourseList();
        System.out.println(courseSelectionPrompt);
        String selectedCourse = scan.nextLine();
        if (selectedCourse == null || !courseExists(selectedCourse)) {
            System.out.println("The course you entered does not exist!");
        } else {
            String discussionBoardsListFileName = selectedCourse + "-forumslist.txt";
            course = new Course(selectedCourse, username, firstName, lastName, null, discussionBoardsListFileName);
            course.readForumListFile(scan);
            openCourseMainMethod(scan);
        }
    }
    //gives user four options: open discussion forum, create forum, delete forum, and show dashboard
    public void openCourseMainMethod(Scanner scan) throws Exception {
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
                    case 1 -> course.teacherDiscussionForumOpened(scan);
                    case 2 -> course.createForum(scan);
                    case 3 -> course.deleteForum(scan);
                }
            }
        }
    }
}
