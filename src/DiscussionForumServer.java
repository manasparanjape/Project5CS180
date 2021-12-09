import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class DiscussionForumServer {
    private String courseName;
    private String forumName;
    private String messagesFileName;
    private String pointsFileName;
    private String upvoteFileName;
    private String firstName;
    private String lastName;
    private String username;
    private String forumCreationTime;

    private ArrayList<ArrayList<String>> messagesArray = new ArrayList<>();
    private ArrayList<ArrayList<String>> pointsArray = new ArrayList<>();
    private ArrayList<ArrayList<String>> upvotesArray = new ArrayList<>();

    private PrintWriter printWriter;

    private BufferedReader bufferedReader;

    private boolean ifDashboard = false;

    public static final Object object = new Object();

    private int userNumber;

    public DiscussionForumServer(String courseName, String forumName, String messagesFileName, String pointsFileName, String upvoteFileName, String firstName, String lastName, String username, PrintWriter printWriter, BufferedReader bufferedReader, int userNumber) {
        this.forumName = forumName;
        this.messagesFileName = messagesFileName;
        this.pointsFileName = pointsFileName;
        this.upvoteFileName = upvoteFileName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.printWriter = printWriter;
        this.bufferedReader = bufferedReader;
        this.courseName = courseName;
        this.userNumber = userNumber;
    }

    public void readUpvoteFile() throws IOException {
        File f = new File(upvoteFileName);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        String line = bfr.readLine();
        while (line != null) {
            String[] separatedLine = line.split("§§§");
            ArrayList<String> singleLine = new ArrayList<>(Arrays.asList(separatedLine));
            output.add(singleLine);
            line = bfr.readLine();
        }
        bfr.close();
        upvotesArray = output;
    }

    public void readMessagesFile() throws Exception {
        File f = new File(messagesFileName);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        String line = bfr.readLine();
        forumCreationTime = line.split("§§§")[1];
        line = bfr.readLine();
        while (line != null) {
            String[] separatedLine = line.split("§§§");
            ArrayList<String> singleLine = new ArrayList<>(Arrays.asList(separatedLine));
            output.add(singleLine);
            line = bfr.readLine();
        }
        if (output.size() > 0) {
            output.remove(output.size() - 1);
        }
        bfr.close();
        messagesArray = output;
    }

    public void readPointsFile() throws Exception {
        File f = new File(pointsFileName);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        String line = bfr.readLine();
        while (line != null) {
            String[] separatedLine = line.split("§§§");
            ArrayList<String> singleLine = new ArrayList<>(Arrays.asList(separatedLine));
            output.add(singleLine);
            line = bfr.readLine();
        }
        pointsArray = output;
    }

    public void writeToMessagesFile() throws Exception {
        FileOutputStream fos = new FileOutputStream(messagesFileName, false);
        PrintWriter pw = new PrintWriter(fos);
        String toWrite = forumName + "§§§" + forumCreationTime;
        if (messagesArray.size() > 0) {
            toWrite += "\n" + convertMessagesArrayToFileString();
        }
        synchronized (object) {
            pw.println(toWrite);
        }
        pw.close();
    }

    public String convertMessagesArrayToFileString() {
        StringBuilder output = new StringBuilder();
        for (ArrayList<String> strings : messagesArray) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < 8; j++) {
                line.append(strings.get(j)).append("§§§");
            }
            line = new StringBuilder(line.substring(0, line.length() - 3));
            output.append(line).append("\n");
        }
        return output.toString();
    }

    public void printSpecificStudentMessages(String studentUsername) throws Exception {
        StringBuilder output = new StringBuilder("Messages by " + studentUsername + "\n");
        boolean studentHasPosted = false;
        for (ArrayList<String> strings : messagesArray) {
            if (strings.get(6).equals(studentUsername)) {
                studentHasPosted = true;
                output.append(strings.get(0)).append(". ");
                output.append(strings.get(1)).append("\n");
                output.append("   - ").append(strings.get(2)).append(" ");
                output.append(strings.get(3)).append("\n");
                int upvotes = Integer.parseInt(strings.get(4));
                if (upvotes > 0) {
                    output.append("Upvotes: ").append(upvotes).append("\n");
                }
                int replyTo = Integer.parseInt(strings.get(5));
                if (replyTo > 0) {
                    output.append("Reply to message no.: ").append(replyTo).append("\n");
                }
                output.append("\n");
            }
        }
        if (!studentHasPosted) {
            output.append("This student has not posted any replies to this forum yet.");
        } else {
            output = new StringBuilder(output.substring(0, output.length() - 1));
        }
        String toSend = output.toString();
        toSend = toSend.replaceAll("\n", "§§§");
        printWriter.write(toSend);
        printWriter.println();
        printWriter.flush();
    }

    public String choice;

    public Timer timer;

    public void mainMethod() throws Exception {
        /*choice = "-1";
        timer = new Timer(1000, new MyTimerActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionListenerMethod();
            }
        });

        timer.start();
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            System.out.println("Check 1");
        }
        timer.stop();*/
        choice = bufferedReader.readLine();
        switch (choice) {
            case "-1" -> back();
            case "1" -> send();
            case "2" -> upvote();
            case "3" -> deleteMessage();
            case "4" -> gradeStudent();
            case "5" -> dashBoard();
            case "6" -> changeTopic();
            case "7" -> sendMessageViaFileImport();
            default -> {
                String discussionBoardsListFileName = courseName + "-forumslist.txt";
                CourseServer courseServer = new CourseServer(courseName, username, firstName, lastName, discussionBoardsListFileName, printWriter, bufferedReader, userNumber);
                courseServer.changeForum();
            }
        }
    }

    public void back() throws Exception {
        String discussionBoardsListFileName = courseName + "-forumslist.txt";
        CourseServer courseServer = new CourseServer(courseName, username, firstName, lastName, discussionBoardsListFileName, printWriter, bufferedReader, userNumber);
        courseServer.mainMethod();
    }

    public void send() throws Exception {
        printWriter.write(String.valueOf(messagesArray.size()));
        printWriter.println();
        printWriter.flush();

        String receivedData = bufferedReader.readLine();
        if (!receivedData.equals(" ")) {
            String[] receivedDataArray = receivedData.split("§§§");
            ArrayList<String> newPostArray = new ArrayList<>();
            String fullName = firstName + " " + lastName;
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss MM-dd-yyyy");
            readMessagesFile();
            if (receivedDataArray[1].equals("0")) {
                newPostArray.add(0, Integer.toString(messagesArray.size() + 1));
                newPostArray.add(1, receivedDataArray[0]);
                newPostArray.add(2, fullName);
                newPostArray.add(3, LocalDateTime.now().format(format));
                newPostArray.add(4, "0");
                newPostArray.add(5, "0");
                newPostArray.add(6, username);
                newPostArray.add(7, Integer.toString(messagesArray.size()));
                messagesArray.add(newPostArray);
            } else {
                newPostArray.add(Integer.toString(Integer.parseInt(receivedDataArray[1]) + 1));
                newPostArray.add(receivedDataArray[0]);
                newPostArray.add(fullName);
                newPostArray.add(LocalDateTime.now().format(format));
                newPostArray.add("0");
                newPostArray.add(Integer.toString(Integer.parseInt(receivedDataArray[1])));
                newPostArray.add(username);
                newPostArray.add(Integer.toString(messagesArray.size() + 1));
                messagesArray.add(Integer.parseInt(receivedDataArray[1]), newPostArray);
                for (int i = Integer.parseInt(receivedDataArray[1]) + 1; i < messagesArray.size(); i++) {
                    messagesArray.get(i).set(0, Integer.toString(i + 1));
                }
            }
        }
        for (int i = 0; i < MainClass.forumUpdated.size(); i++) {
            MainClass.forumUpdated.set(i, false);
        }
        MainClass.forumUpdated.set(userNumber, true);
        writeToMessagesFile();
        printForum();
        mainMethod();
    }

    public void upvote() throws Exception {
        int upvoteMessageNumber = Integer.parseInt(bufferedReader.readLine());
        if (upvoteMessageNumber < 0 || upvoteMessageNumber > messagesArray.size()) {
            printWriter.write("1");
            printWriter.println();
            printWriter.flush();
        } else {
            if (upvoteMessageNumber > 0) {
                if (checkAlreadyUpvoted(upvoteMessageNumber)) {
                    printWriter.write("2");
                    printWriter.println();
                    printWriter.flush();
                } else {
                    printWriter.write("3");
                    printWriter.println();
                    printWriter.flush();
                    int upvotes = Integer.parseInt(messagesArray.get(upvoteMessageNumber - 1).get(4));
                    upvotes++;
                    messagesArray.get(upvoteMessageNumber - 1).set(4, Integer.toString(upvotes));
                    if (checkUsernameInUpvotesArray()) {
                        for (ArrayList<String> strings : upvotesArray) {
                            if (strings.get(0).equals(username)) {
                                strings.add(Integer.toString(upvoteMessageNumber));
                            }
                        }
                    } else {
                        ArrayList<String> newUpvoteArray = new ArrayList<>();
                        newUpvoteArray.add(username);
                        newUpvoteArray.add(Integer.toString(upvoteMessageNumber));
                        upvotesArray.add(newUpvoteArray);
                    }
                    writeToMessagesFile();
                    writeToUpvotesFile();
                    printForum();
                }
            } else {
                printWriter.write("0");
                printWriter.println();
                printWriter.flush();
            }
        }
        mainMethod();
    }

    public void deleteMessage() throws Exception {
        int deleteMessageNumber = Integer.parseInt(bufferedReader.readLine());
        if (deleteMessageNumber < 1 || deleteMessageNumber > messagesArray.size()) {
            printWriter.write("0");
            printWriter.println();
            printWriter.flush();
        } else {
            printWriter.write("1");
            printWriter.println();
            printWriter.flush();
            String fullName = firstName + " " + lastName;
            messagesArray.get(deleteMessageNumber - 1).set(1, "This message has been deleted by " + fullName);
            messagesArray.get(deleteMessageNumber - 1).set(4, "0");
            messagesArray.get(deleteMessageNumber - 1).set(7, String.valueOf(messagesArray.size()));
            writeToMessagesFile();
            printForum();
        }
        mainMethod();
    }

    public void gradeStudent() throws Exception {
        boolean checkIfPointsExist = false;
        int i = 0;

        String studentUsername = bufferedReader.readLine();
        if (!studentUsername.equals(" ")) {
            if (checkUsernameNonexistence(studentUsername)) {
                printWriter.write("0");
                printWriter.println();
                printWriter.flush();
            } else {
                printWriter.write("1");
                printWriter.println();
                printWriter.flush();
                printSpecificStudentMessages(studentUsername);
                String pointsString = bufferedReader.readLine();
                if (!pointsString.equals(" ")) {
                    while (!checkIfPointsExist && i < pointsArray.size()) {
                        if (pointsArray.get(i).get(0).equals(studentUsername)) {
                            checkIfPointsExist = true;
                        }
                        i++;
                    }
                    int points = 0;
                    try {
                        points = Integer.parseInt(pointsString);
                        ArrayList<String> output = new ArrayList<>();
                        output.add(studentUsername);
                        output.add(Integer.toString(points));
                        if (checkIfPointsExist) {
                            pointsArray.set(i - 1, output);
                        } else {
                            pointsArray.add(output);
                        }
                        printWriter.write("4");
                        printWriter.println();
                        printWriter.flush();
                        writeToPointsFile();
                    } catch (Exception e) {
                        printWriter.write("3");
                        printWriter.println();
                        printWriter.flush();
                    }

                }
            }
        }
        printForum();
        mainMethod();
    }

    public void dashBoard() throws Exception {
        if (ifDashboard) {
            ifDashboard = false;
            printForum();
        } else {
            ifDashboard = true;
            ArrayList<ArrayList<String>> sortedUpvotesArray = sortUpvotesArray();
            StringBuilder output = new StringBuilder(forumName + " " + forumCreationTime + "\n");
            for (ArrayList<String> strings : sortedUpvotesArray) {
                output.append(strings.get(0)).append(". ");
                output.append(strings.get(1)).append("\n");
                output.append("   - ").append(strings.get(2)).append(" ");
                output.append(strings.get(3)).append("\n");
                int upvotes = Integer.parseInt(strings.get(4));
                output.append("Upvotes: ").append(upvotes).append("\n");
                int replyTo = Integer.parseInt(strings.get(5));
                if (replyTo > 0) {
                    output.append("Reply to message no.: ").append(replyTo).append("\n");
                }
                output.append("\n");
            }
            String toSend = String.valueOf(output);
            toSend = toSend.replaceAll("\n", "§§§");
            printWriter.write(toSend);
            printWriter.println();
            printWriter.flush();
        }
        mainMethod();
    }

    public void changeTopic() throws Exception {
        String newTopic = bufferedReader.readLine();
        if (!newTopic.equals(" ")) {
            forumName = newTopic;
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss MM-dd-yyyy");
            forumCreationTime = LocalDateTime.now().format(format);
            writeToMessagesFile();
            printForum();
        }
        mainMethod();
    }

    public void sendMessageViaFileImport() throws Exception {
        String newPost = bufferedReader.readLine();
        String fullName = firstName + " " + lastName;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss MM-dd-yyyy");
        ArrayList<String> newPostArray = new ArrayList<>();
        newPostArray.add(0, Integer.toString(messagesArray.size() + 1));
        newPostArray.add(1, newPost);
        newPostArray.add(2, fullName);
        newPostArray.add(3, LocalDateTime.now().format(format));
        newPostArray.add(4, "0");
        newPostArray.add(5, "0");
        newPostArray.add(6, username);
        newPostArray.add(7, Integer.toString(messagesArray.size()));
        messagesArray.add(newPostArray);
        writeToMessagesFile();
        printForum();
        mainMethod();
    }

    public String convertToPrintFormat() throws Exception {
        readMessagesFile();
        StringBuilder output = new StringBuilder(forumName + " " + forumCreationTime + "\n");
        for (ArrayList<String> strings : messagesArray) {
            output.append(strings.get(0)).append(". ");
            output.append(strings.get(1)).append("\n");
            output.append("   - ").append(strings.get(2)).append(" ");
            output.append(strings.get(3)).append("\n");
            int upvotes = Integer.parseInt(strings.get(4));
            if (upvotes > 0) {
                output.append("Upvotes: ").append(upvotes).append("\n");
            }
            int replyTo = Integer.parseInt(strings.get(5));
            if (replyTo > 0) {
                output.append("Reply to message no.: ").append(replyTo).append("\n");
            }
            output.append("\n");
        }
        String toSend = String.valueOf(output);
        toSend = toSend.replaceAll("\n", "§§§");
        return toSend;
    }

    public void printForum() throws Exception {
        printWriter.write(convertToPrintFormat());
        printWriter.println();
        printWriter.flush();
    }

    public boolean checkAlreadyUpvoted(int messageNumber) {
        boolean output = false;
        String messageNumberString = Integer.toString(messageNumber);
        for (ArrayList<String> strings : upvotesArray) {
            if (strings.get(0).equals(username)) {
                for (int j = 1; j < strings.size(); j++) {
                    if (strings.get(j).equals(messageNumberString)) {
                        output = true;
                        break;
                    }
                }
            }
        }
        return output;
    }

    public boolean checkUsernameInUpvotesArray() {
        boolean output = false;
        for (ArrayList<String> strings : upvotesArray) {
            if (strings.get(0).equals(username)) {
                output = true;
                break;
            }
        }
        return output;
    }

    public void writeToUpvotesFile() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(upvoteFileName);
        PrintWriter pw = new PrintWriter(fos);
        StringBuilder toWrite = new StringBuilder();
        for (ArrayList<String> strings : upvotesArray) {
            for (String string : strings) {
                toWrite.append(string).append("§§§");
            }
            toWrite = new StringBuilder(toWrite.substring(0, toWrite.length() - 3) + "\n");
        }
        toWrite = new StringBuilder(toWrite.substring(0, toWrite.length() - 1));
        synchronized (object) {
            pw.println(toWrite);
        }
        pw.close();
    }

    public ArrayList<ArrayList<String>> sortUpvotesArray() {
        ArrayList<ArrayList<String>> sortedUpvotesArray = messagesArray;
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for (int i = 0; i < sortedUpvotesArray.size() - 1; i++) {
                if (Integer.parseInt(sortedUpvotesArray.get(i).get(4)) >
                        Integer.parseInt(sortedUpvotesArray.get(i + 1).get(4))) {
                    ArrayList<String> temp = sortedUpvotesArray.get(i);
                    sortedUpvotesArray.set(i, sortedUpvotesArray.get(i + 1));
                    sortedUpvotesArray.set(i + 1, temp);
                    swapped = true;
                }
            }
        }
        return sortedUpvotesArray;
    }

    public boolean checkUsernameNonexistence(String usernameInput) throws IOException {
        ArrayList<ArrayList<String>> accountsArray = new ArrayList<>();
        String accountsFile = "AccountsFile.txt";
        File f = new File(accountsFile);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();
        while (line != null) {
            String[] separatedLine = line.split("§§§");
            ArrayList<String> singleLine = new ArrayList<>(Arrays.asList(separatedLine));
            accountsArray.add(singleLine);
            line = bfr.readLine();
        }
        bfr.close();
        boolean output = false;
        int i = 0;
        while (!output && i < accountsArray.size()) {
            if (accountsArray.get(i).get(0).equals(usernameInput)) {
                output = true;
            }
            i++;
        }
        return !output;
    }

    public void writeToPointsFile() throws Exception {
        FileOutputStream fos = new FileOutputStream(pointsFileName, false);
        PrintWriter pw = new PrintWriter(fos);
        StringBuilder toWrite = new StringBuilder();
        for (ArrayList<String> strings : pointsArray) {
            toWrite.append(strings.get(0)).append("§§§").append(strings.get(1)).append("\n");
        }
        toWrite = new StringBuilder(toWrite.substring(0, toWrite.length() - 1));
        synchronized (object) {
            pw.println(toWrite);
        }
        pw.close();
    }

}

