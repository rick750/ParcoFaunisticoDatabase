package parcofaunistico.model;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;

import parcofaunistico.data.AcquistiProdotto;
import parcofaunistico.data.Affluenza;
import parcofaunistico.data.ApplicazioneSconto;
import parcofaunistico.data.ClassificaProdotto;
import parcofaunistico.data.Esemplare;
import parcofaunistico.data.IncassoBiglietto;
import parcofaunistico.data.Visitatore;

public final class DBModel implements Model {

    private final Connection connection;

    public DBModel(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
    }

    @Override
    public List<Visitatore> loadPersone() {
        var persone = Visitatore.DAO.list(this.connection);
        return persone;
    }

    @Override
    public List<Esemplare> loadEsemplari() {
        var esemplari = Esemplare.DAO.list(this.connection);
        return esemplari;
    }

    @Override
    public List<Affluenza> loadAffluenze() {
        final var affluenze = Affluenza.DAO.list(this.connection);
        return affluenze;
    }

    @Override
    public List<ApplicazioneSconto> loadApplicazioniSconto() {
        final var applicazioniSconto = ApplicazioneSconto.DAO.list(this.connection);
        return applicazioniSconto;
    }

    @Override
    public List<IncassoBiglietto> loadIncassiBiglietti() {
        final var incassi = IncassoBiglietto.DAO.list(this.connection);
        return incassi;
    }

    @Override
    public List<ClassificaProdotto> loadClassificaProdotti() {
        final var classifica = ClassificaProdotto.DAO.list(this.connection);
        return classifica;
    }

    @Override
    public List<AcquistiProdotto> loadAcquistiProdotti() {
        final var acquisti = AcquistiProdotto.DAO.list(this.connection);
        return acquisti;
    }
}
