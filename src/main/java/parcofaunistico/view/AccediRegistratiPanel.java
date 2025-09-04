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

public class AccediRegistratiPanel extends JPanel{

    private final JButton accediBtn = new JButton("Accedi");
    private final JButton registratiBtn = new JButton("Registrati");

    public AccediRegistratiPanel(final MainView mainView) {
        this.setLayout(new GridBagLayout());
        this.setOpaque(true);
        this.setBackground(UIManager.getColor("Panel.background"));
        this.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        final Font btnFont = UIManager.getFont("Button.font");
        accediBtn.setBackground(UIManager.getColor("Button.background"));
        accediBtn.setForeground(UIManager.getColor("Button.foreground"));
        accediBtn.setBorderPainted(false);
        accediBtn.setFocusPainted(false);
        accediBtn.setFont(btnFont);
        registratiBtn.setBackground(UIManager.getColor("Button.background"));
        registratiBtn.setForeground(UIManager.getColor("Button.foreground"));
        registratiBtn.setBorderPainted(false);
        registratiBtn.setFocusPainted(false);
        registratiBtn.setFont(btnFont);

        final Dimension bigSize = new Dimension(360, 96);
        accediBtn.setPreferredSize(bigSize);
        registratiBtn.setPreferredSize(bigSize);
        accediBtn.setFocusPainted(false);
        registratiBtn.setFocusPainted(false);

        this.registratiBtn.addActionListener(e -> mainView.showPanel(Pannelli.REGISTRAZIONE));
        this.accediBtn.addActionListener(e -> mainView.showPanel(Pannelli.LOGIN));

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 18, 0);
        this.add(accediBtn, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.add(registratiBtn, gbc);
    }

}
