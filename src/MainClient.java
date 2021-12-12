import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

public class MainClient {
    private static String usernamePrompt = "Please enter your username.";
    private static String passwordPrompt = "Please enter your password";
    private static String usernameUnavailablePrompt = "The username you entered is unavailable.";
    private static String accountCreationSuccess = "Your account was successfully created";
    private static String accountDeletionSuccess = "Your account has been deleted";
    private static String username;
    private static String password;
    private static String firstName;
    private static String lastName;

    private static boolean ifTeacher;

    private static JFrame jframe = new JFrame();

    private static JButton loginButton;
    private static JButton createNewAccountButton;
    private static JButton deleteAccountButton;
    private static JButton changePasswordButton;
    private static JButton createAccountButton;
    private static JButton backButton;

    private static JTextField usernameField;
    private static JTextField passwordField;
    private static JTextField firstNameField;
    private static JTextField lastNameField;
    private static JTextField ifTeacherField;

    private static JLabel usernameLabel = new JLabel("Username");
    private static JLabel passwordLabel = new JLabel("Password");
    private static JLabel firstNameLabel = new JLabel("First Name");
    private static JLabel lastNameLabel = new JLabel("Last Name");
    private static JLabel ifTeacherLabel = new JLabel("Are you a teacher or student?");

    private static BufferedReader bufferedReader;
    private static BufferedReader dummyReader;

    private static PrintWriter printWriter;

