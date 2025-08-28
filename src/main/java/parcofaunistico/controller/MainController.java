package parcofaunistico.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import parcofaunistico.data.Queries;
import parcofaunistico.model.Model;

public class MainController {

    Model model;

    private static final List<Queries> QUERIES_DIPENDENTE = new ArrayList<>(Arrays.asList(Queries.SHOW_AREE, Queries.SHOW_ZONA_AMMINISTRATIVA,
    Queries.SHOW_ZONA_RICREATIVA, Queries.SHOW_HABITAT,Queries.SHOW_ESEMPLARE, Queries.SHOW_SPECIE, Queries.SHOW_ORDINE,
    Queries.SHOW_PRODOTTO));

    private static final List<Queries> QUERIES_VISITATORE = new ArrayList<>(Arrays.asList(Queries.SHOW_ZONA_RICREATIVA, Queries.SHOW_HABITAT,
    Queries.SHOW_ESEMPLARE, Queries.SHOW_SPECIE, Queries.SHOW_PRODOTTO, Queries.SHOW_SCONTO));

    private static final List<Queries> QUERIES_MANAGER = new ArrayList<>(Arrays.asList(Queries.SHOW_AREE, Queries.SHOW_ZONA_AMMINISTRATIVA,
    Queries.SHOW_ZONA_RICREATIVA, Queries.SHOW_HABITAT,Queries.SHOW_ESEMPLARE, Queries.SHOW_SPECIE, Queries.SHOW_ORDINE,
    Queries.SHOW_PRODOTTO, Queries.SHOW_ACQUISTI_PRODOTTO, Queries.SHOW_AFFLUENZA, Queries.SHOW_APPLICAZIONI_SCONTI,
    Queries.SHOW_CLASSIFICA_PRODOTTI, Queries.SHOW_INCASSI_BIGLIETTI, Queries.SHOW_DIPENDENTE, Queries.SHOW_VISITATORE,
    Queries.SHOW_RENDIMENTO_GIORNALIERO));

    public MainController(final Model model) {
        Objects.requireNonNull(model, "Controller created with null model");
        this.model = model;
    }

    public Model getModel() {
        return this.model;
    }

    public List<Queries> getManagerQueries() {
        return QUERIES_MANAGER;
    }

    public List<Queries> getDipendenteQueries() {
        return QUERIES_DIPENDENTE;
    }

    public List<Queries> getVisitatoreQueries() {
        return QUERIES_VISITATORE;
    }
}
