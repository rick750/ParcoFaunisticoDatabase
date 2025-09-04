package parcofaunistico.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EnumMap;
import java.util.Map;

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

import parcofaunistico.controller.RegistrazioneOrdineController;
import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class OrdiniPanel extends JPanel{
    public static final String CARD_USER = "user";
    private static final long serialVersionUID = 1L;
    private static final double RESIZE_FACTOR = 1.0;
    private static final double FIELD_HEIGHT_RATIO = 0.05;
    private static final double FIELD_WIDTH_RATIO = 0.15;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int SCREEN_WIDTH = (int) (SCREEN_SIZE.getWidth() * RESIZE_FACTOR);
    private static final int SCREEN_HEIGHT = (int) (SCREEN_SIZE.getHeight() * RESIZE_FACTOR);
    private final MainView mainView;
    private final JTextField codicefiscaleField;
    private final JTextField codiceProdottoField;
    private final JTextField quantita;
    private final JTextField nomeZonaField;
    private final JButton completaDati;
    private final Map<Parametri, JTextField> textfields;
    private final RegistrazioneOrdineController regController;

    public OrdiniPanel(final MainView mainView, final WritingModel writingModel) {
        this.mainView = mainView;
        this.setLayout(new GridBagLayout());
        this.setBackground(UIManager.getColor("Panel.background"));
        this.setOpaque(true);

        this.codicefiscaleField = createField();
        this.codiceProdottoField = createField();
        this.quantita = createField();
        this.nomeZonaField = createField();
        this.completaDati = createCompletamentoButton();
        this.textfields = new EnumMap<>(Parametri.class);
        this.textfields.put(Parametri.CODICE_FISCALE, codicefiscaleField);
        this.textfields.put(Parametri.CODICE_PRODOTTO, codiceProdottoField);
        this.textfields.put(Parametri.QUANTITA, quantita);
        this.textfields.put(Parametri.NOME_ZONA, nomeZonaField);
        this.arrangeComponents();

        this.regController = new RegistrazioneOrdineController(writingModel);
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
        final JLabel title = createLabel("Menù ordini per il VISITATORE");
        title.setFont(UIManager.getFont("BigLabel.font"));
        title.setForeground(new Color(255, 215, 0));
        title.setBackground(new Color(18, 30, 49));
        title.setOpaque(true);
        this.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;

        addRow("Codice Fiscale: ", codicefiscaleField, ++row, gbc);
        addRow("codice Prodotto: ", codiceProdottoField, ++row, gbc);
        addRow("Quantita: ", quantita, ++row, gbc);
        addRow("Nome zona: ", nomeZonaField, ++row, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(completaDati, gbc);

        gbc.gridy = ++row;
        final JButton backButton = new JButton("INDIETRO");
        backButton.setFont(UIManager.getFont("Button.font"));
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(e -> mainView.showPanel(Pannelli.USER));
        this.add(backButton, gbc);
    }

    private void addRow(final String labelText, final JComponent component, final int row,
                         final GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        this.add(createLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        this.add(component, gbc);
    }

    private JButton createCompletamentoButton() {
        final JButton button = new JButton("COMPLETA DATI");
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
            this.regController.setData(this.textfields);
            if (! this.regController.check()) {
                this.showErrorMessage(this.regController.getErrorMessage());
            } else {
                final var automaticFields = this.regController.getAutomaticFields();
                final var title = "Inserimento";
                final var message = "Nome Prodotto: " + automaticFields.get(Parametri.NOME_PRODOTTO) + "\n" +
                "Prezzo prodotto: " + automaticFields.get(Parametri.PREZZO_BASE) + "\n" + 
                "Prezzo finale: " + automaticFields.get(Parametri.PREZZO_EFFETTIVO) + "\n" +
                "Codice ordine: " + automaticFields.get(Parametri.CODICE_ORDINE);
                ;
                final var executeBtn = new JButton("OK");
                final Dialog dialog = new Dialog(title, message, false);
                dialog.setLocationRelativeTo(this);
                executeBtn.addActionListener(e -> {
                    dialog.dispose(); 
                    this.regController.executeInsertQuery();
                });
                final var cancelBtn = new JButton("CANCEL");
                cancelBtn.addActionListener(e -> {
                    dialog.dispose();
                    final var entries = this.textfields.entrySet();
                    for (final var entry: entries) {
                        entry.getValue().setText("");
                    }
                });
                
                dialog.addButton(cancelBtn);
                dialog.addButton(executeBtn);
                dialog.setVisible(true);
            }
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

    private void showErrorMessage(final String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Attenzione",
            JOptionPane.ERROR_MESSAGE);
    }
}
