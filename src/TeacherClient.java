import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;

public class TeacherClient {
    private String username;
    private String firstName;
    private String lastName;
    private static String newCourseNamePrompt = "What would you like to name the new course?";
    private static String newCourseCreated = "New course has been created!";
    private static String courseSelectionPrompt = "Which course would you like to open?";

    private CourseClient courseClient;

    private ArrayList<String> courseList = new ArrayList<>();

    private JFrame jframe;

    private JButton createDiscussionForumButton;
    private JButton deleteDiscussionForumButton;
    private JButton openDiscussionForumsButton;
    private JButton createDiscussionForumViaFileImportButton;
    private JButton backButton;

    private BufferedReader bufferedReader;
    private BufferedReader dummyReader;

    private PrintWriter printWriter;

    public TeacherClient(String username, String firstName, String lastName, CourseClient courseClient, JFrame jframe, PrintWriter printWriter, BufferedReader bufferedReader, BufferedReader dummyReader) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courseClient = courseClient;
        this.jframe = jframe;
        this.printWriter = printWriter;
        this.bufferedReader = bufferedReader;
        this.dummyReader = dummyReader;
    }

    public void createDiscussionForumButtonMethod() throws IOException {
        printWriter.write("1");
        printWriter.println();
        printWriter.flush();
        courseClient.createForum();
    }
    public void deleteDiscussionForumButtonMethod() throws Exception {
        printWriter.write("2");
        printWriter.println();
        printWriter.flush();
        courseClient.deleteForum();
    }
    public void openDiscussionForumsButtonMethod() throws Exception {
        printWriter.write("3");
        printWriter.println();
        printWriter.flush();
        courseClient.teacherDiscussionForumOpened();
    }
    public void createDiscussionForumViaFileImportButtonMethod() {
        printWriter.write("4");
        printWriter.println();
        printWriter.flush();
        courseClient.createForumViaFileImport();
    }
    public void backMethod() throws Exception {
        printWriter.write("-1");
        printWriter.println();
        printWriter.flush();
        AccountClient accountClient = new AccountClient(username, firstName, lastName, true, jframe, printWriter, bufferedReader, dummyReader);
        accountClient.mainMethod();
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == createDiscussionForumButton) {
                try {
                    createDiscussionForumButtonMethod();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == deleteDiscussionForumButton) {
                try {
                    deleteDiscussionForumButtonMethod();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == openDiscussionForumsButton) {
                try {
                    openDiscussionForumsButtonMethod();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == backButton) {
                try {
                    backMethod();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == createDiscussionForumViaFileImportButton) {
                createDiscussionForumViaFileImportButtonMethod();
            }
        }
    };

    public void runMethodTeacher() {
        Container container = jframe.getContentPane();
        container.removeAll();
        container.setLayout(new GridLayout(2,1));
        container.revalidate();

        createDiscussionForumButton = new JButton("Create new discussion forum");
        createDiscussionForumButton.addActionListener(actionListener);
        createDiscussionForumButton.setBackground(Color.decode("#c0c0c0"));
        createDiscussionForumButton.setPreferredSize(new Dimension(130,100));
        deleteDiscussionForumButton = new JButton("Delete discussion forum");
        deleteDiscussionForumButton.addActionListener(actionListener);
        deleteDiscussionForumButton.setBackground(Color.decode("#c0c0c0"));
        deleteDiscussionForumButton.setPreferredSize(new Dimension(130,100));
        openDiscussionForumsButton = new JButton("Open discussion forums");
        openDiscussionForumsButton.addActionListener(actionListener);
        openDiscussionForumsButton.setBackground(Color.decode("#c0c0c0"));
        openDiscussionForumsButton.setPreferredSize(new Dimension(130,100));
        createDiscussionForumViaFileImportButton = new JButton("Create new discussion forum via file import");
        createDiscussionForumViaFileImportButton.addActionListener(actionListener);
        createDiscussionForumViaFileImportButton.setBackground(Color.decode("#c0c0c0"));
        createDiscussionForumViaFileImportButton.setPreferredSize(new Dimension(130,100));
        backButton = new JButton("Back");
        backButton.addActionListener(actionListener);
        backButton.setBackground(Color.decode("#c0c0c0"));
        backButton.setPreferredSize(new Dimension(130,100));

        JPanel topPanel = new JPanel();
        //topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.decode("#CEB888"));

        JPanel centerPanel = new JPanel(new GridLayout(3,2,20,20));
        centerPanel.add(openDiscussionForumsButton);
        centerPanel.add(createDiscussionForumButton);
        centerPanel.add(deleteDiscussionForumButton);
        centerPanel.add(createDiscussionForumViaFileImportButton);
        centerPanel.add(backButton);
        centerPanel.setBackground(Color.decode("#000000"));

        jframe.setSize(700, 600);

        JLabel titleText = new JLabel("Discussion Forum Options");
        titleText.setFont(new Font("Calibri", Font.BOLD, 28));
        topPanel.add(titleText);

        topPanel.setBorder(new EmptyBorder(80, 20, 20, 20));
        centerPanel.setBorder(new EmptyBorder(20, 20, 80, 20));

        container.add(topPanel);
        container.add(centerPanel);
    }

    public void createCourse() throws Exception {
        String newCourseName = JOptionPane.showInputDialog(null, newCourseNamePrompt, "New Course", JOptionPane.QUESTION_MESSAGE);
        if (newCourseName != null) {
            if (newCourseName.isBlank()) {
                String errorMessage = "Please enter a valid course name(ie. Not all spaces or blank).";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                printWriter.write(" ");
                printWriter.println();
                printWriter.flush();
            } else {
                printWriter.write(newCourseName);
                printWriter.println();
                printWriter.flush();

                String receivedData = null;
                try {
                    receivedData = bufferedReader.readLine();
                } catch (SocketException e) {
                    String errorMessage = "The server unexpectedly closed. Please try again later";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                if (receivedData.equals("1")) {
                    JOptionPane.showMessageDialog(null, newCourseCreated, "Success", JOptionPane.INFORMATION_MESSAGE);
                } else if (receivedData.equals("0")){
                    String errorMessage = "A course with that name already exists!";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            printWriter.write(" ");
            printWriter.println();
            printWriter.flush();

            try {
                String dummy = bufferedReader.readLine();
            } catch (SocketException e) {
                String errorMessage = "The server unexpectedly closed. Please try again later";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }        }
        AccountClient accountClient = new AccountClient(username, firstName, lastName, true, jframe, printWriter, bufferedReader, dummyReader);
        accountClient.mainMethod();
    }

    public void openCourse() throws Exception {
        String receivedData = null;
        try {
            receivedData = bufferedReader.readLine();
        } catch (SocketException e) {
            String errorMessage = "The server unexpectedly closed. Please try again later";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        if (receivedData.isBlank()) {
            printWriter.write(" ");
            printWriter.println();
            printWriter.flush();
            String errorMessage = "No course has been created yet.";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            AccountClient accountClient = new AccountClient(username, firstName, lastName, true, jframe, printWriter, bufferedReader, dummyReader);
            accountClient.mainMethod();
        } else {
            Object[] options = receivedData.split("§§§");
            Object selectedObject = JOptionPane.showInputDialog(null, courseSelectionPrompt, "Open Course", JOptionPane.PLAIN_MESSAGE, null, options, JOptionPane.CLOSED_OPTION);
            if (selectedObject != null) {
                String selectedCourse = selectedObject.toString();
                printWriter.write(selectedCourse);
                printWriter.println();
                printWriter.flush();
                courseClient = new CourseClient(selectedCourse, username, firstName, lastName, null, jframe, printWriter, bufferedReader, dummyReader);
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
            } else {
                printWriter.write(" ");
                printWriter.println();
                printWriter.flush();
                AccountClient accountClient = new AccountClient(username, firstName, lastName, true, jframe, printWriter, bufferedReader, dummyReader);
                accountClient.mainMethod();
            }
        }
    }
}
