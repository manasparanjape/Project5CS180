import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) {

        while (true) {

            try (ServerSocket serverSocket = new ServerSocket(1234)) {
                Socket socket = serverSocket.accept();
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                Thread t = new ClientHandler(socket, inputStream, outputStream);

                t.start();


            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

}
