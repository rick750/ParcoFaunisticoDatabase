package parcofaunistico.view;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AccediRegistratiPanel extends JPanel{

    private final JPanel mainPanel;
    private final JButton accediBtn = new JButton("Accedi");
    private final JButton registratiBtn = new JButton("Registrati");
    private final String nomeLogin;

    public AccediRegistratiPanel(final MainView mainView, final String nomeLogin) {
        this.nomeLogin = nomeLogin;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.mainPanel = new JPanel(new FlowLayout());
        this.setUpPanels(mainView);
        this.mainPanel.add(registratiBtn);
        this.mainPanel.add(accediBtn);
        this.add(mainPanel);
    }

    private void setUpPanels(final MainView mainView) {
        this.registratiBtn.addActionListener(
            e -> {
                //mainView.changePanel(this.nomePanel);
            }
        );

        this.accediBtn.addActionListener(
            e -> {
                mainView.changePanel(this.nomeLogin);
            }
        );
    }

}
