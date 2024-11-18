package com.MovieSystemServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class MovieGenres {

    public void manageMovieGenres(Connection connection, Scanner scanner) {
        System.out.println("\n=== Movie-Genre Management ===");
        System.out.println("1. Insert Movie-Genre");
        System.out.println("2. Delete Movie-Genre");
        System.out.println("3. View All Movie-Genres");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: insertMovieGenre(connection, scanner); break;
            case 2: deleteMovieGenre(connection, scanner); break;
            case 3: getAllMovieGenres(connection); break;
            default: System.out.println("Invalid choice.");
        }
    }

    public void insertMovieGenre(Connection connection, Scanner scanner) {
        System.out.print("Enter MovieID: ");
        int movieId = scanner.nextInt();
        System.out.print("Enter GenreID: ");
        int genreId = scanner.nextInt();

        String query = "INSERT INTO MovieGenres (MovieID, GenreID) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, movieId);
            pstmt.setInt(2, genreId);
            pstmt.executeUpdate();
            System.out.println("Movie-Genre inserted.");
        } catch (Exception e) {
            System.out.println("Error inserting Movie-Genre: " + e.getMessage());
        }
    }

    public void deleteMovieGenre(Connection connection, Scanner scanner) {
        System.out.print("Enter MovieID: ");
        int movieId = scanner.nextInt();
        System.out.print("Enter GenreID: ");
        int genreId = scanner.nextInt();

        String query = "DELETE FROM MovieGenres WHERE MovieID = ? AND GenreID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, movieId);
            pstmt.setInt(2, genreId);
            pstmt.executeUpdate();
            System.out.println("Movie-Genre deleted.");
        } catch (Exception e) {
            System.out.println("Error deleting Movie-Genre: " + e.getMessage());
        }
    }

    public void getAllMovieGenres(Connection connection) {
        String query = "SELECT * FROM MovieGenres";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("MovieID: " + rs.getInt("MovieID") + ", GenreID: " + rs.getInt("GenreID"));
            }
        } catch (Exception e) {
            System.out.println("Error retrieving Movie-Genres: " + e.getMessage());
        }
    }
}
