package parcofaunistico.view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import parcofaunistico.controller.AggiornamentoAnimaliController;
import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class AggiornamentoAnimaliPanel extends JPanel{
     private static final long serialVersionUID = 1L;
    private static final double RESIZE_FACTOR = 1.0;
    private static final double FIELD_HEIGHT_RATIO = 0.05;
    private static final double FIELD_WIDTH_RATIO = 0.15;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int SCREEN_WIDTH = (int) (SCREEN_SIZE.getWidth() * RESIZE_FACTOR);
    private static final int SCREEN_HEIGHT = (int) (SCREEN_SIZE.getHeight() * RESIZE_FACTOR);

    private final MainView mainView;
    private final JTextField nome_scientifico, nomeEsemplare, eta, altezza, peso, alimento, numero_pasti;
    private final JCheckBox malato;
    private final JButton sendButton;
    private final Map<Parametri, JTextField> textfields;
    private final AggiornamentoAnimaliController regController;

    public AggiornamentoAnimaliPanel(final MainView mainView, final WritingModel writingModel) {
        this.mainView = mainView;
        this.setLayout(new BorderLayout());

        final JPanel innerPanel = new JPanel(new GridBagLayout());
        innerPanel.setBackground(UIManager.getColor("Panel.background"));
        innerPanel.setOpaque(true);

        this.nome_scientifico = createField();
        this.nomeEsemplare = createField();
        this.eta = createField();
        this.altezza = createField();
        this.peso = createField();
        this.malato = new JCheckBox();
        this.alimento = createField();
        this.numero_pasti = createField();
        this.sendButton = createSendButton();

        this.textfields = new EnumMap<>(Parametri.class);
        this.textfields.put(Parametri.NOME_SCIENTIFICO, nome_scientifico);
        this.textfields.put(Parametri.NOME_ESEMPLARE, nomeEsemplare);
        this.textfields.put(Parametri.ETA, eta);
        this.textfields.put(Parametri.ALTEZZA, altezza);
        this.textfields.put(Parametri.PESO, peso);
        this.textfields.put(Parametri.ALIMENTO, alimento);
        this.textfields.put(Parametri.NUMERO_PASTI, numero_pasti);

        arrangeComponents(innerPanel);

        JScrollPane scrollPane = new JScrollPane(innerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        this.add(scrollPane, BorderLayout.CENTER);

        this.regController = new AggiornamentoAnimaliController(writingModel);
    }

    private void arrangeComponents(JPanel panel) {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.NONE;

        int row = 0;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        final JLabel title = createLabel("Benvenuto al menù di registrazione SPECIE/ANIMALE");
        title.setFont(UIManager.getFont("BigLabel.font"));
        title.setForeground(new Color(255, 215, 0));
        title.setBackground(new Color(18, 30, 49));
        title.setOpaque(true);
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;

        addRow("Nome scientifico: ", nome_scientifico, ++row, gbc, panel);
        addRow("Nome Esemplare: ", nomeEsemplare, ++row, gbc, panel);
        addRow("Eta: ", eta, ++row, gbc, panel);
        addRow("Altezza: ", altezza, ++row, gbc, panel);
        addRow("Peso: ", peso, ++row, gbc, panel);
        addRow("Malato: ", malato, ++row, gbc, panel);
        addRow("Alimento: ", alimento, ++row, gbc, panel);
        addRow("Numero Pasti: ", numero_pasti, ++row, gbc, panel);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(sendButton, gbc);

        gbc.gridy = ++row;
        final JButton backButton = new JButton("INDIETRO");
        backButton.setFont(UIManager.getFont("Button.font"));
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(e -> mainView.showPanel(Pannelli.USER));
        panel.add(backButton, gbc);
    }

    private void addRow(final String labelText, final JComponent jComponent, final int row, final GridBagConstraints gbc, final JPanel panel) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(createLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(jComponent, gbc);
    }

    private JButton createSendButton() {
        final JButton button = new JButton("INVIA");
        button.setFont(UIManager.getFont("Button.font"));
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setBackground(UIManager.getColor("Button.background"));
        button.setForeground(UIManager.getColor("Button.foreground"));
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.addMouseListener(new MouseAdapter() {
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
            regController.setData(textfields, this.malato.isSelected());
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
        showInfoDialog(message, "Input Error", Color.WHITE, Color.BLACK);
    }

    private void showInfoDialog(final String message, final String title, final Color bg, final Color fg) {
        final JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        final JDialog dialog = pane.createDialog(this, title);
        setBackgroundAndForegroundRecursively(pane, bg, fg);
        if (dialog.getContentPane() != null) {
            setBackgroundAndForegroundRecursively(dialog.getContentPane(), bg, fg);
        }
        dialog.pack();
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private void setBackgroundAndForegroundRecursively(final Component c, final Color bg, final Color fg) {
        if (c == null)
            return;
        try {
            c.setBackground(bg);
        } catch (final Exception ignored) {
        }
        try {
            c.setForeground(fg);
        } catch (final Exception ignored) {
        }
        if (c instanceof JComponent) {
            ((JComponent) c).setOpaque(true);
        }
        for (final Component child : ((c instanceof JComponent) ? ((JComponent) c).getComponents() : new Component[0])) {
            setBackgroundAndForegroundRecursively(child, bg, fg);
        }
    }
}
