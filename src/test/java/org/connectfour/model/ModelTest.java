package org.connectfour.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private Model model;

    @BeforeEach
    void setUp() {
        model = new Model(6, 7); // Standard Connect Four grid size
    }

    @Test
    void testDropPieceValid() {
        assertTrue(model.dropPiece(0, 1)); // Dropping a piece in a valid column should return true
        assertEquals(1, model.getBoard()[5][0]); // The bottom of the column should now contain player's piece (1)
    }

    @Test
    void testDropPieceFullColumn() {
        // Fill a column
        for (int i = 0; i < 6; i++) {
            assertTrue(model.dropPiece(0, 1));
        }
        assertFalse(model.dropPiece(0, 1)); // Now the column is full, and should return false
    }

    @Test
    void testHorizontalWin() {
        // Simulate horizontal win
        model.dropPiece(0, 1);
        model.dropPiece(1, 1);
        model.dropPiece(2, 1);
        model.dropPiece(3, 1);
        assertTrue(model.checkWin(1)); // Player should win horizontally
    }

    @Test
    void testVerticalWin() {
        // Simulate vertical win
        model.dropPiece(0, 1);
        model.dropPiece(0, 1);
        model.dropPiece(0, 1);
        model.dropPiece(0, 1);
        assertTrue(model.checkWin(1)); // Player should win vertically
    }

    @Test
    void testDiagonalWin() {
        // Simulate diagonal win (bottom-left to top-right)
        model.dropPiece(0, 1); // First column
        model.dropPiece(1, 2); model.dropPiece(1, 1); // Second column
        model.dropPiece(2, 2); model.dropPiece(2, 2); model.dropPiece(2, 1); // Third column
        model.dropPiece(3, 2); model.dropPiece(3, 2); model.dropPiece(3, 2); model.dropPiece(3, 1); // Fourth column
        assertTrue(model.checkWin(1)); // Player should win diagonally
    }

    @Test
    void testBoardIsFull() {
        // Fill the entire board
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                model.dropPiece(col, 1);
            }
        }
        assertTrue(model.isBoardFull()); // The board should be full now
    }
}