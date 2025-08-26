package parcofaunistico;

import parcofaunistico.data.DAOException;
import parcofaunistico.model.Model;
import java.util.Objects;

public final class Controller {
    private final Model model;
    private final MainView view;

    public Controller(Model model, MainView view) {
        Objects.requireNonNull(model, "Controller created with null model");
        Objects.requireNonNull(view, "Controller created with null view");
        this.view = view;
        this.model = model;
    }

    public void userRequestedPersone() {
        try {
            var persone = this.model.loadPersone();
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

}
