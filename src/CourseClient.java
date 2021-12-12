import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

public class CourseClient {
    private String courseName;
    private String username;
    private String firstName;
    private String lastName;
    private static String newForumNamePrompt = "What would you like to name the new discussion forum?";
    private static String newForumCreated = "New forum has been created!";
    private static String deleteForumPrompt = "Which forum would you like to delete?";
    private static String forumDeleted = "The forum you selected has been deleted";

    private DiscussionForumClient discussionForumClient;

    private JFrame jframe;

    private ArrayList<JButton> discussionForumButtonsArray = new ArrayList<>(0);

    private JButton sendButton;
    private JButton upvoteButton;
    private JButton deleteMessageButton;
    private JButton gradeStudentButton;
    private JButton dashboardButton;
    private JButton changeTopicButton;
    private JButton backButtonTeacher;
    private JButton backButtonStudent;
    private JButton sendMessageViaFileImportButton;
    private JButton backButtonPoints;

    private boolean ifDashBoard = false;

    private JTextArea textArea;

    private JTextField messageNumberField;
    private JTextField newMessageField;

    private JLabel newMessageFieldLabel;
    private JLabel messageNumberFieldLabel;

    private PrintWriter printWriter;

    private BufferedReader bufferedReader;
    private BufferedReader dummyReader;

    private boolean ifTeacher;

    public CourseClient(String courseName, String username, String firstName, String lastName, DiscussionForumClient discussionForumClient, JFrame jFrame, PrintWriter printWriter, BufferedReader bufferedReader, BufferedReader dummyReader) {
        this.courseName = courseName;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.discussionForumClient = discussionForumClient;
        this.jframe = jFrame;
        this.printWriter = printWriter;
        this.bufferedReader = bufferedReader;
        this.dummyReader = dummyReader;
    }

