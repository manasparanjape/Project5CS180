import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * DiscussionForum.java
 * <p>
 * Contains all possible methods to be executed after opening a discussion forum
 *
 * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
 * @version 11/15/2021
 */

public class DiscussionForum {
    private String forumName;
    private String messagesFileName;
    private String pointsFileName;
    private String upvotesFileName;
    private String firstName;
    private String lastName;
    private String username;
    private ArrayList<ArrayList<String>> messagesArray = new ArrayList<>();
    private ArrayList<ArrayList<String>> pointsArray = new ArrayList<>();
    private ArrayList<ArrayList<String>> upvotesArray = new ArrayList<>();
    private String forumCreationTime;

    private static String topicChangePrompt = "What do you want to change the topic to?";
    private static String studentSpecificMessagesPrompt = "Please enter username of the student who's posts you want"
            + "to view.";
    private static String gradingStudentPrompt = "Please enter the number of point you wish to assign this"
            + " student.";

    public DiscussionForum(String forumName, String messagesFileName, String pointsFileName, String firstName,
                           String lastName, String username, String upvotesFile, JTextArea textArea) {
        this.forumName = forumName;
        this.messagesFileName = messagesFileName;
        this.pointsFileName = pointsFileName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.upvotesFileName = upvotesFile;
        this.textArea = textArea;
    }

    //reads file where all upvotes are stored
    //stores that information into an arraylist
    public void readUpvotesFile() throws IOException {
        File f = new File(upvotesFileName);
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

    //checks if a user already upvoted a post/message by going through a arraylist called upvotesArray
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

    //reads file that stores all messages in discussion forum
    //splits this info and store into arraylist
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

    //checks whether username is found in upvotes array
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

    //adds all the messages stored in the discussion to a file
    //infromation is split by "§§§"
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

    //writes message to specificed file
    public void writeToMessagesFile() throws Exception {
        FileOutputStream fos = new FileOutputStream(messagesFileName, false);
        PrintWriter pw = new PrintWriter(fos);
        String toWrite = forumName + "§§§" + forumCreationTime;
        if (messagesArray.size() > 0) {
            toWrite += "\n" + convertMessagesArrayToFileString();
        }
        pw.println(toWrite);
        pw.close();
    }

    //writes information to upvote file
    //uses "§§§" as delimiter
    public void writeToUpvotesFile() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(upvotesFileName);
        PrintWriter pw = new PrintWriter(fos);
        StringBuilder toWrite = new StringBuilder();
        for (ArrayList<String> strings : upvotesArray) {
            for (String string : strings) {
                toWrite.append(string).append("§§§");
            }
            toWrite = new StringBuilder(toWrite.substring(0, toWrite.length() - 3) + "\n");
        }
        toWrite = new StringBuilder(toWrite.substring(0, toWrite.length() - 1));
        pw.println(toWrite);
        pw.close();
    }

    //read points file and store into an array
    //assigns the array to the points array
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

