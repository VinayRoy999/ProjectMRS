package com.MovieSystemServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Reviews {

    public void manageReviews(Connection connection, Scanner scanner) {
        System.out.println("\n=== Review Management ===");
        System.out.println("1. Add Review");
        System.out.println("2. View All Reviews");
        System.out.println("3. Remove Review");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1: addReview(connection, scanner); break;
            case 2: viewAllReviews(connection); break;
            case 3: removeReview(connection, scanner); break;
            default: System.out.println("Invalid choice.");
        }
    }

    public void addReview(Connection connection, Scanner scanner) {
        System.out.print("Enter UserID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter MovieID: ");
        int movieId = scanner.nextInt();
        scanner.nextLine();  // consume the newline
        System.out.print("Enter Review Text: ");
        String reviewText = scanner.nextLine();
        
        String query = "INSERT INTO Reviews (UserID, MovieID, ReviewText) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, movieId);
            pstmt.setString(3, reviewText);
            pstmt.executeUpdate();
            System.out.println("Review added.");
        } catch (Exception e) {
            System.out.println("Error adding review: " + e.getMessage());
        }
    }

    public void viewAllReviews(Connection connection) {
        String query = "SELECT * FROM Reviews";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("ReviewID: " + rs.getInt("ReviewID") + ", UserID: " + rs.getInt("UserID") + ", MovieID: " + rs.getInt("MovieID") + ", ReviewText: " + rs.getString("ReviewText") + ", ReviewDate: " + rs.getTimestamp("ReviewDate"));
            }
        } catch (Exception e) {
            System.out.println("Error retrieving reviews: " + e.getMessage());
        }
    }

    public void removeReview(Connection connection, Scanner scanner) {
        System.out.print("Enter ReviewID to remove: ");
        int reviewId = scanner.nextInt();
        String query = "DELETE FROM Reviews WHERE ReviewID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, reviewId);
            pstmt.executeUpdate();
            System.out.println("Review removed.");
        } catch (Exception e) {
            System.out.println("Error removing review: " + e.getMessage());
        }
    }
}
