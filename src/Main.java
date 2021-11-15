import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main.java
 *
 * Contains the main method and runs the program
 *
 * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
 *
 * @version 11/15/2021
 *
 */

public class Main {
    private static final String accountsFile = "AccountsFile.txt";
    private static String username;
    private static String password;
    private static String firstName;
    private static String lastName;
    private static boolean ifTeacher;
    private final static String welcomeMessage = "Welcome! ";
    private final static String initialPrompt = "What would you like to do today?\n1) Login to my account\n2)"
            + " Create new account\n3) Delete my account\n4) Exit";
    private final static String usernamePrompt = "Please enter your username.";
    private final static String passwordPrompt = "Please enter your password";
    private final static String setUsernamePrompt = "Set a new username:";
    private final static String setPasswordPrompt = "Set a new password:";
    private static final String setFirstNamePrompt = "Enter your first name:";
    private static final String setLastNamePrompt = "Enter your last name:";
    private static final String usernameUnavailablePrompt = "The username you entered is unavailable.";
    private static final  String accountCreationSuccess = "Your account was successfully created";
    private final static String accountTypePrompt = "Are you a teacher or a student?\n1. Teacher\n2. Student";
    private final static String accountDeletionSuccess = "Your account has been deleted";
    static ArrayList<ArrayList<String>> accountDetailsArray = null;

