import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainClass implements Runnable {

    public static ArrayList<Boolean> forumUpdated = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2000);
        serverSocket.setReuseAddress(true);
        while (true) {
            Socket socket = serverSocket.accept();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    MainServer mainServer = new MainServer(bufferedReader, printWriter);
                    try {
                        mainServer.mainRunMethod();
                    } catch (Exception ignored) {
                    }
                }
            });
            t.start();
        }
    }

    @Override
    public void run() {

    }
}
