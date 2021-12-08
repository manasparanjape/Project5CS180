import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
    JButton sendMessageViaFileImportButton;

    boolean ifDashBoard = false;

    JTextArea textArea;
    JTextField messageNumberField;
    JTextField newMessageField;

    JLabel newMessageFieldLabel;
    JLabel messageNumberFieldLabel;

    private static String newForumNamePrompt = "What would you like to name the new discussion forum?";
    private static String newForumCreated = "New forum has been created!";
    private static String deleteForumPrompt = "Which forum would you like to delete?";
    private static String forumDeleted = "The forum you selected has been deleted";

    private ArrayList<String> forumList = new ArrayList<>();

    public Course(String courseName, String username, String firstName, String lastName,
                  DiscussionForum discussionForum, String discussionBoardsListFileName, JFrame jframe) {
        this.courseName = courseName;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.discussionForum = discussionForum;
        this.discussionBoardsListFileName = discussionBoardsListFileName;
        this.jframe = jframe;
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
                    discussionForumPointsFileName, firstName, lastName, username, discussionForumUpvotesFileName, textArea);
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
        }
    }

    //displays list of discussion forums and asks user to open one
    public void teacherDiscussionForumOpened() throws Exception {
        readForumListFile();
        if (forumList.size() == 1) {
            String errorMessage = "This course does not have any discussion forums.";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String selectedForum = forumList.get(0);
            String discussionForumMessagesFileName = courseName + "-" + selectedForum + "-messages" + ".txt";
            String discussionForumPointsFileName = courseName + "-" + selectedForum + "-points" + ".txt";
            String discussionForumUpvotesFileName = courseName + "-" + selectedForum + "-upvotes" + ".txt";
            discussionForum = new DiscussionForum(selectedForum, discussionForumMessagesFileName,
                    discussionForumPointsFileName, firstName, lastName, username, discussionForumUpvotesFileName, textArea);
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
        }
    }

    /*
     * GUI part
     * how to create the interface.
     */

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
        Teacher teacher = new Teacher(username, firstName, lastName, new Course(courseName, username, firstName, lastName, null, discussionBoardsListFileName, jframe), jframe);
        teacher.runnableMethod();
    }
    public void backButtonStudentMethod() throws Exception {
        Student student = new Student(username, firstName, lastName, new Course(courseName, username, firstName, lastName, null, discussionBoardsListFileName, jframe), jframe);
        student.runnableMethod();
    }
    public void sendMessageViaFileImportButtonMethod() throws Exception {
        discussionForum.readNewPostFileInGUI();
    }

    public void openDiscussionForum(String forumName) throws Exception {
        String discussionForumMessagesFileName = courseName + "-" + forumName + "-messages" + ".txt";
        String discussionForumPointsFileName = courseName + "-" + forumName + "-points" + ".txt";
        String discussionForumUpvotesFileName = courseName + "-" + forumName + "-upvotes" + ".txt";
        discussionForum = new DiscussionForum(forumName, discussionForumMessagesFileName,
                discussionForumPointsFileName, firstName, lastName, username, discussionForumUpvotesFileName, textArea);
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
                try {
                    backButtonStudentMethod();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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
            if (e.getSource() == sendMessageViaFileImportButton) {
                try {
                    sendMessageViaFileImportButtonMethod();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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
        sendMessageViaFileImportButton = new JButton("Import File with message");
        sendMessageViaFileImportButton.addActionListener(actionListener);

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
        bottomPanel.add(sendMessageViaFileImportButton);

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
                        openDiscussionForum(selectedForum);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
        for (int i = 0; i < discussionForumButtonsArray.size(); i++) {
            leftPanel.add(discussionForumButtonsArray.get(i));
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
        container.revalidate();

        sendButton = new JButton("Send/Reply");
        sendButton.addActionListener(actionListener);
        upvoteButton = new JButton("Upvote");
        upvoteButton.addActionListener(actionListener);
        backButtonStudent = new JButton("Back");
        backButtonStudent.addActionListener(actionListener);
        sendMessageViaFileImportButton = new JButton("Import File with message");
        sendMessageViaFileImportButton.addActionListener(actionListener);

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
        bottomPanel.add(sendMessageViaFileImportButton);

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
                        openDiscussionForum(selectedForum);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
        for (int i = 0; i < discussionForumButtonsArray.size(); i++) {
            leftPanel.add(discussionForumButtonsArray.get(i));
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

    public void deleteForumInGUI() throws Exception {
        boolean deleted = false;
        readForumListFile();
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

    public void createForumViaFileImportInGUI() {
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
                String newForumName = String.valueOf(output);
                if (newForumName == null || newForumName.isBlank()) {
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
                    System.out.println(newForumCreated);
                    pw.close();
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
