package parcofaunistico.model;

import java.sql.Connection;

public interface Model {

    static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }
}
