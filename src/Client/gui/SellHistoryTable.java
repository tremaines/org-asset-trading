package Client.gui;

import Client.ServerAPI;
import Client.TradeHistory;
import Client.Trades;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class SellHistoryTable extends JFrame {

    JTable assetHistoryTable;
    Trades trades;
    TradeHistory tradeHistory;

    // Type of asset
    private static final int assetType = 0;
    // Sold amount for a sell listing
    private static final int soldAmount = 1;
    // Cost per asset unit
    private static final int price = 2;
    // Time of the listing
    private static final int time = 3;

    public SellHistoryTable(JPanel panel, String assetName, ServerAPI server) {

        // Row data in the table
        Object tableData[] = new Object[2];

        DefaultTableModel model = new DefaultTableModel();

        // Assets Table
        assetHistoryTable = new JTable(model);

        // Column names
        model.addColumn("Amount Sold");
        model.addColumn("Price");

        ArrayList<int[]> values = server.getHistory(assetName);

        // Adds a row for each Asset type
        for(int[] value : values) {
            model.addRow(new Object[]{value[0], value[1]});
        }


        JScrollPane scrollPane = new JScrollPane(assetHistoryTable);
        assetHistoryTable.setAutoCreateRowSorter(true);
        assetHistoryTable.setFillsViewportHeight(true);


        panel.add(scrollPane);
    }

    public JTable getSellHistoryTable() {
        return assetHistoryTable;
    }

}


