package Client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Point of entry for the GUI of the program
 */
public class AssetTradingGUI extends JFrame implements ActionListener {

    private JTextField loginField;
    private JPasswordField passwordField;

    private JPanel maincontent;
    private JPanel homePanel;
    private JPanel buyPanel;
    private JPanel sellPanel;
    private JPanel accountPanel;


    private CardLayout cardLayout = new CardLayout();

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
                    btnName = "Account";
                    icon = new ImageIcon(this.getClass().getResource("account.png"));
                    break;
            }
            JButton btn = new JButton(btnName, icon);
            btn.setBorderPainted(false);
            btn.setBackground(Utility.PRIMARYBLUE);
            btn.setForeground(Color.WHITE);
            btn.setPreferredSize(new Dimension(130, 30));
            btn.addActionListener(this);

            topMenuButtons[i] = btn;
            topMenuPanel.add(btn);
        }

        JLabel creditsLabel = new JLabel("Credits: [1200]");
        creditsLabel.setPreferredSize(new Dimension(100, 30));
        creditsLabel.setForeground(Color.WHITE);
        topMenuPanel.add(creditsLabel);


        // Main content area
        maincontent = new JPanel();
        maincontent.setLayout(cardLayout);
        add(maincontent, BorderLayout.CENTER);

        homePanel = new JPanel();
        homePanel.setBorder(BorderFactory.createTitledBorder("Home"));
        maincontent.add(homePanel, "1");

        buyPanel = new JPanel();
        buyPanel.setBorder(BorderFactory.createTitledBorder("Buy"));
        maincontent.add(buyPanel, "2");

        sellPanel = new JPanel();
        sellPanel.setBorder(BorderFactory.createTitledBorder("Sell"));
        maincontent.add(sellPanel, "3");

        accountPanel = new JPanel();
        accountPanel.setBorder(BorderFactory.createTitledBorder("Account"));
        maincontent.add(accountPanel, "4");

        cardLayout.show(maincontent, "1");

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AssetTradingGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnSrcTxt = e.getActionCommand();


        if (btnSrcTxt.equals("Home")) {
            cardLayout.show(maincontent, "1");
        } else if (btnSrcTxt.equals("Buy")) {
            cardLayout.show(maincontent, "2");
        } else if (btnSrcTxt.equals("Sell")) {
            cardLayout.show(maincontent, "3");
        } else if (btnSrcTxt.equals("Account")) {
            cardLayout.show(maincontent, "4");
        }
    }
}