    public void createForum() throws IOException {
        String newForumName = JOptionPane.showInputDialog(null, newForumNamePrompt, "New Forum", JOptionPane.QUESTION_MESSAGE);
        if (newForumName != null) {
            if (newForumName.isBlank()) {
                printWriter.write(" ");
                printWriter.println();
                printWriter.flush();
                String errorMessage = "Please enter a valid discussion forum name(ie. Not all spaces or blank).";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                printWriter.write(newForumName);
                printWriter.println();
                printWriter.flush();

                String receivedData = null;
                try {
                    receivedData = bufferedReader.readLine();
                } catch (SocketException e) {
                    String errorMessage = "The server unexpectedly closed. Please try again later";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    jframe.setVisible(false);
        jframe.dispose();
                }
                if (receivedData.equals("0")) {
                    String errorMessage = "A discussion forum with that name already exists in this course.";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                } else if (receivedData.equals("1")) {
                    JOptionPane.showMessageDialog(null, newForumCreated, "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            printWriter.write(" ");
            printWriter.println();
            printWriter.flush();
        }
        TeacherClient teacherClient = new TeacherClient(username, firstName, lastName, new CourseClient(courseName, username, firstName, lastName, null, jframe, printWriter, bufferedReader, dummyReader), jframe, printWriter, bufferedReader, dummyReader);
        teacherClient.runMethodTeacher();
    }

    public void deleteForum() throws Exception {
        String receivedData = null;
        try {
            receivedData = bufferedReader.readLine();
        } catch (SocketException e) {
            String errorMessage = "The server unexpectedly closed. Please try again later";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            jframe.setVisible(false);
        jframe.dispose();
        }
        if (receivedData.equals(" ")) {
            printWriter.write(" ");
            printWriter.println();
            printWriter.flush();
            String errorMessage = "No forum has been created yet.";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Object[] options = receivedData.split("§§§");
            Object selectedObject = JOptionPane.showInputDialog(null, deleteForumPrompt, "Delete Forum", JOptionPane.PLAIN_MESSAGE, null, options, JOptionPane.CLOSED_OPTION);
            if (selectedObject != null) {
                String toDeleteForum = selectedObject.toString();
                printWriter.write(toDeleteForum);
                printWriter.println();
                printWriter.flush();

                JOptionPane.showMessageDialog(null, forumDeleted, "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                printWriter.write(" ");
                printWriter.println();
                printWriter.flush();
            }
        }
        TeacherClient teacherClient = new TeacherClient(username, firstName, lastName, new CourseClient(courseName, username, firstName, lastName, null, jframe, printWriter, bufferedReader, dummyReader), jframe, printWriter, bufferedReader, dummyReader);
        teacherClient.runMethodTeacher();
    }

    public void createForumViaFileImport() {
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
                    printWriter.write(" ");
                    printWriter.println();
                    printWriter.flush();
                    String errorMessage = "Please enter a valid discussion forum name(ie. Not all spaces or blank).";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    printWriter.write(newForumName);
                    printWriter.println();
                    printWriter.flush();

                    String receivedData = null;
                    try {
                        receivedData = bufferedReader.readLine();
                    } catch (SocketException e) {
                        String errorMessage = "The server unexpectedly closed. Please try again later";
                        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                        jframe.setVisible(false);
        jframe.dispose();
                    }
                    if (receivedData.equals("0")) {
                        String errorMessage = "A discussion forum with that name already exists in this course.";
                        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (receivedData.equals("1")) {
                        JOptionPane.showMessageDialog(null, newForumCreated, "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (FileNotFoundException e) {
                printWriter.write(" ");
                printWriter.println();
                printWriter.flush();
                String errorMessage = "The file you entered was not found.";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                printWriter.write(" ");
                printWriter.println();
                printWriter.flush();
                String errorMessage = "Error parsing contents of the file.";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        TeacherClient teacherClient = new TeacherClient(username, firstName, lastName, new CourseClient(courseName, username, firstName, lastName, null, jframe, printWriter, bufferedReader, dummyReader), jframe, printWriter, bufferedReader, dummyReader);
        teacherClient.runMethodTeacher();
    }

    public void changeForum() throws Exception {
        discussionForumClient.printMessages();
        discussionForumClient.setTextArea(textArea);
    }

    public void runMethodTeacher() throws Exception {
        ifTeacher = true;
        Container container = jframe.getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());
        container.revalidate();

        backButtonTeacher = new JButton("Back");
        backButtonTeacher.addActionListener(actionListener);
        backButtonTeacher.setBackground(Color.decode("#c0c0c0"));
        sendButton = new JButton("Send/Reply");
        sendButton.addActionListener(actionListener);
        sendButton.setBackground(Color.decode("#c0c0c0"));
        upvoteButton = new JButton("Upvote");
        upvoteButton.addActionListener(actionListener);
        upvoteButton.setBackground(Color.decode("#c0c0c0"));
        deleteMessageButton = new JButton("Delete Message");
        deleteMessageButton.addActionListener(actionListener);
        deleteMessageButton.setBackground(Color.decode("#c0c0c0"));
        gradeStudentButton = new JButton("Grade");
        gradeStudentButton.addActionListener(actionListener);
        gradeStudentButton.setBackground(Color.decode("#c0c0c0"));
        dashboardButton = new JButton("View Dashboard");
        dashboardButton.addActionListener(actionListener);
        dashboardButton.setBackground(Color.decode("#c0c0c0"));
        changeTopicButton = new JButton("Change Topic");
        changeTopicButton.addActionListener(actionListener);
        changeTopicButton.setBackground(Color.decode("#c0c0c0"));
        sendMessageViaFileImportButton = new JButton("Import File with message");
        sendMessageViaFileImportButton.addActionListener(actionListener);
        sendMessageViaFileImportButton.setBackground(Color.decode("#c0c0c0"));

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

        //jframe.setSize(900, 600);
        //jframe.setLocationRelativeTo(null);
        //jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //jframe.setVisible(true);

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

        String receivedData = null;
        try {
            receivedData = bufferedReader.readLine();
        } catch (SocketException e) {
            String errorMessage = "The server unexpectedly closed. Please try again later";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            jframe.setVisible(false);
        jframe.dispose();
        }
        String[] receivedDataArray = receivedData.split("§§§");
        ArrayList<String> forumList = new ArrayList<>(Arrays.asList(receivedDataArray));
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
                        printWriter.write("0");
                        printWriter.println();
                        printWriter.flush();
                        changeDiscussionForum(selectedForum);
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

        changeDiscussionForum(receivedDataArray[0]);
    }

    public void runMethodStudent() throws Exception {
        ifTeacher = false;
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

        //jframe.setSize(900, 600);
        //jframe.setLocationRelativeTo(null);
        //jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //jframe.setVisible(true);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(newMessageField);
        bottomPanel.add(sendButton);
        bottomPanel.add(messageNumberField);
        bottomPanel.add(upvoteButton);
        bottomPanel.add(sendMessageViaFileImportButton);

        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(backButtonStudent);

        String receivedData = null;
        try {
            receivedData = bufferedReader.readLine();
        } catch (SocketException e) {
            String errorMessage = "The server unexpectedly closed. Please try again later";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            jframe.setVisible(false);
        jframe.dispose();
        }
        String[] receivedDataArray = receivedData.split("§§§");
        ArrayList<String> forumList = new ArrayList<>(Arrays.asList(receivedDataArray));
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
                        printWriter.write("0");
                        printWriter.println();
                        printWriter.flush();
                        changeDiscussionForum(selectedForum);
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

        changeDiscussionForum(receivedDataArray[0]);
    }

    public void changeDiscussionForum(String forumName) throws Exception {
        String discussionForumMessagesFileName = courseName + "-" + forumName + "-messages" + ".txt";
        String discussionForumPointsFileName = courseName + "-" + forumName + "-points" + ".txt";
        String discussionForumUpvotesFileName = courseName + "-" + forumName + "-upvotes" + ".txt";

        printWriter.write(forumName + "§§§" + discussionForumMessagesFileName + "§§§" + discussionForumPointsFileName + "§§§" + discussionForumUpvotesFileName);
        printWriter.println();
        printWriter.flush();

        String receivedData = null;
        try {
            receivedData = bufferedReader.readLine();
        } catch (SocketException e) {
            String errorMessage = "The server unexpectedly closed. Please try again later";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            jframe.setVisible(false);
        jframe.dispose();
        }
        if (receivedData.equals("1")) {
            System.out.println("Point reached");
            int i = 0;
            boolean buttonFound = false;
            while (i < discussionForumButtonsArray.size() && !buttonFound) {
                if (forumName.equals(discussionForumButtonsArray.get(i).getText())) {
                    buttonFound = true;
                }
                i++;
            }
            discussionForumClient = new DiscussionForumClient(printWriter, bufferedReader, textArea, dummyReader, discussionForumButtonsArray.get(i - 1), jframe);
            changeForum();
        } else {
            String errorMessage = "The discussion forum you chose may have been deleted or renamed.";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            if (ifTeacher) {
                runMethodTeacher();
            } else {
                runMethodStudent();
            }
        }
    }

    public void viewPoints() throws IOException {
        Container container = jframe.getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());
        container.revalidate();

        backButtonPoints = new JButton("Back");
        backButtonPoints.addActionListener(actionListener);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setPreferredSize(new Dimension(20, 100));
        String pointsString = null;
        try {
            pointsString = bufferedReader.readLine();
        } catch (SocketException e) {
            String errorMessage = "The server unexpectedly closed. Please try again later";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            jframe.setVisible(false);
        jframe.dispose();
        }
        pointsString = pointsString.replaceAll("§§§", "\n" );
        textArea.setText(pointsString);


        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(backButtonPoints);

        container.add(areaScrollPane, BorderLayout.CENTER);
        container.add(leftPanel, BorderLayout.WEST);
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
                try {
                    dashboardButtonMethod();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
            if (e.getSource() == backButtonPoints) {
                backButtonPointsMethod();
            }
        }
    };

    public void sendButtonMethod() {
        printWriter.write("1");
        printWriter.println();
        printWriter.flush();
        try {
            String newPost = newMessageField.getText();
            String messageNumber = messageNumberField.getText();
            discussionForumClient.send(newPost, messageNumber);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        newMessageField.setText("");
        messageNumberField.setText("");
    }
    public void upvoteButtonMethod() {
        printWriter.write("2");
        printWriter.println();
        printWriter.flush();
        String upvoteMessageNumber = messageNumberField.getText();
        try {
            discussionForumClient.upvote(upvoteMessageNumber);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        messageNumberField.setText("");
    }
    public void deleteButtonMethod() {
        printWriter.write("3");
        printWriter.println();
        printWriter.flush();
        String messageNumber = messageNumberField.getText();
        try {
            discussionForumClient.deleteMessage(messageNumber);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        messageNumberField.setText("");
    }
    public void gradeStudentButtonMethod() {
        printWriter.write("4");
        printWriter.println();
        printWriter.flush();
        try {
            discussionForumClient.gradeStudent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void dashboardButtonMethod() throws IOException {
        printWriter.write("5");
        printWriter.println();
        printWriter.flush();
        if (ifDashBoard) {
            try {
                discussionForumClient.printMessages();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            ifDashBoard = false;
            dashboardButton.setText("Show Dashboard");
        } else {
            discussionForumClient.dashBoard();
            ifDashBoard = true;
            dashboardButton.setText("Show Messages");
        }
    }
    public void changeTopicButtonMethod() {
        printWriter.write("6");
        printWriter.println();
        printWriter.flush();
        try {
            discussionForumClient.changeTopic();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void sendMessageViaFileImportButtonMethod() throws Exception {
        printWriter.write("7");
        printWriter.println();
        printWriter.flush();
        discussionForumClient.sendMessageViaFileImport();
    }
    public void backButtonTeacherMethod() {
        printWriter.write("-1");
        printWriter.println();
        printWriter.flush();
        TeacherClient teacherClient = new TeacherClient(username, firstName, lastName, new CourseClient(courseName, username, firstName, lastName, null, jframe, printWriter, bufferedReader, dummyReader), jframe, printWriter, bufferedReader, dummyReader);
        teacherClient.runMethodTeacher();
    }
    public void backButtonStudentMethod() throws Exception {
        printWriter.write("-2");
        printWriter.println();
        printWriter.flush();
        StudentClient studentClient = new StudentClient(username, firstName, lastName, new CourseClient(courseName, username, firstName, lastName, null, jframe, printWriter, bufferedReader, dummyReader), jframe, printWriter, bufferedReader, dummyReader);
        studentClient.runMethodStudent();
    }
    public void backButtonPointsMethod() {
        StudentClient studentClient = new StudentClient(username, firstName, lastName, new CourseClient(courseName, username, firstName, lastName, null, jframe, printWriter, bufferedReader, dummyReader),jframe, printWriter, bufferedReader, dummyReader);
        studentClient.runMethodStudent();
    }

    public void studentDiscussionForumOpened() throws Exception {
        String receivedData = null;
        try {
            receivedData = bufferedReader.readLine();
        } catch (SocketException e) {
            String errorMessage = "The server unexpectedly closed. Please try again later";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            jframe.setVisible(false);
        jframe.dispose();
        }
        if (receivedData.equals(" ")) {
            String errorMessage = "This course does not have any discussion forums.";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            StudentClient studentClient = new StudentClient(username, firstName, lastName, new CourseClient(courseName, username, firstName, lastName, null, jframe, printWriter, bufferedReader, dummyReader), jframe, printWriter, bufferedReader, dummyReader);
            studentClient.runMethodStudent();
        } else {
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

    public void teacherDiscussionForumOpened() throws Exception {
        String receivedData = null;
        try {
            receivedData = bufferedReader.readLine();
        } catch (SocketException e) {
            String errorMessage = "The server unexpectedly closed. Please try again later";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            jframe.setVisible(false);
        jframe.dispose();
        }
        if (receivedData.equals(" ")) {
            String errorMessage = "This course does not have any discussion forums.";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            TeacherClient teacherClient = new TeacherClient(username, firstName, lastName, new CourseClient(courseName, username, firstName, lastName, null, jframe, printWriter, bufferedReader, dummyReader), jframe, printWriter, bufferedReader, dummyReader);
            teacherClient.runMethodTeacher();
        } else {
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
}
