package Client.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Point of entry for the GUI of the program
 */
public class AssetTradingGUI extends JFrame{

    private JTextField loginField;
    private JPasswordField passwordField;

    public AssetTradingGUI() {
        super("Asset Trading");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 700));
        setLayout(new BorderLayout(0, 0));

        JPanel homePanel = new JPanel();
        JPanel leftMenuPanel = new JPanel();
        JPanel topMenuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,15));

        leftMenuPanel.setPreferredSize(new Dimension(140, 0));
        leftMenuPanel.setBackground(Utility.LIGHTBLUE);
        add(leftMenuPanel, BorderLayout.WEST);

        topMenuPanel.setPreferredSize(new Dimension(0, 60));
        topMenuPanel.setBackground(Utility.PRIMARYBLUE);
        add(topMenuPanel, BorderLayout.NORTH);

        // Buttons in left menu
        for (int i = 0; i < 4; i++) {
            String btnName = "";
            switch(i) {
                case 0:
                    btnName = "Notifications";
                    break;
                case 1:
                    btnName = "Item History";
                    break;
                case 2:
                    btnName = "My Listings";
                    break;
                case 3:
                    btnName = "View Assets";
                    break;

            }
            JButton btn = new JButton(btnName);
            btn.setPreferredSize(new Dimension(125, 25));
            leftMenuPanel.add(btn);
        }

        // Just using these to test layout
        JLabel searchLabel = new JLabel("Search for...");
        topMenuPanel.add(searchLabel);


        // Buttons in top menu (Will come and refactor)
        for (int i = 0; i < 4; i++) {
            String btnName = "";
            switch(i) {
                case 0:
                    btnName = "Home";
                    break;
                case 1:
                    btnName = "Buy";
                    break;
                case 2:
                    btnName = "Sell";
                    break;
                case 3:
                    btnName = "Acccount";
                    break;
            }
            JButton btn = new JButton(btnName);
            btn.setPreferredSize(new Dimension(100, 25));
            topMenuPanel.add(btn);
        }

        JLabel creditsLabel = new JLabel("Credits: [1200]");
        topMenuPanel.add(creditsLabel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AssetTradingGUI();
    }
}

