package parcofaunistico.model;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import parcofaunistico.data.Area;
import parcofaunistico.data.Biglietto;
import parcofaunistico.data.Dieta;
import parcofaunistico.data.Dipendente;
import parcofaunistico.data.Esemplare;
import parcofaunistico.data.GiornataLavorativa;
import parcofaunistico.data.Gruppo;
import parcofaunistico.data.Habitat;
import parcofaunistico.data.Manutenzione;
import parcofaunistico.data.Ordine;
import parcofaunistico.data.PagamentoBiglietto;
import parcofaunistico.data.Parametri;
import parcofaunistico.data.Percorso;
import parcofaunistico.data.Prodotto;
import parcofaunistico.data.RendimentoGiornaliero;
import parcofaunistico.data.Sconto;
import parcofaunistico.data.Specie;
import parcofaunistico.data.Visitatore;
import parcofaunistico.data.ZonaAmministrativa;
import parcofaunistico.data.ZonaRicreativa;

public class WritingModelImpl implements WritingModel {

    private final Connection connection;

    public WritingModelImpl(final Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
    }

    @Override
    public boolean insertVisitatore(final Map<Parametri, String> textfields) {
        return Visitatore.DAO.insert(connection, textfields);
    }

    @Override
    public boolean insertVisita(final String codiceFiscale, final String nomeArea) {
        return Visitatore.DAO.insertNewVisita(connection, codiceFiscale, nomeArea);
    }

    @Override
    public boolean insertManutenzione(final Map<Parametri, String> textFields) {
        return Manutenzione.DAO.insert(connection, textFields);
    }

    public boolean updateManutenzione(final Map<Parametri, String> textFields) {
        return Manutenzione.DAO.update(connection, textFields);
    }

    @Override
    public boolean checkManutenzione(final String nomeZona) {
        return Manutenzione.DAO.check(connection, nomeZona);
    }

    @Override
    public Map<Parametri, String> getVisitatore(final String codiceFiscale) {
        return Visitatore.DAO.getVisitatore(connection, codiceFiscale);
    }

    @Override
    public Boolean checkVisitatore(final String codiceFiscale) {
        final var trovato = Visitatore.DAO.check(connection, codiceFiscale);
        return trovato;
    }

    @Override
    public boolean insertGruppo(final String codiceGruppo, final int numPartecipanti,
            final List<Map<Parametri, String>> partecipanti) {
        boolean fatto = false;

        final List<String> codiciFiscali = new ArrayList<>();
        for (final var partecipante : partecipanti) {
            if (!this.checkVisitatore(partecipante.get(Parametri.CODICE_FISCALE))) {
                fatto = this.insertVisitatore(partecipante);
            }
            codiciFiscali.addLast(partecipante.get(Parametri.CODICE_FISCALE));
        }
        if (fatto) {
            fatto = Gruppo.DAO.insert(connection, codiceGruppo, numPartecipanti, codiciFiscali);
        }

        return fatto;
    }

    @Override
    public boolean insertPagamentoBiglietto(final Map<Parametri, String> fields) {
        boolean fatto = PagamentoBiglietto.DAO.insert(connection, fields);
        fatto = Biglietto.DAO.insert(connection, fields);
        return fatto;
    }

    @Override
    public String getLastPayment() {
        return PagamentoBiglietto.DAO.getLast(connection);
    }

    @Override
    public String getLastTicket() {
        return Biglietto.DAO.getLast(connection);
    }

    @Override
    public Double getDiscountPercent(final String codiceSconto) {
        return Sconto.DAO.getPercentuale(connection, codiceSconto);
    }

    @Override
    public Boolean checkPathName(final String percorso) {
        return Percorso.DAO.check(connection, percorso);
    }

    @Override
    public String getLastGroupCode() {
        return Gruppo.DAO.getLast(connection);
    }

    @Override
    public boolean checkProdotto(final String codiceProdotto) {
        return Prodotto.DAO.check(connection, codiceProdotto);
    }

    @Override
    public boolean checkZonaRicreativa(final String nomeZona) {
        return ZonaRicreativa.DAO.check(connection, nomeZona);
    }

    @Override
    public String getNomeProdotto(final String codiceProdotto) {
        return Prodotto.DAO.getName(connection, codiceProdotto);
    }

    @Override
    public Double getPrezzoProdotto(final String codiceProdotto) {
        return Prodotto.DAO.getPrezzo(connection, codiceProdotto);
    }

