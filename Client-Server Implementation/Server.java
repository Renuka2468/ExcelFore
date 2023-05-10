import java.io.*;
import java.net.*;

public class Server {

    // Define the server listening address and port
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        
        //define the ServerSocket class to setup server port
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started, listening on port " + PORT);

        while (true) {
            
            //accepts client request and connect with server
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + clientSocket.getRemoteSocketAddress());

            // Start a new thread to handle the client connection
            Thread thread = new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        // Handle the received data (respond to a ping with a pong)
                        if (inputLine.equals("ping")) {
                            out.println("pong");
                        }
                    }

                    System.out.println("Client disconnected: " + clientSocket.getRemoteSocketAddress());
                    clientSocket.close();

                } catch (IOException e) {
                    System.err.println("Error handling client: " + e.getMessage());
                }
            });
            thread.start();
        }
    }
}

