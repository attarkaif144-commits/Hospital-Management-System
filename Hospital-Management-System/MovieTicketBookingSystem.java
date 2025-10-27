package com.learnJDBC; // Package name
import java.sql.*;
import java.util.Scanner;

public class MovieTicketBookingSystem {

    // Database connection details
    static final String DB_URL = "jdbc:mysql://localhost:3306/movieticketdb"; // Change DB name if needed
    static final String USER = "root"; // Your MySQL username
    static final String PASS = "kaif@ATTAR1"; // Your MySQL password

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            while (true) {
                // Menu
                System.out.println("==== Movie Ticket Booking System ====");
                System.out.println("1. Add Movie");
                System.out.println("2. View Movies");
                System.out.println("3. Book Ticket");
                System.out.println("4. View Bookings");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        // Add Movie
                        System.out.print("Enter Movie Name: ");
                        String movieName = sc.nextLine();
                        System.out.print("Enter Ticket Price: ");
                        double price = sc.nextDouble();
                        addMovie(conn, movieName, price);
                        break;

                    case 2:
                        // View Movies
                        viewMovies(conn);
                        break;

                    case 3:
                        // Book Ticket
                        System.out.print("Enter Your Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Movie ID: ");
                        int movieId = sc.nextInt();
                        System.out.print("Enter Number of Seats: ");
                        int seats = sc.nextInt();
                        bookTicket(conn, name, movieId, seats);
                        break;

                    case 4:
                        // View Bookings
                        viewBookings(conn);
                        break;

                    case 5:
                        // Exit Program
                        System.out.println("THANK YOU! FOR USING MOVIE TICKET BOOKING SYSTEM!!");
                        return;

                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to Add Movie
    static void addMovie(Connection conn, String name, double price) throws SQLException {
        String sql = "INSERT INTO movies (name, price) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.executeUpdate();
            System.out.println("Movie added successfully!");
        }
    }

    // Method to View Movies
    static void viewMovies(Connection conn) throws SQLException {
        String sql = "SELECT * FROM movies";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Movies:");
            System.out.println("+----------+-------------------------+--------------+");
            System.out.println("| Movie ID | Name                    | Price        |");
            System.out.println("+----------+-------------------------+--------------+");

            while (rs.next()) {
                System.out.printf("| %-8d | %-23s | ₹%-10.2f |\n",
                        rs.getInt("id"), rs.getString("name"), rs.getDouble("price"));
            }

            System.out.println("+----------+-------------------------+--------------+");
        }
    }

    // Method to Book Ticket
    static void bookTicket(Connection conn, String customerName, int movieId, int seats) throws SQLException {
        // Get Movie Price
        String priceQuery = "SELECT name, price FROM movies WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(priceQuery)) {
            pstmt.setInt(1, movieId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String movieName = rs.getString("name");
                double price = rs.getDouble("price");
                double totalPrice = seats * price;

                // Insert booking
                String insertBooking = "INSERT INTO bookings (customer_name, movie_name, seats, total_price) VALUES (?, ?, ?, ?)";
                try (PreparedStatement bookingStmt = conn.prepareStatement(insertBooking)) {
                    bookingStmt.setString(1, customerName);
                    bookingStmt.setString(2, movieName);
                    bookingStmt.setInt(3, seats);
                    bookingStmt.setDouble(4, totalPrice);
                    bookingStmt.executeUpdate();
                }

                System.out.println("Booking successful! Total Price: ₹" + totalPrice);
            } else {
                System.out.println("Movie not found!");
            }
        }
    }

    // Method to View Bookings
    static void viewBookings(Connection conn) throws SQLException {
        String sql = "SELECT * FROM bookings";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Bookings:");
            System.out.println("+------------+------------------+------------------+-------+--------------+");
            System.out.println("| Booking ID | Name             | Movie            | Seats | Total Price  |");
            System.out.println("+------------+------------------+------------------+-------+--------------+");

            while (rs.next()) {
                System.out.printf("| %-10d | %-16s | %-16s | %-5d | ₹%-10.2f |\n",
                        rs.getInt("id"), rs.getString("customer_name"),
                        rs.getString("movie_name"), rs.getInt("seats"),
                        rs.getDouble("total_price"));
            }

            System.out.println("+------------+------------------+------------------+-------+--------------+");
        }
    }
}
