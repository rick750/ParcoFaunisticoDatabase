package parcofaunistico.view;

import java.awt.Component;
import java.awt.Dimension;
import java.time.LocalDate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import parcofaunistico.controller.ReadingController;
import parcofaunistico.model.WritingModel;

public class VisitatorePanel extends JPanel implements UserPanel{
    private final WritingModel writingModel;
    private final String codiceFiscale;
    public VisitatorePanel(final ReadingController readingController, final WritingModel writingModel,
                             final String codiceFiscale) {
        this.writingModel = writingModel;
        this.codiceFiscale = codiceFiscale;
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

        final var btnVisite = new JButton("Visite effettuate");
        btnVisite.addActionListener(e -> readingController.userRequestedVisiteEffettuate(codiceFiscale));
        centerButton(btnVisite);

         // Aggiunta del componente personalizzato
        final CampoConDescrizionePulsante componente = new CampoConDescrizionePulsante(
            "Inserire nuova area da visitare: ", "Aggiungi", this);
        this.add(Box.createVerticalStrut(10));
        this.add(componente);



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
        add(Box.createVerticalStrut(8));
        add(btnVisite);
        add(Box.createVerticalStrut(8));
        add(componente);
    }
    private static void centerButton(final JButton b) {
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        final Dimension pref = b.getPreferredSize();
        b.setMaximumSize(new Dimension(Math.max(pref.width, 220), pref.height));
    }

    @Override
    public void notifyUserPressButton(final String textInsert) {
        final var title = "Inserimento Visita";
        String message;
        if (! this.writingModel.insertVisita(codiceFiscale, textInsert)) {
            message = "L'area inserita è già stata visitata in data: " + String.valueOf(LocalDate.now());
        } else {
            message = "Nuova visita inserita correttamente per la data: " + String.valueOf(LocalDate.now());
        }
        
        final var dialog = new Dialog(title, message, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
