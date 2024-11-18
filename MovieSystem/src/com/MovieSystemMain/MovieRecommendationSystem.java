package com.MovieSystemMain;

import java.sql.Connection;
import java.util.Scanner;

import com.BusinessLogic.UserBusinessLogic;
import com.BusinessLogic.MovieBusinessLogic;
import com.BusinessLogic.UserPreferencesBusinessLogic;
import com.BusinessLogic.WatchHistoryBusinessLogic;

import utils.DBConnection;

import com.MovieSystemServices.Signup;
import com.MovieSystemServices.Movies;
import com.MovieSystemServices.Actors;
import com.MovieSystemServices.Directors;
import com.MovieSystemServices.Genres;  // Import the Genres service
import com.movieSystem.login.Login;
import com.MovieSystemServices.Users;  // Add this import
import com.MovieSystemServices.WatchHistory;


public class MovieRecommendationSystem {
    
	private static Connection connection;
    private static Scanner scanner;
    private static Login loginService;
    private static Signup signupService;
    private static UserBusinessLogic userBusinessLogic;
    private static MovieBusinessLogic movieBusinessLogic;
    private static UserPreferencesBusinessLogic userPreferencesBusinessLogic;
    private static WatchHistoryBusinessLogic watchHistoryBusinessLogic;
    private static Movies movieService;
    private static Actors actorsService;
    private static Directors directorsService;
    private static Genres genresService;  // Declare Genres service
    private static Users usersService;  // Declare Users service


    public static void main(String[] args) {
        try {
            connection = DBConnection.getConnection();
            scanner = new Scanner(System.in);
            loginService = new Login();
            signupService = new Signup();
            userBusinessLogic = new UserBusinessLogic();
            movieBusinessLogic = new MovieBusinessLogic();
            userPreferencesBusinessLogic = new UserPreferencesBusinessLogic();
            watchHistoryBusinessLogic = new WatchHistoryBusinessLogic();
            movieService = new Movies();
            actorsService = new Actors();
            directorsService = new Directors();
            genresService = new Genres();  // Initialize Genres service
            usersService = new Users();  // Initialize Users service


            System.out.println("\n     Welcome to the Movie Recommendation System");
            System.out.println("\n              Do you want to \n\n          (L)ogin___[or]___(S)ign up?: ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("L")) {
                if (loginService.authenticateUser(connection, scanner)) {
                    handleRoleBasedMenu();
                }
            } else if (choice.equalsIgnoreCase("S")) {
                if (signupService.createAccount(connection, scanner)) {
                    System.out.println("Account created successfully! Please log in.");
                    if (loginService.authenticateUser(connection, scanner)) {
                        handleRoleBasedMenu();
                    }
                }
            } else {
                System.out.println("Invalid choice. Please restart the application.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    private static void handleRoleBasedMenu() {
        String role = loginService.getUserRole().toLowerCase();
        boolean exit = false;
        switch (role) {
            case "admin":
                exit = adminMenu();
                break;
            case "moderator":
                exit = moderatorMenu();
                break;
            case "user":
                exit = userMenu();
                break;
            default:
                System.out.println("Unknown role.");
        }

        if (exit) {
            System.out.println("Logging out...");
        }
    }

    private static boolean adminMenu() {
        String choice;
        do {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. User Management");
            System.out.println("2. Movie Management");
            System.out.println("3. Watch History Management");
            System.out.println("4. Actor Management");
            System.out.println("5. Director Management");
            System.out.println("6. Genre Management");  // Added Genre Management option
            System.out.println("0. Logout");
            System.out.println("b. Go Back");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();

            switch (choice) {
           
                case "1":
                usersService.manageUsers(connection, scanner);  // Call manageUsers from Users service
                break;

                case "2":
                    movieService.manageMovies(connection, scanner);
                    break;
                case "3":
                    watchHistoryBusinessLogic.manageWatchHistory(connection, scanner);
                    break;
                case "4":
                    actorsService.manageActors(connection, scanner); // Call actorsService
                    break;
                case "5":
                    directorsService.manageDirectors(connection, scanner); // Call directorsService
                    break;
                case "6":
                    genresService.manageGenres(connection, scanner);  // Call genresService
                    break;
                    
                case "0":
                    return true;
                case "b":
                    return false;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (true);
    }

    private static boolean moderatorMenu() {
        String choice;
        WatchHistory watchHistory = new WatchHistory(); // Initialize WatchHistory service
        do {
            System.out.println("\n=== Moderator Menu ===");
            System.out.println("1. Movie Management");
            System.out.println("2. Watch History Management");
            System.out.println("0. Logout");
            System.out.println("b. Go Back");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    movieService.manageMovies(connection, scanner);
                    break;
                case "2":
                    watchHistory.manageWatchHistory(connection, scanner);
                    break;
                case "0":
                    return true;
                case "b":
                    return false;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (true);
    }

    private static boolean userMenu() {
        String choice;
        do {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. View Movies");
            System.out.println("2. Rate a Movie");
            System.out.println("3. Manage User Preferences");
            System.out.println("4. View Your Watch History");
            System.out.println("0. Logout");
            System.out.println("b. Go Back");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    movieBusinessLogic.viewAllMovies(connection);
                    break;
                case "2":
                    System.out.print("Enter movie title: ");
                    String movieTitle = scanner.nextLine();
                    System.out.print("Enter your rating (1-5): ");
                    int rating = Integer.parseInt(scanner.nextLine());
                    movieBusinessLogic.rateMovie(connection, movieTitle, rating);
                    break;
                case "3":
                    userPreferencesBusinessLogic.updatePreferences(connection, scanner);
                    break;
                case "4":
                    System.out.print("Enter your UserID to view your watch history: ");
                    int userId = scanner.nextInt();
                    watchHistoryBusinessLogic.viewUserWatchHistory(connection, userId);
                    break;
                case "0":
                    return true;
                case "b":
                    return false;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (true);
    }

    private static void closeResources() {
        try {
            if (scanner != null) scanner.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
}
