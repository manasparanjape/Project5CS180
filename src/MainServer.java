import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class MainServer {
    private String accountsFile = "AccountsFile.txt";
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    private boolean ifTeacher;

    private ArrayList<ArrayList<String>> accountDetailsArray = null;

    private BufferedReader bufferedReader;

    private PrintWriter printWriter;
    private PrintWriter dummyWriter;

    public static final Object object = new Object();

    private static ArrayList<String> usernames = new ArrayList<>();

    private static int userNumber = 0;

    public MainServer(BufferedReader bufferedReader, PrintWriter printWriter, PrintWriter dummyWriter) {
        this.bufferedReader = bufferedReader;
        this.printWriter = printWriter;
        this.dummyWriter = dummyWriter;
    }

    public static ArrayList<String> getUsernames() {
        return usernames;
    }

    public String convertToString() {
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

    public void readFile() throws IOException {
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

    public boolean checkUsernameAvailability(String studentUsername) throws IOException {
        boolean output = false;
        int i = 0;
        readFile();
        while (!output && i < accountDetailsArray.size()) {
            output = accountDetailsArray.get(i).get(0).equalsIgnoreCase(studentUsername);
            i++;
        }
        return output;
    }

    public void writeToFile() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(accountsFile, true);
        PrintWriter pw = new PrintWriter(fos);
        synchronized (object) {
            pw.println(convertToString());
        }
        pw.close();
    }

    public void writeAllAccountsToFile() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(accountsFile, false);
        PrintWriter pw = new PrintWriter(fos);
        StringBuilder toWrite = new StringBuilder();
        for (ArrayList<String> strings : accountDetailsArray) {
            toWrite.append(strings.get(0)).append("§§§");
            toWrite.append(strings.get(1)).append("§§§");
            toWrite.append(strings.get(2)).append("§§§");
            toWrite.append(strings.get(3)).append("§§§");
            toWrite.append(strings.get(4)).append("\n");
        }
        toWrite = new StringBuilder(toWrite.substring(0, toWrite.length() - 1));
        synchronized (object) {
            pw.println(toWrite);
        }
        pw.close();
    }

    public boolean securityCheckServer() throws IOException {
        readFile();
        boolean output = false;
        String receivedData = bufferedReader.readLine();
        if (!receivedData.equals("-1")) {
            receivedData = bufferedReader.readLine();
            if (!receivedData.equals("0")) {
                String[] receivedDataArray = receivedData.split("§§§");
                username = receivedDataArray[0];
                password = receivedDataArray[1];

                int i = 0;
                String toSend = "0";

                readFile();
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
                    output = true;
                    toSend = "1" + "§§§";
                    toSend += accountDetailsArray.get(i - 1).get(0) + "§§§";
                    toSend += accountDetailsArray.get(i - 1).get(1) + "§§§";
                    toSend += accountDetailsArray.get(i - 1).get(2) + "§§§";
                    toSend += accountDetailsArray.get(i - 1).get(3) + "§§§";
                    toSend += accountDetailsArray.get(i - 1).get(4);
                    username = accountDetailsArray.get(i - 1).get(0);
                    password = accountDetailsArray.get(i - 1).get(1);
                    firstName = accountDetailsArray.get(i - 1).get(2);
                    lastName = accountDetailsArray.get(i - 1).get(3);
                    ifTeacher = accountDetailsArray.get(i - 1).get(4).equalsIgnoreCase("teacher");
                }

                printWriter.write(toSend);
                printWriter.println();
                printWriter.flush();
            }
        }
        return output;
    }

    public void loginMethod() throws Exception {
        if (securityCheckServer()) {
            if (usernames.contains(username)) {
                printWriter.write("0");
                printWriter.println();
                printWriter.flush();
                mainRunMethod();
            } else {
                printWriter.write("1");
                printWriter.println();
                printWriter.flush();
                usernames.add(username);
                MainClass.forumUpdated.add(true);
                AccountServer accountServer = new AccountServer(username, firstName, lastName, ifTeacher, printWriter, bufferedReader, userNumber, dummyWriter);
                userNumber++;
                accountServer.mainMethod();
            }
        } else {
            mainRunMethod();
        }
    }

    public void createAccountMethod() throws Exception {
        String receivedData = bufferedReader.readLine();
        char charAt0 = receivedData.charAt(0);
        receivedData = receivedData.substring(1);
        if (charAt0 == '1') {
            String[] receivedDataArray = receivedData.split("§§§");
            username = receivedDataArray[0];
            password = receivedDataArray[1];
            firstName = receivedDataArray[2];
            lastName = receivedDataArray[3];
            ifTeacher = receivedDataArray[4].equals("teacher");
            if (checkUsernameAvailability(username)) {
                printWriter.write("0");
            } else {
                writeToFile();
                printWriter.write("1");
            }
            printWriter.println();
            printWriter.flush();
        }
        mainRunMethod();
    }

    public void deleteAccountMethod() throws Exception {
        if (securityCheckServer()) {
            int i = 0;
            readFile();
            boolean usernameFound = false;
            while (i < accountDetailsArray.size() && !usernameFound) {
                usernameFound = accountDetailsArray.get(i).get(0).equalsIgnoreCase(username);
                i++;
            }
            accountDetailsArray.remove(i - 1);
            writeAllAccountsToFile();
        }
        mainRunMethod();
    }

    public void changePasswordMethod() throws Exception {
        if (securityCheckServer()) {
            password = bufferedReader.readLine();
            if (!password.equals(" ")) {
                int i = 0;
                boolean usernameFound = false;
                while (i < accountDetailsArray.size() && !usernameFound) {
                    usernameFound = accountDetailsArray.get(i).get(0).equalsIgnoreCase(username);
                    i++;
                }
                accountDetailsArray.get(i - 1).set(1, password);
                writeAllAccountsToFile();
            }
        }
        mainRunMethod();
    }

    public void mainRunMethod() throws Exception {
        readFile();
        String choice = bufferedReader.readLine();

        switch(choice) {
            case "1" -> loginMethod();
            case "2" -> createAccountMethod();
            case "3" -> deleteAccountMethod();
            case "4" -> changePasswordMethod();
        }
    }
}