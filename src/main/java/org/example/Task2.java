package org.example;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Task2 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/top";
        String user = "root";
        String password = "Fenix0190";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT c.name AS course_name, c.type AS type, s.age AS age" +
                     " FROM courses c " +
                     " JOIN subscriptions sub ON c.id = sub.course_id " +
                     " JOIN students s ON sub.student_id = s.id");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            Map<String, Integer> courses = new HashMap<>();
            Map<String, Integer> count = new HashMap<>();

            while (resultSet.next()) {
                String type = resultSet.getString("type");
                String courseName = resultSet.getString("course_name");
                Integer age = resultSet.getInt("age");

                if (type.equals("DESIGN")) {
                    count.put(courseName, count.get(courseName) == null ? 1 : count.get(courseName) + 1);
                    courses.put(courseName, courses.get(courseName) == null ? age : courses.get(courseName) + age);
                }
            }

            System.out.printf("\n%-35s%20s\n\n", "course_name", "avg_age");
            for (String course_name : courses.keySet()) {
                double avgAge = (double) courses.get(course_name) / count.get(course_name);
                System.out.printf("%-35s%20d\n", course_name, Math.round(avgAge));
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
