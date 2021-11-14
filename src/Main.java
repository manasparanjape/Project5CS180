import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    String username;
    String firstName;
    String lastName;
    boolean ifTeacher;
    Teacher teacher;
    Student student;

    private final static String teacherAccountEnteredPrompt = """
            Please enter the option number of what you want to do.
            1) Create course
            2) Open course
            3) Log out""";

    private final static String studentAccountEnteredPrompt = """
            Please enter the option number of what you want to do.
            1) Open course
            2) Log out""";

    public Main(String username, String firstName, String lastName, boolean ifTeacher) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ifTeacher = ifTeacher;
    }

    public void teacherMainMethod() throws Exception {
        teacher = new Teacher(username, firstName, lastName, null);
        Scanner scan = new Scanner(System.in);
        int option = 0;
        while (option != 3) {
        	try {
        		System.out.println(teacherAccountEnteredPrompt);
        		option = scan.nextInt();
        	} catch (Exception e) {
        		System.out.println("Please enter a valid number!");
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
        //scan.close();
    }

    public void studentMainMethod() throws Exception {
        student = new Student(username, firstName, lastName, null, "CoursesList.txt");
        Scanner scan = new Scanner(System.in);
        int option = 0;
        while (option != 2) {
        	try {
                System.out.println(studentAccountEnteredPrompt);
        		option = scan.nextInt();
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
        //scan.close();
    }

    public void accountMainMethod() throws Exception {
        if (ifTeacher) {
            teacherMainMethod();
        } else {
            studentMainMethod();
        }
    }
}
