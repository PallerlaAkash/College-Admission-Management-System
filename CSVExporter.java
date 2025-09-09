package util;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CSVExporter {
    public static void exportAdmissionList(ResultSet rs, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Student ID,Name,Course,Status\n");
            while (rs.next()) {
                writer.write(rs.getInt("student_id") + "," +
                        rs.getString("name") + "," +
                        rs.getString("course_name") + "," +
                        rs.getString("status") + "\n");
            }
            System.out.println("Admission list exported to " + filePath);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}

