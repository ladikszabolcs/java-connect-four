package org.connectfour.model;

import java.util.Random;

public class Model {
    private int[][] board; // 0 = empty, 1 = player (yellow), 2 = computer (red)
    private int numRows;
    private int numCols;
    private boolean playerTurn; // True if it's the player's turn, false for the computer

    public Model(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.board = new int[numRows][numCols];
        this.playerTurn = true; // Player starts first
    }

    // Method to drop a piece into a column
    public boolean dropPiece(int col, int player) {
        for (int row = numRows - 1; row >= 0; row--) {
            if (board[row][col] == 0) {
                board[row][col] = player;
                return true;
            }
        }
        return false; // Column is full
    }

    // Check for a win condition
    public boolean checkWin(int player) {
        // Check horizontal, vertical, and diagonal wins
        return checkHorizontalWin(player) || checkVerticalWin(player) || checkDiagonalWin(player);
    }

    private boolean checkHorizontalWin(int player) {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols - 3; col++) {
                if (board[row][col] == player && board[row][col + 1] == player &&
                        board[row][col + 2] == player && board[row][col + 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVerticalWin(int player) {
        for (int row = 0; row < numRows - 3; row++) {
            for (int col = 0; col < numCols; col++) {
                if (board[row][col] == player && board[row + 1][col] == player &&
                        board[row + 2][col] == player && board[row + 3][col] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalWin(int player) {
        // Check for diagonal from bottom-left to top-right
        for (int row = 3; row < numRows; row++) {
            for (int col = 0; col < numCols - 3; col++) {
                if (board[row][col] == player && board[row - 1][col + 1] == player &&
                        board[row - 2][col + 2] == player && board[row - 3][col + 3] == player) {
                    return true;
                }
            }
        }

        // Check for diagonal from top-left to bottom-right
        for (int row = 0; row < numRows - 3; row++) {
            for (int col = 0; col < numCols - 3; col++) {
                if (board[row][col] == player && board[row + 1][col + 1] == player &&
                        board[row + 2][col + 2] == player && board[row + 3][col + 3] == player) {
                    return true;
                }
            }
        }

        return false;
    }

    // Simulate the computer's turn (random drop)
    public int computerMove() {
        Random rand = new Random();
        int col;
        do {
            col = rand.nextInt(numCols); // Random column
        } while (!dropPiece(col, 2)); // Try to drop a piece, repeat if column is full
        return col; // Return the column where the piece was dropped
    }

    public boolean isBoardFull() {
        for (int col = 0; col < numCols; col++) {
            if (board[0][col] == 0) {
                return false;
            }
        }
        return true;
    }

    public int[][] getBoard() {
        return board;
    }

    // Set the current board state (used when loading from database)
    public void setBoard(int[][] board) {
        this.board = board;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }
}