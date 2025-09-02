package parcofaunistico.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;

import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;
import parcofaunistico.view.AcquistoBigliettoPanel;

public class RegistrazionePagamentoController {
    private static final String NOME_ZONA = "ZA01";
    private static final Double PREZZO_BASE = 20.0;
    private final String codiceGruppo;
    private final String codiceFiscale;
    private final String codiceTransazione;
    private final String codiceBiglietto;
    private Double prezzoEffettivo;
    private final Date dataEffettuazione;
    private String codiceSconto;
    private final AcquistoBigliettoPanel panel;
    private final WritingModel model;
    private Map<Parametri, String> datiPagamento;
    public RegistrazionePagamentoController(final WritingModel writingModel, final AcquistoBigliettoPanel panel, 
                                            final boolean persona, final String codiceFiscale,
                                            final int eta, final int numPartecipanti, final String codiceGruppo) {
        this.panel = panel;
        this.model = writingModel;
        this.codiceGruppo = codiceGruppo;
        this.codiceFiscale = codiceFiscale;
        final LocalDate oggi = LocalDate.now();
        this.dataEffettuazione = Date.valueOf(oggi);

        String ultimo = writingModel.getLastPayment(); 
        String prefisso = ultimo.substring(0, 1);
        int numero = Integer.parseInt(ultimo.substring(1));
        numero++;
        this.codiceTransazione = prefisso + String.format("%03d", numero);

        if(persona) {
            System.out.println("Ho trovato come età: " + eta);
            if (eta <= 6) {
                this.codiceSconto = "S03";
            } else if( eta > 6 && eta <= 15) {
                this.codiceSconto = "S04";
            } else {
                this.codiceSconto = "nessuno";
            }
        } else {
            System.out.println("Ho trovato come numero partecipanti: " + numPartecipanti);
            if (numPartecipanti >= 6 && numPartecipanti <= 15) {
                this.codiceSconto = "S01";
            } else if (numPartecipanti > 15) {
                this.codiceSconto = "S02";
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

    public void checkPathAndDate(final String percorso, final String dataValidita) {
        String errorMessage;
        try {
            final Date data = Date.valueOf(dataValidita);
            if (!(data.after(this.dataEffettuazione) || data.equals(this.dataEffettuazione))) {
                errorMessage = "La data deve essere la stessa oppure successiva al giorno corrente";
                panel.showErrorMessage(errorMessage);
            } else {
                if(! this.model.checkPathName(percorso)) {
                    errorMessage = "Il codice percorso inserito non figura tra quelli del parco";
                    panel.showErrorMessage(errorMessage);
                } else {
                    this.datiPagamento = this.getPaymentData();
                    datiPagamento.putAll(this.getTicketData());
                    datiPagamento.put(Parametri.DATA_VALIDITA, dataValidita);
                    datiPagamento.put(Parametri.CODICE_PERCORSO, percorso);
                    if ("nessuno".equals(datiPagamento.get(Parametri.CODICE_GRUPPO))) {
                        datiPagamento.put(Parametri.CODICE_GRUPPO, null);
                    }
                    if ("nessuno".equals(datiPagamento.get(Parametri.CODICE_FISCALE))) {
                        datiPagamento.put(Parametri.CODICE_FISCALE, null);
                    }
                    if ("nessuno".equals(datiPagamento.get(Parametri.CODICE_SCONTO))) {
                        datiPagamento.put(Parametri.CODICE_SCONTO, null);
                    }
                    this.panel.showConfirmDialog();
                }
            }
        } catch (IllegalArgumentException e) {
            errorMessage = "La data non è stata inserita nel formato previsto (anno/mese/giorno)";
            panel.showErrorMessage(errorMessage);
        }

        
    }

    public void executeInsertQuery() {
          if (!this.model.insertPagamentoBiglietto(datiPagamento)) {  
                final String errorMessage = "Errore nell'inserimento del nuovo pagamento visita";
                panel.showErrorMessage(errorMessage);
        }
    }
}
