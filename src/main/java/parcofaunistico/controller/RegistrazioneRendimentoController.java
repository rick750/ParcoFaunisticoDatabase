package parcofaunistico.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

import javax.swing.JTextField;

import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class RegistrazioneRendimentoController {
    private final WritingModel writingModel;
    private Map<Parametri, JTextField> textFields;
    private String errorMessage;

    public RegistrazioneRendimentoController(final WritingModel writingModel) {
        this.writingModel = writingModel;
    }

    public void setData(final Map<Parametri, JTextField> textfields) {
        this.textFields = textfields;
    }

    public boolean startChecks() {
        if (this.textFields == null) {
            this.errorMessage = "Dati mancanti";
            return false;
        }
        
        for (var entry : textFields.entrySet()) {
            final JTextField tf = entry.getValue();
            if (tf == null || tf.getText() == null || tf.getText().trim().isEmpty()) {
                this.errorMessage = "Tutti i campi devono essere compilati";
                return false;
            }
        }

        final String nomeArea = textFields.get(Parametri.NOME_ZONA_RICREATIVA).getText().trim();
        if (nomeArea == null || nomeArea.isEmpty() || !writingModel.checkZonaRicreativa(nomeArea)) {
            this.errorMessage = "L'area deve figurare tra quelle registrate";
            return false;
        }

        final String dataStr = textFields.get(Parametri.DATA_RENDIMENTO).getText().trim();
        Date d;
        try {
            d = Date.valueOf(dataStr); 
        } catch (IllegalArgumentException e) {
            this.errorMessage = "La data deve essere nel formato YYYY-MM-DD";
            return false;
        }

        final Date oggi = Date.valueOf(LocalDate.now());
        if (d.after(oggi)) {
            this.errorMessage = "La data non può essere nel futuro";
            return false;
        }

        return true;
    }

    public boolean executeInsertQuery() {
        try {
            final String nomeArea = textFields.get(Parametri.NOME_ZONA_RICREATIVA).getText().trim();
            final Date data = Date.valueOf(textFields.get(Parametri.DATA_RENDIMENTO).getText().trim());
            return this.writingModel.insertRendimento(nomeArea, data);
        } catch (NoSuchMethodError | AbstractMethodError e) {
            return false;
        } catch (Throwable t) {
            return false;
        }
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
