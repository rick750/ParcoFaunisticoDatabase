package parcofaunistico.controller;

import java.sql.Time;
import java.util.Map;

import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class RegistrazioneAreaController {
    
    private final WritingModel writingModel;
    private Parametri zonaSpecifica;
    private Map<Parametri, String> fields;
    private String errorMessage;

    public RegistrazioneAreaController(final WritingModel writingModel) {
        this.writingModel = writingModel;
    }

    public void setData(Parametri zonaSpecifica, Map<Parametri, String> fields) {
        this.zonaSpecifica = zonaSpecifica;
        this.fields = fields;
    }

    public boolean startChecks() {
        if(checkVoids()) {
            this.errorMessage = "Tutti i campi indicati devono essere compilati obbligatoriamente";
            return false;
        }

        Time inizio;
        Time fine;

        try {
            inizio = Time.valueOf(fields.get(Parametri.ORARIO_INIZIO));
            fine = Time.valueOf(fields.get(Parametri.ORARIO_FINE));
        } catch (IllegalArgumentException e) {
             this.errorMessage = "gli orari devono essere scritti nel formato: HH:MM:SS";
             return false;
        }
        
        if (! inizio.before(fine)) {
            errorMessage = "L'orario di inizio deve precedere quello di fine";
            return false;
        }
        return true;
    }

    private boolean checkVoids() {
        final var entries = this.fields.entrySet();
        for (final var entry: entries) {
            if ("".equals(entry.getValue())) {
                this.errorMessage = "Tutti i campi indicati devono essere compilati obbligatoriamente";
                return true;
            }
        }
        return false;
    }

    public boolean executeInsertQuery() {
        boolean fatto = false;
        String nome = "";
        switch (zonaSpecifica) {
            case NOME_ZONA_AMMINISTRATIVA -> {
                nome = this.writingModel.getLastZonaAmministrativa();
                final var prefisso = nome.substring(0, 2);
                var numero = Integer.parseInt(nome.substring(2));
                numero++;
                nome = prefisso + String.format("%02d", numero);
            }
            case NOME_ZONA_RICREATIVA -> {
                nome = this.writingModel.getLastZonaRicreativa();
                final var prefisso = nome.substring(0, 2);
                var numero = Integer.parseInt(nome.substring(2));
                numero++;
                nome = prefisso + String.format("%02d", numero);
            }
            case NOME_HABITAT -> {
                nome = this.writingModel.getLastHabitat();
                final var prefisso = nome.substring(0, 1);
                var numero = Integer.parseInt(nome.substring(1));
                numero++;
                nome = prefisso + String.format("%02d", numero);
            }
            default -> {
                this.errorMessage = "Il tipo di area inserito non è valido";
                return false;
            }
        }
        fatto = this.writingModel.insertArea(nome, zonaSpecifica, fields);
        if(fatto) {
                switch(zonaSpecifica) {
                case NOME_ZONA_AMMINISTRATIVA -> {
                    fatto = this.writingModel.insertZonaAmministrativa(nome);
                    if(!fatto) {
                        this.errorMessage = "errore con zona Amministrativa";
                        return false;
                    }
                }
                case NOME_ZONA_RICREATIVA -> {
                    this.writingModel.insertZonaRicreativa(nome);
                    if(!fatto) {
                        this.errorMessage = "errore con zona Ricreativa";
                        return false;
                    }
                }
                case NOME_HABITAT -> {
                    this.writingModel.insertHabitat(nome);
                    if(!fatto) {
                        this.errorMessage = "errore con Habitat";
                        return false;
                    }
                }
                default -> {}
            }
        }
        return fatto;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
