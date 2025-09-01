package parcofaunistico.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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
    private JButton sendButton;
    private RegistrazionePagamentoController regController;
    private final WritingModel writingModel;

    public AcquistoBigliettoPanel(final MainView mainView, final WritingModel writingModel) {
        this.mainView = mainView;
        this.writingModel = writingModel;
        this.setLayout(new GridBagLayout());
        this.setBackground(UIManager.getColor("Panel.background"));
        this.setOpaque(true);
    }

    public void setData(final boolean persona, final String codFiscale, final String codGruppo) {
        this.regController = new RegistrazionePagamentoController(writingModel, persona, codFiscale, codGruppo);
        final var paymentData = this.regController.getPaymentData();
        final var ticketData = this.regController.getTicketData();
        this.codiceTransazioneLabel = this.createLabel("codice Transazione: " + paymentData.get(Parametri.CODICE_TRANSAZIONE));
        this.codicefiscaleLabel = this.createLabel("Codice Fiscale: " + paymentData.get(Parametri.CODICE_FISCALE));
        this.codiceGruppoLabel = this.createLabel("Codice Gruppo: " + paymentData.get(Parametri.CODICE_GRUPPO));
        this.dataEffettuazioneLabel = this.createLabel("Data effettuazione: " + paymentData.get(Parametri.DATA_EFFETTUAZIONE));
        this.codiceScontoLabel = this.createLabel("Codice sconto: " + paymentData.get(Parametri.CODICE_SCONTO));
        this.nomeZonaLabel = this.createLabel("Nome zona effettuazione pagamento: " + paymentData.get(Parametri.NOME_ZONA));
        this.codBigliettoLabel = this.createLabel("Codice Biglietto generato: " + ticketData.get(Parametri.CODICE_BIGLIETTO));
        this.prezzoBaselabel = this.createLabel("Prezzo Base: " + ticketData.get(Parametri.PREZZO_BASE));
        this.prezzoEffettivoLabel = this.createLabel("Prezzo Effettivo: " + ticketData.get(Parametri.PREZZO_EFFETTIVO));
       
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

        addRow(codiceTransazioneLabel, ++row, gbc);
        addRow(this.codicefiscaleLabel, ++row, gbc);
        addRow(this.codiceGruppoLabel, ++row, gbc);
        addRow(this.dataEffettuazioneLabel, ++row, gbc);
        addRow(this.codiceScontoLabel, ++row, gbc);
        addRow(this.nomeZonaLabel, ++row, gbc);
        addRow(this.codBigliettoLabel, ++row, gbc);
        addRow(this.prezzoBaselabel, ++row, gbc);
        addRow(this.prezzoEffettivoLabel, ++row, gbc);

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

    private void addRow(final JLabel label, final int row, final GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(label, gbc);
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
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setFocusable(false);
        return label;
    }

    private void showErrorMessage(final String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Attenzione",
            JOptionPane.ERROR_MESSAGE);
    }
}
