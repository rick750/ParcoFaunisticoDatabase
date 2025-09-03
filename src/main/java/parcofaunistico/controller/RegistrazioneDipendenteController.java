package parcofaunistico.controller;

import java.util.EnumMap;
import java.util.Map;

import javax.swing.JTextField;

import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class RegistrazioneDipendenteController {
    private Map<Parametri, JTextField> textFields;
    private final WritingModel writingModel;
    private RegistrazioneVisitatoreController visitatoreController;
    private String errorMessage;

    public RegistrazioneDipendenteController(final WritingModel writingModel) {
        this.writingModel = writingModel;
    }

    public void setData(final Map<Parametri, JTextField> fields) {
        this.textFields = fields;
        this.visitatoreController = new RegistrazioneVisitatoreController(writingModel, fields);
    }

    public boolean check() {
        if (! this.visitatoreController.check()){
            this.errorMessage = this.visitatoreController.getErrorMessage();
            return false;
        }

        if (!this.checkMansione()) {
            this.errorMessage = "La mansione e la descrizione non devono contenere numeri";
            return false;
        }

        if(!this.checkLavoro()) {
            this.errorMessage = "La zona di lavoro deve essere tra quelle registrate";
            return false;
        }

        return true;
    }

    private boolean checkMansione() {
        if (this.textFields.get(Parametri.MANSIONE).getText().matches("[\\p{L}']+") &&
             this.textFields.get(Parametri.DESCRIZIONE_MANSIONE).getText().matches("[\\p{L}'\\s]+")) {
                return true;
        }

        return false;
    }

    private boolean checkLavoro() {
        if(this.writingModel.checkArea(this.textFields.get(Parametri.NOME_ZONA).getText())) {
            return true;
        }

        return false;
    }

    public boolean executeInsertQuery() {
        final Map<Parametri, String> fields = new EnumMap<>(Parametri.class);
        final var entries = this.textFields.entrySet();
        for(final var entry: entries) {
            fields.put(entry.getKey(), entry.getValue().getText());
        }
        return this.writingModel.insertDipendente(fields);
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
