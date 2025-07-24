import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCDemo {
    public static void main(String[] args) {
        // Step 1: Declare JDBC variables
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        // Step 2: Database credentials and URL
        String url = "jdbc:mysql://localhost:3306/your_database"; // Replace with your DB URL
        String user = "your_username"; // Replace with your username
        String password = "your_password"; // Replace with your password

        try {
            // Step 3: Load MySQL JDBC Driver (optional in newer versions)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 4: Establish Connection
            conn = DriverManager.getConnection(url, user, password);

            // Step 5: Create Statement
            stmt = conn.createStatement();

            // Step 6: Execute Query
            String sql = "SELECT * FROM your_table";
            rs = stmt.executeQuery(sql);

            // Step 7: Process ResultSet
            while (rs.next()) {
                int id = rs.getInt("id"); // Replace 'id' with your table column
                String name = rs.getString("name"); // Replace 'name' with your table column
                System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver class not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL Exception!");
            e.printStackTrace();
        } finally {
            // Step 8: Close the resources in reverse order of opening
            try {
                if (rs != null) rs.close();
            } catch (SQLException se) { /* ignore */ }
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) { /* ignore */ }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) { /* ignore */ }
        }
    }
}
