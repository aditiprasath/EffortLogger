package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EffortLogDAO {

    // Method to add a new effort log entry
    public boolean addEffortLog(EffortLog log) {
        String sql = "INSERT INTO effort_logs (date, start_time, stop_time, delta_time, project_id, lifecycle_step_id, effort_category_id, plan_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, log.getDate());
            pstmt.setTime(2, log.getStartTime());
            pstmt.setTime(3, log.getStopTime());
            
            // Update to set delta_time as seconds
            pstmt.setDouble(4, log.getDeltaTimeInSeconds());
            
            pstmt.setInt(5, log.getProjectId());
            pstmt.setInt(6, log.getLifecycleStepId());
            pstmt.setInt(7, log.getEffortCategoryId());
            pstmt.setInt(8, log.getPlanId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // Updated method to get all effort logs
    public List<EffortLog> getAllEffortLogs() {
        List<EffortLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM effort_logs";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                EffortLog log = new EffortLog(
                    rs.getInt("id"),
                    rs.getDate("date"),
                    rs.getTime("start_time"),
                    rs.getTime("stop_time"),
                    rs.getInt("project_id"),
                    rs.getInt("lifecycle_step_id"),
                    rs.getInt("effort_category_id"),
                    rs.getInt("plan_id")
                );
                logs.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }
}