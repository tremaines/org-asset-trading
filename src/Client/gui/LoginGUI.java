package Client.gui;

import Client.User;
import Server.DBConnection;
import Server.UserDBSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class LoginGUI extends JFrame {

    private static JTextField usernameInput;
    private static JPasswordField passwordInput;

    private UserDBSource db;

    private User userLoggingIn;
    private User allUsers;

    public LoginGUI() {
        super("LOGIN");
        Connection connection = DBConnection.getConnection("./src/Server/dbserver.props");

        this.db = new UserDBSource(connection);

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
                String password = User.hashPassword(passwordInput.getText());

                if(!db.checkUsername(username)) {
                    JOptionPane.showMessageDialog(null, "Incorrect Username", "Invalid", JOptionPane.ERROR_MESSAGE);
                } else if (!(password.equals(db.userPassword(username)))) {
                    JOptionPane.showMessageDialog(null, "Incorrect Password", "Invalid", JOptionPane.ERROR_MESSAGE);
                } else {
                    setVisible(false);
                    User user = db.getUser(username);
                    System.out.printf("Login attempt for user '" + user.getFirstName() + "' was successful\n");
                    new AssetTradingGUI(user);
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
