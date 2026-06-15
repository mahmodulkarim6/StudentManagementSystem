import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        Connection con = null;

        try {
            System.out.println("Connecting to database...");

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_db",
                "root",
                "root"
            );

            System.out.println("✅ DATABASE CONNECTED SUCCESSFULLY!");

        } catch (Exception e) {
            System.out.println("❌ DB ERROR: " + e);
        }

        return con;
    }
}