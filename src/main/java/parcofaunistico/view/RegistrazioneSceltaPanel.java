package parcofaunistico.view;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.UIManager;

public class RegistrazioneSceltaPanel extends JPanel{
    private final JButton visitatoreBtn = new JButton("Visitatore");
    private final JButton gruppoBtn = new JButton("Gruppo");

    public RegistrazioneSceltaPanel(final MainView mainView) {

        this.setLayout(new GridBagLayout());
        this.setOpaque(true);
        this.setBackground(UIManager.getColor("Panel.background"));
        this.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        final Font btnFont = UIManager.getFont("Button.font");
        visitatoreBtn.setBackground(UIManager.getColor("Button.background"));
        visitatoreBtn.setForeground(UIManager.getColor("Button.foreground"));
        visitatoreBtn.setBorderPainted(false);
        visitatoreBtn.setFocusPainted(false);
        visitatoreBtn.setFont(btnFont);
        gruppoBtn.setBackground(UIManager.getColor("Button.background"));
        gruppoBtn.setForeground(UIManager.getColor("Button.foreground"));
        gruppoBtn.setBorderPainted(false);
        gruppoBtn.setFocusPainted(false);
        gruppoBtn.setFont(btnFont);

        final Dimension bigSize = new Dimension(360, 96);
        visitatoreBtn.setPreferredSize(bigSize);
        gruppoBtn.setPreferredSize(bigSize);
        visitatoreBtn.setFocusPainted(false);
        gruppoBtn.setFocusPainted(false);

        this.gruppoBtn.addActionListener(e -> mainView.showPanel(Pannelli.REGISTRAZIONE_GRUPPO));
        this.visitatoreBtn.addActionListener(e -> mainView.showPanel(Pannelli.REGISTRAZIONE_VISITATORE));

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 18, 0);
        this.add(visitatoreBtn, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.add(gruppoBtn, gbc);
    }
}
