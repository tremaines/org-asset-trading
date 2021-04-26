package Client.gui;

import Client.Assets;
import Client.Organisation;
import Client.Trades;
import Client.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;


public class MyListingsTableBuy extends JFrame {
    JTable buyTable;

    private Assets assets;
    private Trades trades;
    private User user;
    private User userLoggedIn;
    private Organisation organisation;

    // Trade type (Buy/Sell)
    private static final int tradeType = 0;
    // Type of asset
    private static final int assetType = 1;
    // Quantity of the asset
    private static final int assetAmount = 2;
    // Cost per asset unit
    private static final int price = 3;
    // Original asset amount
    private static final int originalAmount = 4;
    // Username
    private static final int userName = 5;
    // Name of the organisation
    private static final int orgName = 6;
    // Trade fulfilled status (Yes/No/Partial/Cancelled)
    private static final int tradeFulfilled = 7;
    // Time of the listing
    private static final int time = 8;

    public MyListingsTableBuy(JPanel panel, Assets assets, Trades trades,
                              User userLoggedIn, User user, Organisation organisation) {
        setLayout(new FlowLayout());

        this.assets = assets;
        this.trades = trades;
        this.userLoggedIn = userLoggedIn;
        this.user = user;
        this.organisation = organisation;

        // Row data in the table
        Object tableData[] = new Object[3];

        DefaultTableModel model = new DefaultTableModel();

        // Assets Table
        JTable assetsTable = new JTable(model);

        // Column names
        model.addColumn("Trade ID");
        model.addColumn("Asset Name");
        model.addColumn("Quantity");
        model.addColumn("Price");

        buyTable = new JTable(model);
        buyTable.setDefaultEditor(Object.class, null);
        buyTable.setFillsViewportHeight(true);

        // Row data in the table
        // Adds a row for each Asset type
        for(int i = 0; i < trades.getListing().size() ; i++) {
            ArrayList<String> currentListing = trades.getListing().get(i + 1);
            if ((currentListing.get(userName) == userLoggedIn.getUsername()) && (currentListing.get(tradeType) == "Buy")) {
                if (currentListing.get(tradeFulfilled) == "No" || currentListing.get(tradeFulfilled) == "Partial") {
                    model.addRow(new Object[]{i+1, currentListing.get(assetType),
                            currentListing.get(assetAmount), currentListing.get(price)});
                }
            }
        }

        JScrollPane scrollPane = new JScrollPane(buyTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Buy Orders"));
        panel.add(scrollPane);
    }
}
