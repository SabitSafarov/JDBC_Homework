package org.example;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Task3 {
    static String url = "jdbc:mysql://localhost:3306/top";
    static String user = "root";
    static String password = "Fenix0190";
    static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        // Task 1
        printInfoCourses();
        // Task 2
        addNewCourse("Новый курс", 1);
        // Task 3
        updateDurationCourse("Веб-разработчик не с нуля", 20);
        // Task 4
        printStudentsInfo("Веб-разработчик не с нуля");
        // Task 5
        addNewStudent("Иван Иванович", 99, "Веб-разработчик не с нуля");

    }

    // Task 1
    public static void printInfoCourses() {
        try (CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet()) {

            rowSet.setCommand("SELECT * FROM courses");
            rowSet.execute(connection);

            while (rowSet.next()) {
                System.out.println(rowSet.getString("name") + " - " + rowSet.getString("duration"));
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    // Task 2
    public static void addNewCourse(String name, int duration) {
        try (CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet()) {

            rowSet.setCommand("SELECT * FROM courses");
            rowSet.execute(connection);

            while (rowSet.next()) {
                System.out.println(rowSet.getString("id") + "." + rowSet.getString("name") + " - " + rowSet.getString("duration"));
            }

            rowSet.moveToInsertRow();
            rowSet.updateInt("id", rowSet.size() + 1);
            rowSet.updateString("name", name);
            rowSet.updateInt("duration", duration);
            rowSet.updateNull("type");
            rowSet.updateNull("description");
            rowSet.updateNull("teacher_id");
            rowSet.updateNull("students_count");
            rowSet.updateNull("price");
            rowSet.updateNull("price_per_hour");
            rowSet.insertRow();
            rowSet.moveToCurrentRow();
            System.out.println(rowSet.getString("id") + "." + rowSet.getString("name") + " - " + rowSet.getString("duration"));

            connection.setAutoCommit(false);
            rowSet.acceptChanges();



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Task 3
    public static void updateDurationCourse(String name, int duration) {
        try (CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet()) {

            rowSet.setCommand("SELECT * FROM courses");
            rowSet.execute(connection);

            while (rowSet.next()) {
                if (rowSet.getString("name").equals(name)) {
                    rowSet.updateInt("duration", duration);
                    rowSet.updateRow();
                }
                System.out.println(rowSet.getString("name") + " - " + rowSet.getString("duration"));
            }

            connection.setAutoCommit(false);
            rowSet.acceptChanges();
            System.out.println("OK!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Task 4
    public static void printStudentsInfo(String name) {
        try (CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet()) {

            rowSet.setCommand("SELECT c.name AS course_name, s.name AS student_name, s.age AS age FROM courses c " +
                    "JOIN subscriptions sub ON sub.course_id = c.id " +
                    "JOIN students s ON s.id = sub.student_id");
            rowSet.execute(connection);

            System.out.println("\nCourse_Name: " + name + "\n");
            while (rowSet.next()) {
                if (rowSet.getString(1).equals(name)) {
                    System.out.println("Name: " + rowSet.getString(2) + " | Age: " + rowSet.getString(3));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Task 5
    public static void addNewStudent(String name, int age, String courseName) {
        try (CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet()) {

            rowSet.setCommand("SELECT c.name AS course_name, s.name AS student_name, s.age AS age FROM courses c " +
                    "JOIN subscriptions sub ON sub.course_id = c.id " +
                    "JOIN students s ON s.id = sub.student_id");
            rowSet.execute(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
