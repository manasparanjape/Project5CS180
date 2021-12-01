import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Main.java
 * <p>
 * Contains the main method and runs the program
 *
 * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
 * @version 11/15/2021
 */

public class Main {
    private static String accountsFile = "AccountsFile.txt";
    private static String username;
    private static String password;
    private static String firstName;
    private static String lastName;
    private static boolean ifTeacher;
    private static String welcomeMessage = "Welcome! ";
    private static String initialPrompt = "What would you like to do today?\n1) Login to my account\n2)"
            + " Create new account\n3) Change my password\n4) Delete my account\n5) Exit";
    private static String usernamePrompt = "Please enter your username.";
    private static String passwordPrompt = "Please enter your password";
    private static String setUsernamePrompt = "Set a new username:";
    private static String setPasswordPrompt = "Set a new password:";
    private static String setFirstNamePrompt = "Enter your first name:";
    private static String setLastNamePrompt = "Enter your last name:";
    private static String usernameUnavailablePrompt = "The username you entered is unavailable.";
    private static String accountCreationSuccess = "Your account was successfully created";
    private static String accountTypePrompt = "Are you a teacher or a student?\n1. Teacher\n2. Student";
    private static String accountDeletionSuccess = "Your account has been deleted";
    static ArrayList<ArrayList<String>> accountDetailsArray = null;

