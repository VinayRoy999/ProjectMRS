package com.BusinessLogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class WatchHistoryBusinessLogic {

    // Check if a user has already watched the movie
    public boolean hasUserWatchedMovie(Connection connection, int userId, int movieId) {
        String query = "SELECT COUNT(*) FROM WatchHistory WHERE UserID = ? AND MovieID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, movieId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            System.out.println("Error checking watch history: " + e.getMessage());
        }
        return false;
    }

    // View specific user's watch history
    public void viewUserWatchHistory(Connection connection, int userId) {
        String query = "SELECT * FROM WatchHistory WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("WatchID: " + rs.getInt("WatchID") + 
                                       ", MovieID: " + rs.getInt("MovieID") + 
                                       ", WatchDate: " + rs.getTimestamp("WatchDate"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving watch history: " + e.getMessage());
        }
    }

    // Method to manage watch history (view, delete, etc.)
    public void manageWatchHistory(Connection connection, Scanner scanner) {
        System.out.println("Managing Watch History...");
        // Implement options for managing watch history (viewing, deleting, etc.)
    }
}
