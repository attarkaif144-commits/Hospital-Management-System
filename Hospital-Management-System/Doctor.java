package com.learnJDBC;

import java.sql.*;

public class Doctor {

    private Connection connection; // DB connection

    public Doctor(Connection connection) {
        this.connection = connection;
    }

    // Display all doctors
    public void viewDoctors() {
        String query = "SELECT * FROM doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery();

            System.out.println("Doctors: ");
            System.out.println("+------------+-------------------------+----------------------+");
            System.out.println("| Doctor ID  | Name                    | Specialization       |");
            System.out.println("+------------+-------------------------+----------------------+");

            // Loop through each doctor
            while (resultset.next()) {
                int id = resultset.getInt("id");
                String name = resultset.getString("name");
                String specialization = resultset.getString("Specilization"); // DB column spelling

                System.out.printf("| %-10s | %-20s | %-12s |\n", id, name, specialization);
                System.out.println("+------------+-------------------------+----------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check if doctor exists by ID
    public boolean getDoctorByID(int id) {
        String query = "SELECT * FROM doctors WHERE id = ?";
        try {
            PreparedStatement preparedstatement = connection.prepareStatement(query);
            preparedstatement.setInt(1, id);
            ResultSet resultset = preparedstatement.executeQuery();
            return resultset.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
