import javax.swing.*;
import java.io.*;

public class DiscussionForumClient {
    private static String topicChangePrompt = "What do you want to change the topic to?";
    private static String studentSpecificMessagesPrompt = "Please enter username of the student who's posts you want"
            + "to view.";
    private static String gradingStudentPrompt = "Please enter the number of point you wish to assign this"
            + " student.";

    private PrintWriter printWriter;

    private BufferedReader bufferedReader;
    private BufferedReader dummyReader;

    private JTextArea textArea;

    private JButton discussionForumButton;

    public DiscussionForumClient(PrintWriter printWriter, BufferedReader bufferedReader, JTextArea textArea, BufferedReader dummyReader, JButton discussionForumButton) {
        this.printWriter = printWriter;
        this.bufferedReader = bufferedReader;
        this.textArea = textArea;
        this.dummyReader = dummyReader;
        this.discussionForumButton = discussionForumButton;
    }

    public void send(String newPost, String messageNumberString) throws Exception {
        boolean errorCheck = false;
        boolean reply = false;
        int messageNumber = 0;

        int messagesArraySize = Integer.parseInt(bufferedReader.readLine());
        try {
            if (messageNumberString == null || messageNumberString.isBlank()) {
                reply = false;
            } else {
                messageNumber = Integer.parseInt(messageNumberString);
                if (messageNumber < 0 || messageNumber > messagesArraySize) {
                    String errorMessage = "You did not input a valid integer. Please input 0 or leave blank if you want to post a message. Please input an integer between 1 and "
                            + messagesArraySize + " if you want to reply to a message.";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    errorCheck = true;
                    printWriter.write(" ");
                    printWriter.println();
                    printWriter.flush();
                } else {
                    reply = messageNumber != 0;
                }
            }
        } catch (NumberFormatException e) {
            String errorMessage = "You did not input an integer. Please input 0 or leave blank if you want to post a message. Please input an integer between 1 and "
                    + messagesArraySize + " if you want to reply to a message.";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            errorCheck = true;
            printWriter.write(" ");
            printWriter.println();
            printWriter.flush();
        }
        if (newPost == null || newPost.isBlank()) {
            String errorMessage = "Please enter a valid post(ie. Not all spaces or blank).";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            printWriter.write(" ");
            printWriter.println();
            printWriter.flush();
        } else if (!errorCheck){
            if (reply) {
                printWriter.write(newPost + "§§§" + messageNumber);
            } else {
                printWriter.write(newPost + "§§§0");
            }
            printWriter.println();
            printWriter.flush();
        }

        printMessages();
    }

    public void upvote(String upvoteMessageNumber) throws Exception {
        int messageNumber = 0;
        try {
            messageNumber = Integer.parseInt(upvoteMessageNumber);
            printWriter.write(upvoteMessageNumber);
            printWriter.println();
            printWriter.flush();

            String receivedData = bufferedReader.readLine();
            if (receivedData.equals("1")) {
                String errorMessage = "You entered an invalid option.";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } else if (receivedData.equals("2")) {
                String errorMessage = "You have already upvoted this message. You cannot upvote the same message twice.";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } else if (receivedData.equals("3")) {
                printMessages();
            }
        } catch (NumberFormatException | IOException e) {
            String errorMessage = "You did not input an integer. Please input an integer.";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            printWriter.write("0");
            printWriter.println();
            printWriter.flush();
        }
    }

