package org.connectfour.view;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    public View() {
        // Set the title of the window
        setTitle("Connect Four");

        // Initialize and set the layout for the game board and other components
        setLayout(new BorderLayout());
        setSize(400, 400);  // Window size, you can change it later
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add components to the view (empty for now)
        // You will add buttons, labels, or a game board later

        // Example: A label in the center (remove or replace this with the game board later)
        JLabel label = new JLabel("Connect Four Game", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }

    // Later you will add methods for updating the UI based on game state
}