    //prints student messages/replies (if they have any)
    public void printSpecificStudentMessages(String studentUsername) {
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
            System.out.println("This student has not posted any replies to this forum yet.");
        } else {
            output = new StringBuilder(output.substring(0, output.length() - 1));
            System.out.println(output);
        }
    }

    //checks if a username exists
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

    //writes to points file
    public void writeToPointsFile() throws Exception {
        FileOutputStream fos = new FileOutputStream(pointsFileName, false);
        PrintWriter pw = new PrintWriter(fos);
        StringBuilder toWrite = new StringBuilder();
        System.out.println(pointsArray);
        for (ArrayList<String> strings : pointsArray) {
            toWrite.append(strings.get(0)).append("§§§").append(strings.get(1)).append("\n");
        }
        toWrite = new StringBuilder(toWrite.substring(0, toWrite.length() - 1));
        pw.println(toWrite);
        pw.close();
    }

    //sorts upvotes array using a simple sorting algorithm
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

    /*
     * GUI part
     * how to create the interface.
     */

    JTextArea textArea;

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
        return String.valueOf(output);
    }

    public void printMessagesInGUI() throws Exception {
        String output = convertToPrintFormat();
        textArea.setText(String.valueOf(output));
    }

    public void postMessageInGUI(String newPost, String messageNumberString) throws Exception {
        boolean errorCheck = false;
        boolean reply = false;
        int messageNumber = 0;
        try {
            if (messageNumberString == null || messageNumberString.isBlank()) {
                reply = false;
            } else {
                messageNumber = Integer.parseInt(messageNumberString);
                if (messageNumber < 0 || messageNumber > messagesArray.size()) {
                    String errorMessage = "You did not input a valid integer. Please input 0 or leave blank if you want to post a message. Please input an integer between 1 and "
                            + messagesArray.size() + " if you want to reply to a message.";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    errorCheck = true;
                } else {
                    reply = messageNumber != 0;
                }
            }
        } catch (NumberFormatException e) {
            String errorMessage = "You did not input an integer. Please input 0 or leave blank if you want to post a message. Please input an integer between 1 and "
                    + messagesArray.size() + " if you want to reply to a message.";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            errorCheck = true;
        }
        if (newPost == null || newPost.isBlank()) {
            String errorMessage = "Please enter a valid post(ie. Not all spaces or blank).";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!errorCheck){
            String fullName = firstName + " " + lastName;
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss MM-dd-yyyy");
            ArrayList<String> newPostArray = new ArrayList<>();
            if (reply) {
                newPostArray.add(Integer.toString(messageNumber + 1));
                newPostArray.add(newPost);
                newPostArray.add(fullName);
                newPostArray.add(LocalDateTime.now().format(format));
                newPostArray.add("0");
                newPostArray.add(Integer.toString(messageNumber));
                newPostArray.add(username);
                newPostArray.add(Integer.toString(messagesArray.size() + 1));
                messagesArray.add(messageNumber, newPostArray);
                for (int i = messageNumber + 1; i < messagesArray.size(); i++) {
                    messagesArray.get(i).set(0, Integer.toString(i + 1));
                }
            } else {
                newPostArray.add(0, Integer.toString(messagesArray.size() + 1));
                newPostArray.add(1, newPost);
                newPostArray.add(2, fullName);
                newPostArray.add(3, LocalDateTime.now().format(format));
                newPostArray.add(4, "0");
                newPostArray.add(5, "0");
                newPostArray.add(6, username);
                newPostArray.add(7, Integer.toString(messagesArray.size()));
                messagesArray.add(newPostArray);
            }
            writeToMessagesFile();
            printMessagesInGUI();
        }
    }

    public void upvoteMessageInGUI(String upvoteMessageNumber) throws Exception {
        int messageNumber = 0;
        try {
            messageNumber = Integer.parseInt(upvoteMessageNumber);
        } catch (NumberFormatException e) {
            String errorMessage = "You did not input an integer. Please input an integer between 1 and "
                    + messagesArray.size() + ".";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (messageNumber < 0 || messageNumber > messagesArray.size()) {
            if (checkAlreadyUpvoted(messageNumber)) {
                String errorMessage = "You have already upvoted this message. You cannot upvote the same message twice.";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String errorMessage = "You entered an invalid option. Please enter a number between 1 and "
                        + messagesArray.size() + ".";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            if (messageNumber > 0) {
                int upvotes = Integer.parseInt(messagesArray.get(messageNumber - 1).get(4));
                upvotes++;
                messagesArray.get(messageNumber - 1).set(4, Integer.toString(upvotes));
                if (checkUsernameInUpvotesArray()) {
                    for (ArrayList<String> strings : upvotesArray) {
                        if (strings.get(0).equals(username)) {
                            strings.add(Integer.toString(messageNumber));
                        }
                    }
                } else {
                    ArrayList<String> newUpvoteArray = new ArrayList<>();
                    newUpvoteArray.add(username);
                    newUpvoteArray.add(Integer.toString(messageNumber));
                    upvotesArray.add(newUpvoteArray);
                }
                writeToMessagesFile();
                writeToUpvotesFile();
                printMessagesInGUI();
            }
        }
    }

    public void deleteMessageInGUI(String messageNumberString) throws Exception {
        int messageNumber = 0;
        boolean numberValid = true;
        try {
            messageNumber = Integer.parseInt(messageNumberString);
        } catch (NumberFormatException e) {
            String errorMessage = "You did not input an integer. In order to delete a message, please input an integer between 1 and "
                    + messagesArray.size() + ".";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            numberValid = false;
        }
        if (numberValid) {
            if (messageNumber < 1 || messageNumber > messagesArray.size()) {
                String errorMessage = "You entered an invalid option. In order to delete a message, please enter a number between 1 and "
                        + messagesArray.size() + ".";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                numberValid = false;
            }
        }
        if (numberValid) {
            String fullName = firstName + " " + lastName;
            messagesArray.get(messageNumber - 1).set(1, "This message has been deleted by " + fullName);
            messagesArray.get(messageNumber - 1).set(4, "0");
            messagesArray.get(messageNumber - 1).set(7, String.valueOf(messagesArray.size()));
            writeToMessagesFile();
            printMessagesInGUI();
        }
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void showDashboardInGUI() {
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
        textArea.setText(String.valueOf(output));
    }

    public void gradeStudentsInGUI() throws Exception {
        boolean checkIfPointsExist = false;
        String studentUsername = JOptionPane.showInputDialog(null, studentSpecificMessagesPrompt, "Student Grading", JOptionPane.QUESTION_MESSAGE);
        int i = 0;
        while (!checkIfPointsExist && i < pointsArray.size()) {
            if (pointsArray.get(i).get(0).equals(studentUsername)) {
                checkIfPointsExist = true;
            }
            i++;
        }
        if (studentUsername == null || checkUsernameNonexistence(studentUsername)) {
            String errorMessage = "The student username you entered does not exist!";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            printSpecificStudentMessages(studentUsername);
            String pointsString;
            int points = 0;
            try {
                pointsString = JOptionPane.showInputDialog(null, gradingStudentPrompt, "Student Grading", JOptionPane.QUESTION_MESSAGE);
                if (pointsString != null) {
                    points = Integer.parseInt(pointsString);
                    ArrayList<String> output = new ArrayList<>();
                    output.add(studentUsername);
                    output.add(Integer.toString(points));
                    if (checkIfPointsExist) {
                        pointsArray.set(i - 1, output);
                    } else {
                        pointsArray.add(output);
                    }
                    writeToPointsFile();
                }
            } catch (Exception e) {
                String errorMessage = "You did not enter an integer. Please enter an integer in order to assign points.";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void changeTopicInGUI() throws Exception {
        String newTopic = JOptionPane.showInputDialog(null, topicChangePrompt, "New Topic", JOptionPane.QUESTION_MESSAGE);
        if (newTopic == null || newTopic.isBlank()) {
            String errorMessage = "Please enter a valid discussion forum name(ie. Not all spaces or blank).";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            forumName = newTopic;
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss MM-dd-yyyy");
            forumCreationTime = LocalDateTime.now().format(format);
            writeToMessagesFile();
            printMessagesInGUI();
        }
    }

    public void readNewPostFileInGUI() throws Exception {
        StringBuilder output = new StringBuilder();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(new JOptionPane());
        if (result == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            try {
                FileReader fr = new FileReader(f);
                BufferedReader bfr = new BufferedReader(fr);
                String line = bfr.readLine();
                while (line != null) {
                    output.append(line);
                    line = bfr.readLine();
                }
                output = new StringBuilder(output.toString().replace("\r\n", " ").replace("\n", " "));
                output = new StringBuilder(output.toString().replace(".", ". "));
                String newPost = String.valueOf(output);
                if (newPost == null || newPost.isBlank()) {
                    String errorMessage = "Please enter a valid post(ie. Not all spaces or blank).";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
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
                    printMessagesInGUI();
                }
            } catch (FileNotFoundException e) {
                String errorMessage = "The file you entered was not found.";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                String errorMessage = "Error parsing contents of the file.";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
