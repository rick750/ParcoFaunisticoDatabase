package parcofaunistico.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import parcofaunistico.controller.RegistrazionePagamentoController;
import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class AcquistoBigliettoPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final MainView mainView;
    private JLabel codiceTransazioneLabel;
    private JLabel codicefiscaleLabel;
    private JLabel codiceGruppoLabel;
    private JLabel dataEffettuazioneLabel;
    private JLabel codiceScontoLabel;
    private JLabel nomeZonaLabel;
    private JLabel codBigliettoLabel;
    private JLabel prezzoBaselabel;
    private JLabel prezzoEffettivoLabel;
    private JTextField dataValidita;
    private JTextField percorso;
    private JButton sendButton;
    private RegistrazionePagamentoController regController;
    private final WritingModel writingModel;
    private boolean visitatore;

    public AcquistoBigliettoPanel(final MainView mainView, final WritingModel writingModel) {
        this.mainView = mainView;
        this.writingModel = writingModel;
        this.setLayout(new GridBagLayout());
        this.setBackground(UIManager.getColor("Panel.background"));
        this.setOpaque(true);
    }

    public void setData(final boolean visitatore, final String codFiscale, final int eta, final int numPartecipanti,
            final String codGruppo) {
        this.visitatore = visitatore;
        this.regController = new RegistrazionePagamentoController(writingModel, this, visitatore, codFiscale, eta,
                numPartecipanti, codGruppo);
        final var paymentData = this.regController.getPaymentData();
        final var ticketData = this.regController.getTicketData();
        this.codiceTransazioneLabel = this.createLabel(paymentData.get(Parametri.CODICE_TRANSAZIONE));
        this.codicefiscaleLabel = this.createLabel(paymentData.get(Parametri.CODICE_FISCALE));
        this.codiceGruppoLabel = this.createLabel(paymentData.get(Parametri.CODICE_GRUPPO));
        this.dataEffettuazioneLabel = this.createLabel(paymentData.get(Parametri.DATA_EFFETTUAZIONE));
        this.codiceScontoLabel = this.createLabel(paymentData.get(Parametri.CODICE_SCONTO));
        this.nomeZonaLabel = this.createLabel(paymentData.get(Parametri.NOME_ZONA));
        this.codBigliettoLabel = this.createLabel(ticketData.get(Parametri.CODICE_BIGLIETTO));
        this.prezzoBaselabel = this.createLabel(ticketData.get(Parametri.PREZZO_BASE));
        this.prezzoEffettivoLabel = this.createLabel(ticketData.get(Parametri.PREZZO_EFFETTIVO));
        this.dataValidita = this.createField();
        this.percorso = this.createField();

        this.sendButton = createSendButton();
        this.arrangeComponents();
        this.repaint();
    }

    private void arrangeComponents() {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.NONE;

        int row = 0;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        final JLabel title = createLabel("Riepilogo pagamento del biglietto:");
        title.setFont(UIManager.getFont("Label.font"));
        title.setForeground(new Color(255, 215, 0));
        title.setBackground(new Color(18, 30, 49));
        title.setOpaque(true);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        this.add(title, gbc);
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;

        addRow("Codice transazione: ", codiceTransazioneLabel, ++row, gbc);
        addRow("Codice fiscale: ", this.codicefiscaleLabel, ++row, gbc);
        addRow("Codice gruppo: ", this.codiceGruppoLabel, ++row, gbc);
        addRow("Data effettuazione: ", this.dataEffettuazioneLabel, ++row, gbc);
        addRow("Codice sconto: ", this.codiceScontoLabel, ++row, gbc);
        addRow("nome Zona effettuazione: ", this.nomeZonaLabel, ++row, gbc);
        addRow("Codice biglietto: ", this.codBigliettoLabel, ++row, gbc);
        addRow("Prezzo base: ", this.prezzoBaselabel, ++row, gbc);
        addRow("Prezzo effettivo: ", this.prezzoEffettivoLabel, ++row, gbc);
        addRow("Data Validità: ", this.dataValidita, ++row, gbc);
        addRow("Codice percorso: ", this.percorso, ++row, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(sendButton, gbc);

        gbc.gridy = ++row;
        final JButton backButton = new JButton("INDIETRO");
        backButton.setFont(UIManager.getFont("Button.font"));
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(e -> mainView.showPanel(Pannelli.ACCEDI_REGISTRATI));
        this.add(backButton, gbc);
    }

    private void addRow(final String labelText, final JComponent component, final int row,
            final GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        final JLabel lbl = createLabel(labelText);
        lbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        this.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        this.add(component, gbc);

        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
    }

    private JButton createSendButton() {
        final JButton button = new JButton("INVIA");
        button.setFont(UIManager.getFont("Button.font"));
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setBackground(UIManager.getColor("Button.background"));
        button.setForeground(UIManager.getColor("Button.foreground"));
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseEntered(final MouseEvent evt) {
                        button.setBackground(button.getBackground().brighter());
                    }

                    @Override
                    public void mouseExited(final MouseEvent evt) {
                        button.setBackground(UIManager.getColor("Button.background"));
                    }
                });

        button.addActionListener(act -> {
            this.regController.checkPathAndDate(this.percorso.getText(), this.dataValidita.getText());
        });
        return button;
    }

    private JTextField createField() {
        final JTextField field = new JTextField();
        field.setOpaque(true);
        field.setColumns(20);
        field.setFont(UIManager.getFont("TextField.font"));
        return field;
    }

    private JLabel createLabel(final String text) {
        final JLabel label = new JLabel(text);
        label.setFont(UIManager.getFont("Label.font"));
        label.setForeground(UIManager.getColor("Label.foreground"));
        label.setFocusable(false);
        return label;
    }

    
    public void showErrorMessage(final String message) {
        showInfoDialog(message, "Input Error", Color.WHITE, Color.BLACK);
    }

    private void showInfoDialog(String message, String title, Color bg, Color fg) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(this, title);
        setBackgroundAndForegroundRecursively(pane, bg, fg);
        if (dialog.getContentPane() != null) {
            setBackgroundAndForegroundRecursively(dialog.getContentPane(), bg, fg);
        }
        dialog.pack();
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private void setBackgroundAndForegroundRecursively(Component c, Color bg, Color fg) {
        if (c == null)
            return;
        try {
            c.setBackground(bg);
        } catch (Exception ignored) {
        }
        try {
            c.setForeground(fg);
        } catch (Exception ignored) {
        }
        if (c instanceof JComponent) {
            ((JComponent) c).setOpaque(true);
        }
        for (Component child : ((c instanceof JComponent) ? ((JComponent) c).getComponents() : new Component[0])) {
            setBackgroundAndForegroundRecursively(child, bg, fg);
        }
    }

    public void showConfirmDialog() {
        final String title = "Conferma inserimento...";
        final String message = "I dati sono stati inseriti correttamente. Premere OK per ultimare l'inserimento";
        final Dialog confirmDialog = new Dialog(title, message, true);
        final JButton ok = new JButton("OK");
        ok.addActionListener(act -> {
            confirmDialog.dispose();
            if (visitatore) {
                mainView.notifyVisitatoreInsert();
            } else {
                System.out.println("notifico che bisogna inserire il gruppo");
                mainView.notifyGruppoInsert();
            }
            this.regController.executeInsertQuery();
            this.dataValidita.setText("");
            this.percorso.setText("");
            mainView.showPanel(Pannelli.ACCEDI_REGISTRATI);
        });
        confirmDialog.setLocationRelativeTo(this);
        confirmDialog.addButton(ok);
        confirmDialog.setVisible(true);
    }
}
