package parcofaunistico.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import parcofaunistico.controller.ReadingController;
import parcofaunistico.data.Pannelli;
import parcofaunistico.model.WritingModel;

public class ManagerPanel extends JPanel {

    public ManagerPanel(final ReadingController readingController,
                        final WritingModel writingModel,
                        final MainView mainView,
                        final String codiceFiscale) {

        // Pannello interno che conterrà tutti i pulsanti
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Creazione pulsanti come prima
        final var btnAree = new JButton("Pagina aree");
        btnAree.addActionListener(e -> readingController.userRequestedAree());
        centerButton(btnAree);
        contentPanel.add(btnAree);
        contentPanel.add(Box.createVerticalStrut(8));

        final var btnZoneAmministrative = new JButton("Pagina Zone Amministrative");
        btnZoneAmministrative.addActionListener(e -> readingController.userRequestedZoneAmministrative());
        centerButton(btnZoneAmministrative);
        contentPanel.add(btnZoneAmministrative);
        contentPanel.add(Box.createVerticalStrut(8));

        final var btnZonaRicreativa = new JButton("Pagina Zona Ricreative");
        btnZonaRicreativa.addActionListener(e -> readingController.userRequestedZoneRicreative());
        centerButton(btnZonaRicreativa);
        contentPanel.add(btnZonaRicreativa);
        contentPanel.add(Box.createVerticalStrut(8));

        final var btnHabitat = new JButton("Pagina Habitat");
        btnHabitat.addActionListener(e -> readingController.userRequestedHabitat());
        centerButton(btnHabitat);
        contentPanel.add(btnHabitat);
        contentPanel.add(Box.createVerticalStrut(8));

        final var btnVisitatori = new JButton("Pagina Visitatori");
        btnVisitatori.addActionListener(e -> readingController.userRequestedVisitatori());
        centerButton(btnVisitatori);
        contentPanel.add(btnVisitatori);
        contentPanel.add(Box.createVerticalStrut(8));

        final var btnDipendenti = new JButton("Pagina Dipendenti");
        btnDipendenti.addActionListener(e -> readingController.userRequestedDipendenti());
        centerButton(btnDipendenti);
        contentPanel.add(btnDipendenti);
        contentPanel.add(Box.createVerticalStrut(8));

        final var btnSpecie = new JButton("Pagina Specie");
        btnSpecie.addActionListener(e -> readingController.userRequestedSpecie());
        centerButton(btnSpecie);
        contentPanel.add(btnSpecie);
        contentPanel.add(Box.createVerticalStrut(8));

        final var btnEsemplari = new JButton("Pagina Esemplari");
        btnEsemplari.addActionListener(e -> readingController.userRequestedEsemplari());
        centerButton(btnEsemplari);
        contentPanel.add(btnEsemplari);
        contentPanel.add(Box.createVerticalStrut(8));

        final var btnOrdini = new JButton("Pagina Ordini");
        btnOrdini.addActionListener(e -> readingController.userRequestedOrdini());
        centerButton(btnOrdini);
        contentPanel.add(btnOrdini);
        contentPanel.add(Box.createVerticalStrut(8));

        final var btnProdotti = new JButton("Pagina Prodotti");
        btnProdotti.addActionListener(e -> readingController.userRequestedProdotti());
        centerButton(btnProdotti);
        contentPanel.add(btnProdotti);
        contentPanel.add(Box.createVerticalStrut(8));

        final var btnAcqProdotti = new JButton("Pagina acquisti prodotti");
        btnAcqProdotti.addActionListener(e -> readingController.userRequestedAcquistiProdotti());
        centerButton(btnAcqProdotti);
        contentPanel.add(btnAcqProdotti);
        contentPanel.add(Box.createVerticalStrut(8));

        final var btnAffluenze = new JButton("Pagina Affluenze");
        btnAffluenze.addActionListener(e -> readingController.userRequestedAffluenze());
        centerButton(btnAffluenze);
        contentPanel.add(btnAffluenze);
        contentPanel.add(Box.createVerticalStrut(8));

        final var btnAppSconto = new JButton("Pagina Applicazioni Sconto");
        btnAppSconto.addActionListener(e -> readingController.userRequestedApplicazioniSconto());
        centerButton(btnAppSconto);
        contentPanel.add(btnAppSconto);
        contentPanel.add(Box.createVerticalStrut(8));

        final var btnClassProdotti = new JButton("Pagina classifica Prodotti");
        btnClassProdotti.addActionListener(e -> readingController.userRequestedClassificaProdotti());
        centerButton(btnClassProdotti);
        contentPanel.add(btnClassProdotti);
        contentPanel.add(Box.createVerticalStrut(8));

        final var btnIncBiglietti = new JButton("Pagina Incassi Biglietti");
        btnIncBiglietti.addActionListener(e -> readingController.userRequestedIncassiBiglietti());
        centerButton(btnIncBiglietti);
        contentPanel.add(btnIncBiglietti);
        contentPanel.add(Box.createVerticalStrut(8));

        final var btnRendimentiGiornalieri = new JButton("Pagina Rendimenti Giornalieri");
        btnRendimentiGiornalieri.addActionListener(e -> readingController.userRequestedRendimentiGiornalieri());
        centerButton(btnRendimentiGiornalieri);
        contentPanel.add(btnRendimentiGiornalieri);
        contentPanel.add(Box.createVerticalStrut(8));

        final var btnDipendente = new JButton("Aggiungi nuovo dipendente");
        btnDipendente.addActionListener(e -> mainView.showPanel(Pannelli.REGISTRAZIONE_DIPENDENTE));
        centerButton(btnDipendente);
        contentPanel.add(btnDipendente);

        // ScrollPane che contiene il pannello dei pulsanti
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Layout principale: BorderLayout per riempire tutto
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    private static void centerButton(JButton b) {
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        Dimension pref = b.getPreferredSize();
        b.setMaximumSize(new Dimension(Math.max(pref.width, 200), pref.height));
    }
}
