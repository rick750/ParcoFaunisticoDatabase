package parcofaunistico.controller;

import java.util.Objects;

import parcofaunistico.model.Model;

public class MainController {

    Model model;

    public MainController(final Model model) {
        Objects.requireNonNull(model, "Controller created with null model");
        this.model = model;
    }

    public Model getModel() {
        return this.model;
    }


}
