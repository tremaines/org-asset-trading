package Client.gui;

import Client.Assets;
import Client.Organisation;
import Client.Trades;
import Client.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {

    private static JTextField usernameInput;
    private static JPasswordField passwordInput;

    private Organisation org;
    private User userLoggingIn;
    private Trades allTrades;
    private Assets allAssets;

    public LoginGUI(User user, Organisation organisation, Assets assets,
                    Trades trades) {
        super("LOGIN");

        this.org = organisation;
        this.userLoggingIn = user;
        this.allAssets = assets;
        this.allTrades = trades;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        setResizable(false);

        JPanel loginPanel = new JPanel();

        loginPanel.setLayout(null);

        // Login icon
        ImageIcon icon = new ImageIcon(this.getClass().getResource("images/lock.png"));
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBounds(240, 80, 100, 45);
        loginPanel.add(iconLabel);

        // Login label
        JLabel headingLabel = new JLabel("LOGIN");
        headingLabel.setBounds(170, 90, 140, 25);
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
        loginBtn.setBackground(Utility.PRIMARYBLUE);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Myriad Pro",Font.PLAIN,15));
        loginBtn.setBounds(140, 310, 200, 30);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameInput.getText();
                String password = passwordInput.getText();

                if(!userLoggingIn.userExists(username)) {
                    JOptionPane.showMessageDialog(null, "Incorrect Username", "Invalid", JOptionPane.ERROR_MESSAGE);
                } else if (!userLoggingIn.loginSuccessful(username, password)) {
                    JOptionPane.showMessageDialog(null, "Incorrect Password", "Invalid", JOptionPane.ERROR_MESSAGE);
                } else if (userLoggingIn.loginSuccessful(username, password)) {
                    setVisible(false);
                    System.out.printf("Login attempt for user '" + username + "' was successful\n");
                    userLoggingIn = user.getUser(username);
                    new AssetTradingGUI(org, userLoggingIn, allAssets, allTrades);
                }
            }
        });

        loginPanel.add(loginBtn);

        add(loginPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