    public static int securityCheckClient() throws IOException {
        int output = 0;
        username = JOptionPane.showInputDialog(null, usernamePrompt, "Account verification", JOptionPane.QUESTION_MESSAGE);
        if (username == null) {
            printWriter.write("-1");
            printWriter.println();
            printWriter.flush();
        } else {
            printWriter.write("0");
            printWriter.println();
            printWriter.flush();
            password = JOptionPane.showInputDialog(null, passwordPrompt, "Account verification", JOptionPane.QUESTION_MESSAGE);
            if (password == null) {
                printWriter.write("0");
                printWriter.println();
                printWriter.flush();
            } else {
                printWriter.write(username + "§§§" + password);
                printWriter.println();
                printWriter.flush();

                String accountVerificationString = null;
                try {
                    accountVerificationString = bufferedReader.readLine();
                } catch (SocketException e) {
                    String errorMessage = "The server unexpectedly closed. Please try again later";
                    System.exit(0);
                }
                String[] accountVerificationArray = accountVerificationString.split("§§§");
                output = Integer.parseInt(accountVerificationArray[0]);

                if (output == 0) {
                    String errorMessage = "The account details entered were invalid";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    username = accountVerificationArray[1];
                    password = accountVerificationArray[2];
                    firstName = accountVerificationArray[3];
                    lastName = accountVerificationArray[4];
                    ifTeacher = accountVerificationArray[5].equals("teacher");
                    if (ifTeacher) {
                        output = 1;
                    } else {
                        output = 2;
                    }
                }
            }
        }
        return output;
    }
    public static void loginButtonMethod() throws Exception {
        printWriter.write("1");
        printWriter.println();
        printWriter.flush();
        int accountCheck = securityCheckClient();
        if (accountCheck != 0) {
            String accountType = null;
            try {
                accountType = bufferedReader.readLine();
            } catch (SocketException e) {
                String errorMessage = "The server unexpectedly closed. Please try again later";
                System.exit(0);
            }
            if (accountType.equals("1")) {
                AccountClient accountClient = new AccountClient(username, firstName, lastName, ifTeacher, jframe, printWriter, bufferedReader, dummyReader);
                accountClient.mainMethod();
            } else {
                String errorMessage = "This account has logged in on another device. Please log out from that device to log in here.";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public static void createNewAccountButtonMethod() throws IOException {
        Container container = jframe.getContentPane();
        container.removeAll();
        container.setLayout(new GridLayout(2,1));
        container.revalidate();

        createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(actionListener);
        backButton = new JButton("Back");
        backButton.addActionListener(actionListener);
        usernameField = new JTextField(20);
        usernameLabel.setForeground(Color.decode("#CEB888"));
        usernameLabel.setBorder(new LineBorder(Color.decode("#CEB888")));
        usernameField.setMaximumSize(usernameField.getPreferredSize());
        passwordField = new JTextField(20);
        passwordLabel.setForeground(Color.decode("#CEB888"));
        passwordLabel.setBorder(new LineBorder(Color.decode("#CEB888")));
        passwordField.setMaximumSize(passwordField.getPreferredSize());
        firstNameField = new JTextField(20);
        firstNameLabel.setForeground(Color.decode("#CEB888"));
        firstNameLabel.setBorder(new LineBorder(Color.decode("#CEB888")));
        firstNameField.setMaximumSize(firstNameField.getPreferredSize());
        lastNameField = new JTextField(20);
        lastNameLabel.setForeground(Color.decode("#CEB888"));
        lastNameLabel.setBorder(new LineBorder(Color.decode("#CEB888")));
        lastNameField.setMaximumSize(lastNameField.getPreferredSize());
        ifTeacherField = new JTextField(20);
        ifTeacherLabel.setForeground(Color.decode("#CEB888"));
        ifTeacherLabel.setBorder(new LineBorder(Color.decode("#CEB888")));
        ifTeacherField.setMaximumSize(ifTeacherField.getPreferredSize());

        jframe.setSize(900, 600);
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.decode("#CEB888"));
        JPanel centerPanel = new JPanel(new GridLayout(6, 2));
        centerPanel.setBackground(Color.decode("#000000"));
        //centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(usernameLabel);
        centerPanel.add(usernameField);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordField);
        centerPanel.add(firstNameLabel);
        centerPanel.add(firstNameField);
        centerPanel.add(lastNameLabel);
        centerPanel.add(lastNameField);
        centerPanel.add(ifTeacherLabel);
        centerPanel.add(ifTeacherField);
        centerPanel.add(createAccountButton);
        centerPanel.add(backButton);

        JLabel titleText = new JLabel("Learning Management System Discussion Board");
        titleText.setFont(new Font("Calibri", Font.BOLD, 28));
        topPanel.add(titleText);

        topPanel.setBorder(new EmptyBorder(80, 20, 20, 20));
        centerPanel.setBorder(new EmptyBorder(20, 20, 80, 20));

        container.add(topPanel);
        container.add(centerPanel);
    }
    public static void createAccountButtonMethod() throws IOException {
        printWriter.write("2");
        printWriter.println();
        printWriter.flush();
        boolean accountSet = true;
        String errorMessage = "";
        username = usernameField.getText();
        password = passwordField.getText();
        firstName = firstNameField.getText();
        lastName = lastNameField.getText();
        if (ifTeacherField.getText().equalsIgnoreCase("teacher")) {
            ifTeacher = true;
        } else if (ifTeacherField.getText().equalsIgnoreCase("student")) {
            ifTeacher = false;
        } else {
            accountSet = false;
            errorMessage += "Please enter either 'student' or 'teacher' to create an account";
        }
        if (username == null || username.isBlank()) {
            accountSet = false;
            errorMessage += "The username you entered is invalid.";
        }
        if (password == null || password.isBlank()) {
            accountSet = false;
            errorMessage += "The password you entered is invalid.";
        }
        if (firstName == null || firstName.isBlank()) {
            accountSet = false;
            errorMessage += "The first name you entered is invalid.";
        }
        if (lastName == null || lastName.isBlank()) {
            accountSet = false;
            errorMessage += "The last name you entered is invalid.";
        }
        if (accountSet) {
            username = username.toLowerCase();
            printWriter.write("1" + username + "§§§" + password + "§§§" + firstName + "§§§" + lastName + "§§§" + ifTeacherField.getText().toLowerCase());
            printWriter.println();
            printWriter.flush();

            String accountVerificationString = null;
            try {
                accountVerificationString = bufferedReader.readLine();
            } catch (SocketException e) {
                errorMessage = "The server unexpectedly closed. Please try again later";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            if (accountVerificationString.equals("0")) {
                errorMessage = usernameUnavailablePrompt;
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, accountCreationSuccess, "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            mainRunMethod();
        } else {
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            printWriter.write("0");
            printWriter.println();
            printWriter.flush();
        }
        usernameField.setText("");
        passwordField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        ifTeacherField.setText("");
    }
    public static void deleteAccountButtonMethod() throws IOException {
        printWriter.write("3");
        printWriter.println();
        printWriter.flush();
        boolean accountVerification = (securityCheckClient() != 0);
        if (accountVerification) {
            JOptionPane.showMessageDialog(null, accountDeletionSuccess, "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public static void changePasswordButtonMethod() throws IOException {
        printWriter.write("4");
        printWriter.println();
        printWriter.flush();
        if (securityCheckClient() != 0) {
            String newPassword = JOptionPane.showInputDialog(null, "What do you want to set your new password to?", "Account verification", JOptionPane.QUESTION_MESSAGE);
            if (newPassword == null) {
                printWriter.write(" ");
                printWriter.println();
                printWriter.flush();
            } else if (newPassword.isBlank()) {
                printWriter.write(" ");
                printWriter.println();
                printWriter.flush();
                String errorMessage = "The password you entered is invalid.";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } else if (newPassword.equals(password)) {
                printWriter.write(" ");
                printWriter.println();
                printWriter.flush();
                String errorMessage = "The new password you entered is the same as your old one.";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                printWriter.write(newPassword);
                printWriter.println();
                printWriter.flush();
                JOptionPane.showMessageDialog(null, "Your password has been changed!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
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
                try {
                    deleteAccountButtonMethod();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == changePasswordButton) {
                try {
                    changePasswordButtonMethod();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == createAccountButton) {
                try {
                    createAccountButtonMethod();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == backButton) {
                try {
                    mainRunMethod();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };

    public static void mainRunMethod() throws IOException {
        Container container = jframe.getContentPane();
        container.removeAll();
        container.setLayout(new GridLayout(2,1));
        container.revalidate();

        loginButton = new JButton("Login");
        loginButton.addActionListener(actionListener);
        loginButton.setBackground(Color.decode("#c0c0c0"));
        createNewAccountButton = new JButton("Create new account");
        createNewAccountButton.addActionListener(actionListener);
        createNewAccountButton.setBackground(Color.decode("#c0c0c0"));
        deleteAccountButton = new JButton("Delete account");
        deleteAccountButton.addActionListener(actionListener);
        deleteAccountButton.setBackground(Color.decode("#c0c0c0"));
        changePasswordButton = new JButton("Change Password");
        changePasswordButton.addActionListener(actionListener);
        changePasswordButton.setBackground(Color.decode("#c0c0c0"));

        jframe.setSize(600, 400);
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jframe.setVisible(true);
        jframe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printWriter.write("Close");
                printWriter.println();
                printWriter.flush();
                System.exit(0);
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.decode("#CEB888"));
        JPanel centerPanel = new JPanel(new GridLayout(2,2,30,30));
        centerPanel.setBackground(Color.decode("#000000"));

        JLabel titleText = new JLabel("Learning Management System\n Discussion Board");
        titleText.setFont(new Font("Calibri", Font.BOLD, 28));
        topPanel.add(titleText);

        centerPanel.add(loginButton);
        centerPanel.add(createNewAccountButton);
        centerPanel.add(deleteAccountButton);
        centerPanel.add(changePasswordButton);

        topPanel.setBorder(new EmptyBorder(80, 20, 20, 20));
        centerPanel.setBorder(new EmptyBorder(20, 20, 80, 20));

        container.add(topPanel);
        container.add(centerPanel);
    }

    public static void main(String[] args) throws IOException {
        try {
            Socket socket = new Socket("localhost", 2002);
            Socket dummySocket = new Socket("localhost", 2001);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dummyReader = new BufferedReader(new InputStreamReader(dummySocket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (ConnectException e) {
            String errorMessage = "The server is not running at this time, please try again later!";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }



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