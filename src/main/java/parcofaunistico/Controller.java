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

    public void loadInitialPage() {
        this.view.showMainMenu();
    }

    public void userRequestedInitialPage() {
        this.loadInitialPage();
    }

    public void userRequestedPersone() {
        try {
            var persone = this.model.loadPersone();
            this.view.showPersone(persone);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void userRequestedEsemplari() {
        try {
            var esemplari = this.model.loadEsemplari();
            this.view.showEsemplari(esemplari);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void userRequestedMainMenu() {
        this.view.showMainMenu();
    }
}
