package Client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {

    final String USERNAME = "ben";
    final String PASSWORD = "123";

    private static JTextField usernameInput;
    private static JPasswordField passwordInput;

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
        usernameInput = new JTextField(20);
        usernameInput.setBounds(140, 180, 200, 25);
        loginPanel.add(usernameInput);

        // Password label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(140, 220, 80, 25);
        passwordLabel.setFont(new Font("Myriad Pro",Font.PLAIN,15));
        loginPanel.add(passwordLabel);

        // Password textbox
        passwordInput = new JPasswordField();
        passwordInput.setBounds(140, 250, 200, 25);;
        loginPanel.add(passwordInput);

        // Login button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(140, 310, 200, 30);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = usernameInput.getText();
                String password = passwordInput.getText();

                if (user.equals(USERNAME) && password.equals(PASSWORD)) {
                    setVisible(false);
                    new AssetTradingGUI();
                } else if (!user.equals(USERNAME)){
                    JOptionPane.showMessageDialog(null, "Incorrect Username", "Invalid", JOptionPane.ERROR_MESSAGE);
                } else if (!password.equals(PASSWORD)) {
                    JOptionPane.showMessageDialog(null, "Incorrect Password", "Invalid", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

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
