package parcofaunistico.view;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Optional;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import parcofaunistico.controller.ReadingController;

public class DipendentiPanel extends JPanel {

    private final Optional<ReadingController> readContr;
    
    public DipendentiPanel(final ReadingController rContr, final String codiceFiscale) {
        this.readContr = Optional.of(rContr);
        setPreferredSize(this.getPreferredSize());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final var btnAree = new JButton("Pagina aree");
        centerButton(btnAree);
        btnAree.addActionListener(e -> readContr.ifPresent(ReadingController::userRequestedAree));

        final var btnZoneAmministrative = new JButton("Pagina Zone Amministrative");
        centerButton(btnZoneAmministrative);
        btnZoneAmministrative.addActionListener(e -> readContr.ifPresent(ReadingController::userRequestedZoneAmministrative));

        final var btnZoneRicreative = new JButton("Pagina Zone Ricreative");
        centerButton(btnZoneRicreative);
        btnZoneRicreative.addActionListener(e -> readContr.ifPresent(ReadingController::userRequestedZoneRicreative));

        final var btnHabitat = new JButton("Pagina Habitat");
        centerButton(btnHabitat);
        btnHabitat.addActionListener(e -> readContr.ifPresent(ReadingController::userRequestedHabitat));

        final var btnSpecie = new JButton("Pagina Specie");
        centerButton(btnSpecie);
        btnSpecie.addActionListener(e -> readContr.ifPresent(ReadingController::userRequestedSpecie));

        final var btnEsemplari = new JButton("Pagina Esemplari");
        centerButton(btnEsemplari);
        btnEsemplari.addActionListener(e -> readContr.ifPresent(ReadingController::userRequestedEsemplari));

        final var btnOrdini = new JButton("Pagina Ordini");
        centerButton(btnOrdini);
        btnOrdini.addActionListener(e -> readContr.ifPresent(ReadingController::userRequestedOrdini));

        final var btnProdotti = new JButton("Pagina prodotti");
        centerButton(btnProdotti);
        btnProdotti.addActionListener(e -> readContr.ifPresent(ReadingController::userRequestedProdotti));

        final var btnGiornateLavorative = new JButton("Pagina Giornate Lavorative");
        centerButton(btnGiornateLavorative);
        btnGiornateLavorative.addActionListener(e -> readContr.ifPresent(rc -> rc.userRequestedGiornateLavorative(codiceFiscale)));

        add(btnAree);
        add(Box.createVerticalStrut(8));
        add(btnZoneAmministrative);
        add(Box.createVerticalStrut(8));
        add(btnZoneRicreative);
        add(Box.createVerticalStrut(8));
        add(btnHabitat);
        add(Box.createVerticalStrut(8));
        add(btnSpecie);
        add(Box.createVerticalStrut(8));
        add(btnEsemplari);
        add(Box.createVerticalStrut(8));
        add(btnOrdini);
        add(Box.createVerticalStrut(8));
        add(btnProdotti);
        add(Box.createVerticalStrut(8));
        add(btnGiornateLavorative);
    }

    private static void centerButton(JButton b) {
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        Dimension pref = b.getPreferredSize();
        b.setMaximumSize(new Dimension(Math.max(pref.width, 220), pref.height));
    }

}
