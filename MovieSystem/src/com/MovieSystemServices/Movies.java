package com.MovieSystemServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Movies {

    public void manageMovies(Connection connection, Scanner scanner) {
        System.out.println("\n=== Movie Management ===");
        System.out.println("1. Insert Movie");
        System.out.println("2. Update Movie");
        System.out.println("3. Delete Movie");
        System.out.println("4. View All Movies");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1: insertMovie(connection, scanner); break;
            case 2: updateMovie(connection, scanner); break;
            case 3: deleteMovie(connection, scanner); break;
            case 4: getAllMovies(connection); break;
            default: System.out.println("Invalid choice.");
        }
    }

    public void insertMovie(Connection connection, Scanner scanner) {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        System.out.print("Enter duration: ");
        int duration = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter summary: ");
        String summary = scanner.nextLine();
        
        String query = "INSERT INTO Movies (Title, ReleaseYear, DurationMinutes, Summary) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setInt(2, year);
            pstmt.setInt(3, duration);
            pstmt.setString(4, summary);
            pstmt.executeUpdate();
            System.out.println("Movie inserted.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateMovie(Connection connection, Scanner scanner) {
        System.out.print("Enter movie ID: ");
        int movieId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new title: ");
        String newTitle = scanner.nextLine();
        System.out.print("Enter new year: ");
        int newYear = scanner.nextInt();
        System.out.print("Enter new duration: ");
        int newDuration = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new summary: ");
        String newSummary = scanner.nextLine();
        
        String query = "UPDATE Movies SET Title = ?, ReleaseYear = ?, DurationMinutes = ?, Summary = ? WHERE MovieID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, newTitle);
            pstmt.setInt(2, newYear);
            pstmt.setInt(3, newDuration);
            pstmt.setString(4, newSummary);
            pstmt.setInt(5, movieId);
            pstmt.executeUpdate();
            System.out.println("Movie updated.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteMovie(Connection connection, Scanner scanner) {
        System.out.print("Enter movie ID: ");
        int movieId = scanner.nextInt();
        String query = "DELETE FROM Movies WHERE MovieID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, movieId);
            pstmt.executeUpdate();
            System.out.println("Movie deleted.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void getAllMovies(Connection connection) {
        String query = "SELECT * FROM Movies";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("MovieID") + ", Title: " + rs.getString("Title")
                        + ", Year: " + rs.getInt("ReleaseYear") + ", Duration: " + rs.getInt("DurationMinutes")
                        + " minutes, Summary: " + rs.getString("Summary"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
