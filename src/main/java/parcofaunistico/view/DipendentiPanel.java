package parcofaunistico.view;

import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import parcofaunistico.controller.ReadingController;

public class DipendentiPanel extends JPanel {

    private final Optional<ReadingController> readContr;
    
    public DipendentiPanel(final ReadingController rContr, final String codiceFiscale) {
        this.readContr = Optional.of(rContr);
        setPreferredSize(this.getPreferredSize());
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(new JLabel("\n"));
        add(new JLabel("Parco Faunistico"));
        add(new JLabel("\n"));

        final var btnAree = new JButton("Pagina aree");
        btnAree.addActionListener(e -> {
            if (this.readContr.isPresent()) {
                readContr.get().userRequestedAree();
            }
        });

        final var btnZoneAmministrative = new JButton("Pagina Zone Amministrative");
        btnZoneAmministrative.addActionListener(e -> {
            if (readContr.isPresent()) {
                readContr.get().userRequestedZoneAmministrative();
            }
        });

        final var btnZoneRicreative = new JButton("Pagina Zone RIcreative");
        btnZoneRicreative.addActionListener(e -> {
            if (readContr.isPresent()) {
                readContr.get().userRequestedZoneRicreative();
            }
        });

        final var btnHabitat = new JButton("Pagina Habitat");
        btnHabitat.addActionListener(e -> {
            if (readContr.isPresent()) {
                readContr.get().userRequestedHabitat();
            }
        });

        final var btnSpecie = new JButton("Pagina Specie");
         btnSpecie.addActionListener(e -> {
            if (readContr.isPresent()) {
                readContr.get().userRequestedSpecie();
            }
        });

        final var btnEsemplari = new JButton("Pagina Esemplari");
        btnEsemplari.addActionListener(e -> {
            if (readContr.isPresent()) {
                readContr.get().userRequestedEsemplari();
            }
        });

        final var btnOrdini = new JButton("Pagina Ordini");
        btnOrdini.addActionListener(e -> {
            if (readContr.isPresent()) {
                readContr.get().userRequestedOrdini();
            }
        });

        final var btnProdotti = new JButton("Pagina prodotti");
        btnProdotti.addActionListener(e -> {
            if (readContr.isPresent()) {
                readContr.get().userRequestedProdotti();
            }
        });

        final var btnGiornateLavorative = new JButton("Pagina Giornate Lavorative");
        btnGiornateLavorative.addActionListener(e -> {
            if (readContr.isPresent()) {
                readContr.get().userRequestedGiornateLavorative(codiceFiscale);
            }
        });

        add(btnAree);
        add(new JLabel(" "));
        add(btnZoneAmministrative);
        add(new JLabel(" "));
        add(btnZoneRicreative);
        add(new JLabel(" "));
        add(btnHabitat);
        add(new JLabel(" "));
        add(btnSpecie);
        add(new JLabel(" "));
        add(btnEsemplari);
        add(new JLabel(" "));
        add(btnOrdini);
        add(new JLabel(" "));
        add(btnProdotti);
        add(new JLabel(" "));
        add(btnGiornateLavorative);
    }

}
