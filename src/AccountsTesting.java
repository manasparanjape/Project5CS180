import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountsTesting {
  //Tester class used to test each method included in the Accounts class
  public static void main(String[] args) throws Exception {
    
    //Creating Accounts object representing a student account
    Accounts studentOne = new Accounts("Billy23", "B1L@#", "Billy", "Smith", false);
    
    //Testing covertToString method in Accounts.java
    String studentString = studentOne.convertToString();
    System.out.println(studentString);
    //output:
    //Billy23---B1L@#---Billy---Smith---student
    //matches expected output
    
    //check if writeToFile method returns a FileNotFoundException
    try {
    	studentOne.writeToFile();
    } catch(FileNotFoundException e) {
    	System.out.println("File Not Found");
    }


    //main method in Accounts that tests full functionality of the project
    //Tests capabilities of the program to create, login, and logout of an account
    //Tests whether students can open courses and view forums and points. 
    Accounts.main(args);
    
    
  }
}
