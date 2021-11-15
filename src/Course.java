import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Course.java
 *
 * Contains forum selection level options for students and teacher as well as methods 
 * for forum creation, deletion and point viewing
 *
 * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
 *
 * @version 11/15/2021
 *
 */

public class Course {
    private String courseName;
    private String username;
    private String firstName;
    private String lastName;
    private String discussionBoardsListFileName;
    private DiscussionForum discussionForum;
    private static String newForumNamePrompt = "What would you like to name the new discussion forum?";
    private static String newForumCreated = "New forum has been created!";
    private static String deleteForumPrompt = "Which forum would you like to delete?";
    private static String forumDeleted = "The forum you selected has been deleted";
    private static String forumSelectionPrompt = "Which discussion forum would you like to open?";
    private static String forumNamePrompt = "Please enter the file name and path of the file which "
        + "contains the new forum name.";
    private static String discussionForumEnteredStudentPrompt = "Please enter the option number of what "
        + "you want to do.\n"
            + "1) View discussion forum\n2) Post message\n3) Reply to message\n4) Upvote message\n5) Exit forum";
    private static String discussionForumEnteredTeacherPrompt = "Please enter the option number "
        + "of what you want to do.\n" +
            "1) View messages\n2) Post message\n3) Reply to message\n" +
        "4) Grade student messages\n5) Change topic\n6) View dashboard\n" +
            "7) Exit forum";
    private static String methodOfNewForumPrompt = "How do you want to create the new discussion forum?\n" +
            "1) Enter the new forum name via terminal.\n2) Import text file with the forum name.\n3) Cancel.";

    private ArrayList<String> forumList = new ArrayList<>();
    //prints a list of forums
    public void printForumList() {
        StringBuilder output = new StringBuilder();
        for (String s : forumList) {
            output.append(s).append("\n");
        }
        output = new StringBuilder(output.substring(0, output.length() - 1));
        if (output.toString().isEmpty()) {
            System.out.println("No courses created yet.");
        } else {
            System.out.println(output);
        }
    }
    
