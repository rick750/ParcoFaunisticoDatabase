package parcofaunistico.controller;

import parcofaunistico.data.DAOException;
import parcofaunistico.model.ReadingModel;
import parcofaunistico.view.MainView;

import java.util.Objects;

public final class ReadingController {
    private final ReadingModel model;
    private final MainView view;

    public ReadingController(ReadingModel model, MainView view) {
        Objects.requireNonNull(model, "Controller created with null model");
        Objects.requireNonNull(view, "Controller created with null view");
        this.view = view;
        this.model = model;
    }

    public void userRequestedPersone() {
        try {
            var persone = this.model.loadVisitatori();
            this.view.showVociPanel(persone, "Visitatori");
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void userRequestedEsemplari() {
        try {
            var esemplari = this.model.loadEsemplari();
            this.view.showVociPanel(esemplari, "Esemplari");
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void userRequestedAffluenze() {
        try {
            final var affluenze = this.model.loadAffluenze();
            this.view.showVociPanel(affluenze, "Affluenze");
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void userRequestedApplicazioniSconto() {
        try {
            final var applicazioni = this.model.loadApplicazioniSconto();
            this.view.showVociPanel(applicazioni, "Applicazioni Sconto");
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void userRequestedIncassiBiglietti() {
          try {
            final var incassi = this.model.loadIncassiBiglietti();
            this.view.showVociPanel(incassi, "Incassi Biglietti");
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void userRequestedClassificaProdotti() {
        try {
            final var incassi = this.model.loadClassificaProdotti();
            this.view.showVociPanel(incassi, "Classifica Prodotti più venduti");
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void userRequestedAcquistiProdotti() {
        try {
            final var acquisti = this.model.loadAcquistiProdotti();
            this.view.showVociPanel(acquisti, "Numero di vendite dei prodotti");
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public void userRequestedAree() {
       try {
         final var aree = this.model.loadAree();
         this.view.showVociPanel(aree, "Aree del parco");
       } catch (Exception e) {
            e.printStackTrace();
       }
    }

    public void userRequestedVisiteEffettuate(final String codiceFiscale) {
        try {
         final var visite = this.model.loadVisiteEffettuate(codiceFiscale);
         this.view.showVociPanel(visite, "Visite effettuate dal visitatore: " + codiceFiscale);
       } catch (Exception e) {
            e.printStackTrace();
       }
    }

    public void userRequestedZoneAmministrative() {
        try {
            final var zoneAmm = this.model.loadZoneAmministrative();
            this.view.showVociPanel(zoneAmm, "Zone Amministrative del parco");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void userRequestedZoneRicreative() {
        try {
            final var zoneRic = this.model.loadZoneRicreative();
            this.view.showVociPanel(zoneRic, "Zone Ricreative del parco");
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public void userRequestedHabitat() {
        final var habitat = this.model.loadHabitat();
        this.view.showVociPanel(habitat, "Habitat del parco");
    }

    public void userRequestedSpecie() {
        final var specie = this.model.loadSpecie();
        this.view.showVociPanel(specie, "Specie registrate");
    }

    public void userRequestedOrdini() {
        final var ordini = this.model.loadOrdini();
        this.view.showVociPanel(ordini, "Ordini registrati");
    }

    public void userRequestedProdotti() {
        final var prodotti = this.model.loadProdotti();
        this.view.showVociPanel(prodotti, "Prodotti in vendita");
    }

    public void userRequestedSconti() {
        final var sconti = this.model.loadSconti();
        this.view.showVociPanel(sconti, "Tipologie di sconto");
    }

    public void userRequestedVisitatori() {
        final var visitatori = this.model.loadVisitatori();
        this.view.showVociPanel(visitatori, "Visitatori del parco registrati");
    }

    public void userRequestedDipendenti() {
        final var dipendenti = this.model.loadDipendenti();
        this.view.showVociPanel(dipendenti, "Dipendenti del parco registrati");
    }

    public void userRequestedRendimentiGiornalieri() {
        final var rendimenti = this.model.loadRendimentiGiornalieri();
        this.view.showVociPanel(rendimenti, "Rendimenti Giornalieri delle zone Ricreative");
    }

    public void userRequestedGiornateLavorative(final String codiceFiscale) {
        final var giornateLavorative = this.model.loadGiornateLavorative(codiceFiscale);
        this.view.showVociPanel(giornateLavorative, "Giornate lavorative timbrate dal dipendente: " + codiceFiscale);
    }

    public void userRequestedOrdiniVisitatore(final String codiceFiscale) {
        final var ordiniVisitatore = this.model.loadOrdiniVisitatore(codiceFiscale);
        this.view.showVociPanel(ordiniVisitatore, "Ordini richiesti dal visitatore: " + codiceFiscale);
    }

    public void userRequestedMedieGiornaliere() {
        final var medieGiornaliere = this.model.loadMedieGiornaliere();
        this.view.showVociPanel(medieGiornaliere, "Visite medie giornaliere per area: ");
    }
    
}
