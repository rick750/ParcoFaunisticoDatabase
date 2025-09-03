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
import javax.swing.JScrollPane;

import parcofaunistico.controller.MainController;
import parcofaunistico.controller.ReadingController;
import parcofaunistico.data.Pannelli;
import parcofaunistico.data.User;
import parcofaunistico.model.WritingModel;

import java.util.List;

public final class MainView extends JFrame{
    private static final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int WIDTH = (int) SCREENSIZE.getWidth();
    private static final int HEIGHT = (int) SCREENSIZE.getHeight();
    public static final String CARD_USER = "user";
    public static final String EMPTY = "empty";

    private Optional<ReadingController> readingController;
    private WritingModel writingModel;
    private final CardLayout layout = new CardLayout();
    private final JPanel cardPanel = new JPanel(this.layout);
    private final JPanel emptyPanel;
    private final LoginPanel loginPanel;
    private final RegistrazioneSceltaPanel registrazionePanel;
    private final RegVisitatorePanel regVisitatorePanel;
    private final RegGruppoPanel regGruppoPanel;
    private final AcquistoBigliettoPanel acBigliettoVisitatorePanel;
    private final AcquistoBigliettoPanel acBigliettoGruppoPanel;
    private final AccediRegistratiPanel accRegPanel;
    private final OrdiniPanel ordiniPanel;
    private final RegDipendentePanel regDipendentePanel;

    public MainView(final MainController mainController, final Runnable onClose) {
        this.readingController = Optional.empty();
        this.writingModel = mainController.getWritingModel();
        setupMainFrame(onClose);
        this.emptyPanel = new JPanel();
        this.loginPanel = new LoginPanel(this, mainController.getReadingModel());
        this.registrazionePanel = new RegistrazioneSceltaPanel(this);
        this.acBigliettoVisitatorePanel = new AcquistoBigliettoPanel(this, mainController.getWritingModel());
        this.acBigliettoGruppoPanel = new AcquistoBigliettoPanel(this, mainController.getWritingModel());
        this.regVisitatorePanel = new RegVisitatorePanel(this, mainController.getWritingModel(), acBigliettoVisitatorePanel);
        this.regGruppoPanel = new RegGruppoPanel(this, mainController.getWritingModel(), acBigliettoGruppoPanel);
        this.ordiniPanel = new OrdiniPanel(this, mainController.getWritingModel());
        this.regDipendentePanel = new RegDipendentePanel(this, writingModel);

        final JScrollPane scrollGruppo = new JScrollPane(this.regGruppoPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.accRegPanel = new AccediRegistratiPanel(this);

        this.cardPanel.add(emptyPanel, EMPTY);
        this.cardPanel.add(loginPanel, Pannelli.LOGIN.get());
        this.cardPanel.add(registrazionePanel, Pannelli.REGISTRAZIONE.get());
        this.cardPanel.add(acBigliettoGruppoPanel, Pannelli.ACQUISTO_BIGLIETTO_GRUPPO.get());
        this.cardPanel.add(acBigliettoVisitatorePanel, Pannelli.ACQUISTO_BIGLIETTO_VISITATORE.get());
        this.cardPanel.add(regVisitatorePanel, Pannelli.REGISTRAZIONE_VISITATORE.get());
        this.cardPanel.add(scrollGruppo, Pannelli.REGISTRAZIONE_GRUPPO.get());
        this.cardPanel.add(accRegPanel, Pannelli.ACCEDI_REGISTRATI.get());
        this.cardPanel.add(ordiniPanel, Pannelli.ORDINE.get());
        this.cardPanel.add(regDipendentePanel, Pannelli.REGISTRAZIONE_DIPENDENTE.get());
        this.add(cardPanel);
        this.layout.show(this.cardPanel, Pannelli.ACCEDI_REGISTRATI.get());
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

    public <T> void showVociPanel(final List<T> voci, final String subtitle) {
        final var vociPanel = new VociPanel<T>(() -> showPanel(Pannelli.USER));
        vociPanel.setVoci(voci, subtitle);
        vociPanel.setPreferredSize(new Dimension(this.getPreferredSize().width - 100, 
        this.getPreferredSize().height - 100));
        this.emptyPanel.add(vociPanel);
        this.layout.show(this.cardPanel, EMPTY);
    }

    public void setUserPanel(final User user, final String codiceFiscale) {
        switch(user) {
            case MANAGER -> {
                final var panel = new ManagerPanel(this.readingController.get(), writingModel, this, codiceFiscale);
                this.cardPanel.add(panel, CARD_USER);
                this.layout.show(this.cardPanel, CARD_USER);
            }

            case VISITATORE -> {
                final var panel = new VisitatorePanel(this.readingController.get(), codiceFiscale);
                this.cardPanel.add(panel, CARD_USER);
                this.layout.show(this.cardPanel, CARD_USER);
            }

            case DIPENDENTE -> {
                final var panel = new DipendentiPanel(this.readingController.get(), this.writingModel, this, codiceFiscale);
                this.cardPanel.add(panel, CARD_USER);
                this.layout.show(this.cardPanel, CARD_USER);
            }
        }
    }

    public void showPanel(final Pannelli panelName) {
        this.layout.show(this.cardPanel, panelName.get());
        this.emptyPanel.removeAll();
    }

    public void notifyVisitatoreInsert() {
        this.regVisitatorePanel.executeInsertVisitatore();
    }

    public void notifyGruppoInsert() {
        this.regGruppoPanel.executeInsertGruppo();
        this.regGruppoPanel.restartData();
    }
}
