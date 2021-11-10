/*
Methods Required:
1) Enter a specific discussion forum
2) Create a new discussion forum
3) Delete a discussion forum
*/

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Course {
    private final String courseName;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String discussionBoardsListFileName;
    //private final String discussionsPointsFileName;
    private DiscussionForum discussionForum;
    private final static String tryAgainPrompt = """
            Error Occurred! Do you want to try again?
            1. Yes
            2. No""";
    private final static String newForumNamePrompt = "What would you like to name the new discussion forum?";
    private final static String newForumCreated = "New forum has been created!";
    private final static String deleteForumPrompt = "Which forum would you like to delete?";
    private final static String forumDeleted = "The forum you selected has been deleted";
    private final static String forumSelectionPrompt = "Which discussion forum would you like to open?";

    private final static String discussionForumEnteredStudentPrompt = """
            Please enter the option number of what you want to do.
            1) View discussion forum
            2) Post message
            3) Reply to message
            4) Upvote message
            5) Exit forum""";

    private final static String discussionForumEnteredTeacherPrompt = """
            Please enter the option number of what you want to do.
            1) View messages
            2) Post message
            3) Reply to message
            4) Grade student messages
            5) Change topic
            6) Exit forum""";

    private ArrayList<String> forumList;

    public Course(String courseName, String username, String firstName, String lastName, DiscussionForum discussionForum, String discussionBoardsListFileName /*String discussionsPointsFileName*/) {
        this.courseName = courseName;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.discussionForum = discussionForum;
        this.discussionBoardsListFileName = discussionBoardsListFileName;
        //this.discussionsPointsFileName = discussionsPointsFileName;
    }

    public void readForumListFile() throws Exception {
        File f = new File(discussionBoardsListFileName);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();
        line = bfr.readLine();
        while (line != null) {
            forumList.add(line);
            line = bfr.readLine();
        }
    }

    public boolean discussionForumExists(String forumName) {
        return forumList.contains(forumName);
    }

    public void createForum() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        do {
            System.out.println(newForumNamePrompt);
            String newForumName = scan.nextLine();
            if (newForumName == null || newForumName.isBlank() || discussionForumExists(newForumName)) {
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
                FileOutputStream fos = new FileOutputStream(courseName + "-" + newForumName + "-messages" + ".txt", false);
                PrintWriter pw = new PrintWriter(fos);
                pw.println(newForumName);
                fos = new FileOutputStream(discussionBoardsListFileName, true);
                pw = new PrintWriter(fos);
                pw.println(newForumName);
                fos = new FileOutputStream(courseName + "-" + newForumName + "-points" + ".txt");
                pw = new PrintWriter(fos);
                pw.println("");
                System.out.println(newForumCreated);
            }
        } while (loop);
    }

    public void deleteForum() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        do {
            System.out.println(deleteForumPrompt);
            String toDeleteForum = scan.nextLine();
            if (toDeleteForum == null || !discussionForumExists(toDeleteForum)) {
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
                FileOutputStream fos = new FileOutputStream(discussionBoardsListFileName);
                PrintWriter pw = new PrintWriter(fos);
                forumList.remove(toDeleteForum);
                File f = new File(courseName + "-" + toDeleteForum + "-messages" + ".txt");
                if (f.delete()) {
                    f = new File(courseName + "-" + toDeleteForum + "-points" + ".txt");
                    if (f.delete()) {
                        System.out.println(forumDeleted);
                    }
                }
            }
        } while (loop);
    }

    public void viewPoints() throws IOException {
        String output = "";
        for (int i = 0; i < forumList.size(); i++) {
            output += forumList.get(i) + ": ";
            File f = new File(courseName + "-" + forumList.get(i) + "-points" + ".txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            boolean pointsFound = false;
            while (line != null) {
                if (line.contains(username)) {
                    String[] splitLine = line.split("---");
                    output += splitLine[1] + "\n";
                    pointsFound = true;
                }
                line = bfr.readLine();
            }
            if (!pointsFound) {
                output += "Points not assigned to this forum yet.\n";
            }
        }
        System.out.println(output);
    }

    public void showDashboard() {

    }

    public void studentDiscussionForumOpened() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        do {
            readForumListFile();
            System.out.println(forumSelectionPrompt);
            String selectedForum = scan.nextLine();
            if (selectedForum == null || !discussionForumExists(selectedForum)) {
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
                String discussionForumMessagesFileName = courseName + "-" + selectedForum + "-messages" + ".txt";
                String discussionForumPointsFileName = courseName + "-" + selectedForum + "-points" + ".txt";
                discussionForum = new DiscussionForum(selectedForum, discussionForumMessagesFileName, discussionForumPointsFileName, firstName, lastName, username);
                discussionForum.readMessagesFile();
                discussionForum.readPointsFile();
                showDiscussionForumMainMethodStudent();
            }
        } while (loop);
    }

    public void teacherDiscussionForumOpened() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        do {
            readForumListFile();
            System.out.println(forumSelectionPrompt);
            String selectedForum = scan.nextLine();
            if (selectedForum == null || !discussionForumExists(selectedForum)) {
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
                String discussionForumMessagesFileName = courseName + "-" + selectedForum + "-messages" + ".txt";
                String discussionForumPointsFileName = courseName + "-" + selectedForum + "-points" + ".txt";
                discussionForum = new DiscussionForum(selectedForum, discussionForumMessagesFileName, discussionForumPointsFileName, firstName, lastName, username);
                discussionForum.readMessagesFile();
                discussionForum.readPointsFile();
                showDiscussionForumMainMethodTeacher();
            }
        } while (loop);
    }

    public void showDiscussionForumMainMethodStudent() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        int option = 0;
        while (option != 5) {
            do {
                System.out.println(discussionForumEnteredStudentPrompt);
                option = scan.nextInt();
                scan.nextLine();
                if (option < 1 || option > 5) {
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
                    switch (option) {
                        case 1 -> discussionForum.printMessages();
                        case 2 -> discussionForum.postMessage();
                        case 3 -> discussionForum.replyToPost();
                        case 4 -> discussionForum.upvote();
                    }
                }
            } while (loop);
        }
    }

    public void showDiscussionForumMainMethodTeacher() throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        int option = 0;
        while (option != 6) {
            do {
                System.out.println(discussionForumEnteredTeacherPrompt);
                option = scan.nextInt();
                scan.nextLine();
                if (option < 1 || option > 6) {
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
                    switch (option) {
                        case 1 -> discussionForum.printMessages();
                        case 2 -> discussionForum.postMessage();
                        case 3 -> discussionForum.replyToPost();
                        case 4 -> {
                            discussionForum.printSpecificStudentMessages();
                            discussionForum.responseGrading();
                        }
                        case 5 -> discussionForum.changeTopic();
                    }
                }
            } while (loop);
        }
    }
}
