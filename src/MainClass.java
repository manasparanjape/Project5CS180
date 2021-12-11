import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainClass {

    public static ArrayList<Boolean> forumUpdated = new ArrayList<>();

    private static BufferedReader bufferedReader;

    private static PrintWriter printWriter;
    private static PrintWriter dummyWriter;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2000);
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

    static class NewThreadMain extends Thread {
        public void run() {
            MainServer mainServer = new MainServer(bufferedReader, printWriter, dummyWriter);
            try {
                System.out.println(Thread.currentThread());
                mainServer.mainRunMethod();
            } catch (Exception ignored) {
            }
        }
    }
}
