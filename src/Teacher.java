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
    Scanner scan;

    private static String newCourseNamePrompt = "What would you like to name the new course?";
    private static String newCourseCreated = "New course has been created!";
    private static String courseSelectionPrompt = "Which course would you like to open?";
    private static String courseEnteredPrompt = "Please enter the option number of what you want to do.\n1)"
            + " Open discussion forum\n2) Create discussion forum\n3) Delete discussion forum\n4) Exit course";
    private ArrayList<String> courseList = new ArrayList<>();

    public Teacher(String username, String firstName, String lastName, Course course, Scanner scan, JFrame jframe) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.scan = scan;
        this.jframe = jframe;
    }

    //uses a for loop to iterate through the courseList array and appends
    //every course to a StringBuilder object
    //prints out course list as string
    public void printCourseList() {
        StringBuilder output = new StringBuilder();
        for (String s : courseList) {
            output.append(s).append("\n");
        }
        output = new StringBuilder(output.substring(0, output.length() - 1));
        System.out.println(output);
        if (output.toString().isEmpty()) {
            System.out.println("No courses created yet.");
        }
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

    //creates a course
    //adds course name to courseList array list
    //writes course name to text file using PrintWriter
    public void createCourse() throws IOException {
        System.out.println(newCourseNamePrompt);
        String newCourseName = scan.nextLine();
        if (newCourseName == null || newCourseName.isBlank()) {
            System.out.println("Please enter a valid course name(ie. Not all spaces or blank).");
        } else if (courseExists(newCourseName)) {
            System.out.println("A course with that name already exists.");
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

    //checks if user inputted course is present in the courseList array list
    //reads all the discussion forum title names for that specific course
    public void openCourse() throws Exception {
        readCourseListsFile();
        printCourseList();
        System.out.println(courseSelectionPrompt);
        String selectedCourse = scan.nextLine();
        if (selectedCourse == null || !courseExists(selectedCourse)) {
            System.out.println("The course you entered does not exist!");
        } else {
            String discussionBoardsListFileName = selectedCourse + "-forumslist.txt";
            course = new Course(selectedCourse, username, firstName, lastName, null, discussionBoardsListFileName, scan, jframe);
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
            //openCourseMainMethod();
        }
    }

    //gives user four options: open discussion forum, create forum, delete forum, and show dashboard
    public void openCourseMainMethod() throws Exception {
        int option = 0;
        while (option != 4) {
            try {
                System.out.println(courseEnteredPrompt);
                option = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("You did not input an integer. Please input an integer between 1 and 5.");
                option = 0;
                scan.nextLine();
                continue;
            }
            if (option < 1 || option > 4) {
                System.out.println("You entered an invalid number. Please enter a number between 1 and 5.");
            } else {
                switch (option) {
                    case 1 -> course.teacherDiscussionForumOpened();
                    case 2 -> course.createForum();
                    case 3 -> course.deleteForum();
                }
            }
        }
    }

    /*
     * GUI part
     * how to create the interface.
     */

    //JFrame jframe = new JFrame();
    JFrame jframe;

    JButton createDiscussionForumButton;
    JButton deleteDiscussionForumButton;
    JButton openDiscussionForumsButton;
    JButton backButton;

    public void createDiscussionForumButtonMethod() throws FileNotFoundException {
        course.createForumInGUI();
    }
    public void deleteDiscussionForumButtonMethod() throws FileNotFoundException {
        course.deleteForumInGUI();
    }
    public void openDiscussionForumsButtonMethod() throws Exception {
        course.teacherDiscussionForumOpened();
    }
    public void backButtonMethod() throws Exception {
        Account account = new Account(username, firstName, lastName, true, scan);
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
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == openDiscussionForumsButton) {
                try {
                    openDiscussionForumsButtonMethod();
                } catch (Exception ex) {
                    System.out.println("Check1");
                    ex.printStackTrace();
                    System.out.println("Check2");
                }
            }
            if (e.getSource() == backButton) {
                try {
                    backButtonMethod();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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
}
