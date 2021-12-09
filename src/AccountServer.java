import java.io.BufferedReader;
import java.io.PrintWriter;

public class AccountServer {
    private String username;
    private String firstName;
    private String lastName;

    private boolean ifTeacher;

    private TeacherServer teacherServer;

    private StudentServer studentServer;

    private PrintWriter printWriter;

    private BufferedReader bufferedReader;

    private int userNumber;

    public AccountServer(String username, String firstname, String lastname, boolean ifTeacher, PrintWriter printWriter, BufferedReader bufferedReader, int userNumber) {
        this.username = username;
        this.firstName = firstname;
        this.lastName = lastname;
        this.ifTeacher = ifTeacher;
        this.printWriter = printWriter;
        this.bufferedReader = bufferedReader;
        this.userNumber = userNumber;
    }

    public void openCourseMethod() throws Exception {
        if (ifTeacher) {
            teacherServer.openCourse();
        } else {
            studentServer.openCourse();
        }
    }

    public void createCourseMethod() throws Exception {
        teacherServer = new TeacherServer(username, firstName, lastName, printWriter, bufferedReader, userNumber);
        teacherServer.createCourse();
    }

    public void mainMethod() throws Exception {
        String choice = bufferedReader.readLine();
        studentServer = new StudentServer(username, firstName, lastName, printWriter, bufferedReader, userNumber);
        teacherServer = new TeacherServer(username, firstName, lastName, printWriter, bufferedReader, userNumber);
        MainServer mainServer = new MainServer(bufferedReader, printWriter);
        switch (choice) {
            case "0" -> {
                MainServer.getUsernames().remove(username);
                mainServer.mainRunMethod();
            }
            case "1" -> openCourseMethod();
            case "2" -> createCourseMethod();
        }
    }
}
