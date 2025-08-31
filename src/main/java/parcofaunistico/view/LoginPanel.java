package parcofaunistico.view;

import java.util.Collections;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import parcofaunistico.controller.LoginController;
import parcofaunistico.data.User;
import parcofaunistico.model.ReadingModel;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPanel extends JPanel {
    private static final String NOME_MANAGER = "Manager";
    private static final String NOME_DIPENDENTE = "Dipendente";
    private static final String NOME_VISITATORE = "Visitatore";
    private static final String CARD_BUTTONS = "buttons";
    private static final String CARD_FIELD = "field";
    private static final long serialVersionUID = 1L;
    private static final double RESIZE_FACTOR = 1.0;
    private static final double FIELD_HEIGHT_RATIO = 0.05;
    private static final double FIELD_WIDTH_RATIO = 0.15;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int SCREEN_WIDTH = (int) (SCREEN_SIZE.getWidth() * RESIZE_FACTOR);
    private static final int SCREEN_HEIGHT = (int) (SCREEN_SIZE.getHeight() * RESIZE_FACTOR);

    private final JTextField usernameField;
    private final JButton sendButton;
    private final LoginController loginContr;
    private final CardLayout layout = new CardLayout();
    private final JPanel btnsPanel;
    private final JPanel fieldPanel;
    private final MainView mainView;
    private String selected;
    
    private final JRadioButton visitatoreBtn;
    private final JRadioButton dipendenteBtn;
    private final JRadioButton managerBtn;
    private final ButtonGroup btnsGroup;

    public LoginPanel(MainView mainView, ReadingModel model) {
        this.mainView = mainView;
        this.loginContr = new LoginController(model);
        this.configurePanel();
        this.usernameField = createUsernameField();
        this.sendButton = createSendButton();
        this.fieldPanel = new JPanel();
        this.fieldPanel.setLayout(new BoxLayout(this.fieldPanel, BoxLayout.Y_AXIS));
        this.arrangeComponents();
        
        this.btnsGroup = new ButtonGroup();
        this.visitatoreBtn = new JRadioButton(NOME_VISITATORE);
        this.dipendenteBtn = new JRadioButton(NOME_DIPENDENTE);
        this.managerBtn = new JRadioButton(NOME_MANAGER);
        this.btnsPanel = new JPanel();
        this.btnsPanel.setLayout(new BoxLayout(this.btnsPanel, BoxLayout.Y_AXIS));
        setUpRadioButtons();

        this.add(fieldPanel, CARD_FIELD);
        this.layout.show(this, CARD_BUTTONS);
    }

    private void setUpRadioButtons() {
        this.btnsGroup.add(visitatoreBtn);
        this.btnsGroup.add(dipendenteBtn);
        this.btnsGroup.add(managerBtn);
        styleRadioButton(visitatoreBtn);
        styleRadioButton(dipendenteBtn);
        styleRadioButton(managerBtn);
        this.btnsPanel.add(visitatoreBtn);
        this.btnsPanel.add(dipendenteBtn);
        this.btnsPanel.add(managerBtn);
        final JButton backButton = new JButton("INDIETRO");
        backButton.addActionListener(e -> this.mainView.showMenuPanel());
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        this.btnsPanel.add(Box.createVerticalStrut(20));
        this.btnsPanel.add(backButton);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(UIManager.getColor("Panel.background"));
        wrapper.add(this.btnsPanel);

        this.add(wrapper, CARD_BUTTONS);

        for (final AbstractButton btn : Collections.list(btnsGroup.getElements())) {
            btn.addActionListener(
                e -> {
                    switch (btn.getText()) {
                        case NOME_VISITATORE -> this.selected = NOME_VISITATORE;
                        case NOME_DIPENDENTE -> this.selected = NOME_DIPENDENTE;
                        case NOME_MANAGER -> this.selected = NOME_MANAGER;
                    }
                    layout.show(getMainPanel(), CARD_FIELD);
                }
            );
        }
    }


    private void styleRadioButton(JRadioButton btn) {
        btn.setFont(UIManager.getFont("Button.font"));
        btn.setBackground(UIManager.getColor("Button.background"));
        btn.setForeground(UIManager.getColor("Button.foreground"));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setMaximumSize(new Dimension(300, 60));
        btn.setAlignmentX(CENTER_ALIGNMENT);
    }

    private void configurePanel() {
        this.setBackground(UIManager.getColor("Panel.background"));
        this.setLayout(this.layout);
    }

    private JPanel getMainPanel() {
        return this;
    }

    private void arrangeComponents() {
        fieldPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridy = 0;
        fieldPanel.add(createInputPanel(), gbc);

        gbc.gridy = 1;
        fieldPanel.add(sendButton, gbc);

        gbc.gridy = 2;
        final JButton backButton = new JButton("INDIETRO");
        backButton.addActionListener(e -> layout.show(getMainPanel(), CARD_BUTTONS));
        fieldPanel.add(backButton, gbc);

        usernameField.setOpaque(true);
        sendButton.setOpaque(true);
    }

    private JPanel createInputPanel() {
        final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(UIManager.getColor("Panel.backgorund"));
        panel.add(createLabel("Codice Fiscale:"));
        panel.add(usernameField);
        return panel;
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
                button.getBackground().brighter();
            }

            @Override
            public void mouseExited(final MouseEvent evt) {
                button.setBackground(UIManager.getColor("Button.background"));
            }
        });

        button.addActionListener(sendActionListener());
        return button;
    }

    private JTextField createUsernameField() {
        final JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(
            (int) (SCREEN_WIDTH * FIELD_WIDTH_RATIO),
            (int) (SCREEN_HEIGHT * FIELD_HEIGHT_RATIO)));
        field.setFont(UIManager.getFont("Field.font"));
        field.addActionListener(sendActionListener());
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

    private ActionListener sendActionListener() {
        return e -> {
            final String codiceFiscale = usernameField.getText().trim();
            if (!loginContr.checkValidity(codiceFiscale)) {
                this.showError("Il codice fiscale deve essere lungo 16 caratteri!");
            } else if (confirmUser(codiceFiscale)) {
                switch(this.selected) {
                    case "Manager" -> this.mainView.setUserPanel(User.MANAGER, codiceFiscale);
                    case "Visitatore" -> this.mainView.setUserPanel(User.VISITATORE, codiceFiscale);
                    case "Dipendente" -> this.mainView.setUserPanel(User.DIPENDENTE, codiceFiscale);
                }
                this.mainView.showUserPanel();
            }
            this.usernameField.setText("");
        };
    }

    private boolean confirmUser(final String codiceFiscale) {
        final boolean isNew = !loginContr.checkExistance(codiceFiscale, this.selected);

        final String message = String.format(
            "Il codice fiscale%sè presente nel database.",
            isNew ? " non " : " "
        );

        JOptionPane.showMessageDialog(
            null,
            message,
            "Informazione",
            JOptionPane.INFORMATION_MESSAGE
        );
        return !isNew;
    }

    private void showError(final String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Input Error",
            JOptionPane.ERROR_MESSAGE);
    }
}
