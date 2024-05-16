import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkingServer {
    public static void main(String[] args) {

        ServerSocket server = null;
        Socket client;

        //Default port number
        int portnumber = 1234;
        if (args.length >= 1) {
            portnumber = Integer.parseInt(args[0]);
        }

        //Create Server side socket
        try {
            server = new ServerSocket(portnumber);

        } catch (Exception e) {
            System.out.println("Cannot open socket " + e);
            System.exit(1);
        }
        System.out.println("Server is created " + server);

        //wait for data from client
        while (true) {
            try {

                System.out.println("Waiting for connection...");
                client = server.accept();

                System.out.println("Connect request is accepted...");
                String clientHost = client.getInetAddress().getHostAddress();
                int clientPort = client.getPort();
                System.out.println("CLient host = " + clientHost + "Client port = " + clientPort);

                //read data from the client
                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
                String msgFromClient = br.readLine();
                System.out.println("Message from client = " + msgFromClient);

                //send response to client
                if (msgFromClient != null && !msgFromClient.equalsIgnoreCase("bye")) {
                    OutputStream clientOut = client.getOutputStream();
                    PrintWriter pw = new PrintWriter(clientOut, true);
                    String ansMsg = "Hello " + msgFromClient;
                    pw.println(ansMsg);

                }

                //close socket
                if (msgFromClient != null && msgFromClient.equalsIgnoreCase("bye")) {

                    server.close();
                    client.close();
                    break;
                }

            } catch (Exception e) {
                System.out.println("Error something went wrong");
            }


        }

        System.out.println("Hello world!");
    }
}