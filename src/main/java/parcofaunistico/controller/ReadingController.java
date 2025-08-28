package parcofaunistico.controller;

import parcofaunistico.data.BelongingQueries;
import parcofaunistico.data.DAOException;
import parcofaunistico.data.Queries;
import parcofaunistico.model.Model;
import parcofaunistico.view.MainView;

import java.util.Map;
import java.util.Objects;

public final class ReadingController {
    private final Model model;
    private final MainView view;
    private final Map<Integer, Queries> readingQueries;

    public ReadingController(Model model, MainView view) {
        Objects.requireNonNull(model, "Controller created with null model");
        Objects.requireNonNull(view, "Controller created with null view");
        this.view = view;
        this.model = model;
        this.readingQueries = BelongingQueries.getReadingQueries();
    }

    public void userRequestedPersone() {
        try {
            var persone = this.model.loadVisitatori();
            this.view.showPanel(persone, "Visitatori");
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void userRequestedEsemplari() {
        try {
            var esemplari = this.model.loadEsemplari();
            this.view.showPanel(esemplari, "Esemplari");
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void userRequestedAffluenze() {
        try {
            final var affluenze = this.model.loadAffluenze();
            this.view.showPanel(affluenze, "Affluenze");
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void userRequestedApplicazioniSconto() {
        try {
            final var applicazioni = this.model.loadApplicazioniSconto();
            this.view.showPanel(applicazioni, "Applicazioni Sconto");
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void userRequestedIncassiBiglietti() {
          try {
            final var incassi = this.model.loadIncassiBiglietti();
            this.view.showPanel(incassi, "Incassi Biglietti");
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void userRequestedClassificaProdotti() {
        try {
            final var incassi = this.model.loadClassificaProdotti();
            this.view.showPanel(incassi, "Classifica Prodotti più venduti");
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void userRequestedAcquistiProdotti() {
        try {
            final var acquisti = this.model.loadAcquistiProdotti();
            this.view.showPanel(acquisti, "NUmero di vendite dei prodotti");
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
