package Client.gui;

import javax.swing.*;
import java.awt.*;


public class MyListingsTableSell extends JFrame {
    JTable sellTable;

    public MyListingsTableSell(JPanel panel) {
        setLayout(new FlowLayout());

        String[] columnNames = {"Asset Name", "Quantity"};

        // Row data in the table
        Object[][] data = {
                {"Software Lincenses", "100"},
                {"Desktops", "5"},
                {"Computational Resources", "75"}
        };


        sellTable = new JTable(data, columnNames);
        sellTable.setDefaultEditor(Object.class, null);
        sellTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(sellTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Sell Orders"));
        panel.add(scrollPane);
    }
}
