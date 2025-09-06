package parcofaunistico.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import parcofaunistico.controller.ReadingController;
import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class DipendentiPanel extends JPanel implements UserPanel {
    private final Optional<ReadingController> readContr;
    private final WritingModel writingModel;

    public DipendentiPanel(final ReadingController rContr, final WritingModel writingModel,
                           final MainView mainView, final String codiceFiscale) {
        this.readContr = Optional.of(rContr);
        this.writingModel = writingModel;

        // Layout principale con BorderLayout
        setLayout(new BorderLayout());

        // Pannello interno scrollabile
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] nomeCognome = readContr.get().getNomeCognomeFromDipendente(codiceFiscale);
        String nome = nomeCognome[0];
        String cognome = nomeCognome[1];
        JLabel lblBenvenuto = new JLabel("Benvenuto/a " + nome +" "+ cognome);
        lblBenvenuto.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblBenvenuto.setFont(UIManager.getFont("BigLabel.font"));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(lblBenvenuto);
        contentPanel.add(Box.createVerticalStrut(10));

        // Pulsanti
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

        final var btnNuovoOrdine = new JButton("Nuovo ordine visitatore");
        centerButton(btnNuovoOrdine);
        btnNuovoOrdine.addActionListener(e -> mainView.showPanel(Pannelli.ORDINE));

        final var btnGiornataLavorativa = new JButton("Aggiungi giornata lavorativa");
        centerButton(btnGiornataLavorativa);
        btnGiornataLavorativa.addActionListener(e -> {
            final String title = "Inserimento giornata lavorativa";
            String message;
            if (writingModel.insertGiornataLavorativa(codiceFiscale)) {
                message = "Giornata lavorativa inserita correttamente";
            } else {
                message = "Problemi durante l'inserimento di una nuova giornata lavorativa";
            }
            final Dialog dialog = new Dialog(title, message, true);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });

        if (writingModel.checkGiornataLavorativa(codiceFiscale, LocalDate.now().toString())) {
            btnGiornataLavorativa.setEnabled(false);
        }

        final LocalTime oraAttuale = LocalTime.now();
        final LocalTime inizio = LocalTime.of(9, 0);
        final LocalTime fine = LocalTime.of(18, 0);

        if (oraAttuale.isBefore(inizio) || oraAttuale.isAfter(fine)) {
            btnGiornataLavorativa.setEnabled(false);
        }

        final var btnManutenzione = new JButton("Aggiungi/Aggiorna Manutenzione");
        centerButton(btnManutenzione);
        btnManutenzione.addActionListener(e -> mainView.showPanel(Pannelli.REGISTRAZIONE_MANUTENZIONE));

        final var btnAddAnimale = new JButton("Aggiungi Specie/Esemplare");
        centerButton(btnAddAnimale);
        btnAddAnimale.addActionListener(e -> mainView.showPanel(Pannelli.REGISTRAZIONE_SPECIE_ESEMPLARE));

        final var btnModificaAnimale = new JButton("Modifica Esemplare");
        centerButton(btnModificaAnimale);
        btnModificaAnimale.addActionListener(e -> mainView.showPanel(Pannelli.MODIFICA_ESEMPLARE));

        final var btnInserimentoProdotto = new JButton("Aggiungi nuovo prodotto");
        btnInserimentoProdotto.addActionListener(e -> mainView.showPanel(Pannelli.REGISTRAZIONE_PRODOTTO));
        centerButton(btnInserimentoProdotto);
        contentPanel.add(btnInserimentoProdotto);

        // Aggiunta dei componenti al pannello interno
        Component[] components = {
            btnAree, btnZoneAmministrative, btnZoneRicreative, btnHabitat, btnSpecie,
            btnEsemplari, btnOrdini, btnProdotti, btnGiornateLavorative, btnNuovoOrdine,
            btnGiornataLavorativa, btnManutenzione, btnAddAnimale, btnModificaAnimale,
            btnInserimentoProdotto
        };

        for (Component c : components) {
            contentPanel.add(c);
            contentPanel.add(Box.createVerticalStrut(8));
        }

        // Aggiunta del componente personalizzato
        CampoConDescrizionePulsante componente = new CampoConDescrizionePulsante(
            "Inserire a fianco il nome di un esemplare da eliminare", "Elimina", this);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(componente);

        // ScrollPane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        this.add(scrollPane, BorderLayout.CENTER);
    }

    private static void centerButton(final JButton b) {
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        final Dimension pref = b.getPreferredSize();
        b.setMaximumSize(new Dimension(Math.max(pref.width, 220), pref.height));
    }

    public void notifyUserPressButton(final String textInsert) {
        final var text = "Eliminazione esemplare: ";
        String message = "Sei sicuro di volere eliminare l'esemplare: " + textInsert + " ?";
        final var dialog = new Dialog(text, message, true);
        final JButton ok = new JButton("OK");
        ok.addActionListener(e -> {
            dialog.dispose();
            if (this.writingModel.checkEsemplare(textInsert)) {
                final var fields = this.writingModel.getSpecieFromEsemplare(textInsert);
                final var done = this.writingModel.deleteEsemplare(textInsert);
                if(Integer.parseInt(fields.get(Parametri.NUMERO_ESEMPLARI)) > 1) {
                    this.writingModel.updateSpecieCount(fields.get(Parametri.NOME_SCIENTIFICO), false);
                } else {
                    this.writingModel.deleteSpecie(fields.get(Parametri.NOME_SCIENTIFICO));
                }
                String mes;
                if (done) {
                    mes = "Esemplare " + textInsert + " eliminato correttamente";
                } else {
                    mes = "Problemi durante eliminazione dell'esemplare " + textInsert;
                }
                final var diag = new Dialog(text, mes, true);
                diag.setLocationRelativeTo(this);
                diag.setVisible(true);
            } else {
                 final String mes = "L'esemplare riportato non è registrato";
                final var diag = new Dialog(text, mes, true);
                diag.setLocationRelativeTo(this);
                diag.setVisible(true);
            }
           
        });

        dialog.addButton(ok);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}