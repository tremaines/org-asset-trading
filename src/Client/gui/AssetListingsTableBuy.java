package Client.gui;

import Client.ServerAPI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;


public class AssetListingsTableBuy extends JFrame {
    JTable buyTable;

    public AssetListingsTableBuy(JPanel panel, String assetName, ServerAPI server) {
        setLayout(new FlowLayout());

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

        HashMap<Integer, String[]> buyTrades = server.getByTypeAndAsset("buy", assetName);

        // Row data in the table
        // Adds a row for each Asset type
        for(Integer trade: buyTrades.keySet()) {
            model.addRow(new Object[]{trade.toString(),
                    buyTrades.get(trade)[0],
                    buyTrades.get(trade)[1],
                    buyTrades.get(trade)[2]});
        }


        JScrollPane scrollPane = new JScrollPane(buyTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Current " + assetName +  " Buy " +
                "Listings"));
        panel.add(scrollPane);
    }
}
