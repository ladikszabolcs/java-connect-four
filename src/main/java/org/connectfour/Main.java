package org.connectfour;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Set intellij light theme using FlatLaf
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            Model model = new Model();
            View view = new View();
            Controller controller = new Controller(model, view);
            view.setVisible(true);
        });
    }
}