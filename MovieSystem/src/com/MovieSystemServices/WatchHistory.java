package com.MovieSystemServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class WatchHistory {

    public void manageWatchHistory(Connection connection, Scanner scanner) {
        System.out.println("\n=== Watch History Management ===");
        System.out.println("1. Add Watch History");
        System.out.println("2. View User's Watch History");
        System.out.println("3. Remove Watch History");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: addWatchHistory(connection, scanner); break;
            case 2: viewUserWatchHistory(connection, scanner); break;
            case 3: removeWatchHistory(connection, scanner); break;
            default: System.out.println("Invalid choice.");
        }
    }

    public void addWatchHistory(Connection connection, Scanner scanner) {
        System.out.print("Enter UserID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter MovieID: ");
        int movieId = scanner.nextInt();
        String query = "INSERT INTO WatchHistory (UserID, MovieID) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, movieId);
            pstmt.executeUpdate();
            System.out.println("Watch history added.");
        } catch (Exception e) {
            System.out.println("Error adding watch history: " + e.getMessage());
        }
    }

    public void viewUserWatchHistory(Connection connection, Scanner scanner) {
        System.out.print("Enter UserID to view watch history: ");
        int userId = scanner.nextInt();
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
    

    public void removeWatchHistory(Connection connection, Scanner scanner) {
        System.out.print("Enter WatchID to remove: ");
        int watchId = scanner.nextInt();
        String query = "DELETE FROM WatchHistory WHERE WatchID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, watchId);
            pstmt.executeUpdate();
            System.out.println("Watch history removed.");
        } catch (Exception e) {
            System.out.println("Error removing watch history: " + e.getMessage());
        }
    }
}
