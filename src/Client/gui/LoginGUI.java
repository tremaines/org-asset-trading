package Client.gui;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {

    final String USERNAME = "Test";
    final String PASSWORD = "123";

    public LoginGUI() {
        super("LOGIN");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        JPanel loginPanel = new JPanel(new FlowLayout());


        JTextField usernameField = new JTextField("Username: ");
        JPasswordField passwordField = new JPasswordField("Password");

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new LoginGUI();
        });
    }


}
