import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CourseServer {
    private String courseName;
    private String username;
    private String firstName;
    private String lastName;
    private String discussionBoardsListFileName;

    private DiscussionForumServer discussionForumServer;

    private ArrayList<String> forumList = new ArrayList<>();

    private PrintWriter printWriter;
    private PrintWriter dummyWriter;

    private BufferedReader bufferedReader;

    private  int userNumber;

    public CourseServer(String courseName, String username, String firstName, String lastName, String discussionBoardsListFileName, PrintWriter printWriter, BufferedReader bufferedReader, int userNumber, PrintWriter dummyWriter) {
        this.courseName = courseName;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.discussionBoardsListFileName = discussionBoardsListFileName;
        this.printWriter = printWriter;
        this.bufferedReader = bufferedReader;
        this.userNumber = userNumber;
        this.dummyWriter = dummyWriter;
    }

    public void readForumListFile() throws Exception {
        File f = new File(discussionBoardsListFileName);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<String> output = new ArrayList<>();
        String line = bfr.readLine();
        while (line != null) {
            output.add(line);
            line = bfr.readLine();
        }
        forumList = output;
    }

    public boolean discussionForumExists(String forumName) {
        if (forumList == null) {
            return false;
        } else {
            return forumList.contains(forumName);
        }
    }

    public String convertToSendFormat() {
        String output = "";
        if (forumList != null && forumList.size() != 0)  {
            for (int i = 0; i < forumList.size(); i++) {
                output += forumList.get(i) + "§§§";
            }
            output = output.substring(0, output.length() - 3);
        } else {
            output = " ";
        }
        return output;
    }

    public void createForum() throws Exception {
        readForumListFile();
        String newForumName = bufferedReader.readLine();
        if (!newForumName.equals(" ")) {
            if (discussionForumExists(newForumName)) {
                printWriter.write("0");
            } else {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss MM-dd-yyyy");
                forumList.add(newForumName);
                FileOutputStream fos = new FileOutputStream(courseName + "-" + newForumName +
                        "-messages" + ".txt", true);
                PrintWriter pw = new PrintWriter(fos);
                pw.println(newForumName + "§§§" + LocalDateTime.now().format(format));
                pw.close();
                fos = new FileOutputStream(courseName + "-" + newForumName + "-points" + ".txt", false);
                pw = new PrintWriter(fos);
                pw.println("");
                pw.close();
                fos = new FileOutputStream(courseName + "-" + newForumName + "-upvotes" + ".txt", false);
                pw = new PrintWriter(fos);
                pw.println("");
                pw.close();
                fos = new FileOutputStream(discussionBoardsListFileName, true);
                pw = new PrintWriter(fos);
                pw.println(newForumName);
                /*if (forumList.size() == 1) {
                    pw.print(newForumName);
                } else {
                    pw.print("\n" + newForumName);
                }*/
                pw.close();
                printWriter.write("1");
            }
        }
        printWriter.println();
        printWriter.flush();

        mainMethod();
    }

    public void deleteForum() throws Exception {
        readForumListFile();
        printWriter.write(convertToSendFormat());
        printWriter.println();
        printWriter.flush();

        String toDeleteForum = bufferedReader.readLine();
        if (!toDeleteForum.equals(" ")) {
            forumList.remove(toDeleteForum);
            File f = new File(courseName + "-" + toDeleteForum + "-messages" + ".txt");
            f.delete();
            f = new File(courseName + "-" + toDeleteForum + "-points" + ".txt");
            f.delete();
            f = new File(courseName + "-" + toDeleteForum + "-upvotes" + ".txt");
            f.delete();
            FileOutputStream fos = new FileOutputStream(discussionBoardsListFileName, false);
            PrintWriter pw = new PrintWriter(fos);
            StringBuilder output = new StringBuilder();
            for (String s : forumList) {
                output.append(s).append("\n");
            }
            if (output.length() > 0) {
                output = new StringBuilder(output.substring(0, output.length() - 1));
            }
            pw.println(output);
            pw.close();
        }

        mainMethod();
    }

    public void openForum() throws Exception {
        readForumListFile();
        String forumListString = convertToSendFormat();
        printWriter.write(forumListString);
        printWriter.println();
        printWriter.flush();

        if (!forumListString.equals(" ")) {
            printWriter.write(forumListString);
            printWriter.println();
            printWriter.flush();

            changeForum();
        } else {
            mainMethod();
        }
    }

    public void changeForum() throws Exception {
        String receivedData = bufferedReader.readLine();
        String[] forumFileNames = receivedData.split("§§§");

        discussionForumServer = new DiscussionForumServer(courseName, forumFileNames[0], forumFileNames[1], forumFileNames[2], forumFileNames[3], firstName, lastName, username, printWriter, bufferedReader, userNumber, dummyWriter);
        discussionForumServer.readMessagesFile();
        discussionForumServer.readPointsFile();
        discussionForumServer.readUpvoteFile();

        discussionForumServer.printForum();
        discussionForumServer.mainMethod();
    }

    public void backButtonStudent() throws Exception {
        StudentServer studentServer = new StudentServer(username, firstName, lastName, printWriter, bufferedReader, userNumber, dummyWriter);
        studentServer.back();
    }

    public void backButtonTeacher() throws Exception {
        TeacherServer teacherServer = new TeacherServer(username, firstName, lastName, printWriter, bufferedReader, userNumber, dummyWriter);
        teacherServer.back();
    }

    public void viewPoints() throws Exception {
        readForumListFile();
        StringBuilder output = new StringBuilder();
        for (String s : forumList) {
            output.append(s).append(": ");
            File f = new File(courseName + "-" + s + "-points" + ".txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            boolean pointsFound = false;
            while (line != null) {
                if (line.contains(username)) {
                    String[] splitLine = line.split("§§§");
                    output.append(splitLine[1]).append("\n");
                    pointsFound = true;
                }
                line = bfr.readLine();
            }
            if (!pointsFound) {
                output.append("Points not assigned to this forum yet.\n");
            }
        }
        String toSend = String.valueOf(output);
        toSend = toSend.replaceAll("\n", "§§§");
        printWriter.write(toSend);
        printWriter.println();
        printWriter.flush();
        mainMethod();
    }

    public void mainMethod() throws Exception {
        String choice = bufferedReader.readLine();
        switch (choice) {
            case "-1" -> backButtonTeacher();
            case "0" -> backButtonStudent();
            case "1", "4" -> createForum();
            case "2" -> deleteForum();
            case "3" -> openForum();
            case "5" -> viewPoints();
            case "Close" -> {
                MainServer.getUsernames().remove(username);
                Thread.currentThread().stop();
            }
        }
    }
}
