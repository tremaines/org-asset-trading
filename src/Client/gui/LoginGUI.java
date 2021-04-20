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

        JPanel loginPanel = new JPanel();

        loginPanel.setLayout(null);

        // Login label
        JLabel headingLabel = new JLabel("LOGIN");
        headingLabel.setBounds(195, 90, 140, 25);
        headingLabel.setFont(new Font("Myriad Pro",Font.PLAIN,30));
        loginPanel.add(headingLabel);

        // Username label
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(140, 150, 80, 25);
        usernameLabel.setFont(new Font("Myriad Pro",Font.PLAIN,15));
        loginPanel.add(usernameLabel);

        // Username textbox
        JTextField usernameText = new JTextField(20);
        usernameText.setBounds(140, 180, 200, 25);
        loginPanel.add(usernameText);

        // Password label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(140, 220, 80, 25);
        passwordLabel.setFont(new Font("Myriad Pro",Font.PLAIN,15));
        loginPanel.add(passwordLabel);

        // Password textbox
        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(140, 250, 200, 25);;
        loginPanel.add(passwordText);


        // Submit button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(140, 310, 200, 30);
        loginPanel.add(loginBtn);


        add(loginPanel);
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
