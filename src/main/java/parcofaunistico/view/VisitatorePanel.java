package parcofaunistico.view;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import parcofaunistico.controller.ReadingController;

public class VisitatorePanel extends JPanel{
    
    public VisitatorePanel(final ReadingController readingController) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final var btnZonaRicreativa = new JButton("Pagina Zona Ricreative");
        btnZonaRicreativa.addActionListener(e -> readingController.userRequestedZoneRicreative());
        final var btnHabitat = new JButton("Pagina Habitat");
        btnHabitat.addActionListener(e -> readingController.userRequestedHabitat());
        final var btnSpecie = new JButton("Pagina Specie");
        btnSpecie.addActionListener(e -> readingController.userRequestedSpecie());
        final var btnEsemplari = new JButton("Pagina Esemplari");
        btnEsemplari.addActionListener(e -> readingController.userRequestedEsemplari());
        final var btnProdotti = new JButton("Pagina Prodotti");
        btnProdotti.addActionListener(e -> readingController.userRequestedProdotti());
        final var btnSconti = new JButton("Pagina Sconti");
        btnSconti.addActionListener(e -> readingController.userRequestedSconti());

        this.add(btnZonaRicreativa);
        this.add(new JLabel(" "));
        this.add(btnHabitat);
        this.add(new JLabel(" "));
        this.add(btnSpecie);
        this.add(new JLabel(" "));
        this.add(btnEsemplari);
        this.add(new JLabel(" "));
        this.add(btnProdotti);
        this.add(new JLabel(" "));
        this.add(btnSconti);
    }
}
