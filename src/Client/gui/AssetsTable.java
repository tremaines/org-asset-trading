package Client.gui;

import javax.swing.*;
import java.awt.*;


public class AssetsTable extends JFrame {

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

    public AssetsTable(JPanel panel) {
        // Assets Table
        JTable assetsTable = new JTable(data, columnNames);
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
