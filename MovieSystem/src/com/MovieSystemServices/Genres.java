package com.MovieSystemServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Genres {

    public void manageGenres(Connection connection, Scanner scanner) {
        System.out.println("\n=== Genre Management ===");
        System.out.println("1. Insert Genre");
        System.out.println("2. Update Genre");
        System.out.println("3. Delete Genre");
        System.out.println("4. View All Genres");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume the newline
        switch (choice) {
            case 1: insertGenre(connection, scanner); break;
            case 2: updateGenre(connection, scanner); break;
            case 3: deleteGenre(connection, scanner); break;
            case 4: getAllGenres(connection); break;
            default: System.out.println("Invalid choice.");
        }
    }

    public void insertGenre(Connection connection, Scanner scanner) {
        System.out.print("Enter genre name: ");
        String genreName = scanner.nextLine();
        String query = "INSERT INTO Genres (GenreName) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, genreName);
            pstmt.executeUpdate();
            System.out.println("Genre inserted.");
        } catch (Exception e) {
            System.out.println("Error inserting genre: " + e.getMessage());
        }
    }

    public void updateGenre(Connection connection, Scanner scanner) {
        System.out.print("Enter genre ID to update: ");
        int genreId = scanner.nextInt();
        scanner.nextLine();  // Consume the newline
        System.out.print("Enter new genre name: ");
        String newGenreName = scanner.nextLine();
        String query = "UPDATE Genres SET GenreName = ? WHERE GenreID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, newGenreName);
            pstmt.setInt(2, genreId);
            pstmt.executeUpdate();
            System.out.println("Genre updated.");
        } catch (Exception e) {
            System.out.println("Error updating genre: " + e.getMessage());
        }
    }

    public void deleteGenre(Connection connection, Scanner scanner) {
        System.out.print("Enter genre ID to delete: ");
        int genreId = scanner.nextInt();
        String query = "DELETE FROM Genres WHERE GenreID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, genreId);
            pstmt.executeUpdate();
            System.out.println("Genre deleted.");
        } catch (Exception e) {
            System.out.println("Error deleting genre: " + e.getMessage());
        }
    }

    public void getAllGenres(Connection connection) {
        String query = "SELECT * FROM Genres";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("GenreID: " + rs.getInt("GenreID") + ", GenreName: " + rs.getString("GenreName"));
            }
        } catch (Exception e) {
            System.out.println("Error retrieving genres: " + e.getMessage());
        }
    }
}