    public Main(String username, String password, String firstName, String lastName, boolean ifTeacher) {
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

    public static void changePassword(Scanner scan) throws IOException {
        readFile();
        boolean accountVerification = (securityCheck() != 0);
        if (accountVerification) {
            System.out.println("What do you want to set your new password to?");
            String newPassword = scan.nextLine();
            if (newPassword == null) {
                System.out.println("The password you entered is invalid.");
            } else if (newPassword.equals(password)) {
                System.out.println("The new password you entered is the same as your old one.");
            } else {
                String output = "";
                for (int i = 0; i < accountDetailsArray.size(); i++) {
                    if (accountDetailsArray.get(i).get(0).equals(username)) {
                        accountDetailsArray.get(i).set(1, newPassword);
                    }
                    output += accountDetailsArray.get(i).get(0) + "§§§";
                    output += accountDetailsArray.get(i).get(1) + "§§§";
                    output += accountDetailsArray.get(i).get(2) + "§§§";
                    output += accountDetailsArray.get(i).get(3) + "§§§";
                    if (accountDetailsArray.get(i).get(4).equals("teacher")) {
                        output += "teacher";
                    } else if (accountDetailsArray.get(i).get(4).equals("student")) {
                        output += "student";
                    }
                    output += "\n";
                }
                output = output.substring(0, output.length() - 1);
                FileOutputStream fos = new FileOutputStream(accountsFile, false);
                PrintWriter pw = new PrintWriter(fos);
                pw.println(output);
                pw.close();
                System.out.println("Your password has been changed!");
            }
        }
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
    public static boolean checkUsernameAvailability(String studentUsername) throws IOException {
        boolean output = false;
        int i = 0;
        readFile();
        while (!output && i < accountDetailsArray.size()) {
            output = accountDetailsArray.get(i).get(0).equalsIgnoreCase(studentUsername);
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
        } else if (checkUsernameAvailability(username)) {
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
    public static void findAccount(String studentUsername) throws IOException {
        readFile();
        for (ArrayList<String> strings : accountDetailsArray) {
            if (strings.get(0).equals(studentUsername)) {
                firstName = strings.get(2);
                lastName = strings.get(3);
                ifTeacher = strings.get(4).equals("teacher");
            }
        }
    }

    //checks whether or not username and password match with what is stored in Accounts.txt file
    public static int securityCheck() throws IOException {
        int output = 0;
        //System.out.println(usernamePrompt);
        //username = scan.nextLine();
        username = JOptionPane.showInputDialog(null, usernamePrompt, "Account verification", JOptionPane.QUESTION_MESSAGE);
        //System.out.println(passwordPrompt);
        //password = scan.nextLine();
        password = JOptionPane.showInputDialog(null, passwordPrompt, "Account verification", JOptionPane.QUESTION_MESSAGE);
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
        boolean accountVerification = (securityCheck() != 0);
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
        /*Scanner scan = new Scanner(System.in);
        int option = 0;
        System.out.println(welcomeMessage);
        while (option != 5) {
            System.out.println("Check 1");
            try {
                //System.out.println(initialPrompt);
                //option = scan.nextInt();
                //scan.nextLine();
                //todo: make a new jframe object
                Object[] options = {"Login to my account", "Create new account", "Change my password", "Delete my account", "Exit"};
                option = JOptionPane.showOptionDialog(null, "Select an option", "Option choosing", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]) + 1;
                if (option < 1 || option > 5) {
                    System.out.println("You entered an invalid option. Please enter a number between 1 and 5.");
                } else {
                    switch (option) {
                        case 1 -> {
                            int accountCheck = securityCheck(scan);
                            Account account;
                            if (accountCheck == 1) {
                                findAccount(username);
                                account = new Account(username, firstName, lastName, true, scan);
                                account.accountMainMethod();
                                System.out.println("Check 2");
                            } else if (accountCheck == 2) {
                                findAccount(username);
                                account = new Account(username, firstName, lastName, false, scan);
                                account.accountMainMethod();
                            }
                        }
                        case 2 -> getNewAccountDetails(scan);
                        case 3 -> changePassword(scan);
                        case 4 -> deleteAccount(scan);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("You did not input an integer. Please input an integer between 1 and 4.");
                option = 0;
                scan.nextLine();
            }
        }
        scan.close();*/
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    mainRunMethod();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    static JFrame jframe = new JFrame();
    static JButton loginButton;
    static JButton createNewAccountButton;
    static JButton deleteAccountButton;
    static JButton changePasswordButton;

    public static void loginButtonMethod() throws Exception {
        Scanner scan = new Scanner(System.in);
        int accountCheck = securityCheck();
        Account account;
        if (accountCheck == 1) {
            findAccount(username);
            account = new Account(username, firstName, lastName, true, scan, jframe);
            account.accountMainMethod();
            System.out.println("Check 2");
        } else if (accountCheck == 2) {
            findAccount(username);
            account = new Account(username, firstName, lastName, false, scan, jframe);
            account.accountMainMethod();
        } else {
            String errorMessage = "The account details you entered were invalid!";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void createNewAccountButtonMethod() throws IOException {
        Scanner scan = new Scanner(System.in);
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
        } else if (checkUsernameAvailability(username)) {
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
    public static void deleteAccountButtonMethod() {

    }
    public static void changePasswordButtonMethod() {

    }

    static ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                try {
                    loginButtonMethod();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == createNewAccountButton) {
                try {
                    createNewAccountButtonMethod();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == deleteAccountButton) {
                deleteAccountButtonMethod();
            }
            if (e.getSource() == changePasswordButton) {
                changePasswordButtonMethod();
            }
        }
    };

    public static void mainRunMethod() {
        Container container = jframe.getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());
        container.revalidate();

        loginButton = new JButton("Login");
        loginButton.addActionListener(actionListener);
        createNewAccountButton = new JButton("Create new account");
        createNewAccountButton.addActionListener(actionListener);
        deleteAccountButton = new JButton("Delete account");
        deleteAccountButton.addActionListener(actionListener);
        changePasswordButton = new JButton("Change Password");
        changePasswordButton.addActionListener(actionListener);

        jframe.setSize(900, 600);
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);

        JPanel centerPanel = new JPanel();
        centerPanel.add(loginButton);
        centerPanel.add(createNewAccountButton);
        centerPanel.add(deleteAccountButton);
        centerPanel.add(changePasswordButton);

        container.add(centerPanel);
    }
}
