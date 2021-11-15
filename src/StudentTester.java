import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * StudentTester.java
 *
 * Contains instructions for testing student class
 *
 * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
 *
 * @version 11/15/2021
 *
 */

public class StudentTester {
  
  public static void main(String[] args) throws Exception {
    
    Main studentOne = null;
    //Creating Accounts object representing a student account
    studentOne = new Main("Billy23", "B1L@#", "Billy", "Smith", false);
    if(studentOne != null) {
      System.out.println(true);
    } else {
      System.out.println(false);
    }
    //successfully created if true
    
    //Testing covertToString method in Main.java
    String studentString = studentOne.convertToString();
    System.out.println(studentString.equals("Billy23---B1L@#---Billy---Smith---student"));
    //returns true if method matches expected output
    
    //check if writeToFile method returns a FileNotFoundException
    try {
    	studentOne.writeToFile();
    } catch(FileNotFoundException e) {
    	System.out.println("File Not Found");
    }
    
    //checks if username is already in use; true if username is used and false otherwise
    System.out.println(studentOne.checkUsernameAvailability("Billy23"));
    
  }
  
}
