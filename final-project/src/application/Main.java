package application;

import gui.MenuGUI;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuGUI().setVisible(true));
    }
}
