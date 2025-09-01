package parcofaunistico.view;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import parcofaunistico.controller.ReadingController;

public class ManagerPanel extends JPanel{
    public ManagerPanel(final ReadingController readingController, final String codiceFiscale) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final var btnAree = new JButton("Pagina aree");
        btnAree.addActionListener(e -> readingController.userRequestedAree());
        centerButton(btnAree);

        final var btnZoneAmministrative = new JButton("Pagina Zone Amministrative");
        btnZoneAmministrative.addActionListener(e -> readingController.userRequestedZoneAmministrative());
        centerButton(btnZoneAmministrative);

        final var btnZonaRicreativa = new JButton("Pagina Zona Ricreative");
        btnZonaRicreativa.addActionListener(e -> readingController.userRequestedZoneRicreative());
        centerButton(btnZonaRicreativa);

        final var btnHabitat = new JButton("Pagina Habitat");
        btnHabitat.addActionListener(e -> readingController.userRequestedHabitat());
        centerButton(btnHabitat);

        final var btnVisitatori = new JButton("Pagina Visitatori");
        btnVisitatori.addActionListener(e -> readingController.userRequestedVisitatori());
        centerButton(btnVisitatori);

        final var btnDipendenti = new JButton("Pagina Dipendenti");
        btnDipendenti.addActionListener(e -> readingController.userRequestedDipendenti());
        centerButton(btnDipendenti);

        final var btnSpecie = new JButton("Pagina Specie");
        btnSpecie.addActionListener(e -> readingController.userRequestedSpecie());
        centerButton(btnSpecie);

        final var btnEsemplari = new JButton("Pagina Esemplari");
        btnEsemplari.addActionListener(e -> readingController.userRequestedEsemplari());
        centerButton(btnEsemplari);

        final var btnOrdini = new JButton("Pagina Ordini");
        btnOrdini.addActionListener(e -> readingController.userRequestedOrdini());
        centerButton(btnOrdini);

        final var btnProdotti = new JButton("Pagina Prodotti");
        btnProdotti.addActionListener(e -> readingController.userRequestedProdotti());
        centerButton(btnProdotti);

        final var btnAcqProdotti = new JButton("Pagina acquisti prodotti");
        btnAcqProdotti.addActionListener(e -> readingController.userRequestedAcquistiProdotti());
        centerButton(btnAcqProdotti);

        final var btnAffluenze = new JButton("Pagina Affluenze");
        btnAffluenze.addActionListener(e -> readingController.userRequestedAffluenze());
        centerButton(btnAffluenze);

        final var btnAppSconto = new JButton("Pagina Applicazioni Sconto");
        btnAppSconto.addActionListener(e -> readingController.userRequestedApplicazioniSconto());
        centerButton(btnAppSconto);

        final var btnClassProdotti = new JButton("Pagina classifica Prodotti");
        btnClassProdotti.addActionListener(e -> readingController.userRequestedClassificaProdotti());
        centerButton(btnClassProdotti);

        final var btnIncBiglietti = new JButton("Pagina Incassi Biglietti");
        btnIncBiglietti.addActionListener(e -> readingController.userRequestedIncassiBiglietti());
        centerButton(btnIncBiglietti);

        final var btnRendimentiGiornalieri = new JButton("Pagina Rendimenti Giornalieri");
        btnRendimentiGiornalieri.addActionListener(e -> readingController.userRequestedRendimentiGiornalieri());
        centerButton(btnRendimentiGiornalieri);

        add(btnAree);
        add(Box.createVerticalStrut(8));
        add(btnZoneAmministrative);
        add(Box.createVerticalStrut(8));
        add(btnZonaRicreativa);
        add(Box.createVerticalStrut(8));
        add(btnHabitat);
        add(Box.createVerticalStrut(8));
        add(btnVisitatori);
        add(Box.createVerticalStrut(8));
        add(btnDipendenti);
        add(Box.createVerticalStrut(8));
        add(btnSpecie);
        add(Box.createVerticalStrut(8));
        add(btnEsemplari);
        add(Box.createVerticalStrut(8));
        add(btnOrdini);
        add(Box.createVerticalStrut(8));
        add(btnProdotti);
        add(Box.createVerticalStrut(8));
        add(btnAcqProdotti);
        add(Box.createVerticalStrut(8));
        add(btnAffluenze);
        add(Box.createVerticalStrut(8));
        add(btnAppSconto);
        add(Box.createVerticalStrut(8));
        add(btnClassProdotti);
        add(Box.createVerticalStrut(8));
        add(btnIncBiglietti);
        add(Box.createVerticalStrut(8));
        add(btnRendimentiGiornalieri);
    }

    private static void centerButton(JButton b) {
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        Dimension pref = b.getPreferredSize();
        b.setMaximumSize(new Dimension(Math.max(pref.width, 240), pref.height));
    }
}
