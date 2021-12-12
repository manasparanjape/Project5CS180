import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.ArrayList;

public class StudentClient {
    private String username;
    private String firstName;
    private String lastName;
    private static String courseSelectionPrompt = "Which course would you like to open?";

    private CourseClient courseClient;

    private JFrame jframe;

    private JButton viewPointsButton;
    private JButton openDiscussionForumsButton;
    private JButton backButton;

    private PrintWriter printWriter;

    private BufferedReader bufferedReader;
    private BufferedReader dummyReader;

    public StudentClient(String username, String firstName, String lastName, CourseClient courseClient, JFrame jframe, PrintWriter printWriter, BufferedReader bufferedReader, BufferedReader dummyReader) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courseClient = courseClient;
        this.jframe = jframe;
        this.printWriter = printWriter;
        this.bufferedReader = bufferedReader;
        this.dummyReader = dummyReader;
    }

    public void openCourse() throws Exception {
        String receivedData = null;
        try {
            receivedData = bufferedReader.readLine();
        } catch (SocketException e) {
            String errorMessage = "The server unexpectedly closed. Please try again later";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        if (receivedData.isBlank()) {
            printWriter.write(" ");
            printWriter.println();
            printWriter.flush();
            String errorMessage = "No course has been created yet.";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            AccountClient accountClient = new AccountClient(username, firstName, lastName, false, jframe, printWriter, bufferedReader, dummyReader);
            accountClient.mainMethod();
        } else {
            Object[] options = receivedData.split("§§§");
            Object selectedObject = JOptionPane.showInputDialog(null, courseSelectionPrompt, "Delete Forum", JOptionPane.PLAIN_MESSAGE, null, options, JOptionPane.CLOSED_OPTION);
            if (selectedObject != null) {
                String selectedCourse = selectedObject.toString();
                printWriter.write(selectedCourse);
                printWriter.println();
                printWriter.flush();
                courseClient = new CourseClient(selectedCourse, username, firstName, lastName, null, jframe, printWriter, bufferedReader, dummyReader);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            runMethodStudent();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                printWriter.write(" ");
                printWriter.println();
                printWriter.flush();
                AccountClient accountClient = new AccountClient(username, firstName, lastName, false, jframe, printWriter, bufferedReader, dummyReader);
                accountClient.mainMethod();
            }
        }
    }

    public void viewPointsButtonMethod() throws IOException {
        printWriter.write("5");
        printWriter.println();
        printWriter.flush();
        courseClient.viewPoints();
    }
    public void openDiscussionForumsButtonMethod() throws Exception {
        printWriter.write("3");
        printWriter.println();
        printWriter.flush();
        courseClient.studentDiscussionForumOpened();
    }
    public void backMethod() throws Exception {
        printWriter.write("-2");
        printWriter.println();
        printWriter.flush();
        AccountClient accountClient = new AccountClient(username, firstName, lastName, false, jframe, printWriter, bufferedReader, dummyReader);
        accountClient.mainMethod();
    }

    public void runMethodStudent() {
        Container container = jframe.getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());
        container.revalidate();

        openDiscussionForumsButton = new JButton("Open discussion forums");
        openDiscussionForumsButton.addActionListener(actionListener);
        viewPointsButton = new JButton("View Points");
        viewPointsButton.addActionListener(actionListener);
        backButton = new JButton("Back");
        backButton.addActionListener(actionListener);

        //jframe.setSize(900, 600);
        //jframe.setLocationRelativeTo(null);
        //jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //jframe.setVisible(true);

        JPanel centerPanel = new JPanel();
        centerPanel.add(openDiscussionForumsButton);
        centerPanel.add(viewPointsButton);

        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(backButton);

        container.add(centerPanel, BorderLayout.CENTER);
        container.add(leftPanel, BorderLayout.WEST);
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == viewPointsButton) {
                try {
                    viewPointsButtonMethod();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == openDiscussionForumsButton) {
                try {
                    openDiscussionForumsButtonMethod();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == backButton) {
                try {
                    backMethod();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    };

}
