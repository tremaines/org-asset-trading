package Client.gui;

import Client.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

    private JPanel mainContent;
    private JPanel buyPanel;
    private JPanel sellPanel;
    private JPanel accountPanel;
    private JPanel assetsPanel;
    private JPanel myListingsPanel;
    private JPanel notificationsPanel;

    // Top menu components
    private JPanel topMenuContainer;
    private JPanel topMenuRight;
    private JPanel topMenuLeft;

    // Side menu components
    private JPanel sideMenuTop;
    private JPanel sideMenuBottom;

    private CardLayout cardLayout = new CardLayout();

    private Organisation org;
    private User userLoggedIn;
    private User allUsers;
    private Assets allAssets;
    private Trades allTrades;

    public AssetTradingGUI(Organisation organisation, User userAccount,
                           User allUserAccounts, Assets assets,
                           Trades trades) {
        super("Asset Trading");

        // Collection of all instances of Organisation objects
        this.org = organisation;
        // Current User object of the user account logged in
        this.userLoggedIn = userAccount;
        // Collection of all instances of User objects
        this.allUsers = allUserAccounts;
        // Current Asset object
        this.allAssets = assets;
        // Current Trades object
        this.allTrades = trades;

        // Setup of main frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 700));
        setLayout(new BorderLayout(0, 0));

        // Main content in center
        mainContent = new JPanel();
        mainContent.setLayout(cardLayout);
        add(mainContent, BorderLayout.CENTER);

        // Setup Panels
        addTopMenu();
        addSideMenu();
        setupBuyPanel();
        setupSellPanel();
        setupAccountPanel();
        setupAssetsPanel();
        setupMyListingsPanel();

        // Display assets panel on startup
        cardLayout.show(mainContent, "4");

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void refreshGUI() {
        topMenuRight.setVisible(false);
        topMenuContainer.setVisible(false);
        topMenuLeft.setVisible(false);
        addTopMenu();
        setupAssetsPanel();
    }

    public static void main(String[] args) throws UserException, TradesException, AssetsException {
        Organisation organisation = new Organisation();
        List<String> assetsList = new ArrayList<>();
        assetsList.add("Hardware Resources");
        List<Integer> assetAmountsList = new ArrayList<>();
        assetAmountsList.add(20);
        organisation.createOrganisation("Microsoft", 100, assetsList, assetAmountsList);
        User user = new User(organisation);
        user.createUser("test", "test123", false, "Microsoft");
        user.createUser("admin", "admin", true, "");
        Assets assets = new Assets();
        Trades trades = new Trades(organisation, user);
        trades.createListing("test", "Sell", "Hardware Resources", 10, 25);
        new LoginGUI(user, user, organisation, assets, trades);
    }

    public void addTopMenu() {
        // Contains all components
        topMenuContainer = new JPanel(new BorderLayout());
        topMenuContainer.setBackground(Utility.PRIMARYBLUE);

        topMenuRight = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,15));
        topMenuRight.setBackground(Utility.PRIMARYBLUE);

        topMenuLeft = new JPanel();
        topMenuLeft.setBorder(new EmptyBorder(0, 20, 0, 0));
        topMenuLeft.setBackground(Utility.PRIMARYBLUE);

        add(topMenuContainer, BorderLayout.NORTH);
        topMenuContainer.add(topMenuLeft, BorderLayout.WEST);
        topMenuContainer.add(topMenuRight, BorderLayout.EAST);

        // Display username in top left corner
        JLabel userLabel = new JLabel(userLoggedIn.getUsername());
        userLabel.setPreferredSize(new Dimension(60, 50));
        userLabel.setForeground(Color.WHITE);
        topMenuLeft.add(userLabel);

        ImageIcon icon = new ImageIcon(this.getClass().getResource("images/bell.png"));

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
            topMenuRight.add(btn);
        }

        JLabel creditsLabel =
                new JLabel("Credits: [" + org.getOrganisation(userLoggedIn.getOrganisationName()).getCredits() + "]");
        creditsLabel.setPreferredSize(new Dimension(100, 30));
        creditsLabel.setForeground(Color.WHITE);
        topMenuRight.add(creditsLabel);
    }

    public void addSideMenu() {

        JPanel leftMenuPanel = new JPanel();
        leftMenuPanel.setPreferredSize(new Dimension(140, 0));
        leftMenuPanel.setLayout(new BorderLayout());
        leftMenuPanel.setBackground(Utility.DARKGREY);
        add(leftMenuPanel, BorderLayout.WEST);
        sideMenuTop = new JPanel();
        sideMenuBottom = new JPanel();
        sideMenuTop.setBackground(Utility.DARKGREY);
        sideMenuBottom.setBackground(Utility.DARKGREY);
        leftMenuPanel.add(sideMenuTop);
        leftMenuPanel.add(sideMenuBottom, BorderLayout.SOUTH);

        ImageIcon icon = new ImageIcon(this.getClass().getResource("images/bell.png"));

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
            if(btnName.equals("Logout")) {
                sideMenuBottom.add(btn, BorderLayout.SOUTH);
            } else  {
                sideMenuTop.add(btn);
            }
        }
    }

    public void setupBuyPanel() {

        buyPanel = new JPanel(new BorderLayout(0, 0));
        buyPanel.setBorder(BorderFactory.createTitledBorder("Buy"));

        setSize(800,600);
        JLabel label1 , label2, label3 , label4, label5;
        JSpinner s2, s3;

        JCheckBox terms;
        JButton submit;
        JLabel msg;

        label1 = new JLabel("Asset Type");
        label1.setBounds(30, 50, 100, 20);

        JComboBox assetTypeList = new JComboBox(allAssets.getAllAssets().toArray(new String[0]));
        assetTypeList.setBounds(140 , 50, 150 , 20);
        
        label2 = new JLabel("Amount");
        label2.setBounds(30 , 80, 100 , 20);

        s2 = new JSpinner();
        s2.setBounds(140 , 80, 150 , 20);

        label3 = new JLabel("Cost Per Unit");
        label3.setBounds(30 , 110, 100 , 20);

        s3 = new JSpinner();
        s3.setBounds(140 , 110, 150 , 20);

        terms = new JCheckBox("Please accept that the details you have entered are correct");
        terms.setBounds(27 , 143, 400 , 20);

        submit = new JButton("Submit");
        submit.setBounds(30 , 180, 100 , 20);

        msg = new JLabel("");
        msg.setBounds(140 , 230, 100 , 20);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = (String) assetTypeList.getSelectedItem();
                String amount = s2.getValue() + "";
                String price = s3.getValue() + "";
                boolean boxSelected = terms.isSelected();

                // Checks if the amount and price values are positive and non-zero
                if(Integer.parseInt(amount) > 0 && Integer.parseInt(price) > 0) {

                    // If the checkbox is selected
                    if(boxSelected) {
                        try {
                            allTrades.createListing(userLoggedIn.getUsername(), "Buy", type,
                                    Integer.parseInt(amount), Integer.parseInt(price));
                            refreshGUI();
                        } catch (TradesException tradesException) {
                            JOptionPane.showMessageDialog(null, "You do not have enough credits to " +
                                            "create this listing.", "Credits Error",
                                    JOptionPane.ERROR_MESSAGE);
                            tradesException.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "You have not accepted " +
                                        "the terms. Please select the checkbox.", "Checkbox Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid entry: Please enter a positive " +
                                    "non-zero number for both Amount and Cost", "Checkbox Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        // accountPanel.add();
        buyPanel.add(label1);
        buyPanel.add(label2);
        buyPanel.add(s2);
        buyPanel.add(label3);
        buyPanel.add(s3);
        buyPanel.add(assetTypeList);
        buyPanel.add(terms);
        buyPanel.add(submit);
        buyPanel.add(msg);

        mainContent.add(buyPanel, "1");

    }

    public void setupSellPanel() {

        sellPanel = new JPanel(new BorderLayout(0, 0));
        sellPanel.setBorder(BorderFactory.createTitledBorder("Sell"));

        setSize(800,600);
        JLabel label1 , label2, label3 , label4, label5;
        JSpinner s2, s3;

        JCheckBox terms;
        JButton submit;
        JLabel msg;

        label1 = new JLabel("Asset Type");
        label1.setBounds(30, 50, 100, 20);

        JComboBox assetOwnedList =
                new JComboBox(org.getOrganisation(userLoggedIn.getOrganisationName()).getAssets().toArray(new String[0]));
        assetOwnedList.setBounds(140 , 50, 150 , 20);

        label2 = new JLabel("Amount");
        label2.setBounds(30 , 80, 100 , 20);

        s2 = new JSpinner();
        s2.setBounds(140 , 80, 150 , 20);

        label3 = new JLabel("Cost Per Unit");
        label3.setBounds(30 , 110, 100 , 20);

        s3 = new JSpinner();
        s3.setBounds(140 , 110, 150 , 20);

        terms = new JCheckBox("Please confirm that the details you have entered are correct");
        terms.setBounds(27 , 143, 400 , 20);

        submit = new JButton("Submit");
        submit.setBounds(30 , 180, 100 , 20);

        msg = new JLabel("");
        msg.setBounds(140 , 230, 100 , 20);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = (String) assetOwnedList.getSelectedItem();
                String amount = s2.getValue() + "";
                String price = s3.getValue() + "";
                boolean boxSelected = terms.isSelected();

                // Checks if the amount and price values are positive and non-zero
                if(Integer.parseInt(amount) > 0 && Integer.parseInt(price) > 0) {

                    // If the checkbox is selected
                    if(boxSelected) {
                        try {
                            allTrades.createListing(userLoggedIn.getUsername(), "Sell", type,
                                    Integer.parseInt(amount), Integer.parseInt(price));
                            refreshGUI();
                        } catch (TradesException tradesException) {
                            JOptionPane.showMessageDialog(null, "You do not have enough assets " +
                                            "to create this listing", "Assets Error",
                                    JOptionPane.ERROR_MESSAGE);
                            tradesException.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "You have not accepted " +
                                        "the terms. Please select the checkbox.", "Checkbox Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid entry: Please enter a positive " +
                                    "non-zero number for both Amount and Cost", "Checkbox Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        // accountPanel.add();
        sellPanel.add(label1);
        sellPanel.add(label2);
        sellPanel.add(s2);
        sellPanel.add(label3);
        sellPanel.add(s3);
        sellPanel.add(assetOwnedList);
        sellPanel.add(terms);
        sellPanel.add(submit);
        sellPanel.add(msg);

        mainContent.add(sellPanel, "2");

    }

    public void setupAccountPanel() {
        accountPanel = new JPanel(new BorderLayout(0, 0));
        accountPanel.setBorder(BorderFactory.createTitledBorder("Account"));

        setSize(800,600);
        JLabel label1 , label2, label3 , label4, label5;
        JTextField t1, t2, t3,t4;
        //JComboBox day, month, year;
        //JRadioButton AccUser, AccAdmin;
        //JTextArea ta1;
        String[] messageStrings = {"Admin" , "User"};

        //JLabel lbltext = new JLabel();
        JCheckBox terms;
        JButton submit;
        JLabel msg;


        label1 = new JLabel("First Name");
        label1.setBounds(30, 50, 100, 20);

        t1 = new JTextField();
        t1.setBounds(140 , 50, 100 , 20);

        label2 = new JLabel("Last Name");
        label2.setBounds(30 , 80, 100 , 20);

        t2 = new JTextField();
        t2.setBounds(140 , 80, 100 , 20);

        label3 = new JLabel("Email");
        label3.setBounds(30 , 110, 100 , 20);

        t3 = new JTextField();
        t3.setBounds(140 , 110, 100 , 20);

        label4 = new JLabel("Unit");
        label4.setBounds(30 , 140, 100 , 20);

        t4 = new JTextField();
        t4.setBounds(140 , 140, 100 , 20);

        label5 = new JLabel("Access Level");
        label5.setBounds(30 , 170, 100 , 20);

        //AccUser  = new JRadioButton("User");
        //AccAdmin = new JRadioButton("Admin");
        //AccUser.setBounds(140 , 170, 100 , 20);
        //AccAdmin.setBounds(240 , 170, 100 , 20);
        //AccUser.setSelected(true);

        JComboBox cmbMessageList = new JComboBox(messageStrings);
        cmbMessageList.setBounds(140 , 170 , 100, 20);

        terms = new JCheckBox("Please Accept that the " +
                "details you have entered are correct");
        terms.setBounds(30 , 200, 400 , 20);

        submit = new JButton("Submit");
        submit.setBounds(30 , 230, 100 , 20);

        msg = new JLabel("");
        msg.setBounds(140 , 230, 100 , 20);

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
        //accountPanel.add(AccUser);
        //accountPanel.add(AccAdmin);
        accountPanel.add(cmbMessageList);
        accountPanel.add(terms);
        accountPanel.add(submit);
        accountPanel.add(msg);

        mainContent.add(accountPanel, "3");
    }

    public void setupAssetsPanel() {
        assetsPanel = new JPanel(new BorderLayout(0, 0));
        assetsPanel.setBorder(BorderFactory.createTitledBorder("Assets"));
        mainContent.add(assetsPanel, "4");
        AssetsTable table = new AssetsTable(assetsPanel, allAssets, allTrades);
    }

    public void setupMyListingsPanel() {
        myListingsPanel = new JPanel(new GridLayout(0, 2, 10, 20));
        myListingsPanel.setBorder(BorderFactory.createTitledBorder("My Listings"));

        MyListingsTableBuy buyTable = new MyListingsTableBuy(myListingsPanel);
        MyListingsTableSell sellTable = new MyListingsTableSell(myListingsPanel);

        mainContent.add(myListingsPanel, "5");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnSrcTxt = e.getActionCommand();

        if (btnSrcTxt.equals("Buy")) {
            cardLayout.show(mainContent, "1");
        } else if (btnSrcTxt.equals("Sell")) {
            cardLayout.show(mainContent, "2");
        } else if (btnSrcTxt.equals("Account")) {
            cardLayout.show(mainContent, "3");
        } else if (btnSrcTxt.equals("View Assets")) {
            refreshGUI();
            cardLayout.show(mainContent, "4");
        } else if (btnSrcTxt.equals("Logout")) {
            setVisible(false);
            new LoginGUI(allUsers, userLoggedIn, org, allAssets, allTrades);
        } else if (btnSrcTxt.equals("My Listings")) {
            cardLayout.show(mainContent, "5");
        }

    }
}

