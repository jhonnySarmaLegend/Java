/*
CREATE TABLE person (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    age INT
);

Use PreparedStatement over Statement for most queries, especially where parameters are used and performance or security matters.

Use executeQuery() for SELECT, executeUpdate() for update/insert/delete.

*/



import java.sql.*;

public class MySQLJDBCCRUD {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/your_database?serverTimezone=UTC";
        String user = "your_username";
        String password = "your_password";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 1. Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Establish Connection
            conn = DriverManager.getConnection(url, user, password);

            // --------------------------
            // CREATE (INSERT)
            // --------------------------
            String insertSQL = "INSERT INTO person (name, age) VALUES (?, ?)";
            pstmt = conn.prepareStatement(insertSQL);
            pstmt.setString(1, "Alice");
            pstmt.setInt(2, 30);
            int rowsInserted = pstmt.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
            pstmt.close();

            // --------------------------
            // READ (SELECT)
            // --------------------------
            String selectSQL = "SELECT id, name, age FROM person";
            pstmt = conn.prepareStatement(selectSQL);
            rs = pstmt.executeQuery();
            System.out.println("Table Contents:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println(id + " - " + name + " - " + age);
            }
            rs.close();
            pstmt.close();

            // --------------------------
            // UPDATE
            // --------------------------
            String updateSQL = "UPDATE person SET age = ? WHERE name = ?";
            pstmt = conn.prepareStatement(updateSQL);
            pstmt.setInt(1, 31);
            pstmt.setString(2, "Alice");
            int rowsUpdated = pstmt.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
            pstmt.close();

            // --------------------------
            // DELETE
            // --------------------------
            String deleteSQL = "DELETE FROM person WHERE name = ?";
            pstmt = conn.prepareStatement(deleteSQL);
            pstmt.setString(1, "Alice");
            int rowsDeleted = pstmt.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
            pstmt.close();

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL Exception:");
            e.printStackTrace();
        } finally {
            // Close resources
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
}
