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
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DiscussionForum {
    private String forumName;
    private String messagesFileName;
    private String pointsFileName;
    private String upvotesFile;
    private String firstName;
    private String lastName;
    private String username;
    private ArrayList<ArrayList<String>> messagesArray = new ArrayList<>();
    private ArrayList<ArrayList<String>> pointsArray = new ArrayList<>();
    private ArrayList<ArrayList<String>> upvotesArray = new ArrayList<>();
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

    public DiscussionForum(String forumName, String messagesFileName, String pointsFileName, String firstName, String lastName, String username, String upvotesFile) {
        this.forumName = forumName;
        this.messagesFileName = messagesFileName;
        this.pointsFileName = pointsFileName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.upvotesFile = upvotesFile;
    }

    public void readUpvotesFile() throws IOException {
        File f = new File(upvotesFile);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        String line = bfr.readLine();
        while (line != null) {
            String[] separatedLine = line.split("---");
            System.out.println(separatedLine);
            ArrayList<String> singleLine = new ArrayList<>(Arrays.asList(separatedLine));
            output.add(singleLine);
            line = bfr.readLine();
        }
        bfr.close();
        upvotesArray = output;
    }

    public boolean checkAlreadyUpvoted(int messageNumber) {
        boolean output = false;
        String messageNumberString = Integer.toString(messageNumber);
        for (int i = 0; i < upvotesArray.size(); i++) {
            if (upvotesArray.get(i).get(0).equals(username)) {
                for (int j = 1; j < upvotesArray.get(i).size(); j++) {
                    if (upvotesArray.get(i).get(j).equals(messageNumberString)) {
                        output = true;
                    }
                }
            }
        }
        return output;
    }

    public void readMessagesFile() throws Exception {
        File f = new File(messagesFileName);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
        String line = bfr.readLine();
        //line = bfr.readLine();
        while (line != null) {
            String[] separatedLine = line.split("---");
            ArrayList<String> singleLine = new ArrayList<String>(Arrays.asList(separatedLine));
            output.add(singleLine);
            line = bfr.readLine();
        }
        output.remove(0);
        if (output.size() > 0) {
            output.remove(output.size() - 1);
        }
        bfr.close();
        messagesArray = output;
    }

    public boolean checkUsernameInUpvotesArray() {
        boolean output = false;
        for (int i = 0; i < upvotesArray.size(); i++) {
            if (upvotesArray.get(i).get(0).equals(username)) {
                output = true;
            }
        }
        return output;
    }

    public void printMessages() throws Exception {
        readMessagesFile();
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
        String output = "";
        for (int i = 0; i < messagesArray.size(); i++) {
            String line = "";
            for (int j = 0; j < 8; j++) {
                line += messagesArray.get(i).get(j) + "---";
            }
            line = line.substring(0, line.length() - 3);
            output += line + "\n";
        }
        return output;
    }

    public void writeToMessagesFile() throws Exception {
        FileOutputStream fos = new FileOutputStream(messagesFileName, false);
        PrintWriter pw = new PrintWriter(fos);
        String toWrite = forumName;
        if (messagesArray.size() > 0) {
            toWrite += "\n" + convertMessagesArrayToFileString();
        }
        System.out.println(toWrite);
        pw.println(toWrite);
        pw.close();
    }

    public void postMessage() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        do {
            loop = false;
            System.out.println(newPostPrompt);
            String newPost = scan.nextLine();
            if (newPost == null || newPost.isBlank()) {
                int tryAgain;
                do {
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
                //System.out.println(messagesArray.size());
                //System.out.println(messagesArray.get(0).size());
                writeToMessagesFile();
                /*
                ArrayList<String> newPostPointsArray = new ArrayList<>(4);
                newPostPointsArray.set(0, Integer.toString(sortedUpvotesArray.size() + 1));
                newPostPointsArray.set(1, newPost);
                newPostPointsArray.set(2, fullName);
                newPostPointsArray.set(3, "0");
                sortedUpvotesArray.add(newPostPointsArray);*/
            }
        } while (loop);
    }

    public void replyToPost() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        int replyNumber;
        do {
            loop = false;
            System.out.println(replyNumberPrompt);
            replyNumber = scan.nextInt();
            scan.nextLine();
            if (replyNumber < 0 || replyNumber > messagesArray.size()) {
                int tryAgain;
                do {
                    System.out.println(tryAgainPrompt);
                    tryAgain = scan.nextInt();
                    scan.nextLine();
                    if (tryAgain == 1) {
                        loop = true;
                    }
                } while (tryAgain != 1 && tryAgain != 2);
            }
        } while (loop);

        if (replyNumber > 0 && replyNumber < messagesArray.size() + 1) {
            do {
                loop = false;
                System.out.println(replyMessagePrompt);
                String newPost = scan.nextLine();
                if (newPost == null || newPost.isBlank()) {
                    int tryAgain;
                    do {
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
                    ArrayList<String> newPostArray = new ArrayList<>();
                    System.out.println(newPostArray.size());
                    newPostArray.add(Integer.toString(replyNumber + 1));
                    newPostArray.add(newPost);
                    newPostArray.add(fullName);
                    newPostArray.add(LocalDateTime.now().format(format));
                    newPostArray.add("0");
                    newPostArray.add(Integer.toString(replyNumber));
                    newPostArray.add(username);
                    newPostArray.add(Integer.toString(messagesArray.size() + 1));
                    messagesArray.add(replyNumber, newPostArray);
                    for (int i = replyNumber + 1; i < messagesArray.size(); i++) {
                        messagesArray.get(i).set(0, Integer.toString(i + 1));
                    }
                    writeToMessagesFile();
                    /*ArrayList<String> newPostPointsArray = new ArrayList<>(4);
                    newPostPointsArray.set(0, Integer.toString(sortedUpvotesArray.size() + 1));
                    newPostPointsArray.set(1, newPost);
                    newPostPointsArray.set(2, fullName);
                    newPostPointsArray.set(3, "0");
                    sortedUpvotesArray.add(newPostPointsArray);
                    newPostArray.set(7, Integer.toString(messagesArray.size()));*/
                }
            } while (loop);
        }
    }

    public void upvote() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        do {
            loop = false;
            System.out.println(upvotePrompt);
            int messageNumber = scan.nextInt();
            if (messageNumber < 0 || messageNumber > messagesArray.size() || checkAlreadyUpvoted(messageNumber)) {
                int tryAgain;
                do {
                    System.out.println(tryAgainPrompt);
                    tryAgain = scan.nextInt();
                    scan.nextLine();
                    if (tryAgain == 1) {
                        loop = true;
                    }
                } while (tryAgain != 1 && tryAgain != 2);
            } else {
                if (messageNumber > 0) {
                    int upvotes = Integer.parseInt(messagesArray.get(messageNumber - 1).get(4));
                    upvotes++;
                    messagesArray.get(messageNumber - 1).set(4, Integer.toString(upvotes));
                    if (checkUsernameInUpvotesArray()) {
                        for (int i = 0; i < upvotesArray.size(); i++) {
                            if (upvotesArray.get(i).get(0).equals(username)) {
                                upvotesArray.get(i).add(Integer.toString(messageNumber));
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
                }
                /*ArrayList<String> temp = messagesArray.get(messageNumber);
                int index = sortedUpvotesArray.indexOf(temp);
                temp = sortedUpvotesArray.get(index);
                sortUpvotesArray();
                int newIndex = sortedUpvotesArray.indexOf(temp);
                messagesArray.set()*/
            }
        } while (loop);
    }

    public void writeToUpvotesFile() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(upvotesFile);
        PrintWriter pw = new PrintWriter(fos);
        String toWrite = "";
        for (int i = 0; i < upvotesArray.size(); i++) {
            for (int j = 0; j < upvotesArray.get(i).size(); j++) {
                toWrite += upvotesArray.get(i).get(j) + "---";
            }
            toWrite = toWrite.substring(0, toWrite.length() - 3) + "\n";
        }
        toWrite = toWrite.substring(0, toWrite.length() - 1);
        pw.println(toWrite);
        pw.close();
    }

    public void changeTopic() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        do {
            loop = false;
            System.out.println(topicChangePrompt);
            String newTopic = scan.nextLine();
            if (newTopic == null || newTopic.isBlank()) {
                int tryAgain;
                do {
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
            loop = false;
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
                ArrayList<String> output = new ArrayList<>(2);
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



    /*public void sortUpvotesArray() {
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
    }*/
}
