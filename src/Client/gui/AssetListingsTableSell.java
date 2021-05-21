package Client.gui;

import Client.ServerAPI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;


public class AssetListingsTableSell extends JFrame {
    JTable sellTable;


    public AssetListingsTableSell(JPanel panel, String assetName, ServerAPI server) {
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

        sellTable = new JTable(model);
        sellTable.setDefaultEditor(Object.class, null);
        sellTable.setFillsViewportHeight(true);

        HashMap<Integer, String[]> sellTrades = server.getByTypeAndAsset("sell", assetName);

        // Row data in the table
        // Adds a row for each Asset type
        for(Integer trade: sellTrades.keySet()) {
            model.addRow(new Object[]{trade.toString(),
                    sellTrades.get(trade)[0],
                    sellTrades.get(trade)[1],
                    sellTrades.get(trade)[2]});
        }


        JScrollPane scrollPane = new JScrollPane(sellTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Current " + assetName +  " Sell " +
                "Listings"));
        panel.add(scrollPane);
    }
}
