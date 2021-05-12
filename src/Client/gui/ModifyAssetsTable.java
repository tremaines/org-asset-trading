package Client.gui;

import Client.Assets;
import Client.Organisation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ModifyAssetsTable extends JFrame {

    JTable assetsTable;
    Assets assets;

    public ModifyAssetsTable(JPanel panel, Assets assets, Organisation org) {
        this.assets = assets;

        // Row data in the table
        Object tableData[] = new Object[3];

        //DefaultTableModel model = new DefaultTableModel();

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
        };

        // Assets Table
        assetsTable = new JTable(model);

        // Column names
        model.addColumn("Asset");
        model.addColumn("Amount");

        // Adds a row for each Asset type
        for(int i = 0; i < assets.getAllAssets().size(); i++) {
            model.addRow(new Object[]{
                    assets.getAllAssets().get(i),
                    org.getAssetsAmounts(assets.getAllAssets().get(i))});
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


