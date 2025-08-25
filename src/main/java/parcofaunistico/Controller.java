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

    void loadInitialPage() {
        try {
            this.view.loadingPersone();
            var persone = this.model.loadPersone();
            this.view.showPersone(persone);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    
    public void userRequestedInitialPage() {
        this.loadInitialPage();
    }

}
