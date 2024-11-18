package com.MovieSystemServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class MovieDirectors {

    public void manageMovieDirectors(Connection connection, Scanner scanner) {
        System.out.println("\n=== Movie-Director Management ===");
        System.out.println("1. Assign Director to Movie");
        System.out.println("2. Remove Director from Movie");
        System.out.println("3. View Movie-Director Assignments");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: assignDirectorToMovie(connection, scanner); break;
            case 2: removeDirectorFromMovie(connection, scanner); break;
            case 3: viewMovieDirectors(connection); break;
            default: System.out.println("Invalid choice.");
        }
    }

    public void assignDirectorToMovie(Connection connection, Scanner scanner) {
        System.out.print("Enter MovieID: ");
        int movieId = scanner.nextInt();
        System.out.print("Enter DirectorID: ");
        int directorId = scanner.nextInt();

        String query = "INSERT INTO MovieDirectors (MovieID, DirectorID) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, movieId);
            pstmt.setInt(2, directorId);
            pstmt.executeUpdate();
            System.out.println("Director assigned to movie.");
        } catch (Exception e) {
            System.out.println("Error assigning director: " + e.getMessage());
        }
    }

    public void removeDirectorFromMovie(Connection connection, Scanner scanner) {
        System.out.print("Enter MovieID: ");
        int movieId = scanner.nextInt();
        System.out.print("Enter DirectorID: ");
        int directorId = scanner.nextInt();

        String query = "DELETE FROM MovieDirectors WHERE MovieID = ? AND DirectorID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, movieId);
            pstmt.setInt(2, directorId);
            pstmt.executeUpdate();
            System.out.println("Director removed from movie.");
        } catch (Exception e) {
            System.out.println("Error removing director: " + e.getMessage());
        }
    }

    public void viewMovieDirectors(Connection connection) {
        String query = "SELECT m.Title, d.Name FROM MovieDirectors md " +
                       "JOIN Movies m ON md.MovieID = m.MovieID " +
                       "JOIN Directors d ON md.DirectorID = d.DirectorID";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Movie: " + rs.getString("Title") + ", Director: " + rs.getString("Name"));
            }
        } catch (Exception e) {
            System.out.println("Error retrieving movie-director assignments: " + e.getMessage());
        }
    }
}
