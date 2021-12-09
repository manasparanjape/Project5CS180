import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Account.java
 *
 * Bifurcates to teacher and student methods respectively
 *
 * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
 *
 * @version 11/15/2021
 */

public class AccountClient {
    private String username;
    private String firstName;
    private String lastName;

    private boolean ifTeacher;

    private TeacherClient teacherClient;

    private StudentClient studentClient;

    private JFrame jframe;

    private JButton createCourseButton;
    private JButton openCourseButton;
    private JButton logoutButton;

    private PrintWriter printWriter;

    private BufferedReader bufferedReader;

    public AccountClient(String username, String firstname, String lastname, boolean ifTeacher, JFrame jframe, PrintWriter printWriter, BufferedReader bufferedReader) {
        this.username = username;
        this.firstName = firstname;
        this.lastName = lastname;
        this.ifTeacher = ifTeacher;
        this.jframe = jframe;
        this.printWriter = printWriter;
        this.bufferedReader = bufferedReader;
    }

    public void runMethodTeacher() {
        Container container = jframe.getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());
        container.revalidate();

        createCourseButton = new JButton("Create Course");
        createCourseButton.addActionListener(actionListener);
        openCourseButton = new JButton("Open Course");
        openCourseButton.addActionListener(actionListener);
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(actionListener);

        //jframe.setSize(900, 600);
        //jframe.setLocationRelativeTo(null);
        //jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //jframe.setVisible(true);

        JPanel centerPanel = new JPanel();
        centerPanel.add(openCourseButton);
        centerPanel.add(createCourseButton);

        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(logoutButton);

        container.add(centerPanel, BorderLayout.CENTER);
        container.add(leftPanel, BorderLayout.WEST);
    }

    public void runMethodStudent() {
        Container container = jframe.getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());
        container.revalidate();

        openCourseButton = new JButton("Open Course");
        openCourseButton.addActionListener(actionListener);
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(actionListener);

        //jframe.setSize(900, 600);
        //jframe.setLocationRelativeTo(null);
        //jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //jframe.setVisible(true);

        JPanel centerPanel = new JPanel();
        centerPanel.add(openCourseButton);

        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(logoutButton);

        container.add(centerPanel, BorderLayout.CENTER);
        container.add(leftPanel, BorderLayout.WEST);
    }

    public void teacherMainMethod() throws Exception {
        teacherClient = new TeacherClient(username, firstName, lastName, null, jframe, printWriter, bufferedReader);
        runMethodTeacher();
    }

    public void studentMainMethod() throws Exception {
        studentClient = new StudentClient(username, firstName, lastName, null, jframe, printWriter, bufferedReader);
        runMethodStudent();
    }

    public void mainMethod() throws Exception {
        if (ifTeacher) {
            teacherMainMethod();
        } else {
            studentMainMethod();
        }
    }

    public void createCourseButtonMethod() throws Exception {
        printWriter.write("2");
        printWriter.println();
        printWriter.flush();
        teacherClient.createCourse();
    }
    public void openCourseButtonMethod() throws Exception {
        printWriter.write("1");
        printWriter.println();
        printWriter.flush();
        if (ifTeacher) {
            studentClient = new StudentClient(username, firstName, lastName, null, jframe, printWriter, bufferedReader);
            teacherClient.openCourse();
        } else {
            studentClient = new StudentClient(username, firstName, lastName, null, jframe, printWriter, bufferedReader);
            studentClient.openCourse();
        }
    }
    public void logoutButtonMethod() {
        printWriter.write("0");
        printWriter.println();
        printWriter.flush();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    MainClient.mainRunMethod();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == createCourseButton) {
                try {
                    createCourseButtonMethod();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == openCourseButton) {
                try {
                    openCourseButtonMethod();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == logoutButton) {
                logoutButtonMethod();
            }
        }
    };
}
