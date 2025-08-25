package parcofaunistico;

import java.sql.SQLException;

import parcofaunistico.model.Model;
import parcofaunistico.data.DAOUtils;

public final class App {

    public static void main(String[] args) throws SQLException {
        var connection = DAOUtils.localMySQLConnection("parco_faunistico", "root", "");
        var model = Model.fromConnection(connection);
        var view = new MainView(() -> {
            try {
               connection.close();
            } catch (Exception ignored) {}
        });
        var controller = new Controller(model, view);
        view.setController(controller);
        controller.userRequestedInitialPage();
    }
}
