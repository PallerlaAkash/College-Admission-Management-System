package service;

import db.DBConnection;

import java.sql.*;
import java.util.Scanner;
import util.CSVExporter;

public class AdmissionService {
    Scanner sc = new Scanner(System.in);

    public void registerStudent() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Marks: ");
            int marks = Integer.parseInt(sc.nextLine());
            System.out.print("Category: ");
            String category = sc.nextLine();

            String query = "INSERT INTO Students(name, email, marks, category) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setInt(3, marks);
            stmt.setString(4, category);
            stmt.executeUpdate();

            System.out.println("Student registered!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void applyForCourseMenu() {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("Student ID: ");
            int studentId = Integer.parseInt(sc.nextLine());
            System.out.print("Course ID: ");
            int courseId = Integer.parseInt(sc.nextLine());

            String query = "INSERT INTO Applications(student_id, course_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();

            System.out.println("Application submitted!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processApplications() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = """
                    SELECT a.app_id, s.student_id, s.name, s.marks, c.course_id, c.course_name, c.cut_off, c.available_seats
                    FROM Applications a
                    JOIN Students s ON a.student_id = s.student_id
                    JOIN Courses c ON a.course_id = c.course_id
                    WHERE a.status = 'Pending'
                    ORDER BY s.marks DESC;
                    """;

            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int appId = rs.getInt("app_id");
                int studentId = rs.getInt("student_id");
                int marks = rs.getInt("marks");
                int courseId = rs.getInt("course_id");
                int cutOff = rs.getInt("cut_off");
                int availableSeats = rs.getInt("available_seats");

                String status;
                if (marks >= cutOff && availableSeats > 0) {
                    status = "Approved";
                    // Update seats
                    PreparedStatement updateSeats = conn.prepareStatement("UPDATE Courses SET available_seats = available_seats - 1 WHERE course_id = ?");
                    updateSeats.setInt(1, courseId);
                    updateSeats.executeUpdate();
                } else {
                    status = "Rejected";
                }

                PreparedStatement updateApp = conn.prepareStatement("UPDATE Applications SET status = ? WHERE app_id = ?");
                updateApp.setString(1, status);
                updateApp.setInt(2, appId);
                updateApp.executeUpdate();
            }

            System.out.println("Applications processed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportAdmissionsToCSV() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = """
                    SELECT s.student_id, s.name, c.course_name, a.status
                    FROM Applications a
                    JOIN Students s ON a.student_id = s.student_id
                    JOIN Courses c ON a.course_id = c.course_id
                    WHERE a.status = 'Approved';
                    """;

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            CSVExporter.exportAdmissionList(rs, "admission_list.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

