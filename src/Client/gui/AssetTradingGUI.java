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
        leftMenuPanel.setBackground(Utility.DARKGREY);
        add(leftMenuPanel, BorderLayout.WEST);

        topMenuPanel.setPreferredSize(new Dimension(0, 60));
        topMenuPanel.setBackground(Utility.PRIMARYBLUE);
        add(topMenuPanel, BorderLayout.NORTH);

        ImageIcon icon = new ImageIcon(this.getClass().getResource("bell.png"));

        // Buttons in left menu
        JButton[] leftMenuButtons = new JButton[3];
        JButton[] topMenuButtons = new JButton[4];

        for (int i = 0; i < 3; i++) {
            String btnName = "";
            switch(i) {
                case 0:
                    btnName = "Notifications";
                    break;
                case 1:
                    btnName = "My Listings";
                    icon = new ImageIcon(this.getClass().getResource("mylistings.png"));
                    break;
                case 2:
                    btnName = "View Assets";
                    icon = new ImageIcon(this.getClass().getResource("viewassets.png"));
                    break;

            }
            JButton btn = new JButton(btnName, icon);
            btn.setBorderPainted(false);
            btn.setBackground(Utility.DARKGREY);
            btn.setForeground(Color.WHITE);
            btn.setPreferredSize(new Dimension(125, 40));

            leftMenuButtons[i] = btn;
            leftMenuPanel.add(btn);
        }

        // Just using these to test layout
        JLabel searchLabel = new JLabel("Search for...");
        searchLabel.setForeground(Color.WHITE);
        topMenuPanel.add(searchLabel);

        // Buttons in top menu (Will come back and refactor)
        for (int i = 0; i < 4; i++) {
            String btnName = "";
            switch(i) {
                case 0:
                    btnName = "Home";
                    icon = new ImageIcon(this.getClass().getResource("home.png"));
                    break;
                case 1:
                    btnName = "Buy";
                    icon = new ImageIcon(this.getClass().getResource("buy.png"));
                    break;
                case 2:
                    btnName = "Sell";
                    icon = new ImageIcon(this.getClass().getResource("sell.png"));
                    break;
                case 3:
                    btnName = "Acccount";
                    icon = new ImageIcon(this.getClass().getResource("account.png"));
                    break;
            }
            JButton btn = new JButton(btnName, icon);
            btn.setBorderPainted(false);
            btn.setBackground(Utility.PRIMARYBLUE);
            btn.setForeground(Color.WHITE);
            btn.setPreferredSize(new Dimension(130, 30));

            topMenuButtons[i] = btn;
            topMenuPanel.add(btn);
        }

        JLabel creditsLabel = new JLabel("Credits: [1200]");
        creditsLabel.setPreferredSize(new Dimension(100, 30));
        creditsLabel.setForeground(Color.WHITE);
        topMenuPanel.add(creditsLabel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AssetTradingGUI();
    }
}

