
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountsTester {
  
  public static void main(String[] args) throws Exception {
    
    //main method in Accounts that tests full functionality of the project
    //Accounts test:
    //Tests capabilities of the program to create, delete, login, and logout of an account
    //[] indicate input
    //Test: Creating account and loggging in and logging out
    /*
    Welcome!
    What would you like to do today?
    1) Login to my account
    2) Create new account
    3) Delete my account
    4) Exit
    [2]
    Set a new username:
    All usernames are case insensitive.
    [Ms.Hibblestein]
    Set a new password:
    [hibblestein2293]
    Enter your first name:
    [Stacy]
    Enter your last name:
    [Hibblestein]
    Are you a teacher or a student?
    1. Teacher
    2. Student
    [1]
    Your account was successfully created
    What would you like to do today?
    1) Login to my account
    2) Create new account
    3) Delete my account
    4) Exit
    [1]
    Please enter your username.
    [Ms.Hibblestein]
    Please enter your password
    [hibblestein2293]
    Please enter the option number of what you want to do.
    1) Create course
    2) Open course
    3) Log out
    [3]
    What would you like to do today?
    1) Login to my account
    2) Create new account
    3) Delete my account
    4) Exit
    [4]
    */
    //Test: Logging into an existing account
    /*
    Welcome!
    What would you like to do today?
    1) Login to my account
    2) Create new account
    3) Delete my account
    4) Exit
    [1]
    Please enter your username.
    [Ms.Hibblestein]
    Please enter your password
    [hibblestein2293]
    Please enter the option number of what you want to do.
    1) Create course
    2) Open course
    3) Log out
    [3]
    What would you like to do today?
    1) Login to my account
    2) Create new account
    3) Delete my account
    4) Exit
    [4]
    */

    //Tests if Teachers can create, edit, and delete forums
    //tests if Teachers can reply to student responses.
    Accounts.main(args);
    
  }
  
}
