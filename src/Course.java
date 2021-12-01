import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Course.java
 * <p>
 * Contains forum selection level options for students and teacher as well as methods
 * for forum creation, deletion and point viewing
 *
 * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
 * @version 11/15/2021
 */

public class Course {
    private String courseName;
    private String username;
    private String firstName;
    private String lastName;
    private String discussionBoardsListFileName;
    private DiscussionForum discussionForum;
    private Scanner scan;
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
            "1) View messages\n2) Post message\n3) Reply to message\n" + "4) Delete message\n" +
            "5) Grade student messages\n6) Change topic\n7) View dashboard\n" +
            "8) Exit forum";
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
                  DiscussionForum discussionForum, String discussionBoardsListFileName, Scanner scan, JFrame jframe) {
        this.courseName = courseName;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.discussionForum = discussionForum;
        this.discussionBoardsListFileName = discussionBoardsListFileName;
        this.scan = scan;
        this.jframe = jframe;
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
    public void createForum() {
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
    public void deleteForum() throws Exception {
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
    public void studentDiscussionForumOpened() throws Exception {
        readForumListFile();
        if (forumList.size() == 1) {
            String errorMessage = "This course does not have any discussion forums.";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String selectedForum = forumList.get(1);
            String discussionForumMessagesFileName = courseName + "-" + selectedForum + "-messages" + ".txt";
            String discussionForumPointsFileName = courseName + "-" + selectedForum + "-points" + ".txt";
            String discussionForumUpvotesFileName = courseName + "-" + selectedForum + "-upvotes" + ".txt";
            discussionForum = new DiscussionForum(selectedForum, discussionForumMessagesFileName,
                    discussionForumPointsFileName, firstName, lastName, username, discussionForumUpvotesFileName, scan, textArea);
            discussionForum.readMessagesFile();
            discussionForum.readPointsFile();
            discussionForum.readUpvotesFile();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        runMethodStudent();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            showDiscussionForumMainMethodStudent();
        }
    }

    //displays list of discussion forums and asks user to open one
    public void teacherDiscussionForumOpened() throws Exception {
        readForumListFile();
        if (forumList.size() == 1) {
            String errorMessage = "This course does not have any discussion forums.";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String selectedForum = forumList.get(1);
            String discussionForumMessagesFileName = courseName + "-" + selectedForum + "-messages" + ".txt";
            String discussionForumPointsFileName = courseName + "-" + selectedForum + "-points" + ".txt";
            String discussionForumUpvotesFileName = courseName + "-" + selectedForum + "-upvotes" + ".txt";
            discussionForum = new DiscussionForum(selectedForum, discussionForumMessagesFileName,
                    discussionForumPointsFileName, firstName, lastName, username, discussionForumUpvotesFileName, scan, textArea);
            discussionForum.readMessagesFile();
            discussionForum.readPointsFile();
            discussionForum.readUpvotesFile();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        runMethodTeacher();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            //showDiscussionForumMainMethodTeacher();
        }
    }

    //gives students the choice to: print messages, post messages,
    //reply to a post, upvote a message, or exit
    public void showDiscussionForumMainMethodStudent() throws Exception {
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
    public void showDiscussionForumMainMethodTeacher() {
        int option = 0;
        while (option != 8) {
            try {
                System.out.println(discussionForumEnteredTeacherPrompt);
                option = scan.nextInt();
                scan.nextLine();
                if (option < 1 || option > 8) {
                    System.out.println("You entered an invalid number. Please enter a number between 1 and 6.");
                } else {
                    switch (option) {
                        case 1 -> discussionForum.printMessages();
                        case 2 -> discussionForum.postMessage();
                        case 3 -> discussionForum.replyToPost();
                        case 4 -> discussionForum.deletePost();
                        case 5 -> discussionForum.responseGrading();
                        case 6 -> discussionForum.changeTopic();
                        case 7 -> discussionForum.showDashboard();
                    }
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number");
                scan.nextLine();
                option = 0;

            }
        }
    }

    /*
    * GUI part
    * how to create the interface.
     */

    //JFrame jframe = new JFrame(courseName);
    JFrame jframe;
    ArrayList<JButton> discussionForumButtonsArray = new ArrayList<>(0);
    JButton sendButton;
    JButton upvoteButton;
    JButton deleteMessageButton;
    JButton gradeStudentButton;
    JButton dashboardButton;
    JButton changeTopicButton;
    JButton backButtonTeacher;
    JButton backButtonStudent;

    boolean ifDashBoard = false;

    JTextArea textArea;
    JTextField messageNumberField;
    JTextField newMessageField;

    JLabel newMessageFieldLabel;
    JLabel messageNumberFieldLabel;

    public void sendButtonMethod() {
        try {
            String newPost = newMessageField.getText();
            String messageNumber = messageNumberField.getText();
            discussionForum.postMessageInGUI(newPost, messageNumber);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        newMessageField.setText("");
        messageNumberField.setText("");
    }
    public void upvoteButtonMethod() {
        String upvoteMessageNumber = messageNumberField.getText();
        try {
            discussionForum.upvoteMessageInGUI(upvoteMessageNumber);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        messageNumberField.setText("");
    }
    public void deleteButtonMethod() {
        String messageNumber = messageNumberField.getText();
        try {
            discussionForum.deleteMessageInGUI(messageNumber);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        messageNumberField.setText("");
    }
    public void gradeStudentButtonMethod() {
        try {
            discussionForum.gradeStudentsInGUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void dashboardButtonMethod() {
        if (ifDashBoard) {
            try {
                discussionForum.printMessagesInGUI();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            ifDashBoard = false;
            dashboardButton.setText("Show Dashboard");
        } else {
            discussionForum.showDashboardInGUI();
            ifDashBoard = true;
            dashboardButton.setText("Show Messages");
        }
    }
    public void changeTopicButtonMethod() {
        try {
            discussionForum.changeTopicInGUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void backButtonTeacherMethod() {
        Teacher teacher = new Teacher(username, firstName, lastName, new Course(courseName, username, firstName, lastName, null, discussionBoardsListFileName, scan, jframe), scan, jframe);
        teacher.runnableMethod();
    }
    public void backButtonStudentMethod() {
        Student student = new Student(username, firstName, lastName, new Course(courseName, username, firstName, lastName, null, discussionBoardsListFileName, scan, jframe), scan, jframe);

    }

    public void openDiscussionForumTeacher(String forumName) throws Exception {
        String discussionForumMessagesFileName = courseName + "-" + forumName + "-messages" + ".txt";
        String discussionForumPointsFileName = courseName + "-" + forumName + "-points" + ".txt";
        String discussionForumUpvotesFileName = courseName + "-" + forumName + "-upvotes" + ".txt";
        discussionForum = new DiscussionForum(forumName, discussionForumMessagesFileName,
                discussionForumPointsFileName, firstName, lastName, username, discussionForumUpvotesFileName, scan, textArea);
        try {
            discussionForum.readMessagesFile();
            discussionForum.readPointsFile();
            discussionForum.readUpvotesFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        changeForum();
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == backButtonTeacher) {
                backButtonTeacherMethod();
            }
            if (e.getSource() == backButtonStudent) {
                backButtonStudentMethod();
            }
            if (e.getSource() == sendButton) {
                sendButtonMethod();
            }
            if (e.getSource() == upvoteButton) {
                upvoteButtonMethod();
            }
            if (e.getSource() == deleteMessageButton) {
                deleteButtonMethod();
            }
            if (e.getSource() == gradeStudentButton) {
                gradeStudentButtonMethod();
            }
            if (e.getSource() == dashboardButton) {
                dashboardButtonMethod();
            }
            if (e.getSource() == changeTopicButton) {
                changeTopicButtonMethod();
            }
        }
    };

    public void runMethodTeacher() throws Exception {
        Container container = jframe.getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());
        container.revalidate();

        backButtonTeacher = new JButton("Back");
        backButtonTeacher.addActionListener(actionListener);
        sendButton = new JButton("Send/Reply");
        sendButton.addActionListener(actionListener);
        upvoteButton = new JButton("Upvote");
        upvoteButton.addActionListener(actionListener);
        deleteMessageButton = new JButton("Delete Message");
        deleteMessageButton.addActionListener(actionListener);
        gradeStudentButton = new JButton("Grade");
        gradeStudentButton.addActionListener(actionListener);
        dashboardButton = new JButton("View Dashboard");
        dashboardButton.addActionListener(actionListener);
        changeTopicButton = new JButton("Change Topic");
        changeTopicButton.addActionListener(actionListener);

        newMessageFieldLabel = new JLabel("New Message");
        messageNumberFieldLabel = new JLabel("Message No. to reply/delete");

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setPreferredSize(new Dimension(20, 100));

        newMessageField = new JTextField(30);
        messageNumberField = new JTextField(3);

        jframe.setSize(900, 600);
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(newMessageFieldLabel);
        bottomPanel.add(newMessageField);
        bottomPanel.add(sendButton);
        bottomPanel.add(messageNumberFieldLabel);
        bottomPanel.add(messageNumberField);

        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(backButtonTeacher);
        for (int i = 0; i < forumList.size(); i++) {
            discussionForumButtonsArray.add(new JButton(forumList.get(i)));
            discussionForumButtonsArray.get(i).setMaximumSize(new Dimension(150, 200));
            discussionForumButtonsArray.get(i).setMinimumSize(new Dimension(150, 200));
            int finalI = i;
            discussionForumButtonsArray.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedForum = discussionForumButtonsArray.get(finalI).getText();
                    try {
                        openDiscussionForumTeacher(selectedForum);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
        for (int i = 0; i < discussionForumButtonsArray.size() - 1; i++) {
            leftPanel.add(discussionForumButtonsArray.get(i + 1));
        }
        JScrollPane leftScrollPane = new JScrollPane(leftPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        leftScrollPane.setPreferredSize(new Dimension(150, 600));

        JPanel rightPanel = new JPanel(new GridLayout(4, 1));
        rightPanel.add(changeTopicButton);
        rightPanel.add(dashboardButton);
        rightPanel.add(gradeStudentButton);
        rightPanel.add(deleteMessageButton);

        container.add(areaScrollPane, BorderLayout.CENTER);
        container.add(bottomPanel, BorderLayout.SOUTH);
        container.add(rightPanel, BorderLayout.EAST);
        container.add(leftScrollPane, BorderLayout.WEST);
        discussionForum.setTextArea(textArea);
        discussionForum.printMessagesInGUI();
    }

    public void changeForum() throws Exception {
        discussionForum.setTextArea(textArea);
        discussionForum.printMessagesInGUI();
    }

    public void runMethodStudent() throws Exception {
        Container container = jframe.getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());

        sendButton = new JButton("Send/Reply");
        sendButton.addActionListener(actionListener);
        upvoteButton = new JButton("Upvote");
        upvoteButton.addActionListener(actionListener);
        backButtonStudent = new JButton("Back");
        backButtonStudent.addActionListener(actionListener);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setPreferredSize(new Dimension(20, 100));

        newMessageField = new JTextField(30);
        messageNumberField = new JTextField(3);

        newMessageFieldLabel = new JLabel("New Message");
        messageNumberFieldLabel = new JLabel("Message No. to reply/upvote");

        jframe.setSize(900, 600);
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(newMessageField);
        bottomPanel.add(sendButton);
        bottomPanel.add(messageNumberField);
        bottomPanel.add(upvoteButton);

        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(backButtonStudent);
        for (int i = 0; i < forumList.size(); i++) {
            discussionForumButtonsArray.add(new JButton(forumList.get(i)));
            discussionForumButtonsArray.get(i).setMaximumSize(new Dimension(150, 200));
            discussionForumButtonsArray.get(i).setMinimumSize(new Dimension(150, 200));
            int finalI = i;
            discussionForumButtonsArray.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedForum = discussionForumButtonsArray.get(finalI).getText();
                    try {
                        openDiscussionForumTeacher(selectedForum);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
        for (int i = 0; i < discussionForumButtonsArray.size() - 1; i++) {
            leftPanel.add(discussionForumButtonsArray.get(i + 1));
        }
        JScrollPane leftScrollPane = new JScrollPane(leftPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        leftScrollPane.setPreferredSize(new Dimension(150, 600));

        container.add(areaScrollPane, BorderLayout.CENTER);
        container.add(bottomPanel, BorderLayout.SOUTH);
        container.add(leftScrollPane, BorderLayout.WEST);
        discussionForum.setTextArea(textArea);
        discussionForum.printMessagesInGUI();
    }

    public void createForumInGUI() throws FileNotFoundException {
        String newForumName = JOptionPane.showInputDialog(null, newForumNamePrompt, "New Forum", JOptionPane.QUESTION_MESSAGE);
        if (newForumName != null) {
            if (newForumName.isBlank()) {
                String errorMessage = "Please enter a valid discussion forum name(ie. Not all spaces or blank).";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } else if (discussionForumExists(newForumName)) {
                String errorMessage = "A discussion forum with that name already exists in this course.";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
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
                pw.close();
                JOptionPane.showMessageDialog(null, newForumCreated, "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void deleteForumInGUI() throws FileNotFoundException {
        boolean deleted = false;
        Object[] options = new Object[forumList.size()];
        for (int i = 0; i < forumList.size(); i++) {
            options[i] = forumList.get(i);
        }
        Object selectedObject = JOptionPane.showInputDialog(null, deleteForumPrompt, "Delete Forum", JOptionPane.PLAIN_MESSAGE, null, options, JOptionPane.CLOSED_OPTION);
        if (selectedObject != null) {
            String toDeleteForum = selectedObject.toString();
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
            if (deleted) {
                JOptionPane.showMessageDialog(null, forumDeleted, "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
