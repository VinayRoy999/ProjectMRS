package com.MovieSystemServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Actors {

    public void manageActors(Connection connection, Scanner scanner) {
        System.out.println("\n=== Actor Management ===");
        System.out.println("1. Add Actor");
        System.out.println("2. Update Actor");
        System.out.println("3. Remove Actor");
        System.out.println("4. View All Actors");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline character
        switch (choice) {
            case 1: addActor(connection, scanner); break;
            case 2: updateActor(connection, scanner); break;
            case 3: removeActor(connection, scanner); break;
            case 4: viewAllActors(connection); break;
            default: System.out.println("Invalid choice.");
        }
    }

    public void addActor(Connection connection, Scanner scanner) {
        System.out.print("Enter Actor Name: ");
        String name = scanner.nextLine();
        String query = "INSERT INTO Actors (Name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Actor added.");
        } catch (Exception e) {
            System.out.println("Error adding actor: " + e.getMessage());
        }
    }

    public void updateActor(Connection connection, Scanner scanner) {
        System.out.print("Enter ActorID to update: ");
        int actorId = scanner.nextInt();
        scanner.nextLine();  // Consume newline character
        System.out.print("Enter new Actor Name: ");
        String name = scanner.nextLine();
        String query = "UPDATE Actors SET Name = ? WHERE ActorID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, actorId);
            pstmt.executeUpdate();
            System.out.println("Actor updated.");
        } catch (Exception e) {
            System.out.println("Error updating actor: " + e.getMessage());
        }
    }

    public void removeActor(Connection connection, Scanner scanner) {
        System.out.print("Enter ActorID to remove: ");
        int actorId = scanner.nextInt();
        String query = "DELETE FROM Actors WHERE ActorID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, actorId);
            pstmt.executeUpdate();
            System.out.println("Actor removed.");
        } catch (Exception e) {
            System.out.println("Error removing actor: " + e.getMessage());
        }
    }

    public void viewAllActors(Connection connection) {
        String query = "SELECT * FROM Actors";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("ActorID: " + rs.getInt("ActorID") + ", Name: " + rs.getString("Name"));
            }
        } catch (Exception e) {
            System.out.println("Error retrieving actors: " + e.getMessage());
        }
    }
}
