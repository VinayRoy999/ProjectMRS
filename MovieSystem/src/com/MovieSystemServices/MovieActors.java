package com.MovieSystemServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class MovieActors {

    public void manageMovieActors(Connection connection, Scanner scanner) {
        System.out.println("\n=== Movie-Actor Management ===");
        System.out.println("1. Add Movie-Actor Association");
        System.out.println("2. Remove Movie-Actor Association");
        System.out.println("3. View All Movie-Actor Associations");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: addMovieActor(connection, scanner); break;
            case 2: removeMovieActor(connection, scanner); break;
            case 3: viewAllMovieActors(connection); break;
            default: System.out.println("Invalid choice.");
        }
    }

    public void addMovieActor(Connection connection, Scanner scanner) {
        System.out.print("Enter MovieID: ");
        int movieId = scanner.nextInt();
        System.out.print("Enter ActorID: ");
        int actorId = scanner.nextInt();
        String query = "INSERT INTO MovieActors (MovieID, ActorID) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, movieId);
            pstmt.setInt(2, actorId);
            pstmt.executeUpdate();
            System.out.println("Movie-Actor association added.");
        } catch (Exception e) {
            System.out.println("Error adding Movie-Actor association: " + e.getMessage());
        }
    }

    public void removeMovieActor(Connection connection, Scanner scanner) {
        System.out.print("Enter MovieID to remove association: ");
        int movieId = scanner.nextInt();
        System.out.print("Enter ActorID to remove association: ");
        int actorId = scanner.nextInt();
        String query = "DELETE FROM MovieActors WHERE MovieID = ? AND ActorID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, movieId);
            pstmt.setInt(2, actorId);
            pstmt.executeUpdate();
            System.out.println("Movie-Actor association removed.");
        } catch (Exception e) {
            System.out.println("Error removing Movie-Actor association: " + e.getMessage());
        }
    }

    public void viewAllMovieActors(Connection connection) {
        String query = "SELECT * FROM MovieActors";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("MovieID: " + rs.getInt("MovieID") + ", ActorID: " + rs.getInt("ActorID"));
            }
        } catch (Exception e) {
            System.out.println("Error retrieving Movie-Actor associations: " + e.getMessage());
        }
    }
}
