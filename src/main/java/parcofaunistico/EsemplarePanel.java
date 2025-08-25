package parcofaunistico;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import parcofaunistico.data.Esemplare;

public class EsemplarePanel extends JPanel {

    private final JPanel contentPanel;
    private final JButton backButton;
    private final Runnable backAction;

    public EsemplarePanel(Runnable onBack) {
        super();
        this.backAction = onBack;

        this.setLayout(new BorderLayout());

        this.contentPanel = new JPanel();
        this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        this.add(scrollPane, BorderLayout.CENTER);

        var bottom = new JPanel();
        backButton = new JButton("Torna al menu principale");
        backButton.addActionListener(e -> {
            if (backAction != null) backAction.run();
        });
        bottom.add(backButton);
        this.add(bottom, BorderLayout.SOUTH);
    }

    public void setEsemplari(List<Esemplare> esemplari) {
        contentPanel.removeAll();
        contentPanel.add(new JLabel("Esemplari:"));
        contentPanel.add(new JLabel(" "));
        addEsemplare(contentPanel, esemplari);
        contentPanel.add(new JLabel(" "));
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void addEsemplare(Container cp, List<Esemplare> esemplari) {
        for (Esemplare esemplare : esemplari) {
            cp.add(new JLabel(esemplare.toString()));
        }
    }
}