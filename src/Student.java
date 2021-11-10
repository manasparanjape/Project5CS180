import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Student {
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private Course course;
    private final String coursesListFileName;
    private final static String tryAgainPrompt = """
            Error Occurred! Do you want to try again?
            1. Yes
            2. No""";
    private final static String courseEntryPrompt = """
            Please enter the option number of what you want to do.
            1) Open course
            2) View points
            3) Exit course""";
    private ArrayList<String> courseList;

    public Student(String username, String password, String firstName, String lastName, Course course, String coursesListFileName) {
        this.username = username;
        this.password = password;
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

    public void viewCourse() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        do {
            System.out.println(courseEntryPrompt);
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
                switch()
            }
        } while (loop);
    }
}