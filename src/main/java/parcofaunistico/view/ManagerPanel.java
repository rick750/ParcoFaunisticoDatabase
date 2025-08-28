package parcofaunistico.view;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import parcofaunistico.controller.ReadingController;

public class ManagerPanel extends JPanel{
    public ManagerPanel(final ReadingController readingController) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final var btnAree = new JButton("Pagina aree");
        btnAree.addActionListener(e -> readingController.userRequestedAree());
        final var btnZoneAmministrative = new JButton("Pagina Zone Amministrative");
        btnZoneAmministrative.addActionListener(e -> readingController.userRequestedZoneAmministrative());
        final var btnZonaRicreativa = new JButton("Pagina Zona Ricreative");
        btnZonaRicreativa.addActionListener(e -> readingController.userRequestedZoneRicreative());
        final var btnHabitat = new JButton("Pagina Habitat");
        btnHabitat.addActionListener(e -> readingController.userRequestedHabitat());
        final var btnVisitatori = new JButton("Pagina Visitatori");
        btnVisitatori.addActionListener(e -> readingController.userRequestedVisitatori());
        final var btnDipendenti = new JButton("Pagina Dipendenti");
        btnDipendenti.addActionListener(e -> readingController.userRequestedDipendenti());
        final var btnSpecie = new JButton("Pagina Specie");
        btnSpecie.addActionListener(e -> readingController.userRequestedSpecie());
        final var btnEsemplari = new JButton("Pagina Esemplari");
        btnEsemplari.addActionListener(e -> readingController.userRequestedEsemplari());
        final var btnOrdini = new JButton("Pagina Ordini");
        btnOrdini.addActionListener(e -> readingController.userRequestedOrdini());
        final var btnProdotti = new JButton("Pagina Prodotti");
        btnProdotti.addActionListener(e -> readingController.userRequestedProdotti());
        final var btnAcqProdotti = new JButton("Pagina acquisti prodotti");
        btnAcqProdotti.addActionListener(e -> readingController.userRequestedAcquistiProdotti());
        final var btnAffluenze = new JButton("Pagina Affluenze");
        btnAffluenze.addActionListener(e -> readingController.userRequestedAffluenze());
        final var btnAppSconto = new JButton("Pagina Applicazioni Sconto");
        btnAppSconto.addActionListener(e -> readingController.userRequestedApplicazioniSconto());
        final var btnClassProdotti = new JButton("Pagina classifica Prodotti");
        btnClassProdotti.addActionListener(e -> readingController.userRequestedClassificaProdotti());
        final var btnIncBiglietti = new JButton("Pagina Incassi Biglietti");
        btnIncBiglietti.addActionListener(e -> readingController.userRequestedIncassiBiglietti());
        final var btnRendimentiGiornalieri = new JButton("Pagina Rendimenti Giornalieri");
        btnRendimentiGiornalieri.addActionListener(e -> readingController.userRequestedRendimentiGiornalieri());

        this.add(btnAree);
        this.add(new JLabel(" "));
        this.add(btnZoneAmministrative);
        this.add(new JLabel(" "));
        this.add(btnZonaRicreativa);
        this.add(new JLabel(" "));
        this.add(btnHabitat);
        this.add(new JLabel(" "));
        this.add(btnVisitatori);
        this.add(new JLabel(" "));
        this.add(btnDipendenti);
        this.add(new JLabel(" "));
        this.add(btnSpecie);
        this.add(new JLabel(" "));
        this.add(btnEsemplari);
        this.add(new JLabel(" "));
        this.add(btnOrdini);
        this.add(new JLabel(" "));
        this.add(btnProdotti);
        this.add(new JLabel(" "));
        this.add(btnAcqProdotti);
        this.add(new JLabel(" "));
        this.add(btnAffluenze);
        this.add(new JLabel(" "));
        this.add(btnAppSconto);
        this.add(new JLabel(" "));
        this.add(btnClassProdotti);
        this.add(new JLabel(" "));
        this.add(btnIncBiglietti);
        this.add(new JLabel(" "));
        this.add(btnRendimentiGiornalieri);
        

        
    }
}
