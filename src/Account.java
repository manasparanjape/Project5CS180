import java.util.Scanner;

/**
 * Account.java
 *
 * Bifurcates to teacher and student methods respectively
 *
 * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
 *
 * @version 11/15/2021
 *
 */

public class Account {
    String username;
    String firstName;
    String lastName;
    boolean ifTeacher;
    Teacher teacher;
    Student student;
    Scanner scan;

    private final static String teacherAccountEnteredPrompt = "Please enter the option number of what you want to do.\n1) Create course\n2) Open course\n3) Log out";
    private final static String studentAccountEnteredPrompt = "Please enter the option number of what you want to do.\n1) Open course\n2) Log out";

    public Account(String username, String firstName, String lastName, boolean ifTeacher, Scanner scan) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ifTeacher = ifTeacher;
        this.scan = scan;
    }

    public void teacherMainMethod() throws Exception {
        teacher = new Teacher(username, firstName, lastName, null, scan);
        int option = 0;
        while (option != 3) {
            try {
                System.out.println(teacherAccountEnteredPrompt);
                option = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("You did not input an integer. Please input a number between 1 and 3.");
                option = 0;
                scan.nextLine();
                continue;
            }
            if (option < 1 || option > 3) {
                System.out.println("You entered an invalid option. Please enter a number between 1 and 3.");
            } else {
                switch (option) {
                    case 1 -> teacher.createCourse();
                    case 2 -> teacher.openCourse();
                }
            }
        }
    }

    public void studentMainMethod() throws Exception {
        student = new Student(username, firstName, lastName, null, "CoursesList.txt", scan);
        int option = 0;
        while (option != 2) {
            try {
                System.out.println(studentAccountEnteredPrompt);
                option = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("Please enter a valid number!");
                option = 0;
                scan.nextLine();
                continue;
            }
            if (option < 1 || option > 2) {
                System.out.println("You entered an invalid option. Please enter a number between 1 and 2.");
            } else {
                if (option == 1) {
                    student.openCourse();
                }
            }
        }
    }

    public void accountMainMethod() throws Exception {
        if (ifTeacher) {
            teacherMainMethod();
        } else {
            studentMainMethod();
        }
    }
}
