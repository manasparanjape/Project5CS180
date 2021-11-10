import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Accounts {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private boolean ifTeacher;
    private final static String tryAgainPrompt = "Error! Do you want to try again?\n" + "1. Yes\n" + "2. No";
    private final static String usernamePrompt = "Please enter your username.";
    private final static String passwordPrompt = "Please enter your password";
    private final static String setUsernamePrompt = "Set a new username:";
    private final static String setPasswordPrompt = "Set a new password:";
    private static final String setFirstNamePrompt = "Enter your first name:";
    private static final String setLastNamePrompt = "Enter your last name:";
    private static final String usernameUnavailablePrompt = "The username you entered is unavailable.";
    private static final  String accountCreationSuccess = "Your account was successfully created";
    private final static String accountTypePrompt = "Are you a teacher or a student?\n" + "1. Teacher\n" + "2. Student";
    String filename = "AccountDetails.txt";

    public Accounts(String username, String password, String firstName, String lastName, boolean ifTeacher) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ifTeacher = ifTeacher;
    }

    public String toString() {
        String output = username + "; ";
        output += password + "; ";
        output += firstName + "; ";
        output += lastName + "; ";
        if (ifTeacher) {
            output += "teacher";
        } else {
            output += "student";
        }
        return output;
    }

    public void writeToFile() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(filename, true);
        PrintWriter pw = new PrintWriter(fos);
        pw.println(toString());
        pw.close();
    }

    public ArrayList<ArrayList<String>> readFile() throws IOException {
        ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
        File f = new File(filename);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();
        while (line != null) {
            String[] separatedLine = line.split(";");
            ArrayList<String> singleLine = new ArrayList<String>(Arrays.asList(separatedLine));
            output.add(singleLine);
        }
        bfr.close();
        return output;
    }

    public boolean checkUsernameAvailability(String username) throws IOException {
        boolean output = false;
        int i = 0;
        ArrayList<ArrayList<String>> accountDetails = readFile();
        while (!output && i < accountDetails.size()) {
            output = accountDetails.get(i).get(0).equalsIgnoreCase(username);
            i++;
        }
        return output;
    }
    public void getNewAccountDetails() throws IOException {
        boolean accountSet;
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        do {
            accountSet = true;
            System.out.println(setUsernamePrompt);
            System.out.println("All usernames are case insensitive.");
            String username = scan.nextLine();
            System.out.println(setPasswordPrompt);
            String password = scan.nextLine();
            System.out.println(setFirstNamePrompt);
            String firstName = scan.nextLine();
            System.out.println(setLastNamePrompt);
            String lastName = scan.nextLine();
            System.out.println(accountTypePrompt);
            int accountType = scan.nextInt();
            scan.nextLine();

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
            if (accountType != 1 && accountType != 2) {
                accountSet = false;
                System.out.println("Please choose a valid account type.");
            }

            if (!accountSet) {
                System.out.println(tryAgainPrompt);
                int tryAgain;
                do {
                    tryAgain = scan.nextInt();
                    scan.nextLine();
                    if (tryAgain == 1) {
                        loop = true;
                    } else if (tryAgain == 2) {
                        loop = false;
                    }
                } while (tryAgain != 1 && tryAgain != 2);
            }

        } while (loop);
        scan.close();
        if (accountSet) {
            writeToFile();
            System.out.println(accountCreationSuccess);
        }
    }

    public int securityCheck() throws IOException {
        int output = 0;
        boolean loop = false;
        Scanner scan = new Scanner(System.in);

        do {
            System.out.println(usernamePrompt);
            String username = scan.nextLine();
            System.out.println(passwordPrompt);
            String password = scan.nextLine();
            if (username == null || password == null) {
                System.out.println("The details entered were invalid");
            } else {
                ArrayList<ArrayList<String>> accountDetails = readFile();
                int i = 0;
                boolean usernameFound = false;
                boolean passwordMatch = false;
                while (i < accountDetails.size() && !usernameFound) {
                    usernameFound = accountDetails.get(i).get(0).equalsIgnoreCase(username);
                    i++;
                }
                if (usernameFound) {
                    passwordMatch = accountDetails.get(i - 1).get(1).equals(password);
                }
                if (usernameFound && passwordMatch) {
                    if (accountDetails.get(i - 1).get(4).equals("teacher")) {
                        output = 1;
                    } else if (accountDetails.get(i - 1).get(4).equals("student")) {
                        output = 2;
                    }
                }
            }

            if (output == 0) {
                System.out.println(tryAgainPrompt);
                int tryAgain;
                do {
                    tryAgain = scan.nextInt();
                    scan.nextLine();
                    if (tryAgain == 1) {
                        loop = true;
                    } else if (tryAgain == 2) {
                        loop = false;
                    }
                } while (tryAgain != 1 && tryAgain != 2);
            }
        } while (loop);
        scan.close();

        return output;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
}
