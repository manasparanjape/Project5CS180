import java.io.*;
import java.util.ArrayList;

public class StudentServer {
    private String username;
    private String firstName;
    private String lastName;
    private String coursesListFileName = "CoursesList.txt";

    private ArrayList<String> courseList = new ArrayList<>();

    private PrintWriter printWriter;

    private BufferedReader bufferedReader;

    private int userNumber;

    public StudentServer(String username, String firstName, String lastName,  PrintWriter printWriter, BufferedReader bufferedReader, int userNumber) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.printWriter = printWriter;
        this.bufferedReader = bufferedReader;
        this.userNumber = userNumber;
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
            AccountServer accountServer = new AccountServer(username, firstName, lastName, false, printWriter, bufferedReader, userNumber);
            accountServer.mainMethod();
        } else {
            String discussionBoardsListFileName = chosenCourse + "-forumslist.txt";
            CourseServer courseServer = new CourseServer(chosenCourse, username, firstName, lastName, discussionBoardsListFileName,printWriter, bufferedReader, userNumber);
            courseServer.mainMethod();
        }
    }

    public void back() throws Exception {
        AccountServer accountServer = new AccountServer(username, firstName, lastName, false, printWriter, bufferedReader, userNumber);
        accountServer.mainMethod();
    }
}
