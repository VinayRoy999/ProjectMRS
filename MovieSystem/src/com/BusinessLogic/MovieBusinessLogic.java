package com.BusinessLogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MovieBusinessLogic {

    // Validate release year (shouldn't be in the future)
    public boolean isReleaseYearValid(int releaseYear) {
        int currentYear = java.time.Year.now().getValue();
        return releaseYear <= currentYear;
    }

    // Check if movie already exists by title
    public boolean isMovieTitleUnique(Connection connection, String title) {
        String query = "SELECT COUNT(*) FROM Movies WHERE Title = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, title);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (Exception e) {
            System.out.println("Error checking movie title uniqueness: " + e.getMessage());
        }
        return false;
    }

    // Validate movie duration
    public boolean isDurationValid(int durationMinutes) {
        return durationMinutes > 0;
    }

    // View all movies in the database
    public void viewAllMovies(Connection connection) {
        String query = "SELECT * FROM Movies";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("MovieID: " + rs.getInt("MovieID") + ", Title: " + rs.getString("Title") +
                                   ", Release Year: " + rs.getInt("ReleaseYear") + ", Duration: " + rs.getInt("DurationMinutes"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Rate a movie by the title and rating
    public void rateMovie(Connection connection, String movieTitle, int rating) {
        // Assuming there's a method to get the UserID based on logged-in user
        int userId = getUserIdByUsername(connection);
        
        String query = "INSERT INTO Ratings (UserID, MovieID, Rating) " +
                       "SELECT ?, MovieID, ? FROM Movies WHERE Title = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, rating);
            pstmt.setString(3, movieTitle);
            pstmt.executeUpdate();
            System.out.println("Rating added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Get UserID based on the logged-in user's username
    private int getUserIdByUsername(Connection connection) {
        // Replace with actual logic to get the logged-in user's username
        String username = "exampleUsername"; // This should be replaced with actual username fetching logic
        String query = "SELECT UserID FROM Users WHERE Username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("UserID");
                }
            }
        } catch (Exception e) {
            System.out.println("Error getting UserID: " + e.getMessage());
        }
        return -1; // Return -1 if user is not found
    }
}
