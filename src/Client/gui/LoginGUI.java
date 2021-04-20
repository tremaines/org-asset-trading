package Client.gui;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {
    /**
     * Correct username and password
     */
    final String USERNAME = "Test";
    final String PASSWORD = "123";

    /**
     * Creates new form LoginForm
     */
    public LoginGUI() {
        super("LOGIN");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        SpringLayout layout = new SpringLayout();
        JPanel loginPanel = new JPanel(new SpringLayout());

        String[] labels = {"Username: ", "Password: "};
        int numPairs = labels.length;

        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            loginPanel.add(l);
            JTextField textField = new JTextField(10);
            l.setLabelFor(textField);
            loginPanel.add(textField);
        }

        //Lay out the panel.
        add(loginPanel);




        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void initComponents() {

        username = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        loginButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setLocation(new java.awt.Point(0, 0));
        setResizable(false);

        username.setPreferredSize(new java.awt.Dimension(100, 25));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Login");

        password.setPreferredSize(new java.awt.Dimension(100, 25));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Password:");

        loginButton.setText("Sign In");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Username:");



        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        String userName = username.getText();
        String passWord = new String(password.getPassword());
        if(!userName.equals(USERNAME)) {
            JOptionPane.showMessageDialog(null, "Incorrect Username", "Invalid", JOptionPane.ERROR_MESSAGE);
        }
        else if(!passWord.equals(PASSWORD)) {
            JOptionPane.showMessageDialog(null, "Incorrect Password", "Invalid", JOptionPane.ERROR_MESSAGE);
        }
        else {
            this.setVisible(false);
            JFrame frame = new JFrame();
            frame.setVisible(true);
            frame.setTitle("Welcome");
            frame.setSize(400, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.add(new JLabel("Welcome", SwingConstants.CENTER));
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new LoginGUI();
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField password;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
