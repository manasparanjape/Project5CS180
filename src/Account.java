import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Account.java
 *
 * Bifurcates to teacher and student methods respectively
 *
 * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
 *
 * @version 11/15/2021
 */

public class Account {
    String username;
    String firstName;
    String lastName;
    boolean ifTeacher;
    Teacher teacher;
    Student student;

    public Account(String username, String firstName, String lastName, boolean ifTeacher, JFrame jframe) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ifTeacher = ifTeacher;
        this.jframe = jframe;
    }

    //gives the teacher three options: create course, open course, or exit
    public void teacherMainMethod() throws Exception {
        teacher = new Teacher(username, firstName, lastName, null, jframe);
        runMethodTeacher();
    }

    //gives the student two options: open a course or exit
    public void studentMainMethod() throws Exception {
        student = new Student(username, firstName, lastName, null, jframe);
        runMethodStudent();
    }

    //decides which method to run in the main method depending on whether the user is a student or a teacher
    public void accountMainMethod() throws Exception {
        if (ifTeacher) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        teacherMainMethod();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            studentMainMethod();
        }
    }

    JFrame jframe;

    JButton createCourseButton;
    JButton openCourseButton;
    JButton logoutButton;

    public void createCourseButtonMethod() throws IOException {
        teacher.createCourseInGUI();
    }
    public void openCourseButtonMethod() throws Exception {
        if (ifTeacher) {
            teacher.openCourse();
        } else {
            student.openCourse();
        }
    }
    public void logoutButtonMethod() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    MainClient.mainRunMethod();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == createCourseButton) {
                try {
                    createCourseButtonMethod();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == openCourseButton) {
                try {
                    openCourseButtonMethod();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == logoutButton) {
                logoutButtonMethod();
            }
        }
    };

    public void runMethodTeacher() {
        Container container = jframe.getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());
        container.revalidate();

        createCourseButton = new JButton("Create Course");
        createCourseButton.addActionListener(actionListener);
        openCourseButton = new JButton("Open Course");
        openCourseButton.addActionListener(actionListener);
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(actionListener);

        jframe.setSize(900, 600);
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);

        JPanel centerPanel = new JPanel();
        centerPanel.add(openCourseButton);
        centerPanel.add(createCourseButton);

        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(logoutButton);

        container.add(centerPanel, BorderLayout.CENTER);
        container.add(leftPanel, BorderLayout.WEST);
    }

    public void runMethodStudent() {
        Container container = jframe.getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());
        container.revalidate();

        openCourseButton = new JButton("Open Course");
        openCourseButton.addActionListener(actionListener);
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(actionListener);

        jframe.setSize(900, 600);
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);

        JPanel centerPanel = new JPanel();
        centerPanel.add(openCourseButton);

        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(logoutButton);

        container.add(centerPanel, BorderLayout.CENTER);
        container.add(leftPanel, BorderLayout.WEST);
    }
}
