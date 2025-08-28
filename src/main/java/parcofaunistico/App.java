package parcofaunistico;
 
import java.sql.SQLException;
 
import parcofaunistico.model.Model;
import parcofaunistico.view.MainView;
import parcofaunistico.controller.MainController;
import parcofaunistico.controller.ReadingController;
import parcofaunistico.data.DAOUtils;
 
public final class App {
 
    public static void main(String[] args) throws SQLException {
        var connection = DAOUtils.localMySQLConnection("parco_faunistico", "root", "");
        var model = Model.fromConnection(connection);
        var mainController = new MainController(model);
        var view = new MainView(mainController, () -> {
            try {
               connection.close();
            } catch (Exception ignored) {}
        });
        var readingController = new ReadingController(model, view);
        view.setReadingController(readingController);
        
    }
}