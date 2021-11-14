import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountsTesting {
  //Tester class used to test each method included in the Accounts class
  public static void main(String[] args) throws Exception {
    
    Accounts studentOne = null;
    //Creating Accounts object representing a student account
    Accounts studentOne = new Accounts("Billy23", "B1L@#", "Billy", "Smith", false);
    if(studentOne != null) {
      System.out.println(true);
    } else {
      System.out.println(false);
    }
    //successfully created if true
    
    //Testing covertToString method in Accounts.java
    String studentString = studentOne.convertToString();
    System.out.println(studentString.equals("Billy23---B1L@#---Billy---Smith---student"));
    //returns true if method matches expected output
   
    //Creating Accounts object representing a teacher account
    Accounts teacherOne = null;
    Accounts teacherOne = new Accounts("Ms.Paul", "skooliskool", "Mary", "Paul", true);
    if(teacherOne != null) {
      System.out.println(true);
    } else {
      System.out.println(false);
    }
    //Testing covertToString method in Accounts.java
    String teacherString = teacherOne.convertToString();
    System.out.println(studentString.equals("Ms.Paul---skooliskool---Mary---Paul---teacher"));
    //returns true if method matches expected output
    
    //check if writeToFile method returns a FileNotFoundException
    try {
    	studentOne.writeToFile();
    } catch(FileNotFoundException e) {
    	System.out.println("File Not Found");
    }
    //passed; does not return file not found
    
    //checks if username is already in use; true if username is used and false otherwise
    System.out.println(studentOne.checkUsernameAvailability("Billy23"));
    
    //main method in Accounts that tests full functionality of the project
    //Tests capabilities of the program to create, delete, login, and logout of an account
    //Tests if Teachers can create, edit, and delete forums
    //Tests if Students can create replies
    //Tests whether students can open courses and view forums and points. 
    //Tests methods that require all five classes to work together
    Accounts.main(args);
    
    
  }
}