    public Course(String courseName, String username, String firstName, String lastName, 
                  DiscussionForum discussionForum, String discussionBoardsListFileName) {
        this.courseName = courseName;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.discussionForum = discussionForum;
        this.discussionBoardsListFileName = discussionBoardsListFileName;
    }
    //takes input for file location and puts contents onto string
    public String readNewForumFile() {
        StringBuilder output = new StringBuilder();
        try {
            System.out.println(forumNamePrompt);
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
    //reads discussionBoardsListFileName and puts onto string
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
    //checks if the discussion forum exists
    public boolean discussionForumExists(String forumName) {
        if (forumList == null) {
            return false;
        } else {
            return forumList.contains(forumName);
        }
    }
    //asks user how they want to create a forum
    //creates forum accordingly, adds to forumList array list
    public void createForum(Scanner scan) {
        boolean cancelled = false;
        int option = 0;
        while (option != 3 && !cancelled) {
            try {
                System.out.println(methodOfNewForumPrompt);
                option = scan.nextInt();
                scan.nextLine();
                String newForumName = "";
                if (option == 1) {
                    System.out.println(newForumNamePrompt);
                    newForumName = scan.nextLine();
                } else if (option == 2) {
                    newForumName = readNewForumFile();
                } else if (option != 3) {
                    System.out.println("You entered an invalid option. Please enter a number between 1 and 3.");
                }
                if (option == 1 || option == 2) {
                    if (newForumName == null || newForumName.isBlank()) {
                        System.out.println("Please enter a valid discussion forum name" +
                                "(ie. Not all spaces or blank).");
                    } else if (discussionForumExists(newForumName)) {
                        System.out.println("A discussion forum with that name already exists in this course.");
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
                        System.out.println(newForumCreated);
                        pw.close();
                        cancelled = true;
                    }
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number!");
                option = 0;
                scan.nextLine();
            }
        }
    }
    //deletes a forum
    //rewrites all the discussion boards file names to the String Builder object called output
    public void deleteForum(Scanner scan) throws Exception {
        System.out.println(deleteForumPrompt);
        String toDeleteForum = scan.nextLine();
        boolean deleted = false;
        if (toDeleteForum == null || !discussionForumExists(toDeleteForum)) {
            if (!discussionForumExists(toDeleteForum)) {
                System.out.println("The discussion forum you entered does not exist.");
            } else {
                System.out.println("Please enter a valid discussion forum name(ie. Not all spaces or blank).");
            }
        } else {
            forumList.remove(toDeleteForum);
            File f = new File(courseName + "-" + toDeleteForum + "-messages" + ".txt");
            deleted = f.delete();
            f = new File(courseName + "-" + toDeleteForum + "-points" + ".txt");
            deleted &= f.delete();
            f = new File(courseName + "-" + toDeleteForum + "-upvotes" + ".txt");
            deleted &= f.delete();
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
        if (deleted) {
            System.out.println(forumDeleted);
        }
    }
    //returns points for a student for each forum
    //by searching through points txt file
    public void viewPoints() throws IOException {
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
        System.out.println(output);
    }
    //displays list of discussion forums and asks user to open one
    public void studentDiscussionForumOpened(Scanner scan) throws Exception {
        readForumListFile();
        printForumList();
        System.out.println(forumSelectionPrompt);
        String selectedForum = scan.nextLine();
        if (selectedForum == null || !discussionForumExists(selectedForum)) {
            System.out.println("The discussion forum you entered does not exist!");
        } else {
            String discussionForumMessagesFileName = courseName + "-" + selectedForum + "-messages" + ".txt";
            String discussionForumPointsFileName = courseName + "-" + selectedForum + "-points" + ".txt";
            String discussionForumUpvotesFileName = courseName + "-" + selectedForum + "-upvotes" + ".txt";
            discussionForum = new DiscussionForum(selectedForum, discussionForumMessagesFileName,
                    discussionForumPointsFileName, firstName, lastName, username, discussionForumUpvotesFileName, scan);
            discussionForum.readMessagesFile();
            discussionForum.readPointsFile();
            discussionForum.readUpvotesFile();
            showDiscussionForumMainMethodStudent();
        }
    }
       //displays list of discussion forums and asks user to open one
    public void teacherDiscussionForumOpened(Scanner scan) throws Exception {
        readForumListFile();
        printForumList();
        System.out.println(forumSelectionPrompt);
        String selectedForum = scan.nextLine();
        if (selectedForum == null || !discussionForumExists(selectedForum)) {
            System.out.println("The discussion forum you entered does not exist!");
        } else {
            String discussionForumMessagesFileName = courseName + "-" + selectedForum + "-messages" + ".txt";
            String discussionForumPointsFileName = courseName + "-" + selectedForum + "-points" + ".txt";
            String discussionForumUpvotesFileName = courseName + "-" + selectedForum + "-upvotes" + ".txt";
            discussionForum = new DiscussionForum(selectedForum, discussionForumMessagesFileName,
                    discussionForumPointsFileName, firstName, lastName, username, discussionForumUpvotesFileName, scan);
            discussionForum.readMessagesFile();
            discussionForum.readPointsFile();
            discussionForum.readUpvotesFile();
            showDiscussionForumMainMethodTeacher();
        }
    }
    //gives students the choice to: print messages, post messages, 
    //reply to a post, upvote a message, or exit
    public void showDiscussionForumMainMethodStudent(Scanner scan) throws Exception {
        int option = 0;
        while (option != 5) {
            try {
                System.out.println(discussionForumEnteredStudentPrompt);
                option = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("Please enter a valid number!");
                option = 0;
                scan.nextLine();
            }
            if (option < 1 || option > 5) {
                System.out.println("You entered an invalid number. Please enter a number between 1 and 5.");
            } else {
                switch (option) {
                    case 1 -> discussionForum.printMessages();
                    case 2 -> discussionForum.postMessage();
                    case 3 -> discussionForum.replyToPost();
                    case 4 -> discussionForum.upvote();
                }
            }
        }
    }
    //gives teachers the option to: print messages, post messages, reply to a post
    //grade a response, change the forum topic, show dashboard, or exit
    public void showDiscussionForumMainMethodTeacher(Scanner scan) {
        int option = 0;
        while (option != 7) {
            try {
                System.out.println(discussionForumEnteredTeacherPrompt);
                option = scan.nextInt();
                scan.nextLine();
                if (option < 1 || option > 7) {
                    System.out.println("You entered an invalid number. Please enter a number between 1 and 6.");
                } else {
                    switch (option) {
                        case 1 -> discussionForum.printMessages();
                        case 2 -> discussionForum.postMessage();
                        case 3 -> discussionForum.replyToPost();
                        case 4 -> discussionForum.responseGrading();
                        case 5 -> discussionForum.changeTopic();
                        case 6 -> discussionForum.showDashboard();
                    }
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number");
                scan.nextLine();
                option = 0;

            }
        }
    }
}
