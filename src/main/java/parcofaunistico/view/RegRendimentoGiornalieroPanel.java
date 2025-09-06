// file: parcofaunistico/view/RegRendimentoGiornalieroPanel.java
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
import javax.swing.JTextField;
import javax.swing.UIManager;

import parcofaunistico.controller.RegistrazioneRendimentoController;
import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class RegRendimentoGiornalieroPanel extends JPanel {
    private final MainView mainView;
    private final RegistrazioneRendimentoController regController;
    private final JLabel titolo;
    private final JTextField nomeAreaField;
    private final JTextField dataField;
    private final Map<Parametri, JTextField> textfields;

    public RegRendimentoGiornalieroPanel(final MainView mainView, final WritingModel writingModel) {
        this.mainView = mainView;
        this.regController = new RegistrazioneRendimentoController(writingModel);

        this.setLayout(new GridBagLayout());
        this.setBackground(UIManager.getColor("Panel.background"));
        this.setOpaque(true);

        this.titolo = createLabel("Benvenuto nel menù di registrazione di un rendimento giornaliero:");
        this.nomeAreaField = createField();
        this.dataField = createField();
        this.textfields = new EnumMap<>(Parametri.class);

        this.textfields.put(Parametri.NOME_ZONA_RICREATIVA, nomeAreaField);
        this.textfields.put(Parametri.DATA_RENDIMENTO, dataField);

        final JButton sendButton = createSendButton();
        arrangeComponents(sendButton);
        this.repaint();
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

    private void arrangeComponents(final JButton sendButton) {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.NONE;

        int row = 0;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.titolo.setFont(UIManager.getFont("Label.font"));
        this.titolo.setForeground(new Color(255, 215, 0));
        this.titolo.setBackground(new Color(18, 30, 49));
        this.titolo.setOpaque(true);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        this.titolo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        this.add(this.titolo, gbc);
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        addRow("Nome area", this.nomeAreaField, ++row, gbc);
        addRow("Data", this.dataField, ++row, gbc);

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

        button.addActionListener(act -> {
            // passare i dati al controller
            regController.setData(textfields);
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
                    } else {
                        textfields.values().forEach(field -> field.setText(""));
                    }
                });
                dialog.addButton(executeBtn);
                dialog.setVisible(true);
            }
        });
        return button;
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
        if (c instanceof JComponent)
            ((JComponent) c).setOpaque(true);
        for (Component child : ((c instanceof JComponent) ? ((JComponent) c).getComponents() : new Component[0])) {
            setBackgroundAndForegroundRecursively(child, bg, fg);
        }
    }
}
