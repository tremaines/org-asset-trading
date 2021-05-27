package Client.gui;

import Client.Assets;
import Client.ServerAPI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OrganisationAssetsTable extends JFrame {

    JTable assetsTable;

    public OrganisationAssetsTable(JPanel panel, ServerAPI server) {

        // Row data in the table
        Object tableData[] = new Object[3];

        //DefaultTableModel model = new DefaultTableModel();

        DefaultTableModel model = new DefaultTableModel() {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return column == 1;
//            }
        };

        Assets[] assets = server.getAllAssets();
        // Assets Table
        assetsTable = new JTable(model);

        // Column names
        model.addColumn("ID");
        model.addColumn("Asset");
        model.addColumn("Amount");

        // Adds a row for each Asset type
        // Make the "Asset" fields blank (to be filled by admin) and the quantity default to 0
        for(int i = 0; i < assets.length; i++) {
            model.addRow(new Object[]{
                    assets[i].getAssetID(),
                    assets[i].getAssetName(),
                    0});
        }

        JScrollPane scrollPane = new JScrollPane(assetsTable);
        assetsTable.setAutoCreateRowSorter(true);
        assetsTable.setFillsViewportHeight(true);

        // Make cells uneditable
        //assetsTable.setEditingColumn(1);

//        JPanel topTableBar = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,0));
//        panel.add(topTableBar, BorderLayout.NORTH);
        panel.add(scrollPane);
    }

    public JTable getAssetsTable() {
        return assetsTable;
    }

}


