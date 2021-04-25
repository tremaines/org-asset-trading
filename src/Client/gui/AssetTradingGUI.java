package Client.gui;

import Client.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


/**
 * Point of entry for the GUI of the program
 */
public class AssetTradingGUI extends JFrame implements ActionListener {

    private JTextField loginField;
    private JPasswordField passwordField;

    private JPanel maincontent;
    private JPanel buyPanel;
    private JPanel sellPanel;
    private JPanel accountPanel;
    private JPanel assetsPanel;
    private JPanel notificationsPanel;
    private static JTextField searchBox;

    private CardLayout cardLayout = new CardLayout();

    private Organisation org;
    private User userLoggedIn;
    private Assets allAssets;
    private Trades allTrades;

    public AssetTradingGUI(Organisation organisation, User userAccount, Assets assets,
                           Trades trades) {
        super("Asset Trading");

        // Collection of all instances of Organisation objects
        org = organisation;
        // Current User object of the user account logged in
        userLoggedIn = userAccount;
        // Current Asset object
        allAssets = assets;
        // Current Trades object
        allTrades = trades;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 700));
        setLayout(new BorderLayout(0, 0));

        JPanel leftMenuPanel = new JPanel();
        JPanel topMenuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,15));

        leftMenuPanel.setPreferredSize(new Dimension(140, 0));
        leftMenuPanel.setLayout(new BorderLayout());
        leftMenuPanel.setBackground(Utility.DARKGREY);
        add(leftMenuPanel, BorderLayout.WEST);
        JPanel topLeft = new JPanel();
        JPanel bottomLeft = new JPanel();
        topLeft.setBackground(Utility.DARKGREY);
        bottomLeft.setBackground(Utility.DARKGREY);

        topMenuPanel.setPreferredSize(new Dimension(0, 60));
        topMenuPanel.setBackground(Utility.PRIMARYBLUE);
        add(topMenuPanel, BorderLayout.NORTH);

        ImageIcon icon = new ImageIcon(this.getClass().getResource("images/bell.png"));

        // Buttons in left menu
        JButton[] leftMenuButtons = new JButton[4];
        JButton[] topMenuButtons = new JButton[4];

        for (int i = 0; i < 4; i++) {
            String btnName = "";
            switch(i) {
                case 0:
                    btnName = "Notifications";
                    break;
                case 1:
                    btnName = "My Listings";
                    icon = new ImageIcon(this.getClass().getResource("images/mylistings.png"));
                    break;
                case 2:
                    btnName = "View Assets";
                    icon = new ImageIcon(this.getClass().getResource("images/viewassets.png"));
                    break;
                case 3:
                    btnName = "Logout";
                    icon = new ImageIcon(this.getClass().getResource("images/logout.png"));
                    break;

            }
            JButton btn = new JButton(btnName, icon);
            btn.setBorderPainted(false);
            btn.setBackground(Utility.DARKGREY);
            btn.setForeground(Color.WHITE);
            btn.setPreferredSize(new Dimension(125, 40));
            btn.addActionListener(this);
            leftMenuButtons[i] = btn;
            if(btnName.equals("Logout")) {
                bottomLeft.add(btn, BorderLayout.SOUTH);
            } else  {
                topLeft.add(btn);
            }
        }
        leftMenuPanel.add(topLeft);
        leftMenuPanel.add(bottomLeft, BorderLayout.SOUTH);

        // Welcome message
        JLabel welcomeMessage = new JLabel("Welcome, " + userLoggedIn.getUsername() + "!");
        welcomeMessage.setFont(new Font("Myriad Pro",Font.BOLD,16));
        welcomeMessage.setForeground(Color.WHITE);
        welcomeMessage.setPreferredSize(new Dimension(240, 25));
        topMenuPanel.add(welcomeMessage);

        // Just using these to test layout
        JLabel searchLabel = new JLabel("Search assets");
        searchBox = new JTextField(10);
        searchLabel.setForeground(Color.WHITE);
        topMenuPanel.add(searchLabel);
        topMenuPanel.add(searchBox);

        // Buttons in top menu (Will come back and refactor)
        for (int i = 0; i < 3; i++) {
            String btnName = "";
            switch(i) {
                case 0:
                    btnName = "Buy";
                    icon = new ImageIcon(this.getClass().getResource("images/buy.png"));
                    break;
                case 1:
                    btnName = "Sell";
                    icon = new ImageIcon(this.getClass().getResource("images/sell.png"));
                    break;
                case 2:
                    btnName = "Account";
                    icon = new ImageIcon(this.getClass().getResource("images/account.png"));
                    break;
            }
            JButton btn = new JButton(btnName, icon);
            btn.setBorderPainted(false);
            btn.setBackground(Utility.PRIMARYBLUE);
            btn.setForeground(Color.WHITE);
            btn.setPreferredSize(new Dimension(120, 30));
            btn.addActionListener(this);

            topMenuButtons[i] = btn;
            topMenuPanel.add(btn);
        }

        JLabel creditsLabel =
                new JLabel("Credits: [" + org.getOrganisation(userLoggedIn.getOrganisationName()).getCredits() + "]");
        creditsLabel.setPreferredSize(new Dimension(100, 30));
        creditsLabel.setForeground(Color.WHITE);
        topMenuPanel.add(creditsLabel);

        // Main content area
        maincontent = new JPanel();
        maincontent.setLayout(cardLayout);
        add(maincontent, BorderLayout.CENTER);

        // Buy Panel
        buyPanel = new JPanel();
        buyPanel.setBorder(BorderFactory.createTitledBorder("Buy"));
        maincontent.add(buyPanel, "1");

        // Sell Panel
        sellPanel = new JPanel();
        sellPanel.setBorder(BorderFactory.createTitledBorder("Sell"));
        maincontent.add(sellPanel, "2");

        // Account Panel
        setSize(800,600);
        JLabel label1 , label2, label3 , label4, label5;
        JTextField t1, t2, t3,t4;
        //JComboBox day, month, year;
        JRadioButton AccUser, AccAdmin;
        //JTextArea ta1;
        JCheckBox terms;
        JButton submit;
        JLabel msg;

        accountPanel = new JPanel();
        accountPanel.setBorder(BorderFactory.createTitledBorder("Account"));

        label1 = new JLabel("First Name");
        label1.setBounds(30, 50, 100, 20);

        t1 = new JTextField();
        t1.setBounds(140 , 50, 100 , 20);

        label2 = new JLabel("Last Name");
        label2.setBounds(140 , 60, 100 , 20);

        t2 = new JTextField();
        t2.setBounds(140 , 60, 100 , 20);

        label3 = new JLabel("Email");
        label3.setBounds(140 , 70, 100 , 20);

        t3 = new JTextField();
        t3.setBounds(140 , 70, 100 , 20);

        label4 = new JLabel("Unit");
        label4.setBounds(140 , 80, 100 , 20);

        t4 = new JTextField();
        t4.setBounds(140 , 80, 100 , 20);

        label5 = new JLabel("Access Level");
        label5.setBounds(140 , 90, 100 , 20);

        AccUser  = new JRadioButton("Access Level: User");
        AccAdmin = new JRadioButton("Access Level: Admin");
        AccUser.setBounds(140 , 90, 100 , 20);
        AccAdmin.setBounds(140 , 90, 100 , 20);
        AccUser.setSelected(true);

        terms = new JCheckBox("Please Accept that the " +
                "details you have entered are correct");
        terms.setBounds(140 , 100, 100 , 20);

        submit = new JButton("Submit");
        submit.setBounds(140 , 110, 100 , 20);

        msg = new JLabel("");
        msg.setBounds(140 , 120, 100 , 20);

        // accountPanel.add();
        accountPanel.add(label1);
        accountPanel.add(t1);
        accountPanel.add(label2);
        accountPanel.add(t2);
        accountPanel.add(label3);
        accountPanel.add(t3);
        accountPanel.add(label4);
        accountPanel.add(t4);
        accountPanel.add(label5);
        accountPanel.add(AccUser);
        accountPanel.add(AccAdmin);
        accountPanel.add(terms);
        accountPanel.add(submit);
        accountPanel.add(msg);

        maincontent.add(accountPanel, "3");

        // Assets Panel
        assetsPanel = new JPanel(new BorderLayout(0, 0));
        assetsPanel.setBorder(BorderFactory.createTitledBorder("Assets"));
        maincontent.add(assetsPanel, "4");

        AssetsTable table = new AssetsTable(assetsPanel, allAssets, allTrades);

        // Display 'View Assets' page as default
        cardLayout.show(maincontent, "4");

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) throws UserException, TradesException {
        Organisation organisation = new Organisation();
        List<String> assetsList = new ArrayList<>();
        assetsList.add("Hardware Resources");
        List<Integer> assetAmountsList = new ArrayList<>();
        assetAmountsList.add(10);
        organisation.createOrganisation("Microsoft", 100, assetsList, assetAmountsList);
        User user = new User(organisation);
        user.createUser("test", "test123", false, "Microsoft");
        Assets assets = new Assets();
        Trades trades = new Trades(organisation, user);
        trades.createListing("test", "Sell", "Hardware Resources", 10, 25);
        new LoginGUI(user, organisation, assets, trades);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnSrcTxt = e.getActionCommand();

        if (btnSrcTxt.equals("Buy")) {
            cardLayout.show(maincontent, "1");
        } else if (btnSrcTxt.equals("Sell")) {
            cardLayout.show(maincontent, "2");
        } else if (btnSrcTxt.equals("Account")) {
            cardLayout.show(maincontent, "3");
        } else if (btnSrcTxt.equals("View Assets")) {
            cardLayout.show(maincontent, "4");
        } else if (btnSrcTxt.equals("Logout")) {
            setVisible(false);
            new LoginGUI(userLoggedIn, org, allAssets, allTrades);
        }
    }
}

