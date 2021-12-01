import javax.swing.*;
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

    public Account(String username, String firstName, String lastName, boolean ifTeacher, Scanner scan) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ifTeacher = ifTeacher;
        this.scan = scan;
    }

    //gives the teacher three options: create course, open course, or exit
    public void teacherMainMethod() throws Exception {
        int option = 0;
        try {
            String[] options = {"Create a course", "Open a course", "Log Out"};
            option = JOptionPane.showOptionDialog(null, "Select an option",
                    "Option choosing", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            teacher = new Teacher(username, firstName, lastName, null, scan);
            while (option != 2) {
                try {
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
                if (option < 0 || option > 1) {
                    System.out.println("You entered an invalid option. Please enter a number between 1 and 3.");
                } else {
                    switch (option) {
                        case 0 -> teacher.createCourse();
                        case 1 -> teacher.openCourse();
                        //case 2 -> JOptionPane.showMessageDialog();
                    }
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("You did not input an integer. Please input an integer between 1 and 4.");
            option = 0;
            scan.nextLine();
        }
    }

    //gives the student two options: open a course or exit
    public void studentMainMethod() throws Exception {
        student = new Student(username, firstName, lastName, null, "CoursesList.txt", scan);
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
            teacherMainMethod();
        } else {
            studentMainMethod();
        }
    }
}
