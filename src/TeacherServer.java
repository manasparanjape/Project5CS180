import java.io.*;
import java.util.ArrayList;
/**
 * TeacherServer.java
 *
 * Handles all the processing and backend portion of creating files, writing to files for the Teacher methods and
 * keeps the Courses updated with the latest information
 * Sends information to the client
 *
 * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
 *
 * @version 12/13/2021
 */
public class TeacherServer {
    private String username;
    private String firstName;
    private String lastName;
    private String coursesListFileName = "CoursesList.txt";

    private ArrayList<String> courseList = new ArrayList<>();

    private PrintWriter printWriter;
    private PrintWriter dummyWriter;

    private BufferedReader bufferedReader;

    public static final Object OBJ = new Object();

    private int userNumber;

    public TeacherServer(String username, String firstName, String lastName,
                         PrintWriter printWriter, BufferedReader bufferedReader,
                         int userNumber, PrintWriter dummyWriter) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.printWriter = printWriter;
        this.bufferedReader = bufferedReader;
        this.userNumber = userNumber;
        this.dummyWriter = dummyWriter;
    }

    public void readCourseListsFile() throws IOException {
        File f = new File(coursesListFileName);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();
        while (line != null && !line.isBlank()) {
            courseList.add(line);
            line = bfr.readLine();
        }
    }

    public String convertToSendFormat() {
        String output = "";
        if (courseList != null && courseList.size() != 0) {
            for (int i = 0; i < courseList.size(); i++) {
                output += courseList.get(i) + "§§§";
            }
            output = output.substring(0, output.length() - 3);
        }
        return output;
    }

    public void openCourse() throws Exception {
        readCourseListsFile();
        printWriter.write(convertToSendFormat());
        printWriter.println();
        printWriter.flush();

        String chosenCourse = bufferedReader.readLine();
        if (chosenCourse.equals(" ")) {
            AccountServer accountServer = new AccountServer(username, firstName, lastName,
                    false, printWriter, bufferedReader, userNumber, dummyWriter);
            accountServer.mainMethod();
        } else {
            String discussionBoardsListFileName = chosenCourse + "-forumslist.txt";
            CourseServer courseServer = new CourseServer(chosenCourse, username, firstName,
                    lastName, discussionBoardsListFileName, printWriter, bufferedReader, userNumber, dummyWriter);
            courseServer.mainMethod();
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

    public void createCourse() throws Exception {
        String newCourseName = bufferedReader.readLine();
        if (newCourseName.equals(" ")) {
            printWriter.write(" ");
        } else {
            if (courseExists(newCourseName)) {
                printWriter.write("0");
            } else {
                courseList.add(newCourseName);
                FileOutputStream fos = new FileOutputStream(newCourseName + "-forumslist.txt", false);
                PrintWriter pw = new PrintWriter(fos);
                pw.print("");
                pw.close();
                fos = new FileOutputStream(coursesListFileName, true);
                pw = new PrintWriter(fos);
                synchronized (OBJ) {
                    pw.println(newCourseName);
                }
                pw.close();
                printWriter.write("1");
            }
        }
        printWriter.println();
        printWriter.flush();
        AccountServer accountServer = new AccountServer(username, firstName, lastName,
                false, printWriter, bufferedReader, userNumber, dummyWriter);
        accountServer.mainMethod();
    }

    public void back() throws Exception {
        AccountServer accountServer = new AccountServer(username, firstName, lastName,
                true, printWriter, bufferedReader, userNumber, dummyWriter);
        accountServer.mainMethod();
    }
}
