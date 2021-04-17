package Client.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Point of entry for the GUI of the program
 */
public class AssetTradingGUI extends JFrame {

    private JTextField loginField;
    private JPasswordField passwordField;

    public AssetTradingGUI() {
        super("Asset Trading");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 700));
        setLayout(new BorderLayout(0, 7));

        JPanel homePanel = new JPanel();
        JPanel leftMenuPanel = new JPanel();
        JPanel topMenuPanel = new JPanel();

        leftMenuPanel.setPreferredSize(new Dimension(140, 0));
        leftMenuPanel.setBackground(Color.GRAY);
        add(leftMenuPanel, BorderLayout.WEST);

        topMenuPanel.setPreferredSize(new Dimension(0, 60));
        topMenuPanel.setBackground(Color.GRAY);
        add(topMenuPanel, BorderLayout.NORTH);


        // Buttons in left menu
        JButton notificationsBtn = new JButton("Notifications");
        JButton historyBtn = new JButton("Item History");
        JButton myListingsBtn = new JButton("My Listings");
        JButton viewAssetsBtn = new JButton("View Assets");

        leftMenuPanel.add(notificationsBtn);
        leftMenuPanel.add(historyBtn);
        leftMenuPanel.add(myListingsBtn);
        leftMenuPanel.add(viewAssetsBtn);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AssetTradingGUI();
    }
}

