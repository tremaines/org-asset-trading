package Client.gui;

import Client.ServerAPI;
import Client.Units;
import Client.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;


public class MyListingsTableSell extends JFrame {
    JTable sellTable;

    private User userLoggedIn;
    private Units unit;


    public MyListingsTableSell(JPanel panel, Units unit, User userLoggedIn, ServerAPI server) {
        setLayout(new FlowLayout());

        this.userLoggedIn = userLoggedIn;
        this.unit = unit;

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

        // Row data in the table
        // Adds a row for each Asset type
        // Trades as hashmap
        HashMap<Integer, String[]> trades = server.getTradesByUnit(unit.getUnitID(), "sell");

        // Row data in the table
        // Adds a row for each Asset type
        for(Integer trade : trades.keySet()) {
            model.addRow(new Object[]{trade.toString(),
                    trades.get(trade)[0],
                    trades.get(trade)[1],
                    trades.get(trade)[2]});
        }

        sellTable = new JTable(model);
        sellTable.setDefaultEditor(Object.class, null);
        sellTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(sellTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Sell Orders"));
        panel.add(scrollPane);
    }

    public JTable getSellTable() {
        return sellTable;
    }
}
