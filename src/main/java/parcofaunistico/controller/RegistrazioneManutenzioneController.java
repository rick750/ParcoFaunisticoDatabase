package parcofaunistico.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class RegistrazioneManutenzioneController {
     private final WritingModel writingModel;
    private Map<Parametri, String> textFields;
    private String errorMessage;
    private boolean newManutenzione;
    public RegistrazioneManutenzioneController(final WritingModel writingModel) {
        this.writingModel = writingModel;
    }

    public void setData(final Map<Parametri, String> fields) {
        this.newManutenzione = false;
        this.textFields = fields;
    }

    public boolean startChecks() {
        if (this.checkVoids()) {
            return false;
        }

        if (! this.writingModel.checkManutenzione(this.textFields.get(Parametri.NOME_ZONA))) {
            if (! this.checkIntegritàManutenzione()) {
                return false;
            }
            this.newManutenzione = true;
            return true;
        } else {
            if (! this.checkIntegritàManutenzione()) {
                return false;
            }
            this.newManutenzione = false;
            return true;
        }
    }


    private boolean checkVoids() {
        final var entries = this.textFields.entrySet();
        for (final var entry: entries) {
            if ("".equals(entry.getValue())) {
                this.errorMessage = "Tutti i campi indicati devono essere compilati obbligatoriamente";
                return true;
            }
        }
        return false;
    }

    private boolean checkIntegritàManutenzione() {
        final var oggi = Date.valueOf(LocalDate.now());
        Date inizio;
        Date fine;
        try {
            inizio = Date.valueOf(this.textFields.get(Parametri.DATA_INIZIO));
            fine = Date.valueOf(this.textFields.get(Parametri.DATA_FINE));
        } catch (IllegalArgumentException e) {
            this.errorMessage = "Le date devono essere scritte nel formato: YY-MM-DD";
            return false;
        }
        
        if((inizio.equals(oggi) || inizio.after(oggi)) && (oggi.equals(fine) || oggi.before(fine)) &&
         (inizio.before(fine) || inizio.equals(fine))) {
            return true;
        } else {
            this.errorMessage = "IL nome della zona deve figurare tra quelli già registrati e la data di inizio e di fine devono essere compatibili con la data odierna";
            return false;
        }
    }

    public boolean executeInsertQuery() {
        boolean fatto;

        if(! newManutenzione) {
            fatto = this.writingModel.updateManutenzione(textFields);
        } else {
            fatto = this.writingModel.insertManutenzione(textFields);
        }
        return fatto;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
