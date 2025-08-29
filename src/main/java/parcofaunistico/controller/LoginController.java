package parcofaunistico.controller;

import java.util.Objects;

import parcofaunistico.model.ReadingModel;

public class LoginController {
    
    private static final String NOME_MANAGER = "Manager";
    private static final String NOME_DIPENDENTE = "Dipendente";
    private static final String NOME_VISITATORE = "Visitatore";
    private String codiceManager = "RVIBNC00S51G273Y";

    private final ReadingModel model;

    public LoginController(final ReadingModel model) {
        Objects.requireNonNull(model, "Controller created with null model");
        this.model = model;
    }

    public boolean checkExistance(final String codiceFiscale, final String selected) {
        boolean trovato = false;
        try {
            switch (selected) {
                case NOME_VISITATORE:
                    trovato = this.model.checkVisitatore(codiceFiscale);
                    break;
                case NOME_DIPENDENTE:
                    trovato = this.model.checkDipendente(codiceFiscale);
                    break;
                case NOME_MANAGER:
                    trovato = this.codiceManager.equals(codiceFiscale);
                    break;
                default:
                    break;
            }
            System.out.println("\nTrovato = "+trovato);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return trovato;
    }

    public boolean checkValidity(final String codiceFiscale) {
        return codiceFiscale.length() == 16;
    }
}
