import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * TeacherTester.java
 *
 * Contains instructions for testing teacher class
 *
 * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
 *
 * @version 11/15/2021
 *
 */
public class TeacherTester {
  
  public static void main(String[] args) throws Exception {
    //Creating Accounts object representing a teacher account
    Main teacherOne = null;
    teacherOne = new Main("Ms.Paul", "skooliskool", "Mary", "Paul", true);
    if(teacherOne != null) {
      System.out.println(true);
    } else {
      System.out.println(false);
    }
    //successfully created if true
    
    //Testing covertToString method in Main.java
    String teacherString = teacherOne.convertToString();
    System.out.println(teacherString.equals("Ms.Paul---skooliskool---Mary---Paul---teacher"));
    //returns true if method matches expected output
    
    //check if writeToFile method returns a FileNotFoundException
    try {
    	teacherOne.writeToFile();
    } catch(FileNotFoundException e) {
    	System.out.println("File Not Found");
    }
    
    //checks if username is already in use; true if username is used and false otherwise
    System.out.println(teacherOne.checkUsernameAvailability("Ms.Paul"));
    
  }
}
