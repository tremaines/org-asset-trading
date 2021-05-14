package Client;

import Server.ServerCommands;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * This class sends commands to the server and receives replies
 */
public class ServerAPI {

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    /**
     * Constructor for the sever API class
     */
    public ServerAPI() {
        Properties props = new Properties();
        FileInputStream details = null;

        String ip = null;
        int port = 0;

        // Read in details for sever connection
        try {
            details = new FileInputStream("./src/Client/client.props");
            props.load(details);
            details.close();

            ip = props.getProperty("server.ip");
            port = Integer.parseInt(props.getProperty("server.port"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create socket connection
        try {
            socket = new Socket(ip, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve a user
     * @param username The username of the user to be retrieved
     * @return An instance of the user
     */
    public User getUser(String username) {
        try {
            outputStream.writeObject(ServerCommands.GET_USER);
            outputStream.writeObject(username);

            outputStream.flush();

            return (User) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Check whether a user exists
     * @param username The username to be checked
     * @return True if they exist, false if not or null if there is a connection error
     */
    public Boolean checkUser(String username) {
        try {
            outputStream.writeObject(ServerCommands.CHECK_USER);
            outputStream.writeObject(username);
            outputStream.flush();

            return (Boolean) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
