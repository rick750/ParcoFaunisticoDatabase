package parcofaunistico.view;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import parcofaunistico.controller.ReadingController;

public class VisitatorePanel extends JPanel{
    
    public VisitatorePanel(final ReadingController readingController, final String codiceFiscale) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final var btnZonaRicreativa = new JButton("Pagina Zona Ricreative");
        btnZonaRicreativa.addActionListener(e -> readingController.userRequestedZoneRicreative());
        centerButton(btnZonaRicreativa);

        final var btnHabitat = new JButton("Pagina Habitat");
        btnHabitat.addActionListener(e -> readingController.userRequestedHabitat());
        centerButton(btnHabitat);

        final var btnSpecie = new JButton("Pagina Specie");
        btnSpecie.addActionListener(e -> readingController.userRequestedSpecie());
        centerButton(btnSpecie);

        final var btnEsemplari = new JButton("Pagina Esemplari");
        btnEsemplari.addActionListener(e -> readingController.userRequestedEsemplari());
        centerButton(btnEsemplari);

        final var btnProdotti = new JButton("Pagina Prodotti");
        btnProdotti.addActionListener(e -> readingController.userRequestedProdotti());
        centerButton(btnProdotti);

        final var btnSconti = new JButton("Pagina Sconti");
        btnSconti.addActionListener(e -> readingController.userRequestedSconti());
        centerButton(btnSconti);

        final var btnOrdini = new JButton("Ordini richiesti");
        btnOrdini.addActionListener(e -> readingController.userRequestedOrdiniVisitatore(codiceFiscale));
        centerButton(btnOrdini);

        add(btnZonaRicreativa);
        add(Box.createVerticalStrut(8));
        add(btnHabitat);
        add(Box.createVerticalStrut(8));
        add(btnSpecie);
        add(Box.createVerticalStrut(8));
        add(btnEsemplari);
        add(Box.createVerticalStrut(8));
        add(btnProdotti);
        add(Box.createVerticalStrut(8));
        add(btnSconti);
        add(Box.createVerticalStrut(8));
        add(btnOrdini);
    }
    private static void centerButton(JButton b) {
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        Dimension pref = b.getPreferredSize();
        b.setMaximumSize(new Dimension(Math.max(pref.width, 220), pref.height));
    }
}
