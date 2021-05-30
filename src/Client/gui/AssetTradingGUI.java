package Client.gui;

import Client.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
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
    private JPanel createPanel;
    private JPanel modifyPanel;
    private JPanel accountPanel;
    private JPanel assetsPanel;
    private JPanel myListingsPanel;
    private JPanel sellHistoryPanel;
    private JPanel allListingsPanel;
    private JPanel assetListingPanel;

    // Top menu components
    private JPanel topMenuContainer;
    private JPanel topMenuRight;
    private JPanel topMenuLeft;

    // Tables
    private ModifyAssetsTable modifyAssetsTable;
    private JPanel tableBordered;
    private JPanel tableBorder;

    // Side menu components
    private JPanel sideMenuTop;
    private JPanel sideMenuBottom;

    private CardLayout cardLayout = new CardLayout();

    private int tableTradeID;
    private String assetName = null;

    // The unit the logged in user belongs to
    private Units unit;
    private User userLoggedIn;

    // Server connection
    private static final ServerAPI server = new ServerAPI();

    public AssetTradingGUI(User user) {

        super("Asset Trading");

        this.unit = server.getUnit(user.getUnit());
        this.userLoggedIn = user;

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
        setupCreatePanel();
        setupModifyPanel();
        setupAllListingsPanel();
        setupSellHistoryPanel();
        setupAssetListingPanel();


        if(userLoggedIn.getBuyNotificationStatus()) {
            JOptionPane.showMessageDialog(null, "One of your buy orders was recently " +
                            "partially or fully completed",
                    "Buy Order Update", JOptionPane.INFORMATION_MESSAGE);
            userLoggedIn.setNotificationStatus("Buy", false);
            server.updateUser(userLoggedIn);
        } else if(userLoggedIn.getSellNotificationStatus()) {
            JOptionPane.showMessageDialog(null, "One of your sell orders was recently " +
                            "partially or fully completed",
                    "Sell Order Update", JOptionPane.INFORMATION_MESSAGE);
            userLoggedIn.setNotificationStatus("Sell", false);
            server.updateUser(userLoggedIn);
        }

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
        unit = server.getUnit(userLoggedIn.getUnit());
        addTopMenu();
        setupBuyPanel();
        setupSellPanel();
        setupAssetsPanel();
        setupMyListingsPanel();
        setupAccountPanel();
        setupCreatePanel();
        setupModifyPanel();
        setupAllListingsPanel();
        setupSellHistoryPanel();
        setupAssetListingPanel();

        userLoggedIn = server.getUser(userLoggedIn.getUsername());

        if(userLoggedIn.getBuyNotificationStatus()) {
            JOptionPane.showMessageDialog(null, "One of your buy orders was recently " +
                            "partially or fully completed",
                    "Buy Order Update", JOptionPane.INFORMATION_MESSAGE);
            userLoggedIn.setNotificationStatus("Buy", false);
            server.updateUser(userLoggedIn);
        } else if(userLoggedIn.getSellNotificationStatus()) {
            JOptionPane.showMessageDialog(null, "One of your sell orders was recently " +
                            " partially or completed",
                    "Sell Order Update", JOptionPane.INFORMATION_MESSAGE);
            userLoggedIn.setNotificationStatus("Sell", false);
            server.updateUser(userLoggedIn);
        }
    }

    public static void main(String[] args) throws UserException, TradesException, AssetsException {
        new LoginGUI(server);
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

        // Set initial icon and then change it in loop below for other buttons
        ImageIcon icon = new ImageIcon(this.getClass().getResource("images/bell.png"));

        // Create buttons for top menu
        if(userLoggedIn.getAdminStatus() == true) {
            for (int i = 0; i < 5; i++) {
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
                    case 3:
                        btnName = "Create";
                        icon = new ImageIcon(this.getClass().getResource("images/plus.png"));
                        break;
                    case 4:
                        btnName = "Modify";
                        icon = new ImageIcon(this.getClass().getResource("images/modify.png"));
                        break;
                }
                JButton btn = new JButton(btnName, icon);
                btn.setBorderPainted(false);
                btn.setBackground(Utility.PRIMARYBLUE);
                btn.setForeground(Color.WHITE);
                btn.setPreferredSize(new Dimension(130, 30));
                btn.addActionListener(this);
                topMenuRight.add(btn);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                String btnName = "";
                switch (i) {
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
        }

        JLabel creditsLabel =
                new JLabel("Credits: [" + unit.getCredits() + "]");
        creditsLabel.setPreferredSize(new Dimension(100, 30));
        creditsLabel.setForeground(Color.WHITE);
        topMenuRight.add(creditsLabel);
    }

    public void addSideMenu() {
        // Panel setup
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

        // Set initial icon and then change it in loop below for other buttons
        ImageIcon icon = new ImageIcon(this.getClass().getResource("images/bell.png"));

        // Create buttons for side menu
        for (int i = 0; i < 6; i++) {
            String btnName = "";
            switch(i) {
                case 0:
                    btnName = "Sell History";
                    icon = new ImageIcon(this.getClass().getResource("images/sellhistory.png"));
                    break;
                case 1:
                    btnName = "My Listings";
                    icon = new ImageIcon(this.getClass().getResource("images/mylistings.png"));
                    break;
                case 2:
                    btnName = "Summary";
                    icon = new ImageIcon(this.getClass().getResource("images/viewassets.png"));
                    break;
                case 3:
                    btnName = "Logout";
                    icon = new ImageIcon(this.getClass().getResource("images/logout.png"));
                    break;
                case 4:
                    btnName = "All Listings";
                    icon = new ImageIcon(this.getClass().getResource("images/alllistings.png"));
                    break;
                case 5:
                    btnName = "Assets List";
                    icon = new ImageIcon(this.getClass().getResource("images/assetlistings.png"));
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
        // Panel setup
        buyPanel = new JPanel(new BorderLayout(0, 0));
        buyPanel.setBorder(BorderFactory.createTitledBorder("Buy"));
        setSize(1000, 700);

        // Panel elements
        JLabel label1 , label2, label3;
        JSpinner spinner2, spinner3;
        JCheckBox terms;
        JButton submit;
        JLabel msg;
        JComboBox assetTypeList = new JComboBox(server.getAssetNames());
        label1 = new JLabel("Asset Type");
        label2 = new JLabel("Amount");
        label3 = new JLabel("Cost Per Unit");
        spinner2 = new JSpinner();
        spinner3 = new JSpinner();
        terms = new JCheckBox("Please accept that the details you have entered are correct");
        submit = new JButton("Submit");
        msg = new JLabel("");

        // Position elements
        label1.setBounds(30, 50, 100, 20);
        label2.setBounds(30 , 80, 100 , 20);
        label3.setBounds(30 , 110, 100 , 20);
        assetTypeList.setBounds(140 , 50, 150 , 20);
        spinner2.setBounds(140 , 80, 150 , 20);
        spinner3.setBounds(140 , 110, 150 , 20);
        terms.setBounds(27 , 143, 400 , 20);
        submit.setBounds(30 , 180, 100 , 20);
        msg.setBounds(140 , 230, 100 , 20);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = (String) assetTypeList.getSelectedItem();
                String amount = spinner2.getValue() + "";
                String price = spinner3.getValue() + "";
                boolean boxSelected = terms.isSelected();

                // Checks if the amount and price values are positive and non-zero
                if(Integer.parseInt(amount) > 0 && Integer.parseInt(price) > 0) {

                    // If the checkbox is selected
                    if(boxSelected) {
                        Trades newTrade;
                        try {
                            newTrade = new Trades(Trades.TradeType.buy, userLoggedIn.getUsername(),
                                    server.getAsset(type).getAssetID(), Integer.parseInt(amount), Integer.parseInt(price));
                            server.addTrade(newTrade);
                            JOptionPane.showMessageDialog(null, "Buy order was placed!",
                                    "Successful", JOptionPane.INFORMATION_MESSAGE);
                            refreshGUI();
                        } catch (TradesException tradesException) {
                            JOptionPane.showMessageDialog(null, "Not enough credits!"
                                    , "Error", JOptionPane.ERROR_MESSAGE);
                            tradesException.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "You have not accepted " +
                                        "the terms. Please select the checkbox.", "Checkbox Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid entry: Please enter a positive "
                                    + "non-zero number for both Amount and Cost", "Checkbox Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add elements to panel so they are visible
        buyPanel.add(label1);
        buyPanel.add(label2);
        buyPanel.add(spinner2);
        buyPanel.add(label3);
        buyPanel.add(spinner3);
        buyPanel.add(assetTypeList);
        buyPanel.add(terms);
        buyPanel.add(submit);
        buyPanel.add(msg);

        // Display buy panel in card layout
        mainContent.add(buyPanel, "1");

    }

    public void setupSellPanel() {
        // Panel setup
        JPanel sellPanelContainer = new JPanel(new GridLayout(1, 2));
        JPanel leftPanel = new JPanel(new BorderLayout(0, 0));
        JPanel rightPanel = new JPanel(new BorderLayout(0, 0));
        rightPanel.setBorder(new EmptyBorder(0,0,30,30));
        sellPanelContainer.add(leftPanel);
        sellPanelContainer.add(rightPanel);
        sellPanelContainer.setBorder(BorderFactory.createTitledBorder("Sell"));

        // Panel elements
        JLabel label1 , label2, label3 , label4, label5;
        JSpinner spinner1, spinner2;
        JCheckBox terms;
        JButton submit;
        JLabel msg;
        label1 = new JLabel("Asset Type");
        label2 = new JLabel("Amount");
        label3 = new JLabel("Cost Per Unit");
        JComboBox assetOwnedList =
                new JComboBox(server.getAssetsByUnit(unit.getUnitID()));
        spinner1 = new JSpinner();
        spinner2 = new JSpinner();
        terms = new JCheckBox("Please confirm that the details you have entered are correct");
        submit = new JButton("Submit");
        msg = new JLabel("");

        // Position Elements
        label1.setBounds(30, 50, 100, 20);
        label2.setBounds(30 , 80, 100 , 20);
        label3.setBounds(30 , 110, 100 , 20);
        assetOwnedList.setBounds(140 , 50, 150 , 20);
        spinner1.setBounds(140 , 80, 150 , 20);
        spinner2.setBounds(140 , 110, 150 , 20);
        terms.setBounds(27 , 143, 400 , 20);
        submit.setBounds(30 , 180, 100 , 20);
        msg.setBounds(140 , 230, 100 , 20);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = (String) assetOwnedList.getSelectedItem();
                String amount = spinner1.getValue() + "";
                String price = spinner2.getValue() + "";
                boolean boxSelected = terms.isSelected();

                // Checks if the amount and price values are positive and non-zero
                if(Integer.parseInt(amount) > 0 && Integer.parseInt(price) > 0) {

                    // If the checkbox is selected
                    if(boxSelected) {
                        try {
                            Trades newTrade;
                            newTrade = new Trades(Trades.TradeType.sell, userLoggedIn.getUsername(),
                                    server.getAsset(type).getAssetID(), Integer.parseInt(amount), Integer.parseInt(price));
                            server.addTrade(newTrade);
                            refreshGUI();
                            cardLayout.show(mainContent, "2");
                            JOptionPane.showMessageDialog(null, "Sell order was placed!",
                                    "Successful", JOptionPane.INFORMATION_MESSAGE);
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

        // Row data in the table
        Object tableData[] = new Object[2];

        UserAssetsTable userAssetsTable = new UserAssetsTable(rightPanel, unit, userLoggedIn, server);

        // Add elements to panel so they are visible
        leftPanel.add(label1);
        leftPanel.add(label2);
        leftPanel.add(spinner1);
        leftPanel.add(label3);
        leftPanel.add(spinner2);
        leftPanel.add(assetOwnedList);
        leftPanel.add(terms);
        leftPanel.add(submit);
        leftPanel.add(msg);

        // Display sell panel in card layout
        mainContent.add(sellPanelContainer, "2");
    }

    public void setupAccountPanel() {
        // Panel setup
        accountPanel = new JPanel(new BorderLayout(0, 0));
        accountPanel.setBorder(BorderFactory.createTitledBorder("Account"));
        setSize(1000, 700);

        // Panel elements
        JLabel label1 , label2, label4, label5, label6, label7;
        JTextField t1;
        JPasswordField userPassword, newPasswordInput, confirmPasswordInput;
        String[] messageStrings = {"User", "Admin"};
        String[] unitNames = server.getUnitNames();
        JCheckBox terms;
        JButton submit, changePassword;
        JLabel msg;

        label1 = new JLabel("Username");
        label2 = new JLabel("Password");
        label4 = new JLabel("Unit");
        label5 = new JLabel("Access Level");
        label6 = new JLabel("New Password");
        label7 = new JLabel("Confirm Password");
        t1 = new JTextField();
        userPassword = new JPasswordField();
        JComboBox units = new JComboBox(unitNames);
        newPasswordInput = new JPasswordField();
        confirmPasswordInput = new JPasswordField();
        changePassword = new JButton("Change");
        submit = new JButton("Submit");
        JComboBox cmbMessageList = new JComboBox(messageStrings);
        terms = new JCheckBox("Please accept that the " +
                "details you have entered are correct");

        // Position elements
        label1.setBounds(30, 50, 100, 20);
        label2.setBounds(30 , 80, 100 , 20);
        label4.setBounds(30 , 110, 100 , 20);
        label5.setBounds(30 , 140, 100 , 20);
        label6.setBounds(30 , 340, 100 , 20);
        label7.setBounds(30 , 370, 130 , 20);
        t1.setBounds(140 , 50, 100 , 20);
        units.setBounds(140 , 110, 100 , 20);
        userPassword.setBounds(140 , 80, 100 , 20);
        newPasswordInput.setBounds(160 , 340, 100 , 20);
        confirmPasswordInput.setBounds(160 , 370, 100 , 20);
        changePassword.setBounds(30 , 400, 100 , 20);
        submit.setBounds(30 , 200, 100 , 20);
        cmbMessageList.setBounds(140 , 140 , 100, 20);
        terms.setBounds(30 , 170, 400 , 20);

        // Submit new user
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = t1.getText();
                String password = userPassword.getText();
                String unit = units.getSelectedItem().toString().trim();
                String userType = cmbMessageList.getSelectedItem().toString();
                boolean boxSelected = terms.isSelected();
                boolean admin = userType.trim() == messageStrings[1];

                if (admin && !unit.equals("IT Administration")) {
                    JOptionPane.showMessageDialog(null, "Only IT Admin staff can be admins!",
                            "Invalid Unit For Admin", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (boxSelected) {
                        try {
                            // Initialise new user object
                            User newUser = new User(null, null, null, username, password,
                                    admin, server.getUnit(unit).getUnitID());
                            // Check username doesn't exist already
                            if (server.checkUser(newUser.getUsername())) {
                                JOptionPane.showMessageDialog(null, "This username already exists" +
                                                " in the system.",
                                        "Username Error", JOptionPane.ERROR_MESSAGE);
                                throw new UserException("Username already exists!");

                            } else {
                                server.addUser(newUser);
                                JOptionPane.showMessageDialog(null, username +
                                        " was added to the " + unit + " organisational unit!",
                                        "User added", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } catch (UserException userException) {
                            userException.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "The terms box was not selected",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        changePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = newPasswordInput.getText();
                String confirmPassword = confirmPasswordInput.getText();
                if (newPassword.length() != 0 && confirmPassword.length() != 0) {
                    if (newPassword.equals(confirmPassword)) {
                        JOptionPane.showMessageDialog(null, "Password has been changed",
                                "Successful", JOptionPane.INFORMATION_MESSAGE);
                        userLoggedIn.setPassword(User.hashPassword(newPassword));
                        server.updateUser(userLoggedIn);
                    } else {
                        JOptionPane.showMessageDialog(null, "Passwords don't match!",
                                "Invalid", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Password fields cannot be empty",
                            "Invalid", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        msg = new JLabel("");
        msg.setBounds(140 , 230, 100 , 20);

        if(userLoggedIn.getAdminStatus() == false) {
            label6.setBounds(30, 50, 130, 20);
            accountPanel.add(label6);
            label7.setBounds(30, 80, 130, 20);
            accountPanel.add(label7);
            newPasswordInput.setBounds(160 , 50, 100 , 20);
            accountPanel.add(newPasswordInput);
            accountPanel.add(confirmPasswordInput);
            confirmPasswordInput.setBounds(160, 80, 100, 20);
            accountPanel.add(changePassword);
            changePassword.setBounds(30 , 110, 100 , 20);
        } else {
            accountPanel.add(label1);
            accountPanel.add(t1);
            accountPanel.add(label2);
            accountPanel.add(userPassword);
            accountPanel.add(label4);
            accountPanel.add(units);
            accountPanel.add(label5);
            accountPanel.add(label6);
            accountPanel.add(label7);
            accountPanel.add(newPasswordInput);
            accountPanel.add(confirmPasswordInput);
            accountPanel.add(changePassword);
            accountPanel.add(cmbMessageList);
            accountPanel.add(terms);
            accountPanel.add(submit);
        }
        accountPanel.add(msg);
        mainContent.add(accountPanel, "3");
    }

    public void setupAssetsPanel() {
        assetsPanel = new JPanel(new BorderLayout(0, 0));
        assetsPanel.setBorder(BorderFactory.createTitledBorder("Asset Summary"));
        mainContent.add(assetsPanel, "4");
        AssetsTable table = new AssetsTable(assetsPanel, server);
    }

    public void setupMyListingsPanel() {
        myListingsPanel = new JPanel(new BorderLayout());
        myListingsPanel.setBorder(BorderFactory.createTitledBorder("My Listings"));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,15));
        JPanel gridPanel = new JPanel(new GridLayout(1, 2, 10, 20));
        JButton cancelOrderBtn = new JButton("Cancel Order");
        topPanel.add(cancelOrderBtn);

        MyListingsTableBuy buyTable = new MyListingsTableBuy(gridPanel, unit, userLoggedIn, server);
        MyListingsTableSell sellTable = new MyListingsTableSell(gridPanel, unit, userLoggedIn, server);

        JTable buyTableClick = buyTable.getBuyTable();
        JTable sellTableClick = sellTable.getSellTable();

        buyTableClick.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JTable table = (JTable)e.getSource();
                int row = table.rowAtPoint(e.getPoint());

                if(row != -1) {
                    tableTradeID = Integer.parseInt(table.getModel().getValueAt(row, 0) + "");
                }
            }
        });

        sellTableClick.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JTable table = (JTable)e.getSource();
                int row = table.rowAtPoint(e.getPoint());

                if(row != -1) {
                    tableTradeID = Integer.parseInt(table.getModel().getValueAt(row, 0) + "");
                }
            }
        });

        cancelOrderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get trades object
                Trades tradeToCancel = server.getTrade(tableTradeID);
                // Pass to cancel trade method
                server.cancelTrade(tradeToCancel);
                refreshGUI();
                cardLayout.show(mainContent, "5");
                JOptionPane.showMessageDialog(null, "Order was successfully cancelled",
                        "Order Cancelled", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        myListingsPanel.add(topPanel, BorderLayout.NORTH);
        myListingsPanel.add(gridPanel);

        mainContent.add(myListingsPanel, "5");
    }

    public void setupCreatePanel() {
        JPanel createPanelContainer = new JPanel(new GridLayout(1, 2));
        JPanel leftPanel = new JPanel(new BorderLayout(0, 0));
        JPanel rightPanel = new JPanel(new BorderLayout(0, 0));
        rightPanel.setBorder(new EmptyBorder(30,0,30,30));

        createPanelContainer.add(leftPanel);
        createPanelContainer.add(rightPanel);
        createPanelContainer.setBorder(BorderFactory.createTitledBorder("Create"));

        setSize(1000, 700);
        JLabel label1 , label2, label3, label4;
        JSpinner s1;
        JTextField t1, t2, t3;
        JCheckBox terms, terms2;
        JButton submitOrg, submitAsset;
        JLabel msg;


        // Create organisation elements

        label1 = new JLabel("Organisational Unit Name");
        label1.setBounds(30, 50, 150, 20);

        t1 = new JTextField();
        t1.setBounds(220 , 50, 150 , 20);

        label2 = new JLabel("Credits");
        label2.setBounds(30 , 80, 100 , 20);

        s1 = new JSpinner();
        s1.setBounds(220 , 80, 150 , 20);

        JComponent editor = s1.getEditor();
        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)editor;
        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);

        terms = new JCheckBox("Please confirm that the " +
                "details you have entered are correct");
        terms.setBounds(30 , 110, 400 , 20);

        terms2 = new JCheckBox("Please confirm that the " +
                "details you have entered are correct");
        terms2.setBounds(30 , 260, 400 , 20);


        submitOrg = new JButton("Create");
        submitOrg.setBounds(30 , 140, 100 , 20);

        msg = new JLabel("");
        msg.setBounds(140 , 180, 100 , 20);

        OrganisationAssetsTable organisationAssetsTable = new OrganisationAssetsTable(rightPanel, server);
        // Create Asset elements

        label3 = new JLabel("Asset Name");
        label3.setBounds(30, 230, 150, 20);

        t2 = new JTextField();
        t2.setBounds(220 , 230, 150 , 20);


        submitAsset = new JButton("Create Asset");
        submitAsset.setBounds(30 , 290, 140 , 20);

        organisationAssetsTable.getAssetsTable().putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        submitOrg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Units newUnit;
                String unitName = t1.getText();
                String credits = s1.getValue() + "";
                boolean boxSelected = terms.isSelected();


                String[] unitNames = server.getUnitNames();

                if(boxSelected) {
                    // Check credits are >= 0, if not set to 0 by default
                    int intCredits = Math.max(Integer.parseInt(credits), 0);

                    newUnit = new Units(unitName, intCredits);
                    // Check if unit already exists
                    if(!Arrays.asList(unitNames).contains(unitName)) {
                        server.addUnit(newUnit);
                        // Get the unit ID generated by the database
                        int newUnitId = server.getUnit(newUnit.getUnitName()).getUnitID();

                        // Add unit assets
                        int assetID;
                        int assetQty;
                        for (int i = 0; i < organisationAssetsTable.getAssetsTable().getRowCount(); i++) {
                            assetID = Integer.parseInt(organisationAssetsTable.getAssetsTable()
                                    .getValueAt(i, 0).toString());
                            // If the user has entered a non-numerical value, default to 0
                            try{
                                assetQty = Integer.parseInt(organisationAssetsTable.getAssetsTable()
                                        .getValueAt(i, 2).toString());
                            } catch (NumberFormatException err) {
                                assetQty = 0;
                            }

                            // Check if asset qty >= 0, if not set to 0 as default
                            assetQty = Math.max(assetQty, 0);

                            server.addAssetOwned(assetID, newUnitId, assetQty, false);
                        }
                        JOptionPane.showMessageDialog(null,
                                unitName + " has been added as a new organisation"  , "Successful",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "An organisation with the same name " +
                                        "has already been created.", "Organisation Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    } else {
                    JOptionPane.showMessageDialog(null, "You have not accepted " +
                                    "the terms. Please select the checkbox.", "Checkbox Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        submitAsset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newAssetName = t2.getText();
                String[] assetNames = server.getAssetNames();
                boolean boxSelected = terms2.isSelected();

                if(boxSelected) {
                    if(Arrays.asList(assetNames).contains(newAssetName)) {
                        JOptionPane.showMessageDialog(null, "An asset with this name already exists " +
                                        "in the system", "Asset Creation Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        Assets newAsset = new Assets();
                        newAsset.setAssetName(newAssetName);
                        server.addAsset(newAsset);

                        refreshGUI();
                        cardLayout.show(mainContent, "6");

                        JOptionPane.showMessageDialog(null,
                                newAssetName+ " has been added as a new asset"  , "Successful",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "You have not accepted " +
                                    "the terms. Please select the checkbox.", "Checkbox Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        leftPanel.add(label1);
        leftPanel.add(label2);
        leftPanel.add(label3);
        leftPanel.add(t1);
        leftPanel.add(t2);
        leftPanel.add(s1);
        leftPanel.add(terms);
        leftPanel.add(terms2);
        leftPanel.add(submitOrg);
        leftPanel.add(submitAsset);
        leftPanel.add(msg);

        mainContent.add(createPanelContainer, "6");
    }

    public void setupModifyPanel() {
        JPanel modifyPanelContainer = new JPanel(new GridLayout(1, 2));
        JPanel leftPanel = new JPanel(new BorderLayout(0, 0));
        JPanel rightPanel = new JPanel(new BorderLayout(0, 0));
        rightPanel.setBorder(new EmptyBorder(30, 0, 30, 30));

        String[] unitNames = server.getUnitNames();
        List<String> sortedNames = new ArrayList(Arrays.asList(unitNames));
//        if(sortedNames.contains("None (Admin)")) {
//            sortedNames.remove("None (Admin)");
//        }
        java.util.Collections.sort(sortedNames);
        unitNames = sortedNames.toArray(new String[0]);

        JComboBox units = new JComboBox(unitNames);
        units.setBounds(220, 40, 150, 20);
        String unitName = units.getSelectedItem().toString();
        Units selectedOrg = server.getUnit(unitName);

        tableBordered = new JPanel(new BorderLayout());
        tableBordered.setBorder(BorderFactory.createTitledBorder(unitName + "'s Assets"));
        rightPanel.add(tableBordered);

        modifyPanelContainer.add(leftPanel);
        modifyPanelContainer.add(rightPanel);
        modifyPanelContainer.setBorder(BorderFactory.createTitledBorder("Modify"));

        setSize(1000, 700);
        JLabel label1, label2;
        JSpinner creditsSpinner;
        JCheckBox terms;
        JButton submit;
        JLabel msg;

        label1 = new JLabel("Select Unit");
        label1.setBounds(30, 40, 150, 20);

        label2 = new JLabel("Set Credits");
        label2.setBounds(30 , 70, 100 , 20);

        creditsSpinner = new JSpinner();
        creditsSpinner.setValue(selectedOrg.getCredits());
        creditsSpinner.setBounds(220 , 70, 150 , 20);

        JComponent editor = creditsSpinner.getEditor();
        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)editor;
        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);

        terms = new JCheckBox("Please confirm that the " +
                "details you have entered are correct");
        terms.setBounds(30 , 100, 400 , 20);

        submit = new JButton("Submit");
        submit.setBounds(30 , 130, 100 , 20);

        msg = new JLabel("");
        msg.setBounds(140 , 180, 100 , 20);

        modifyAssetsTable = new ModifyAssetsTable(tableBordered, server.getUnit(unitName), server);
        modifyAssetsTable.getModifyTable().putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        units.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String unitName = units.getSelectedItem().toString();
                Units selectedUnit = server.getUnit(unitName);

                // Remove old table
                rightPanel.remove(tableBordered);
                rightPanel.revalidate();
                rightPanel.repaint();

                // Show credits for selected organisation
                creditsSpinner.setValue(selectedUnit.getCredits());

                // Add new table
                tableBordered = new JPanel(new BorderLayout());
                rightPanel.add(tableBordered);
                modifyAssetsTable = new ModifyAssetsTable(tableBordered, server.getUnit(unitName), server);
                modifyAssetsTable.getModifyTable().putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

                // Update screen
                rightPanel.revalidate();
                rightPanel.repaint();

                tableBordered.setBorder(BorderFactory.createTitledBorder(unitName + "'s Assets"));
            }
        });


        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String unitName = units.getSelectedItem().toString();
                Units selectedUnit = server.getUnit(unitName);
                int creditsInput = (Integer) creditsSpinner.getValue();
                boolean boxSelected = terms.isSelected();
                boolean hasNegativeInput = false;

                List<Assets> updatedAssets = new ArrayList<>();

                if(creditsInput >= 0) {
                    try {

                        for (int i = 0; i < modifyAssetsTable.getModifyTable().getRowCount(); i++) {
                            int assetId = Integer.parseInt(modifyAssetsTable.getModifyTable().getValueAt(i, 0).toString());
                            String assetName = modifyAssetsTable.getModifyTable().getValueAt(i, 1).toString();
                            int assetQty = Integer.parseInt(modifyAssetsTable.getModifyTable().getValueAt(i, 2).toString());

                            if (assetQty >= 0) {
                                // Asset amount
                                Assets updatedAsset = new Assets(assetId, assetName, assetQty);
                                updatedAssets.add(updatedAsset);
                            }
                            else if (assetQty < 0) {
                                hasNegativeInput = true;

                                JOptionPane.showMessageDialog(null, "Negative numbers are " +
                                                "discarded for asset quantities.",
                                        "Asset Quantity",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                        }

                        if (boxSelected) {
                            for (Assets asset : updatedAssets) {
                                server.addAssetOwned(asset.getAssetID(), selectedUnit.getUnitID(),
                                        asset.getQuantity(), true);
                            }
                            selectedUnit.setCredits(creditsInput);
                            server.updateUnit(selectedUnit);

                            if (hasNegativeInput) {
                                // TODO: Put following code in rerender table function
                                // Rerender table
                                rightPanel.remove(tableBordered);
                                rightPanel.revalidate();
                                rightPanel.repaint();
                                tableBordered = new JPanel(new BorderLayout());
                                rightPanel.add(tableBordered);
                                modifyAssetsTable = new ModifyAssetsTable(tableBordered, unit, server);
                                modifyAssetsTable.getModifyTable().putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
                                rightPanel.revalidate();
                                rightPanel.repaint();
                                tableBordered.setBorder(BorderFactory.createTitledBorder(unitName + "'s Assets"));
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "Organisation details were updated", "Successful",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "You have not accepted " +
                                            "the terms. Please select the checkbox.", "Checkbox Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException nfe) {
                        refreshGUI();
                        JOptionPane.showMessageDialog(null, "Invalid entry for an asset quantity, " +
                                        "please only enter integer values.",
                                "Invalid Entry",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Cannot set credits to a negative number",
                            "Invalid Entry",
                            JOptionPane.ERROR_MESSAGE);
                    creditsSpinner.setValue(selectedUnit.getCredits());
                }
            }
        });

        leftPanel.add(label1);
        leftPanel.add(label2);
        leftPanel.add(units);
        leftPanel.add(creditsSpinner);
        leftPanel.add(terms);
        leftPanel.add(submit);
        leftPanel.add(msg);

        mainContent.add(modifyPanelContainer, "7");
    }

    public void setupAllListingsPanel() {
        allListingsPanel = new JPanel(new BorderLayout());
        allListingsPanel.setBorder(BorderFactory.createTitledBorder("All Listings"));
        JPanel gridPanel = new JPanel(new GridLayout(1, 2, 10, 20));

        AllListingsTableBuy buyTable = new AllListingsTableBuy(gridPanel, server);
        AllListingsTableSell sellTable  = new AllListingsTableSell(gridPanel, server);

        allListingsPanel.add(gridPanel);

        mainContent.add(allListingsPanel, "8");
    }

    private void setupAssetListingPanel() {
        assetListingPanel = new JPanel(new BorderLayout());
        assetListingPanel.setBorder(BorderFactory.createTitledBorder("Assets List"));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,15));
        JPanel gridPanel = new JPanel(new GridLayout(1, 2, 10, 20));

        String[] assetNames = server.getAssetNames();
        assetName = assetName == null ? assetNames[0] : assetName;

        JComboBox assets = new JComboBox(assetNames);
        assets.setBounds(220, 40, 150, 20);

        topPanel.add(assets);

        assets.setSelectedItem(assetName);

        AssetListingsTableBuy buyTable = new AssetListingsTableBuy(gridPanel, assetName, server);

        AssetListingsTableSell sellTable = new AssetListingsTableSell(gridPanel, assetName, server);

        assets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assetName = assets.getSelectedItem().toString();
                assets.setSelectedItem(assetName);
                refreshGUI();
                cardLayout.show(mainContent, "10");
            }
        });

        assetListingPanel.add(topPanel, BorderLayout.NORTH);
        assetListingPanel.add(gridPanel);

        mainContent.add(assetListingPanel, "10");
    }

    public void setupSellHistoryPanel() {
        sellHistoryPanel = new JPanel(new BorderLayout());
        sellHistoryPanel.setBorder(BorderFactory.createTitledBorder("Asset Sell History"));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,15));
        JPanel gridPanel = new JPanel(new GridLayout(1, 2, 10, 20));



        String[] assetNames = server.getAssetNames();
        try {
            assetName = assetName == null ? assetNames[0] : assetName;
        } catch (ArrayIndexOutOfBoundsException e) {
            assetName = "No Assets Found!";
        }


        JComboBox assets = new JComboBox(assetNames);
        assets.setBounds(220, 40, 150, 20);

        topPanel.add(assets);

        assets.setSelectedItem(assetName);

        tableBorder = new JPanel(new BorderLayout());
        tableBorder.setBorder(BorderFactory.createTitledBorder(assetName + " Sales History"));

        SellHistoryTable historyTable = new SellHistoryTable(gridPanel, assetName, server);

        assets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assetName = assets.getSelectedItem().toString();
                assets.setSelectedItem(assetName);
                refreshGUI();
                cardLayout.show(mainContent, "9");
            }
        });


        sellHistoryPanel.add(topPanel, BorderLayout.NORTH);
        sellHistoryPanel.add(gridPanel);
        mainContent.add(sellHistoryPanel, "9");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnSrcTxt = e.getActionCommand();

        if (btnSrcTxt.equals("Buy")) {
            refreshGUI();
            cardLayout.show(mainContent, "1");
        } else if (btnSrcTxt.equals("Sell")) {
            refreshGUI();
            cardLayout.show(mainContent, "2");
        } else if (btnSrcTxt.equals("Account")) {
            refreshGUI();
            cardLayout.show(mainContent, "3");
        } else if (btnSrcTxt.equals("Summary")) {
            refreshGUI();
            cardLayout.show(mainContent, "4");
        } else if (btnSrcTxt.equals("Logout")) {
            setVisible(false);
            new LoginGUI(server);
        } else if (btnSrcTxt.equals("My Listings")) {
            refreshGUI();
            cardLayout.show(mainContent, "5");
        } else if (btnSrcTxt.equals("Create")) {
            refreshGUI();
            cardLayout.show(mainContent, "6");
        } else if (btnSrcTxt.equals("Modify")) {
            refreshGUI();
            cardLayout.show(mainContent, "7");
        } else if (btnSrcTxt.equals("All Listings")) {
            refreshGUI();
            cardLayout.show(mainContent, "8");
        } else if (btnSrcTxt.equals("Sell History")) {
            refreshGUI();
            cardLayout.show(mainContent, "9");
        } else if (btnSrcTxt.equals("Assets List")) {
            refreshGUI();
            cardLayout.show(mainContent, "10");
        }
    }
}

