package Client.gui;

import Client.TradeHistory;
import Client.Trades;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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

    public SellHistoryTable(JPanel panel, Trades trades, String assetName) {
        this.trades = trades;

        tradeHistory = new TradeHistory(trades);

        // Row data in the table
        Object tableData[] = new Object[2];

        DefaultTableModel model = new DefaultTableModel();

        // Assets Table
        assetHistoryTable = new JTable(model);

        // Column names
        model.addColumn("Amount Sold");
        model.addColumn("Price");

        // Adds a row for each Asset type
        for(int i = 1; i < tradeHistory.getSummaryTradeHistory().size() + 1; i++) {
            if(tradeHistory.getSummaryTradeHistory().get(i).get(assetType) == assetName) {
                model.addRow(new Object[]{tradeHistory.getSummaryTradeHistory().get(i).get(soldAmount),
                        tradeHistory.getSummaryTradeHistory().get(i).get(price)});
            }
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


