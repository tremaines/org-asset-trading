package Client.gui;

import Client.Organisation;
import Client.Units;
import Client.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserAssetsTable extends JFrame {

    JTable table;
    Units unit;
    User userLoggedIn;

    public UserAssetsTable(JPanel panel, Organisation organisation, User userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
        this.unit = userLoggedIn.getUnit();

        // Row data in the table
        Object tableData[] = new Object[3];

        DefaultTableModel model = new DefaultTableModel();

        // Assets Table
        JTable assetsTable = new JTable(model);

        // Column names
        model.addColumn("Asset");
        model.addColumn("Amount");

        Organisation userOrganisation = org.getOrganisation(userLoggedIn.getOrganisationName());
        int numberOfAssetsOwned = userOrganisation.getAssets().size();

        // Adds a row for each Asset type
        for(int i = 0; i < numberOfAssetsOwned; i++) {
            model.addRow(new Object[]{
                    userOrganisation.getAssets().get(i),
                    userOrganisation.getAmounts().get(i).toString()});
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


