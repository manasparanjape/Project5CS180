import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Student {
    private final String username;
    private final String firstName;
    private final String lastName;
    private Course course;
    private final String coursesListFileName;

    private final static String courseSelectionPrompt = "Which course would you like to open?";
    private final static String courseEnteredPrompt = """
            Please enter the option number of what you want to do.
            1) Open discussion forum
            2) View points
            3) Exit course""";
    private ArrayList<String> courseList = new ArrayList<>();

    public Student(String username, String firstName, String lastName, Course course, String coursesListFileName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.coursesListFileName = coursesListFileName;
    }

    public void printCourseList() {
        StringBuilder output = new StringBuilder();
        for (String s : courseList) {
            output.append(s).append("\n");
        }
        output = new StringBuilder(output.substring(0, output.length() - 1));
        System.out.println(output);
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

    public boolean courseExists(String courseName) {
        if (courseList == null) {
            return false;
        } else {
            return courseList.contains(courseName);
        }
    }

    public void openCourse() throws Exception {
        Scanner scan = new Scanner(System.in);
        readCourseListsFile();
        printCourseList();
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
        while (option != 3) {
        	try {
        		System.out.println(courseEnteredPrompt);
        		option = scan.nextInt();
        	} catch (Exception e) {
        		System.out.println("Please enter a valid number!");
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
}
