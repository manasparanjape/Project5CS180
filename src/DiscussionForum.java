import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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
    private final String messagesFileName;
    private final String pointsFileName;
    private final String upvotesFile;
    private final String firstName;
    private final String lastName;
    private final String username;
    private ArrayList<ArrayList<String>> messagesArray = new ArrayList<>();
    private ArrayList<ArrayList<String>> pointsArray = new ArrayList<>();
    private ArrayList<ArrayList<String>> upvotesArray = new ArrayList<>();
    private String forumCreationTime;

    private static String newPostPrompt = "What do you want to post?";
    private static String replyNumberPrompt = "Which message do you want to reply to? " +
            "Please enter the message number or 0 if you do not want to reply any message.";
    private static String replyMessagePrompt = "What is your reply?";
    private static String upvotePrompt = "Which message do you want to upvote? " +
            "Please enter the message number or 0 if you do not want to upvote any message.";
    private static String topicChangePrompt = "What do you want to change the topic to?";
    private static String studentSpecificMessagesPrompt = "Please enter username of the student who's posts you want"
            + "to view.";
    private static String gradingStudentPrompt = "Please enter the number of point you wish to assign this"
            + " student.";
    private static String methodOfNewPostPrompt = "How do you want to write the new post?\n" +
            "1) Write the post via terminal line.\n2) Import text file with post in file.\n3) Cancel.";
    private static String methodOfNewReplyPrompt = "How do you want to write the new reply?\n1)" +
            "1) Write the reply via terminal line.\n2) Import text file with reply in file.\n3) Cancel.";
    private static String fileNamePrompt = "Please enter the file name and path of the file which contains the "
            + "post";

    public DiscussionForum(String forumName, String messagesFileName, String pointsFileName, String firstName,
                           String lastName, String username, String upvotesFile) {
        this.forumName = forumName;
        this.messagesFileName = messagesFileName;
        this.pointsFileName = pointsFileName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.upvotesFile = upvotesFile;
    }

    //reads user entered file and returns a string of the contents called output
    public String readNewPostFile(Scanner scan) {
        StringBuilder output = new StringBuilder();
        try {
            System.out.println(fileNamePrompt);
            String fileName = scan.nextLine();
            File f = new File(fileName);
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                output.append(line);
                line = bfr.readLine();
            }
            output = new StringBuilder(output.toString().replace("\r\n", " ").replace("\n", " "));
            output = new StringBuilder(output.toString().replace(".", ". "));
        } catch (FileNotFoundException e) {
            System.out.println("The file you entered was not found.");
        } catch (IOException e) {
            System.out.println("Error parsing contents of the file.");
        }
        return output.toString();
    }

    //reads file where all upvotes are stored
    //stores that information into an arraylist
    public void readUpvotesFile() throws IOException {
        File f = new File(upvotesFile);
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

    //reads Messages file, then adds all information stored in discussion forum to a string
    public void printMessages() throws Exception {
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
        System.out.println(output);
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

    //posts a message
    //adds that new array to the messages Array
    public void postMessage(Scanner scan) throws Exception {
        boolean cancelled = false;
        int option = 0;
        while (option != 3 && !cancelled) {
            System.out.println(methodOfNewPostPrompt);
            try {
                option = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("Please enter a valid number!");
                option = 0;
                scan.nextLine();
                continue;
            }
            String newPost = "";
            if (option == 1) {
                System.out.println(newPostPrompt);
                newPost = scan.nextLine();
            } else if (option == 2) {
                newPost = readNewPostFile(scan);
            } else if (option != 3) {
                System.out.println("You entered an invalid option. Please enter a number between 1 and 3.");
            }
            if (option == 1 || option == 2) {
                if (newPost == null || newPost.isBlank()) {
                    System.out.println("Please enter a valid post(ie. Not all spaces or blank).");
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
                    writeToMessagesFile();
                    cancelled = true;
                }
            }
        }
    }

    //replies to post
    //updates all the information in the messages file
    public void replyToPost(Scanner scan) throws Exception {
        int replyNumber = 0;
        boolean cancelled = false;
        try {
            System.out.println(replyNumberPrompt);
            replyNumber = scan.nextInt();
            scan.nextLine();
        } catch (Exception e) {
            System.out.println("You did not enter an integer. Please enter a valid integer between 1 and "
                    + messagesArray.size() + ".");
            scan.nextLine();
        }
        if (replyNumber < 0 || replyNumber > messagesArray.size()) {
            System.out.println("You entered an invalid number. Please enter a valid message number between 1 and "
                    + messagesArray.size() + ".");
        }
        if (replyNumber > 0 && replyNumber < messagesArray.size() + 1) {
            int option = 0;
            while (option != 3 && !cancelled) {
                try {
                    System.out.println(methodOfNewReplyPrompt);
                    option = scan.nextInt();
                    scan.nextLine();
                } catch (Exception e) {
                    System.out.println("Please enter a valid number!");
                    scan.nextLine();
                    option = 0;
                    continue;
                }
                String newPost = "";
                if (option == 1) {
                    System.out.println(replyMessagePrompt);
                    newPost = scan.nextLine();
                } else if (option == 2) {
                    newPost = readNewPostFile(scan);
                } else if (option != 3) {
                    System.out.println("You entered an invalid option. Please enter a number between 1 and 3.");
                }
                if (option == 1 || option == 2) {
                    if (newPost == null || newPost.isBlank()) {
                        System.out.println("Please enter a valid reply(ie. Not all spaces or blank).");
                    } else {
                        String fullName = firstName + lastName;
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss MM-dd-yyyy");
                        ArrayList<String> newPostArray = new ArrayList<>();
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
                        cancelled = true;
                    }
                }
            }
        }
    }

    //upvotes a post/message
    //checks if user upvoted a message or not
    public void upvote(Scanner scan) throws Exception {
        int messageNumber = 0;
        try {
            System.out.println(upvotePrompt);
            messageNumber = scan.nextInt();
            scan.nextLine();
        } catch (Exception e) {
            System.out.println("You did not input an integer. Please input an integer between 1 and "
                    + messagesArray.size() + ".");
            scan.nextLine();
        }
        if (messageNumber < 0 || messageNumber > messagesArray.size()) {
            if (checkAlreadyUpvoted(messageNumber)) {
                System.out.println("You already upvoted this message. You can only upvote a message once");
            } else {
                System.out.println("You entered an invalid option. Please enter a number between 1 and "
                        + messagesArray.size() + ".");
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
            }
        }
    }

    //writes information to upvote file
    //uses "§§§" as delimiter
    public void writeToUpvotesFile() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(upvotesFile);
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

    //changes topic/updates time changed
    public void changeTopic(Scanner scan) throws Exception {
        System.out.println(topicChangePrompt);
        String newTopic = scan.nextLine();
        if (newTopic == null || newTopic.isBlank()) {
            System.out.println("Please enter a valid discussion forum name(ie. Not all spaces or blank).");
        } else {
            forumName = newTopic;
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss MM-dd-yyyy");
            forumCreationTime = LocalDateTime.now().format(format);
            writeToMessagesFile();
        }
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

    //prints student messages and grade
    //adds the new information to the points array and updates the points file
    public void responseGrading(Scanner scan) throws Exception {
        boolean checkIfPointsExist = false;
        System.out.println(studentSpecificMessagesPrompt);
        String studentUsername = scan.nextLine();
        int i = 0;
        while (!checkIfPointsExist && i < pointsArray.size()) {
            if (pointsArray.get(i).get(0).equals(studentUsername)) {
                checkIfPointsExist = true;
            }
            i++;
        }
        if (studentUsername == null || checkUsernameNonexistence(studentUsername)) {
            System.out.println("The student username you entered does not exist!");
        } else {
            printSpecificStudentMessages(studentUsername);
            int points;
            try {
                System.out.println(gradingStudentPrompt);
                points = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("Please enter a valid number!");
                points = 0;
                scan.nextLine();
            }
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

    //displays messages in order of lowest to highest upvotes
    public void showDashboard() {
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
        System.out.println(output);
        System.out.println("Check");
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
}
