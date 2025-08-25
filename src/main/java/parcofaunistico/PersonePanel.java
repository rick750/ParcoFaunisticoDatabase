package parcofaunistico;

import java.awt.Container;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import parcofaunistico.data.Visitatore;

public class PersonePanel extends JPanel {

    private final JPanel contentPanel;

    public PersonePanel() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.contentPanel = new JPanel();
        this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        this.add(scrollPane);
    }

    public void setPersone(List<Visitatore> persone) {
        contentPanel.removeAll();
        contentPanel.add(new JLabel("Persone:"));
        contentPanel.add(new JLabel(" "));
        addPersone(contentPanel, persone);
        contentPanel.add(new JLabel(" "));
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void addPersone(Container cp, List<Visitatore> persone) {
        for (Visitatore persona : persone) {
            cp.add(new JLabel(persona.toString()));
        }
    }
}
