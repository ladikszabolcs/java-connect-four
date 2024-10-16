package org.connectfour.controller;

import org.connectfour.model.Model;
import org.connectfour.view.View;
import org.connectfour.storage.DatabaseManager;

import javax.swing.*;

public class Controller {
    private Model model;
    private View view;
    private DatabaseManager dbManager;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.dbManager = new DatabaseManager();

        // Set the controller reference in the view so it can call controller methods
        view.setController(this);
    }

    // Method for handling player's move
    public void playerMove(int col) {
        if (!model.isPlayerTurn()) return;

        // Player makes a move
        if (model.dropPiece(col, 1)) {
            view.updateBoard(model.getBoard());

            // Check if the player has won
            if (model.checkWin(1)) {
                JOptionPane.showMessageDialog(view, "Player wins!");
                dbManager.incrementScore(view.getPlayerName()); // Increment the player's score
                view.disableBoard();
                return;
            }

            // Switch turn to the computer
            model.setPlayerTurn(false);

            // Check if the board is full before computer moves
            if (model.isBoardFull()) {
                JOptionPane.showMessageDialog(view, "It's a draw!");
                view.disableBoard();
                return;
            }

            // Computer makes a random move
            computerMove();
        } else {
            JOptionPane.showMessageDialog(view, "Column is full! Choose another one.");
        }
    }

    // Method for computer's random move
    public void computerMove() {
        SwingUtilities.invokeLater(() -> {
            int col = model.computerMove();
            view.updateBoard(model.getBoard());

            // Check if the computer has won
            if (model.checkWin(2)) {
                JOptionPane.showMessageDialog(view, "Computer wins!");
                view.disableBoard();
                return;
            }

            // Switch turn back to the player
            model.setPlayerTurn(true);
        });
    }

    // Method to reset the game
    public void resetGame() {
        // Reset the model (clearing the board and setting player turn)
        model = new Model(view.getNumRows(), view.getNumCols());

        // Reset the view (to reinitialize the buttons)
        view.updateBoard(model.getBoard());
    }

    // Save the game state (serialize board state)
    public void saveGameState() {
        String boardState = serializeBoard(model.getBoard());
        dbManager.saveGameState(boardState);
    }

    // Load the game state
    public void loadGameState() {
        String boardState = dbManager.loadGameState();
        if (boardState == null || boardState.isEmpty()) {
            // If no valid board state is found, initialize an empty board
            model = new Model(view.getNumRows(), view.getNumCols());
        } else {
            model.setBoard(deserializeBoard(boardState));
        }
        view.updateBoard(model.getBoard());
    }

    // Helper methods for board serialization and deserialization
    private String serializeBoard(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            for (int cell : row) {
                sb.append(cell);
            }
            sb.append(";");
        }
        return sb.toString();
    }

    private int[][] deserializeBoard(String boardState) {
        String[] rows = boardState.split(";");
        int[][] board = new int[rows.length][rows[0].length()]; // assuming rows and columns are correct
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length(); j++) {
                board[i][j] = Character.getNumericValue(rows[i].charAt(j));
            }
        }
        return board;
    }
}