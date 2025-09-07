package parcofaunistico.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class DAOUtils {

    public static Connection localMySQLConnection(String dbName, String user, String pass) throws SQLException {
        String host = "localhost";
        String port = "3306";
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName
                + "?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
        return DriverManager.getConnection(url, user, pass);
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
