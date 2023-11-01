package org.example;

import java.sql.*;

public class Task1 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/top";
        String user = "root";
        String password = "Secret";

        // Задание 1
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM courses WHERE duration > 30")) {

            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") + " - " +
                        resultSet.getString("duration") + " | " + resultSet.getString("type"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Задание 2
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                     "courses(name, duration, type, description, price, price_per_hour) " +
                     "VALUES (?, ?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, "Курс по Git");
            preparedStatement.setInt(2, 4);
            preparedStatement.setString(3, "PROGRAMMING");
            preparedStatement.setString(4, "Представляем вашему вниманию курс по Git");
            preparedStatement.setInt(5, 18000);
            preparedStatement.setDouble(6, 4500.0);

            int ok = preparedStatement.executeUpdate();
            if (ok > 0) {
                System.out.println("Course added successfully!");
            } else {
                System.out.println("Oops... Something went wrong...");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Задание 3
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE courses SET price = ? WHERE type = ?")) {

            preparedStatement.setInt(1, 5000);
            preparedStatement.setString(2, "PROGRAMMING");

            int ok = preparedStatement.executeUpdate();
            if (ok > 0) {
                System.out.println("Course prices have been successfully updated!");
            } else {
                System.out.println("Oops... Something went wrong...");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Задание 4
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM courses WHERE duration < ?")) {

            preparedStatement.setInt(1, 10);
            int ok = preparedStatement.executeUpdate();

            if (ok > 0) {
                System.out.println("Courses successfully deleted!");
            } else {
                System.out.println("Oops... Something went wrong...");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}