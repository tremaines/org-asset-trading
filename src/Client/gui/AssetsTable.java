package Client.gui;

import Client.Assets;
import Client.Trades;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class AssetsTable extends JFrame {

    private Assets allAssets;
    private Trades allTrades;

    // Dummy data to test table

    String[] columnNames = {
            "Asset Type",
            "Description",
            "Quantity Available"
    };

    Object[][] data = {
            {"CPU Hours", "CPU Hours",
                    "700"},
            {"CPU Hours", "CPU Hours",
                    "500"},
            {"CPU Hours", "CPU Hours",
                    "1200"},
            {"CPU Hours", "CPU Hours",
                    "300"},
            {"CPU Hours", "CPU Hours",
                    "150"},
    };

    public AssetsTable(JPanel panel, Assets assets, Trades trades) {
        this.allAssets = assets;
        this.allTrades = trades;

        Object tableData[] = new Object[3];
        DefaultTableModel model = new DefaultTableModel();

        // Assets Table
        //JTable assetsTable = new JTable(data, columnNames);
        JTable assetsTable = new JTable(model);

        model.addColumn("Asset Type");
        model.addColumn("Quantity Available");
        model.addColumn("Lowest Price per Unit");

        for(int i = 0; i < assets.getAllAssets().size(); i++) {
            model.addRow(new Object[]{assets.getAllAssets().get(i),
                    trades.getAssetQuantity(assets.getAllAssets().get(i)),
                    trades.getLowestPrice(assets.getAllAssets().get(i))});
        }


        JScrollPane scrollPane = new JScrollPane(assetsTable);
        assetsTable.setAutoCreateRowSorter(true);

        // Make cells uneditable
        assetsTable.setDefaultEditor(Object.class, null);

        JButton itemHistoryBtn = new JButton("Item History");
        JPanel topTableBar = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,15));
        panel.add(topTableBar, BorderLayout.NORTH);
        topTableBar.add(itemHistoryBtn);
        panel.add(scrollPane);
    }
}
