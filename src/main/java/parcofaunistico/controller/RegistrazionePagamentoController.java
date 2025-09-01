package parcofaunistico.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;

import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class RegistrazionePagamentoController {
    private static final String NOME_ZONA = "ZA01";
    private static final Double PREZZO_BASE = 20.0;
    private String errorMessage;
    private final String codiceFiscale;
    private final String codiceGruppo;
    private final String codiceTransazione;
    private final String codiceBiglietto;
    private String dataValidita;
    private Double prezzoEffettivo;
    private String codicePercorso;
    private final Date dataEffettuazione;
    private String codiceSconto;
    public RegistrazionePagamentoController(final WritingModel writingModel, final boolean persona, 
                                            final String codiceFiscale, final String codiceGruppo) {
        this.codiceFiscale = codiceFiscale;
        this.codiceGruppo = codiceGruppo;
        final LocalDate oggi = LocalDate.now();
        this.dataEffettuazione = Date.valueOf(oggi);

        String ultimo = writingModel.getLastPayment(); 
        String prefisso = ultimo.substring(0, 1);
        int numero = Integer.parseInt(ultimo.substring(1));
        numero++;
        this.codiceTransazione = prefisso + String.format("%03d", numero);

        if(persona) {
            final int eta = writingModel.getVisitatoreEta(codiceFiscale);
            System.out.println("Ho trovato come età: " + eta);
            if (eta <= 6) {
                this.codiceSconto = "S03";
            } else if( eta > 6 && eta <= 15) {
                this.codiceSconto = "S04";
            } else {
                this.codiceSconto = "nessuno";
            }
        }

        
        ultimo = writingModel.getLastTicket();
        System.out.println("ultimo biglietto trovato: " + ultimo);
        prefisso = ultimo.substring(0, 1);
        numero = Integer.parseInt(ultimo.substring(1));
        numero++;
        this.codiceBiglietto = prefisso + String.format("%04d", numero);

        if (!"nessuno".equals(codiceSconto)) {
            this.prezzoEffettivo = PREZZO_BASE - (PREZZO_BASE * writingModel.getDiscountPercent(codiceSconto)) / 100; 
        } else {
            this.prezzoEffettivo = PREZZO_BASE;
        }
        
    }

    public Map<Parametri, String> getPaymentData() {
        final Map<Parametri, String> dati = new EnumMap<>(Parametri.class);
        dati.put(Parametri.CODICE_TRANSAZIONE, this.codiceTransazione);
        dati.put(Parametri.CODICE_FISCALE, this.codiceFiscale);
        dati.put(Parametri.CODICE_GRUPPO, this.codiceGruppo);
        dati.put(Parametri.DATA_EFFETTUAZIONE, this.dataEffettuazione.toString());
        dati.put(Parametri.CODICE_SCONTO, this.codiceSconto);
        dati.put(Parametri.NOME_ZONA, NOME_ZONA);

        return dati;
    }

    public Map<Parametri, String> getTicketData() {
        final Map<Parametri, String> dati = new EnumMap<>(Parametri.class);
        dati.put(Parametri.CODICE_BIGLIETTO, this.codiceBiglietto);
        dati.put(Parametri.PREZZO_BASE, PREZZO_BASE.toString());
        dati.put(Parametri.PREZZO_EFFETTIVO, this.prezzoEffettivo.toString());
        dati.put(Parametri.NOME_ZONA, NOME_ZONA);
        return dati;
    }
}
