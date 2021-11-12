import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Accounts {
    private static String accountsFile = "AccountsFile.txt";
    private static String username;
    private static String password;
    private static String firstName;
    private static String lastName;
    private static boolean ifTeacher;
    private static Main main;
    private final static String welcomeMessage = "Welcome! ";
    private final static String initialPrompt = """
            What would you like to do today?
            1) Login to my account
            2) Create new account
            3) Exit""";
    private final static String tryAgainPrompt = "Error! Do you want to try again?\n" + "1. Yes\n" + "2. No";
    private final static String usernamePrompt = "Please enter your username.";
    private final static String passwordPrompt = "Please enter your password";
    private final static String setUsernamePrompt = "Set a new username:";
    private final static String setPasswordPrompt = "Set a new password:";
    private static final String setFirstNamePrompt = "Enter your first name:";
    private static final String setLastNamePrompt = "Enter your last name:";
    private static final String usernameUnavailablePrompt = "The username you entered is unavailable.";
    private static final  String accountCreationSuccess = "Your account was successfully created";
    private final static String accountTypePrompt = """
            Are you a teacher or a student?
            1. Teacher
            2. Student""";
    static ArrayList<ArrayList<String>> accountDetailsArray = null;

    public Accounts(String username, String password, String firstName, String lastName, boolean ifTeacher) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ifTeacher = ifTeacher;
    }

    public static String convertToString() {
        String output = username + "---";
        output += password + "---";
        output += firstName + "---";
        output += lastName + "---";
        if (ifTeacher) {
            output += "teacher";
        } else {
            output += "student";
        }
        return output;
    }

    public static void writeToFile() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(accountsFile, true);
        PrintWriter pw = new PrintWriter(fos);
        pw.println(convertToString());
        pw.close();
    }

    public static void readFile() throws IOException {
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        File f = new File(accountsFile);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();
        while (line != null) {
            String[] separatedLine = line.split("---");
            ArrayList<String> singleLine = new ArrayList<>(Arrays.asList(separatedLine));
            output.add(singleLine);
            line = bfr.readLine();
        }
        bfr.close();
        accountDetailsArray = output;
    }

    public static boolean checkUsernameAvailability(String username) throws IOException {
        boolean output = false;
        int i = 0;
        readFile();
        System.out.println(accountDetailsArray.size());
        while (!output && i < accountDetailsArray.size()) {
            output = accountDetailsArray.get(i).get(0).equalsIgnoreCase(username);
            i++;
        }
        return output;
    }

    public static void getNewAccountDetails() throws IOException {
        boolean accountSet;
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        do {
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
            //scan.nextLine();

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
            }

            if (!accountSet) {
                System.out.println(tryAgainPrompt);
                String tryAgain;
                do {
                    tryAgain = scan.nextLine();
                    scan.nextLine();
                    if (tryAgain.equals("1")) {
                        loop = true;
                    } else if (tryAgain.equals("2")) {
                        loop = false;
                    }
                } while (!tryAgain.equals("1") && !tryAgain.equals("2"));
            }

        } while (loop);
        scan.close();
        if (accountSet) {
            username = username.toLowerCase();
            writeToFile();
            System.out.println(accountCreationSuccess);
        }
    }

    public static void findAccount(String username) throws IOException {
        readFile();
        for (int i = 0; i < accountDetailsArray.size(); i++) {
            if (accountDetailsArray.get(i).get(0).equals(username)) {
                firstName = accountDetailsArray.get(i).get(2);
                lastName = accountDetailsArray.get(i).get(3);
                ifTeacher = accountDetailsArray.get(i).get(4).equals("teacher");
            }
        }
    }

    public static int securityCheck() throws IOException {
        int output = 0;
        boolean loop = false;
        Scanner scan = new Scanner(System.in);

        do {
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
                }
            }

            if (output == 0) {
                System.out.println(tryAgainPrompt);
                int tryAgain;
                do {
                    loop = false;
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
        //scan.close();
        return output;
    }

    /*public static void deleteAccount() throws IOException {
        boolean accountVerification = (securityCheck() != 0);
        if (accountVerification) {
            for (int i = -0; i < accountDetailsArray.size(); i++) {
                if ()
            }
        }
    }*/

    public String getName() {
        return firstName + " " + lastName;
    }

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;
        int option = 0;
        System.out.println(welcomeMessage);
        while (option != 3) {
            System.out.println(initialPrompt);
            option = scan.nextInt();
            scan.nextLine();
            if (option < 1 || option > 3) {
                System.out.println("You entered an invalid option. Please enter a number between 1 and 3.");
            } else {
                switch(option) {
                    case 1 -> {
                        int accountCheck = securityCheck();
                        if (accountCheck == 1) {
                            findAccount(username);
                            main = new Main(username, firstName, lastName, true);
                            main.accountMainMethod();
                        } else if (accountCheck == 2) {
                            findAccount(username);
                            main = new Main(username, firstName, lastName, false);
                            main.accountMainMethod();
                        }
                    }
                    case 2 -> {
                        System.out.println("Check1");
                        getNewAccountDetails();
                    }
                }
            }
        }
        scan.close();
    }
}
