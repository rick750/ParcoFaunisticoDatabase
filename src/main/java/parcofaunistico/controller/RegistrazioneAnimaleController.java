package parcofaunistico.controller;

import java.util.EnumMap;
import java.util.Map;

import javax.swing.JTextField;

import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class RegistrazioneAnimaleController {
    private final WritingModel writingModel;
    private Map<Parametri, JTextField> textFields;
    private boolean malato;
    private String errorMessage;
    private boolean newSpecie;
    private boolean newDieta;
    public RegistrazioneAnimaleController(final WritingModel writingModel) {
        this.writingModel = writingModel;
    }

    public void setData(final Map<Parametri, JTextField> fields, final boolean malato) {
        this.newSpecie = false;
        this.newDieta = false;
        this.textFields = fields;
        this.malato = malato;
    }

    public boolean startChecks() {
        if (this.checkVoids()) {
            return false;
        }

        if (! this.writingModel.checkSpecie(this.textFields.get(Parametri.NOME_SCIENTIFICO).getText())) {
            this.newSpecie = true;
            if (! this.checkIntegritàSpecie()) {
                return false;
            }
        } else {
            this.newSpecie = false;
        }

        if(this.writingModel.checkEsemplare(this.textFields.get(Parametri.NOME_ESEMPLARE).getText())) {
            this.errorMessage = "Questo nome per l'esemplare è già registrato";
            return false;
        }

        if(!this.writingModel.checkDieta(this.textFields.get(Parametri.ALIMENTO).getText())) {
            this.newDieta = true;
            if(!this.checkNumeroPasti()) {
                return false;
            }
        } else {
            this.newDieta = false;
        }

        if (! this.checkEtaAltezzaPeso()) {
            return false;
        }

        if (! this.checkSesso()) {
            return false;
        }
        return true;
    }


    private boolean checkVoids() {
        final var entries = this.textFields.entrySet();
        for (final var entry: entries) {
            if ("".equals(entry.getValue().getText()) && !(entry.getKey().equals(Parametri.NOME_COMUNE))
                && !(entry.getKey().equals(Parametri.ABITUDINI)) && !(entry.getKey().equals(Parametri.NUMERO_PASTI))
                && !(entry.getKey().equals(Parametri.NOME_HABITAT))) {
                this.errorMessage = "Tutti i campi indicati (tranne nome comune, abitudini, numero pasti e nome habitat) devono essere compilati obbligatoriamente";
                return true;
            }
        }
        return false;
    }

    private boolean checkIntegritàSpecie() {
        if(this.textFields.get(Parametri.NOME_COMUNE).getText().matches("[\\p{L}'\\s]+") &&
           this.textFields.get(Parametri.ABITUDINI).getText().matches("[\\p{L}'\\s]+") &&
           this.writingModel.checkArea(this.textFields.get(Parametri.NOME_HABITAT).getText())) {
            return true;
        } else {
            this.errorMessage = "Il nome comune e le abitudini della specie non devono contenere numeri e il nome habitat deve figuare tra quelli registrati";
            return false;
        }
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
        if(this.textFields.get(Parametri.ETA).getText().matches("\\d+") &&
            this.textFields.get(Parametri.ALTEZZA).getText().matches("\\d+")&&
            this.textFields.get(Parametri.PESO).getText().matches("\\d+")) {
            if ( 0 < Integer.parseInt(this.textFields.get(Parametri.ETA).getText()) &&
                0 < Double.parseDouble(this.textFields.get(Parametri.ALTEZZA).getText())&&
                0 < Double.parseDouble(this.textFields.get(Parametri.PESO).getText())) {
                    return true;
                }
       }
       this.errorMessage = "l'età, l'altezza e il peso devono essere numeri sopra lo zero";
       return false;
    }

    private boolean checkSesso() {
        if ("M".equals(this.textFields.get(Parametri.SESSO).getText()) || 
            "F".equals(this.textFields.get(Parametri.SESSO).getText())) {
                return true;
            } 
        this.errorMessage = "il sesso deve essere scritto come M oppure F";
        return false;
    }

    public boolean executeInsertQuery() {
        final var fields = new EnumMap<Parametri, String>(Parametri.class);
        final var entries = this.textFields.entrySet();
        for(final var entry: entries) {
            fields.put(entry.getKey(), entry.getValue().getText());
        }
        fields.put(Parametri.MALATO, String.valueOf(this.malato));
        boolean fatto;

        if(!newSpecie) {
            fatto = this.writingModel.updateSpecieCount(this.textFields.get(Parametri.NOME_SCIENTIFICO).getText(), true);
        }
        
        if(newSpecie) {
            fields.put(Parametri.NUMERO_ESEMPLARI, String.valueOf(1));
            fatto = this.writingModel.insertSpecie(fields);
        }

        if(newDieta) {
            fatto = this.writingModel.insertDieta(fields);
        }

        fatto = this.writingModel.insertEsemplare(fields);
        return fatto;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

}
