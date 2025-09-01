package parcofaunistico.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import parcofaunistico.controller.MainController;
import parcofaunistico.controller.ReadingController;
import parcofaunistico.data.User;

import java.util.List;

public final class MainView extends JFrame{

    private static final String CARD_ACCEDI_REGISTRATI = "accReg";
    private static final String CARD_LOGIN = "login";
    private static final String CARD_REGISTRAZIONE = "registrazione";
    private static final String CARD_ACQUISTO_BIGLIETTO = "acquistoBiglietto";
    private static final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int WIDTH = (int) SCREENSIZE.getWidth();
    private static final int HEIGHT = (int) SCREENSIZE.getHeight();
    public static final String CARD_USER = "user";
    public static final String EMPTY = "empty";

    private Optional<ReadingController> readingController;
    private final CardLayout layout = new CardLayout();
    private final JPanel cardPanel = new JPanel(this.layout);
    private final JPanel emptyPanel;
    private final LoginPanel loginPanel;
    private final RegVisitatorePanel registrazionePanel;
    private final AcquistoBigliettoPanel acquistoBigliettoPanel;
    private final AccediRegistratiPanel accRegPanel;

    public MainView(final MainController mainController, final Runnable onClose) {
        this.readingController = Optional.empty();
        setupMainFrame(onClose);
        this.emptyPanel = new JPanel();
        this.loginPanel = new LoginPanel(this, mainController.getReadingModel());
        this.acquistoBigliettoPanel = new AcquistoBigliettoPanel(this, mainController.getWritingModel());
        this.registrazionePanel = new RegVisitatorePanel(this, mainController.getWritingModel(), acquistoBigliettoPanel);
        this.accRegPanel = new AccediRegistratiPanel(this, CARD_LOGIN, CARD_REGISTRAZIONE);

        this.cardPanel.add(emptyPanel, EMPTY);
        this.cardPanel.add(loginPanel, CARD_LOGIN);
        this.cardPanel.add(acquistoBigliettoPanel, CARD_ACQUISTO_BIGLIETTO);
        this.cardPanel.add(registrazionePanel, CARD_REGISTRAZIONE);
        this.cardPanel.add(accRegPanel, CARD_ACCEDI_REGISTRATI);
        this.add(cardPanel);
        this.layout.show(this.cardPanel, CARD_ACCEDI_REGISTRATI);
        this.setVisible(true);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }

    private void setupMainFrame(final Runnable onClose) {
        final var padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        ((JComponent) this.getContentPane()).setBorder(padding);
        this.setSize(new Dimension(WIDTH, HEIGHT));
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(final WindowEvent e) {
                    onClose.run();
                    System.exit(0);
                }
            }
        );
        this.setLocationRelativeTo(null);
    }

    public void setReadingController(final ReadingController readingController) {
        Objects.requireNonNull(readingController, "Set null controller in view");
        this.readingController = Optional.of(readingController);
    }

    public <T> void showPanel(final List<T> voci, final String subtitle) {
        final var vociPanel = new VociPanel<T>(() -> showUserPanel());
        vociPanel.setVoci(voci, subtitle);
        vociPanel.setPreferredSize(new Dimension(this.getPreferredSize().width - 100, 
        this.getPreferredSize().height - 100));
        this.emptyPanel.add(vociPanel);
        this.layout.show(this.cardPanel, EMPTY);
    }

    public void changePanel(final String nome) {
        this.layout.show(this.cardPanel, nome);
    }

    public void setUserPanel(final User user, final String codiceFiscale) {
        switch(user) {
            case MANAGER -> {
                final var panel = new ManagerPanel(this.readingController.get(), codiceFiscale);
                this.cardPanel.add(panel, CARD_USER);
                this.layout.show(this.cardPanel, CARD_USER);
            }

            case VISITATORE -> {
                final var panel = new VisitatorePanel(this.readingController.get(), codiceFiscale);
                this.cardPanel.add(panel, CARD_USER);
                this.layout.show(this.cardPanel, CARD_USER);
            }

            case DIPENDENTE -> {
                final var panel = new DipendentiPanel(this.readingController.get(), codiceFiscale);
                this.cardPanel.add(panel, CARD_USER);
                this.layout.show(this.cardPanel, CARD_USER);
            }
        }
    }

    public void showUserPanel() {
        this.layout.show(this.cardPanel, CARD_USER);
        this.emptyPanel.removeAll();
    }

    public void showMenuPanel() {
        this.layout.show(this.cardPanel, CARD_ACCEDI_REGISTRATI);
        this.emptyPanel.removeAll();
    }

    public void showAcquistoBigliettoPanel() {
        this.layout.show(this.cardPanel, CARD_ACQUISTO_BIGLIETTO);
        this.emptyPanel.removeAll();
    }
}
