package parcofaunistico.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import parcofaunistico.controller.MainController;
import parcofaunistico.controller.ReadingController;

import javax.swing.BoxLayout;

import java.util.List;

public final class MainView extends JFrame{

    private static final String CARD_ACCEDI_REGISTRATI = "accReg";
    private static final String CARD_LOGIN = "login";
    private static final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int WIDTH = (int) SCREENSIZE.getWidth();
    private static final int HEIGHT = (int) SCREENSIZE.getHeight();
    public static final String CARD_MAIN = "main";
    public static final String EMPTY = "empty";

    private final MainController mainController;
    private Optional<ReadingController> readingController;
    private final CardLayout layout = new CardLayout();
    private final JPanel cardPanel = new JPanel(this.layout);
    private final JPanel emptyPanel;
    private final JPanel mainMenuPanel;
    private final LoginPanel loginPanel;
    private final AccediRegistratiPanel accRegPanel;

    public MainView(final MainController mainController, final Runnable onClose) {
        this.readingController = Optional.empty();
        this.mainController = mainController;
        setupMainFrame(onClose);
        this.mainMenuPanel = createMainMenuPanel();
        this.emptyPanel = new JPanel();
        this.loginPanel = new LoginPanel(this, mainController.getModel(), CARD_LOGIN);
        this.accRegPanel = new AccediRegistratiPanel(this, CARD_LOGIN);

        this.cardPanel.add(emptyPanel, EMPTY);
        this.cardPanel.add(mainMenuPanel, CARD_MAIN);
        this.cardPanel.add(loginPanel, CARD_LOGIN);
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

    private JPanel createMainMenuPanel() {
        final var panel = new JPanel();
        panel.setPreferredSize(this.getPreferredSize());
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(new JLabel("\n"));
        panel.add(new JLabel("Parco Faunistico"));
        panel.add(new JLabel("\n"));

        final var btnPersone = new JButton("Pagina Visitatori");
        btnPersone.addActionListener(e -> {
            if (readingController.isPresent()) {
                readingController.get().userRequestedPersone();
            }
        });

        final var btnEsemplari = new JButton("Pagina Esemplari");
        btnEsemplari.addActionListener(e -> {
            if (readingController.isPresent()) {
                readingController.get().userRequestedEsemplari();
            }
        });

        final var btnAffluenze = new JButton("Pagina Affluenze");
        btnAffluenze.addActionListener(e -> {
            if (readingController.isPresent()) {
                readingController.get().userRequestedAffluenze();
            }
        });

        final var btnAppSconto = new JButton("Pagina Applicazioni Sconto");
        btnAppSconto.addActionListener(e -> {
            if (readingController.isPresent()) {
                readingController.get().userRequestedApplicazioniSconto();
            }
        });

        final var btnIncBiglietti = new JButton("Pagina Incassi Biglietti");
         btnIncBiglietti.addActionListener(e -> {
            if (readingController.isPresent()) {
                readingController.get().userRequestedIncassiBiglietti();
            }
        });

        final var btnClassProdotti = new JButton("Pagina classifica Prodotti");
        btnClassProdotti.addActionListener(e -> {
            if (readingController.isPresent()) {
                readingController.get().userRequestedClassificaProdotti();
            }
        });

        final var btnAcqProdotti = new JButton("Pagina acquisti prodotti");
        btnAcqProdotti.addActionListener(e -> {
            if (readingController.isPresent()) {
                readingController.get().userRequestedAcquistiProdotti();
            }
        });

        panel.add(btnPersone);
        panel.add(new JLabel(" "));
        panel.add(btnEsemplari);
        panel.add(new JLabel(" "));
        panel.add(btnAffluenze);
        panel.add(new JLabel(" "));
        panel.add(btnAppSconto);
        panel.add(new JLabel(" "));
        panel.add(btnIncBiglietti);
        panel.add(new JLabel(" "));
        panel.add(btnClassProdotti);
        panel.add(new JLabel(" "));
        panel.add(btnAcqProdotti);

        return panel;
    }

    public void setReadingController(final ReadingController readingController) {
        Objects.requireNonNull(readingController, "Set null controller in view");
        this.readingController = Optional.of(readingController);
    }

    public <T> void showPanel(final List<T> voci, final String subtitle) {
        this.remove(this.mainMenuPanel);
        final var vociPanel = new VociPanel<T>(() -> showMainMenu());
        vociPanel.setVoci(voci, subtitle);
        vociPanel.setPreferredSize(new Dimension(this.mainMenuPanel.getPreferredSize().width - 100, 
        this.mainMenuPanel.getPreferredSize().height - 100));
        this.emptyPanel.add(vociPanel);
        this.layout.show(this.cardPanel, EMPTY);
    }

    public void changePanel(final String nome) {
        this.layout.show(this.cardPanel, nome);
    }

    public void showMainMenu() {
        this.layout.show(this.cardPanel, CARD_MAIN);
        this.emptyPanel.removeAll();
    }
}
