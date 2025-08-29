package parcofaunistico.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import parcofaunistico.controller.RegistrazioneVisitatoreController;
import parcofaunistico.data.Parametri;
import parcofaunistico.model.WritingModel;

public class RegVisitatorePanel extends JPanel{
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
    private final MainView mainView;
    private final JTextField codicefiscaleField;
    private final JTextField nomeField;
    private final JTextField cognomeField;
    private final JTextField etaField;
    private final JTextField indirizzoField;
    private final JTextField telefonoField;
    private final JTextField emailField;
    private final JButton sendButton;
    private final Map<Parametri, JTextField> textfields;
    private final RegistrazioneVisitatoreController regController;
    public RegVisitatorePanel(final MainView mainView, final WritingModel writingModel) {
        this.mainView = mainView;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel("Benvenuto al menù di registrazione"));

        this.codicefiscaleField = createField();
        this.nomeField = createField();
        this.cognomeField = createField();
        this.etaField = createField();
        this.indirizzoField = createField();
        this.telefonoField = createField();
        this.emailField = createField();
        this.sendButton = createSendButton();
        this.textfields = new EnumMap<>(Parametri.class);
        this.textfields.put(Parametri.CODICE_FISCALE, codicefiscaleField);
        this.textfields.put(Parametri.NOME, nomeField);
        this.textfields.put(Parametri.COGNOME, cognomeField);
        this.textfields.put(Parametri.ETA, etaField);
        this.textfields.put(Parametri.INDIRIZZO, indirizzoField);
        this.textfields.put(Parametri.TELEFONO, telefonoField);
        this.textfields.put(Parametri.EMAIL, emailField);
        this.arrangeComponents();

        final JButton backButton = new JButton("INDIETRO");
        this.add(backButton);
        backButton.addActionListener(
            e -> {
                mainView.showMenuPanel();
            }
        );

        this.regController = new RegistrazioneVisitatoreController(writingModel, this.textfields);
    }

    private void arrangeComponents() {
        this.add(Box.createVerticalGlue());
        this.add(createLabel("Insert a username to continue", SUBTITLE_FONT, LABEL_COLOR));
        this.add(Box.createVerticalGlue());
        this.add(createInputPanel("Codice Fiscale", codicefiscaleField));
        this.add(Box.createVerticalGlue());
        this.add(createInputPanel("nome", nomeField));
        this.add(Box.createVerticalGlue());
        this.add(createInputPanel("cognome", cognomeField));
        this.add(Box.createVerticalGlue());
        this.add(createInputPanel("età", etaField));
        this.add(Box.createVerticalGlue());
        this.add(createInputPanel("indirizzo", indirizzoField));
        this.add(Box.createVerticalGlue());
        this.add(createInputPanel("telefono", telefonoField));
        this.add(Box.createVerticalGlue());
        this.add(createInputPanel("email", emailField));
        this.add(Box.createVerticalGlue());
        this.add(this.sendButton);
        this.add(Box.createVerticalGlue());
        this.sendButton.setOpaque(true);
    }

    private JPanel createInputPanel(final String text, final JTextField field) {
        final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(BG_COLOR);
        panel.add(createLabel(text, LABEL_FONT, LABEL_COLOR));
        panel.add(field);
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

            public void mouseExited(final MouseEvent evt) {
                button.setBackground(BTN_BG_COLOR);
            }
        });

        button.addActionListener(act -> {
            if (! this.regController.check()) {
                this.showErrorMessage(this.regController.getErrorMessage());
            } else {
                this.regController.executeInsertQuery();
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
        field.setFont(FIELD_FONT);
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

     private static Font getResponsiveFont(final int style, final int size) {
        final double scale = (double) SCREEN_WIDTH / BASE_SCREEN_WIDTH;
        return new Font(FONT_NAME, style, (int) (size * scale));
    }

    private void showErrorMessage(final String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Attenzione",
            JOptionPane.ERROR_MESSAGE);
    }
}
