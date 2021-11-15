import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * StudentTester.java
 * <p>
 * Contains instructions for testing Main class
 *
 * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
 * @version 11/15/2021
 */

public class LoginTester {

    public static void main(String[] args) throws Exception {

        //main method in Accounts that tests full functionality of the project
        //Accounts test:
        //Tests capabilities of the program to create, delete, login, and logout of an account
        //[] indicate input

        Main.main(args);

        //Test: Creating teacher account and loggging in and logging out
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
        //Test: Logging into an existing teacher account
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

        //Test: Creating student account and loggging in and logging out 
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
        [SachinP]
        Set a new password:
        [sp!2]
        Enter your first name:
        [Sachin]
        Enter your last name:
        [Patel]
        Are you a teacher or a student?
        1. Teacher
        2. Student
        [2]
        Your account was successfully created
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [1]
        Please enter your username.
        [SachinP]
        Please enter your password
        [sp!2]
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
        //Test: Logging into an existing student account
        /*
        Welcome!
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [1]
        Please enter your username.
        [SachinP]
        Please enter your password
        [sp!2]
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
        //Test: Delete an account
        /*
        Welcome!
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [3]
        Please enter your username.
        [SachinP]
        Please enter your password
        [sp!2]
        Your account has been deleted
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [4]
        */


        //Error Tests
        //Test: Input string instead of integer and input integer out of bounds
        /*
        Welcome!
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [login]
        You did not input an integer. Please input an integer between 1 and 4.
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [sign in]
        You did not input an integer. Please input an integer between 1 and 4.
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [6]
        You entered an invalid option. Please enter a number between 1 and 4.
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [4]
        */
        //Test: Invalid Login
        /*
        Welcome!
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [1]
        Please enter your username.
        [peepee]
        Please enter your password
        [poopoo]
        The account details you entered are invalid.
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [1]
        Please enter your username.
        [SachinP]
        Please enter your password
        [poopoo]
        The account details you entered are invalid.
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [4]    
        */
        //Test: trying to create account with already active username
        /*
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [2]
        Set a new username:
        All usernames are case insensitive.
        [SachinP]
        Set a new password:
        [ps091]
        Enter your first name:
        [Sachin]
        Enter your last name:
        [Poop]
        Are you a teacher or a student?
        1. Teacher
        2. Student
        [2]
        The username you entered is unavailable.
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [4]
        */


    }

}
