package Client.gui;

import Client.Assets;
import Client.ServerAPI;
import Client.Units;
import Client.User;
import Server.AssetDBSource;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserAssetsTable extends JFrame {

    JTable table;
    Units unit;
    User userLoggedIn;
    AssetDBSource adb;

    public UserAssetsTable(JPanel panel, Units unit, User userLoggedIn, ServerAPI server) {
        this.userLoggedIn = userLoggedIn;
        this.unit = unit;
        this.adb = adb;

        // Row data in the table
        Object tableData[] = new Object[3];

        DefaultTableModel model = new DefaultTableModel();

        // Assets Table
        JTable assetsTable = new JTable(model);

        // Column names
        model.addColumn("Asset");
        model.addColumn("Amount");

        Assets[] assets = server.getAllAssetsOwned(unit.getUnitID());

        // Adds a row for each Asset type
        for(Assets asset : assets) {
            model.addRow(new Object[]{asset.getAssetName(), asset.getQuantity()});
        }

            JScrollPane scrollPane = new JScrollPane(assetsTable);
        assetsTable.setAutoCreateRowSorter(true);
        assetsTable.setFillsViewportHeight(true);

        // Make cells uneditable
        assetsTable.setDefaultEditor(Object.class, null);

        JPanel topTableBar = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,15));
        panel.add(topTableBar, BorderLayout.NORTH);
        panel.add(scrollPane);
    }

}


