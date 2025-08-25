package parcofaunistico;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import parcofaunistico.data.Visitatore;

import java.util.List;

public final class MainView {

    public static final String CARD_LOADING = "loading";
    public static final String CARD_PERSONE = "persone";

    private Optional<Controller> controller;
    private final JFrame mainFrame;
    private final JPanel cardsPanel;
    private final PersonePanel personePanel;

    public MainView(Runnable onClose) {
        this.controller = Optional.empty();
        this.mainFrame = setupMainFrame(onClose);
        this.cardsPanel = new JPanel(new CardLayout());
        
        JPanel loadingPanel = new JPanel();
        loadingPanel.add(new JLabel("Loading persone...", SwingConstants.CENTER));

        this.personePanel = new PersonePanel();

        cardsPanel.add(loadingPanel, CARD_LOADING);
        cardsPanel.add(personePanel, CARD_PERSONE);

        this.mainFrame.add(cardsPanel);
    }

    private JFrame setupMainFrame(Runnable onClose) {
        var frame = new JFrame("Tessiland");
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
        return frame;
    }

    public void setController(Controller controller) {
        Objects.requireNonNull(controller, "Set null controller in view");
        this.controller = Optional.of(controller);
    }

    public void loadingPersone() {
        ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, CARD_LOADING);
    }

    public void showPersone(List<Visitatore> persone) {
        personePanel.setPersone(persone);
        ((CardLayout) cardsPanel.getLayout()).show(cardsPanel, CARD_PERSONE);
    }
}
