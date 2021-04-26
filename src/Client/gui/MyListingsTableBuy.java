package Client.gui;

import javax.swing.*;
import java.awt.*;


public class MyListingsTableBuy extends JFrame {
    JTable buyTable;

    public MyListingsTableBuy(JPanel panel) {
        setLayout(new FlowLayout());

        String[] columnNames = {"Asset Name", "Quantity"};

        // Row data in the table
        Object[][] data = {
                {"Software Lincenses", "100"},
                {"Desktops", "5"},
                {"Computational Resources", "75"}
        };


        buyTable = new JTable(data, columnNames);
        buyTable.setDefaultEditor(Object.class, null);
        buyTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(buyTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Buy Orders"));
        panel.add(scrollPane);
    }
}
