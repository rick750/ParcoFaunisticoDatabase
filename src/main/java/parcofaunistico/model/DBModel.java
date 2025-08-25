package parcofaunistico.model;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import parcofaunistico.data.Esemplare;
import parcofaunistico.data.Visitatore;

public final class DBModel implements Model {

    private final Connection connection;
    private Optional<List<Visitatore>> persone;
    private Optional<List<Esemplare>> esemplari;

    public DBModel(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
    }

    @Override
    public List<Visitatore> loadPersone() {
        var persone = Visitatore.DAO.list(this.connection);
        this.persone = Optional.of(persone);
        return persone;
    }

    @Override
    public List<Esemplare> loadEsemplari() {
        var esemplari = Esemplare.DAO.list(this.connection);
        this.esemplari = Optional.of(esemplari);
        return esemplari;
    }
}
