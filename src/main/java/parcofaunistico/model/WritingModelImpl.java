package parcofaunistico.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import parcofaunistico.data.Biglietto;
import parcofaunistico.data.GiornataLavorativa;
import parcofaunistico.data.Gruppo;
import parcofaunistico.data.Ordine;
import parcofaunistico.data.PagamentoBiglietto;
import parcofaunistico.data.Parametri;
import parcofaunistico.data.Percorso;
import parcofaunistico.data.Prodotto;
import parcofaunistico.data.Sconto;
import parcofaunistico.data.Visitatore;
import parcofaunistico.data.ZonaRicreativa;

public class WritingModelImpl implements WritingModel{
    
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
            System.out.println("codice fiscale: "  + partecipante.get(Parametri.CODICE_FISCALE) +
             " nome: " + partecipante.get(Parametri.NOME) +
             " cognome: " + partecipante.get(Parametri.COGNOME) +
             " età: " + partecipante.get(Parametri.ETA));
            fatto = this.insertVisitatore(partecipante);
            codiciFiscali.addLast(partecipante.get(Parametri.CODICE_FISCALE));
            System.out.println("Inserito visitatore: " + codiciFiscali.getLast());
        }
        if (fatto) {
            fatto = Gruppo.DAO.insert(connection, codiceGruppo, numPartecipanti, codiciFiscali);
            System.out.println("finiti gli inserimenti");
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
    public boolean insertGiornataLavorativa(String codiceFiscale) {
        return GiornataLavorativa.DAO.insert(connection, codiceFiscale);
    }

    @Override
    public boolean checkGiornataLavorativa(String codiceFiscale, String data) {
        return GiornataLavorativa.DAO.check(connection, codiceFiscale, data);
    }
}
