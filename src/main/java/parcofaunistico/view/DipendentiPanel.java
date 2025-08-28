package parcofaunistico.view;

import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import parcofaunistico.controller.ReadingController;

public class DipendentiPanel extends JPanel {

    private final Optional<ReadingController> readContr;
    
    public DipendentiPanel(final ReadingController rContr) {
        this.readContr = Optional.of(rContr);
        final var panel = new JPanel();
        panel.setPreferredSize(this.getPreferredSize());
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(new JLabel("\n"));
        panel.add(new JLabel("Parco Faunistico"));
        panel.add(new JLabel("\n"));

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

        panel.add(btnAree);
        panel.add(new JLabel(" "));
        panel.add(btnZoneAmministrative);
        panel.add(new JLabel(" "));
        panel.add(btnZoneRicreative);
        panel.add(new JLabel(" "));
        panel.add(btnHabitat);
        panel.add(new JLabel(" "));
        panel.add(btnSpecie);
        panel.add(new JLabel(" "));
        panel.add(btnEsemplari);
        panel.add(new JLabel(" "));
        panel.add(btnOrdini);
        panel.add(new JLabel(" "));
        panel.add(btnProdotti);
    }

}
