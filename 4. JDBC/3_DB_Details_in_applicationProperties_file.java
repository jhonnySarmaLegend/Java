/*
application.properties
      
db.url=jdbc:mysql://localhost:3306/your_database?serverTimezone=UTC
db.username=your_username
db.password=your_password

*/


// java code'
import java.sql.*;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class DBConnection {
    public static void main(String[] args) {
        Properties props = new Properties();

        // Load the properties file
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return;
            }
            // Load the properties from the file
            props.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        // Get properties
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        // JDBC objects
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Load driver class
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Get connection using values from properties
            conn = DriverManager.getConnection(url, user, password);

            // Run a sample query
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT NOW()"); // Just shows DB current time

            if (rs.next()) {
                System.out.println("DB Time is: " + rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) { }
            try { if (stmt != null) stmt.close(); } catch (SQLException ex) { }
            try { if (conn != null) conn.close(); } catch (SQLException ex) { }
        }
    }
}
