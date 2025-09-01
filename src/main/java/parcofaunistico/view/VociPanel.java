package parcofaunistico.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class VociPanel<T> extends JPanel {

    private final JPanel contentPanel;
    private final JButton backButton;
    private final Runnable backAction;

    public VociPanel(Runnable onBack) {
        super();
        this.backAction = onBack;

        this.setLayout(new BorderLayout());

        this.contentPanel = new JPanel();
        this.contentPanel.setOpaque(true);
        this.contentPanel.setBackground(Color.WHITE);
        this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setOpaque(true);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setOpaque(true);
        scrollPane.getViewport().setBackground(Color.WHITE);
        this.add(scrollPane, BorderLayout.CENTER);

        var bottom = new JPanel();
        backButton = new JButton("Torna al menu principale");
        backButton.addActionListener(e -> {
            if (backAction != null) backAction.run();
        });
        bottom.add(backButton);
        this.add(bottom, BorderLayout.SOUTH);
    }

    public void setVoci(List<T> voci, final String subTitle) {
        contentPanel.removeAll();
        final var label = new JLabel(subTitle);
        label.setBorder(new EmptyBorder(10, 10, 20, 0));
        contentPanel.add(label);
        addVoci(contentPanel, voci);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void addVoci(Container container, List<T> voci) {
        for (final T voce : voci) {
            final var label = new JLabel(voce.toString());
            label.setForeground(Color.black);
            label.setBorder(new EmptyBorder(0, 10, 10, 0));
            container.add(label);
        }
    }
}
