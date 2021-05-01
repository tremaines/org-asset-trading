package Client.gui;

import Client.Units;
import Client.User;
import Server.TradeDBSource;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;


public class MyListingsTableBuy extends JFrame {
    JTable buyTable;

    private User userLoggedIn;
    private Units unit;
    private TradeDBSource tdb;

//    // Trade type (Buy/Sell)
//    private static final int tradeType = 0;
//    // Type of asset
//    private static final int assetType = 1;
//    // Quantity of the asset
//    private static final int assetAmount = 2;
//    // Cost per asset unit
//    private static final int price = 3;
//    // Original asset amount
//    private static final int originalAmount = 4;
//    // Username
//    private static final int userName = 5;
//    // Name of the organisation
//    private static final int orgName = 6;
//    // Trade fulfilled status (Yes/No/Partial/Cancelled)
//    private static final int tradeFulfilled = 7;
//    // Time of the listing
//    private static final int time = 8;

    public MyListingsTableBuy(JPanel panel, Units unit, User userLoggedIn, TradeDBSource tdb) {
        setLayout(new FlowLayout());

        this.userLoggedIn = userLoggedIn;
        this.unit = unit;
        this.tdb = tdb;

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

        // Trades as hashmap
        HashMap<Integer, String[]> trades = tdb.getTradesByUnit(unit.getUnitID(), "buy");

        // Row data in the table
        // Adds a row for each Asset type
        for(Integer trade : trades.keySet()) {
            model.addRow(new Object[]{trade.toString(),
                    trades.get(trade)[0],
                    trades.get(trade)[1],
                    trades.get(trade)[2]});
        }


        JScrollPane scrollPane = new JScrollPane(buyTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Buy Orders"));
        panel.add(scrollPane);
    }

    public JTable getBuyTable() {
        return buyTable;
    }
}
