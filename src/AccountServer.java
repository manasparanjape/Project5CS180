import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * AccountServer.java
 *
 * Handles all the processing and data storing and searching realting to Accounts
 *
 * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
 *
 * @version 12/13/2021
 */

public class AccountServer {
    private String username;
    private String firstName;
    private String lastName;

    private boolean ifTeacher;

    private TeacherServer teacherServer;

    private StudentServer studentServer;

    private PrintWriter printWriter;
    private PrintWriter dummyWriter;

    private BufferedReader bufferedReader;

    private int userNumber;

    public AccountServer(String username, String firstname, String lastname,
                         boolean ifTeacher, PrintWriter printWriter, BufferedReader bufferedReader,
                         int userNumber, PrintWriter dummyWriter) {
        this.username = username;
        this.firstName = firstname;
        this.lastName = lastname;
        this.ifTeacher = ifTeacher;
        this.printWriter = printWriter;
        this.bufferedReader = bufferedReader;
        this.userNumber = userNumber;
        this.dummyWriter = dummyWriter;
    }

    public void openCourseMethod() throws Exception {
        if (ifTeacher) {
            teacherServer.openCourse();
        } else {
            studentServer.openCourse();
        }
    }

    public void createCourseMethod() throws Exception {
        teacherServer = new TeacherServer(username, firstName, lastName,
                printWriter, bufferedReader, userNumber, dummyWriter);
        teacherServer.createCourse();
    }

    public void mainMethod() throws Exception {
        String choice = bufferedReader.readLine();
        studentServer = new StudentServer(username, firstName, lastName,
                printWriter, bufferedReader, userNumber, dummyWriter);
        teacherServer = new TeacherServer(username, firstName, lastName,
                printWriter, bufferedReader, userNumber, dummyWriter);
        MainServer mainServer = new MainServer(bufferedReader, printWriter, dummyWriter);
        switch (choice) {
            case "0" -> {
                MainServer.getUsernames().remove(username);
                mainServer.mainRunMethod();
            }
            case "1" -> openCourseMethod();
            case "2" -> createCourseMethod();
            case "Close" -> {
                MainServer.getUsernames().remove(username);
            }
        }
    }
}
