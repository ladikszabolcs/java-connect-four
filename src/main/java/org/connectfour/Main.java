package org.connectfour;

import com.formdev.flatlaf.FlatIntelliJLaf;
import org.connectfour.controller.Controller;
import org.connectfour.model.Model;
import org.connectfour.view.View;

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
            // Create the View first to get the number of rows and columns
            View view = new View();

            // Create the Model with the row and column sizes from the View
            Model model = new Model(view.getNumRows(), view.getNumCols());

            // Create the Controller and link it to the Model and View
            Controller controller = new Controller(model, view);

            // Show the view
            view.setVisible(true);
        });
    }
}