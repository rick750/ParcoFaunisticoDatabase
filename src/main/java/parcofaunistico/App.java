package parcofaunistico;
 
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.UIManager;

import parcofaunistico.model.ReadingModelImpl;
import parcofaunistico.model.WritingModelImpl;
import parcofaunistico.view.MainView;
import parcofaunistico.controller.MainController;
import parcofaunistico.controller.ReadingController;
import parcofaunistico.data.DAOUtils;
 
public final class App {
    
    public static void main(String[] args) throws SQLException {
        setUpUI();
        final var connection = DAOUtils.localMySQLConnection("parco_faunistico", "root", "");
        final var readingModel = new ReadingModelImpl(connection);
        final var writingModel = new WritingModelImpl(connection);
        final var mainController = new MainController(readingModel, writingModel);
        var view = new MainView(mainController, () -> {
            try {
               connection.close();
            } catch (Exception ignored) {}
        });
        var readingController = new ReadingController(readingModel, view);
        view.setReadingController(readingController);
    }

    private static void setUpUI() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {

        }
        UIManager.put("Panel.background", new Color(18, 30, 49));
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 30));
        UIManager.put("Button.background", new Color(255, 185, 0));
        UIManager.put("Button.foreground", new Color(18, 30, 49));
        UIManager.put("BigLabel.font", new Font("Segoe UI", Font.PLAIN, 40));
        UIManager.put("Field.font", new Font("Segoe UI", Font.PLAIN, 24));
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 30));
        UIManager.put("Label.background", new Color(173, 216, 230));
        UIManager.put("Label.foreground", new Color(144, 238, 144));
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 30));
    }    
}
