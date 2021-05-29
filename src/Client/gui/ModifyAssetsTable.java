package Client.gui;

import Client.Assets;
import Client.ServerAPI;
import Client.Units;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ModifyAssetsTable extends JFrame {

    JTable assetsTable;
    Assets[] assets;

    public ModifyAssetsTable(JPanel panel, Units unit, ServerAPI server) {
        this.assets = server.getOwnedAndUnownedAssets(unit.getUnitID());

        // Row data in the table
        Object tableData[] = new Object[3];

        //DefaultTableModel model = new DefaultTableModel();

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        // Assets Table
        assetsTable = new JTable(model);

        // Column names
        model.addColumn("Asset ID");
        model.addColumn("Asset Name");
        model.addColumn("Amount");

        // Adds a row for each Asset type
        for(Assets asset : assets) {
            model.addRow(new Object[]{
                    asset.getAssetID(),
                    asset.getAssetName(), asset.getQuantity()});
        }

        JScrollPane scrollPane = new JScrollPane(assetsTable);
        assetsTable.setAutoCreateRowSorter(true);
        assetsTable.setFillsViewportHeight(true);

        // Make column one editable
        assetsTable.setEditingColumn(1);

//        JPanel topTableBar = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,0));
//        panel.add(topTableBar, BorderLayout.NORTH);
        panel.add(scrollPane);
    }

    public JTable getModifyTable() {
        return assetsTable;
    }

}


