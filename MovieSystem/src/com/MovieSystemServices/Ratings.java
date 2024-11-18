package com.MovieSystemServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Ratings {

    public void manageRatings(Connection connection, Scanner scanner) {
        System.out.println("\n=== Rating Management ===");
        System.out.println("1. Add Rating");
        System.out.println("2. Update Rating");
        System.out.println("3. View Ratings");
        System.out.println("4. View Movies by Rating");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: addRating(connection, scanner); break;
            case 2: updateRating(connection, scanner); break;
            case 3: viewRatings(connection); break;
            case 4: viewMoviesByRating(connection, scanner); break;
            default: System.out.println("Invalid choice.");
        }
    }

    public void addRating(Connection connection, Scanner scanner) {
        System.out.print("Enter MovieID: ");
        int movieId = scanner.nextInt();
        System.out.print("Enter UserID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter Rating (1-5): ");
        int rating = scanner.nextInt();
        String query = "INSERT INTO Ratings (UserID, MovieID, Rating) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, movieId);
            pstmt.setInt(3, rating);
            pstmt.executeUpdate();
            System.out.println("Rating added.");
        } catch (Exception e) {
            System.out.println("Error adding rating: " + e.getMessage());
        }
    }

    public void updateRating(Connection connection, Scanner scanner) {
        System.out.print("Enter RatingID to update: ");
        int ratingId = scanner.nextInt();
        System.out.print("Enter new Rating (1-5): ");
        int newRating = scanner.nextInt();
        String query = "UPDATE Ratings SET Rating = ? WHERE RatingID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, newRating);
            pstmt.setInt(2, ratingId);
            pstmt.executeUpdate();
            System.out.println("Rating updated.");
        } catch (Exception e) {
            System.out.println("Error updating rating: " + e.getMessage());
        }
    }

    public void viewRatings(Connection connection) {
        String query = "SELECT * FROM Ratings";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("RatingID: " + rs.getInt("RatingID") + ", MovieID: " + rs.getInt("MovieID")
                        + ", UserID: " + rs.getInt("UserID") + ", Rating: " + rs.getInt("Rating"));
            }
        } catch (Exception e) {
            System.out.println("Error viewing ratings: " + e.getMessage());
        }
    }

    public void viewMoviesByRating(Connection connection, Scanner scanner) {
        System.out.print("Enter Rating (1-5): ");
        int rating = scanner.nextInt();
        String query = "SELECT * FROM Movies WHERE MovieID IN (SELECT MovieID FROM Ratings WHERE Rating = ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, rating);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("MovieID: " + rs.getInt("MovieID") + ", Title: " + rs.getString("Title")
                        + ", Release Year: " + rs.getInt("ReleaseYear"));
            }
        } catch (Exception e) {
            System.out.println("Error viewing movies by rating: " + e.getMessage());
        }
    }
}
