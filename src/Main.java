package src;

import javax.swing.SwingUtilities;
import src.view.QueensGUI;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QueensGUI gui = new QueensGUI();
            gui.setVisible(true);
        });
    }
}
