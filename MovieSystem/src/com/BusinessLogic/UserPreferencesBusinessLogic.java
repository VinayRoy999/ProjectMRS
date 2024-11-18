package com.BusinessLogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UserPreferencesBusinessLogic {

    // Validate preference level (should be between 1 and 5)
    public boolean isValidPreferenceLevel(int preferenceLevel) {
        return preferenceLevel >= 1 && preferenceLevel <= 5;
    }

    // Check if user already has a preference for the given genre
    public boolean isPreferenceUniqueForUser(Connection connection, int userId, int genreId) {
        String query = "SELECT COUNT(*) FROM UserPreferences WHERE UserID = ? AND GenreID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, genreId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (Exception e) {
            System.out.println("Error checking user preference uniqueness: " + e.getMessage());
        }
        return false;
    }

    // Update user preferences
    public void updatePreferences(Connection connection, Scanner scanner) {
        System.out.print("Enter your user ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter genre ID: ");
        int genreId = scanner.nextInt();
        System.out.print("Enter preference level (1-5): ");
        int preferenceLevel = scanner.nextInt();
        
        if (isValidPreferenceLevel(preferenceLevel)) {
            if (isPreferenceUniqueForUser(connection, userId, genreId)) {
                String query = "INSERT INTO UserPreferences (UserID, GenreID, PreferenceLevel) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                    pstmt.setInt(1, userId);
                    pstmt.setInt(2, genreId);
                    pstmt.setInt(3, preferenceLevel);
                    pstmt.executeUpdate();
                    System.out.println("Preference updated.");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("You already have a preference for this genre.");
            }
        } else {
            System.out.println("Invalid preference level.");
        }
    }
}
