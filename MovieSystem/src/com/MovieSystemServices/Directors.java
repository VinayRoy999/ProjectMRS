package com.MovieSystemServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Directors {

    public void manageDirectors(Connection connection, Scanner scanner) {
        System.out.println("\n=== Director Management ===");
        System.out.println("1. Insert Director");
        System.out.println("2. Update Director");
        System.out.println("3. Delete Director");
        System.out.println("4. View All Directors");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1: insertDirector(connection, scanner); break;
            case 2: updateDirector(connection, scanner); break;
            case 3: deleteDirector(connection, scanner); break;
            case 4: getAllDirectors(connection); break;
            default: System.out.println("Invalid choice.");
        }
    }

    public void insertDirector(Connection connection, Scanner scanner) {
        System.out.print("Enter Director's Name: ");
        String name = scanner.nextLine();

        String query = "INSERT INTO Directors (Name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Director inserted.");
        } catch (Exception e) {
            System.out.println("Error inserting director: " + e.getMessage());
        }
    }

    public void updateDirector(Connection connection, Scanner scanner) {
        System.out.print("Enter DirectorID to update: ");
        int directorId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();

        String query = "UPDATE Directors SET Name = ? WHERE DirectorID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, newName);
            pstmt.setInt(2, directorId);
            pstmt.executeUpdate();
            System.out.println("Director updated.");
        } catch (Exception e) {
            System.out.println("Error updating director: " + e.getMessage());
        }
    }

    public void deleteDirector(Connection connection, Scanner scanner) {
        System.out.print("Enter DirectorID to delete: ");
        int directorId = scanner.nextInt();

        String query = "DELETE FROM Directors WHERE DirectorID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, directorId);
            pstmt.executeUpdate();
            System.out.println("Director deleted.");
        } catch (Exception e) {
            System.out.println("Error deleting director: " + e.getMessage());
        }
    }

    public void getAllDirectors(Connection connection) {
        String query = "SELECT * FROM Directors";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("DirectorID: " + rs.getInt("DirectorID") + ", Name: " + rs.getString("Name"));
            }
        } catch (Exception e) {
            System.out.println("Error retrieving directors: " + e.getMessage());
        }
    }
}
