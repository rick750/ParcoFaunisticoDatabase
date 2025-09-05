package parcofaunistico.model;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;

import parcofaunistico.data.AcquistiProdotto;
import parcofaunistico.data.Affluenza;
import parcofaunistico.data.ApplicazioneSconto;
import parcofaunistico.data.Area;
import parcofaunistico.data.ClassificaProdotto;
import parcofaunistico.data.Dipendente;
import parcofaunistico.data.Esemplare;
import parcofaunistico.data.GiornataLavorativa;
import parcofaunistico.data.Habitat;
import parcofaunistico.data.IncassoBiglietto;
import parcofaunistico.data.Manutenzione;
import parcofaunistico.data.Ordine;
import parcofaunistico.data.Prodotto;
import parcofaunistico.data.RendimentoGiornaliero;
import parcofaunistico.data.Sconto;
import parcofaunistico.data.Specie;
import parcofaunistico.data.StatisticheVisiteArea;
import parcofaunistico.data.Visita;
import parcofaunistico.data.Visitatore;
import parcofaunistico.data.ZonaAmministrativa;
import parcofaunistico.data.ZonaRicreativa;

public final class ReadingModelImpl implements ReadingModel {

    private final Connection connection;

    public ReadingModelImpl(final Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
    }

    @Override
    public List<Visitatore> loadVisitatori() {
        final var visitatori = Visitatore.DAO.list(this.connection);
        return visitatori;
    }

    @Override
    public List<Visita> loadVisiteEffettuate(String codiceFiscale) {
        return Visita.DAO.list(connection, codiceFiscale);
    }

    @Override
    public List<Esemplare> loadEsemplari() {
        final var esemplari = Esemplare.DAO.list(this.connection);
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

    @Override
    public Boolean checkVisitatore(final String codiceFiscale) {
        final var trovato = Visitatore.DAO.check(connection, codiceFiscale);
        return trovato;
    }

    @Override
    public Boolean checkDipendente(final String codiceFiscale) {
        final var trovato = Dipendente.DAO.check(connection, codiceFiscale);
        return trovato;
    }

    @Override
    public List<Area> loadAree() {
        return Area.DAO.list(this.connection);
    }

    @Override
    public List<Manutenzione> loadManutenzioni() {
        return Manutenzione.DAO.list(connection);
    }

    @Override
    public List<ZonaAmministrativa> loadZoneAmministrative() {
        return ZonaAmministrativa.DAO.list(this.connection);
    }

    @Override
    public List<ZonaRicreativa> loadZoneRicreative() {
        return ZonaRicreativa.DAO.list(this.connection);
    }

    @Override
    public List<Habitat> loadHabitat() {
        return Habitat.DAO.list(this.connection);
    }

    @Override
    public List<Dipendente> loadDipendenti() {
        return Dipendente.DAO.list(this.connection);
    }

    @Override
    public List<Specie> loadSpecie() {
        return Specie.DAO.list(this.connection);
    }

    @Override
    public List<Ordine> loadOrdini() {
        return Ordine.DAO.list(this.connection);
    }

    @Override
    public List<Prodotto> loadProdotti() {
        return Prodotto.DAO.list(this.connection);
    }

    @Override
    public List<RendimentoGiornaliero> loadRendimentiGiornalieri() {
        return RendimentoGiornaliero.DAO.list(this.connection);
    }

    @Override
    public List<Sconto> loadSconti() {
        return Sconto.DAO.list(this.connection);
    }

    @Override
    public List<GiornataLavorativa> loadGiornateLavorative(final String codiceFiscale) {
        return GiornataLavorativa.DAO.list(this.connection, codiceFiscale);
    }

    @Override
    public List<Ordine> loadOrdiniVisitatore(final String codiceFiscale) {
        return Ordine.DAO.getVisitatoreOrdini(connection, codiceFiscale);
    }

    @Override
    public List<StatisticheVisiteArea> loadMedieGiornaliere() {
        return StatisticheVisiteArea.DAO.list(connection);
    }
}
