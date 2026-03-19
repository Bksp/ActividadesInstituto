package mantencionHirata.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para establecer y gestionar la conexión a la base de datos.
 * @author 
 * @version (2026.03.15 - 1.0)
 */
public class DataBase {
    private static final String URL = "jdbc:mysql://localhost:3306/mantencionHirata";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}