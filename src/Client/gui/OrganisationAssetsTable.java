package Client.gui;

import Client.Assets;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OrganisationAssetsTable extends JFrame {

    JTable assetsTable;
    Assets assets;

    public OrganisationAssetsTable(JPanel panel, int rows) {
        this.assets = assets;

        // Row data in the table
        Object tableData[] = new Object[3];

        //DefaultTableModel model = new DefaultTableModel();

        DefaultTableModel model = new DefaultTableModel() {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return column == 1;
//            }
        };

        // Assets Table
        assetsTable = new JTable(model);

        // Column names
        model.addColumn("Asset");
        model.addColumn("Amount");

        // Adds a row for each Asset type
        // Make the "Asset" fields blank (to be filled by admin) and the quantity default to 0
        for(int i = 0; i < rows; i++) {
            model.addRow(new Object[]{
                    "",
                    0});
        }

        JScrollPane scrollPane = new JScrollPane(assetsTable);
        assetsTable.setAutoCreateRowSorter(true);
        assetsTable.setFillsViewportHeight(true);

        // Make cells uneditable
        //assetsTable.setEditingColumn(1);

        JPanel topTableBar = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,15));
        panel.add(topTableBar, BorderLayout.NORTH);
        panel.add(scrollPane);
    }

    public JTable getAssetsTable() {
        return assetsTable;
    }

}


