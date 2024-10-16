package org.connectfour.storage;

import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:connectfour.db";

    public DatabaseManager() {
        // Initialize database and create tables if they don't exist
        try (Connection conn = connect()) {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                // Create settings table
                stmt.execute("CREATE TABLE IF NOT EXISTS settings (" +
                        "id INTEGER PRIMARY KEY, " +
                        "num_rows INTEGER, " +
                        "num_cols INTEGER, " +
                        "playername TEXT CHECK (LENGTH(player_name) <= 12))");
                stmt.execute("CREATE TABLE IF NOT EXISTS game_state (id INTEGER PRIMARY KEY, board TEXT)");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Save game settings
    public void saveSettings(int numRows, int numCols, String playerName) {
        String sql = "INSERT OR REPLACE INTO settings (id, num_rows, num_cols, playername) VALUES (1, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, numRows);
            pstmt.setInt(2, numCols);
            pstmt.setString(3, playerName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Load game settings
    public String[] loadSettings() {
        String sql = "SELECT num_rows, num_cols, playername FROM settings WHERE id = 1";
        String[] settings = {String.valueOf(6), String.valueOf(7), ""}; // default values

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                settings[0] = String.valueOf(rs.getInt("num_rows"));
                settings[1] = String.valueOf(rs.getInt("num_cols"));
                settings[2] = rs.getString("playername");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return settings;
    }

    // Save game state (board)
    public void saveGameState(String boardState) {
        String sql = "INSERT OR REPLACE INTO game_state (id, board) VALUES (1, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, boardState);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Load game state (board)
    public String loadGameState() {
        String sql = "SELECT board FROM game_state WHERE id = 1";
        String boardState = "";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                boardState = rs.getString("board");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return boardState;
    }
}