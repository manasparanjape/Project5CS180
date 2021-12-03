import java.io.*;
import java.net.*;

public class ClientHandler extends Thread{

    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private final Socket socket;

    private static String accountsFile = "AccountsFile.txt";

    public ClientHandler(Socket socket, DataInputStream inputStream, DataOutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {

        while (true){
            try {
                String input = inputStream.readUTF();

                if (input.equals("Read Accounts File")) {
                    BufferedReader bfr = new BufferedReader(new FileReader(new File(accountsFile)));
                    String line = bfr.readLine();
                    while (line != null) {
                        outputStream.writeUTF(line);
                        line = bfr.readLine();
                    }
                    outputStream.writeUTF("End");
                    bfr.close();
                } else if (input.equals("End Connection")) {
                    this.socket.close();
                }

            } catch (EOFException e) {
                // fine
            } catch (IOException e){
                e.printStackTrace();
            }
        }




    }

}
