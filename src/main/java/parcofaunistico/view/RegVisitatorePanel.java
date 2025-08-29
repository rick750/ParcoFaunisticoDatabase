package parcofaunistico.view;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import parcofaunistico.model.WritingModel;

public class RegVisitatorePanel extends JPanel{
    private final MainView mainView;
    private final WritingModel writingModel;
    public RegVisitatorePanel(final MainView mainView, final WritingModel writingModel) {
        this.mainView = mainView;
        this.writingModel = writingModel;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel("Benvenuto al menù di registrazione"));

        final JButton backButton = new JButton("INDIETRO");
        this.add(backButton);
        backButton.addActionListener(
            e -> {
                mainView.showMenuPanel();
            }
        );
    }
}
