package parcofaunistico.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

import parcofaunistico.controller.RegistrazioneGruppoController;
import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class RegGruppoPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final MainView mainView;
    private final JLabel codicegruppoLabel;
    private final JLabel numPartecipantiLabel;
    private final JTextField codicefiscaleField;
    private final JTextField nomeField;
    private final JTextField cognomeField;
    private final JTextField etaField;
    private final JTextField indirizzoField;
    private final JTextField telefonoField;
    private final JTextField emailField;
    private final JButton addButton;
    private final JButton sendButton;
    private final Map<Parametri, JTextField> textfields;
    private final RegistrazioneGruppoController regController;
    private final AcquistoBigliettoPanel acquistoBigliettoPanel;

    public RegGruppoPanel(final MainView mainView, final WritingModel writingModel,
            final AcquistoBigliettoPanel acquistoPanel) {
        this.mainView = mainView;
        this.acquistoBigliettoPanel = acquistoPanel;
        this.regController = new RegistrazioneGruppoController(writingModel);
        this.setLayout(new GridBagLayout());
        this.setBackground(UIManager.getColor("Panel.background"));
        this.setOpaque(true);

        this.codicegruppoLabel = createLabel(this.regController.getActualGroupCode());
        this.numPartecipantiLabel = createLabel(String.valueOf(this.regController.getNumPartecipanti()));
        this.codicefiscaleField = createField();
        this.nomeField = createField();
        this.cognomeField = createField();
        this.etaField = createField();
        this.indirizzoField = createField();
        this.telefonoField = createField();
        this.emailField = createField();
        this.textfields = new EnumMap<>(Parametri.class);
        this.textfields.put(Parametri.CODICE_FISCALE, codicefiscaleField);
        this.textfields.put(Parametri.NOME, nomeField);
        this.textfields.put(Parametri.COGNOME, cognomeField);
        this.textfields.put(Parametri.ETA, etaField);
        this.textfields.put(Parametri.INDIRIZZO, indirizzoField);
        this.textfields.put(Parametri.TELEFONO, telefonoField);
        this.textfields.put(Parametri.EMAIL, emailField);
        this.addButton = createAddButton();
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
        final JLabel title = createLabel("Benvenuto al menù di registrazione GRUPPO.");
        title.setFont(UIManager.getFont("BigLabel.font"));
        title.setForeground(new Color(255, 215, 0));
        title.setBackground(new Color(18, 30, 49));
        title.setOpaque(true);
        this.add(title, gbc);
        gbc.gridy = ++row;
        final JLabel subtitle = createLabel("Se il visitatore è già registrato, indicare solo il codice fiscale.");
        subtitle.setFont(UIManager.getFont("Label.font"));
        subtitle.setForeground(new Color(255, 215, 0));
        subtitle.setBackground(new Color(18, 30, 49));
        subtitle.setOpaque(true);
        this.add(subtitle, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;

        addRow("Codice gruppo: ", codicegruppoLabel, ++row, gbc);
        addRow("Numero di partecipanti attuali: ", numPartecipantiLabel, ++row, gbc);
        addRow("Codice Fiscale: ", codicefiscaleField, ++row, gbc);
        addRow("Nome: ", nomeField, ++row, gbc);
        addRow("Cognome: ", cognomeField, ++row, gbc);
        addRow("Età: ", etaField, ++row, gbc);
        addRow("Indirizzo: ", indirizzoField, ++row, gbc);
        addRow("Telefono: ", telefonoField, ++row, gbc);
        addRow("Email: ", emailField, ++row, gbc);
        gbc.gridx = 0;
        gbc.gridy = ++row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(addButton);
        buttonPanel.add(sendButton);

        this.add(buttonPanel, gbc);

        gbc.gridy = ++row;
        final JButton backButton = new JButton("INDIETRO");
        backButton.setFont(UIManager.getFont("Button.font"));
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(e -> mainView.showMenuPanel());
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
            if (this.regController.checkGruppo()) {
                final var title = "Conferma registrazione";
                final var message = "I dati del gruppo sono stati inseriti correttamente.\n"
                        + "Premere OK per passare al pagamento visita o CANCEL per annullare";

                final var executeBtn = new JButton("OK");
                final var cancelBtn = new JButton("CANCEL");
                final Dialog dialog = new Dialog(title, message, false);
                dialog.setLocationRelativeTo(this);
                executeBtn.addActionListener(e -> {
                    dialog.dispose();
                    this.acquistoBigliettoPanel.setData(false, "nessuno",
                            0, regController.getNumPartecipanti(),
                            this.codicegruppoLabel.getText());
                    this.mainView.showAcquistoBigliettoPanel(false);
                });
                cancelBtn.addActionListener(e -> {
                    dialog.dispose();
                    this.regController.clearPartecipanti();
                    this.numPartecipantiLabel.setText(String.valueOf(this.regController.getNumPartecipanti()));
                });
                dialog.addButton(cancelBtn);
                dialog.addButton(executeBtn);
                dialog.setVisible(true);
            } else {
                this.showErrorMessage(this.regController.getErrorMessage());
            }

        });
        return button;
    }

    public JButton createAddButton() {
        final JButton button = new JButton("AGGIUNGI PERSONA");
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
            if (this.regController.checkVisitatoreExistance(this.textfields.get(Parametri.CODICE_FISCALE).getText())) {
                final var title = "Conferma registrazione";
                final var message = "Il codice fiscale: " + this.textfields.get(Parametri.CODICE_FISCALE).getText()
                        + " è già registrato.\nSi vuole aggiungere questo visitatore al gruppo?";
                final var executeBtn = new JButton("OK");
                final Dialog dialog = new Dialog(title, message, true);
                dialog.setLocationRelativeTo(this);
                executeBtn.addActionListener(e -> {
                    dialog.dispose();
                    this.regController.addPartecipanteSingolo(this.textfields.get(Parametri.CODICE_FISCALE).getText());
                    this.codicefiscaleField.setText("");
                    this.numPartecipantiLabel.setText(String.valueOf(this.regController.getNumPartecipanti()));
                });
                dialog.addButton(executeBtn);
                dialog.setVisible(true);
            } else {
                if (!this.regController.checkVisitatore(this.textfields)) {
                    this.showErrorMessage(this.regController.getErrorMessage());
                } else {
                    this.numPartecipantiLabel.setText(String.valueOf(this.regController.getNumPartecipanti()));
                    final var title = "Conferma registrazione";
                    final var message = "I dati del nuovo partecipante sono stati inseriti correttamente";

                    final var executeBtn = new JButton("OK");
                    final Dialog dialog = new Dialog(title, message, false);
                    dialog.setLocationRelativeTo(this);
                    executeBtn.addActionListener(e -> {
                        dialog.dispose();
                        final var entries = this.textfields.entrySet();
                        for (final var entry : entries) {
                            entry.getValue().setText("");
                        }
                        this.codicegruppoLabel.setText(this.regController.getActualGroupCode());
                    });
                    dialog.addButton(executeBtn);
                    dialog.setVisible(true);
                }
            }
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

    public void executeInsertGruppo() {
        this.regController.executeInsertQuery();
    }

    public void restartData() {
        this.codicegruppoLabel.setText(this.regController.getActualGroupCode());
        this.numPartecipantiLabel.setText(String.valueOf(this.regController.getNumPartecipanti()));
    }
}
