package parcofaunistico.controller;

import java.util.EnumMap;
import java.util.Map;

import javax.swing.JTextField;

import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class AggiornamentoAnimaliController {
    private final WritingModel writingModel;
    private Map<Parametri, JTextField> textFields;
    private boolean malato;
    private String errorMessage;
    private boolean newDieta;
    public AggiornamentoAnimaliController(final WritingModel writingModel) {
        this.writingModel = writingModel;
    }

    public void setData(final Map<Parametri, JTextField> fields, final boolean malato) {
        this.newDieta = false;
        this.textFields = fields;
        this.malato = malato;
    }

    public boolean startChecks() {
        if (this.checkVoids()) {
            return false;
        }

        if (! this.writingModel.checkSpecie(this.textFields.get(Parametri.NOME_SCIENTIFICO).getText())) {
            this.errorMessage = "Il nome scientifico della specie deve figurare tra quelli inseriti";
            return false;
        }

        if(!this.writingModel.checkEsemplareInSpecie(this.textFields.get(Parametri.NOME_ESEMPLARE).getText())) {
            this.errorMessage = "Questo nome per l'esemplare non è registrato all'interno della specie selezionata";
            return false;
        }

        if (!"".equals(this.textFields.get(Parametri.ALIMENTO).getText())) {
            if(!this.writingModel.checkDieta(this.textFields.get(Parametri.ALIMENTO).getText())) {
                this.newDieta = true;
                if(!this.checkNumeroPasti()) {
                    return false;
                }
            } else {
                this.newDieta = false;
            }
        }
        

        if (! this.checkEtaAltezzaPeso()) {
            return false;
        }
        return true;
    }


    private boolean checkVoids() {
        final var entries = this.textFields.entrySet();
        for (final var entry: entries) {
            if ("".equals(entry.getValue().getText()) && ((entry.getKey().equals(Parametri.NOME_SCIENTIFICO) ||
                (entry.getKey().equals(Parametri.NOME_ESEMPLARE))))) {
                this.errorMessage = " nome scientifico e nome esemplare devono essere compilati obbligatoriamente";
                return true;
            }
        }
        return false;
    }

    private boolean checkNumeroPasti() {
       if(this.textFields.get(Parametri.NUMERO_PASTI).getText().matches("\\d+")) {
            if ( 0 < Integer.parseInt(this.textFields.get(Parametri.NUMERO_PASTI).getText()) && 
                Integer.parseInt(this.textFields.get(Parametri.NUMERO_PASTI).getText()) <= 5) {
                    return true;
                }
       }
       this.errorMessage = "Il numero di pasti deve essere un numero compreso tra 1 e 5";
       return false;
    }

    private boolean checkEtaAltezzaPeso() {
        if(!"".equals(this.textFields.get(Parametri.ETA).getText())) {
            if (! this.textFields.get(Parametri.ETA).getText().matches("\\d+") || !(0 < Integer.parseInt(this.textFields.get(Parametri.ETA).getText()))) {
                this.errorMessage = "l'età deve essere un numero sopra lo zero";
                return false;
            }
        }
        if(!"".equals(this.textFields.get(Parametri.ALTEZZA).getText())) {
            if (! this.textFields.get(Parametri.ALTEZZA).getText().matches("\\d+") || !(0 < Integer.parseInt(this.textFields.get(Parametri.ALTEZZA).getText()))) {
                this.errorMessage = "l'altezza deve essere un numero sopra lo zero";
                return false;
            }
        }
        if(!"".equals(this.textFields.get(Parametri.PESO).getText())) {
            if (! this.textFields.get(Parametri.PESO).getText().matches("\\d+") || !(0 < Integer.parseInt(this.textFields.get(Parametri.PESO).getText()))) {
                this.errorMessage = "il peso deve essere un numero sopra lo zero";
                return false;
            }
        }
        return true;
    }

    public boolean executeInsertQuery() {
        final var fields = new EnumMap<Parametri, String>(Parametri.class);
        final var entries = this.textFields.entrySet();
        for(final var entry: entries) {
            fields.put(entry.getKey(), entry.getValue().getText());
        }
        fields.put(Parametri.MALATO, String.valueOf(this.malato));
        boolean fatto;

        if(newDieta) {
            fatto = this.writingModel.insertDieta(fields);
        }

        fatto = this.writingModel.updateEsemplare(fields);
        return fatto;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

}
