import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class MainServer {
    private static String accountsFile = "AccountsFile.txt";
    private static String username;
    private static String password;
    private static String firstName;
    private static String lastName;

    private static boolean ifTeacher;

    private static ArrayList<ArrayList<String>> accountDetailsArray = null;

    private static BufferedReader bufferedReader;

    private static PrintWriter printWriter;

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

    public static void writeToFile() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(accountsFile, true);
        PrintWriter pw = new PrintWriter(fos);
        pw.println(convertToString());
        pw.close();
    }

    public static void writeAllAccountsToFile() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(accountsFile, false);
        PrintWriter pw = new PrintWriter(fos);
        String toWrite = "";
        for (int i = 0; i < accountDetailsArray.size(); i++) {
            toWrite += accountDetailsArray.get(i).get(0) + "§§§";
            toWrite += accountDetailsArray.get(i).get(1) + "§§§";
            toWrite += accountDetailsArray.get(i).get(2) + "§§§";
            toWrite += accountDetailsArray.get(i).get(3) + "§§§";
            toWrite += accountDetailsArray.get(i).get(4) + "\n";
        }
        toWrite = toWrite.substring(0, toWrite.length() - 1);
        pw.println(toWrite);
        pw.close();
    }

    public static boolean securityCheckServer() throws IOException {
        readFile();
        boolean output = false;
        String receivedData = bufferedReader.readLine();
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
        return output;
    }

    public static void loginMethod() throws Exception {
        System.out.println(accountDetailsArray);
        if (securityCheckServer()) {
            AccountServer accountServer = new AccountServer(username, firstName, lastName, ifTeacher, printWriter, bufferedReader);
            accountServer.mainMethod();
        } else {
            mainRunMethod();
        }
    }

    public static void createAccountMethod() throws Exception {
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

    public static void deleteAccountMethod() throws Exception {
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

    public static void changePasswordMethod() throws Exception {
        if (securityCheckServer()) {
            password = bufferedReader.readLine();
            int i = 0;
            boolean usernameFound = false;
            while (i < accountDetailsArray.size() && !usernameFound) {
                usernameFound = accountDetailsArray.get(i).get(0).equalsIgnoreCase(username);
                i++;
            }
            accountDetailsArray.get(i - 1).set(1, password);
            writeAllAccountsToFile();
        }
        mainRunMethod();
    }

    public static void mainRunMethod() throws Exception {
        readFile();
        String choice = bufferedReader.readLine();

        switch(choice) {
            case "1" -> loginMethod();
            case "2" -> createAccountMethod();
            case "3" -> deleteAccountMethod();
            case "4" -> changePasswordMethod();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1000);
        while (true) {
            Socket socket = serverSocket.accept();
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream());

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
    }
}