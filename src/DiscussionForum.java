/*
Methods required:
1) View the messages in the discussion forum  $$$
2) Post messages in the discussion forum  $$$
3) Reply to message in the discussion forum  $$$
4) Upvote messages $$$
5) Write the changes to file  $$$
6) Read the file and save messages in array  $$$
7) Change the topic name  $$$
8) Grade the responses  $$$
9) Dashboard
*/

import javax.annotation.processing.Filer;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DiscussionForum {
    private String forumName;
    private String messagesFileName;
    private String pointsFileName;
    private String firstName;
    private String lastName;
    private String username;
    private ArrayList<ArrayList<String>> messagesArray;
    private ArrayList<ArrayList<String>> pointsArray;
    private ArrayList<ArrayList<String>> sortedUpvotesArray;

    private final static String tryAgainPrompt = """
            Error Occurred! Do you want to try again?
            1. Yes
            2. No""";
    private final static String newPostPrompt = "What do you want to post?";
    private final static String replyNumberPrompt = "Which message do you want to reply to? Please enter the message number or 0 if you do not want to upvote any message.";
    private final static String replyMessagePrompt = "What is your reply?";
    private final static String upvotePrompt = "Which message do you want to upvote? Please enter the message number or 0 if you do not want to upvote any message.";
    private final static String topicChangePrompt = "What do you want to change the topic to?";
    private final static String studentSpecificMessagesPrompt = "Please enter username of the student who's posts you want to view.";
    private final static String gradingStudentPrompt = "Please enter the number of point you wish to assign this student.";

    public DiscussionForum(String forumName, String messagesFileName, String pointsFileName, String firstName, String lastName, String username) {
        this.forumName = forumName;
        this.messagesFileName = messagesFileName;
        this.pointsFileName = pointsFileName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public void readMessagesFile() throws Exception {
        File f = new File(messagesFileName);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
        String line = bfr.readLine();
        line = bfr.readLine();
        while (line != null) {
            String[] separatedLine = line.split("---");
            ArrayList<String> singleLine = new ArrayList<String>(Arrays.asList(separatedLine));
            output.add(singleLine);
            line = bfr.readLine();
        }
        bfr.close();
        messagesArray = output;
    }

    public void printMessages() {
        String output = "";
        for (int i = 0; i < messagesArray.size(); i++) {
            output += messagesArray.get(i).get(0) + ". ";
            output += messagesArray.get(i).get(1) + "\n";
            output += "   - " + messagesArray.get(i).get(2) + " ";
            output += messagesArray.get(i).get(3) + "\n";
            int upvotes = Integer.parseInt(messagesArray.get(i).get(4));
            if (upvotes > 0) {
                output += "Upvotes: " + upvotes + "\n";
            }
            int replyTo = Integer.parseInt(messagesArray.get(i).get(5));
            if (replyTo > 0) {
                output += "Reply to message no.: " + replyTo + "\n";
            }
            output += "\n";
        }
        System.out.println(output);
    }

    public String convertMessagesArrayToFileString() throws Exception {
        String output = forumName + "\n";
        for (int i = 0; i < messagesArray.size(); i++) {
            String line = "";
            for (int j = 0; j < 7; j++) {
                line += messagesArray.get(i).get(j) + "---";
            }
            line = output.substring(0, line.length() - 3);
            output += line + "\n";
        }
        return output;
    }

    public void writeToMessagesFile() throws Exception {
        FileOutputStream fos = new FileOutputStream(messagesFileName, false);
        PrintWriter pw = new PrintWriter(fos);
        String toWrite = forumName + "\n" + convertMessagesArrayToFileString();
        pw.println(toWrite);
        pw.close();
    }

    public void postMessage() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        do {
            System.out.println(newPostPrompt);
            String newPost = scan.nextLine();
            if (newPost == null || newPost.isBlank()) {
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
                String fullName = firstName + lastName;
                DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss MM-dd-yyyy");
                ArrayList<String> newPostArray = new ArrayList<>(6);
                newPostArray.set(0, Integer.toString(messagesArray.size() + 1));
                newPostArray.set(1, newPost);
                newPostArray.set(2, fullName);
                newPostArray.set(3, LocalDateTime.now().format(format));
                newPostArray.set(4, "0");
                newPostArray.set(5, "0");
                newPostArray.set(6, username);
                newPostArray.set(7, Integer.toString(messagesArray.size()));
                messagesArray.add(newPostArray);
                writeToMessagesFile();
                ArrayList<String> newPostPointsArray = new ArrayList<>(4);
                newPostPointsArray.set(0, Integer.toString(sortedUpvotesArray.size() + 1));
                newPostPointsArray.set(1, newPost);
                newPostPointsArray.set(2, fullName);
                newPostPointsArray.set(3, "0");
                sortedUpvotesArray.add(newPostPointsArray);
            }
        } while (loop);
    }

    public void replyToPost() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        int replyNumber;
        do {
            System.out.println(replyNumberPrompt);
            replyNumber = scan.nextInt();
            if (replyNumber < 0 || replyNumber > messagesArray.size()) {
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
            }
        } while (loop);

        if (replyNumber > 0 && replyNumber < messagesArray.size()) {
            do {
                System.out.println(replyMessagePrompt);
                String newPost = scan.nextLine();
                if (newPost == null || newPost.isBlank()) {
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
                    String fullName = firstName + lastName;
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss MM-dd-yyyy");
                    ArrayList<String> newPostArray = new ArrayList<String>(6);
                    newPostArray.set(0, Integer.toString(replyNumber + 1));
                    newPostArray.set(1, newPost);
                    newPostArray.set(2, fullName);
                    newPostArray.set(3, LocalDateTime.now().format(format));
                    newPostArray.set(4, "0");
                    newPostArray.set(5, Integer.toString(replyNumber));
                    newPostArray.set(6, username);
                    messagesArray.add(replyNumber, newPostArray);
                    for (int i = replyNumber + 1; i < messagesArray.size(); i++) {
                        messagesArray.get(i).set(0, Integer.toString(i + 1));
                    }
                    writeToMessagesFile();
                    ArrayList<String> newPostPointsArray = new ArrayList<>(4);
                    newPostPointsArray.set(0, Integer.toString(sortedUpvotesArray.size() + 1));
                    newPostPointsArray.set(1, newPost);
                    newPostPointsArray.set(2, fullName);
                    newPostPointsArray.set(3, "0");
                    sortedUpvotesArray.add(newPostPointsArray);
                    newPostArray.set(7, Integer.toString(messagesArray.size()));
                }
            } while (loop);
        }
    }

    public void upvote() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        do {
            System.out.println(upvotePrompt);
            int messageNumber = scan.nextInt();
            if (messageNumber < 0 || messageNumber > messagesArray.size()) {
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
                if (messageNumber > 0) {
                    int upvotes = Integer.parseInt(messagesArray.get(messageNumber).get(4));
                    upvotes++;
                    messagesArray.get(messageNumber).set(4, Integer.toString(upvotes));
                    writeToMessagesFile();
                    ArrayList<String> temp = messagesArray.get(messageNumber);
                    int index = sortedUpvotesArray.indexOf(temp);
                    temp = sortedUpvotesArray.get(index);
                    sortUpvotesArray();
                    int newIndex = sortedUpvotesArray.indexOf(temp);
                    messagesArray.set()
                }
            }
        } while (loop);
    }

    public void changeTopic() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        do {
            System.out.println(topicChangePrompt);
            String newTopic = scan.nextLine();
            if (newTopic == null || newTopic.isBlank()) {
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
                forumName = newTopic;
                writeToMessagesFile();
            }
        } while (loop) ;
    }

    public void readPointsFile() throws Exception {
        File f = new File(pointsFileName);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        String line = bfr.readLine();
        while (line != null) {
            String[] separatedLine = line.split("---");
            ArrayList<String> singleLine = new ArrayList<String>(Arrays.asList(separatedLine));
            output.add(singleLine);
            line = bfr.readLine();
        }
        pointsArray = output;
    }

    public void printSpecificStudentMessages() {
        Scanner scan = new Scanner(System.in);
        System.out.println(studentSpecificMessagesPrompt);
        String studentUsername = scan.nextLine();
        String output = "Messages by " + studentUsername + "\n";
        for (int i = 0; i < messagesArray.size(); i++) {
            if (messagesArray.get(i).get(6).equals(studentUsername)) {
                output += messagesArray.get(i).get(0) + ". ";
                output += messagesArray.get(i).get(1) + "\n";
                output += "   - " + messagesArray.get(i).get(2) + " ";
                output += messagesArray.get(i).get(3) + "\n";
                int upvotes = Integer.parseInt(messagesArray.get(i).get(4));
                if (upvotes > 0) {
                    output += "Upvotes: " + upvotes + "\n";
                }
                int replyTo = Integer.parseInt(messagesArray.get(i).get(5));
                if (replyTo > 0) {
                    output += "Reply to message no.: " + replyTo + "\n";
                }
                output += "\n";
            }
        }
        System.out.println(output);
    }

    public boolean checkUsernameExistence(String username) {
        boolean output = false;
        int i = 0;
        while (!output && i < messagesArray.size()) {
            if (messagesArray.get(i).get(6).equals(username)) {
                output = true;
            }
            i++;
        }
        return output;
    }

    public void responseGrading() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        readPointsFile();
        boolean checkIfPointsExist = false;
        do {
            System.out.println(studentSpecificMessagesPrompt);
            String studentUsername = scan.nextLine();
            int i = 0;
            while (!checkIfPointsExist && i < pointsArray.size()) {
                if (pointsArray.get(i).get(0).equals(studentUsername)) {
                    checkIfPointsExist = true;
                }
                i++;
            }
            if (studentUsername == null || !checkUsernameExistence(studentUsername) || checkIfPointsExist) {
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
                System.out.println(gradingStudentPrompt);
                int points = scan.nextInt();
                scan.nextLine();
                ArrayList<String> output = new ArrayList<String>(2);
                output.set(0, studentUsername);
                output.set(1, Integer.toString(points));
                pointsArray.add(output);
                writeToPointsFile();
            }
        } while (loop) ;
    }

    public void writeToPointsFile() throws Exception {
        FileOutputStream fos = new FileOutputStream(pointsFileName, false);
        PrintWriter pw = new PrintWriter(fos);
        String toWrite = "";
        for (int i = 0; i < pointsArray.size(); i++) {
            toWrite += pointsArray.get(i).get(0) + "---" + pointsArray.get(i).get(1) + "\n";
        }
        pw.println(toWrite);
        pw.close();
    }

    public void sortUpvotesArray() {
        for (int i = 0; i < sortedUpvotesArray.size(); i++) {
            if (Integer.parseInt(sortedUpvotesArray.get(i).get(3)) > Integer.parseInt(sortedUpvotesArray.get(i + 1).get(3))) {
                ArrayList<String> temp = sortedUpvotesArray.get(i);
                sortedUpvotesArray.set(i, sortedUpvotesArray.get(i + 1));
                sortedUpvotesArray.set(i + 1, temp);
            }
        }
        for (int i = 0; i < sortedUpvotesArray.size(); i++) {
            sortedUpvotesArray.get(i).set(0, Integer.toString(i + 1));
        }
        for (int i = 0; i < messagesArray.size(); i++) {
            messagesArray.get(i).set(7, sortedUpvotesArray.);
        }
    }
}