    @Override
    public String getActualOrderCode() {
        return Ordine.DAO.getLast(connection);
    }

    @Override
    public boolean insertOrdine(final Map<Parametri, String> fields) {
        return Ordine.DAO.insert(connection, fields);
    }

    @Override
    public boolean insertGiornataLavorativa(final String codiceFiscale) {
        return GiornataLavorativa.DAO.insert(connection, codiceFiscale);
    }

    @Override
    public boolean checkGiornataLavorativa(final String codiceFiscale, final String data) {
        return GiornataLavorativa.DAO.check(connection, codiceFiscale, data);
    }

    @Override
    public boolean checkArea(final String nomeArea) {
        return Area.DAO.check(connection, nomeArea);
    }

    @Override
    public boolean insertDipendente(final Map<Parametri, String> fields) {
        return Dipendente.DAO.insert(connection, fields);
    }

    @Override
    public boolean checkDipendente(final String codiceFiscale) {
        return Dipendente.DAO.check(connection, codiceFiscale);
    }

    @Override
    public boolean checkSpecie(final String nome_scientifico) {
        return Specie.DAO.check(connection, nome_scientifico);
    }

    @Override
    public Map<Parametri, String> getSpecieFromEsemplare(final String nomeEsemplare) {
        return Specie.DAO.getFromEsemplare(connection, nomeEsemplare);
    }

    @Override
    public boolean checkEsemplare(final String nomeEsemplare) {
        return Esemplare.DAO.check(connection, nomeEsemplare);
    }

    @Override
    public boolean checkEsemplareInSpecie(final String nomeEsemplare) {
        return Esemplare.DAO.checkInSpecie(connection, nomeEsemplare);
    }

    @Override
    public boolean updateEsemplare(final Map<Parametri, String> fields) {
        return Esemplare.DAO.update(connection, fields);
    }

    @Override
    public boolean deleteEsemplare(final String nomeEsemplare) {
        return Esemplare.DAO.delete(connection, nomeEsemplare);
    }

    @Override
    public boolean checkDieta(final String alimento) {
        return Dieta.DAO.check(connection, alimento);
    }

    @Override
    public boolean insertSpecie(final Map<Parametri, String> fields) {
        return Specie.DAO.insert(connection, fields);
    }

    @Override
    public boolean updateSpecieCount(final String nomeScientifico, final Boolean adding) {
        if (adding) {
            return Specie.DAO.addEsemplare(connection, nomeScientifico);
        } else {
            return Specie.DAO.removeEsemplare(connection, nomeScientifico);
        }
    }

    @Override
    public boolean deleteSpecie(final String nomeScientifico) {
        return Specie.DAO.delete(connection, nomeScientifico);
    }

    @Override
    public boolean insertEsemplare(final Map<Parametri, String> fields) {
        return Esemplare.DAO.insert(connection, fields);
    }

    @Override
    public boolean insertDieta(final Map<Parametri, String> fields) {
        return Dieta.DAO.insert(connection, fields);
    }

    @Override
    public String getLastZonaAmministrativa() {
        return ZonaAmministrativa.DAO.getLast(connection);
    }

    @Override
    public boolean insertZonaAmministrativa(final String nome) {
        return ZonaAmministrativa.DAO.insert(connection, nome);
    }

    @Override
    public String getLastZonaRicreativa() {
        return ZonaRicreativa.DAO.getLast(connection);
    }

    @Override
    public boolean insertZonaRicreativa(final String nome) {
        return ZonaRicreativa.DAO.insert(connection, nome);
    }

    @Override
    public String getLastHabitat() {
        return Habitat.DAO.getLast(connection);
    }

    @Override
    public boolean insertHabitat(final String nome) {
        return Habitat.DAO.insert(connection, nome);
    }

    @Override
    public boolean insertArea(final String nome, final Parametri tipoZona, final Map<Parametri, String> fields) {
        return Area.DAO.insert(connection, nome, tipoZona, fields);
    }

    @Override
    public boolean insertProdotto(final Map<Parametri, String> fields) {
        return Prodotto.DAO.insert(connection, fields);
    }

    @Override
    public String getActualProductCode() {
        return Prodotto.DAO.getLast(connection);
    }

    @Override
    public boolean insertRendimento(String nomeArea, Date data) {
        return RendimentoGiornaliero.DAO.insert(connection, nomeArea, data);
    }
}