    public void deleteMessage(String messageNumberString) throws Exception {
        int messageNumber = 0;
        boolean numberValid = true;
        try {
            messageNumber = Integer.parseInt(messageNumberString);

            printWriter.write(messageNumberString);
            printWriter.println();
            printWriter.flush();
        } catch (NumberFormatException e) {
            String errorMessage = "You did not input an integer.";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            numberValid = false;
        }
        if (numberValid) {
            String receivedData = bufferedReader.readLine();
            if (receivedData.equals("0")) {
                numberValid = false;
                String errorMessage = "You entered an invalid option.";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (numberValid) {
            printMessages();
        }
    }

    public void gradeStudent() throws Exception {
        String studentUsername = JOptionPane.showInputDialog(textArea, studentSpecificMessagesPrompt, "Student Grading", JOptionPane.QUESTION_MESSAGE);

        if (studentUsername == null || studentUsername.isBlank()) {
            printWriter.write(" ");
            printWriter.println();
            printWriter.flush();
        } else {
            printWriter.write(studentUsername);
            printWriter.println();
            printWriter.flush();

            String receivedData = bufferedReader.readLine();

            if (receivedData.equals("0")) {
                String errorMessage = "The student username you entered does not exist!";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                printSpecificStudentMessages(studentUsername);
                String pointsString = JOptionPane.showInputDialog(null, gradingStudentPrompt, "Student Grading", JOptionPane.QUESTION_MESSAGE);

                if (pointsString == null || pointsString.isBlank()) {
                    printWriter.write(" ");
                    printWriter.println();
                    printWriter.flush();
                } else {
                    printWriter.write(pointsString);
                    printWriter.println();
                    printWriter.flush();

                    receivedData = bufferedReader.readLine();
                    if (receivedData.equals("3")) {
                        String errorMessage = "You did not enter an integer. Please enter an integer in order to assign points.";
                        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (receivedData.equals("4")) {
                        JOptionPane.showMessageDialog(null, "Points assigned", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
        printMessages();
    }

    public void dashBoard() throws IOException {
        String toPrint = bufferedReader.readLine().replaceAll("§§§", "\n");
        textArea.setText(toPrint);
    }

    public void changeTopic() throws Exception {
        String newTopic = JOptionPane.showInputDialog(null, topicChangePrompt, "New Topic", JOptionPane.QUESTION_MESSAGE);
        if (newTopic == null) {
            printWriter.write(" ");
            printWriter.println();
            printWriter.flush();
        } else if (newTopic.isBlank()) {
            printWriter.write(" ");
            printWriter.println();
            printWriter.flush();
            String errorMessage = "Please enter a valid discussion forum name(ie. Not all spaces or blank).";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            printWriter.write(newTopic);
            printWriter.println();
            printWriter.flush();
            String receivedData = bufferedReader.readLine();
            System.out.println(receivedData);
            if (receivedData.equals("0")) {
                String errorMessage = "A forum with that name already exists!";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                discussionForumButton.setText(newTopic);
                printMessages();
            }
        }
    }

    public void sendMessageViaFileImport() throws Exception {
        StringBuilder output = new StringBuilder();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(new JOptionPane());
        if (result == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            try {
                FileReader fr = new FileReader(f);
                BufferedReader bfr = new BufferedReader(fr);
                String line = bfr.readLine();
                while (line != null) {
                    output.append(line);
                    line = bfr.readLine();
                }
                output = new StringBuilder(output.toString().replace("\r\n", " ").replace("\n", " "));
                output = new StringBuilder(output.toString().replace(".", ". "));
                String newPost = String.valueOf(output);
                if (newPost == null || newPost.isBlank()) {
                    String errorMessage = "Please enter a valid post(ie. Not all spaces or blank).";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    printWriter.write(newPost);
                    printWriter.println();
                    printWriter.flush();
                    printMessages();
                }
            } catch (FileNotFoundException e) {
                String errorMessage = "The file you entered was not found.";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException e) {
                String errorMessage = "Error parsing contents of the file.";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void printMessages() throws Exception {
        String messages = bufferedReader.readLine();
        if (messages.equals("0")) {
            messages = bufferedReader.readLine();
        }
        messages = messages.replaceAll("§§§", "\n");
        textArea.setText(messages);
    }

    public void printSpecificStudentMessages(String studentUsername) throws IOException {
        String toPrint = bufferedReader.readLine();
        toPrint = toPrint.replaceAll("§§§", "\n");
        textArea.setText(toPrint);
    }
}
