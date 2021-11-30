import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Trial extends JComponent implements Runnable {
    JFrame jframe = new JFrame("Discussion Forum");
    JTextField newMessageField;
    JButton sendButton;
    JButton button1;
    JButton button2;
    JButton button3;
    JTextArea textArea;
    String toWrite = "";
    Trial trial;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == sendButton) {
                textArea.append(newMessageField.getText() + "\n");
                toWrite = "";
                newMessageField.setText("");
            }
        }
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Trial());
    }

    @Override
    public void run()   {
        Container container = jframe.getContentPane();
        container.setLayout(new BorderLayout());
        //trial = new Trial();

        sendButton = new JButton("Send");
        sendButton.addActionListener(actionListener);
        textArea = new JTextArea();
        //textArea.setPreferredSize(new Dimension(400, 50));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        //textArea.setText("xx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\n");
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setPreferredSize(new Dimension(20, 100));
        newMessageField = new JTextField(10);
        button1 = new JButton("Button 1");
        button2 = new JButton("Button 2");
        button3 = new JButton("Button 3");

        jframe.setSize(900, 600);
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setVisible(true);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(newMessageField);
        bottomPanel.add(sendButton);
        JPanel leftPanel = new JPanel();
        leftPanel.add(button1);
        leftPanel.add(button2);
        leftPanel.add(button3);
        //JPanel centerPanel = new JPanel();
        //centerPanel.add(textArea);
        //centerPanel.add(areaScrollPane);

        container.add(areaScrollPane, BorderLayout.CENTER);
        container.add(bottomPanel, BorderLayout.SOUTH);
        container.add(leftPanel, BorderLayout.WEST);
    }
}

/*public class Trial extends JComponent implements Runnable {

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Trial());
    }

    public void run() {
        // create a jtextarea
        JTextArea textArea = new JTextArea();

        // add text to it; we want to make it scroll
        textArea.setText("xx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\n");

        // create a scrollpane, givin it the textarea as a constructor argument
        JScrollPane scrollPane = new JScrollPane(textArea);

        // now add the scrollpane to the jframe's content pane, specifically
        // placing it in the center of the jframe's borderlayout
        JFrame frame = new JFrame("JScrollPane Test");
        Container container = frame.getContentPane();
        container.add(scrollPane, BorderLayout.CENTER);

        // make it easy to close the application
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set the frame size (you'll usually want to call frame.pack())
        frame.setSize(new Dimension(240, 180));

        // center the frame
        frame.setLocationRelativeTo(null);

        // make it visible to the user
        frame.setVisible(true);
    }
}*/

