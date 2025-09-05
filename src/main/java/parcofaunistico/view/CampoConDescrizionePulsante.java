package parcofaunistico.view;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CampoConDescrizionePulsante extends JPanel{
    private final JLabel descrizione;
    private final JTextField campoTesto;
    private final JButton pulsante;

    public CampoConDescrizionePulsante(String testoDescrizione, String testoPulsante, final UserPanel container) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        descrizione = new JLabel(testoDescrizione);
        campoTesto = new JTextField(20);
        pulsante = new JButton(testoPulsante);

        this.add(descrizione);
        this.add(Box.createHorizontalStrut(10)); // Spazio tra label e campo
        this.add(campoTesto);
        this.add(Box.createHorizontalStrut(10)); // Spazio tra campo e pulsante
        this.add(pulsante);

        // Esempio di azione sul pulsante
        pulsante.addActionListener(e -> {
            final String valore = campoTesto.getText();
            container.notifyUserPressButton(valore);
        });
    }

    public String getTestoInserito() {
        return campoTesto.getText();
    }
}
