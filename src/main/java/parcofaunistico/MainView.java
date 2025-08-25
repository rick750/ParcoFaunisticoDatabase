package parcofaunistico;

import java.awt.CardLayout;
import java.awt.Dimension;
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

import parcofaunistico.data.Esemplare;
import parcofaunistico.data.Visitatore;

import java.util.List;

public final class MainView {

    public static final String CARD_MAIN = "main";
    public static final String CARD_LOADING = "loading";
    public static final String CARD_PERSONE = "persone";
    public static final String CARD_ESEMPLARI = "esemplari";

    private Optional<Controller> controller;
    private final JFrame mainFrame;
    private final JPanel cardsPanel;
    private final PersonePanel personePanel;
    private final EsemplarePanel esemplarePanel;
    private final JPanel mainMenuPanel;

    public MainView(Runnable onClose) {
        this.controller = Optional.empty();
        this.mainFrame = setupMainFrame(onClose);
        this.cardsPanel = new JPanel(new CardLayout());

        this.personePanel = new PersonePanel(() -> showMainMenu());
        this.esemplarePanel = new EsemplarePanel(() -> showMainMenu());

        this.mainMenuPanel = createMainMenuPanel();

        cardsPanel.add(mainMenuPanel, CARD_MAIN);
        cardsPanel.add(personePanel, CARD_PERSONE);
        cardsPanel.add(esemplarePanel, CARD_ESEMPLARI);

        this.mainFrame.add(cardsPanel);

        this.showMainMenu();
        this.mainFrame.setVisible(true);
    }

    private JFrame setupMainFrame(Runnable onClose) {
        var frame = new JFrame("Parco Faunistico");
        var padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        ((JComponent) frame.getContentPane()).setBorder(padding);
        frame.setMinimumSize(new Dimension(1200, 400));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    onClose.run();
                    System.exit(0);
                }
            }
        );
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    private JPanel createMainMenuPanel() {
        var panel = new JPanel();
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

        panel.add(btnPersone);
        panel.add(new JLabel(" "));
        panel.add(btnEsemplari);

        return panel;
    }

    public void setController(Controller controller) {
        Objects.requireNonNull(controller, "Set null controller in view");
        this.controller = Optional.of(controller);
    }

    public void showPersone(List<Visitatore> persone) {
        personePanel.setPersone(persone);
        ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, CARD_PERSONE);
    }

    public void showEsemplari(List<Esemplare> esemplari) {
        esemplarePanel.setEsemplari(esemplari);
        ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, CARD_ESEMPLARI);
    }

    public void showMainMenu() {
        ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, CARD_MAIN);
    }
}
