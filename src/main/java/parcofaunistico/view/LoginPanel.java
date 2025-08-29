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

import parcofaunistico.controller.LoginController;
import parcofaunistico.data.User;
import parcofaunistico.model.ReadingModel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
    private static final int BASE_SCREEN_WIDTH = 1920;
    private static final double RESIZE_FACTOR = 1.0;
    private static final String FONT_NAME = "DialogInput";
    private static final double FIELD_HEIGHT_RATIO = 0.05;
    private static final double FIELD_WIDTH_RATIO = 0.15;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int SCREEN_WIDTH = (int) (SCREEN_SIZE.getWidth() * RESIZE_FACTOR);
    private static final int SCREEN_HEIGHT = (int) (SCREEN_SIZE.getHeight() * RESIZE_FACTOR);
    private static final Color BG_COLOR = new Color(15, 35, 65);
    private static final Color LABEL_COLOR = new Color(144, 238, 144);
    private static final Color BTN_BG_COLOR = new Color(255, 215, 0);
    private static final Color BTN_FG_COLOR = new Color(15, 35, 65);
    private static final Font SUBTITLE_FONT = getResponsiveFont(Font.BOLD, 40);
    private static final Font LABEL_FONT = getResponsiveFont(Font.BOLD, 30);
    private static final Font FIELD_FONT = getResponsiveFont(Font.BOLD, 28);
    private static final Font BTN_FONT = new Font("Monospaced", Font.BOLD, 26);

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
        final JButton backButton = new JButton("INDIETRO");
        backButton.addActionListener(e -> this.mainView.showMenuPanel());
        this.btnsPanel.add(backButton);

        this.add(fieldPanel, CARD_FIELD);
        this.add(btnsPanel, CARD_BUTTONS);
        this.layout.show(this, CARD_BUTTONS);
    }

    private void setUpRadioButtons() {
        this.btnsGroup.add(visitatoreBtn);
        this.btnsGroup.add(dipendenteBtn);
        this.btnsGroup.add(managerBtn);

        this.btnsPanel.add(visitatoreBtn);
        this.btnsPanel.add(dipendenteBtn);
        this.btnsPanel.add(managerBtn);

 
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

    private void configurePanel() {
        this.setBackground(BG_COLOR);
        this.setLayout(this.layout);
    }

    private JPanel getMainPanel() {
        return this;
    }

    private void arrangeComponents() {
        this.fieldPanel.add(Box.createVerticalGlue());
        this.fieldPanel.add(createLabel("Insert a username to continue", SUBTITLE_FONT, LABEL_COLOR));
        this.fieldPanel.add(Box.createVerticalGlue());
        this.fieldPanel.add(createInputPanel());
        this.fieldPanel.add(Box.createVerticalGlue());
        this.fieldPanel.add(this.sendButton);
        this.fieldPanel.add(Box.createVerticalGlue());
        this.usernameField.setOpaque(true);
        this.sendButton.setOpaque(true);
        final JButton backButton = new JButton("INDIETRO");
        this.fieldPanel.add(backButton);
        backButton.addActionListener(
            e -> {
                layout.show(getMainPanel(), CARD_BUTTONS);
            }
        );
    }

    private JPanel createInputPanel() {
        final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(BG_COLOR);
        panel.add(createLabel("Username:", LABEL_FONT, LABEL_COLOR));
        panel.add(usernameField);
        return panel;
    }

    private JButton createSendButton() {
        final JButton button = new JButton("SEND");
        button.setFont(BTN_FONT);
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setBackground(BTN_BG_COLOR);
        button.setForeground(BTN_FG_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent evt) {
                button.setBackground(BTN_BG_COLOR.brighter());
            }

            @Override
            public void mouseExited(final MouseEvent evt) {
                button.setBackground(BTN_BG_COLOR);
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
        field.setFont(FIELD_FONT);
        field.addActionListener(sendActionListener());
        return field;
    }

    private JLabel createLabel(final String text, final Font font, final Color fg) {
        final JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(fg);
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
            this,
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

    private static Font getResponsiveFont(final int style, final int size) {
        final double scale = (double) SCREEN_WIDTH / BASE_SCREEN_WIDTH;
        return new Font(FONT_NAME, style, (int) (size * scale));
    }
}
