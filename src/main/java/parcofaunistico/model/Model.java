package parcofaunistico.model;

import java.sql.Connection;
import java.util.List;

import parcofaunistico.data.Esemplare;
import parcofaunistico.data.Visitatore;

public interface Model {

    List<Visitatore> loadPersone();

    List<Esemplare> loadEsemplari();

    static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }
}
