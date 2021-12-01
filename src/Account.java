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
    Scanner scan;

    private static String teacherAccountEnteredPrompt = "Please enter the option number of " +
            "what you want to do.\n" +
            "1) Create course\n2) Open course\n3) Log out";
    private static String studentAccountEnteredPrompt = "Please enter the option number of " +
            "what you want to do.\n" +
            "1) Open course\n2) Log out";

    public Account(String username, String firstName, String lastName, boolean ifTeacher, Scanner scan, JFrame jframe) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ifTeacher = ifTeacher;
        this.scan = scan;
        this.jframe = jframe;
    }

    //gives the teacher three options: create course, open course, or exit
    public void teacherMainMethod() throws Exception {
        /*int option = 0;
        try {
            String[] options = {"Create a course", "Open a course", "Log Out"};
            option = JOptionPane.showOptionDialog(null, "Select an option",
                    "Option choosing", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[1]);
            teacher = new Teacher(username, firstName, lastName, null, scan, jframe);
            while (option != 3) {
                /*try {
                    System.out.println(teacherAccountEnteredPrompt);
                    option = scan.nextInt();
                    scan.nextLine();
                } catch (Exception e) {
                    System.out.println("You did not input an integer. Please input a number between 1 and 3.");
                    //todo: check what option = 0 means in the gui
                    option = 0;
                    scan.nextLine();
                    continue;
                }
                if (option < 0 || option > 3) {
                    System.out.println("You entered an invalid option. Please enter a number between 1 and 3.");
                } else {
                    switch (option) {
                        case 0 -> teacher.createCourse();
                        case 1 -> teacher.openCourse();
                        //case 2 -> JOptionPane.showMessageDialog();
                    }
                //}
            }
        } catch (InputMismatchException e) {
            System.out.println("You did not input an integer. Please input an integer between 1 and 4.");
            option = 0;
            scan.nextLine();
        }*/
        teacher = new Teacher(username, firstName, lastName, null, scan, jframe);
        runMethodTeacher();
    }

    //gives the student two options: open a course or exit
    public void studentMainMethod() throws Exception {
        student = new Student(username, firstName, lastName, null, scan, jframe);
        int option = 0;
        try {
            String[] options = {"Open a course", "Log Out"};
            option = JOptionPane.showOptionDialog(null, "Select an option",
                    "Option choosing", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            while (option != 1) {
                try {
                    System.out.println(studentAccountEnteredPrompt);
                    option = scan.nextInt();
                    scan.nextLine();
                } catch (Exception e) {
                    System.out.println("Please enter a valid number!");
                    //todo: check what option = 0 means in the gui
                    option = 0;
                    scan.nextLine();
                    continue;
                }
                if (option < 0 || option > 1) {
                    System.out.println("You entered an invalid option. Please enter a number between 1 and 2.");
                } else {
                    if (option == 1) {
                        student.openCourse();
                    }
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("You did not input an integer. Please input an integer between 1 and 4.");
            option = 0;
            scan.nextLine();
        }
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
    JButton backButton;

    public void createCourseButtonMethod() throws IOException {
        teacher.createCourseInGUI();
    }
    public void openCourseButtonMethod() throws Exception {
        teacher.openCourse();
    }
    public void backButtonMethod() throws Exception {
        //Main.main(null);
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

        createCourseButton = new JButton("Create Course");
        createCourseButton.addActionListener(actionListener);
        openCourseButton = new JButton("Open Course");
        openCourseButton.addActionListener(actionListener);
        backButton = new JButton("Back");
        backButton.addActionListener(actionListener);

        jframe.setSize(900, 600);
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);

        JPanel centerPanel = new JPanel();
        centerPanel.add(openCourseButton);
        centerPanel.add(createCourseButton);

        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(backButton);

        container.add(centerPanel, BorderLayout.CENTER);
        container.add(leftPanel, BorderLayout.WEST);
        System.out.println("Check");
    }
}
