package parcofaunistico.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
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

public class AcquistoBigliettoPanel extends JPanel{
    private static final long serialVersionUID = 1L;
    private static final double RESIZE_FACTOR = 1.0;
    private static final double FIELD_HEIGHT_RATIO = 0.05;
    private static final double FIELD_WIDTH_RATIO = 0.15;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int SCREEN_WIDTH = (int) (SCREEN_SIZE.getWidth() * RESIZE_FACTOR);
    private static final int SCREEN_HEIGHT = (int) (SCREEN_SIZE.getHeight() * RESIZE_FACTOR);
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

    public void setData(final boolean visitatore, final String codFiscale, final int eta, final int numPartecipanti, final String codGruppo) {
        this.visitatore = visitatore;
        this.regController = new RegistrazionePagamentoController(writingModel, this, visitatore, codFiscale, eta, numPartecipanti, codGruppo);
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
        title.setFont(UIManager.getFont("BigLabel.font"));
        title.setForeground(new Color(255, 215, 0));
        title.setBackground(new Color(18, 30, 49));
        title.setOpaque(true);
        this.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;

        addRow(createLabel("Codice transazione: "), codiceTransazioneLabel, ++row, gbc);
        addRow(createLabel("Codice fiscale: "),this.codicefiscaleLabel, ++row, gbc);
        addRow(createLabel("Codice gruppo: "),this.codiceGruppoLabel, ++row, gbc);
        addRow(createLabel("Data effettuazione: "),this.dataEffettuazioneLabel, ++row, gbc);
        addRow(createLabel("Codice sconto: "),this.codiceScontoLabel, ++row, gbc);
        addRow(createLabel("nome Zona effettuazione: "),this.nomeZonaLabel, ++row, gbc);
        addRow(createLabel("Codice biglietto: "),this.codBigliettoLabel, ++row, gbc);
        addRow(createLabel("Prezzo base: "),this.prezzoBaselabel, ++row, gbc);
        addRow(createLabel("Prezzo effettivo: "),this.prezzoEffettivoLabel, ++row, gbc);
        addRow(createLabel("Data Validità: "),this.dataValidita, ++row, gbc);
        addRow(createLabel("Codice percorso: "),this.percorso, ++row, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(sendButton, gbc);

        gbc.gridy = ++row;
        final JButton backButton = new JButton("INDIETRO");
        backButton.setFont(UIManager.getFont("Button.font"));
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(e -> mainView.showMenuPanel());
        this.add(backButton, gbc);
    }

    private void addRow(final JLabel label, final JComponent component, final int row, final GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(label, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(component, gbc);
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
            }
        );

        button.addActionListener(act -> {
            this.regController.checkPathAndDate(this.percorso.getText(), this.dataValidita.getText());
        });
        return button;
    }

    private JTextField createField() {
        final JTextField field = new JTextField();
        field.setOpaque(true);
        field.setPreferredSize(new Dimension(
            (int) (SCREEN_WIDTH * FIELD_WIDTH_RATIO),
            (int) (SCREEN_HEIGHT * FIELD_HEIGHT_RATIO)));
        field.setFont(UIManager.getFont("TextField.font"));
        return field;
    }

    private JLabel createLabel(final String text) {
        final JLabel label = new JLabel(text);
        label.setFont(UIManager.getFont("Label.font"));
        label.setForeground(UIManager.getColor("Label.foreground"));
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setFocusable(false);
        return label;
    }

    public void showErrorMessage(final String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Attenzione",
            JOptionPane.ERROR_MESSAGE);
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
                mainView.notifyGruppoInsert();
            }
            this.regController.executeInsertQuery();
            mainView.showMenuPanel();
        });
        confirmDialog.setLocationRelativeTo(this);
        confirmDialog.addButton(ok);
        confirmDialog.setVisible(true);
    }
}
