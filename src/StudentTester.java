import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentTester {
  
  public static void main(String[] args) throws Exception {
    
    Accounts studentOne = null;
    //Creating Accounts object representing a student account
    studentOne = new Accounts("Billy23", "B1L@#", "Billy", "Smith", false);
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
