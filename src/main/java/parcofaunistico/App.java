package parcofaunistico;
 
import java.sql.SQLException;

import parcofaunistico.model.ReadingModelImpl;
import parcofaunistico.model.WritingModelImpl;
import parcofaunistico.view.MainView;
import parcofaunistico.controller.MainController;
import parcofaunistico.controller.ReadingController;
import parcofaunistico.data.DAOUtils;
 
public final class App {
 
    public static void main(String[] args) throws SQLException {
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

    
}