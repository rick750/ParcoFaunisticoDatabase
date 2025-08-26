package parcofaunistico;

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
import javax.swing.BoxLayout;

import java.util.List;

public final class MainView extends JFrame{

    private static final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int WIDTH = (int) SCREENSIZE.getWidth();
    private static final int HEIGHT = (int) SCREENSIZE.getHeight();
    public static final String CARD_MAIN = "main";
    public static final String EMPTY = "empty";

    private Optional<Controller> controller;
    private final CardLayout layout = new CardLayout();
    private final JPanel cardPanel = new JPanel(this.layout);
    private final JPanel emptyPanel;
    private final JPanel mainMenuPanel;

    public MainView(Runnable onClose) {
        this.controller = Optional.empty();
        setupMainFrame(onClose);
        this.mainMenuPanel = createMainMenuPanel();
        this.emptyPanel = new JPanel();

        this.cardPanel.add(emptyPanel, EMPTY);
        this.cardPanel.add(mainMenuPanel, CARD_MAIN);
        this.add(cardPanel);
        this.layout.show(this.cardPanel, CARD_MAIN);
        this.setVisible(true);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }

    private void setupMainFrame(Runnable onClose) {
        var padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        ((JComponent) this.getContentPane()).setBorder(padding);
        this.setSize(new Dimension(WIDTH, HEIGHT));
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    onClose.run();
                    System.exit(0);
                }
            }
        );
        this.setLocationRelativeTo(null);
    }

    private JPanel createMainMenuPanel() {
        var panel = new JPanel();
        panel.setPreferredSize(this.getPreferredSize());
        System.out.println("Main Menu " + panel.getPreferredSize());
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(new JLabel("\n"));
        panel.add(new JLabel("Parco Faunistico"));
        panel.add(new JLabel("\n"));

        var btnPersone = new JButton("Pagina Visitatori");
        btnPersone.addActionListener(e -> {
            if (controller.isPresent()) {
                controller.get().userRequestedPersone();
            }
        });

        var btnEsemplari = new JButton("Pagina Esemplari");
        btnEsemplari.addActionListener(e -> {
            if (controller.isPresent()) {
                controller.get().userRequestedEsemplari();
            }
        });

        var btnAffluenze = new JButton("Pagina Affluenze");
        btnAffluenze.addActionListener(e -> {
            if (controller.isPresent()) {
                controller.get().userRequestedAffluenze();
            }
        });

        var btnAppSconto = new JButton("Pagina Applicazioni Sconto");
        btnAppSconto.addActionListener(e -> {
            if (controller.isPresent()) {
                controller.get().userRequestedApplicazioniSconto();
            }
        });

        var btnIncBiglietti = new JButton("Pagina Incassi Biglietti");
         btnIncBiglietti.addActionListener(e -> {
            if (controller.isPresent()) {
                controller.get().userRequestedIncassiBiglietti();
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

        return panel;
    }

    public void setController(Controller controller) {
        Objects.requireNonNull(controller, "Set null controller in view");
        this.controller = Optional.of(controller);
    }

    public <T> void showPanel(List<T> voci, final String subtitle) {
        this.remove(this.mainMenuPanel);
        System.out.println("Aggiungo pannello con subtitle: " + subtitle);
        final var vociPanel = new VociPanel<T>(() -> showMainMenu());
        vociPanel.setVoci(voci, subtitle);
        vociPanel.setPreferredSize(new Dimension(this.mainMenuPanel.getPreferredSize().width - 100, 
        this.mainMenuPanel.getPreferredSize().height - 100));
        System.out.println("VociPanel " + vociPanel.getPreferredSize());
        this.emptyPanel.add(vociPanel);
        this.layout.show(this.cardPanel, EMPTY);
        
    }

    public void showMainMenu() {
        this.layout.show(this.cardPanel, CARD_MAIN);
        this.emptyPanel.removeAll();
    }
}
