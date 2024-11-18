package com.MovieSystemServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Users {

    public void manageUsers(Connection connection, Scanner scanner) {
        System.out.println("\n=== User Management ===");
        System.out.println("1. Insert User");
        System.out.println("2. Update User");
        System.out.println("3. Delete User");
        System.out.println("4. View All Users");
        System.out.println("5. Search User by Email");
        System.out.println("6. List Users by Role");
        System.out.println("7. Lock User Account");
        System.out.println("8. Unlock User Account");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1: insertUser(connection, scanner); break;
            case 2: updateUser(connection, scanner); break;
            case 3: deleteUser(connection, scanner); break;
            case 4: getAllUsers(connection); break;
            case 5: searchUserByEmail(connection, scanner); break;
            case 6: listUsersByRole(connection, scanner); break;
            case 7: lockUserAccount(connection, scanner); break;
            case 8: unlockUserAccount(connection, scanner); break;
            default: System.out.println("Invalid choice.");
        }
    }

    public void insertUser(Connection connection, Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        String query = "INSERT INTO Users (Username, Email, Password) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
            System.out.println("User inserted.");
        } catch (Exception e) {
            System.out.println("Error inserting user: " + e.getMessage());
        }
    }

    public void updateUser(Connection connection, Scanner scanner) {
        System.out.print("Enter user ID to update: ");
        int userId = scanner.nextInt();
        scanner.nextLine();  // Consume the newline
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine();
        System.out.print("Enter new email: ");
        String newEmail = scanner.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        String query = "UPDATE Users SET Username = ?, Email = ?, Password = ? WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, newUsername);
            pstmt.setString(2, newEmail);
            pstmt.setString(3, newPassword);
            pstmt.setInt(4, userId);
            pstmt.executeUpdate();
            System.out.println("User updated.");
        } catch (Exception e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    public void deleteUser(Connection connection, Scanner scanner) {
        System.out.print("Enter user ID to delete: ");
        int userId = scanner.nextInt();
        String query = "DELETE FROM Users WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            System.out.println("User deleted.");
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    public void getAllUsers(Connection connection) {
        String query = "SELECT * FROM Users";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("UserID: " + rs.getInt("UserID") + ", Username: " + rs.getString("Username")
                        + ", Email: " + rs.getString("Email"));
            }
        } catch (Exception e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }
    }

    public void searchUserByEmail(Connection connection, Scanner scanner) {
        System.out.print("Enter email to search for: ");
        String email = scanner.nextLine();
        String query = "SELECT * FROM Users WHERE Email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("UserID: " + rs.getInt("UserID") + ", Username: " + rs.getString("Username")
                            + ", Email: " + rs.getString("Email"));
                } else {
                    System.out.println("No user found with the provided email.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error searching user by email: " + e.getMessage());
        }
    }

    public void listUsersByRole(Connection connection, Scanner scanner) {
        System.out.print("Enter role to filter by (User/Moderator/Admin): ");
        String role = scanner.nextLine();
        String query = "SELECT * FROM Users WHERE Role = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, role);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("UserID: " + rs.getInt("UserID") + ", Username: " + rs.getString("Username")
                            + ", Email: " + rs.getString("Email"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error listing users by role: " + e.getMessage());
        }
    }

    public void lockUserAccount(Connection connection, Scanner scanner) {
        System.out.print("Enter user ID to lock: ");
        int userId = scanner.nextInt();
        String query = "UPDATE Users SET IsLocked = TRUE WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            System.out.println("User account locked.");
        } catch (Exception e) {
            System.out.println("Error locking user account: " + e.getMessage());
        }
    }

    public void unlockUserAccount(Connection connection, Scanner scanner) {
        System.out.print("Enter user ID to unlock: ");
        int userId = scanner.nextInt();
        String query = "UPDATE Users SET IsLocked = FALSE WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            System.out.println("User account unlocked.");
        } catch (Exception e) {
            System.out.println("Error unlocking user account: " + e.getMessage());
        }
    }
}
