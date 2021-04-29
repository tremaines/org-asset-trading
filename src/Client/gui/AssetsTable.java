package Client.gui;

import Client.Assets;
import Client.Trades;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AssetsTable extends JFrame {

    private Assets assets;
    private Trades allTrades;

    public AssetsTable(JPanel panel, Assets assets, Trades trades) {
        this.assets = assets;
        this.allTrades = trades;

        // Row data in the table
        Object tableData[] = new Object[3];

        DefaultTableModel model = new DefaultTableModel();

        // Assets Table
        JTable assetsTable = new JTable(model);

        // Column names
        model.addColumn("Asset Type");
        model.addColumn("Quantity Available");
        model.addColumn("Lowest Price per Unit");

        // Adds a row for each Asset type
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
        itemHistoryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String btnSrcTxt = e.getActionCommand();
                if (btnSrcTxt.equals("Item History")) {

                }
            }
        });

        JPanel topTableBar = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,15));
        panel.add(topTableBar, BorderLayout.NORTH);
        topTableBar.add(itemHistoryBtn);
        panel.add(scrollPane);
    }
}
