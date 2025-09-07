package parcofaunistico.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class DAOUtils {

    /*public static Connection localMySQLConnection(String database, String username, String password) {
        try {
            var host = "localhost";
            var port = "3306";
            var connectionString = "jdbc:mysql://" + host + ":" + port + "/" + database;
            return DriverManager.getConnection(connectionString, username, password);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }*/

    public static Connection localMySQLConnection(String dbName, String user, String pass) throws SQLException {
    try (Connection rootConn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true",
            "root", "")) {

        rootConn.createStatement().executeUpdate(
            "CREATE DATABASE IF NOT EXISTS " + dbName + 
            " DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci");

        rootConn.createStatement().executeUpdate(
            "CREATE USER IF NOT EXISTS 'appuser'@'localhost' IDENTIFIED BY 'StrongP@ssw0rd'");
        rootConn.createStatement().executeUpdate(
            "GRANT ALL PRIVILEGES ON " + dbName + ".* TO 'appuser'@'localhost'");
        rootConn.createStatement().executeUpdate("FLUSH PRIVILEGES");
    }

    return DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/" + dbName + "?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true",
        "appuser", "StrongP@ssw0rd");
}


    public static PreparedStatement prepare(Connection connection, String query, Object... values) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            return statement;
        } catch (Exception e) {
            if (statement != null) {
                statement.close();
            }
            throw e;
        }
    }
}
