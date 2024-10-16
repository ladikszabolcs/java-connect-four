package org.connectfour.controller;

import org.connectfour.model.Model;
import org.connectfour.view.View;

import javax.swing.*;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        // Initialize event listeners for the View
        view.setController(this);
    }

    // Method for handling player's turn
    public void playerMove(int col) {
        if (!model.isPlayerTurn()) return;

        // Player makes a move
        if (model.dropPiece(col, 1)) {
            view.updateBoard(model.getBoard());

            // Check if player wins
            if (model.checkWin(1)) {
                JOptionPane.showMessageDialog(view, "Player wins!");
                view.disableBoard();
                return;
            }

            model.setPlayerTurn(false); // Switch turn to the computer

            // Check if the board is full before computer moves
            if (model.isBoardFull()) {
                JOptionPane.showMessageDialog(view, "It's a draw!");
                return;
            }

            // Computer's turn (random move)
            computerMove();
        } else {
            JOptionPane.showMessageDialog(view, "Column is full! Choose another one.");
        }
    }

    // Computer makes a random move
    public void computerMove() {
        SwingUtilities.invokeLater(() -> {
            int col = model.computerMove();
            view.updateBoard(model.getBoard());

            // Check if computer wins
            if (model.checkWin(2)) {
                JOptionPane.showMessageDialog(view, "Computer wins!");
                view.disableBoard();
                return;
            }

            model.setPlayerTurn(true); // Switch turn back to the player
        });
    }
    // Method to reset the game
    public void resetGame() {
        // Reset the model (clearing the board and setting player turn)
        model = new Model(view.getNumRows(), view.getNumCols());

        // Reset the view (to reinitialize the buttons)
        view.updateBoard(model.getBoard());
    }
}