package com.movieSystem.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Login {
    private String userRole; // Store the user role

    public boolean authenticateUser(Connection connection, Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String query = "SELECT Role FROM Login WHERE Username = ? AND Password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    userRole = rs.getString("Role"); // Set the role
                    System.out.println("Login successful! Role: " + userRole);
                    return true;
                } else {
                    System.out.println("Invalid login credentials.");
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
            return false;
        }
    }
    
    
    public void deleteUser(Connection connection, Scanner scanner) {
        System.out.print("Enter user ID to delete: ");
        int userId = scanner.nextInt();
        try {
            // First, delete related login data
            String deleteLoginQuery = "DELETE FROM Login WHERE UserID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(deleteLoginQuery)) {
                pstmt.setInt(1, userId);
                pstmt.executeUpdate();
            }

            // Now, delete the user
            String query = "DELETE FROM Users WHERE UserID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, userId);
                pstmt.executeUpdate();
                System.out.println("User deleted successfully.");
            }

        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    

    // Getter for userRole
    public String getUserRole() {
        return userRole;
    }
}
