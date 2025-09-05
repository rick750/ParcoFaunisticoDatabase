package parcofaunistico.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EnumMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import parcofaunistico.controller.RegistrazioneAreaController;
import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class RegAreaPanel extends JPanel{
    private static final long serialVersionUID = 1L;
    private final MainView mainView;
    private final JTextField oraApertura;
    private final JTextField oraChiusura;
    private final ButtonGroup btnsGroup;
    private final JRadioButton zAmmBtn;
    private final JRadioButton zRicBtn;
    private final JRadioButton HabBtn;
    private final JButton sendButton;
    private Parametri tipoSelezionato;
    private final RegistrazioneAreaController regController;

    public RegAreaPanel(final MainView mainView, final WritingModel writingModel) {
        this.mainView = mainView;
        this.regController = new RegistrazioneAreaController(writingModel);
        this.btnsGroup = new ButtonGroup();
        this.zAmmBtn = new JRadioButton();
        this.zRicBtn = new JRadioButton();
        this.HabBtn = new JRadioButton();
        btnsGroup.add(zAmmBtn);
        btnsGroup.add(zRicBtn);
        btnsGroup.add(HabBtn);
        zAmmBtn.addActionListener(e -> this.tipoSelezionato = Parametri.NOME_ZONA_AMMINISTRATIVA);
        zRicBtn.addActionListener(e -> this.tipoSelezionato = Parametri.NOME_ZONA_RICREATIVA);
        HabBtn.addActionListener(e -> this.tipoSelezionato = Parametri.NOME_HABITAT);

        this.setLayout(new GridBagLayout());
        this.setBackground(UIManager.getColor("Panel.background"));
        this.setOpaque(true);
        this.oraApertura = createField();
        this.oraChiusura = createField();
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
        final JLabel title = createLabel("Benvenuto nel menù di inserimento Area:");
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
        addRow("Zona Amministrativa", this.zAmmBtn, ++row, gbc);
        addRow("Zona Ricreativa", this.zRicBtn, ++row, gbc);
        addRow("Habitat", this.HabBtn, ++row, gbc);
        addRow("Orario Apertura: ", this.oraApertura, ++row, gbc);
        addRow("Orario Chiusura: ", this.oraChiusura, ++row, gbc);

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
            final var title = "Inserimento nuova area";
            String message;
            if(this.tipoSelezionato == null) {
                message = "Per procedere occorre selezionare una tra le opzioni per la nuova area";
                final var dialog = new Dialog(title, message, true);
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
                return;
            }
            final var fields = new EnumMap<Parametri, String>(Parametri.class);
            fields.put(Parametri.ORARIO_INIZIO, this.oraApertura.getText());
            fields.put(Parametri.ORARIO_FINE, this.oraChiusura.getText());
            this.regController.setData(this.tipoSelezionato, fields);
            if(!this.regController.startChecks()) {
                message = this.regController.getErrorMessage();
                final var diag = new Dialog(title, message, true);
                diag.setLocationRelativeTo(this);
                diag.setVisible(true);
            } else {
                message = "I dati sono stati inseriti correttamente, procedere con OK per l'inserimento";
                final var dialog = new Dialog(title, message, true);
                final var ok = new JButton("OK");
                ok.addActionListener(e -> {
                String text;
                dialog.dispose();
                final var fatto = this.regController.executeInsertQuery();
                if (!fatto) {
                    text = this.regController.getErrorMessage();
                } else {
                    text = "Area inserita correttamente";
                }
                final var diag = new Dialog(title, text, true);
                diag.setLocationRelativeTo(this);
                diag.setVisible(true);
                this.oraApertura.setText("");
                this.oraChiusura.setText("");
            });
            dialog.addButton(ok);
            dialog.setLocationRelativeTo(this);
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
