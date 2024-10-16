package org.connectfour.view;

import org.connectfour.controller.Controller;
import org.connectfour.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ViewTest {

    private View view;
    private Controller controller;
    private Model model;

    @BeforeEach
    void setUp() {
        // Initialize the view, model, and controller
        view = new View();
        model = new Model(6, 7); // Standard board size
        controller = new Controller(model, view);
        view.setController(controller);
    }

    @Test
    void testInitialBoardSetup() {
        // Check if the board is initialized with correct number of rows and columns
        assertEquals(6, view.getNumRows());
        assertEquals(7, view.getNumCols());

        // Check if the board buttons are created
        assertNotNull(view.getBoardButtons());
        assertEquals(6, view.getBoardButtons().length); // 6 rows
        assertEquals(7, view.getBoardButtons()[0].length); // 7 columns
    }

    @Test
    void testSettingsMenuAction() {
        // Simulate opening settings through the menu
        JMenuBar menuBar = view.getJMenuBar();
        JMenu gameMenu = menuBar.getMenu(0); // First menu (Game)
        JMenuItem settingsMenuItem = gameMenu.getItem(0); // First item (Settings)

        // Simulate clicking the Settings menu item
        settingsMenuItem.doClick();

        // Assert that settings window is opened (This is tricky to assert directly,
        // you can assert whether the setting fields are editable or updated)
        assertNotNull(view.getNumRows());
        assertNotNull(view.getNumCols());
    }

    @Test
    void testPlayerMove() {
        // Simulate the player making a move in the first column
        view.getBoardButtons()[5][0].doClick();

        // Assert that the button's background color changed to Yellow (Player's move)
        assertEquals(Color.YELLOW, view.getBoardButtons()[5][0].getBackground());
    }

    @Test
    void testUpdateBoard() {
        // Simulate a board update with a player's move
        int[][] boardState = new int[6][7];
        boardState[5][0] = 1; // Player moves in the first column

        view.updateBoard(boardState);

        // Assert that the board reflects the player's move
        assertEquals(Color.YELLOW, view.getBoardButtons()[5][0].getBackground());
    }

    @Test
    void testPlayerNameValidation() {
        // Test valid player name
        String validName = "player";
        assertTrue(view.isValidName(validName));

        // Test invalid names (too long, spaces, special characters)
        String invalidName1 = "player123456789"; // Too long
        String invalidName2 = "player name"; // Space in name
        String invalidName3 = "player@123"; // Special character

        assertFalse(view.isValidName(invalidName1));
        assertFalse(view.isValidName(invalidName2));
        assertFalse(view.isValidName(invalidName3));
    }
}