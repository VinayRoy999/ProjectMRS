package com.BusinessLogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UserBusinessLogic {

    public void addUser(Connection connection, String username, String email, String password) {
        try {
            // Check if email is unique
            if (!isEmailUnique(connection, email)) {
                System.out.println("Email already exists.");
                return;
            }

            // Validate password
            if (!validatePassword(password)) {
                System.out.println("Password is too short.");
                return;
            }

            // Proceed with inserting user
            String query = "INSERT INTO Users (Username, Email, Password) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, email);
                pstmt.setString(3, password);  // Hash the password in real apps
                pstmt.executeUpdate();
                System.out.println("User added successfully.");
            }

        } catch (Exception e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    private boolean isEmailUnique(Connection connection, String email) {
        String query = "SELECT COUNT(*) FROM Users WHERE Email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (Exception e) {
            System.out.println("Error checking email uniqueness: " + e.getMessage());
        }
        return false;
    }

    private boolean validatePassword(String password) {
        return password.length() >= 6;
    }

    // Method to manage users (add, view, delete, update, etc.)
    public void manageUsers(Connection connection, Scanner scanner) {
        System.out.println("Managing Users...");
        // Implement options to manage users such as viewing, deleting, or updating user details
    }
}
