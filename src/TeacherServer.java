import java.io.*;
import java.util.ArrayList;

public class TeacherServer {
    private String username;
    private String firstName;
    private String lastName;
    private String coursesListFileName = "CoursesList.txt";

    private ArrayList<String> courseList = new ArrayList<>();

    private PrintWriter printWriter;

    private BufferedReader bufferedReader;

    public TeacherServer(String username, String firstName, String lastName, PrintWriter printWriter, BufferedReader bufferedReader) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.printWriter = printWriter;
        this.bufferedReader = bufferedReader;
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
            AccountServer accountServer = new AccountServer(username, firstName, lastName, false, printWriter, bufferedReader);
            accountServer.mainMethod();
        } else {
            String discussionBoardsListFileName = chosenCourse + "-forumslist.txt";
            CourseServer courseServer = new CourseServer(chosenCourse, username, firstName, lastName, discussionBoardsListFileName, printWriter, bufferedReader);
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
                pw.println();
                pw.close();
                fos = new FileOutputStream(coursesListFileName, true);
                pw = new PrintWriter(fos);
                pw.println(newCourseName);
                pw.close();
                printWriter.write("1");
            }
        }
        printWriter.println();
        printWriter.flush();
        AccountServer accountServer = new AccountServer(username, firstName, lastName, false, printWriter, bufferedReader);
        accountServer.mainMethod();
    }

    public void back() throws Exception {
        AccountServer accountServer = new AccountServer(username, firstName, lastName, true, printWriter, bufferedReader);
        accountServer.mainMethod();
    }
}
