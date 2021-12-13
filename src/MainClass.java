import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
/**
 * MainClass.java
 *
 * Creates a socket that can handle multiple connections from various clients. Allows multiple users to connect
 * to the server
 *
 * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
 *
 * @version 12/13/2021
 */
public class MainClass {

    public static ArrayList<Boolean> forumUpdated = new ArrayList<>();

    private static BufferedReader bufferedReader;

    private static PrintWriter printWriter;
    private static PrintWriter dummyWriter;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2002);
        ServerSocket dummyServerSocket = new ServerSocket(2001);
        serverSocket.setReuseAddress(true);
        dummyServerSocket.setReuseAddress(true);
        while (true) {
            Socket socket = serverSocket.accept();
            Socket dummySocket = dummyServerSocket.accept();
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream());
            dummyWriter = new PrintWriter(dummySocket.getOutputStream());
            NewThreadMain t = new NewThreadMain();
            t.start();
        }
    }
    /**
     * NewThread.java
     *
     * Creates new thread for a new socket connection to the server and runs the thread
     *
     * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
     *
     * @version 12/13/2021
     */
    static class NewThreadMain extends Thread {
        public void run() {
            MainServer mainServer = new MainServer(bufferedReader, printWriter, dummyWriter);

            try {
                mainServer.mainRunMethod();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
