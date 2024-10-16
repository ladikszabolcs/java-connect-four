package org.connectfour.view;

import org.connectfour.controller.Controller;
import org.connectfour.storage.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame {

    private int numRows; // Number of rows from settings
    private int numCols; // Number of columns from settings
    private String playerName; // The player's name
    private JButton[][] boardButtons; // Button grid to represent the board
    private JPanel gamePanel; // Panel for the game grid
    private Controller controller; // Reference to the controller
    private DatabaseManager dbManager; // Database manager for loading/saving settings

    public View() {
        // Initialize database manager and load settings
        dbManager = new DatabaseManager();
        String[] settings = dbManager.loadSettings();
        numRows = Integer.parseInt(settings[0]);
        numCols = Integer.parseInt(settings[1]);

        // Set the title of the window
        setTitle("Connect Four");

        // Initialize and set the layout for the game board and other components
        setLayout(new BorderLayout());
        setSize(600, 600);  // Window size, you can change it later
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create the "Game" menu
        JMenu fileMenu = new JMenu("Game");

        // Create "Settings", "New", and "Save" menu items
        JMenuItem settingsMenuItem = new JMenuItem("Settings");
        JMenuItem newMenuItem = new JMenuItem("New Game");
        JMenuItem saveMenuItem = new JMenuItem("Save Game");

        // Add menu items to the "Game" menu
        fileMenu.add(settingsMenuItem);
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);

        // Add "Game" menu to the menu bar
        menuBar.add(fileMenu);

        // Set the menu bar for the frame
        setJMenuBar(menuBar);

        // Add action listener for Settings menu item
        settingsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSettingsWindow();
            }
        });

        // Add action listener for New Game menu item
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame(); // Reset and update the game board when "New Game" is clicked
            }
        });

        // Add action listener for Save Game menu item
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveGameState(); // Save the current game state
                JOptionPane.showMessageDialog(View.this, "Game state saved!");
            }
        });

        // Initialize the game panel with the default grid size
        gamePanel = new JPanel();
        updateGameBoard();
        add(gamePanel, BorderLayout.CENTER);
    }

    // Method to open the settings window (pop-up)
    private void openSettingsWindow() {
        // Create a new JFrame for the settings window
        JFrame settingsFrame = new JFrame("Settings");

        // Set the size and close operation of the settings window
        settingsFrame.setSize(300, 200);
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set the layout
        settingsFrame.setLayout(new GridLayout(4, 2));

        // Add labels and spinners for the number of rows and columns
        JLabel rowsLabel = new JLabel("Rows (4-12):");
        JSpinner rowsSpinner = new JSpinner(new SpinnerNumberModel(numRows, 4, 12, 1)); // Spinner for rows with range 4-12
        JLabel colsLabel = new JLabel("Columns (4-12):");
        JSpinner colsSpinner = new JSpinner(new SpinnerNumberModel(numCols, 4, 12, 1)); // Spinner for columns with range 4-12

        // Add label and input field for the player name
        JLabel nameLabel = new JLabel("Player Name:");
        JTextField nameField = new JTextField(playerName, 12); // Pre-fill with the current player name (max length 12)

        // Add Save button for applying changes
        JButton saveButton = new JButton("Save");

        // Add action listener for the save button to apply the new matrix size
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numRows = (int) rowsSpinner.getValue();
                numCols = (int) colsSpinner.getValue();
                // Validate and sanitize the player name
                String enteredName = nameField.getText().trim().toLowerCase();
                if (isValidName(enteredName)) {
                    playerName = enteredName;
                } else {
                    JOptionPane.showMessageDialog(settingsFrame, "Invalid name. Use only letters, no spaces, max 12 characters.");
                    return;
                }
                dbManager.saveSettings(numRows, numCols, playerName); // Save settings to the database
                JOptionPane.showMessageDialog(settingsFrame, "Settings saved successfully!");
                settingsFrame.dispose(); // Close the settings window
                updateGameBoard(); // Update the game board with the new grid size
                controller.resetGame(); // Reset game because of settings change
            }
        });

        // Add components to the settings frame
        settingsFrame.add(rowsLabel);
        settingsFrame.add(rowsSpinner);
        settingsFrame.add(colsLabel);
        settingsFrame.add(colsSpinner);
        settingsFrame.add(nameLabel);
        settingsFrame.add(nameField);
        settingsFrame.add(new JLabel());  // Empty label for spacing
        settingsFrame.add(saveButton);

        // Set the window to be visible
        settingsFrame.setVisible(true);
    }

    // Method to update the game board based on the current numRows and numCols
    private void updateGameBoard() {
        // Remove the current panel and re-create the grid
        if (gamePanel != null) {
            remove(gamePanel);
        }

        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(numRows, numCols));
        boardButtons = new JButton[numRows][numCols];

        // Add buttons for each cell in the grid
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                JButton cellButton = new JButton();
                cellButton.setPreferredSize(new Dimension(50, 50)); // Size of each cell
                cellButton.setBackground(Color.WHITE); // Default background color
                cellButton.addActionListener(new ColumnListener(col)); // Action listener for moves
                boardButtons[row][col] = cellButton;
                gamePanel.add(cellButton);
            }
        }

        // Add the updated game panel to the main window
        add(gamePanel, BorderLayout.CENTER);

        // Refresh the window to reflect changes
        revalidate();
        repaint();
    }

    // Method to reset the game board
    private void resetGame() {
        if (controller != null) {
            controller.resetGame();
        }
        updateGameBoard();
    }

    // Method to update the game board with the new state
    public void updateBoard(int[][] boardState) {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (boardState[row][col] == 1) {
                    boardButtons[row][col].setBackground(Color.YELLOW); // Player's move
                } else if (boardState[row][col] == 2) {
                    boardButtons[row][col].setBackground(Color.RED); // Computer's move
                } else {
                    boardButtons[row][col].setBackground(Color.WHITE); // Empty
                }
            }
        }
    }

    // Method to disable the board when the game is over
    public void disableBoard() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                boardButtons[row][col].setEnabled(false); // Disable all buttons
            }
        }
    }

    // Method to set the controller reference
    public void setController(Controller controller) {
        this.controller = controller;
    }

    // Listener class to handle user moves
    private class ColumnListener implements ActionListener {
        private int col;

        public ColumnListener(int col) {
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (controller != null) {
                controller.playerMove(col); // Pass the column to the controller for player move
            }
        }
    }

    // Getters for number of rows and columns (can be used by the Controller or Model)
    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public String getPlayerName() {
        return playerName;
    }

    // Helper method to sanitize and validate the player name
    private boolean isValidName(String name) {
        return name.matches("^[a-zA-Z]{1,12}$"); // Only letters, no spaces, 1-12 characters
    }
}