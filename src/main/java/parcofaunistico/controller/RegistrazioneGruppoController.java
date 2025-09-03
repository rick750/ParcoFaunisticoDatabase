package parcofaunistico.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JTextField;

import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class RegistrazioneGruppoController {
    private final WritingModel writingModel;
    private final List<Map<Parametri, String>> partecipanti;
    private int numPartecipanti;
    private String errorMessage;

    
    public RegistrazioneGruppoController(final WritingModel model) {
        this.writingModel = model;
        this.partecipanti = new ArrayList<>();
    }

    public String getActualGroupCode() {
        final var ultimo =  this.writingModel.getLastGroupCode();
        final var prefisso = ultimo.substring(0, 1);
        var numero = Integer.parseInt(ultimo.substring(1));
        numero++;
        return prefisso + String.format("%02d", numero);
    }

    public boolean checkVisitatoreExistance(final String codiceFiscale) {
        for(final var partecipante : this.partecipanti) {
            if (codiceFiscale.equals(partecipante.get(Parametri.CODICE_FISCALE))) {
                this.errorMessage = "Partecipante già inserito nel gruppo";
                return false;
            }
        }
        return this.writingModel.checkVisitatore(codiceFiscale);
    }

    public boolean checkVisitatore(final Map<Parametri, JTextField> fields) {
        for(final var partecipante : this.partecipanti) {
            if (fields.get(Parametri.CODICE_FISCALE).getText().equals(partecipante.get(Parametri.CODICE_FISCALE))) {
                this.errorMessage = "Partecipante già inserito nel gruppo";
                return false;
            }
        }
        final var visController = new RegistrazioneVisitatoreController(writingModel, fields);
        final var check = visController.check();
        if (check) {
            final var copy = new EnumMap<Parametri, String>(Parametri.class);
            for (var entry : fields.entrySet()) {
                copy.put(entry.getKey(), entry.getValue().getText());
            }
            this.partecipanti.addLast(copy);
            this.numPartecipanti ++;
        } else {
            this.errorMessage = visController.getErrorMessage();
        }
        return check;
    }

    public void addPartecipanteSingolo(final String codiceFiscale) {
        this.partecipanti.addLast(this.writingModel.getVisitatore(codiceFiscale));
        this.numPartecipanti++;
    }


    public void clearPartecipanti() {
        this.partecipanti.clear();
        this.numPartecipanti = 0;
        System.out.println("cancello i dati sui partecipanti");
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public boolean checkGruppo() {
        if (this.numPartecipanti < 2) {
            this.errorMessage = "I partecipanti devono essere almeno due";
            return false;
        }
        return true;
    }

    public boolean executeInsertQuery() {
        final var done = this.writingModel.insertGruppo(getActualGroupCode(), numPartecipanti, partecipanti);
        System.out.println("provo a inserire un gruppo con dati: " + getActualGroupCode() + " " + numPartecipanti);
        if (!done) {
            System.out.println("ho avuto problemi a creare il gruppo");
            return false;
        }
        this.clearPartecipanti();
        return done;
    }

    public int getNumPartecipanti() {
        return this.numPartecipanti;
    }
}
