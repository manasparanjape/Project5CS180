import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Teacher {
    String username;
    String firstName;
    String lastName;
    private Course course;
    private final String coursesListFileName = "CoursesList.txt";

    private final static String newCourseNamePrompt = "What would you like to name the new course?";
    private final static String newCourseCreated = "New course has been created!";
    private final static String courseSelectionPrompt = "Which course would you like to open?";
    private final static String tryAgainPrompt = """
            Error Occurred! Do you want to try again?
            1. Yes
            2. No""";
    private final static String courseEnteredPrompt = """
            Please enter the option number of what you want to do.
            1) Open discussion forum
            2) Create discussion forum
            3) Delete discussion forum
            4) Exit course""";
    private ArrayList<String> courseList = new ArrayList<>();

    public Teacher (String username, String firstName, String lastName, Course course) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
    }

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

    public boolean courseExists(String courseName) throws IOException {
        readCourseListsFile();
        if (courseList == null) {
            return false;
        } else {
            return courseList.contains(courseName);
        }
    }

    public void createCourse() throws IOException {
        Scanner scan = new Scanner(System.in);
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
            pw.println(newCourseName);
            fos = new FileOutputStream(coursesListFileName, true);
            pw = new PrintWriter(fos);
            pw.println(newCourseName);
            System.out.println(newCourseCreated);
            pw.close();
        }
    }

    public void openCourse() throws Exception {
        Scanner scan = new Scanner(System.in);
        readCourseListsFile();
        System.out.println(courseSelectionPrompt);
        String selectedCourse = scan.nextLine();
        if (selectedCourse == null || !courseExists(selectedCourse)) {
            System.out.println("The course you entered does not exist!");
        } else {
            String discussionBoardsListFileName = selectedCourse + "-forumslist.txt";
            course = new Course(selectedCourse, username, firstName, lastName, null, discussionBoardsListFileName);
            course.readForumListFile();
            openCourseMainMethod();
        }
    }

    public void openCourseMainMethod() throws Exception {
        Scanner scan = new Scanner(System.in);
        int option = 0;
        while (option != 4) {
            System.out.println(courseEnteredPrompt);
            option = scan.nextInt();
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