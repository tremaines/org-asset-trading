package Client.gui;

import Client.ServerAPI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Create a listener instance that closes the client's connection to the server when the application is closed
 */
public class ClosingListener extends WindowAdapter {

        private ServerAPI server;

    /**
     * Constructor
     * @param server The server connection to close
     */
    public ClosingListener(ServerAPI server) {
            this.server = server;
        }

    /**
     * Event handler
     * @param e
     */
    @Override
        public void windowClosing(WindowEvent e) {
            server.closeServerConnection();
            System.exit(0);
        }
}
