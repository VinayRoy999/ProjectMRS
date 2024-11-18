package com.MovieSystemServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UserPreferences {

    public void manageUserPreferences(Connection connection, Scanner scanner) {
        System.out.println("\n=== User Preferences Management ===");
        System.out.println("1. Insert User Preference");
        System.out.println("2. Update User Preference");
        System.out.println("3. Delete User Preference");
        System.out.println("4. View All User Preferences");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: insertUserPreference(connection, scanner); break;
            case 2: updateUserPreference(connection, scanner); break;
            case 3: deleteUserPreference(connection, scanner); break;
            case 4: getAllUserPreferences(connection); break;
            default: System.out.println("Invalid choice.");
        }
    }

    public void insertUserPreference(Connection connection, Scanner scanner) {
        System.out.print("Enter UserID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter GenreID: ");
        int genreId = scanner.nextInt();
        System.out.print("Enter Preference Level (1 to 5): ");
        int preferenceLevel = scanner.nextInt();

        String query = "INSERT INTO UserPreferences (UserID, GenreID, PreferenceLevel) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, genreId);
            pstmt.setInt(3, preferenceLevel);
            pstmt.executeUpdate();
            System.out.println("User preference inserted.");
        } catch (Exception e) {
            System.out.println("Error inserting user preference: " + e.getMessage());
        }
    }

    public void updateUserPreference(Connection connection, Scanner scanner) {
        System.out.print("Enter PreferenceID to update: ");
        int preferenceId = scanner.nextInt();
        System.out.print("Enter new Preference Level (1 to 5): ");
        int newPreferenceLevel = scanner.nextInt();

        String query = "UPDATE UserPreferences SET PreferenceLevel = ? WHERE PreferenceID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, newPreferenceLevel);
            pstmt.setInt(2, preferenceId);
            pstmt.executeUpdate();
            System.out.println("User preference updated.");
        } catch (Exception e) {
            System.out.println("Error updating user preference: " + e.getMessage());
        }
    }

    public void deleteUserPreference(Connection connection, Scanner scanner) {
        System.out.print("Enter PreferenceID to delete: ");
        int preferenceId = scanner.nextInt();

        String query = "DELETE FROM UserPreferences WHERE PreferenceID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, preferenceId);
            pstmt.executeUpdate();
            System.out.println("User preference deleted.");
        } catch (Exception e) {
            System.out.println("Error deleting user preference: " + e.getMessage());
        }
    }

    public void getAllUserPreferences(Connection connection) {
        String query = "SELECT * FROM UserPreferences";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("PreferenceID: " + rs.getInt("PreferenceID") + ", UserID: " + rs.getInt("UserID") + 
                                   ", GenreID: " + rs.getInt("GenreID") + ", Preference Level: " + rs.getInt("PreferenceLevel"));
            }
        } catch (Exception e) {
            System.out.println("Error retrieving user preferences: " + e.getMessage());
        }
    }
}