    public Main (String username, String password, String firstName, String lastName, boolean ifTeacher) {
        Main.username = username;
        Main.password = password;
        Main.firstName = firstName;
        Main.lastName = lastName;
        Main.ifTeacher = ifTeacher;
    }
    //creates user info string, with their info separated by "§§§"
    public static String convertToString() {
        String output = username + "§§§";
        output += password + "§§§";
        output += firstName + "§§§";
        output += lastName + "§§§";
        if (ifTeacher) {
            output += "teacher";
        } else {
            output += "student";
        }
        return output;
    }
    //writes a given string to an account file
    public static void writeToFile() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(accountsFile, true);
        PrintWriter pw = new PrintWriter(fos);
        pw.println(convertToString());
        pw.close();
    }
    //reads text file to an ArrayList inside an ArrayList called "output"
    public static void readFile() throws IOException {
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        File f = new File(accountsFile);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();
        while (line != null) {
            String[] separatedLine = line.split("§§§");
            ArrayList<String> singleLine = new ArrayList<>(Arrays.asList(separatedLine));
            output.add(singleLine);
            line = bfr.readLine();
        }
        bfr.close();
        accountDetailsArray = output;
    }
    //checks if a given username exists using while loop
    public static boolean checkUsernameAvailability(String username) throws IOException {
        boolean output = false;
        int i = 0;
        readFile();
        while (!output && i < accountDetailsArray.size()) {
            output = accountDetailsArray.get(i).get(0).equalsIgnoreCase(username);
            i++;
        }
        return output;
    }
    //prompts user to create a new account
    //also checks if the details they enter are still available
    public static void getNewAccountDetails(Scanner scan) throws IOException {
        boolean accountSet;
        accountSet = true;
        System.out.println(setUsernamePrompt);
        System.out.println("All usernames are case insensitive.");
        username = scan.nextLine();
        System.out.println(setPasswordPrompt);
        password = scan.nextLine();
        System.out.println(setFirstNamePrompt);
        firstName = scan.nextLine();
        System.out.println(setLastNamePrompt);
        lastName = scan.nextLine();
        System.out.println(accountTypePrompt);
        String accountType = scan.nextLine();

        if (username == null) {
            accountSet = false;
            System.out.println("The username you entered is invalid.");
        } else if (checkUsernameAvailability(username)){
            accountSet = false;
            System.out.println(usernameUnavailablePrompt);
        }
        if (password == null) {
            accountSet = false;
            System.out.println("The password you entered is invalid.");
        }
        if (firstName == null) {
            accountSet = false;
            System.out.println("The first name you entered is invalid.");
        }
        if (lastName == null) {
            accountSet = false;
            System.out.println("The last name you entered is invalid.");
        }
        if (!accountType.equals("1") && !accountType.equals("2")) {
            accountSet = false;
            System.out.println("Please choose a valid account type.");
        } else {
            ifTeacher = accountType.equals("1");
        }
        if (accountSet) {
            username = username.toLowerCase();
            writeToFile();
            System.out.println(accountCreationSuccess);
        }
    }
    //finds a given account from Accounts.txt file
    public static void findAccount(String username) throws IOException {
        readFile();
        for (ArrayList<String> strings : accountDetailsArray) {
            if (strings.get(0).equals(username)) {
                firstName = strings.get(2);
                lastName = strings.get(3);
                ifTeacher = strings.get(4).equals("teacher");
            }
        }
    }
    //checks whether or not username and password match with what is stored in Accounts.txt file
    public static int securityCheck(Scanner scan) throws IOException {
        int output = 0;
        System.out.println(usernamePrompt);
        username = scan.nextLine();
        System.out.println(passwordPrompt);
        String password = scan.nextLine();
        if (username == null || password == null) {
            System.out.println("The details entered were invalid");
        } else {
            readFile();
            int i = 0;
            boolean usernameFound = false;
            boolean passwordMatch = false;
            while (i < accountDetailsArray.size() && !usernameFound) {
                usernameFound = accountDetailsArray.get(i).get(0).equalsIgnoreCase(username);
                i++;
            }
            if (usernameFound) {
                passwordMatch = accountDetailsArray.get(i - 1).get(1).equals(password);
            }
            if (usernameFound && passwordMatch) {
                if (accountDetailsArray.get(i - 1).get(4).equals("teacher")) {
                    output = 1;
                } else if (accountDetailsArray.get(i - 1).get(4).equals("student")) {
                    output = 2;
                }
            } else {
                System.out.println("The account details you entered are invalid.");
            }
        }
        return output;
    }
    //checks if the user is logged in
    //deletes the users account
    public static void deleteAccount(Scanner scan) throws IOException {
        boolean accountVerification = (securityCheck(scan) != 0);
        StringBuilder toWrite = new StringBuilder();
        if (accountVerification) {
            for (ArrayList<String> strings : accountDetailsArray) {
                if (!strings.get(0).equals(username)) {
                    for (int j = 0; j < 5; j++) {
                        toWrite.append(strings.get(j)).append("§§§");
                    }
                    toWrite = new StringBuilder(toWrite.substring(0, toWrite.length() - 3));
                    toWrite.append("\n");
                }
            }
            toWrite = new StringBuilder(toWrite.substring(0, toWrite.length() - 1));
            boolean accountRemoved = false;
            int i = 0;
            while (!accountRemoved && i < accountDetailsArray.size()) {
                if (accountDetailsArray.get(i).get(0).equals(username)) {
                    accountDetailsArray.remove(i);
                    accountRemoved = true;
                }
                i++;
            }
            FileOutputStream fos = new FileOutputStream(accountsFile, false);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(toWrite);
            pw.close();
            System.out.println(accountDeletionSuccess);
        }
    }
    //allows users to access, create, or delete their account
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        int option = 0;
        System.out.println(welcomeMessage);
        while (option != 4) {
            try {
                System.out.println(initialPrompt);
                option = scan.nextInt();
                scan.nextLine();
                if (option < 1 || option > 4) {
                    System.out.println("You entered an invalid option. Please enter a number between 1 and 4.");
                } else {
                    switch(option) {
                        case 1 -> {
                            int accountCheck = securityCheck(scan);
                            Account account;
                            if (accountCheck == 1) {
                                findAccount(username);
                                account = new Account(username, firstName, lastName, true);
                                account.accountMainMethod(scan);
                            } else if (accountCheck == 2) {
                                findAccount(username);
                                account = new Account(username, firstName, lastName, false);
                                account.accountMainMethod(scan);
                            }
                        }
                        case 2 -> getNewAccountDetails(scan); 
                        case 3 -> deleteAccount(scan); 
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("You did not input an integer. Please input an integer between 1 and 4.");
                option = 0;
                scan.nextLine();
            }
        }
        scan.close();
    }
}
