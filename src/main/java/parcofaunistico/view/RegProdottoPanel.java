package parcofaunistico.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import parcofaunistico.controller.RegistrazioneProdottoController;
import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class RegProdottoPanel extends JPanel {
    private MainView mainView;
    private RegistrazioneProdottoController regController;
    private JLabel codiceProdottoLabel;
    private JTextField nomeField;
    private JTextArea descrizioneArea;
    private JTextField prezzoUnitarioField;
    private JButton sendButton;
    private final Map<Parametri, JTextField> textfields;

    public RegProdottoPanel(final MainView mainView, final WritingModel writingModel) {
        this.mainView = mainView;
        this.regController = new RegistrazioneProdottoController(writingModel);

        this.setLayout(new GridBagLayout());
        this.setBackground(UIManager.getColor("Panel.background"));
        this.setOpaque(true);
        this.codiceProdottoLabel = createLabel(this.regController.getActualProductCode());
        this.nomeField = createField();
        this.descrizioneArea = createArea();
        this.prezzoUnitarioField = createField();

        this.textfields = new EnumMap<>(Parametri.class);
        this.textfields.put(Parametri.NOME_PRODOTTO, nomeField);
        this.textfields.put(Parametri.PREZZO_PRODOTTO, prezzoUnitarioField);

        this.sendButton = createSendButton();
        this.arrangeComponents();
        this.repaint();
    }

    private JTextArea createArea() {
        final JTextArea area = new JTextArea();
        area.setOpaque(true);
        area.setColumns(20);
        area.setFont(UIManager.getFont("TextField.font"));
        return area;
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
        final JLabel title = createLabel("Benvenuto nel menù di inserimento Prodotto:");
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
        addRow("Codice Prodotto", this.codiceProdottoLabel, ++row, gbc);
        addRow("Nome", this.nomeField, ++row, gbc);
        addRow("Descrizione", this.descrizioneArea, ++row, gbc);
        addRow("Prezzo Unitario: ", this.prezzoUnitarioField, ++row, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(sendButton, gbc);

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
            this.regController.setData(textfields, descrizioneArea);
            if (!regController.startChecks()) {
                showErrorMessage(regController.getErrorMessage());
            } else {
                final var title = "Conferma registrazione";
                final var message = "I dati sono stati inseriti correttamente\n" +
                        "Premere OK per ultimare l'inserimento oppure CANCEL per annullare";
                final var executeBtn = new JButton("OK");
                final Dialog dialog = new Dialog(title, message, true);
                dialog.setLocationRelativeTo(this);
                executeBtn.addActionListener(e -> {
                    dialog.dispose();
                    boolean fatto = regController.executeInsertQuery();
                    if (!fatto) {
                        showErrorMessage("L'inserimento non è riuscito");
                    }
                    textfields.values().forEach(field -> field.setText(""));
                    this.descrizioneArea.setText("");
                });
                dialog.addButton(executeBtn);
                dialog.setVisible(true);
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

}
