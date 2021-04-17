package Client.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Point of entry for the GUI of the program
 */

public class AssetTradingGUI extends JFrame {

    public AssetTradingGUI() {
        super("Asset Trading");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 700));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AssetTradingGUI();
    }
}

