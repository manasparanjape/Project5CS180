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
    private final static String tryAgainPrompt = """
            Error Occurred! Do you want to try again?
            1. Yes
            2. No""";
    private final static String courseEnteredPrompt = """
            Please enter the option number of what you want to do.
            1) Open course
            2) View points
            3) Exit course""";
    private ArrayList<String> courseList;

    public Student(String username, String firstName, String lastName, Course course, String coursesListFileName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.coursesListFileName = coursesListFileName;
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
        return courseList.contains(courseName);
    }

    public void openCourse() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        do {
            readCourseListsFile();
            System.out.println(courseSelectionPrompt);
            String selectedCourse = scan.nextLine();
            if (selectedCourse == null || !courseExists(selectedCourse)) {
                int tryAgain;
                do {
                    loop = false;
                    System.out.println(tryAgainPrompt);
                    tryAgain = scan.nextInt();
                    scan.nextLine();
                    if (tryAgain == 1) {
                        loop = true;
                    }
                } while (tryAgain != 1 && tryAgain != 2);
            } else {
                String discussionBoardsListFileName = selectedCourse + "-forumslist.txt";
                course = new Course(selectedCourse, username, firstName, lastName, null, discussionBoardsListFileName);
                course.readForumListFile();
                openCourseMainMethod();
            }
        } while (loop);
    }

    public void openCourseMainMethod() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        int option = 0;
        while (option != 5) {
            do {
                System.out.println(courseEnteredPrompt);
                option = scan.nextInt();
                scan.nextLine();
                if (option < 1 || option > 5) {
                    int tryAgain;
                    do {
                        loop = false;
                        System.out.println(tryAgainPrompt);
                        tryAgain = scan.nextInt();
                        scan.nextLine();
                        if (tryAgain == 1) {
                            loop = true;
                        }
                    } while (tryAgain != 1 && tryAgain != 2);
                } else {
                    switch (option) {
                        case 1 -> course.studentDiscussionForumOpened();
                        case 2 -> course.viewPoints();
                    }
                }
            } while (loop);
        }
    }
}