package parcofaunistico.controller;

import java.util.Map;

import javax.swing.JTextField;

import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class RegistrazioneVisitatoreController {
    private final Map<Parametri, JTextField> textfields;
    private final WritingModel writingModel;
    private String errorMessage;

    public RegistrazioneVisitatoreController(final WritingModel writingModel, final Map<Parametri, JTextField> fields) {
        this.textfields = fields;
        this.writingModel = writingModel;
    }

    public boolean check() {
        boolean check = this.checkVoids();
        if (check) {
            return false;
        }

        check = this.textfields.get(Parametri.CODICE_FISCALE).getText().length() == 16;
        if(!check) {
            this.errorMessage = "Il codicefiscale deve essere lungo esattamente 16 cifre";
            return false;
        }
        
        check = this.checkNomeCognome();
        if (!check) {
            this.errorMessage = "Il nome e cognome non devono contenere numeri o simboli";
            return false;
        }

        check =  this.checkEta();
        if(!check) {
            this.errorMessage = "l'età deve contenere solamente numeri senza spazi e deve avere un valore compreso tra 0 e 99";
            return false;
        }

        return true;
    }

    private boolean checkVoids() {
        final var entries = this.textfields.entrySet();
        for (final var entry: entries) {
            if ("".equals(entry.getValue().getText()) && !(entry.getKey().equals(Parametri.TELEFONO))
                && !(entry.getKey().equals(Parametri.EMAIL))) {
                this.errorMessage = "Tutti i campi indicati (tranne telefono e email) devono essere compilati obbligatoriamente";
                return true;
            }
        }
        return false;
    }

    private boolean checkNomeCognome() {
        if (this.textfields.get(Parametri.NOME).getText().matches("[\\p{L}' ]+") &&
             this.textfields.get(Parametri.COGNOME).getText().matches("[\\p{L}' ]+")) {
                return true;
        }

        return false;
    }

    private boolean checkEta() {
       if(this.textfields.get(Parametri.ETA).getText().matches("\\d+")) {
            if ( 0 < Integer.parseInt(this.textfields.get(Parametri.ETA).getText()) && 
                Integer.parseInt(this.textfields.get(Parametri.ETA).getText()) <= 99) {
                    return true;
                }
       }
       return false;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }


    public boolean executeInsertQuery() {
        return this.writingModel.insertVisitatore(textfields);
    }
}
