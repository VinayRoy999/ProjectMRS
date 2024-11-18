package com.MovieSystemServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Signup {

    // Confidential codes for Admin and Moderator roles
    private static final String ADMIN_CODE = "ADMIN123";
    private static final String MODERATOR_CODE = "MODERATOR123";
    private static final String USER_ROLE = "User";  // No code needed for User role
    private static final int MAX_ATTEMPTS = 3;  // Maximum retry attempts

    public boolean createAccount(Connection connection, Scanner scanner) {
        int attempts = 0;
        boolean success = false;

        while (attempts < MAX_ATTEMPTS && !success) {
            System.out.println("=== Signup ===");
            System.out.print("Enter username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();
            System.out.print("Enter password: ");
            String password = scanner.nextLine().trim();
            System.out.print("Enter role (Admin/Moderator/User): ");
            String role = scanner.nextLine().trim();

            // Ensure role is properly validated
            if (role.equalsIgnoreCase("Admin") || role.equalsIgnoreCase("Moderator")) {
                System.out.print("Enter authorization code: ");
                String authCode = scanner.nextLine().trim();

                if (role.equalsIgnoreCase("Admin") && !authCode.equals(ADMIN_CODE)) {
                    System.out.println("Invalid authorization code for Admin.");
                    attempts++;
                    continue;  // Retry
                } else if (role.equalsIgnoreCase("Moderator") && !authCode.equals(MODERATOR_CODE)) {
                    System.out.println("Invalid authorization code for Moderator.");
                    attempts++;
                    continue;  // Retry
                }
            } else if (!role.equalsIgnoreCase(USER_ROLE)) {
                System.out.println("Invalid role specified.");
                attempts++;
                continue;  // Retry
            }

            // SQL query to insert new user into Users table
            String userQuery = "INSERT INTO Users (Username, Email, Password, Role, IsLocked) VALUES (?, ?, ?, ?, ?)";
            String loginQuery = "INSERT INTO Login (UserID, Username, Password, Role) VALUES (?, ?, ?, ?)";

            try {
                // Insert into Users table
                try (PreparedStatement pstmt = connection.prepareStatement(userQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    pstmt.setString(1, username);
                    pstmt.setString(2, email);
                    pstmt.setString(3, password);  // Hash the password in real-world scenarios
                    pstmt.setString(4, role);
                    pstmt.setInt(5, 0);  // Default to unlocked user

                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        // Get the generated UserID
                        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                int userID = generatedKeys.getInt(1);

                                // Insert into Login table
                                try (PreparedStatement loginPstmt = connection.prepareStatement(loginQuery)) {
                                    loginPstmt.setInt(1, userID);
                                    loginPstmt.setString(2, username);
                                    loginPstmt.setString(3, password);
                                    loginPstmt.setString(4, role);

                                    int loginRowsAffected = loginPstmt.executeUpdate();
                                    if (loginRowsAffected > 0) {
                                        System.out.println("Account created successfully!");
                                        success = true;  // Account creation successful
                                    } else {
                                        System.out.println("Error while creating login credentials.");
                                    }
                                }
                            } else {
                                System.out.println("Error generating UserID.");
                            }
                        }
                    } else {
                        System.out.println("Account creation failed.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Error during account creation: " + e.getMessage());
            }

            attempts++;
            if (!success && attempts < MAX_ATTEMPTS) {
                System.out.println("Signup failed. Please try again.");
            }            
        }                                                                                     

        if (!success) {
            System.out.println("Maximum signup attempts reached. Please contact support or try again later.");
        }

        return success;
    }
}
