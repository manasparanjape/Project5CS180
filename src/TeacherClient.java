import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
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
        container.setLayout(new BorderLayout());
        container.revalidate();

        createDiscussionForumButton = new JButton("Create new discussion forum");
        createDiscussionForumButton.addActionListener(actionListener);
        deleteDiscussionForumButton = new JButton("Delete discussion forum");
        deleteDiscussionForumButton.addActionListener(actionListener);
        openDiscussionForumsButton = new JButton("Open discussion forums");
        openDiscussionForumsButton.addActionListener(actionListener);
        createDiscussionForumViaFileImportButton = new JButton("Create new discussion forum via file import");
        createDiscussionForumViaFileImportButton.addActionListener(actionListener);
        backButton = new JButton("Back");
        backButton.addActionListener(actionListener);

        //jframe.setSize(900, 600);
        //jframe.setLocationRelativeTo(null);
        //jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //jframe.setVisible(true);

        JPanel centerPanel = new JPanel();
        centerPanel.add(openDiscussionForumsButton);
        centerPanel.add(createDiscussionForumButton);
        centerPanel.add(deleteDiscussionForumButton);
        centerPanel.add(createDiscussionForumViaFileImportButton);

        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(backButton);

        container.add(centerPanel, BorderLayout.CENTER);
        container.add(leftPanel, BorderLayout.WEST);
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

                String receivedData = bufferedReader.readLine();
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
            String dummy = bufferedReader.readLine();
        }
        AccountClient accountClient = new AccountClient(username, firstName, lastName, true, jframe, printWriter, bufferedReader, dummyReader);
        accountClient.mainMethod();
    }

    public void openCourse() throws Exception {
        String receivedData = bufferedReader.readLine();
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
