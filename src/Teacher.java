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
 * Teacher.java
 * <p>
 * Contains course selection level options for teachers
 *
 * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
 * @version 11/15/2021
 */

public class Teacher {
    String username;
    String firstName;
    String lastName;
    private Course course;
    private String coursesListFileName = "CoursesList.txt";

    private static String newCourseNamePrompt = "What would you like to name the new course?";
    private static String newCourseCreated = "New course has been created!";
    private static String courseSelectionPrompt = "Which course would you like to open?";
    private ArrayList<String> courseList = new ArrayList<>();

    public Teacher(String username, String firstName, String lastName, Course course, JFrame jframe) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.jframe = jframe;
    }

    //reads courses the “CoursesList.txt" file and stores in arraylist
    //stores all the course names in the courseList array list
    public void readCourseListsFile() throws IOException {
        File f = new File(coursesListFileName);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<String> output = new ArrayList<>();
        String line = bfr.readLine();
        while (line != null) {
            output.add(line);
            line = bfr.readLine();
        }
        courseList = output;
    }

    //checks if the given course exists
    public boolean courseExists(String courseName) throws IOException {
        readCourseListsFile();
        if (courseList == null) {
            return false;
        } else {
            return courseList.contains(courseName);
        }
    }

    //checks if user inputted course is present in the courseList array list
    //reads all the discussion forum title names for that specific course
    public void openCourse() throws Exception {
        readCourseListsFile();
        Object[] options = new Object[courseList.size()];
        for (int i = 0; i < courseList.size(); i++) {
            options[i] = courseList.get(i);
        }
        Object selectedObject = JOptionPane.showInputDialog(jframe, courseSelectionPrompt, "Open Course", JOptionPane.PLAIN_MESSAGE, null, options, JOptionPane.CLOSED_OPTION);
        if (selectedObject != null) {
            String selectedCourse = selectedObject.toString();
            String discussionBoardsListFileName = selectedCourse + "-forumslist.txt";
            course = new Course(selectedCourse, username, firstName, lastName, null, discussionBoardsListFileName, jframe);
            course.readForumListFile();
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

    JFrame jframe;

    JButton createDiscussionForumButton;
    JButton deleteDiscussionForumButton;
    JButton openDiscussionForumsButton;
    JButton createDiscussionForumViaFileImportButton;
    JButton backButton;

    public void createDiscussionForumButtonMethod() throws FileNotFoundException {
        course.createForumInGUI();
    }
    public void deleteDiscussionForumButtonMethod() throws Exception {
        course.deleteForumInGUI();
    }
    public void openDiscussionForumsButtonMethod() throws Exception {
        course.teacherDiscussionForumOpened();
    }
    public void createDiscussionForumViaFileImportButtonMethod() {
        course.createForumViaFileImportInGUI();
    }
    public void backButtonMethod() throws Exception {
        Account account = new Account(username, firstName, lastName, true, jframe);
        account.accountMainMethod();
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == createDiscussionForumButton) {
                try {
                    createDiscussionForumButtonMethod();
                } catch (FileNotFoundException ex) {
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
                    backButtonMethod();
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

        jframe.setSize(900, 600);
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);

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

    public void runnableMethod() {
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

    public void createCourseInGUI() throws IOException {
        String newCourseName = JOptionPane.showInputDialog(null, newCourseNamePrompt, "New Course", JOptionPane.QUESTION_MESSAGE);
        if (newCourseName != null) {
            if (newCourseName.isBlank()) {
                String errorMessage = "Please enter a valid course name(ie. Not all spaces or blank).";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } else if (courseExists(newCourseName)) {
                String errorMessage = "A course with that name already exists.";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                courseList.add(newCourseName);
                FileOutputStream fos = new FileOutputStream(newCourseName + "-forumslist.txt", false);
                PrintWriter pw = new PrintWriter(fos);
                pw.println();
                pw.close();
                fos = new FileOutputStream(coursesListFileName, true);
                pw = new PrintWriter(fos);
                pw.println(newCourseName);
                System.out.println(newCourseCreated);
                pw.close();
            }
        }
    }
}
