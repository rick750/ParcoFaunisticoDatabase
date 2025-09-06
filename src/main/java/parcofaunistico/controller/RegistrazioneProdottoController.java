package parcofaunistico.controller;

import java.util.EnumMap;
import java.util.Map;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class RegistrazioneProdottoController {
    private Map<Parametri, JTextField> textFields;
    private String descrizione;
    private WritingModel writingModel;
    private String errorMessage;

    public RegistrazioneProdottoController(WritingModel writingModel) {
        this.writingModel = writingModel;
    }

    public void setData(final Map<Parametri, JTextField> fields, final JTextArea descrizione) {
        this.textFields = fields;
        this.descrizione = descrizione.getText();
    }

    public String getActualProductCode() {
        final var ultimoProdotto = this.writingModel.getActualProductCode();
        final var prefisso = ultimoProdotto.substring(0, 2);
        var numero = Integer.parseInt(ultimoProdotto.substring(2));
        numero++;
        final var nuovoProdotto = prefisso + String.format("%04d", numero);
        return nuovoProdotto;
    }

    public boolean startChecks() {
        boolean check = this.checkVoids();
        if (check) {
            return false;
        }
        check = this.checkQuantita();
        if (!check) {
            this.errorMessage = "La quantità deve essere un numero riportato senza spazi, eventualmente con parte decimale separate da . e > 0";
            return false;
        }

        return check;
    }

    private boolean checkVoids() {
        final var entries = this.textFields.entrySet();
        for (final var entry : entries) {
            if ("".equals(entry.getValue().getText())) {
                this.errorMessage = "Tutti i campi indicati devono essere compilati obbligatoriamente";
                return true;
            }
        }
        return false;
    }

    private boolean checkQuantita() {
        if (this.textFields.get(Parametri.PREZZO_PRODOTTO).getText().matches("\\d+(\\.\\d+)?")) {
            if (0 < Double.parseDouble(this.textFields.get(Parametri.PREZZO_PRODOTTO).getText())) {
                return true;
            }
        }
        return false;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public boolean executeInsertQuery() {
        final Map<Parametri, String> fields = new EnumMap<>(Parametri.class);

        fields.put(Parametri.CODICE_PRODOTTO, this.getActualProductCode());

        for (final var entry : this.textFields.entrySet()) {
            fields.put(entry.getKey(), entry.getValue().getText());
        }

        fields.put(Parametri.DESCRIZIONE_PRODOTTO, this.descrizione);

        return this.writingModel.insertProdotto(fields);
    }

}
