import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class Client {

    // Define the port to connect to particular server
    private static final int PORT = 12345;
    public static void main(String[] args) throws IOException, InterruptedException {
        //Define the client socket class with server port and address
        Socket socket = new Socket("localhost", PORT);
        System.out.println("Connected to server at " + "localhost" + ":" + PORT);

        //classes for reading and writting on server 
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Send a ping message to the server every second
        while (true) {
            out.println("ping");
            System.out.println("Sent ping to server");
            TimeUnit.SECONDS.sleep(1);

            // Receive data from the server
            String inputLine = in.readLine();

            // Handle the received data (check for a pong response)
            if (inputLine.equals("pong")) {
                System.out.println("Received pong from server");
            } else {
                System.out.println("Received unexpected data from server: " + inputLine);
                break;
            }
            break;
        }

        socket.close();
    }
}
