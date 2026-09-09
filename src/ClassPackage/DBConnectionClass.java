

package ClassPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionClass {

    private static final String URL =
            "jdbc:sqlserver://PABASARA\\SQLEXPRESS:1433;"
          + "databaseName=CityStyleShoe;"
          + "encrypt=true;"
          + "trustServerCertificate=true;";

    private static final String USER = "sa";
    private static final String PASSWORD = "Paba@123";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database Connected Successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ SQL Server Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Database Connection Failed!");
            e.printStackTrace();
        }

        return connection;
    }
}
