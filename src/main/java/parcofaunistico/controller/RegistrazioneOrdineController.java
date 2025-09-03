package parcofaunistico.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JTextField;

import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class RegistrazioneOrdineController {
    private Map<Parametri, JTextField> textFields;
    private final WritingModel writingModel;
    private String errorMessage;

    public RegistrazioneOrdineController(final WritingModel writingModel) {
        this.writingModel = writingModel;
    }

    public void setData(final Map<Parametri, JTextField> fields) {
        this.textFields = fields;
    }

    public boolean check() {
        boolean check = this.checkVoids();
        if (check) {
            return false;
        }

        check = this.textFields.get(Parametri.CODICE_FISCALE).getText().length() == 16;
        if(!check) {
            this.errorMessage = "Il codicefiscale deve essere lungo esattamente 16 cifre";
            return false;
        }
        
        check = this.writingModel.checkVisitatore(this.textFields.get(Parametri.CODICE_FISCALE).getText());
         if(!check) {
            this.errorMessage = "Il codicefiscale deve appartenere a un visitatore registrato";
            return false;
        }

         check = this.writingModel.checkProdotto(this.textFields.get(Parametri.CODICE_PRODOTTO).getText());
         if(!check) {
            this.errorMessage = "Il codice prodotto deve appartenere a un prodotto registrato";
            return false;
        }

        check = this.checkQuantita();
        if(!check) {
            this.errorMessage = "La quantità deve essere un numero riportato senza spazi e > 0";
            return false;
        }

        check = this.writingModel.checkZonaRicreativa(this.textFields.get(Parametri.NOME_ZONA).getText());
        if(!check) {
            this.errorMessage = "Il nome della zona ricreativa deve essere già registrato";
            return false;
        }
        return true;
    }

    private boolean checkVoids() {
        final var entries = this.textFields.entrySet();
        for (final var entry: entries) {
            if ("".equals(entry.getValue().getText())) {
                this.errorMessage = "Tutti i campi indicati devono essere compilati obbligatoriamente";
                return true;
            }
        }
        return false;
    }

     private boolean checkQuantita() {
       if(this.textFields.get(Parametri.QUANTITA).getText().matches("\\d+")) {
            if ( 0 < Integer.parseInt(this.textFields.get(Parametri.QUANTITA).getText())) {
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
        final var data = Date.valueOf(LocalDate.now());
        fields.put(Parametri.DATA_EFFETTUAZIONE, String.valueOf(data));

        final var ultimoOrdine = this.writingModel.getActualOrderCode();
        final var prefisso = ultimoOrdine.substring(0, 1);
        var numero = Integer.parseInt(ultimoOrdine.substring(1));
        numero++;
        final var ordineNuovo = prefisso + String.format("%06d", numero);
        fields.put(Parametri.CODICE_ORDINE, ordineNuovo);

        final var entries = this.textFields.entrySet();
        for(final var entry: entries) {
            fields.put(entry.getKey(), entry.getValue().getText());
        }

        return this.writingModel.insertOrdine(fields);
    }

    public Map<Parametri, String> getAutomaticFields() {
        final Map<Parametri, String> automaticFields = new EnumMap<>(Parametri.class);
        final var nomeProd = this.writingModel.getNomeProdotto(this.textFields.get(Parametri.CODICE_PRODOTTO).getText());
        final Double prezzoProd = this.writingModel.getPrezzoProdotto(this.textFields.get(Parametri.CODICE_PRODOTTO).getText());
        final var prezzoFin = (double) Integer.parseInt(this.textFields.get(Parametri.QUANTITA).getText()) * prezzoProd;
        final var ultimoOrdine = this.writingModel.getActualOrderCode();
        final var prefisso = ultimoOrdine.substring(0, 1);
        var numero = Integer.parseInt(ultimoOrdine.substring(1));
        numero++;
        final var ordineNuovo = prefisso + String.format("%06d", numero);
        automaticFields.put(Parametri.NOME_PRODOTTO, nomeProd);
        automaticFields.put(Parametri.PREZZO_BASE, String.valueOf(prezzoProd));
        automaticFields.put(Parametri.PREZZO_EFFETTIVO, String.valueOf(prezzoFin));
        automaticFields.put(Parametri.CODICE_ORDINE, ordineNuovo);

        return automaticFields;
    }
}
